package app.matthewsgalaxy.weatherapp0001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    // DESIGN ELEMENTS
    private ImageView MainCurrentCondition;
    private ImageView Hour1Condition, Hour2Condition, Hour3Condition;
    private Chip MainConditionTextJava;
    private Chip TempChip;
    private Chip chipCurrentWeatherPromptJava;
    private Chip TitleAtAGlance;

    private ImageView ImageHour1, ImageHour2, ImageHour3, ImageHour4, ImageHour5, ImageHour6, ImageHour7, ImageHour8,
            ImageHour9;
    private Chip ChipHour1, ChipHour2, ChipHour3, ChipHour4, ChipHour5, ChipHour6, ChipHour7, ChipHour8, ChipHour9;
    private Chip ChipHourUpperText1, ChipHourUpperText2, ChipHourUpperText3, ChipHourUpperText4, ChipHourUpperText5, ChipHourUpperText6, ChipHourUpperText7, ChipHourUpperText8, ChipHourUpperText9;

    // Chips below the Main temperature Chip
    private Chip chipHumidity, chipWind;
    // VARIABLES
    URL MyApiUrl;
    String APIKEY, APIURL_WEATHER, REQUEST_TYPE, APIURL_FORECAST;
    String ResponseJSON;
    boolean DAYLIGHT = true;
    int MY_PERMISSIONS_WANTED = 0;

    // Variables for the connectivity
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///////// VARIABLES IDENTIFICATION
        MainCurrentCondition = findViewById(R.id.MainImageView);
        MainConditionTextJava = findViewById(R.id.MainConditionText);
        TempChip = findViewById(R.id.chipTemperature);
        chipCurrentWeatherPromptJava = findViewById(R.id.chipCurrentWeatherPrompt);
        chipHumidity = findViewById(R.id.chipHumidity);
        chipWind = findViewById(R.id.chipWind);
        double LATITUDE, LONGITUDE;
        LATITUDE = LONGITUDE = 0;

        // Images for hourly forecast                                   // Chips For hourly forecast                            // TextViews for TIME
        ImageHour1 = findViewById(R.id.ImageHour1);                     ChipHour1 = findViewById(R.id.ChipHour1);               ChipHourUpperText1 = findViewById(R.id.ChipHourUpperText1);
        ImageHour2 = findViewById(R.id.ImageHour2);                     ChipHour2 = findViewById(R.id.ChipHour2);               ChipHourUpperText2 = findViewById(R.id.ChipHourUpperText2);
        ImageHour3 = findViewById(R.id.ImageHour3);                     ChipHour3 = findViewById(R.id.ChipHour3);               ChipHourUpperText3 = findViewById(R.id.ChipHourUpperText3);
        ImageHour4 = findViewById(R.id.ImageHour4);                     ChipHour4 = findViewById(R.id.ChipHour4);               ChipHourUpperText4 = findViewById(R.id.ChipHourUpperText4);
        ImageHour5 = findViewById(R.id.ImageHour5);                     ChipHour5 = findViewById(R.id.ChipHour5);               ChipHourUpperText5 = findViewById(R.id.ChipHourUpperText5);
        ImageHour6 = findViewById(R.id.ImageHour6);                     ChipHour6 = findViewById(R.id.ChipHour6);               ChipHourUpperText6 = findViewById(R.id.ChipHourUpperText6);
        ImageHour7 = findViewById(R.id.ImageHour7);                     ChipHour7 = findViewById(R.id.ChipHour7);               ChipHourUpperText7 = findViewById(R.id.ChipHourUpperText7);
        ImageHour8 = findViewById(R.id.ImageHour8);                     ChipHour8 = findViewById(R.id.ChipHour8);               ChipHourUpperText8 = findViewById(R.id.ChipHourUpperText8);
        ImageHour9 = findViewById(R.id.ImageHour9);                     ChipHour9 = findViewById(R.id.ChipHour9);               ChipHourUpperText9 = findViewById(R.id.ChipHourUpperText9);


        APIKEY = "135e028a4a2ff09b2427b0156dd32030"; // API KEY FOR WEATHER REQUESTS
        if(isOnline()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_WANTED);
                System.out.println("Launched Permissions Window");
            }
            if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                // Permission granted previously
                ///////////// Get the current location
                System.out.println("Accepted Permissions Window");
                LocationManager LM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location Loc = LM.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                try {
                    LATITUDE = Loc.getLatitude();
                    LONGITUDE = Loc.getLongitude();
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    System.out.println("ERROR FETCHING LATITUDE AND LONGITUDE DATA");
                    Toast.makeText(MainActivity.this, "Error Fetching Latitude and Longitude Data", Toast.LENGTH_LONG).show();
                }
                //////////// HTTP REQUEST WITH LOCATION
                if (LATITUDE != 0 || LATITUDE!=LONGITUDE) {
                    APIURL_WEATHER = "http://api.openweathermap.org/data/2.5/" + "weather" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
                    APIURL_FORECAST = "http://api.openweathermap.org/data/2.5/" + "forecast" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location

                    // TODO: DEBUG
                    //APIURL_WEATHER = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"; // TEST -> London, GB
                    //APIURL_FORECAST = "https://samples.openweathermap.org/data/2.5/forecast?q=London&appid=b1b15e88fa797225412429c1c50c122a1";

                }else{
                    APIURL_WEATHER = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"; // TEST -> London, GB
                    APIURL_FORECAST = "https://samples.openweathermap.org/data/2.5/forecast?q=London&appid=b1b15e88fa797225412429c1c50c122a1";

                    Toast.makeText(MainActivity.this, "Error fetching latitude and longitude data - possibly last known location inexistent", Toast.LENGTH_LONG);
                    System.out.println("Bad Internal Error");
                }
                // If a dialog should be shown
                // Functions That Make HTTP Requests returning a json with the current weather conditions
                // FOR DEBUGGING PURPOSES /////////////////////////
                System.out.println(APIURL_WEATHER);
                System.out.println(APIURL_FORECAST);

                DoInBackgroundRequest("weather");
                DoInBackgroundRequest("forecast");
            } else {
                // Show Dialog that some services might be limited

                //////////// HTTP REQUEST FOR FIXED LOCATION
                APIURL_WEATHER = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"; // TEST -> London, GB
                APIURL_FORECAST = "https://samples.openweathermap.org/data/2.5/forecast?q=London&appid=b1b15e88fa797225412429c1c50c122a1";


                System.out.println(APIURL_WEATHER);
                System.out.println(APIURL_FORECAST);

                // Functions That Make HTTP Requests returning a json with the current weather conditions
                DoInBackgroundRequest("forecast");
                DoInBackgroundRequest("weather");
            }
        }else{
            Toast.makeText(MainActivity.this, "No network connection", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Please check your connection and try again later", Toast.LENGTH_LONG).show();
        }

        if(LATITUDE == LONGITUDE && LONGITUDE == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Some features might be limited. Please accept the location permission when prompted to")
                            .setPositiveButton("OK",null);
                    builder.show();

                }
            }
        }


    }

    public void StartIntent(Intent INTENT) {
        startActivity(INTENT);
    }

    private void DoInBackgroundRequest(String REQUEST_TYPE_LOC) {
        // Makes HTTP Request That returns a json with the current weather conditions
        OkHttpClient client = new OkHttpClient(); Request request = null;
        if(REQUEST_TYPE_LOC == "weather") {
            request = new Request.Builder().url(APIURL_WEATHER).build();
        }else if(REQUEST_TYPE_LOC == "forecast"){
            request = new Request.Builder().url(APIURL_FORECAST).build();
        }
        Toast.makeText(MainActivity.this, "Performing request to OpenWM for " + REQUEST_TYPE_LOC, Toast.LENGTH_SHORT).show();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("ERROR WHEN CALLING THE URL");
                Toast.makeText(MainActivity.this, "Could not load weather data. Please try again later", Toast.LENGTH_LONG);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String MyResp = response.body().string();
                    System.out.println("RESPONSE:" + MyResp);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // CONTINUES TO RUN INSIDE MAIN THREAD AFTER ASYNC TASK FINISHED
                            // Get response String
                            ResponseJSON = MyResp;

                            // Parse JSON and Get an ArrayList of conditions
                                if (REQUEST_TYPE_LOC.equals("forecast")) {
                                    if(Integer.parseInt(ParseJSONForecast(ResponseJSON, "cod").get(0)) != 200){
                                        // Something bad happened
                                        Toast.makeText(MainActivity.this, "There was a problem in the response. Please try again later", Toast.LENGTH_LONG);
                                    }else {
                                        // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
                                        ArrayList<String> ConditionsInJson = ParseJSONForecast(ResponseJSON, new String("id_icon"));
                                        // CHANGE IMAGES OF HOUR FORECAST ACCORDING TO IDS
                                        ModifyImageToConditions(ImageHour1, true, ConditionsInJson.get(0));
                                        ModifyImageToConditions(ImageHour2, true, ConditionsInJson.get(1));
                                        ModifyImageToConditions(ImageHour3, true, ConditionsInJson.get(2));
                                        ModifyImageToConditions(ImageHour4, true, ConditionsInJson.get(3));
                                        ModifyImageToConditions(ImageHour5, true, ConditionsInJson.get(4));
                                        ModifyImageToConditions(ImageHour6, true, ConditionsInJson.get(5));
                                        ModifyImageToConditions(ImageHour7, true, ConditionsInJson.get(6));
                                        ModifyImageToConditions(ImageHour8, true, ConditionsInJson.get(7));
                                        ModifyImageToConditions(ImageHour9, true, ConditionsInJson.get(8));

                                        // Change Chip Values according to temperatures predicted
                                        ConditionsInJson = ParseJSONForecast(ResponseJSON, new String("temperature"));
                                        ChipHour1.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(0)) - 273.15) + "°C");
                                        ChipHour2.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(1)) - 273.15) + "°C");
                                        ChipHour3.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(2)) - 273.15) + "°C");
                                        ChipHour4.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(3)) - 273.15) + "°C");
                                        ChipHour5.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(4)) - 273.15) + "°C");
                                        ChipHour6.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(5)) - 273.15) + "°C");
                                        ChipHour7.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(6)) - 273.15) + "°C");
                                        ChipHour8.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(7)) - 273.15) + "°C");
                                        ChipHour9.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(8)) - 273.15) + "°C");

                                        // Change TextView Values according to the corresponding time
                                        ChipHourUpperText1.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(0));
                                        ChipHourUpperText2.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(1));
                                        ChipHourUpperText3.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(2));
                                        ChipHourUpperText4.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(3));
                                        ChipHourUpperText5.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(4));
                                        ChipHourUpperText6.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(5));
                                        ChipHourUpperText7.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(6));
                                        ChipHourUpperText8.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(7));
                                        ChipHourUpperText9.setText(ParseJSONForecast(ResponseJSON, new String("time")).get(8));
                                    }
                                } else {
                                    // IF THE REQUEST WAS MADE ONLY FOR THE CURRENT WEATHER///////////////////////////////////////////////////////////////////
                                    if (Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod")) != 200) {
                                        // Something bad happened
                                        Toast.makeText(MainActivity.this, "There was a problem in the response. Please try again later", Toast.LENGTH_LONG);
                                    } else {
                                        // MODIFY THE DESCRIPTION TEXTVIEW
                                        // System.out.println(Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod"))); -> Call returns 200 if OK
                                        String ToModifyCurrentConds = ParseJSONCurrentWeather(ResponseJSON, "description");
                                        String LocationByLatAndLong = ParseJSONCurrentWeather(ResponseJSON, "city_name");
                                        chipCurrentWeatherPromptJava.setText("Current Weather Conditions for " + LocationByLatAndLong);
                                        MainConditionTextJava.setText(ToCamelCaseWord(ToModifyCurrentConds));
                                        String ToModifyCurrentCondsImage = ParseJSONCurrentWeather(ResponseJSON, "conditions_id");
                                        // Set humidity in forecast
                                        String HumidityRightNow = ParseJSONCurrentWeather(ResponseJSON, "humidity");
                                        String AdditionalConditions = "Unknown condition";
                                        double valuehumid = Double.parseDouble(HumidityRightNow);
                                        if (valuehumid < 20) {
                                            AdditionalConditions = "Uncomfortably Dry";
                                        } else if (valuehumid >= 20 && valuehumid <= 60) {
                                            AdditionalConditions = "In Comfort Range";
                                        } else if (valuehumid > 60) {
                                           AdditionalConditions = "Uncomfortably Wet";
                                        }
                                        chipHumidity.setText(HumidityRightNow + "% - " + AdditionalConditions);
                                        chipWind.setText("Speed - "+ ParseJSONCurrentWeather(ResponseJSON, "w_speed") +""+ " Direction - " + ParseJSONCurrentWeather(ResponseJSON, "w_dir"));

                                        // chipWind.setText(ParseJSONCurrentWeather(ResponseJSON, "wind"));
                                        // MODIFY THE MAIN ICON
                                        ModifyImageToConditions(MainCurrentCondition, DAYLIGHT, ToModifyCurrentCondsImage);

                                        // MODIFY THE TEMPERATURE
                                        String ToModifyCurrentTemperature = ParseJSONCurrentWeather(ResponseJSON, "temperature");
                                        TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "°C");
                                    }
                                }
                            }

                    });
                }

            }
        });
    }

    public ArrayList<String> ParseJSONForecast(String ResourceString, String WhatDoYouWantFromMe) {
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
                // Get the contents of "main" -> An object
                JSONObject MyMainObject = (JSONObject) JObjRead.get("main");


                // Get the contents of the temperature inside main
                if(WhatDoYouWantFromMe.equals("temperature")) {
                    String MyTempString = (String) Double.valueOf(MyMainObject.getDouble("temp")).toString();
                    MyConditions.add(MyTempString);
                }

                //////////////////////////////////////// Get the contents of "weather" -> It is a list with an object
                JSONArray WeatherObject = (JSONArray) JObjRead.get("weather");

                // Get The Object Inside The "weather" Array...
                JSONObject ObjInsideWeather = (JSONObject) WeatherObject.get(0);

                // FOR THE CONDITIONS ///////////////////////////////////////////////////////////
                if(WhatDoYouWantFromMe.equals("description")) {
                    String Conditions = (String) ObjInsideWeather.get("description");
                    // System.out.println(Conditions); -> Prints conditions to the console (Debug)
                    Conditions = Conditions.toUpperCase();
                    MyConditions.add(Conditions);
                }

                if(WhatDoYouWantFromMe.equals("id_icon")) {
                    String IconID = (String) Integer.toString(ObjInsideWeather.getInt("id"));
                    MyConditions.add(IconID);
                }
            }
            // Return an array that contains whatever -> String form
            return MyConditions;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ParseJSONCurrentWeather(String ResourceString, String Request_Descr) {
        try {
            JSONObject JsonReader = new JSONObject(ResourceString);

            // Get the response code
            if(Request_Descr.equals("cod")){
                return Integer.toString(JsonReader.getInt("cod"));
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
    public String ToCamelCaseWord(String Word) {
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

    public void ModifyImageToConditions(ImageView ImageViewConds, boolean DAYLIGHT_LOC, String CONDS) {
        // MODIFY THE ICON
        if (CONDS != null) { // If The command is processed correctly
            if (DAYLIGHT_LOC) {
                if (CONDS.equals("800")) {
                    ImageViewConds.setImageResource(R.drawable.clear_day);
                } else if (CONDS.equals("801")) {
                    ImageViewConds.setImageResource(R.drawable.mostly_clear_day);
                } else if (CONDS.equals("802")) {
                    ImageViewConds.setImageResource(R.drawable.partly_cloudy_day);
                } else if (CONDS.equals("803")) {
                    ImageViewConds.setImageResource(R.drawable.mostly_cloudy_day);
                } else if (CONDS.equals("804")) {
                    ImageViewConds.setImageResource(R.drawable.cloudy_night_2);


                } else if (CONDS.equals("701")) { //Mist
                    ImageViewConds.setImageResource(R.drawable.fog_day);
                } else if (CONDS.equals("711")) { //Smoke
                    ImageViewConds.setImageResource(R.drawable.fog_night);
                } else if (CONDS.equals("721")) { //Haze
                    ImageViewConds.setImageResource(R.drawable.fog_day);
                } else if (CONDS.equals("731")) { //Dust
                    ImageViewConds.setImageResource(R.drawable.fog_night);
                } else if (CONDS.equals("741")) { //Fog
                    ImageViewConds.setImageResource(R.drawable.fog_night);
                } else if (CONDS.equals("751")) { //Sand
                    ImageViewConds.setImageResource(R.drawable.fog_night);
                } else if (CONDS.equals("761")) { //Dust
                    ImageViewConds.setImageResource(R.drawable.fog_night);
                } else if (CONDS.equals("762")) { //Ash
                    ImageViewConds.setImageResource(R.drawable.ash);
                } else if (CONDS.equals("771")) { //Squall
                    ImageViewConds.setImageResource(R.drawable.squall);
                } else if (CONDS.equals("500")) { //Light Rain
                    ImageViewConds.setImageResource(R.drawable.light_rain);
                } else if (CONDS.equals("501")) { //Moderate Rain
                    ImageViewConds.setImageResource(R.drawable.rain_night);
                } else if (CONDS.equals("502")) { //Heavy Rain
                    ImageViewConds.setImageResource(R.drawable.rain_night);
                } else if (CONDS.equals("503")) { // Very Heavy Rain
                    ImageViewConds.setImageResource(R.drawable.very_heavy_rain_night);
                } else if (CONDS.equals("504")) { // Extreme Rain
                    ImageViewConds.setImageResource(R.drawable.extreme_rain_night);
                } else if (CONDS.equals("511")) { // Freezing Rain
                    ImageViewConds.setImageResource(R.drawable.freezing_rain);
                } else if (CONDS.equals("520")) { // Light intensity shower Rain
                    ImageViewConds.setImageResource(R.drawable.light_rain_shower_day);
                } else if (CONDS.equals("521")) { // Shower Rain
                    ImageViewConds.setImageResource(R.drawable.scattered_showers_day);
                } else if (CONDS.equals("522")) { // Heavy intensity Shower Rain
                    ImageViewConds.setImageResource(R.drawable.heavy_shower_rain_day);
                } else if (CONDS.equals("531")) { // Ragged Shower Rain
                    ImageViewConds.setImageResource(R.drawable.ragged_shower_rain_day);
                } else if (CONDS.equals("300")) { // Light Intensity Drizzle
                    ImageViewConds.setImageResource(R.drawable.light_drizzle);
                }


            } else {
                //TODO: NIGHTTIME
            }
        } else {
            System.out.println("There has been a problem in processing the json or the data might be incorrect");
        }
    }
    private String ParseTimeString(String Str){
        return Str.substring(Str.indexOf("-")+1, Str.lastIndexOf(":")) + "h";
    }

    // To fetch if app is online
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
    public void WriteStringToFile(String s) throws IOException {
        File file = new File(getFilesDir(),"JSON_LATEST_DATA.json");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(s);
        bufferedWriter.close();
    }
}