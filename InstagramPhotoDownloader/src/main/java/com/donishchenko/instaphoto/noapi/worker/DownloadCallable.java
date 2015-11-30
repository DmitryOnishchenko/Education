package com.donishchenko.instaphoto.noapi.worker;

import com.donishchenko.instaphoto.noapi.model.Target;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.logError;

public class DownloadCallable implements Callable<String> {
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
                File destFile = new File(target.getDirectory() + "/" + fileName);
                ImageIO.write(image, formatName, destFile);

                return fileName + " downloaded.";
            }
        } catch (IOException e) {
            logError(e.getMessage());
            e.printStackTrace();
        }

        return "Error";
    }
}
