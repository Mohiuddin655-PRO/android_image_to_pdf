package com.picon.utils.defaults;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.picon.utils.models.CountryInfo;
import com.picon.utils.models.LanguageInfo;
import com.picon.utils.validators.Validator;

import java.util.ArrayList;
import java.util.Locale;

public class DefaultData {

    public static class CountryData {

        public static final String DEFAULT_LANGUAGE_CODE = "en";

        @NonNull
        public static ArrayList<CountryInfo> getIsoCountries() {
            return getIsoCountries(DEFAULT_LANGUAGE_CODE);
        }

        @NonNull
        public static ArrayList<CountryInfo> getIsoCountries(@NonNull String language_code) {
            final ArrayList<CountryInfo> list = new ArrayList<>();
            for (String country : Locale.getISOCountries()) {
                try {
                    Locale locale = new Locale(language_code, country);
                    String name = locale.getDisplayCountry();
                    String code = locale.getCountry();
                    String iso = locale.getISO3Country();
                    CountryInfo info = new CountryInfo(code, iso, name);
                    list.add(info);
                } catch (Exception e) {
                    Log.e(CountryData.class.getSimpleName(), "getIsoCountries: " + e.getMessage(), e);
                }
            }
            return list;
        }

        public static CountryInfo getCountryInfo(@NonNull String code) {
            return getCountryInfo(code, DEFAULT_LANGUAGE_CODE);
        }

        @Nullable
        public static CountryInfo getCountryInfo(@NonNull String code, @NonNull String language_code) {
            for (CountryInfo info : getIsoCountries(language_code)) {
                if (Validator.isMatched(code.toLowerCase(), info.getCode().toLowerCase())) {
                    return info;
                }
            }
            return null;
        }

        public static String getName(@NonNull String code) {
            return getName(code, DEFAULT_LANGUAGE_CODE);
        }

        @Nullable
        public static String getName(@NonNull String code, @NonNull String language_code) {
            CountryInfo info = getCountryInfo(code, language_code);
            if (info != null) {
                return info.getName();
            } else {
                return null;
            }
        }

        public static String getIso(@NonNull String code) {
            return getIso(code, DEFAULT_LANGUAGE_CODE);
        }

        @Nullable
        public static String getIso(@NonNull String code, @NonNull String language_code) {
            CountryInfo info = getCountryInfo(code, language_code);
            if (info != null) {
                return info.getIso();
            } else {
                return null;
            }
        }

    }

    public static class LanguageData {

        @NonNull
        public static ArrayList<LanguageInfo> getIsoLanguages() {
            final ArrayList<LanguageInfo> list = new ArrayList<>();
            for (String language : Locale.getISOLanguages()) {
                try {
                    Locale locale = new Locale(language);
                    String name = locale.getDisplayLanguage();
                    String code = locale.getLanguage();
                    String iso = locale.getISO3Language();
                    LanguageInfo info = new LanguageInfo(code, iso, name);
                    list.add(info);
                } catch (Exception e) {
                    Log.e(LanguageData.class.getSimpleName(), "getIsoLanguages: " + e.getMessage(), e);
                }
            }
            return list;
        }

        @Nullable
        public static LanguageInfo getLanguageInfo(@NonNull String code) {
            for (LanguageInfo info : getIsoLanguages()) {
                if (Validator.isMatched(code.toLowerCase(), info.getCode().toLowerCase())) {
                    return info;
                }
            }
            return null;
        }

        @Nullable
        public static String getName(@NonNull String code) {
            LanguageInfo info = getLanguageInfo(code);
            if (info != null) {
                return info.getName();
            } else {
                return null;
            }
        }

        @Nullable
        public static String getIso(@NonNull String code) {
            LanguageInfo info = getLanguageInfo(code);
            if (info != null) {
                return info.getIso();
            } else {
                return null;
            }
        }

    }

}