package com.pawkorchagin.translate.color;

/**
 * MessagePainter
 */
public class MessagePainter {
    public static String purple(String msg) {
        return "\u001B[35m" + msg + "\u001B[35m";
    }
}