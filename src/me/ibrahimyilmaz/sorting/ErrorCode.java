package me.ibrahimyilmaz.sorting;

class ErrorCode {
    private ErrorCode() {

    }

    public static final short NO_ERROR = 0x0;
    public static final short FILE_NOT_FOUND = 0x1;
    public static final short TEMP_FILE_NOT_CREATED = 0x5;
    public static final short UNKNOWN = 0xFF;
}