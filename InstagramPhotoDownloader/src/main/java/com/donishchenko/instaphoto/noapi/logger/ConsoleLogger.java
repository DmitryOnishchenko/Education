package com.donishchenko.instaphoto.noapi.logger;

import com.donishchenko.instaphoto.noapi.gui.StyleProps;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JTextArea logger
 */
public class ConsoleLogger {
    private static HTMLDocument doc;
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("H:mm:ss");

    public static void setDoc(StyledDocument doc) {
        ConsoleLogger.doc = (HTMLDocument) doc;

        StyleSheet styleSheet = ((HTMLDocument) doc).getStyleSheet();
        styleSheet.addRule(
                "body { font-size: " + StyleProps.FONT_SIZE + "px;" +
                "font-family: " + StyleProps.FONT_FAMILY + "; " +
                "color: " + StyleProps.FONT_COLOR + " }");
        styleSheet.addRule(".date { color: #b44f1e }");
        styleSheet.addRule(".message { color: #1a1918 }");
        styleSheet.addRule(".error { color: red }");
    }

    private static void _log(final String message) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), message);
                } catch (BadLocationException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void log(final String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b class = date>[ ").append(DATE_FORMAT.format(new Date())).append(" ]</b> ");
        builder.append("<span class = message>").append(message).append("</span>").append("<br>");

        _log(builder.toString());
    }

    public static void logError(final String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b class = date>[ ").append(DATE_FORMAT.format(new Date())).append(" ]</b> ");
        builder.append("<span class = error><b>").append(message).append("</b></span>").append("<br>");

        _log(builder.toString());
    }
}
