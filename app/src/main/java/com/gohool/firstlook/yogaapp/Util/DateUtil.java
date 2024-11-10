package com.gohool.firstlook.yogaapp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static boolean isDateMatchingDayOfWeek(String dateString, String dayOfWeekString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
        LocalDate date = LocalDate.parse(dateString, formatter);

        DayOfWeek actualDayOfWeek = date.getDayOfWeek();
        DayOfWeek expectedDayOfWeek = DayOfWeek.valueOf(dayOfWeekString.toUpperCase());

        return actualDayOfWeek == expectedDayOfWeek;
    }


}
