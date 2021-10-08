package app.matthewsgalaxy.ataglance.Interfaces;

import android.content.Context;
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


    // Other Functions
    public static void AddElementToFavesArray(String Title, String Descr, String URL, String ImgURL){return;}
    public static void DeleteElementFromFavesArray(int element){return;}
    public static void FillFromFile(Context C){return;}
    public static void FillFromArrays(ArrayList<String> Titles, ArrayList<String> Descr,
                                      ArrayList<String> URL, ArrayList<String> ImgURL){return;}
    public static ArrayList<ArrayList<String>> ReadJSONWithStarred(Context C){return null;}
    public static void WriteJSONWithStarred(Context C,ArrayList<String> TitlesAL, ArrayList<String> DescAL, ArrayList<String> URLAL, ArrayList<String> IMGURLAL){return;}

    // File Operations
    public static boolean deleteFile(Context context, String filename){return false;}
    public static boolean fileExists(Context context, String filename){return false;}
    public static String readFromFile(Context context, String FileName){return null;}
    public static void writeToFile(Context context, String FileName, String data){return;}


    public static String ProcessTimeStamp(String timeStamp){return null;}
    public static String ReturnNewsResponseJSON(String Choice){return null;}
    public static String ReturnWeatherResponseJSON(){return null;}
    public static String ReturnForecastResponseJSON(){return null;}




}
