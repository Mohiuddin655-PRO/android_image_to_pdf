package com.picon.utils.constains;

import androidx.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@Documented
@IntDef({
        ReplacerType.NONE,
        ReplacerType.INSERT,
        ReplacerType.DELETE,
        ReplacerType.RELOAD,
        ReplacerType.UPDATE
})
public @interface ReplacerType {
    int NONE = 15000;
    int DELETE = 15001;
    int INSERT = 15002;
    int RELOAD = 15003;
    int UPDATE = 15004;
}