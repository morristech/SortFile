package me.ibrahimyilmaz.sorting;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SortingLogger {
    static Logger DEFAULT_LOGGER = Logger.getAnonymousLogger();

    public static void i(String message) {
        DEFAULT_LOGGER.log(Level.INFO, message);
    }

    public static void e(String message) {
        DEFAULT_LOGGER.log(Level.SEVERE, message);
    }
}
