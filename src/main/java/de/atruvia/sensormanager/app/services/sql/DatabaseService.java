package de.atruvia.sensormanager.app.services.sql;

import de.atruvia.sensormanager.app.settings.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseService {

    private final static Logger LOGGER = Logger.getLogger("Manager.DatabaseService");
    private Connection connection;

    public DatabaseService() {

        LOGGER.setLevel(Level.ALL);

        String con = "jdbc:mysql://" + DatabaseConfiguration.HOST + ":" + DatabaseConfiguration.PORT + "/" + DatabaseConfiguration.DATABASE;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Verbindung wird hergestellt!");
            this.connection = DriverManager.getConnection(con, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);

            LOGGER.finest("SQL-Verbindung aufgebaut!");

            //Cache.updateReflesh();

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Fehler beim Aufbauen der SQL Verbindung");
            e.printStackTrace();
        }
    }

    public boolean perform(String qry) {
        try {
            getConnection().prepareStatement(qry).executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("SQL Fehler");
            e.printStackTrace();
        }
        return false;
    }

    public Optional<ResultSet> getResult(String qry) {
        try {
            return Optional.of(getConnection().prepareStatement(qry).executeQuery());
        } catch (SQLException e) {
            LOGGER.severe("SQL Fehler");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Connection getConnection() {
        return connection;
    }

    public void reconnect() {

        //Cache.updateReflesh();

        String con = "jdbc:mysql://" + DatabaseConfiguration.HOST + ":" + DatabaseConfiguration.PORT + "/" + DatabaseConfiguration.DATABASE;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Verbindung wird hergestellt!");
            this.connection = DriverManager.getConnection(con, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);
            LOGGER.finest("SQL-Verbindung aufgebaut!");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Fehler beim Aufbauen der SQL Verbindung");
            e.printStackTrace();
        }
    }

    public void checkOnLoad(){
        try {
            if(connection == null || !check()){
                reconnect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean check() throws SQLException {
        return getConnection().prepareStatement("SELECT 1 + 1").executeQuery().next();
    }

    public boolean exists(String module, String identifierAttribute, String identifier) {
        try {
            return getConnection().prepareStatement("SELECT " + identifierAttribute + " FROM  " + module + " WHERE " + identifierAttribute + "='" + identifier + "'").executeQuery().next();
        } catch (SQLException e) {
            LOGGER.severe("Fehler! ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String module, String identifierAttribute, String identifier)  {

        if(!exists(module, identifierAttribute,identifier)){
            return true;
        }

        try {
            getConnection().prepareStatement("DELETE FROM "+ module + " WHERE " + identifierAttribute + "='" + identifier + "'").executeUpdate();
            LOGGER.finest("Entry [" + identifier + "] erfolgreich gelöscht.");
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Fehler! ");
            e.printStackTrace();
            return false;
        }
    }

}
