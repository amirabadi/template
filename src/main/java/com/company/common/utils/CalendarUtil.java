package com.company.common.utils;

import com.ghasemkiani.util.SimplePersianCalendar;
import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CalendarUtil {

    private static final String timeZone="Asia/Tehran";
    private static final int[] STYLES;
    public static final TimeZone TIME_ZONE;

    static {
        STYLES = new int[]{DateFormat.DEFAULT, DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL};
        TIME_ZONE = TimeZone.getTimeZone(timeZone);
    }

    public static String getDate(Locale locale) {
        return getDate(new Date(), locale);
    }

    public static String getDate(Date date) {
        return getDate(date, null);
    }

    public static Date getDate(String date) {
        return getDate(date, null);
    }

    public static Date getDate(String date, Locale locale) {

        if (date == null)
            return null;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            String[] splitDate = date.split("/");
            SimplePersianCalendar spc = new SimplePersianCalendar();
            spc.setDateFields(Integer.parseInt(splitDate[0]), (Integer.parseInt(splitDate[1]) - 1), Integer.parseInt(splitDate[2]));
            return new Date(spc.getTimeInMillis());
        } else {
            String[] splitDate = date.split("/");
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(Integer.parseInt(splitDate[2]), (Integer.parseInt(splitDate[1]) - 1), Integer.parseInt(splitDate[0]));
            return new Date(gc.getTimeInMillis());
        }
    }

    public static String getDate(Date date, Locale locale) {
        if (date == null)
            return null;

        SimpleDateFormat sdf;
        Calendar calendar;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            calendar = new PersianCalendar(locale);
            calendar.setTime(date);
            sdf = (SimpleDateFormat) calendar.getDateTimeFormat(STYLES[1], STYLES[1], locale);
            return sdf.format(calendar.getTime());
        } else {
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(date);
        }
    }

    public static String getDateWithoutSlash(Date date, Locale locale) {
        if (date == null) date = new Date();

        SimpleDateFormat sdf;
        Calendar calendar;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            calendar = new PersianCalendar(locale);
            calendar.setTime(date);

            sdf = (SimpleDateFormat) calendar.getDateTimeFormat(STYLES[1], STYLES[1], locale);
            sdf.applyPattern("yyyyMMddHHmm");

            return sdf.format(calendar.getTime());
        } else {
            sdf = new SimpleDateFormat("ddMMyyyyHHmm");
            return sdf.format(date);
        }
    }

    public static String convertGregorianToPersian(String date) {
        int year, month, day;
        com.ghasemkiani.util.DateFields t;
        String[] splitDate = date.split("/");
        String value = "";
        try {
            try {
                year = Integer.parseInt(splitDate[2]);
            } catch (NumberFormatException nfe) {
                year = 0;
            }
            try {
                month = Integer.parseInt(splitDate[1]) - 1;
            } catch (NumberFormatException nfe) {
                month = 0;
            }
            try {
                day = Integer.parseInt(splitDate[0]);
            } catch (NumberFormatException nfe) {
                day = 0;
            }

            SimplePersianCalendar c = new SimplePersianCalendar();
            c.set(c.YEAR, year);
            c.set(c.MONTH, month);
            c.set(c.DAY_OF_MONTH, day);
            t = c.getDateFields();

            String shamsiYear = Long.toString(t.getYear());
            String shamsiMonth = Long.toString(t.getMonth() + 1);
            String shamsiDay = Long.toString(t.getDay());
            value = shamsiYear + String.valueOf(File.separatorChar) + shamsiMonth + String.valueOf(File.separatorChar) + shamsiDay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String convertPersianToGregorian(String date) {
        int year, month, day;
        DateFields t;
        String[] splitDate = date.split("/");
        String value = "";
        try {
            try {
                year = Integer.parseInt(splitDate[0]);
            } catch (NumberFormatException nfe) {
                year = 0;
            }
            try {
                month = Integer.parseInt(splitDate[1]) - 1;
            } catch (NumberFormatException nfe) {
                month = 0;
            }
            try {
                day = Integer.parseInt(splitDate[2]);
            } catch (NumberFormatException nfe) {
                day = 0;
            }

            SimplePersianCalendar c = new SimplePersianCalendar();
            c.setDateFields(year, month, day);
            String miladiYear = Long.toString(c.get(c.ERA) == c.AD ? c.get(c.YEAR) : -(c.get(c.YEAR) - 1));
            String miladiMonth = Long.toString(c.get(c.MONTH) + 1);
            String miladiDay = Long.toString(c.get(c.DAY_OF_MONTH));
            value = miladiDay + String.valueOf(File.separatorChar) + miladiMonth + String.valueOf(File.separatorChar) + miladiYear;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getDateWithoutTime(Date date, Locale locale) {
        if (date == null)
            return null;

        SimpleDateFormat sdf;
        Calendar calendar;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            calendar = new PersianCalendar(locale);
            calendar.setTime(date);

            sdf = (SimpleDateFormat) calendar.getDateTimeFormat(STYLES[1], STYLES[1], locale);
            sdf.applyPattern("yyyyMMdd");

            return sdf.format(calendar.getTime());
        } else {
            sdf = new SimpleDateFormat("ddMMyyyy");
            return sdf.format(date);
        }
    }

    public static String getDateWithoutTimeWithSlash(Date date, Locale locale) {
        if (date == null)
            return null;

        SimpleDateFormat sdf;
        Calendar calendar;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            calendar = new PersianCalendar(locale);
            calendar.setTime(date);

            sdf = (SimpleDateFormat) calendar.getDateTimeFormat(STYLES[1], STYLES[1], locale);
            sdf.applyPattern("yyyy/MM/dd");

            return sdf.format(calendar.getTime());
        } else {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        }
    }
    public static String getDateWithTimeWithSlash(Date date, Locale locale) {
        if (date == null)
            return null;

        SimpleDateFormat sdf;
        Calendar calendar;

        if (locale != null && locale.equals(LangUtils.LOCALE_FARSI)) {
            calendar = new PersianCalendar(locale);
            calendar.setTime(date);

            sdf = (SimpleDateFormat) calendar.getDateTimeFormat(STYLES[1], STYLES[1], locale);
            sdf.applyPattern("yyyy/MM/dd HH:mm");

            return sdf.format(calendar.getTime());
        } else {
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(date);
        }
    }
    public static String getDateWithoutTimeWithSlashEnglish(Date date, Locale locale) {
        String str = getDateWithoutTimeWithSlash(date, locale);
        String year = str.substring(0, 4);
        String month = str.substring(5, 7);
        String day = str.substring(8, 10);
        return getEnglishNumber(year)+"/"+getEnglishNumber(month)+"/"+getEnglishNumber(day);
    }

    public static String getDateWithoutTime(Locale locale) {
        return getDateWithoutTime(new Date(), locale);
    }

    private static String getEnglishNumber(String input) {
        StringBuffer number = new StringBuffer();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            switch (c) {
                case '۰':
                    number.append('0');
                    break;
                case '۱':
                    number.append('1');
                    break;
                case '۲':
                    number.append('2');
                    break;
                case '۳':
                    number.append('3');
                    break;
                case '۴':
                    number.append('4');
                    break;
                case '۵':
                    number.append('5');
                    break;
                case '۶':
                    number.append('6');
                    break;
                case '۷':
                    number.append('7');
                    break;
                case '۸':
                    number.append('8');
                    break;
                case '۹':
                    number.append('9');
                    break;
                default:
                    number.append(c);
            }
        }
        return number.toString();
    }

    /**
     * This method check that a persian date is valid or not
     * The format of date should be 1391/04/08
     *
     * @param date
     * @return
     */

    public static boolean isPersianDateValid(String date) {
        try {
            String[] values = new String[3];
            if (date.contains("/")) {
                values = date.split("/");
            } else {
                values[0] = date.substring(0, 4);
                values[1] = date.substring(4, 6);
                values[2] = date.substring(6, 8); // 13662211
            }
            int year = Integer.valueOf(values[0]);
            int month = Integer.valueOf(values[1]);
            int day = Integer.valueOf(values[2]);
            if (year < 1300 && year > 1500) return false;
            if (month > 12) return false;
            if (month <= 6 && !(day <= 31)) return false;
            if (month > 6 && month <= 12 && !(day <= 30)) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String convertDateTwoDigits(String date) {
        String tempDate = "";
        try {
            String[] values = new String[3];
            if (date.contains("/")) {
                values = date.split("/");
            } else {
                values[0] = date.substring(0, 4);
                values[1] = date.substring(4, 6);
                values[2] = date.substring(6, 8); // 13662211
            }
            int year = Integer.valueOf(values[0]);
            int month = Integer.valueOf(values[1]);
            int day = Integer.valueOf(values[2]);
            tempDate = Integer.toString(year);
            if (month < 10) {
                tempDate += "0" + Integer.toString(month);
            } else {
                tempDate += "" + Integer.toString(month);
            }
            if (day < 10) {
                tempDate += "0" + Integer.toString(day);
            } else {
                tempDate += "" + Integer.toString(day);
            }
        } catch (Exception e) {
            return "";
        }
        return tempDate;
    }

    public static String convertDateOneDigits(String date) {
        String tempDate = "";
        try {
            String[] values = new String[3];
            if (date.contains("/")) {
                values = date.split("/");
            } else {
                values[0] = date.substring(0, 4);
                values[1] = date.substring(4, 6);
                values[2] = date.substring(6, 8); // 13662211
            }
            int year = Integer.valueOf(values[0]);
            int month = Integer.valueOf(values[1]);
            int day = Integer.valueOf(values[2]);
            tempDate = Integer.toString(year);
            tempDate += "/" + Integer.toString(month);
            tempDate += "/" + Integer.toString(day);
        } catch (Exception e) {
            return "";
        }
        return tempDate;
    }

}
