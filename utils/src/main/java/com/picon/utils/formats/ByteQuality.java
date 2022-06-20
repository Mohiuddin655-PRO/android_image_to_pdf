package com.picon.utils.formats;

public enum ByteQuality {

    NONE;

    private static final long MEGA_BYTE = 1024 * 1024;

    public static final long PHOTO_STANDARD_QUALITY = MEGA_BYTE;
    public static final long PHOTO_HIGH_QUALITY = MEGA_BYTE * 2;
    public static final long PHOTO_MEDIUM_QUALITY = MEGA_BYTE / 2;
    public static final long PHOTO_LOW_QUALITY = MEGA_BYTE / 4;
}
