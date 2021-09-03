package app.matthewsgalaxy.ataglance.additionalClasses;

import android.util.Pair;
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
    public static Pair<Integer, Integer> GetHourAndMinutesFromTimeStamp(String UnixTimeStamp){return null;}
    public static boolean isDaylightFunction(Pair<Integer, Integer> CurrentHour, Pair<Integer, Integer> SunriseHour,Pair<Integer, Integer> SunsetHour){return false;}
    public static String ReturnForecastResponseJSON(){return null;}
    public static String ReturnWeatherResponseJSON(){return null;}


    // Other Functions

}
