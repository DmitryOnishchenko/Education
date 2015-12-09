package com.donishchenko.instaphoto.worker;

import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.model.Target;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

public class DownloadCallable implements Callable<String> {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private DownloadTask task;

    public DownloadCallable(DownloadTask task) {
        this.task = task;
    }

    @Override
    public String call() throws Exception {
        try {
            String urlString = task.getUrl();
            BufferedImage image = ImageIO.read(new URL(urlString));

            String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
            String formatName = urlString.substring(urlString.lastIndexOf('.') + 1);

            if (image != null) {
                Target target = task.getTarget();
                String destPath = target.getDefaultDirectory() + "/" + target.getDirectory() + "/" + fileName;
                File destFile = new File(destPath);
                ImageIO.write(image, formatName, destFile);

                return fileName;
            }
        } catch (IOException e) {
            printer.time().printError(task.getUrl() + " : " + e.getMessage()).br();
            e.printStackTrace();
        }

        return "Error";
    }
}
