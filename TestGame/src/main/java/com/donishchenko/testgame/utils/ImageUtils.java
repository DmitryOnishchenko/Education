package com.donishchenko.testgame.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {
    private ImageUtils() {}

    public static BufferedImage loadImage(Class<?> clazz, String path) {
        BufferedImage img = null;
        URL url = clazz.getResource(path);

        try {
            img = toCompatibleImage(ImageIO.read(url));
        } catch (IOException ex) { ex.printStackTrace(); }

        return img;
    }

    public static VolatileImage convertToVolatileImage(BufferedImage bimage) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

        VolatileImage vimage = createVolatileImage(bimage.getWidth(), bimage.getHeight(), Transparency.TRANSLUCENT);

        Graphics2D g = null;

        // This uses the same code as from Code Example 5, but replaces the try block.
        try {
            g = vimage.createGraphics();

            // These commands cause the Graphics2D object to clear to (0,0,0,0).
            g.setComposite(AlphaComposite.Src);
            g.setColor(Color.BLACK);
            g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight()); // Clears the image.

            g.drawImage(bimage,null,0,0);
        } finally {
            // It's always best to dispose of your Graphics objects.
            g.dispose();
        }

        return vimage;
    }

    public static VolatileImage createVolatileImage(int width, int height, int transparency) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage image = null;

        image = gc.createCompatibleVolatileImage(width, height, transparency);

        int valid = image.validate(gc);

        if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
            image = createVolatileImage(width, height, transparency);
            //TODO recursive test
            System.out.println("Recursive test");
            return image;
        }

        return image;
    }

    public static void flipHorizontally(VolatileImage[] sprites) {
        for (int i = 0; i < sprites.length; i++) {
            VolatileImage image = sprites[i];
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage img = op.filter(image.getSnapshot(), null);
            sprites[i] = convertToVolatileImage(img);
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, float scale){
        int resizedWidth = (int) (originalImage.getWidth() * scale);
        int resizedHeight = (int) (originalImage.getWidth() * scale);

        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);
        g.dispose();

        return resizedImage;
    }

    public static BufferedImage toCompatibleImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfx_config.getColorModel())) {
            return image;
        }

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = new_image.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }
}
