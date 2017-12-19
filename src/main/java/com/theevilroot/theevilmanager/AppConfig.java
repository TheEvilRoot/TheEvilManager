package com.theevilroot.theevilmanager;

import android.util.JsonReader;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class AppConfig {

    public static final String CONFIG_PATH = "/sdcard/tem.json";

    public static AppConfig INSTANCE;

    public static void init() throws Exception {
        File file = new File(CONFIG_PATH);
        if(!file.exists()) {
            writeConfig(file);
        }
        INSTANCE = readConfig(file);
    }

    public static void writeConfig(File file) throws Exception{
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        try(FileWriter writer = new FileWriter(file)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("host", "52.48.142.75");
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(obj));
        }
     }

     public static AppConfig readConfig(File f) throws Exception {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
            JsonObject obj = new JsonParser().parse(reader).getAsJsonObject();
            if(obj.has("host"))
                return new AppConfig(obj.get("host").getAsString());
            return new AppConfig("52.48.142.75");
        }
     }

     public String host;

    public AppConfig(String host){
        this.host = host;
    }

}
