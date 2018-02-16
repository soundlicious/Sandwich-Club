package com.udacity.sandwichclub.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.udacity.sandwichclub.model.Sandwich;

public class JsonUtils {

    /**
     * Deserialize String json into Sandwich Object, return null in case of Parsing Exception
     *
     * @param json The string containing the object
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        Gson gson = new Gson();
        Sandwich sandwich;
        try {
            sandwich = gson.fromJson(json, Sandwich.class);
        } catch (JsonParseException exception){
            sandwich = null;
        }
        return sandwich;
    }
}
