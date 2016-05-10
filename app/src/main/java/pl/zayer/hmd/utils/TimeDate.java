package pl.zayer.hmd.utils;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by ZaYeR on 2016-05-08.
 */
public class TimeDate {

    public static String getFormatedDate(Calendar calendar) {
        DateFormat df = DateFormat.getDateInstance();
        df.setTimeZone(calendar.getTimeZone());
        return df.format(calendar.getTime());
    }

}
