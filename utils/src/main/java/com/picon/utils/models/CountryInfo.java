package com.picon.utils.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CountryInfo implements Serializable {

    private final String code;
    private final String iso;
    private final String name;

    public CountryInfo() {
        this.code = null;
        this.iso = null;
        this.name = null;
    }

    public CountryInfo(@NonNull String code, @NonNull String iso, @NonNull String name) {
        this.code = code;
        this.iso = iso;
        this.name = name;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }
}
