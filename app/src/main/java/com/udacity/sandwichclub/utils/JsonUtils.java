package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// utility functions to handle JSON data from strings.xml
public final class JsonUtils {

    // parse JSON from strings.xml
    public static Sandwich parseSandwichJson(String json) {

        try{
            // initiate object
            JSONObject sandwichDetails = new JSONObject(json);

            // get name, mainName, alsoKnownAs array
            JSONObject name = sandwichDetails.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();

            // loop thru aka array and add items to array
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            // get placeOfOrigin, description, image, ingredients array
            String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");
            String description = sandwichDetails.getString("description");
            String image = sandwichDetails.getString("image");
            JSONArray ingredientsArray = sandwichDetails.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();

            // loop thru ingredients array and add items to array
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }

            // return a new sandwich
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        // catch exception
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    } // end of public static Sandwich parseSandwichJson(String json)

} // end of public final class JsonUtils
