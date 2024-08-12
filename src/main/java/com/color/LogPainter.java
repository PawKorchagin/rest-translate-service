package com.color;

/**
 * ColorLogger
 */
public class LogPainter {
    public static String purple(String msg) {
        return "\u001B[35m" + msg + "\u001B[35m";
    }
}