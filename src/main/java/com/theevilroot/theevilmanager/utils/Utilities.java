package com.theevilroot.theevilmanager.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.theevilroot.theevilmanager.AppConfig;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Utilities {

    public static final String BACKEND_URL = "/MySQL/";

    public static Thread runAsync(Runnable runnable) {
        Thread thr = new Thread(runnable);
        thr.start();
        return thr;
    }

    public static String getBackendURL(String method) {
        return "http://" + AppConfig.INSTANCE.host + BACKEND_URL + method + ".php";
    }

    public static AlertDialog.Builder showProfile(Activity activity, JsonObject user){
        return null;
    }

    public static <T> List<T> jsonArrayToList(JsonArray array, Function<JsonElement, T> func) {
        List<T> ret = new ArrayList<>();
        for(JsonElement e : array) {
            ret.add(func.apply(e));
        }
        return ret;
    }

    public static boolean isValidURL(String url) {
        try{
            new URL(url);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
