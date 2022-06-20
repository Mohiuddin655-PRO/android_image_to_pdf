package com.picon.utils.formats;

public class KeyFormat {
    public static final String ALLOWED_CAPITAL_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public static final String ALLOWED_SMALL_CHARACTERS = "qwertyuiopasdfghjklzxcvbn";
    public static final String ALLOWED_SPECIAL_CHARACTERS = "!@%^&*()_+~`-={}|';:?.,<>";
    public static final String ALLOWED_NUMBERS = "0123456789";
    public static final String ALLOWED_CHARACTERS = ALLOWED_CAPITAL_CHARACTERS + ALLOWED_SMALL_CHARACTERS;
    public static final String ALLOWED_CHARACTERS_WITH_NUMBERS = ALLOWED_CHARACTERS + ALLOWED_NUMBERS;
    public static final String ALLOWED_CHARACTERS_WITH_SPECIALS = ALLOWED_CHARACTERS + ALLOWED_SPECIAL_CHARACTERS;
    public static final String ALLOWED_CAPITAL_CHARACTERS_WITH_NUMBERS = ALLOWED_CAPITAL_CHARACTERS + ALLOWED_NUMBERS;
    public static final String ALLOWED_SMALL_CHARACTERS_WITH_NUMBERS = ALLOWED_SMALL_CHARACTERS + ALLOWED_NUMBERS;
    public static final String ALLOWED_ALL_FORMAT = ALLOWED_CAPITAL_CHARACTERS + ALLOWED_SMALL_CHARACTERS + ALLOWED_NUMBERS + ALLOWED_SPECIAL_CHARACTERS;

    public static final String FORMAT_DATE = "dd MMMM, yyyy";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_IMAGE_NAMING_DATE = "yyMMddhhmmss";
    public static final String FORMAT_REFERENCE_DATE = "yyyy-MM-dd";
}
