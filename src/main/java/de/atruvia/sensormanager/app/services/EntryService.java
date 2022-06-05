package de.atruvia.sensormanager.app.services;

import de.atruvia.sensormanager.app.entities.ValueEntity;
import de.atruvia.sensormanager.app.services.sql.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EntryService {

    private final DatabaseService databaseService;

    public EntryService(DatabaseService databaseService) {

        this.databaseService = databaseService;

        try {
            this.databaseService.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS module_values (timestamp long, temperature float, " +
                    "pressure float, altitude float)").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ValueEntity entity) {
        databaseService.delete("module_values", "timestamp", entity.getTimestamp() + "");
    }

    public void save(ValueEntity entity) {
        delete(entity);

        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(
                    "INSERT INTO module_values (timestamp, temperature, pressure, altitude) VALUES (?,?,?,?)"
            );

            preparedStatement.setLong(1, entity.getTimestamp());
            preparedStatement.setFloat(2, entity.getTemperature());
            preparedStatement.setFloat(3, entity.getPressure());
            preparedStatement.setFloat(4, entity.getAltitude());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ValueEntity> getEntities(){

        ArrayList<ValueEntity> valueEntities = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(

                    "SELECT * FROM module_values LIMIT 50"

            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){



                long timestamp = resultSet.getLong("timestamp");
                float temperature = resultSet.getFloat("temperature");
                float pressure = resultSet.getFloat("pressure");
                float altitude = resultSet.getFloat("altitude");

                valueEntities.add(new ValueEntity(timestamp,temperature,pressure,altitude));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return valueEntities;

    }


}
