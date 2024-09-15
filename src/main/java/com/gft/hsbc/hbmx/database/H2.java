package com.gft.hsbc.hbmx.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

@Configuration
public class H2 {

    private static final String JDBC_URL = "jdbc:h2:mem:test";
    private static final String STM_CREATE_BOOKING = """
            CREATE TABLE BOOKING (
                CUSTOMER_NAME VARCHAR(50),
                TABLE_SIZE INT,
                DATE DATE,
                TIME TIME,
                PRIMARY KEY(TABLE_SIZE, DATE, TIME)
            );
            """;

    private org.h2.tools.Server webServer;

    private org.h2.tools.Server tcpServer;


    private static Logger logger = Logger.getLogger(H2.class.getName());

    private Connection connection;

    private Statement statement;

    public void createTables() throws SQLException {
        this.connection = DriverManager.getConnection(H2.JDBC_URL);

        System.out.println("Connected to H2 in-memory database.");
        //String sql = "Create table students (ID int primary key, name varchar(50))";

        this.statement = this.connection.createStatement();
        this.statement.execute(H2.STM_CREATE_BOOKING);
        System.out.println("Created table booking.");
        connection.createStatement();
    }

    public final Statement getStatement() {
        return this.statement;
    }

    public final Connection getConnection() {
        return this.connection;
    }

    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {

        this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8083", "-tcpAllowOthers").start();
        this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "8082", "-tcpAllowOthers").start();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.tcpServer.stop();
        this.webServer.stop();
    }
}
