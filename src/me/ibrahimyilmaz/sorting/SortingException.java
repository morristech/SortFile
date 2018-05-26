package me.ibrahimyilmaz.sorting;

public class SortingException extends Exception {

    private final short errorCode;

    SortingException(short errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }


    public static SortingException newException(short errorCode) {
        return new SortingException(errorCode, ErrorMessage.get(errorCode));
    }

    public static SortingException newUnknownException(String message) {
        return new SortingException(ErrorCode.UNKNOWN, message);
    }
}
