package app.matthewsgalaxy.ataglance.ui.AtAGlance;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.databinding.FragmentAtaglanceBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;


public class AtAGlanceFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // DESIGN ELEMENTS
    private ImageView MainCurrentCondition;
    private ImageView Hour1Condition, Hour2Condition, Hour3Condition;
    private Chip MainConditionTextJava;
    private Chip TempChip;
    private Chip chipCurrentWeatherPromptJava;
    private Chip TitleAtAGlance;
    private Chip chipLastUpdated;

    private ImageView ImageHour1, ImageHour2, ImageHour3, ImageHour4, ImageHour5, ImageHour6, ImageHour7, ImageHour8,
            ImageHour9;
    private Chip ChipHour1, ChipHour2, ChipHour3, ChipHour4, ChipHour5, ChipHour6, ChipHour7, ChipHour8, ChipHour9;
    private Chip ChipHourUpperText1, ChipHourUpperText2, ChipHourUpperText3, ChipHourUpperText4, ChipHourUpperText5, ChipHourUpperText6, ChipHourUpperText7, ChipHourUpperText8, ChipHourUpperText9;
    private Chip chipHILO;
    // Chips below the Main temperature Chip
    private Chip chipHumidity, chipWind;

    // For the News Card View
    private TextView NewsTitleChip;
    private TextView NewsDescriptionText;
    private Chip PrevArticleChip, NextArticleChip;
    private ImageView ImageNews;
    private CardView NewsCardView;
    private Chip ChipURLLink;


    public static ArrayList<String> MyTitlesArrayListForWorldNews;
    public static ArrayList<String> MyDescriptionsArrayListForWorldNews;
    public static ArrayList<String> MyURLArrayListForWorldNews;
    public static ArrayList<String> MyIMGURLArrayListForWorldNews;
    private static int CURRENT_ARTICLE = 0;

    public double LATITUDE;
    public double LONGITUDE;
    // Variables for the connectivity
    public static ConnectivityManager connectivityManager;
    public static NetworkInfo wifiInfo, mobileInfo;
    public static boolean connected = false;
    public static String APIKEY, APIURL_WEATHER, REQUEST_TYPE, APIURL_FORECAST, APIKEY_NEWS, NEWSAPI_WORLD, NEWSAPI_SCIENCE;
    public static String ResponseJSON;

    public static boolean DAYLIGHT = true;
    public static Date CurrentDate;
    public static Time CurrentTime;


    // For the science news part
    protected FragmentAtaglanceBinding binding;
    ScienceNewsPart MyScienceNewsPart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAtaglanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /////// Application For the main Layout File
        ///////// VARIABLES IDENTIFICATION
        MainCurrentCondition = binding.MainImageView;
        MainConditionTextJava = binding.MainConditionText;
        TempChip = binding.chipTemperature;
        chipCurrentWeatherPromptJava = binding.chipCurrentWeatherPrompt;
        chipHumidity = binding.chipHumidity;
        chipWind = binding.chipWind;
        double LATITUDE, LONGITUDE;
        LATITUDE = LONGITUDE = 0;


        // Images for hourly forecast                                   // Chips For hourly forecast                            // TextViews for TIME
        ImageHour1 = binding.ImageHour1;                     ChipHour1 = binding.ChipHour1;               ChipHourUpperText1 = binding.ChipHourUpperText1;
        ImageHour2 = binding.ImageHour2;                     ChipHour2 = binding.ChipHour2;               ChipHourUpperText2 = binding.ChipHourUpperText2;
        ImageHour3 = binding.ImageHour3;                     ChipHour3 = binding.ChipHour3;               ChipHourUpperText3 = binding.ChipHourUpperText3;
        ImageHour4 = binding.ImageHour4;                     ChipHour4 = binding.ChipHour4;               ChipHourUpperText4 = binding.ChipHourUpperText4;
        ImageHour5 = binding.ImageHour5;                     ChipHour5 = binding.ChipHour5;               ChipHourUpperText5 = binding.ChipHourUpperText5;
        ImageHour6 = binding.ImageHour6;                     ChipHour6 = binding.ChipHour6;               ChipHourUpperText6 = binding.ChipHourUpperText6;
        ImageHour7 = binding.ImageHour7;                     ChipHour7 = binding.ChipHour7;               ChipHourUpperText7 = binding.ChipHourUpperText7;
        ImageHour8 = binding.ImageHour8;                     ChipHour8 = binding.ChipHour8;               ChipHourUpperText8 = binding.ChipHourUpperText8;
        ImageHour9 = binding.ImageHour9;                     ChipHour9 = binding.ChipHour9;               ChipHourUpperText9 = binding.ChipHourUpperText9;
        chipLastUpdated = binding.chipLastUpdated;
        ChipURLLink = binding.ChipURLLink;
        chipHILO = binding.chipHILO;

        NewsTitleChip = binding.ChipNewsTitle;
        PrevArticleChip = binding.PrevArticleChip;
        NextArticleChip = binding.NextArticleChip;
        NewsDescriptionText = binding.NewsDescriptionText;
        ImageNews = binding.ImageNews;
        NewsCardView = binding.NewsCardView;



        MyScienceNewsPart = new ScienceNewsPart(binding);
        APIKEY = "135e028a4a2ff09b2427b0156dd32030"; // API KEY FOR WEATHER REQUESTS
        APIKEY_NEWS = "82de6527ef904da08c127287e4044c27"; // API KEY FOR WEATHER REQUESTS

        if(isOnline()){
            checkLocationPermission();
        }else{
            Toast.makeText(getContext(), "You Are Offline! Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
        }
        // Set Click Listeners for the Chips
        SetOnClickListenersForWorldNewsChips();
        return root;
    }

    // For the location permission
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(requireContext())
                        .setTitle("Location Permission")
                        .setMessage("Some features might be limited if the location permission is not accepted. Please Accept it")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                requestPermissions(
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            // We have the permission
            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
            LocationListener loc_listener = new LocationListener() {

                public void onLocationChanged(Location l) {
                    LATITUDE = l.getLatitude();
                    LONGITUDE = l.getLongitude();
                }

                public void onProviderEnabled(String p) {}

                public void onProviderDisabled(String p) {}

                public void onStatusChanged(String p, int status, Bundle extras) {}
            };
            locationManager.requestLocationUpdates(GPS_PROVIDER, 400, 1, loc_listener);
            Location Loc = locationManager.getLastKnownLocation(GPS_PROVIDER);
            try {
                LATITUDE = Loc.getLatitude();
                LONGITUDE = Loc.getLongitude();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                System.out.println("Error Fetching Latitude and Longitude Data");
            }

            APIURL_WEATHER = "http://api.openweathermap.org/data/2.5/" + "weather" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
            APIURL_FORECAST = "http://api.openweathermap.org/data/2.5/" + "forecast" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
            NEWSAPI_WORLD = "https://newsapi.org/v2/top-headlines?language=en&country=us&apiKey=" + APIKEY_NEWS;
            NEWSAPI_SCIENCE = "https://newsapi.org/v2/top-headlines?language=en&category=science&apiKey=" + APIKEY_NEWS;

            DoInBackgroundRequest("forecast");
            DoInBackgroundRequest("weather");
            DoInBackgroundRequest("news_world");
            DoInBackgroundRequest("news_science");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
                        LocationListener loc_listener = new LocationListener() {

                            public void onLocationChanged(Location l) {
                                LATITUDE = l.getLatitude();
                                LONGITUDE = l.getLongitude();
                            }

                            public void onProviderEnabled(String p) {}

                            public void onProviderDisabled(String p) {}

                            public void onStatusChanged(String p, int status, Bundle extras) {}
                        };
                        locationManager.requestLocationUpdates(GPS_PROVIDER, 400, 1, loc_listener);
                        Location Loc = locationManager.getLastKnownLocation(GPS_PROVIDER);
                        try {
                            LATITUDE = Loc.getLatitude();
                            LONGITUDE = Loc.getLongitude();
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Error Fetching Latitude and Longitude Data");
                        }

                        APIURL_WEATHER = "http://api.openweathermap.org/data/2.5/" + "weather" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
                        APIURL_FORECAST = "http://api.openweathermap.org/data/2.5/" + "forecast" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
                        NEWSAPI_WORLD = "https://newsapi.org/v2/top-headlines?language=en&country=us&apiKey=" + APIKEY_NEWS;
                        NEWSAPI_SCIENCE = "https://newsapi.org/v2/top-headlines?language=en&category=science&apiKey=" + APIKEY_NEWS;

                        DoInBackgroundRequest("forecast");
                        DoInBackgroundRequest("weather");
                        DoInBackgroundRequest("news_world");
                        DoInBackgroundRequest("news_science");

                    }

                } else {

                    // PERMISSION DENIED
                    Toast.makeText(requireContext(), "Location Permission Not Accepted, Limited Features!",Toast.LENGTH_SHORT).show();
                    APIURL_WEATHER = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"; // TEST -> London, GB
                    APIURL_FORECAST = "https://samples.openweathermap.org/data/2.5/forecast?q=London&appid=b1b15e88fa797225412429c1c50c122a1";
                    NEWSAPI_WORLD = "https://newsapi.org/v2/top-headlines?language=en&country=us&apiKey=" + APIKEY_NEWS;
                    NEWSAPI_SCIENCE = "https://newsapi.org/v2/top-headlines?language=en&category=science&apiKey=" + APIKEY_NEWS;

                    DoInBackgroundRequest("forecast");
                    DoInBackgroundRequest("weather");
                    DoInBackgroundRequest("news_world");

                }
                return;
            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
            LocationListener loc_listener = new LocationListener() {

                public void onLocationChanged(Location l) {}

                public void onProviderEnabled(String p) {}

                public void onProviderDisabled(String p) {}

                public void onStatusChanged(String p, int status, Bundle extras) {}
            };
            locationManager.requestLocationUpdates(GPS_PROVIDER, 400, 1, loc_listener);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
            LocationListener loc_listener = new LocationListener() {

                public void onLocationChanged(Location l) {}

                public void onProviderEnabled(String p) {}

                public void onProviderDisabled(String p) {}

                public void onStatusChanged(String p, int status, Bundle extras) {}
            };
            locationManager.removeUpdates(loc_listener);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //////// FUNCTIONS TO BUILD THE APP ITSELF
    public void StartIntent(Intent INTENT) {
        startActivity(INTENT);
    }

    public void DoInBackgroundRequest(String REQUEST_TYPE_LOC) {
        // Makes HTTP Request That returns a json with the current weather conditions
        OkHttpClient client = new OkHttpClient(); Request request = null;
        if(REQUEST_TYPE_LOC == "weather") {
            request = new Request.Builder().url(APIURL_WEATHER).build();
        }else if(REQUEST_TYPE_LOC == "forecast"){
            request = new Request.Builder().url(APIURL_FORECAST).build();
        }else if(REQUEST_TYPE_LOC == "news_world"){
            request = new Request.Builder().url(NEWSAPI_WORLD).build();
        }else if(REQUEST_TYPE_LOC == "news_science"){
            request = new Request.Builder().url(NEWSAPI_SCIENCE).build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("ERROR WHEN CALLING THE URL");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String MyResp = response.body().string();
                    System.out.println("RESPONSE:" + MyResp);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // CONTINUES TO RUN INSIDE MAIN THREAD AFTER ASYNC TASK FINISHED
                            // Get response String
                            ResponseJSON = MyResp;

                            // Parse JSON and Get an ArrayList of conditions
                            if (REQUEST_TYPE_LOC.equals("forecast")) {

                                if(Integer.parseInt(ParseJSONForecast(ResponseJSON, "cod").get(0)) != 200){
                                    // Something bad happened
                                    Toast.makeText(getContext(), "There was a problem in the response for Forecast. Please try again later", Toast.LENGTH_LONG).show();
                                }else {
                                    // SAVE THE JSON REQUEST LOCALLY
                                    try { WriteStringToFile(MyResp, "FILE_JSON_FORECAST.json"); } catch (IOException e) { e.printStackTrace(); }

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
                            } else if (REQUEST_TYPE_LOC.equals("weather")) {
                                // IF THE REQUEST WAS MADE ONLY FOR THE CURRENT WEATHER///////////////////////////////////////////////////////////////////
                                if (Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod")) != 200) {
                                    // Something bad happened
                                    Toast.makeText(getContext(), "There was a problem in the response for Weather. Please try again later", Toast.LENGTH_LONG);
                                } else {
                                    // Get the current date and time and set the upper chip
                                    CurrentDate = new Date((long)1000*Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                    CurrentTime = new Time((long)1000*Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                    chipLastUpdated.setText("Updated At: "+CurrentDate.toString() +" "+ CurrentTime.toString());

                                    // SAVE THE JSON REQUEST LOCALLY
                                    try { WriteStringToFile(ResponseJSON, "FILE_JSON_WEATHER.json"); } catch (IOException e) { e.printStackTrace(); }

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
                                        AdditionalConditions = "Dry";
                                    } else if (valuehumid >= 20 && valuehumid <= 60) {
                                        AdditionalConditions = "Comfortable";
                                    } else if (valuehumid > 60) {
                                        AdditionalConditions = "Humid";
                                    }
                                    chipHumidity.setText(HumidityRightNow + "% - " + AdditionalConditions);

                                    String WindConditions = null; double WindSpeed = Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "w_speed"))*0.001*3600 ;
                                    if(WindSpeed == 0){
                                        WindConditions = "No Wind";
                                    }
                                    if(WindSpeed < 5 && WindSpeed > 0){
                                        WindConditions = "Light Breeze";
                                    }
                                    if(WindSpeed >= 5 && WindSpeed <20){
                                        WindConditions = "Light Wind";
                                    }
                                    if(WindSpeed >= 20 && WindSpeed <30){
                                        WindConditions = "Moderate Wind";
                                    }
                                    if(WindSpeed >= 30){
                                        WindConditions = "Strong Wind";
                                    }

                                    chipWind.setText("Speed - "+ String.format("%.2f", (Double) WindSpeed) + "km/h" + " - " + WindConditions);

                                    // MODIFY THE MAIN ICON
                                    ModifyImageToConditions(MainCurrentCondition, DAYLIGHT, ToModifyCurrentCondsImage);

                                    // MODIFY THE TEMPERATURE
                                    String ToModifyCurrentTemperature = ParseJSONCurrentWeather(ResponseJSON, "temperature");
                                    TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "°C");

                                    chipHILO.setText("High - "+ String.format("%.1f",Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmax"))-273.15)+"C"+ " | Low - "+String.format("%.1f",Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmin"))-273.15)+"C");
                                }
                            }else if(REQUEST_TYPE_LOC.equals("news_world")){
                                MyTitlesArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON,"news_title");
                                NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(0));

                                MyDescriptionsArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON,"news_descr");
                                NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(0));

                                MyURLArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON,"news_url");

                                // To load the first corresponding image
                                MyIMGURLArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON,"news_url_to_img");
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).resize(700, 500).onlyScaleDown().into(ImageNews); // Set Image

                            }else if(REQUEST_TYPE_LOC.equals("news_science")) {
                                MyScienceNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON);
                            }
                        }

                    });
                }

            }
        });
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

    public static String ParseJSONCurrentWeather(String ResourceString, String Request_Descr) {
        try {
            JSONObject JsonReader = new JSONObject(ResourceString);

            // Get the response code
            if(Request_Descr.equals("cod")){
                return Integer.toString(JsonReader.getInt("cod"));
            }
            if(Request_Descr.equals("time")){
                return Integer.toString(JsonReader.getInt("dt"));
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

    public static void ModifyImageToConditions(ImageView ImageViewConds, boolean DAYLIGHT_LOC, String CONDS) {
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
                } else if (CONDS.equals("301")) { // Drizzle
                    ImageViewConds.setImageResource(R.drawable.drizzle);
                }else if (CONDS.equals("302")) { // Drizzle
                    ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle);
                }else if (CONDS.equals("310")) { // Drizzle Rain
                    ImageViewConds.setImageResource(R.drawable.drizzle_rain);
                }else if (CONDS.equals("312")) { // Drizzle Rain Heavy
                    ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
                }else if (CONDS.equals("313")) { // Drizzle
                    ImageViewConds.setImageResource(R.drawable.drizzle_rain);
                }else if (CONDS.equals("314")) { // Heavy Shower Rain And Drizzle
                    ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
                }else if (CONDS.equals("321")) { // Shower Drizzle
                    ImageViewConds.setImageResource(R.drawable.drizzle);
                }

                else if (CONDS.equals("600")) { // Light Snow
                    ImageViewConds.setImageResource(R.drawable.light_shower_snow);
                }else if (CONDS.equals("601")) { // Snow
                    ImageViewConds.setImageResource(R.drawable.snow_day);
                }else if (CONDS.equals("602")) { // HeavySnow
                    ImageViewConds.setImageResource(R.drawable.heavy_snow);
                }else if (CONDS.equals("611")) { // Sleet
                    ImageViewConds.setImageResource(R.drawable.sleet_day);
                }else if (CONDS.equals("612")) { // Light Shower Sleet
                    ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
                }else if (CONDS.equals("613")) { // Sleet
                    ImageViewConds.setImageResource(R.drawable.shower_sleet);
                }else if (CONDS.equals("615")) { // Light Rain And Snow
                    ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
                } else if (CONDS.equals("616")) { // Sleet
                    ImageViewConds.setImageResource(R.drawable.sleet_day);
                } else if (CONDS.equals("620")) { // Light Shower Snow
                    ImageViewConds.setImageResource(R.drawable.light_shower_snow);
                }else if (CONDS.equals("621")) { // Shower Snow
                    ImageViewConds.setImageResource(R.drawable.snow_day);
                }else if (CONDS.equals("622")) { // Sleet
                    ImageViewConds.setImageResource(R.drawable.heavy_snow);
                }

                else if (CONDS.equals("200")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
                }else if (CONDS.equals("201")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_day);
                }else if (CONDS.equals("202")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_rain);
                }else if (CONDS.equals("210")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.thunder_day);
                }else if (CONDS.equals("211")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.thunder_day);
                }else if (CONDS.equals("212")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.thunder_day);
                }else if (CONDS.equals("221")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
                }else if (CONDS.equals("230")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_light_drizzle);
                }else if (CONDS.equals("231")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_drizzle);
                }else if (CONDS.equals("232")) { // ThunderStorm
                    ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_drizzle);
                }


            } else {
                //TODO: NIGHTTIME
            }
        } else {
            System.out.println("There has been a problem in processing the json or the data might be incorrect");
        }
    }
    public static String ParseTimeString(String Str){
        return Str.substring(Str.indexOf("-")+1, Str.lastIndexOf(":")) + "h";
    }

    // To fetch if app is online
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) requireActivity()
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
    public void WriteStringToFile(String s, String Fname) throws IOException {
        File file = new File(requireActivity().getFilesDir(),Fname);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(s);
        bufferedWriter.close();
    }
    public static ArrayList<String> ParseJSONWorldNews(String RequestJSON, String WhatDoYouWantFromMe){
        // Declare the main JSON Reader
        ArrayList<String> MyArrayList = new ArrayList<>();
        try {
            JSONObject JSReader = new JSONObject(RequestJSON);
            if (WhatDoYouWantFromMe.equals("status")) {
                MyArrayList.add(JSReader.getString("status"));
            }

            // Getting the list of News Articles (Array of objects)
            JSONArray NewsHeadlines= JSReader.getJSONArray("articles");
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
    public void StringToOpenInBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void SetOnClickListenersForWorldNewsChips(){
        // Set Click Listeners for the Chips
        PrevArticleChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyTitlesArrayListForWorldNews !=null) {
                    if (CURRENT_ARTICLE > 0) {
                        CURRENT_ARTICLE--;

                        ImageNews.setImageResource(R.drawable.materialwall);
                        if(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE) != "null" && MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)!=null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).resize(700, 500).onlyScaleDown().into(ImageNews); // Set Image
                            }catch(Exception e) {
                                e.printStackTrace();
                                ImageNews.setImageResource(R.drawable.materialwall);
                            }
                        }else{
                            ImageNews.setImageResource(R.drawable.materialwall);
                        }
                        NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(CURRENT_ARTICLE)); // Set title
                        NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(CURRENT_ARTICLE)); // Set Description
                        // TODO: FOR DESCRIPTION, ETC
                    }
                }
            }
        });
        NextArticleChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MyTitlesArrayListForWorldNews !=null) {
                    if (CURRENT_ARTICLE < MyTitlesArrayListForWorldNews.size() - 1 ) {
                        CURRENT_ARTICLE++;
                        // Initially set Image Value to a nice resource
                        ImageNews.setImageResource(R.drawable.materialwall);

                        if(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE) != "null" && MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)!=null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).resize(700, 500).onlyScaleDown().into(ImageNews); // Set Image
                            }catch(Exception e){
                                e.printStackTrace();
                                ImageNews.setImageResource(R.drawable.materialwall);
                            }
                        }else{
                            ImageNews.setImageResource(R.drawable.materialwall);
                        }
                        NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(CURRENT_ARTICLE)); // Set title
                        NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(CURRENT_ARTICLE)); // Set Description
                    }
                }

            }
        });
        ChipURLLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyURLArrayListForWorldNews !=null) {
                    try {
                        StringToOpenInBrowser(MyURLArrayListForWorldNews.get(CURRENT_ARTICLE));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}