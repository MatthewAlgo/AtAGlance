package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import android.Manifest;
import android.content.ActivityNotFoundException;
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
import android.util.Pair;
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

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.databinding.FragmentAtaglanceBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ToCamelCaseWord;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.fileExists;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;


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
    public static String APIKEY, APIURL_WEATHER, REQUEST_TYPE, APIURL_FORECAST, APIKEY_NEWS, NEWSAPI_WORLD,
            NEWSAPI_SCIENCE, NEWSAPI_TECHNOLOGY, NEWSAPI_POLITICS, NEWSAPI_BUSINESS, NEWSAPI_ENTERTAINMENT;
    public static String ResponseJSON, ResponseJsonForecast, ResponseJsonWeather;

    public static boolean isDaylightAtCall = true;
    public static Pair<Integer, Integer> SunriseGlobalHourPair, SunsetGlobalHourPair;
    public static String SunriseGlobalHourString, SunsetGlobalHourString;

    public static Date CurrentDate;
    public static Time CurrentTime;
    public static ArrayList<String> GlobalTimeForExtendedForecast, GlobalHumidityForExtendedForecast,
            GlobalWSpeedForExtendedForecast;

    public static int numberOfInflations = 0;


    // FOR THE NEWS PARTS
    protected FragmentAtaglanceBinding binding;
    ScienceNewsPart MyScienceNewsPart;
    TechnologyNewsPart MyTechnologyNewsPart;
    BusinessNewsPart MyBusinessNewsPart;
    PoliticsNewsPart MyPoliticsNewsPart;
    EntertainmentNewsPart MyEntertainmentNewsPart;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

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

        numberOfInflations++;


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
        MyTechnologyNewsPart = new TechnologyNewsPart(binding);
        MyBusinessNewsPart = new BusinessNewsPart(binding);
        MyPoliticsNewsPart = new PoliticsNewsPart(binding);
        MyEntertainmentNewsPart = new EntertainmentNewsPart(binding);

        APIKEY = "135e028a4a2ff09b2427b0156dd32030"; // API KEY FOR WEATHER REQUESTS
        APIKEY_NEWS = "82de6527ef904da08c127287e4044c27"; // API KEY FOR NEWS REQUESTS
        if(numberOfInflations == 1) {
            if (isOnline()) {
                checkLocationPermission();
            } else {
                Toast.makeText(getContext(), "You Are Offline! Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
                //checkLocationPermission();
                SetterFunctionWhenOffline();
            }
        }else{
            // Load as if the user is offline from the cache
            SetterFunctionWhenOffline();
        }
        // Set Click Listeners for the Chips
        SetOnClickListenersForWorldNewsChipsPlusURLChipsForParts();
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
            // We get the current location
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
            if(LONGITUDE == 0 && LATITUDE == LONGITUDE) {
                locationManager.requestLocationUpdates(GPS_PROVIDER, 400, 1, loc_listener);
                Location Loc = locationManager.getLastKnownLocation(GPS_PROVIDER);

                try {
                    LATITUDE = Loc.getLatitude();
                    LONGITUDE = Loc.getLongitude();
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error Fetching Latitude and Longitude Data");
                }
            }
            InitURLsAndExecuteBackGroundRequests(true);
            return true;
        }
    }
    public void InitURLsAndExecuteBackGroundRequests(boolean LocAccepted){
        if(LocAccepted) {
            APIURL_WEATHER = "http://api.openweathermap.org/data/2.5/" + "weather" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
            APIURL_FORECAST = "http://api.openweathermap.org/data/2.5/" + "forecast" + "?lat=" + Double.toString(LATITUDE) + "&lon=" + Double.toString(LONGITUDE) + "&appid=" + APIKEY; // Auto location
        }else{
            APIURL_WEATHER = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"; // TEST -> London, GB
            APIURL_FORECAST = "https://samples.openweathermap.org/data/2.5/forecast?q=London&appid=b1b15e88fa797225412429c1c50c122a1";
        }
        NEWSAPI_WORLD = "https://newsapi.org/v2/top-headlines?language=en&country=us&apiKey=" + APIKEY_NEWS;
        NEWSAPI_SCIENCE = "https://newsapi.org/v2/top-headlines?language=en&category=science&apiKey=" + APIKEY_NEWS;
        NEWSAPI_TECHNOLOGY = "https://newsapi.org/v2/top-headlines?language=en&category=technology&apiKey=" + APIKEY_NEWS;
        NEWSAPI_POLITICS = "https://newsapi.org/v2/top-headlines?language=en&category=politics&apiKey=" + APIKEY_NEWS;
        NEWSAPI_BUSINESS = "https://newsapi.org/v2/top-headlines?language=en&category=business&apiKey=" + APIKEY_NEWS;
        NEWSAPI_ENTERTAINMENT = "https://newsapi.org/v2/top-headlines?language=en&category=entertainment&apiKey=" + APIKEY_NEWS;

        DoInBackgroundRequest("weather");
        DoInBackgroundRequest("forecast");
        DoInBackgroundRequest("news_world");
        DoInBackgroundRequest("news_science");
        DoInBackgroundRequest("news_technology");
        DoInBackgroundRequest("news_business");
        DoInBackgroundRequest("news_politics");
        DoInBackgroundRequest("news_entertainment");
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
                        InitURLsAndExecuteBackGroundRequests(true);
                    }

                } else {

                    // PERMISSION DENIED
                    Toast.makeText(requireContext(), "Location Permission Not Accepted, Limited Features!",Toast.LENGTH_SHORT).show();
                    InitURLsAndExecuteBackGroundRequests(false);

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        }else if(REQUEST_TYPE_LOC == "news_technology"){
            request = new Request.Builder().url(NEWSAPI_TECHNOLOGY).build();
        }else if(REQUEST_TYPE_LOC == "news_business"){
            request = new Request.Builder().url(NEWSAPI_BUSINESS).build();
        }else if(REQUEST_TYPE_LOC == "news_politics"){
            request = new Request.Builder().url(NEWSAPI_POLITICS).build();
        }else if(REQUEST_TYPE_LOC == "news_entertainment"){
            request = new Request.Builder().url(NEWSAPI_ENTERTAINMENT).build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Most likely we are offline and the request failed
                // DO IT FOR THE FORECAST!
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

                                // Here we will check for the availability of the file
                                if(Integer.parseInt(ParseJSONForecast(ResponseJSON, "cod").get(0)) != 200){
                                    // Something bad happened
                                    Toast.makeText(getContext(), "There was a problem in the response for Forecast. Please try again later", Toast.LENGTH_LONG).show();
                                }else {
                                    // SAVE THE JSON REQUEST LOCALLY
                                    // THE REQUEST WENT WELL
                                    DifferentFunctions.writeToFile(requireContext(),"JSON_FORECAST_CACHE.json", MyResp);
                                }
                                ResponseJSON = readFromFile(requireContext(), "JSON_FORECAST_CACHE.json");
                                ResponseJsonForecast = ResponseJSON;
                                if(ResponseJSON != null && ResponseJSON != "") {
                                    // Now we read the file and put the response inside

                                    // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
                                    ArrayList<String> ConditionsInJson = ParseJSONForecast(ResponseJSON, new String("id_icon"));
                                    ArrayList<String> TimeStamps = ParseJSONForecast(ResponseJSON, new String("time_stamp"));

                                    GlobalTimeForExtendedForecast = ParseJSONForecast(ResponseJSON, new String("time_stamp"));
                                    GlobalHumidityForExtendedForecast = ParseJSONForecast(ResponseJSON, "humidity");
                                    GlobalWSpeedForExtendedForecast = DifferentFunctions.ParseJSONForecast(ResponseJSON, "w_speed");

                                    // CHANGE IMAGES OF HOUR FORECAST ACCORDING TO IDS
                                    ModifyImageToConditions(ImageHour1, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(0)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(0));
                                    ModifyImageToConditions(ImageHour2, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(1)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(1));
                                    ModifyImageToConditions(ImageHour3, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(2)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(2));
                                    ModifyImageToConditions(ImageHour4, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(3)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(3));
                                    ModifyImageToConditions(ImageHour5, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(4)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(4));
                                    ModifyImageToConditions(ImageHour6, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(5)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(5));
                                    ModifyImageToConditions(ImageHour7, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(6)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(6));
                                    ModifyImageToConditions(ImageHour8, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(7)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(7));
                                    ModifyImageToConditions(ImageHour9, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(8)),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(8));

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
                                // Here we will check for the availability of the file
                                if(Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod")) != 200){
                                    // Something bad happened
                                    Toast.makeText(getContext(), "There was a problem in the response for Weather. Please try again later", Toast.LENGTH_LONG).show();
                                }else {
                                    // SAVE THE JSON REQUEST LOCALLY
                                    // THE REQUEST WENT WELL
                                    DifferentFunctions.writeToFile(requireContext(),"JSON_WEATHER_CACHE.json", MyResp);
                                }
                                ResponseJSON = readFromFile(requireContext(), "JSON_WEATHER_CACHE.json");
                                ResponseJsonWeather = ResponseJSON;

                                // IF THE REQUEST WAS MADE ONLY FOR THE CURRENT WEATHER///////////////////////////////////////////////////////////////////
                                if(ResponseJSON != null && ResponseJSON != "") {
                                    // Get the current date and time and set the upper chip
                                    CurrentDate = new Date((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                    CurrentTime = new Time((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                    chipLastUpdated.setText("Updated At: " + CurrentDate.toString() + " " + CurrentTime.toString());

                                    // SAVE THE JSON REQUEST LOCALLY
                                    // try { WriteStringToFile(ResponseJSON, "FILE_JSON_WEATHER.json"); } catch (IOException e) { e.printStackTrace(); }

                                    // MODIFY THE DESCRIPTION TEXTVIEW
                                    // System.out.println(Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod"))); -> Call returns 200 if OK
                                    String ToModifyCurrentConds = ParseJSONCurrentWeather(ResponseJSON, "description");

                                    String LocationByLatAndLong = ParseJSONCurrentWeather(ResponseJSON, "city_name");
                                    // Try to fetch city name - This should not be equal to "Globe"
                                    if (LocationByLatAndLong == "Globe") {
                                        Toast.makeText(getContext(), "Error while fetching your current location. Please try again later.", Toast.LENGTH_LONG).show();
                                    }

                                    String TimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "time");
                                    String SunriseTimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "sunrise");
                                    String SunsetTimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "sunset");
                                    // TIMESTAMP VALUES IN GMT!

                                    /// Process the timestamps
                                    SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix);
                                    SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix);
                                    SunriseGlobalHourString = SunriseTimeStampUnix;
                                    SunsetGlobalHourString = SunsetTimeStampUnix;
                                    // We have sunrise / sunset stored globally

                                    System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix).first);
                                    System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix).second);

                                    System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix).first);
                                    System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix).second);

                                    System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix).first);
                                    System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix).second);

                                    isDaylightAtCall = DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix));

                                    System.out.println(isDaylightAtCall);


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


                                    String WindConditions = null;
                                    double WindSpeed = Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "w_speed")) * 0.001 * 3600;
                                    if (WindSpeed == 0) {
                                        WindConditions = "No Wind";
                                    }
                                    if (WindSpeed < 5 && WindSpeed > 0) {
                                        WindConditions = "Light Breeze";
                                    }
                                    if (WindSpeed >= 5 && WindSpeed < 20) {
                                        WindConditions = "Light Wind";
                                    }
                                    if (WindSpeed >= 20 && WindSpeed < 30) {
                                        WindConditions = "Moderate Wind";
                                    }
                                    if (WindSpeed >= 30) {
                                        WindConditions = "Strong Wind";
                                    }
                                    chipWind.setText("Speed - " + String.format("%.2f", (Double) WindSpeed) + "km/h" + " - " + WindConditions);

                                    // MODIFY THE MAIN ICON
                                    ModifyImageToConditions(MainCurrentCondition, isDaylightAtCall, ToModifyCurrentCondsImage);

                                    // MODIFY THE TEMPERATURE
                                    String ToModifyCurrentTemperature = ParseJSONCurrentWeather(ResponseJSON, "temperature");
                                    TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "°C");

                                    chipHILO.setText("High - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmax")) - 273.15) + "°C" + " | Low - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmin")) - 273.15) + "°C");
                                }
                            }else if(REQUEST_TYPE_LOC.equals("news_world")){
                                // Here we will check for the availability of the file
                                if(!ParseJSONWorldNews(ResponseJSON, "status").get(0).equals("ok")){
                                    // Something bad happened
                                    Toast.makeText(getContext(), "The news response is outdated. Maybe today's calls have expired. Please try again later", Toast.LENGTH_SHORT).show();
                                }else {
                                    // SAVE THE JSON REQUEST LOCALLY
                                    // THE REQUEST WENT WELL
                                    DifferentFunctions.writeToFile(requireContext(),"JSON_WORLDNEWS_CACHE.json", MyResp);
                                }
                                ResponseJSON = readFromFile(requireContext(), "JSON_WORLDNEWS_CACHE.json");

                                if(ResponseJSON != null && ResponseJSON != "") {
                                    MyTitlesArrayListForWorldNews = DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "news_title");
                                    NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(0));

                                    MyDescriptionsArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON, "news_descr");
                                    NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(0));

                                    MyURLArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON, "news_url");

                                    // To load the first corresponding image
                                    MyIMGURLArrayListForWorldNews = ParseJSONWorldNews(ResponseJSON, "news_url_to_img");
                                    if (MyIMGURLArrayListForWorldNews.get(0) == null || MyIMGURLArrayListForWorldNews.get(0) == "null") {
                                        ImageNews.setImageResource(R.drawable.materialwall);
                                    } else {
                                        Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).fit().centerInside().into(ImageNews); // Set Image
                                    }
                                }
                            }else if(REQUEST_TYPE_LOC.equals("news_science")) {
                                MyScienceNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
                            }else if(REQUEST_TYPE_LOC.equals("news_technology")) {
                                MyTechnologyNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
                            }else if(REQUEST_TYPE_LOC.equals("news_business")){
                                MyBusinessNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
                            }else if(REQUEST_TYPE_LOC.equals("news_politics")) {
                                MyPoliticsNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
                            }else if(REQUEST_TYPE_LOC.equals("news_entertainment")){
                                MyEntertainmentNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
                            }
                        }

                    });
                }else{
                    // Most likely we are offline and the request failed
                    Toast.makeText(requireContext(), "Error while processing the request. Loading backup data...", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    private void SetOnClickListenersForWorldNewsChipsPlusURLChipsForParts(){
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
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).fit().centerInside().into(ImageNews); // Set Image
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
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).fit().centerInside().into(ImageNews); // Set Image
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
                        SetOnClickListenersForUrlInBrowser(MyURLArrayListForWorldNews.get(CURRENT_ARTICLE));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        //// Set other onClick Listeners for different parts -> Fragment has to be recognized by the activity
        /// For science news Part
        ////////////////////////////
        MyScienceNewsPart.ChipURLLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyScienceNewsPart.MyURLArrayListForScienceNews !=null) {
                    try {

                        SetOnClickListenersForUrlInBrowser(MyScienceNewsPart.MyURLArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        //////////////////////////
        /// For Technology news Part
        MyTechnologyNewsPart.ChipURLLink3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyTechnologyNewsPart.MyURLArrayListForTechnologyNews != null) {
                    try {
                        SetOnClickListenersForUrlInBrowser(MyTechnologyNewsPart.MyURLArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /////////////////////////////////
        /// For Business news Part
        MyBusinessNewsPart.ChipURLLink4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyBusinessNewsPart.MyURLArrayListForBusinessNews != null) {
                    try {
                        SetOnClickListenersForUrlInBrowser(MyBusinessNewsPart.MyURLArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ///////////////////////////////
        /// For Politics news Part
        MyPoliticsNewsPart.ChipURLLink5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyPoliticsNewsPart.MyURLArrayListForPoliticsNews != null) {
                    try {
                        SetOnClickListenersForUrlInBrowser(MyPoliticsNewsPart.MyURLArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ///////////////////////////////
        /// For Entertainment news Part
        MyEntertainmentNewsPart.ChipURLLink6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyEntertainmentNewsPart.MyURLArrayListForEntertainmentNews != null) {
                    try {
                        SetOnClickListenersForUrlInBrowser(MyEntertainmentNewsPart.MyURLArrayListForEntertainmentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /// To Open the URL's in browser

    /// Functions that depend on the current fragment -> (StartActivity + requireActivity and others)
    public void SetOnClickListenersForUrlInBrowser(String URL){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
    public void SetterFunctionWhenOffline(){
        // For current weather conditions JSON
        if(fileExists(requireContext(), "JSON_WEATHER_CACHE.json")){
            String RespForWeather = readFromFile(requireContext(), "JSON_WEATHER_CACHE.json");
            ResponseJsonWeather = RespForWeather;

            // IF THE REQUEST WAS MADE ONLY FOR THE CURRENT WEATHER///////////////////////////////////////////////////////////////////

            // Get the current date and time and set the upper chip
            CurrentDate = new Date((long)1000*Integer.parseInt(ParseJSONCurrentWeather(RespForWeather, "time")));
            CurrentTime = new Time((long)1000*Integer.parseInt(ParseJSONCurrentWeather(RespForWeather, "time")));
            chipLastUpdated.setText("Updated At: "+CurrentDate.toString() +" "+ CurrentTime.toString());

            // MODIFY THE DESCRIPTION TEXTVIEW
            String ToModifyCurrentConds = ParseJSONCurrentWeather(RespForWeather, "description");

            String LocationByLatAndLong = ParseJSONCurrentWeather(RespForWeather, "city_name");
            // Try to fetch city name - This should not be equal to "Globe"
            if(LocationByLatAndLong == "Globe") { Toast.makeText(getContext(), "Error while fetching your current location. Please try again later.", Toast.LENGTH_LONG).show();}

            String TimeStampUnix = ParseJSONCurrentWeather(RespForWeather, "time");
            String SunriseTimeStampUnix = ParseJSONCurrentWeather(RespForWeather, "sunrise");
            String SunsetTimeStampUnix = ParseJSONCurrentWeather(RespForWeather, "sunset");
            // TIMESTAMP VALUES IN GMT!

            /// Process the timestamps
            SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix);
            SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix);
            SunriseGlobalHourString = SunriseTimeStampUnix;
            SunsetGlobalHourString = SunsetTimeStampUnix;
            // We have sunrise / sunset stored globally

            System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix).first);
            System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix).second);

            System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix).first);
            System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix).second);

            System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix).first);
            System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix).second);

            isDaylightAtCall = DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix));

            System.out.println(isDaylightAtCall);


            chipCurrentWeatherPromptJava.setText("Current Weather Conditions for " + LocationByLatAndLong);
            MainConditionTextJava.setText(ToCamelCaseWord(ToModifyCurrentConds));
            String ToModifyCurrentCondsImage = ParseJSONCurrentWeather(RespForWeather, "conditions_id");

            // Set humidity in forecast
            String HumidityRightNow = ParseJSONCurrentWeather(RespForWeather, "humidity");
            String AdditionalConditions = "Unknown condition";
            double valuehumid = Double.parseDouble(HumidityRightNow); if (valuehumid < 20) { AdditionalConditions = "Dry"; } else if (valuehumid >= 20 && valuehumid <= 60) { AdditionalConditions = "Comfortable"; } else if (valuehumid > 60) { AdditionalConditions = "Humid"; }
            chipHumidity.setText(HumidityRightNow + "% - " + AdditionalConditions);


            String WindConditions = null; double WindSpeed = Double.parseDouble(ParseJSONCurrentWeather(RespForWeather, "w_speed"))*0.001*3600 ;
            if(WindSpeed == 0){ WindConditions = "No Wind"; }if(WindSpeed < 5 && WindSpeed > 0){ WindConditions = "Light Breeze"; }if(WindSpeed >= 5 && WindSpeed <20){ WindConditions = "Light Wind"; }if(WindSpeed >= 20 && WindSpeed <30){ WindConditions = "Moderate Wind"; }if(WindSpeed >= 30){ WindConditions = "Strong Wind"; }
            chipWind.setText("Speed - "+ String.format("%.2f", (Double) WindSpeed) + "km/h" + " - " + WindConditions);

            // MODIFY THE MAIN ICON
            ModifyImageToConditions(MainCurrentCondition, isDaylightAtCall, ToModifyCurrentCondsImage);

            // MODIFY THE TEMPERATURE
            String ToModifyCurrentTemperature = ParseJSONCurrentWeather(RespForWeather, "temperature");
            TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "°C");

            chipHILO.setText("High - "+ String.format("%.1f",Double.parseDouble(ParseJSONCurrentWeather(RespForWeather, "tmax"))-273.15)+"°C"+ " | Low - "+String.format("%.1f",Double.parseDouble(ParseJSONCurrentWeather(RespForWeather, "tmin"))-273.15)+"°C");

        }
        if(fileExists(requireContext(), "JSON_FORECAST_CACHE.json")){
            String RespForForecast = readFromFile(requireContext(), "JSON_FORECAST_CACHE.json");

            ResponseJsonForecast = RespForForecast;

            // Now we read the file and put the response inside

            // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
            ArrayList<String> ConditionsInJson = ParseJSONForecast(RespForForecast, new String("id_icon"));
            ArrayList<String> TimeStamps = ParseJSONForecast(RespForForecast, new String("time_stamp"));

            GlobalTimeForExtendedForecast = ParseJSONForecast(RespForForecast, new String("time_stamp"));
            GlobalHumidityForExtendedForecast = ParseJSONForecast(RespForForecast, "humidity");
            GlobalWSpeedForExtendedForecast = DifferentFunctions.ParseJSONForecast(RespForForecast, "w_speed");

            // CHANGE IMAGES OF HOUR FORECAST ACCORDING TO IDS
            ModifyImageToConditions(ImageHour1, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(0)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(0));
            ModifyImageToConditions(ImageHour2, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(1)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(1));
            ModifyImageToConditions(ImageHour3, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(2)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(2));
            ModifyImageToConditions(ImageHour4, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(3)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(3));
            ModifyImageToConditions(ImageHour5, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(4)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(4));
            ModifyImageToConditions(ImageHour6, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(5)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(5));
            ModifyImageToConditions(ImageHour7, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(6)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(6));
            ModifyImageToConditions(ImageHour8, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(7)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(7));
            ModifyImageToConditions(ImageHour9, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(8)),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ConditionsInJson.get(8));

            // Change Chip Values according to temperatures predicted
            ConditionsInJson = ParseJSONForecast(RespForForecast, new String("temperature"));
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
            ChipHourUpperText1.setText(ParseJSONForecast(RespForForecast, new String("time")).get(0));
            ChipHourUpperText2.setText(ParseJSONForecast(RespForForecast, new String("time")).get(1));
            ChipHourUpperText3.setText(ParseJSONForecast(RespForForecast, new String("time")).get(2));
            ChipHourUpperText4.setText(ParseJSONForecast(RespForForecast, new String("time")).get(3));
            ChipHourUpperText5.setText(ParseJSONForecast(RespForForecast, new String("time")).get(4));
            ChipHourUpperText6.setText(ParseJSONForecast(RespForForecast, new String("time")).get(5));
            ChipHourUpperText7.setText(ParseJSONForecast(RespForForecast, new String("time")).get(6));
            ChipHourUpperText8.setText(ParseJSONForecast(RespForForecast, new String("time")).get(7));
            ChipHourUpperText9.setText(ParseJSONForecast(RespForForecast, new String("time")).get(8));


        }
        if(fileExists(requireContext(), "JSON_WORLDNEWS_CACHE.json")){
            String RespForForecast = readFromFile(requireContext(), "JSON_WORLDNEWS_CACHE.json");

            if(RespForForecast != null && RespForForecast != "") {
                MyTitlesArrayListForWorldNews = DifferentFunctions.ParseJSONWorldNews(RespForForecast, "news_title");
                NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(0));

                MyDescriptionsArrayListForWorldNews = ParseJSONWorldNews(RespForForecast, "news_descr");
                NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(0));

                MyURLArrayListForWorldNews = ParseJSONWorldNews(RespForForecast, "news_url");

                // To load the first corresponding image
                MyIMGURLArrayListForWorldNews = ParseJSONWorldNews(RespForForecast, "news_url_to_img");
                if (MyIMGURLArrayListForWorldNews.get(0) == null || MyIMGURLArrayListForWorldNews.get(0) == "null") {
                    ImageNews.setImageResource(R.drawable.materialwall);
                } else {
                    Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).fit().centerInside().into(ImageNews); // Set Image
                }
            }
        }
        if(fileExists(requireContext(), "JSON_SCIENCENEWS_CACHE.json")){
            String Response = readFromFile(getContext(), "JSON_SCIENCENEWS_CACHE.json");
            MyScienceNewsPart.MyTitlesArrayListForScienceNews = ParseJSONWorldNews(Response,"news_title");

            if(Response != null && Response != "") {

                MyScienceNewsPart.ChipNewsTitle2.setText(MyScienceNewsPart.MyTitlesArrayListForScienceNews.get(0));

                MyScienceNewsPart.MyDescriptionsArrayListForScienceNews = ParseJSONWorldNews(Response, "news_descr");
                if (MyScienceNewsPart.MyDescriptionsArrayListForScienceNews.get(0) != null) {
                    MyScienceNewsPart.NewsDescriptionText2.setText(MyScienceNewsPart.MyDescriptionsArrayListForScienceNews.get(0));
                }

                MyScienceNewsPart.MyURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url");

                // To load the first corresponding image
                MyScienceNewsPart.MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url_to_img");
                MyScienceNewsPart.MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url_to_img");
                if (MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0) == null || MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0) == "null") {
                    MyScienceNewsPart.ImageNews2.setImageResource(R.drawable.materialwall);
                } else {
                    Picasso.get().load(MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0)).fit().centerInside().into(MyScienceNewsPart.ImageNews2); // Set Image
                }
            }
        }
        if(fileExists(requireContext(), "JSON_TECHNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_TECHNEWS_CACHE.json");

            MyTechnologyNewsPart.MyTitlesArrayListForTechnologyNews = ParseJSONWorldNews(Response, "news_title");
            MyTechnologyNewsPart.ChipNewsTitle3.setText(MyTechnologyNewsPart.MyTitlesArrayListForTechnologyNews.get(0));

            MyTechnologyNewsPart.MyDescriptionsArrayListForTechnologyNews = ParseJSONWorldNews(Response, "news_descr");
            if (MyTechnologyNewsPart.MyDescriptionsArrayListForTechnologyNews.get(0) != null) {
                MyTechnologyNewsPart.NewsDescriptionText3.setText(MyTechnologyNewsPart.MyDescriptionsArrayListForTechnologyNews.get(0));
            }

            MyTechnologyNewsPart.MyURLArrayListForTechnologyNews = ParseJSONWorldNews(Response, "news_url");

            // To load the first corresponding image
            MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews = ParseJSONWorldNews(Response, "news_url_to_img");
            if(MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0) == null || MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0) == "null"){
                MyTechnologyNewsPart.ImageNews3.setImageResource(R.drawable.materialwall);
            }else {
                Picasso.get().load(MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0)).fit().centerInside().into(MyTechnologyNewsPart.ImageNews3); // Set Image
            }
        }
        if(fileExists(requireContext(), "JSON_BUSINESSNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_BUSINESSNEWS_CACHE.json");

            MyBusinessNewsPart.MyTitlesArrayListForBusinessNews = ParseJSONWorldNews(Response, "news_title");
            MyBusinessNewsPart.ChipNewsTitle4.setText(MyBusinessNewsPart.MyTitlesArrayListForBusinessNews.get(0));

            MyBusinessNewsPart.MyDescriptionsArrayListForBusinessNews = ParseJSONWorldNews(Response, "news_descr");
            if (MyBusinessNewsPart.MyDescriptionsArrayListForBusinessNews.get(0) != null) {
                MyBusinessNewsPart.NewsDescriptionText4.setText(MyBusinessNewsPart.MyDescriptionsArrayListForBusinessNews.get(0));
            }

            MyBusinessNewsPart.MyURLArrayListForBusinessNews = ParseJSONWorldNews(Response, "news_url");

            // To load the first corresponding image
            MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews = ParseJSONWorldNews(Response, "news_url_to_img");
            if(MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0) == null || MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0) == "null"){
                MyBusinessNewsPart.ImageNews4.setImageResource(R.drawable.materialwall);
            }else {
                Picasso.get().load(MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0)).fit().centerInside().into(MyBusinessNewsPart.ImageNews4); // Set Image
            }
        }
        if(fileExists(requireContext(), "JSON_POLITICSNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_POLITICSNEWS_CACHE.json");


            MyPoliticsNewsPart.MyTitlesArrayListForPoliticsNews = ParseJSONWorldNews(Response, "news_title");
            MyPoliticsNewsPart.ChipNewsTitle5.setText(MyPoliticsNewsPart.MyTitlesArrayListForPoliticsNews.get(0));

            MyPoliticsNewsPart.MyDescriptionsArrayListForPoliticsNews = ParseJSONWorldNews(Response, "news_descr");
            if (MyPoliticsNewsPart.MyDescriptionsArrayListForPoliticsNews.get(0) != null) {
                MyPoliticsNewsPart.NewsDescriptionText5.setText(MyPoliticsNewsPart.MyDescriptionsArrayListForPoliticsNews.get(0));
            }

            MyPoliticsNewsPart.MyURLArrayListForPoliticsNews = ParseJSONWorldNews(Response, "news_url");

            // To load the first corresponding image
            MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews = ParseJSONWorldNews(Response, "news_url_to_img");
            if(MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0) == null || MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0) == "null"){
                MyPoliticsNewsPart.ImageNews5.setImageResource(R.drawable.materialwall);
            }else {
                Picasso.get().load(MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0)).fit().centerInside().into(MyPoliticsNewsPart.ImageNews5); // Set Image
            }
        }
        if(fileExists(requireContext(), "JSON_ENTERTAINMENTNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_ENTERTAINMENTNEWS_CACHE.json");

            MyEntertainmentNewsPart.MyTitlesArrayListForEntertainmentNews = ParseJSONWorldNews(Response, "news_title");
            MyEntertainmentNewsPart.ChipNewsTitle6.setText(MyEntertainmentNewsPart.MyTitlesArrayListForEntertainmentNews.get(0));

            MyEntertainmentNewsPart.MyDescriptionsArrayListForEntertainamentNews = ParseJSONWorldNews(Response, "news_descr");
            if (MyEntertainmentNewsPart.MyDescriptionsArrayListForEntertainamentNews.get(0) != null) {
                MyEntertainmentNewsPart.NewsDescriptionText6.setText(MyEntertainmentNewsPart.MyDescriptionsArrayListForEntertainamentNews.get(0));
            }

            MyEntertainmentNewsPart.MyURLArrayListForEntertainmentNews = ParseJSONWorldNews(Response, "news_url");

            // To load the first corresponding image
            MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews = ParseJSONWorldNews(Response, "news_url_to_img");
            if(MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0) == null || MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0) == "null"){
                MyEntertainmentNewsPart.ImageNews6.setImageResource(R.drawable.materialwall);
            }else {
                Picasso.get().load(MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0)).fit().centerInside().into(MyEntertainmentNewsPart.ImageNews6); // Set Image
            }
        }

    }



}