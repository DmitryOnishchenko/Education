package com.donishchenko.instaphoto.noapi.logger;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JTextArea logger
 */
public class ConsoleLogger {
    private static JTextArea TEXT_AREA = new JTextArea();
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("H:mm:ss ");

    public static JTextArea getTextArea() {
        return TEXT_AREA;
    }

    public static void setTextArea(JTextArea textArea) {
        TEXT_AREA = textArea;
    }

    public static void log(final String message) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TEXT_AREA.append(DATE_FORMAT.format(new Date()));
                TEXT_AREA.append(message);
                TEXT_AREA.append("\n");
                TEXT_AREA.setCaretPosition(TEXT_AREA.getDocument().getLength());
            }
        });
    }
}
