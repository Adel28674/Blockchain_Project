package com.example.blockchain;
import java.sql.*;

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


    public static boolean signup(String userLogin, String userMDP, String userMail, String userPhone, String userName) throws SQLException {

        ConnectionToDb.connect();

        // Requête d'insertion
        String insertionSQL = "INSERT INTO User VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionToDb.con.prepareStatement(insertionSQL);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userMDP);
            preparedStatement.setString(3, userMail);
            preparedStatement.setString(4, userPhone);
            preparedStatement.setString(5, userName);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur dans la requête SQL"+e);
        }

        // Exécution de la requête d'insertion
        int lignesModifiees = preparedStatement.executeUpdate();

        if (lignesModifiees > 0) {
            System.out.println("Insertion réussie !");
            return true;
        } else {
            System.out.println("Échec de l'insertion !");
        }
        return false;
    }


    public static boolean verifyCredentials(String userLogin, String userMDP) throws SQLException {

        ConnectionToDb.connect();


        String requete  = "SELECT * FROM User";

        try (
             Statement statement  = con.createStatement();
             ResultSet lignes= statement.executeQuery(requete)){


            while (lignes.next()) {
                //System.out.println(rs.getInt("id") +  "\t" +
                 //       rs.getString("name") + "\t" +
                   //     rs.getDouble("capacity"));
                System.out.println(lignes.getString("userName") + " s'est connecté ");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
