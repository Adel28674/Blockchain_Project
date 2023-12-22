package com.example.blockchain;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDb {

    public static Connection con = null;

    public static Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/data/mydata.db");
            System.out.println("Connected !");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return con;
    }
}
