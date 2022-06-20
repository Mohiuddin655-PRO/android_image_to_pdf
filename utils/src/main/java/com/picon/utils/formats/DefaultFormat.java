package com.picon.utils.formats;

public class DefaultFormat {

    public static final String DAY_FULL_NAME = "EEEE";
    public static final String DAY_SHORT_NAME = "EE";
    public static final String DAY = "dd";
    public static final String MONTH_FULL_NAME = "MMMM";
    public static final String MONTH_SHORT_NAME = "MMM";
    public static final String MONTH = "MM";
    public static final String FULL_YEAR = "yyyy";
    public static final String SHORT_YEAR = "yy";
    public static final String HOUR = "hh";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String TIME_ZONE = "TZD";

    public static final String TIME_MS = String.format("%s:%s", MINUTE, SECOND);
    public static final String TIME_HM = String.format("%s:%s", HOUR, MINUTE);
    public static final String TIME_HMa = String.format("%s:%s a", HOUR, MINUTE);
    public static final String TIME_HMSa = String.format("%s:%s:%s a", HOUR, MINUTE, SECOND);
    public static final String TIME_HMS_ZONE = String.format("%s:%s:%s %s", HOUR, MINUTE, SECOND, TIME_ZONE);

    public static final String DATE_YMD = String.format("%s-%s-%s", FULL_YEAR, MONTH, DAY);
    public static final String DATE_DMY = String.format("%s-%s-%s", DAY, MONTH, FULL_YEAR);
    public static final String DATE_DM_Y = String.format("%s %s, %s", DAY, MONTH_FULL_NAME, FULL_YEAR);
    public static final String DATE_MD_Y = String.format("%s %s, %s", MONTH_FULL_NAME, DAY, FULL_YEAR);
    public static final String DATE_E_DM_Y = String.format("%s, %s %s, %s", DAY_FULL_NAME, DAY, MONTH_FULL_NAME, FULL_YEAR);
    public static final String DATE_E_MD_Y = String.format("%s, %s %s, %s", DAY_FULL_NAME, MONTH_FULL_NAME, DAY, FULL_YEAR);

}
