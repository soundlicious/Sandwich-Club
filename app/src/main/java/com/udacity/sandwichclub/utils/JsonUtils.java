package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOW_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_LINK = "image";
    public static final String INGREDIENTS = "ingredients";
    public static final String NAME = "name";


    /**
     * Deserialize String json into Sandwich Object, return null in case of Parsing Exception
     *
     * @param json The string containing the object
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.getJSONObject(NAME);
            sandwich.setMainName(nameObject.getString(MAIN_NAME));
            sandwich.setDescription(jsonObject.getString(DESCRIPTION));
            sandwich.setIngredients(JsonArrayToList(jsonObject.getJSONArray(INGREDIENTS)));
            sandwich.setAlsoKnownAs(JsonArrayToList(nameObject.getJSONArray(ALSO_KNOW_AS)));
            sandwich.setImage(jsonObject.getString(IMAGE_LINK));
            sandwich.setPlaceOfOrigin(jsonObject.getString(PLACE_OF_ORIGIN));
        } catch (JSONException e) {
            e.printStackTrace();
            sandwich = null;
        }
        return sandwich;
    }

    /**
     * Take a JsonArray And return it as a list of String.
     *
     * @param jsonArray The JsonArray to convert into list
     * @return List of String Object
     * @throws JSONException
     */
    private static List<String> JsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
