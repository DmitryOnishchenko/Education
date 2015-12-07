package com.donishchenko.instaphoto.logger;

import com.donishchenko.instaphoto.gui.StyleProps;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JTextArea printer
 */
public class ConsolePrinter {
    private static HTMLDocument doc;
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("H:mm:ss");
    private static ConsolePrinter printer = new ConsolePrinter();

    private ConsolePrinter() {}

    public static ConsolePrinter getInstance() {
        return printer;
    }

    public static void setDoc(StyledDocument doc) {
        ConsolePrinter.doc = (HTMLDocument) doc;

        StyleSheet styleSheet = ((HTMLDocument) doc).getStyleSheet();
        styleSheet.addRule(
                "body { font-size: " + StyleProps.FONT_SIZE + "px;" +
                "font-family: " + StyleProps.FONT_FAMILY + "; " +
                "color: " + StyleProps.FONT_COLOR + " }");

        /* Classes */
        styleSheet.addRule(".date { color: #b44f1e }");
        styleSheet.addRule(".message { color: #1a1918 }");
        styleSheet.addRule(".error { color: #9d0200 }");
    }

    private synchronized void _print(final String message) {
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

    public synchronized ConsolePrinter time() {
        StringBuilder builder = new StringBuilder();
        builder.append("<b class=date>[ ").append(DATE_FORMAT.format(new Date())).append(" ] </b>");

        _print(builder.toString());

        return printer;
    }

    public synchronized ConsolePrinter print(final String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<span class=message>").append(message).append("</span>");

        _print(builder.toString());

        return printer;
    }

    public synchronized ConsolePrinter br() {
        _print("<br>");

        return printer;
    }

    public synchronized ConsolePrinter printError(final String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("<span class=error><b>").append(message).append("</b></span>");

        _print(builder.toString());

        return printer;
    }
}
