package com.example.blockchain.modele;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.blockchain.modele.Wallet.deserializeWalletsList;
import static com.example.blockchain.modele.Wallet.serializeListValues;

public class ConnectionToDB {

    public static Connection con = null;

    public static Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/data/mydata.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return con;
    }


    public static boolean signup(String userLogin, String userMDP, String userMail, String userPhone, String userName) throws SQLException {

        com.example.blockchain.modele.ConnectionToDB.connect();

        // Requête d'insertion
        String insertionSQL = "INSERT INTO User VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = com.example.blockchain.modele.ConnectionToDB.con.prepareStatement(insertionSQL);
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

        com.example.blockchain.modele.ConnectionToDB.connect();


        String requete = "SELECT * FROM User WHERE userLogin=? AND userMDP=?";

        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userMDP);

            try (ResultSet lignes = preparedStatement.executeQuery()) {
                while (lignes.next()) {
                    //System.out.println(rs.getInt("id") +  "\t" +
                    //       rs.getString("name") + "\t" +
                    //     rs.getDouble("capacity"));
                    System.out.println(lignes.getString("userName") + " s'est connecté ");
                    return true;
                }
            }
        } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return false;

    }

    public static boolean existsUser(String userLogin) throws SQLException {

        com.example.blockchain.modele.ConnectionToDB.connect();

        String requete = "SELECT * FROM User WHERE userLogin=? ";

        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, userLogin);


            try (ResultSet lignes = preparedStatement.executeQuery()) {
                if (lignes.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public static List<String> getUserInfo(String userLogin) {

        com.example.blockchain.modele.ConnectionToDB.connect();

        String requete = "SELECT * FROM User WHERE userLogin=? ";

        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, userLogin);

            List<String> info = new ArrayList<>();

            try (ResultSet lignes = preparedStatement.executeQuery()) {
                while (lignes.next()) {
                    info.add(lignes.getString("userLogin"));
                    info.add(lignes.getString("userMDP"));
                    info.add(lignes.getString("userMail"));
                    info.add(lignes.getString("userPhone"));
                    info.add(lignes.getString("userName"));
                }

                return info;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }


    public static boolean StockWalletInDatabase(Investor investor) throws SQLException {

        com.example.blockchain.modele.ConnectionToDB.connect();
        if (existsWallet(investor.getUserLogin())){
            String requete = "UPDATE Wallets SET ListWallets = ? WHERE userLogin = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(requete)){
                preparedStatement.setString(2, investor.getUserLogin());
                preparedStatement.setBytes(1, serializeListValues(investor.getWallets()));


                int lignesModifiees = preparedStatement.executeUpdate();

                if (lignesModifiees > 0) {
                    System.out.println("Modification réussie !");
                    return true;
                } else {
                    System.out.println("Échec de la modification !");
                }
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else{
            String requete = "INSERT INTO Wallets (UserLogin, ListWallets) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(requete)){
                preparedStatement.setString(1, investor.getUserLogin());
                preparedStatement.setBytes(2, serializeListValues(investor.getWallets()));


                int lignesModifiees = preparedStatement.executeUpdate();

                if (lignesModifiees > 0) {
                    System.out.println("Insertion réussie !");
                    return true;
                } else {
                    System.out.println("Échec de l'insertion !");
                }
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static HashMap<UUID, Wallet> getWalletsFromDatabase(Investor investor) throws SQLException, IOException, ClassNotFoundException {
        Connection con = com.example.blockchain.modele.ConnectionToDB.connect();
        String requete = "SELECT ListWallets FROM Wallets WHERE userLogin = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, investor.getUserLogin());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] serializedData = resultSet.getBytes("ListWallets");
                return deserializeWalletsList(serializedData);
            }
        }
        return new HashMap<>();
    }

    public static boolean existsWallet(String userLogin) throws SQLException {

        com.example.blockchain.modele.ConnectionToDB.connect();

        String requete = "SELECT * FROM Wallets WHERE userLogin=? ";

        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, userLogin);


            try (ResultSet lignes = preparedStatement.executeQuery()) {
                if (lignes.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }


    public static boolean UpdateInformation(String username, String name, String mail, String phone) throws SQLException {

        com.example.blockchain.modele.ConnectionToDB.connect();
        if (existsWallet(username)){
            String requete = "UPDATE User SET userName = ?, userMail = ?, userPhone=? WHERE userLogin = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(requete)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, mail);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, username);



                int lignesModifiees = preparedStatement.executeUpdate();

                if (lignesModifiees > 0) {
                    System.out.println("Modification réussie !");
                    return true;
                } else {
                    System.out.println("Échec de la modification !");
                }
                return false;
            }

        }
        return false;
    }




}
