package com.picon.utils.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class LanguageInfo implements Serializable {

    private final String code;
    private final String iso;
    private final String name;

    public LanguageInfo() {
        this.code = null;
        this.iso = null;
        this.name = null;
    }

    public LanguageInfo(@NonNull String code, @NonNull String iso, @NonNull String name) {
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
