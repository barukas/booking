package com.gft.hsbc.hbmx;

import com.gft.hsbc.hbmx.database.H2;
import com.gft.hsbc.hbmx.server.Server;

import java.sql.SQLException;

public class Main {

    public static void main (String args [] ) throws SQLException {
        H2 h2 = new H2();
        h2.createTables();
        h2.start();

        Server server = new Server();
        server.createServer();
    }
}
