package com.centennial.comp228.finaltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtil {
    private static boolean isDriverLoaded = false;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Loaded");
            isDriverLoaded = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final static String url = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private final static String user = "COMP214_M22_am_69";
    private final static String password = "password";

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        if (isDriverLoaded) {
            con = DriverManager.getConnection(url, user, password);
        }
        return con;
    }


    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("connection closed");
        }
    }
}