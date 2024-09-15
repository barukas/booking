package com.gft.hsbc.hbmx.server;

import com.gft.hsbc.hbmx.model.Booking;
import com.google.gson.Gson;
import jakarta.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.sql.*;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Path("/reservations")
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class BookingController {

    private static final String JDBC_URL = "jdbc:h2:mem:test";

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public String createReservation(Booking booking) throws ParseException, SQLException {
        boolean booleanInsert = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Connection connection = DriverManager.getConnection(JDBC_URL);
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        ResultSet validateReservation = statement.executeQuery(
                "SELECT " +
                "   TABLE_SIZE," +
                "   DATE, " +
                "   TIME " +
                " FROM " +
                    "BOOKING" +
                " WHERE " +
                    "TABLE_SIZE="  + booking.getTableSize() +
                " AND " +
                    "DATE='" + booking.getDate() + "'"
                );

        try {
            System.out.println("Antes del try 1");
            while (validateReservation.next()) {
                System.out.println("Antes del try 2");
                int tableSize = validateReservation.getInt("TABLE_SIZE");
                String time = validateReservation.getString("TIME");
                if (tableSize > 0) {
                    Time timeIn = Time.valueOf(time);
                    LocalTime localTime = timeIn.toLocalTime();
                    LocalTime localtimePlus = localTime.plus(Duration.ofHours(2));

                    if (localtimePlus.isBefore(booking.getTime().toLocalTime())) {
                        booleanInsert = true;
                    } else {
                        System.out.println("NO INSERT");
                        return "THERE IS ALREADY A RESERVATION FOR THAT TABLE WITH THAT DATE AND WITH THAT TIME";
                    }
                } else {
                    booleanInsert = false;
                }
            }
        } catch (Exception exception) {
            System.out.println("NO RECORDS:" + exception);
        }
        if (booleanInsert) {
            LocalDate myDate = LocalDate.parse(booking.getDate().toString(), formatter);
            Time timeInBooking = Time.valueOf(booking.getTime().toString());
            String insertBooking = "INSERT INTO BOOKING " +
                    "(CUSTOMER_NAME, TABLE_SIZE, DATE, TIME) " +
                    "VALUES ('"+ booking.getCustomerName() + "'," + booking.getTableSize() + ",DATE'" + myDate + "',TIME'" + timeInBooking + "')";
            statement.execute(insertBooking);
        }
        return "SUCCESSFUL RESERTVATION";
    }

    @GET
    @Path("/{date}")
    @Produces("application/json")
    public String getReservationByDate(@PathParam("date") String date) throws SQLException {

        Connection connection = DriverManager.getConnection(JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT CUSTOMER_NAME, TABLE_SIZE, DATE, TIME FROM BOOKING WHERE DATE='" + date + "'");

        List<Booking> bookingList = new ArrayList<>();


        while (result.next()) {
            System.out.println("Entra al while");
            Booking booking = new Booking();
            booking.setCustomerName(result.getString("CUSTOMER_NAME"));
            booking.setTableSize(result.getString("TABLE_SIZE"));
            booking.setDate(result.getString("DATE"));
            booking.setTime(Time.valueOf(result.getString("TIME")));

            bookingList.add(booking);
        }


        Gson gson = new Gson();
        String json = gson.toJson(bookingList);
        return json;
    }
}
