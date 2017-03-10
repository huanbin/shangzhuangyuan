package com.jindou.myapplication.ui.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by zhishi on 2017/3/10.
 */

public class JsonUtil {
    public static JsonObject getJsonObjectFromString(String jsonString){
        StringReader stringReader=new StringReader(jsonString);
        JsonReader jsonReader=new JsonReader(stringReader);
        jsonReader.setLenient(true);
        return new JsonParser().parse(jsonReader).getAsJsonObject();
    }
}
