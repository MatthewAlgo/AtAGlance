package app.matthewsgalaxy.ataglance.AdditionalClasses;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseBusinessNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseEntertainmentNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseJsonForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseJsonWeather;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponsePoliticsNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseScienceNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseTechNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseWorldNews;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.matthewsgalaxy.ataglance.Interfaces.DifferentFunctionsDeclaration;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.BusinessNewsPart;

public class DifferentFunctions implements DifferentFunctionsDeclaration {

    // Global variables - For Read Later
    public static ArrayList<String> MyTitlesFaves = new ArrayList<>();
    public static ArrayList<String> MyDescriptionsFaves= new ArrayList<>();
    public static ArrayList<String> MyURLFaves= new ArrayList<>();
    public static ArrayList<String> MyImagesURLFaves= new ArrayList<>();

    public DifferentFunctions(){}

    // Parse The JSON Files
    public static ArrayList<String> ParseJSONWorldNews(String RequestJSON, String WhatDoYouWantFromMe){
        // Declare the main JSON Reader
        ArrayList<String> MyArrayList = new ArrayList<>();
        try {
            JSONObject JSReader = new JSONObject(RequestJSON);
            if (WhatDoYouWantFromMe.equals("status")) {
                MyArrayList.add(JSReader.getString("status"));
                return MyArrayList;
            }

            // Getting the list of News Articles (Array of objects)
            JSONArray NewsHeadlines= JSReader.getJSONArray("articles");
            if(WhatDoYouWantFromMe.equals("articles_number")){
                MyArrayList.add(Integer.toString(NewsHeadlines.length()));
                return MyArrayList;
            }
            for(int i=0;i<NewsHeadlines.length();++i){
                JSONObject NEWS_HEADLINE = NewsHeadlines.getJSONObject(i);

                // SOURCE NAMES
                if(WhatDoYouWantFromMe.equals("news_sources")) {
                    JSONObject SOURCE_OBJECT = NEWS_HEADLINE.getJSONObject("source");
                    MyArrayList.add(SOURCE_OBJECT.getString("name"));
                }
                if(WhatDoYouWantFromMe.equals("news_title")){
                    MyArrayList.add(NEWS_HEADLINE.getString("title"));
                }
                if(WhatDoYouWantFromMe.equals("news_descr")){
                    MyArrayList.add(NEWS_HEADLINE.getString("description"));
                }
                if(WhatDoYouWantFromMe.equals("news_url")){
                    MyArrayList.add(NEWS_HEADLINE.getString("url"));
                }
                if(WhatDoYouWantFromMe.equals("news_url_to_img")){
                    MyArrayList.add(NEWS_HEADLINE.getString("urlToImage"));
                }
                if(WhatDoYouWantFromMe.equals("date_published")){
                    MyArrayList.add(NEWS_HEADLINE.getString("publishedAt"));
                }

            }
            return MyArrayList; // Return the data in the end
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> ParseJSONForecast(String ResourceString, String WhatDoYouWantFromMe) {
        ArrayList<String> MyConditions = new ArrayList<>(); // ARRAY TO BE RETURNED

        try {
            JSONObject JsonReader = new JSONObject(ResourceString);

            // Check if the COD returned by the JSON is OKAY
            if(WhatDoYouWantFromMe.equals("cod")){
                MyConditions.add(Integer.toString(JsonReader.getInt("cod")));
            }


            JSONArray listOfHourly = JsonReader.getJSONArray("list");
            // Looping Through the list of objects
            for (int i = 0; i < listOfHourly.length(); ++i) {

                JSONObject JObjRead = listOfHourly.getJSONObject(i);

                // Get the contents of date / time -> a string
                String Date_time_text = JObjRead.getString("dt_txt");
                if(WhatDoYouWantFromMe.equals("time")) {
                    MyConditions.add(ParseTimeString(Date_time_text));
                }

                // If we want the time for each hour
                if(WhatDoYouWantFromMe.equals("time_stamp")){
                    String Stamp = JObjRead.getString("dt");
                    MyConditions.add(Stamp);
                }



                // Get the contents of "main" -> An object
                JSONObject MyMainObject = (JSONObject) JObjRead.get("main");


                // Get the contents of the temperature inside main
                if(WhatDoYouWantFromMe.equals("temperature")) {
                    String MyTempString = (String) Double.valueOf(MyMainObject.getDouble("temp")).toString();
                    MyConditions.add(MyTempString);
                }
                if(WhatDoYouWantFromMe.equals("humidity")) {
                    String MyTempString = (String) Double.valueOf(MyMainObject.getDouble("humidity")).toString();
                    MyConditions.add(MyTempString);
                }
                if (WhatDoYouWantFromMe.equals("tmin")) {
                    MyConditions.add(MyMainObject.getString("temp_min"));
                }
                if (WhatDoYouWantFromMe.equals("tmax")) {
                    MyConditions.add(MyMainObject.getString("temp_max"));
                }

                //////////////////////////////////////// Get the contents of "weather" -> It is a list with an object
                JSONArray WeatherObject = (JSONArray) JObjRead.get("weather");

                // Get The Object Inside The "weather" Array...
                JSONObject ObjInsideWeather = (JSONObject) WeatherObject.get(0);

                // FOR THE CONDITIONS ///////////////////////////////////////////////////////////
                if(WhatDoYouWantFromMe.equals("description")) {
                    String Conditions = (String) ObjInsideWeather.get("description");
                    //Conditions = Conditions.toUpperCase();
                    MyConditions.add(Conditions);
                }

                if(WhatDoYouWantFromMe.equals("id_icon")) {
                    String IconID = (String) Integer.toString(ObjInsideWeather.getInt("id"));
                    MyConditions.add(IconID);
                }

                JSONObject WindObject = (JSONObject) JObjRead.get("wind");
                if(WhatDoYouWantFromMe.equals("w_speed")){
                    String Wspeed = WindObject.getString("speed");
                    MyConditions.add(Wspeed);
                }
            }
            // Return an array that contains whatever -> String form
            return MyConditions;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String ParseJSONCurrentWeather(String ResourceString, String Request_Descr) {
        try {
            JSONObject JsonReader = new JSONObject(ResourceString);

            // Get the response code
            if(Request_Descr.equals("cod")){
                return Integer.toString(JsonReader.getInt("cod"));
            }
            if(Request_Descr.equals("time")){
                return Long.toString(JsonReader.getLong("dt"));
            }

            // Gets the city name if it is specified by the parameter
            if (Request_Descr.equals("city_name")) {
                String CName = JsonReader.getString("name");
                return CName;
            }

            // Gets the visibility
            if (Request_Descr.equals("visibility")) {
                int Vis = JsonReader.getInt("visibility");
                return Integer.toString(Vis);
            }

            // Gets the sunrise and sunset
            if(Request_Descr == "sunrise"){
                JSONObject SysObject = (JSONObject) JsonReader.get("sys");
                long sunrise = SysObject.getLong("sunrise");
                return Long.toString(sunrise);
            }
            if(Request_Descr == "sunset"){
                JSONObject SysObject = (JSONObject) JsonReader.get("sys");
                long sunset = SysObject.getLong("sunset");
                return Long.toString(sunset);
            }

            if(Request_Descr == "country"){
                JSONObject SysObject = (JSONObject) JsonReader.get("sys");
                String PrefixLoc = SysObject.getString("country");
                return PrefixLoc;
            }


            // Gets the wind speed and wind direction
            if(Request_Descr == "w_speed"){
                JSONObject WindObject = (JSONObject) JsonReader.get("wind");
                double Wspeed = WindObject.getDouble("speed");
                return Double.toString(Wspeed);
            }
            if(Request_Descr == "w_dir"){
                JSONObject WindObject = (JSONObject) JsonReader.get("wind");
                double Wdir = WindObject.getDouble("deg");
                return Double.toString(Wdir);
            }

            // Gets the current cloud coverage
            if (Request_Descr.equals("cloud_cov")) {
                JSONObject MyClouds = (JSONObject) JsonReader.get("clouds");
                String cov = MyClouds.getString("all");
                return cov;
            }

            // Get The Object Inside The "weather" Array...
            JSONArray WeatherObject = (JSONArray) JsonReader.get("weather");
            JSONObject ObjInsideWeather = (JSONObject) WeatherObject.get(0);
            if (Request_Descr == "description") {
                String Conditions = (String) ObjInsideWeather.get("description");
                return ToCamelCaseWord(Conditions);
            } else if (Request_Descr == "main_text") {
                String Conditions = (String) ObjInsideWeather.get("main");
                return Conditions;
            } else if (Request_Descr == "conditions_id") {
                int Conditions = (int) ObjInsideWeather.getInt("id");
                return Integer.toString(Conditions);
            }

            // Get The object inside the main object
            JSONObject MainObject = (JSONObject) JsonReader.get("main");
            double Temperature = MainObject.getDouble("temp");
            if (Request_Descr == "temperature") {
                return Double.valueOf(Temperature).toString();
            }
            double Pressure = MainObject.getDouble("pressure");
            if (Request_Descr == "pressure") {
                return Double.valueOf(Pressure).toString();
            }
            double Humidity = MainObject.getDouble("humidity");
            if (Request_Descr == "humidity") {
                return Double.valueOf(Humidity).toString();
            }
            double tmin = MainObject.getDouble("temp_min");
            if (Request_Descr == "tmin") {
                return Double.valueOf(tmin).toString();
            }
            double tmax = MainObject.getDouble("temp_max");
            if (Request_Descr == "tmax") {
                return Double.valueOf(tmax).toString();
            }

            // At the end
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("ERROR WHILE READING WEATHER DATA");
            return null;
        }
    }

    // Diferrent Formatting functions
    public static String ToCamelCaseWord(String Word) {
        String ToBeBuilt = new String();
        for (int i = 0; i < Word.length(); ++i) {
            if (i == 0) {
                ToBeBuilt = new StringBuilder().append(ToBeBuilt).append(Character.toUpperCase(Word.charAt(i))).toString();
            } else {
                if (Word.charAt(i - 1) == ' ') {
                    ToBeBuilt = new StringBuilder().append(ToBeBuilt).append(Character.toUpperCase(Word.charAt(i))).toString();
                } else {
                    ToBeBuilt = new StringBuilder().append(ToBeBuilt).append(Character.toLowerCase(Word.charAt(i))).toString();
                }
            }
        }
        return ToBeBuilt;
    }

    public static void ModifyImageToConditions(ImageView ImageViewConds, boolean IsDaylight, String CONDS) {
        // MODIFY THE ICON
        if (CONDS != null) { // If The command is processed correctly
            SetImagesForImageViewIcon.FunctionThatDoesThat(ImageViewConds,CONDS, IsDaylight);
        } else {
            System.out.println("There has been a problem in processing the json or the data might be incorrect");
        }
    }
    public static String ParseTimeString(String Str){
        return Str.substring(Str.indexOf("-")+1, Str.lastIndexOf(":")) + "h";
    }

    // Other Functions

    public static Pair<Integer, Integer> GetHourAndMinutesFromTimeStamp(String UnixTimeStamp, Context context){
        int timeStampValue = 0;
        try {
            timeStampValue = Integer.parseInt(UnixTimeStamp);
        }catch (Exception e){
            Log.d(TAG, "GetHourAndMinutesFromTimeStamp: " + e.getMessage());
        }

        if(timeStampValue != 0) {
            return new Pair<Integer, Integer>(timeStampValue / 60 % 60, timeStampValue / 3600 % 24);
        }else{
            return new Pair<Integer, Integer>(0,0);
        }
    }
    public static boolean isDaylightFunction(Pair<Integer, Integer> CurrentHour, Pair<Integer, Integer> SunriseHour,Pair<Integer, Integer> SunsetHour){
        // Temporary Implementation
        if(CurrentHour.second > 6){
            if(CurrentHour.second < 18){
                return true;
            }
            else if(CurrentHour.second == 18){
            // We are right in the sunset hour -> We check for minutes
            if(CurrentHour.first < 30){
                // We are okay with the minutes -> it is still daylight
                return true;
            }else{
                // The sun already set
                return false;
            }
        }else{
            // The sunset came in terms of hours
            return false;

        }
    }else if(CurrentHour.second == 6){
        // The hour coincides with the sunrise hour
        if(CurrentHour.first >= 30){
            // We are okay in terms of minutes
            return true;
        }else if(CurrentHour.first < 30){
            // We are not ok
            return false;
        }
    }else{
        // The sun did not rise yet in terms of hours
        return false;
    }
        return true;

    }
    public static boolean IsDaylightFunctionSmart(Pair<Integer, Integer> CurrentHour, Pair<Integer, Integer> SunriseHour,Pair<Integer, Integer> SunsetHour){
        // First Check For Hours
        if(CurrentHour.second > SunriseHour.second){
            // We are more than sunrise in terms of hours
            if(CurrentHour.second < SunsetHour.second){
                // We are just right in terms of hours
                return true;
            }else if(CurrentHour.second == SunsetHour.second){
                // We are right in the sunset hour -> We check for minutes
                if(CurrentHour.first <= SunsetHour.first){
                    // We are okay with the minutes -> it is still daylight
                    return true;
                }else{
                    // The sun already set
                    return false;
                }
            }else{
                // The sunset came in terms of hours
                return false;

            }
        }else if(CurrentHour.second == SunriseHour.second){
            // The hour coincides with the sunrise hour
            if(CurrentHour.first >= SunriseHour.first){
                // We are okay in terms of minutes
                return true;
            }else if(CurrentHour.first < SunriseHour.first){
                // We are not ok
                return false;
            }
        }else{
            // The sun did not rise yet in terms of hours
            return false;
        }

        return true;

    }
    public static String ReturnForecastResponseJSON(){
        if(ResponseJsonForecast.length() > 0){
            return ResponseJsonForecast;
        }else{
            return null;
            // Error or empty string
        }

    }
    public static String ReturnWeatherResponseJSON(){
        if(ResponseJsonWeather.length() > 0){
            return ResponseJsonWeather;
        }else{
            return null;
            // Error or empty string
        }

    }
    public static String ReturnNewsResponseJSON(String Choice) {
        switch (Choice){
            case "science":
                return ResponseScienceNews;
            case "world":
                return ResponseWorldNews;
            case "business":
                return ResponseBusinessNews;
            case "tech":
                return ResponseTechNews;
            case "entertainment":
                return ResponseEntertainmentNews;
            case "politics":
                return ResponsePoliticsNews;
        }
        return null;
    }
    public static String ProcessTimeStamp(String timeStamp){
        Date Date = new Date(Long.parseLong(timeStamp) * 1000L);
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(Date);

        return Cal.getTime().toString();
    }

    // Functions to read / write to files
    public static void writeToFile(Context context, String FileName, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context, String FileName) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(FileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("FileNotFound", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("I/OException", "Can't read file: " + e.toString());
        }

        return ret;
    }
    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }
    public static boolean deleteFile(Context context, String filename){
        File dir = context.getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();
        return deleted;
    }

    // For STARRED Function

    public static void WriteJSONWithStarred(Context C,ArrayList<String> TitlesAL, ArrayList<String> DescAL, ArrayList<String> URLAL,
                                              ArrayList<String> IMGURLAL){
        JSONArray JsonArray = new JSONArray();
        for(int i=0;i<TitlesAL.size();++i) {
            JSONObject JsonObject = new JSONObject();

            try {
                JsonObject.put("Title", TitlesAL.get(i));
                JsonObject.put("Description", DescAL.get(i));
                JsonObject.put("URL", URLAL.get(i));
                JsonObject.put("IMGURL", IMGURLAL.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonArray.put(JsonObject);
        }

        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("JSONArray", JsonArray);
            String MainObjectString = mainObj.toString();
            writeToFile(C,"JSON_SAVED_ITEMS_CACHE.json",MainObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<ArrayList<String>> ReadJSONWithStarred(Context C){
        String StringReadFromFile = readFromFile(C, "JSON_SAVED_ITEMS_CACHE.json");

        ArrayList<String> TALIST = new ArrayList<>(), DESCRALIST=new ArrayList<>(), URLALIST=new ArrayList<>(), IMGURLALIST=new ArrayList<>();
        ArrayList<ArrayList<String>> TOBERETURNED =new ArrayList<ArrayList<String>>();
        JSONObject JsonReader = null;
        try {
            JsonReader = new JSONObject(StringReadFromFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray MyMainArray = JsonReader.getJSONArray("JSONArray");
            for(int i=0; i<MyMainArray.length();++i){
                JSONObject MyObjAtIndex = (JSONObject) MyMainArray.get(i);

                TALIST.add(MyObjAtIndex.getString("Title"));
                DESCRALIST.add(MyObjAtIndex.getString("Description"));
                URLALIST.add(MyObjAtIndex.getString("URL"));
                IMGURLALIST.add(MyObjAtIndex.getString("IMGURL"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TOBERETURNED.add(TALIST);
        TOBERETURNED.add(DESCRALIST);
        TOBERETURNED.add(URLALIST);
        TOBERETURNED.add(IMGURLALIST);

        return TOBERETURNED;

    }
    public static void FillFromArrays(ArrayList<String> Titles, ArrayList<String> Descr,
                                      ArrayList<String> URL, ArrayList<String> ImgURL){
        MyTitlesFaves = Titles;
        MyDescriptionsFaves = Descr;
        MyURLFaves = URL;
        MyImagesURLFaves = ImgURL;
    }
    public static void FillFromFile(Context C){
        ArrayList<ArrayList<String>> ReturnedFromFunction = ReadJSONWithStarred(C);
        MyTitlesFaves = ReturnedFromFunction.get(0);
        MyDescriptionsFaves = ReturnedFromFunction.get(1);
        MyURLFaves = ReturnedFromFunction.get(2);
        MyImagesURLFaves = ReturnedFromFunction.get(3);
    }
    public static void DeleteElementFromFavesArray(int element){
        try {
            MyTitlesFaves.remove(element);
            MyDescriptionsFaves.remove(element);
            MyURLFaves.remove(element);
            MyImagesURLFaves.remove(element);
        }catch (Exception e){
            System.out.println("Could not remove an element");
        }
    }
    public static void AddElementToFavesArray(String Title, String Descr, String URL, String ImgURL){
        MyTitlesFaves.add(0, Title);
        MyDescriptionsFaves.add(0, Descr);
        MyURLFaves.add(0, URL);
        MyImagesURLFaves.add(0, ImgURL);
    }

}
