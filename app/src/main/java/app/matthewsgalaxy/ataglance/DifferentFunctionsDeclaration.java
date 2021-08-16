package app.matthewsgalaxy.ataglance;

import android.widget.ImageView;

import java.util.ArrayList;

public interface DifferentFunctionsDeclaration {

    // Parse the JSON Files
    public static ArrayList<String> ParseJSONWorldNews(String RequestJSON, String WhatDoYouWantFromMe){return null;}
    public static ArrayList<String> ParseJSONForecast(String ResourceString, String WhatDoYouWantFromMe){return null;}
    public static String ParseJSONCurrentWeather(String ResourceString, String Request_Descr){return null;}


    // Different modifier functions
    public static String ParseTimeString(String Str) {
        return null;
    }

    public static void ModifyImageToConditions(ImageView ImageViewConds, boolean IsDaylight, String CONDS) {
        return;
    }

    public static String ToCamelCaseWord(String Word){
        return null;
    }

    // Other Functions

}
