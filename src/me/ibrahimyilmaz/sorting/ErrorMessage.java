package me.ibrahimyilmaz.sorting;

public class ErrorMessage {
    private ErrorMessage() {

    }

    private static final short NO_ERROR = 0x0;
    private static final String FILE_NOT_FOUND = "File Not Found";
    private static final String TEMP_FILE_NOT_CREATED = "Temp File Not Created";
    private static final String UNKNOWN = "Unknown Error";

    public static String get(short errorCode) {
        switch (errorCode) {
            case ErrorCode.FILE_NOT_FOUND:
                return FILE_NOT_FOUND;
            case ErrorCode.TEMP_FILE_NOT_CREATED:
                return TEMP_FILE_NOT_CREATED;
            default:
                return UNKNOWN;
        }
    }
}
