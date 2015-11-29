package com.donishchenko.instaphoto.noapi.controller;

import com.donishchenko.instaphoto.noapi.model.Target;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class Config {
    private File configFile = new File("config.json");
    private long lastModified;

    /* Config */
    private String urlString;
    private String targetsFileName;
    private String defaultDirectory;
    private List<Target> targets;

    public boolean checkConfig() {
        /* Check config file */
        if (!configFile.exists()) {
            log("Check config.json! It doesn't exist.");
            return false;
        }

        /* Check modification */
        long checkModified = new Date(configFile.lastModified()).getTime();
        if (checkModified > lastModified) {
            lastModified = checkModified;
            updateConfig();
        }

        return true;
    }

    private void updateConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readValue(configFile, JsonNode.class);

            urlString = node.get("main_url").textValue();
            targetsFileName = node.get("targets_file_name").textValue();
            defaultDirectory = node.get("default_directory").textValue();

            updateTargets();
            checkDirectories();

        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void updateTargets() throws NoSuchFieldException, IOException {
        File targetsFile = new File(targetsFileName);
        if (!targetsFile.exists()) {
            log("No such file: " + targetsFileName);
            throw new NoSuchFieldException(targetsFileName);
        }

        ObjectMapper mapper = new ObjectMapper();
        targets = mapper.readValue(targetsFile,
                mapper.getTypeFactory().constructCollectionType(List.class, Target.class));
    }

    private void checkDirectories() {
        for (Target target : targets) {
            String dirString = target.getDirectory();

            if (dirString == null) {
                dirString = defaultDirectory;
            } else {
                dirString = defaultDirectory + "/" + dirString;
            }
            target.setDirectory(defaultDirectory);

            File directory = new File(dirString);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }
}
