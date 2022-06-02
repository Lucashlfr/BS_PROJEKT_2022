package de.atruvia.sensormanager.app.entities;

import de.atruvia.sensormanager.web.utils.Utils;

public class ValueEntity {

    private long timestamp;
    private float temperature;
    private float pressure;
    private float altitude;

    public ValueEntity(long timestamp, float temperature, float pressure, float altitude) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.altitude = altitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getDate(){
        return Utils.convertLongToDate(timestamp);
    }

    public String getTime(){
        return Utils.convertLongToTime(timestamp);
    }
}
