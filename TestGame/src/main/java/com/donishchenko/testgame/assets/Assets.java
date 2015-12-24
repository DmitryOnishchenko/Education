package com.donishchenko.testgame.assets;

import com.donishchenko.testgame.Application;
import com.donishchenko.testgame.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Assets {

    private static float SPRITE_SCALE = 0.5f;
    private static final HashMap<String, HashMap<String, Object>> assets = new HashMap<>();

    static {
        loadEffectsAssets();
    }

    public static HashMap<String, Object> getProperties(String key) {
        return assets.get(key);
    }

    private static void loadEffectsAssets() {
         /* Effects */
        HashMap<String, Object> effectsAssets = new HashMap<>();
        /* Blood */
        ArrayList<BufferedImage> bloodSprites = new ArrayList<>();
        try {
            for (int i = 0; i < 8; i++) {
                // read original
                BufferedImage img = ImageIO.read(Application.class.getResource("/blood/blood_" + i + ".png"));
                // resize
//                img = ImageUtils.resizeImage(img, SPRITE_SCALE);
                // convert to compatible volatile image
//                VolatileImage compatibleImage = ImageUtils.convertToVolatileImage(img);

                bloodSprites.add(ImageUtils.toCompatibleImage(img));
            }
        }	catch (IOException ex) { ex.printStackTrace(); }
        effectsAssets.put("bloodSprites", bloodSprites);

        assets.put("effectsAssets", effectsAssets);
    }

}
