package com.shendrikov.alex.mynotes.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 05.03.2017.
 */

public class DateUtil {

    private static final String datePattern = "HH:mm:ss \ndd.MM.yy";

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.getDefault());
        return sdf.format(new Date());
    }
}
