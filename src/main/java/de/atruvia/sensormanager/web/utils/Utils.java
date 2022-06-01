package de.atruvia.sensormanager.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String convertLongToDate(long millis){

        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(date);

    }

    public static String convertLongToTime(long millis){

        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        return simpleDateFormat.format(date);

    }

}
