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
import androidx.annotation.Nullable;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.AddElementToFavesArray;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyDescriptionsFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyImagesURLFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyTitlesFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyURLFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ReadJSONWithStarred;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ToCamelCaseWord;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.WriteJSONWithStarred;
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
    private Chip ChipAddToReadLater;


    public static ArrayList<String> MyTitlesArrayListForWorldNews;
    public static ArrayList<String> MyDescriptionsArrayListForWorldNews;
    public static ArrayList<String> MyURLArrayListForWorldNews;
    public static ArrayList<String> MyIMGURLArrayListForWorldNews;
    private static int CURRENT_ARTICLE = 0;
    private View PubView;

    public double LATITUDE;
    public double LONGITUDE;
    // Variables for the connectivity
    public static ConnectivityManager connectivityManager;
    public static NetworkInfo wifiInfo, mobileInfo;
    public static boolean connected = false;
    public static String APIKEY, APIURL_WEATHER, REQUEST_TYPE, APIURL_FORECAST, APIKEY_NEWS, NEWSAPI_WORLD,
            NEWSAPI_SCIENCE, NEWSAPI_TECHNOLOGY, NEWSAPI_POLITICS, NEWSAPI_BUSINESS, NEWSAPI_ENTERTAINMENT;
    public static String ResponseJSON, ResponseJsonForecast, ResponseJsonWeather, ResponseScienceNews,
        ResponseWorldNews, ResponseBusinessNews, ResponsePoliticsNews, ResponseTechNews, ResponseEntertainmentNews;

    public static boolean isDaylightAtCall = true;
    public static Pair<Integer, Integer> SunriseGlobalHourPair, SunsetGlobalHourPair;
    public static String SunriseGlobalHourString, SunsetGlobalHourString;

    public static Date CurrentDate;
    public static Time CurrentTime;
    public static ArrayList<String> GlobalTimeForExtendedForecast, GlobalHumidityForExtendedForecast,
            GlobalWSpeedForExtendedForecast;

    public static int numberOfInflations = 0;
    public static int numberOfErrorCallsOnRequest = 0;


    // FOR THE NEWS PARTS
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ataglancefrag, container, false);

        MainCurrentCondition = (ImageView) view.findViewById(R.id.MainImageView);
        MainConditionTextJava = view.findViewById(R.id.MainConditionText);
        TempChip = view.findViewById(R.id.chipTemperature);
        chipCurrentWeatherPromptJava = view.findViewById(R.id.chipCurrentWeatherPrompt);
        chipHumidity = view.findViewById(R.id.chipHumidity);
        chipWind = view.findViewById(R.id.chipWind);

        // Images for hourly forecast                                   // Chips For hourly forecast                            // TextViews for TIME
        ImageHour1 = view.findViewById(R.id.ImageHour1);                     ChipHour1 = view.findViewById(R.id.ChipHour1);               ChipHourUpperText1 = view.findViewById(R.id.ChipHourUpperText1);
        ImageHour4 = view.findViewById(R.id.ImageHour4);                     ChipHour4 = view.findViewById(R.id.ChipHour4);               ChipHourUpperText4 = view.findViewById(R.id.ChipHourUpperText4);
        ImageHour3 = view.findViewById(R.id.ImageHour3);                     ChipHour3 = view.findViewById(R.id.ChipHour3);               ChipHourUpperText3 = view.findViewById(R.id.ChipHourUpperText3);
        ImageHour5 = view.findViewById(R.id.ImageHour5);                     ChipHour5 = view.findViewById(R.id.ChipHour5);               ChipHourUpperText5 = view.findViewById(R.id.ChipHourUpperText5);
        ImageHour6 = view.findViewById(R.id.ImageHour6);                     ChipHour6 = view.findViewById(R.id.ChipHour6);               ChipHourUpperText6 = view.findViewById(R.id.ChipHourUpperText6);
        ImageHour2 = view.findViewById(R.id.ImageHour2);                     ChipHour2 = view.findViewById(R.id.ChipHour2);               ChipHourUpperText2 = view.findViewById(R.id.ChipHourUpperText2);
        ImageHour7 = view.findViewById(R.id.ImageHour7);                     ChipHour7 = view.findViewById(R.id.ChipHour7);               ChipHourUpperText7 = view.findViewById(R.id.ChipHourUpperText7);
        ImageHour8 = view.findViewById(R.id.ImageHour8);                     ChipHour8 = view.findViewById(R.id.ChipHour8);               ChipHourUpperText8 = view.findViewById(R.id.ChipHourUpperText8);
        ImageHour9 = view.findViewById(R.id.ImageHour9);                     ChipHour9 = view.findViewById(R.id.ChipHour9);               ChipHourUpperText9 = view.findViewById(R.id.ChipHourUpperText9);
        chipLastUpdated = view.findViewById(R.id.chipLastUpdated);
        ChipURLLink = view.findViewById(R.id.ChipURLLink);
        ChipAddToReadLater = view.findViewById(R.id.ChipAddToReadLater);
        chipHILO = view.findViewById(R.id.chipHILO);

        NewsTitleChip = view.findViewById(R.id.ChipNewsTitle);
        PrevArticleChip = view.findViewById(R.id.PrevArticleChip);
        NextArticleChip = view.findViewById(R.id.NextArticleChip);
        NewsDescriptionText = view.findViewById(R.id.NewsDescriptionText);
        ImageNews = view.findViewById(R.id.ImageNews);
        NewsCardView = view.findViewById(R.id.NewsCardView);

        PubView = view;

        MyScienceNewsPart = new ScienceNewsPart(PubView);
        MyTechnologyNewsPart = new TechnologyNewsPart(PubView);
        MyBusinessNewsPart = new BusinessNewsPart(PubView);
        MyPoliticsNewsPart = new PoliticsNewsPart(PubView);
        MyEntertainmentNewsPart = new EntertainmentNewsPart(PubView);

        double LATITUDE, LONGITUDE;
        LATITUDE = LONGITUDE = 0;

        numberOfInflations++;

        APIKEY = "135e028a4a2ff09b2427b0156dd32030"; // API KEY FOR WEATHER REQUESTS
        APIKEY_NEWS = "82de6527ef904da08c127287e4044c27"; // API KEY FOR NEWS REQUESTS

        // Read the buffer from file for starred
        if(fileExists(requireContext(),"JSON_SAVED_ITEMS_CACHE.json")) {
            try {
                if(ReadJSONWithStarred(requireContext()).get(0) != null && ReadJSONWithStarred(requireContext()).get(0)!= null) {
                    MyTitlesFaves = ReadJSONWithStarred(requireContext()).get(0);
                    MyDescriptionsFaves = ReadJSONWithStarred(requireContext()).get(1);
                    MyURLFaves = ReadJSONWithStarred(requireContext()).get(2);
                    MyImagesURLFaves = ReadJSONWithStarred(requireContext()).get(3);
                }
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }
        try {

            System.out.println("READ FROM FAVOURITES TITLES: " + MyTitlesFaves.get(0));
            System.out.println("READ FROM DESCRIPTIONS TITLES: " + MyDescriptionsFaves.get(0));
            System.out.println("READ FROM URL TITLES: " + MyURLFaves.get(0));
            System.out.println("READ FROM IMAGEURL TITLES: " + MyImagesURLFaves.get(0));
        }catch (Exception exc){
            System.out.println("Maybe some of the arrays are empty");
        }

        if(numberOfInflations == 1) {
            fillStringsWithJunkIfFilesAreNonExistent();
            if (isOnline(requireActivity())) {
                Toast.makeText(getContext(), "Loading Data...", Toast.LENGTH_SHORT).show();
                checkLocationPermission(); // Calls the internet for API'S
                SetterFunctionWhenOffline(); // Reloads from cache
                Toast.makeText(getContext(), "Data loaded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "You Are Offline! Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
                checkLocationPermission();
                SetterFunctionWhenOffline();
            }
        }else{
            // Load as if the user is offline from the cache
            SetterFunctionWhenOffline();
        }
        // Set Click Listeners for the Chips
        SetOnClickListenersForWorldNewsChipsPlusURLChipsForParts();
        return view;
    }

    // For the location permission
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
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
                    Toast.makeText(requireContext(), "Error Fetching Latitude and Longitude Data", Toast.LENGTH_SHORT).show();
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
        PubView = null;
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
                                    ModifyImageToConditions(ImageHour1, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(0), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(0));
                                    ModifyImageToConditions(ImageHour2, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(1), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(1));
                                    ModifyImageToConditions(ImageHour3, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(2), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(2));
                                    ModifyImageToConditions(ImageHour4, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(3), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(3));
                                    ModifyImageToConditions(ImageHour5, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(4), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(4));
                                    ModifyImageToConditions(ImageHour6, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(5), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(5));
                                    ModifyImageToConditions(ImageHour7, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(6), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(6));
                                    ModifyImageToConditions(ImageHour8, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(7), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(7));
                                    ModifyImageToConditions(ImageHour9, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(8), requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(8));

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
                                    SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext());
                                    SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext());
                                    SunriseGlobalHourString = SunriseTimeStampUnix;
                                    SunsetGlobalHourString = SunsetTimeStampUnix;
                                    // We have sunrise / sunset stored globally

                                    // System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()).first);
                                    // System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()).second);

                                    // System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()).first);
                                    // System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()).second);

                                    // System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()).first);
                                    // System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()).second);

                                    isDaylightAtCall = DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()),
                                            DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()));

                                    // System.out.println(isDaylightAtCall);


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
                                System.out.println("RESPONSE READ FROM FILE (WORLD NEWS): " + ResponseJSON);
                                if(ResponseJSON == null || ResponseJSON == ""){
                                    ResponseJSON = "{\"status\":\"ok\",\"totalResults\":36,\"articles\":[{\"source\":{\"id\":null,\"name\":\"New York Times\"},\"author\":\"Jack Nicas\",\"title\":\"In Epic vs. Apple Court Fight, a Win for App Developers - The New York Times\",\"description\":\"The decision could have major implications for thousands of businesses that pay Apple billions of dollars each year.\",\"url\":\"https://www.nytimes.com/2021/09/10/technology/epic-apple-app-developers.html\",\"urlToImage\":\"https://static01.nyt.com/images/2021/09/10/business/10epicapple-promo/10epicapple-promo-facebookJumbo.jpg\",\"publishedAt\":\"2021-09-10T16:26:07Z\",\"content\":\"The decision could have a major ripple effect across the digital economy. If Epic prevails after expected appeals, companies would have a new way to avoid the App Store commission, which runs as high… [+1112 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Aaron Gregg\",\"title\":\"DoorDash, Grubhub, UberEats sue New York City over fee caps - The Washington Post\",\"description\":\"The coalition of popular food delivery apps says the law infringes on their private contracts with food sellers.\",\"url\":\"https://www.washingtonpost.com/business/2021/09/10/doordash-grubhub-new-york-city-lawsuit/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/4HOQGKAGW4I6ZM6EYRRLD3OPZA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T15:48:00Z\",\"content\":\"In a lawsuit filed late Thursday in the Southern District of New York, the parent companies of six widely used apps DoorDash, Caviar, Grubhub, Seamless, Postmates and UberEats accused the city of imp… [+139 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn’t the only potential bad actor out there. Here’s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m… [+5151 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"\",\"title\":\"Biden Brushes Off GOP Criticism Of Vaccination Mandate for Teachers - NPR\",\"description\":\"President Biden said he was saddened that some GOP governors are threatening to sue over the new mandates. To them, he said, \\\"Have at it.\\\"\",\"url\":\"https://www.npr.org/2021/09/10/1035869847/biden-brushes-off-gop-threats-to-sue-over-vaccine-mandates\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/10/ap21253539854160_wide-daa8827afff8aca7389512b65f0481a04e13b68b.jpg?s=1400\",\"publishedAt\":\"2021-09-10T15:43:50Z\",\"content\":\"President Biden tours Brookland Middle School in Washington, D.C., on Friday. Biden has encouraged every school district to promote vaccines, including with on-site clinics, to protect students as th… [+2918 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Erica Orden, CNN\",\"title\":\"Giuliani associate Igor Fruman pleads guilty to solicitation of a contribution by a foreign national - CNN\",\"description\":\"Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\",\"url\":\"https://www.cnn.com/2021/09/10/politics/igor-fruman-plea/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/191023132912-01-igor-fruman-1023-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:31:00Z\",\"content\":\"New York (CNN)Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\\r\\n… [+2400 chars]\"},{\"source\":{\"id\":null,\"name\":\"New York Post\"},\"author\":\"Emily Crane\",\"title\":\"China shares supposed video of Taliban using US military planes as toys - New York Post \",\"description\":\"China has mocked the United States’ chaotic withdrawal from Afghanistan by sharing footage that supposedly shows Taliban fighters turning abandoned US military planes into toys.\",\"url\":\"https://nypost.com/2021/09/10/china-shares-supposed-video-of-taliban-using-us-planes-as-toys/\",\"urlToImage\":\"https://nypost.com/wp-content/uploads/sites/2/2021/09/taliban-swings-hp1.jpg?quality=90&strip=all&w=1024\",\"publishedAt\":\"2021-09-10T15:14:00Z\",\"content\":\"China has mocked the United States chaotic withdrawal from Afghanistan by sharing footage that supposedly shows Taliban fighters turning abandoned US military planes into toys.\\r\\nChinese governmental … [+1366 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Alex Heath\",\"title\":\"WhatsApp is adding encrypted backups - The Verge\",\"description\":\"WhatsApp is rolling out end-to-end encrypted backups for chat logs on Android and iOS, protecting the file from anyone without the key.\",\"url\":\"https://www.theverge.com/2021/9/10/22665968/whatsapp-backups-end-to-end-encryption-ios-android\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/MD0eT-m-uto-M_MHFTRUrF_FHDk=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22245551/acastro_210119_1777_whatsapp_0003.jpg\",\"publishedAt\":\"2021-09-10T15:05:45Z\",\"content\":\"Going a step further than Apples iMessage\\r\\nIllustration by Alex Castro / The Verge\\r\\nWhatsApp will let its more than 2 billion users fully encrypt the backups of their messages, the Facebook-owned app… [+2774 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Toyin Owoseje, CNN\",\"title\":\"Kim Kardashian says she's 'not OK' after son Saint breaks his arm - CNN\",\"description\":\"Kim Kardashian revealed on Instagram that 5-year-old Saint West broke his arm, adding that she was left distraught.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/kim-kardashian-son-broken-arm-intl-scli/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/210910090907-kim-kardashian-file-2020-restricted-super-tease.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Deadline\"},\"author\":\"Anthony D'Alessandro\",\"title\":\"Jim Gianopulos Leaving Paramount As Chairman & CEO, Brian Robbins Expected To Take Over - Deadline\",\"description\":\"Jim Gianopulos is leaving Paramount as Chairman and CEO, Deadline has confirmed. A report in the Wall Street Journal said Brian Robbins, the head of ViacomCBS Inc.’s Nickelodeon kids TV empire, will take over Gianopulos’ position as the head of Paramount Pict…\",\"url\":\"https://deadline.com/2021/09/jim-gianopulos-leaving-paramount-as-chairman-ceo-brian-robbins-expected-to-take-over-1234830466/\",\"urlToImage\":\"https://deadline.com/wp-content/uploads/2019/02/jim-gianopulos-paramount.jpg?w=300\",\"publishedAt\":\"2021-09-10T14:18:00Z\",\"content\":\"Jim Gianopulos is leaving Paramount as Chairman and CEO, Deadline has confirmed. A report in the Wall Street Journal said Brian Robbins, the head of ViacomCBS Inc.’s Nickelodeon kids TV empire, will … [+6020 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Robyn Dixon, Reis Thebault\",\"title\":\"Russia begins major military drills with Belarus after moves toward closer integration - The Washington Post\",\"description\":\"Russia’s Vladimir Putin plans to unify Russia’s military space with Belarus, but offered no details on what it means.\",\"url\":\"https://www.washingtonpost.com/world/europe/russia-belarus-zapad-military-drill/2021/09/10/14f3231a-1012-11ec-baca-86b144fc8a2d_story.html\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/A5GICNASHUI6ZPEKRWNFWU2BSQ.jpg&w=1440\",\"publishedAt\":\"2021-09-10T14:17:57Z\",\"content\":\"The Zapad (meaning West) exercise is held regularly, but this iteration comes as Russian relations with NATO are increasingly fraught. Belarus under President Alexander Lukashenko faces Western sanct… [+8166 chars]\"},{\"source\":{\"id\":\"fox-news\",\"name\":\"Fox News\"},\"author\":\"Ryan Gaydos\",\"title\":\"Cowboys' Greg Zuerlein laments missed field goals: 'If I do my job, we win that game' - Fox News\",\"description\":\"Dallas Cowboys kicker Greg Zuerlein was a bit hard on himself after the team’s 31-29 loss to the Tampa Bay Buccaneers on Thursday night.\",\"url\":\"https://www.foxnews.com/sports/cowboys-greg-zuerlein-missed-field-goals\",\"urlToImage\":\"https://static.foxnews.com/foxnews.com/content/uploads/2021/09/Greg-Zuerlein.jpg\",\"publishedAt\":\"2021-09-10T14:11:03Z\",\"content\":\"Dallas Cowboys kicker Greg Zuerlein was a bit hard on himself after the teams 31-29 loss to the Tampa Bay Buccaneers on Thursday night.\\r\\nZuerlein missed two field goals and an extra point attempt tha… [+1564 chars]\"},{\"source\":{\"id\":null,\"name\":\"Fox Business\"},\"author\":\"Jonathan Garber\",\"title\":\"Stocks snap 4-day losing streak, oil nears $70 - Fox Business\",\"description\":\"U.S. stocks bounced around as investors evaluated another hot read on inflation.\",\"url\":\"https://www.foxbusiness.com/markets/stocks-losing-streak-oil-nears-70\",\"urlToImage\":\"https://a57.foxnews.com/static.foxbusiness.com/foxbusiness.com/content/uploads/2018/02/0/0/8815b045-ap16301578658834.jpg?ve=1&tl=1\",\"publishedAt\":\"2021-09-10T13:50:55Z\",\"content\":\"U.S. stock indexes surrendered morning gains sending all three averages lower and diminishing chances that the Dow Jones Industrial Average and the S&amp;P 500 index would snap four-day losing streak… [+2801 chars]\"},{\"source\":{\"id\":\"nbc-news\",\"name\":\"NBC News\"},\"author\":\"Molly Hunter, Petra Cahill\",\"title\":\"At Pakistan border, Afghans find a way out of Taliban rule after a treacherous journey - NBC News\",\"description\":\"The Taliban flag flies on one side of the border. On the other is the Pakistani military— and hope for freedom for terrified Afghans.\",\"url\":\"https://www.nbcnews.com/news/world/pakistan-border-afghans-find-way-out-taliban-rule-after-treacherous-n1278898\",\"urlToImage\":\"https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/newscms/2021_36/3504803/210910-pakistan-taliban-mb-0716.JPG\",\"publishedAt\":\"2021-09-10T13:44:00Z\",\"content\":\"TORKHAM, Pakistan The Taliban flag flies on one side of the border. On the other is the Pakistani military and hope for freedom for terrified Afghans.\\r\\nMursal and Manisha, two teenage sisters from Ka… [+5099 chars]\"},{\"source\":{\"id\":null,\"name\":\"ESPN\"},\"author\":\"Mike Reiss\",\"title\":\"Cam Newton shares surprise at New England Patriots release, didn't think Mac Jones would be comfortable with him as backup - ESPN\",\"description\":\"Free-agent quarterback Cam Newton said he didn't see his release by the Patriots coming and noting that he thought Mac Jones \\\"would have been uncomfortable\\\" with Newton as his backup.\",\"url\":\"https://www.espn.com/nfl/story/_/id/32182159/cam-newton-shares-surprise-new-england-patriots-release-think-mac-jones-comfortable-backup\",\"urlToImage\":\"https://a1.espncdn.com/combiner/i?img=%2Fphoto%2F2021%2F0831%2Fr902858_1296x729_16%2D9.jpg\",\"publishedAt\":\"2021-09-10T13:16:56Z\",\"content\":\"Free-agent quarterback Cam Newton said he didn't see his release from the New England Patriots coming in his first remarks since the team cut him 10 days ago.\\r\\n\\\"Did it catch me by surprise being rele… [+4615 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Allyson Chiu\",\"title\":\"What is ivermectin, and where did people get the idea it can treat covid? - The Washington Post\",\"description\":\"Ivermectin, an anti-parasitic medicine for both humans and animals, is being promoted as a covid treatment despite a lack of evidence.\",\"url\":\"https://www.washingtonpost.com/lifestyle/2021/09/10/ivermectin-covid-humans/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/UPAYQWAL3EI6ZJ6IMG5XWO7WFA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T12:56:15Z\",\"content\":\"Doctors dismayed by patients who fear coronavirus vaccines but clamor for unproven ivermectin\\r\\nInterest in ivermectin has surged during this summers rapid rise in coronavirus infections fueled by the… [+1091 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"David Schaper\",\"title\":\"TSA Timeline: How Travel And Airport Security Changed Since 9/11 - NPR\",\"description\":\"No boarding pass or ID was needed to go to the gate, and 4-inch-blade knives were allowed aboard planes. Now we take off shoes, can't have liquids over 3.4 oz and go through high-tech body scanners.\",\"url\":\"https://www.npr.org/2021/09/10/1035131619/911-travel-timeline-tsa\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/08/2001-09-19t120000z_970614932_pbeahukxucn_rtrmadp_3_world-trade-center1_wide-bf19ae3ccdfbc2952494ae56d30ee65b112f1b4e.jpg?s=1400\",\"publishedAt\":\"2021-09-10T12:46:58Z\",\"content\":\"A traveler at Ronald Reagan Washington National Airport walks to a Transportation Security Administration checkpoint on Nov. 26, 2014.\\r\\nPaul J. Richards/AFP via Getty Images\\r\\nIt's hard to fathom now,… [+20838 chars]\"},{\"source\":{\"id\":null,\"name\":\"Bleeding Green Nation\"},\"author\":\"Brandon Lee Gowton\",\"title\":\"Eagles News: Jason Kelce has a new look - Bleeding Green Nation\",\"description\":\"Philadelphia Eagles news and links for 9/10/21.\",\"url\":\"https://www.bleedinggreennation.com/2021/9/10/22666410/eagles-news-jason-kelce-new-look-blonde-hair-blond-philadelphia-center-analytics-inquirer-falcons\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/J1rUB1dqNmL8_Yg6CHYi-RGXkWM=/0x18:380x217/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839455/Screen_Shot_2021_09_10_at_8.05.37_AM.png\",\"publishedAt\":\"2021-09-10T12:33:47Z\",\"content\":\"Lets get to the Philadelphia Eagles links ...\\r\\nJason Kelce went blonde too, and its blowing our minds - NBCSPEagles tight end Zach Ertz blew the Twitterverse wide open when he reported to training ca… [+12560 chars]\"},{\"source\":{\"id\":null,\"name\":\"Gizmodo.com\"},\"author\":\"Victoria Song\",\"title\":\"You Can Finally Share Dolby Vision Videos Filmed on iPhone 12 - Gizmodo\",\"description\":\"Vimeo, Dolby, and Apple have teamed up so you can finally upload those high-res videos somewhere.\",\"url\":\"https://gizmodo.com/you-can-finally-share-dolby-vision-videos-filmed-on-iph-1847646550\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/3c6d1fe0138f1367b4818e5c069244b1.jpg\",\"publishedAt\":\"2021-09-10T12:30:00Z\",\"content\":\"When Apple trotted out the iPhone 12 lineup last October, it also introduced the ability to record video in Dolby Vision HDR. It was a first for smartphones, not just iPhones. But there was a catch. … [+2127 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Ian Sample\",\"title\":\"Scientists' egos are key barrier to progress, says Covid vaccine pioneer - The Guardian\",\"description\":\"Prof Katalin Karikó of BioNTech says she endured decades of scepticism over her work on mRNA vaccines\",\"url\":\"https://amp.theguardian.com/world/2021/sep/10/scientists-egos-key-barrier-to-progress-covid-vaccine-pioneer-katalin-kariko\",\"urlToImage\":null,\"publishedAt\":\"2021-09-10T11:46:00Z\",\"content\":\"CoronavirusProf Katalin Karikó of BioNTech says she endured decades of scepticism over her work on mRNA vaccines \\r\\nScientists would make swifter progress in solving the worlds problems if they learne… [+5258 chars]\"},{\"source\":{\"id\":null,\"name\":\"Live Science\"},\"author\":\"Stephanie Pappas\",\"title\":\"Spectacular valleys and cliffs hidden beneath the North Sea - Livescience.com\",\"description\":\"Like a bowl of spaghetti noodles spilled across the floor of the North Sea, a vast array of hidden tunnel valleys wind and meander across what was once an ice-covered landscape.&#xA0;These valleys are remnants of ancient rivers that once drained water from me…\",\"url\":\"https://www.livescience.com/north-sea-ice-age-tunnel-valleys.html\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/wtJwXMGsxAhy8ph37Afifb-1200-80.jpg\",\"publishedAt\":\"2021-09-10T11:29:00Z\",\"content\":\"Scientists discovered this esker (a sedimentary cast of a meltwater channel formed beneath an ice sheet), in a tunnel valley beneath the North Sea floor. The landscape is shown in an image based on h… [+6373 chars]\"}]}";
                                    DifferentFunctions.writeToFile(getActivity(),"JSON_WORLDNEWS_CACHE.json", ResponseJSON);
                                    Toast.makeText(getActivity(), "The information is out of date! One or more files did not exist and the API for news is locked for 24 hours", Toast.LENGTH_LONG).show();
                                }
                                ResponseWorldNews = ResponseJSON;
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
                                        if(isOnline(requireActivity()))
                                            Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).fit().centerInside().into(ImageNews); // Set Image
                                        else
                                            ImageNews.setImageResource(R.drawable.materialwall);
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
                }else {
                    numberOfErrorCallsOnRequest++;
                    // Most likely we are offline and the request failed
                    System.out.println("Error while processing the request. Loading backup data...");
                    if(numberOfErrorCallsOnRequest == 1){
                        // Create a Dialog or log entry
                        Log.d(TAG, "onResponse: Error while processing the request. Loading backup data...");
                    }
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
                                if(isOnline(requireActivity()))
                                    Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).fit().centerInside().into(ImageNews); // Set Image
                                else
                                    ImageNews.setImageResource(R.drawable.materialwall);
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
                                if (isOnline(requireActivity())) {
                                    Picasso.get().load(MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE)).fit().centerInside().into(ImageNews); // Set Image
                                }else{
                                    ImageNews.setImageResource(R.drawable.materialwall);
                                }
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
                        SetOnClickListenersForUrlInBrowser(MyURLArrayListForWorldNews.get(CURRENT_ARTICLE), requireActivity());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        ChipAddToReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyURLArrayListForWorldNews !=null) {
                    try {

                        AddElementToFavesArray(MyTitlesArrayListForWorldNews.get(CURRENT_ARTICLE), MyDescriptionsArrayListForWorldNews.get(CURRENT_ARTICLE),
                                MyURLArrayListForWorldNews.get(CURRENT_ARTICLE), MyIMGURLArrayListForWorldNews.get(CURRENT_ARTICLE));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
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

                        SetOnClickListenersForUrlInBrowser(MyScienceNewsPart.MyURLArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber), requireActivity());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        MyScienceNewsPart.ChipURLLink2ReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyScienceNewsPart.MyTitlesArrayListForScienceNews !=null) {
                    try {

                        AddElementToFavesArray(MyScienceNewsPart.MyTitlesArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber), MyScienceNewsPart.MyDescriptionsArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber),
                                MyScienceNewsPart.MyURLArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber), MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(MyScienceNewsPart.CurrentArticleNumber));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
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
                        SetOnClickListenersForUrlInBrowser(MyTechnologyNewsPart.MyURLArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber), requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        MyTechnologyNewsPart.ChipURLLink3ReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyTechnologyNewsPart.MyTitlesArrayListForTechnologyNews !=null) {
                    try {

                        AddElementToFavesArray(MyTechnologyNewsPart.MyTitlesArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber), MyTechnologyNewsPart.MyDescriptionsArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber),
                                MyTechnologyNewsPart.MyURLArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber), MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(MyTechnologyNewsPart.CurrentArticleNumber));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
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
                        SetOnClickListenersForUrlInBrowser(MyBusinessNewsPart.MyURLArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber), requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        MyBusinessNewsPart.ChipURLLink4ReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyBusinessNewsPart.MyTitlesArrayListForBusinessNews !=null) {
                    try {

                        AddElementToFavesArray(MyBusinessNewsPart.MyTitlesArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber), MyBusinessNewsPart.MyDescriptionsArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber),
                                MyBusinessNewsPart.MyURLArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber), MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(MyBusinessNewsPart.CurrentArticleNumber));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
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
                        SetOnClickListenersForUrlInBrowser(MyPoliticsNewsPart.MyURLArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber), requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        MyPoliticsNewsPart.ChipURLLink5ReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyPoliticsNewsPart.MyTitlesArrayListForPoliticsNews !=null) {
                    try {

                        AddElementToFavesArray(MyPoliticsNewsPart.MyTitlesArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber), MyPoliticsNewsPart.MyDescriptionsArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber),
                                MyPoliticsNewsPart.MyURLArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber), MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(MyPoliticsNewsPart.CurrentArticleNumber));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
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
                        SetOnClickListenersForUrlInBrowser(MyEntertainmentNewsPart.MyURLArrayListForEntertainmentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber), requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        MyEntertainmentNewsPart.ChipURLLink6ReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyEntertainmentNewsPart.MyTitlesArrayListForEntertainmentNews !=null) {
                    try {

                        AddElementToFavesArray(MyEntertainmentNewsPart.MyTitlesArrayListForEntertainmentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber), MyEntertainmentNewsPart.MyDescriptionsArrayListForEntertainamentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber),
                                MyEntertainmentNewsPart.MyURLArrayListForEntertainmentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber), MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(MyEntertainmentNewsPart.CurrentArticleNumber));

                        WriteJSONWithStarred(requireContext(), MyTitlesFaves,MyDescriptionsFaves,
                                MyURLFaves, MyImagesURLFaves);
                        Toast.makeText(mContext, "Added Item to Read Later", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not add item to Read Later", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /// To Open the URL's in browser

    /// Functions that depend on the current fragment -> (StartActivity + requireActivity and others)
    public static void SetOnClickListenersForUrlInBrowser(String URL, Context Ctx){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            Ctx.startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Ctx, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    // To fetch if app is online
    public static boolean isOnline(Context ctx) {
        try {
            connectivityManager = (ConnectivityManager) ctx
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
            SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext());
            SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext());
            SunriseGlobalHourString = SunriseTimeStampUnix;
            SunsetGlobalHourString = SunsetTimeStampUnix;
            // We have sunrise / sunset stored globally

            isDaylightAtCall = DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()));

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
            ModifyImageToConditions(ImageHour1, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(0), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(0));
            ModifyImageToConditions(ImageHour2, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(1), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(1));
            ModifyImageToConditions(ImageHour3, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(2), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(2));
            ModifyImageToConditions(ImageHour4, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(3), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(3));
            ModifyImageToConditions(ImageHour5, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(4), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(4));
            ModifyImageToConditions(ImageHour6, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(5), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(5));
            ModifyImageToConditions(ImageHour7, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(6), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(6));
            ModifyImageToConditions(ImageHour8, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(7), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(7));
            ModifyImageToConditions(ImageHour9, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStamps.get(8), requireContext()),
                    DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, requireContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, requireContext())), ConditionsInJson.get(8));

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
            String Response = readFromFile(requireContext(), "JSON_WORLDNEWS_CACHE.json");
            ResponseWorldNews = Response;
            if(Response != null && Response != "") {
                MyTitlesArrayListForWorldNews = DifferentFunctions.ParseJSONWorldNews(Response, "news_title");
                NewsTitleChip.setText(MyTitlesArrayListForWorldNews.get(0));

                MyDescriptionsArrayListForWorldNews = ParseJSONWorldNews(Response, "news_descr");
                NewsDescriptionText.setText(MyDescriptionsArrayListForWorldNews.get(0));

                MyURLArrayListForWorldNews = ParseJSONWorldNews(Response, "news_url");

                // To load the first corresponding image
                MyIMGURLArrayListForWorldNews = ParseJSONWorldNews(Response, "news_url_to_img");
                if (MyIMGURLArrayListForWorldNews.get(0) == null || MyIMGURLArrayListForWorldNews.get(0) == "null") {
                    ImageNews.setImageResource(R.drawable.materialwall);
                } else {
                    if(isOnline(requireActivity()))
                        try {
                            if (MyIMGURLArrayListForWorldNews.get(0) != null || MyIMGURLArrayListForWorldNews.get(0) != "") {
                                Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).fit().centerInside().into(ImageNews); // Set Image
                            } else {
                                ImageNews.setImageResource(R.drawable.materialwall);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                            ImageNews.setImageResource(R.drawable.materialwall);
                        }
                    else
                        ImageNews.setImageResource(R.drawable.materialwall);
                }
            }
        }
        if(fileExists(requireContext(), "JSON_SCIENCENEWS_CACHE.json")){
            String Response = readFromFile(getContext(), "JSON_SCIENCENEWS_CACHE.json");
            ResponseScienceNews = Response;
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
                    if(isOnline(requireActivity()))
                        try{
                            if(MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0) != null || MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0) != "") {
                                Picasso.get().load(MyScienceNewsPart.MyIMGURLArrayListForScienceNews.get(0)).fit().centerInside().into(MyScienceNewsPart.ImageNews2); // Set Image
                            }else{
                                MyScienceNewsPart.ImageNews2.setImageResource(R.drawable.materialwall);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                            MyScienceNewsPart.ImageNews2.setImageResource(R.drawable.materialwall);
                        }
                    else
                        MyScienceNewsPart.ImageNews2.setImageResource(R.drawable.materialwall);
                }
            }
        }
        if(fileExists(requireContext(), "JSON_TECHNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_TECHNEWS_CACHE.json");
            ResponseTechNews = Response;

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

                if(isOnline(requireActivity()))
                    try {
                        if (MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0) != null || MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0) != "") {
                            Picasso.get().load(MyTechnologyNewsPart.MyIMGURLArrayListForTechnologyNews.get(0)).fit().centerInside().into(MyTechnologyNewsPart.ImageNews3); // Set Image
                        } else {
                            MyTechnologyNewsPart.ImageNews3.setImageResource(R.drawable.materialwall);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        MyTechnologyNewsPart.ImageNews3.setImageResource(R.drawable.materialwall);
                    }
                else
                    MyTechnologyNewsPart.ImageNews3.setImageResource(R.drawable.materialwall);

            }
        }
        if(fileExists(requireContext(), "JSON_BUSINESSNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_BUSINESSNEWS_CACHE.json");
            ResponseBusinessNews = Response;

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
                if(isOnline(requireActivity()))
                    try {
                        if (MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0) != null || MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0) != "") {
                            Picasso.get().load(MyBusinessNewsPart.MyIMGURLArrayListForBusinessNews.get(0)).fit().centerInside().into(MyBusinessNewsPart.ImageNews4); // Set Image
                        } else {
                            MyBusinessNewsPart.ImageNews4.setImageResource(R.drawable.materialwall);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        MyBusinessNewsPart.ImageNews4.setImageResource(R.drawable.materialwall);
                    }
                else
                    MyBusinessNewsPart.ImageNews4.setImageResource(R.drawable.materialwall);
            }
        }
        if(fileExists(requireContext(), "JSON_POLITICSNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_POLITICSNEWS_CACHE.json");
            ResponsePoliticsNews = Response;

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
                if(isOnline(requireActivity()))
                    Picasso.get().load(MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0)).fit().centerInside().into(MyPoliticsNewsPart.ImageNews5); // Set Image
                else
                    MyPoliticsNewsPart.ImageNews5.setImageResource(R.drawable.materialwall);

                if(isOnline(requireActivity()))
                    try {
                        if (MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0) != null || MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0) != "") {
                            Picasso.get().load(MyPoliticsNewsPart.MyIMGURLArrayListForPoliticsNews.get(0)).fit().centerInside().into(MyPoliticsNewsPart.ImageNews5); // Set Image
                        } else {
                            MyPoliticsNewsPart.ImageNews5.setImageResource(R.drawable.materialwall);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        MyPoliticsNewsPart.ImageNews5.setImageResource(R.drawable.materialwall);
                    }
                else
                    MyPoliticsNewsPart.ImageNews5.setImageResource(R.drawable.materialwall);


            }
        }
        if(fileExists(requireContext(), "JSON_ENTERTAINMENTNEWS_CACHE.json")) {
            String Response = readFromFile(requireContext(), "JSON_ENTERTAINMENTNEWS_CACHE.json");
            ResponseEntertainmentNews = Response;
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
                if (isOnline(requireActivity())) {
                    Picasso.get().load(MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0)).fit().centerInside().into(MyEntertainmentNewsPart.ImageNews6); // Set Image
                }else{
                    MyEntertainmentNewsPart.ImageNews6.setImageResource(R.drawable.materialwall);


                    try {
                        if (MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0) != null || MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0) != "") {
                            Picasso.get().load(MyEntertainmentNewsPart.MyIMGURLArrayListForEntertainmentNews.get(0)).fit().centerInside().into(MyEntertainmentNewsPart.ImageNews6); // Set Image
                        } else {
                            MyEntertainmentNewsPart.ImageNews6.setImageResource(R.drawable.materialwall);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        MyEntertainmentNewsPart.ImageNews6.setImageResource(R.drawable.materialwall);
                    }
                }
            }
            // TEST - WRITE JSON TO A FILE, FORMATTED
            WriteJSONWithStarred(requireContext(), MyEntertainmentNewsPart.getMyTitlesArrayListForEntertainmentNews(),
                    MyEntertainmentNewsPart.getMyDescriptionsArrayListForEntertainamentNews(), MyEntertainmentNewsPart.getMyURLArrayListForEntertainmentNews(),
                    MyEntertainmentNewsPart.getMyIMGURLArrayListForEntertainmentNews());
        }

    }

    public void fillStringsWithJunkIfFilesAreNonExistent(){
        String ResponseJSON;

        InitWorldNewsOnRetrievalFailure(); // Init the world news

        if(!fileExists(PubView.getContext(), "JSON_SCIENCENEWS_CACHE.json")) {
            ResponseJSON = "{\"status\":\"ok\",\"totalResults\":744,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Inverse\"},\"author\":\"Jon Kelvey\",\"title\":\"The James Webb Telescope is delayed again. Here's why. - Inverse\",\"description\":\"NASA announced Wednesday that the Jams Webb telescope will launch on December 18, rather than October 31 as previously scheduled.\",\"url\":\"https://www.inverse.com/science/why-nasa-has-bumped-the-launch-of-the-james-webb-telescope-again\",\"urlToImage\":\"https://imgix.bustle.com/uploads/getty/2021/9/9/e71b592b-e74a-4aac-8113-4ed579dc32c5-getty-620501012.jpg?w=1200&h=630&fit=crop&crop=faces&fm=jpg\",\"publishedAt\":\"2021-09-10T16:30:25Z\",\"content\":\"The bad news: The launch of the James Webb Space Telescope\\r\\n, the decades in the making, big-freaking-mirror-equipped successor to the Hubble, has been delayed yet again. \\r\\nThe good news: Webb is sti… [+3269 chars]\"},{\"source\":{\"id\":null,\"name\":\"3dnatives\"},\"author\":null,\"title\":\"The First Ever 3D Printed Stellar Nurseries to be Used to Study Star Creation - 3Dnatives\",\"description\":\"The latest news on 3D Printing and Additive Manufacturing technologies. Find the best stories, prices, interviews, market reports and tests on 3Dnatives\",\"url\":\"https://www.3dnatives.com/en/\",\"urlToImage\":\"https://www.3dnatives.com/en/wp-content/uploads/sites/2/logo_3Dnatives2017_carre.png\",\"publishedAt\":\"2021-09-10T16:05:10Z\",\"content\":\"3Dnatives is the largest international online media platform on 3D printing and its applications. With its in-depth analysis of the market, 3Dnatives gets over 1 million unique visitors per month and… [+276 chars]\"},{\"source\":{\"id\":null,\"name\":\"SciTechDaily\"},\"author\":null,\"title\":\"Personality Matters, Even for Wildlife: Social Skills Give Ground Squirrels an Advantage - SciTechDaily\",\"description\":\"Humans acknowledge that personality goes a long way, at least for our species. But scientists have been more hesitant to ascribe personality—defined as consistent behavior over time—to other animals. A study from the University of California, Davis is the fir…\",\"url\":\"https://scitechdaily.com/personality-matters-even-for-wildlife-social-skills-give-ground-squirrels-an-advantage/\",\"urlToImage\":\"https://scitechdaily.com/images/Golden-Mantled-Ground-Squirrel-Close-Up.jpg\",\"publishedAt\":\"2021-09-10T16:02:47Z\",\"content\":\"Humans acknowledge that personality goes a long way, at least for our species. But scientists have been more hesitant to ascribe personality—defined as consistent behavior over time—to other animals.… [+5302 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Chris Young\",\"title\":\"New Images of the \\\"Dog Bone\\\" Asteroid Show It May Soon Fall Apart - Interesting Engineering\",\"description\":\"The rapidly-spinning asteroid is shaped like a dog bone and has at least two orbiting moons.\",\"url\":\"https://interestingengineering.com/new-images-of-the-dog-bone-asteroid-show-it-may-soon-fall-apart\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/BxG2x3qDw9/sizes/kleopatra-asteroid_md.jpg\",\"publishedAt\":\"2021-09-10T15:50:00Z\",\"content\":\"An international team of astronomers captured new images of a rapidly-rotating asteroid between Mars and Jupiter. The asteroid, called Kleopatra, was first discovered orbiting our Sun twenty years ag… [+2841 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNET\"},\"author\":\"CNET staff\",\"title\":\"Save 19% on this Lego kit for the International Space Station - CNET\",\"description\":\"You'll need to supply your own space debris if you want to recreate scenes from Gravity, though.\",\"url\":\"https://www.cnet.com/news/save-19-on-this-lego-kit-for-the-international-space-station/\",\"urlToImage\":\"https://www.cnet.com/a/img/sElBLVWScBcLePjaWQkk2pFwbww=/1200x630/2021/09/10/b3c396a3-1e7e-4839-a025-30b739ed04d2/81nx5db7zil-ac-sl1500.jpg\",\"publishedAt\":\"2021-09-10T15:44:00Z\",\"content\":\"Lego\\r\\nIt's not often you find a good deal on any Lego kit, but that is doubly true when dealing with any of the bigger kits. There are so many different pieces involved in any larger kit, but the mor… [+889 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Dan Avery\",\"title\":\"T-Rexes tore into each other's faces in vicious competitions for mates - Daily Mail\",\"description\":\"Scientists in Canada examined 200 tyrannosaur skulls and found similar scars in half of adults. They believe the dinos were biting each other on the face to establish dominance in ritual fights\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977773/T-Rexes-tore-faces-vicious-competitions-mates.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/17/47755865-0-image-a-4_1631289977994.jpg\",\"publishedAt\":\"2021-09-10T15:37:03Z\",\"content\":\"We know Tyrannosaurus rex was the terror of the Late Cretaceous, but this apex predator would also engage in dino flight clubs with other T-rexes, according to a new report.\\r\\nScientists in Canada exa… [+3848 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Margo Rosenbaum\",\"title\":\"What will the planet look like in 50 years? Here's how climate scientists figure it out - Phys.org\",\"description\":\"Climate change scientists don't like to use the term \\\"prediction.\\\" Rather, they're making \\\"projections\\\" about the future of the planet as sea levels rise, wildfires sweep the West and hurricanes become more ferocious.\",\"url\":\"https://phys.org/news/2021-09-planet-years-climate-scientists-figure.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2019/3-climate.jpg\",\"publishedAt\":\"2021-09-10T15:20:01Z\",\"content\":\"Climate change scientists don't like to use the term \\\"prediction.\\\" Rather, they're making \\\"projections\\\" about the future of the planet as sea levels rise, wildfires sweep the West and hurricanes beco… [+11387 chars]\"},{\"source\":{\"id\":null,\"name\":\"Inverse\"},\"author\":\"Passant Rabie\",\"title\":\"What happens if a solar storm hits Earth? 4 critical questions answered - Inverse\",\"description\":\"How would our modern day technology react to a geomagnetic storm?\",\"url\":\"https://www.inverse.com/science/a-recent-uptick-in-solar-storms-has-scientists-worried\",\"urlToImage\":\"https://imgix.bustle.com/uploads/image/2021/9/8/64fa77b3-fa08-4ce9-93f4-d2fa81701e29-solarfury_cover.jpg?w=1200&h=630&fit=crop&crop=faces&fm=jpg\",\"publishedAt\":\"2021-09-10T15:00:25Z\",\"content\":\"On August 26, \\r\\na flare-up erupted from the Sun and set off a solar tsunami.\\r\\nThis event sent a giant wave of hot particles flowing through the Solar System at speeds of up to 560 miles per hour, and… [+4006 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Chris Young\",\"title\":\"Quantum Gas Experiment Creates the Coldest Temperature Ever - Interesting Engineering\",\"description\":\"The researchers say their lab was momentarily one of the \\\"coldest places in the universe.\\\"\",\"url\":\"https://interestingengineering.com/quantum-gas-broke-the-low-temperature-record-after-a-sharp-free-fall\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/y5wWZ2lY6X/sizes/quantum-gas-broke-the-low-temperature-record-after-a-sharp-free-fall_md.jpg\",\"publishedAt\":\"2021-09-10T14:52:00Z\",\"content\":\"Physicists at the University of Bremen, Germany produced the coldest temperature ever recorded, an incredibly precisely measured 38 trillionths of a degree above absolute zero. They did so as part of… [+2893 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Mercury News\"},\"author\":\"CNN.com Wire Service\",\"title\":\"Growing risk of once-in-a-century solar superstorm that could knock out internet, study says - The Mercury News\",\"description\":\"Imagine if one day the internet was down not just in your neighborhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm. It sounds like science fiction, but a…\",\"url\":\"https://www.mercurynews.com/2021/09/10/growing-risk-of-once-in-a-century-solar-superstorm-that-could-knock-out-internet-study-says/\",\"urlToImage\":\"https://www.mercurynews.com/wp-content/uploads/2016/08/20120125__flare3.jpg?w=400&h=324\",\"publishedAt\":\"2021-09-10T14:44:47Z\",\"content\":\"TORONTO – Imagine if one day the internet was down not just in your neighborhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm.\\r\\nIt sounds like science ficti… [+9825 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Ameya Paleja\",\"title\":\"Physicists Say That a Fifth Dimension Could Be on the Horizon - Interesting Engineering\",\"description\":\"In the 1920s, the five-dimensional theory was proposed to explain how nature works, and now, a century later, the theory might be revived again.\",\"url\":\"https://interestingengineering.com/physicists-say-that-a-fifth-dimension-could-be-on-the-horizon\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/XD6KJrVzGv/sizes/physicists-say-that-a-fifth-dimension-could-be-on-the-horizon_md.jpg\",\"publishedAt\":\"2021-09-10T14:13:59Z\",\"content\":\"Scientists often get asked if they do new experiments in the lab or keep repeating older ones that they know the results for sure. While most scientists do the former, the progress of science also de… [+2632 chars]\"},{\"source\":{\"id\":null,\"name\":\"Euronews\"},\"author\":null,\"title\":\"Dutch scientist uncovers an old recording of a talking duck saying 'you bloody fool!' - Euronews\",\"description\":\"A Dutch scientist has uncovered old recordings of a musk duck mimicking the phrase \\\"You bloody fool!\\\" in an Australian accent.\",\"url\":\"https://www.euronews.com/next/2021/09/10/you-bloody-fool-scientists-uncover-an-old-recording-of-a-talking-duck-during-a-mating-disp\",\"urlToImage\":\"https://static.euronews.com/articles/stories/06/05/01/90/1000x563_cmsv2_e4480ef1-6f39-5655-90cb-ea2d67f70907-6050190.jpg\",\"publishedAt\":\"2021-09-10T14:13:54Z\",\"content\":\"A Dutch scientist has uncovered old recordings of a musk duck mimicking the phrase, \\\"You bloody fool!\\\" - learnt when it was raised by humans in an Australian bird park.\\r\\nLeiden University scientist C… [+1411 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Hayley Dunning\",\"title\":\"Insight into power generation in photosynthesis may lead to more resilient crops - Phys.org\",\"description\":\"A study into the energy-making process in plants could help engineer crops more resistant to stress or bacteria that produce pharmaceuticals.\",\"url\":\"https://phys.org/news/2021-09-insight-power-photosynthesis-resilient-crops.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/insight-into-power-gen.jpg\",\"publishedAt\":\"2021-09-10T14:04:44Z\",\"content\":\"A study into the energy-making process in plants could help engineer crops more resistant to stress or bacteria that produce pharmaceuticals.\\r\\nA team of researchers, led by Imperial College London an… [+4211 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Peter Genzer\",\"title\":\"Catalyst study advances carbon-dioxide-to-ethanol conversion - Phys.org\",\"description\":\"An international collaboration of scientists has taken a significant step toward the realization of a nearly \\\"green\\\" zero-net-carbon technology that will efficiently convert carbon dioxide, a major greenhouse gas, and hydrogen into ethanol, which is useful as…\",\"url\":\"https://phys.org/news/2021-09-catalyst-advances-carbon-dioxide-to-ethanol-conversion.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/2021/catalyst-study-advance.jpg\",\"publishedAt\":\"2021-09-10T13:55:07Z\",\"content\":\"An international collaboration of scientists has taken a significant step toward the realization of a nearly \\\"green\\\" zero-net-carbon technology that will efficiently convert carbon dioxide, a major g… [+5248 chars]\"},{\"source\":{\"id\":null,\"name\":\"Dailynewsegypt.com\"},\"author\":\"Daily News Egypt\",\"title\":\"Relatively massive animal species discovered in half-billion-year-old Burgess Shale - Daily News Egypt\",\"description\":\"massive animal - Royal Ontario Museum palaeontologists unearth one of largest radiodonts of Cambrian explosion\",\"url\":\"https://dailynewsegypt.com/2021/09/10/relatively-massive-animal-species-discovered-in-half-billion-year-old-burgess-shale/\",\"urlToImage\":\"https://dneegypt.nyc3.digitaloceanspaces.com/2021/09/Titanokorys_ROMIP_65745-1-scaled.jpeg\",\"publishedAt\":\"2021-09-10T13:52:47Z\",\"content\":\"Palaeontologists at the Royal Ontario Museum (ROM) have uncovered the remains of a huge new fossil species belonging to an extinct animal group in half-a-billion-year-old Cambrian rocks from Kootenay… [+3390 chars]\"},{\"source\":{\"id\":null,\"name\":\"KTVZ\"},\"author\":\"CNN Newsource\",\"title\":\"Growing risk of once-in-a-century solar superstorm that could knock out internet, study says - KTVZ\",\"description\":\"By Alexandra Mae Jones Click here for updates on this story     TORONTO (CTV Network) — Imagine if one day the internet was down not just in your neighbourhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm. It sounds …\",\"url\":\"https://ktvz.com/cnn-regional/2021/09/10/growing-risk-of-once-in-a-century-solar-superstorm-that-could-knock-out-internet-study-says/\",\"urlToImage\":\"https://ktvz.b-cdn.net/2021/06/KTVZ-USworld2.png\",\"publishedAt\":\"2021-09-10T13:43:39Z\",\"content\":\"By Alexandra Mae Jones\\r\\nClick here for updates on this story\\r\\n    TORONTO (CTV Network) — Imagine if one day the internet was down not just in your neighbourhood, but across the globe, knocked out by… [+9936 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Liu Jia\",\"title\":\"Scientists discover mechanism of bone growth - Phys.org\",\"description\":\"In a study published in Cell Stem Cell, the research group led by Dr. Zhou Bo from the Center for Excellence in Molecular Cell Science, Shanghai Institute of Biochemistry and Cell Biology of the Chinese Academy of Sciences reported the transition of skeletal …\",\"url\":\"https://phys.org/news/2021-09-scientists-mechanism-bone-growth.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/2021/scientists-discover-me.jpg\",\"publishedAt\":\"2021-09-10T13:40:01Z\",\"content\":\"In a study published in Cell Stem Cell, the research group led by Dr. Zhou Bo from the Center for Excellence in Molecular Cell Science, Shanghai Institute of Biochemistry and Cell Biology of the Chin… [+2751 chars]\"},{\"source\":{\"id\":null,\"name\":\"NASA\"},\"author\":null,\"title\":\"Hubble Captures a Sparkling Cluster - NASA\",\"description\":\"This star-studded image from the NASA/ESA Hubble Space Telescope depicts NGC 6717, which lies more than 20,000 light-years from Earth in the constellation Sagittarius.\",\"url\":\"https://www.nasa.gov/image-feature/goddard/2021/hubble-captures-a-sparkling-cluster/\",\"urlToImage\":\"http://www.nasa.gov/sites/default/files/thumbnails/image/potw2136a_0.jpg\",\"publishedAt\":\"2021-09-10T13:39:02Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Science X staff\",\"title\":\"Image: Volcanic trenches on Mars - Phys.org\",\"description\":\"This image of the young volcanic region of Elysium Planitia on Mars [10.3°N, 159.5°E] was taken on 14 April 2021 by the CaSSIS camera on the ESA-Roscosmos ExoMars Trace Gas Orbiter (TGO).\",\"url\":\"https://phys.org/news/2021-09-image-volcanic-trenches-mars.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/volcanic-trenches-on-m.jpg\",\"publishedAt\":\"2021-09-10T13:26:55Z\",\"content\":\"This image of the young volcanic region of Elysium Planitia on Mars [10.3°N, 159.5°E] was taken on 14 April 2021 by the CaSSIS camera on the ESA-Roscosmos ExoMars Trace Gas Orbiter (TGO).\\r\\nThe two bl… [+1423 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Ryan Morrison\",\"title\":\"Driveway where Winchcombe meteorite crashed will go on display - Daily Mail\",\"description\":\"The meteorite fell to Earth in a fireball seen from across the UK, tracked by doorbell cameras, eventually landing in the Cotswold town of Winchcombe back in February.\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977375/Driveway-Winchcombe-meteorite-crashed-display.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/14/47750397-0-image-a-39_1631281581750.jpg\",\"publishedAt\":\"2021-09-10T13:25:15Z\",\"content\":\"A piece of the driveway where an 'extremely rare' meteorite worth £100,000 crashed will go on display at the Natural History Museum along with the space rock.\\r\\nThe meteorite fell to Earth in a fireba… [+7185 chars]\"}]}";
            ResponseWorldNews = ResponseJSON;
            MyScienceNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
        }

        if(!fileExists(PubView.getContext(), "JSON_TECHNEWS_CACHE.json")) {
            ResponseJSON = "{\"status\":\"ok\",\"totalResults\":702,\"articles\":[{\"source\":{\"id\":\"the-wall-street-journal\",\"name\":\"The Wall Street Journal\"},\"author\":\"Tim Higgins\",\"title\":\"Apple Trial Ends in Mixed Verdict After Fortnite-Maker’s Monopoly Allegations - The Wall Street Journal\",\"description\":\"Case pitted Steve Jobs’s vision of a walled garden of Apple hardware against Epic co-founder Tim Sweeney’s desire for an open ecosystem\",\"url\":\"https://www.wsj.com/articles/apple-trial-ends-in-mixed-verdict-after-fortnite-makers-monopoly-allegations-11631289792\",\"urlToImage\":\"https://images.wsj.net/im-386969/social\",\"publishedAt\":\"2021-09-10T16:31:00Z\",\"content\":null},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Chaim Gartenberg\",\"title\":\"Vimeo adds support for Apple’s iPhone 12 Dolby Vision HDR videos - The Verge\",\"description\":\"Vimeo is adding support for Dolby Vision HDR videos shot with Apple’s iPhone 12 phones, making it the first video website to allow for easy sharing of the higher-quality video format from iPhones.\",\"url\":\"https://www.theverge.com/2021/9/10/22666520/vimeo-apple-iphone-12-pro-dolby-vision-hdr-videos-support\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/8VaEgoUodNr-QJKLnpLJLcWSbjs=/0x4:2050x1077/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22023094/vpavic_4279_20201107_0048.0.jpg\",\"publishedAt\":\"2021-09-10T15:48:35Z\",\"content\":\"A year later, theres finally somewhere to post your Dolby Vision HDR iPhone videos\\r\\nIf you buy something from a Verge link, Vox Media may earn a commission. See our ethics statement.\\r\\nPhoto by Vjeran… [+1789 chars]\"},{\"source\":{\"id\":null,\"name\":\"Digital Trends\"},\"author\":\"Jacob Roach\",\"title\":\"Why Windows 11 is more than just a Windows 10 reskin - Digital Trends\",\"description\":\"Windows 11 is built on the same foundation as Windows 10, but it's more than a visual update. In a new video, Microsoft explains why.\",\"url\":\"https://www.digitaltrends.com/computing/windows-11-optimizations-updates-cpu-drivers/\",\"urlToImage\":\"https://icdn.digitaltrends.com/image/digitaltrends/windows-11-lifestyle-feature.jpg\",\"publishedAt\":\"2021-09-10T15:45:45Z\",\"content\":\"Windows 11 is, for the most part, a visual update to Windows 10. It’s the same operating system under the hood, just with new features and an updated look. Despite being built on the same OS, Microso… [+2981 chars]\"},{\"source\":{\"id\":\"techradar\",\"name\":\"TechRadar\"},\"author\":\"John Loeffler\",\"title\":\"Apple loses Epic fight: app developers can now avoid App Store payments - TechRadar\",\"description\":\"This is a huge hit to Apple's bottom line\",\"url\":\"https://www.techradar.com/news/developers-can-now-evade-apples-in-app-payment-scheme-to-directly-sell-to-users\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/fWxXJzo6UpWj9RVFTFS5rJ-1200-80.jpg\",\"publishedAt\":\"2021-09-10T15:42:49Z\",\"content\":\"Apple was just hit with a permanent injunction by the judge in its legal battle with Epic Games, preventing the company from forcing developers to use the App Store's payment system for in-app purcha… [+2270 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Kif Leswing\",\"title\":\"Apple can no longer force developers to use in-app purchasing, judge rules in Epic Games case - CNBC\",\"description\":\"The trial took place in Oakland, California, in May, and included both company CEOs testifying in open court.\",\"url\":\"https://www.cnbc.com/2021/09/10/epic-games-v-apple-judge-reaches-decision-.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106940080-1631290244185-106940080-1631288561148-gettyimages-1233028006-APPLE-EPIC-TRIAL.jpg?v=1631290254\",\"publishedAt\":\"2021-09-10T15:36:06Z\",\"content\":\"Apple's lucrative App Store business received a major blow Friday thanks to a federal judge's decision in the company's legal battle with Epic Games.\\r\\nJudge Yvonne Gonzalez Rogers on Friday handed do… [+5386 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNA\"},\"author\":null,\"title\":\"US judge in 'Fortnite' case strikes down Apple's in-app payment restrictions - CNA\",\"description\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect payments in apps. The ruling is similar to a move that Apple ma…\",\"url\":\"https://www.channelnewsasia.com/business/us-judge-fortnite-case-strikes-down-apples-app-payment-restrictions-2170116\",\"urlToImage\":\"https://onecms-res.cloudinary.com/image/upload/s--0xar_RRy--/fl_relative%2Cg_south_east%2Cl_one-cms:core:watermark:reuters%2Cw_0.1/f_auto%2Cq_auto/c_fill%2Cg_auto%2Ch_676%2Cw_1200/v1/one-cms/core/2021-09-10t154906z_3_lynxmpeh890pr_rtroptp_3_apple-epic-games.jpg?itok=aiOLk8rM\",\"publishedAt\":\"2021-09-10T15:34:00Z\",\"content\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect pay… [+3342 chars]\"},{\"source\":{\"id\":null,\"name\":\"9to5Mac\"},\"author\":\"Michael Potuck\",\"title\":\"WhatsApp launching end-to-end encrypted cloud backups for iOS and Android - 9to5Mac\",\"description\":\"Following through with a long-requested feature from users, Facebook-owned WhatsApp is going to make cloud backups end-to-end encrypted to go along with the end-to-end encryption of the messages sent with the service. The app will offer the feature in “the co…\",\"url\":\"http://9to5mac.com/2021/09/10/whatsapp-end-to-end-encrypted-cloud-backups-ios-android/\",\"urlToImage\":\"https://i1.wp.com/9to5mac.com/wp-content/uploads/sites/6/2021/09/whatsapp-encrypted-backups-ios.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1\",\"publishedAt\":\"2021-09-10T15:28:00Z\",\"content\":\"Following through with a long-requested feature from users, Facebook-owned WhatsApp is going to make cloud backups end-to-end encrypted to go along with the end-to-end encryption of the messages sent… [+1802 chars]\"},{\"source\":{\"id\":\"the-times-of-india\",\"name\":\"The Times of India\"},\"author\":\"TIMESOFINDIA.COM\",\"title\":\"Instagram is testing favourite users feature: What it means, how it may affect your feed and more - Times of India\",\"description\":\"Instagram, the photo-sharing app owned by Facebook, is said to be testing a new feature that will allow its users to mark other users as their favouri\",\"url\":\"https://timesofindia.indiatimes.com/gadgets-news/instagram-is-testing-favourite-users-feature-what-it-means-how-it-may-affect-your-feed-and-more/articleshow/86097799.cms\",\"urlToImage\":\"https://static.toiimg.com/thumb/msid-86097792,width-1070,height-580,imgsize-27702,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg\",\"publishedAt\":\"2021-09-10T15:23:00Z\",\"content\":\"#Instagram is working on \\\"Favorites\\\" Posts from your favorites are shown higher in feed. https://t.co/NfBd8v4IHR\\r\\n— Alessandro Paluzzi (@alex193a) 1631184880000\"},{\"source\":{\"id\":\"ars-technica\",\"name\":\"Ars Technica\"},\"author\":\"Ron Amadeo\",\"title\":\"Google.com dark mode is rolling out to everyone - Ars Technica\",\"description\":\"Say goodbye to the blinding-white Google.com start page and search results.\",\"url\":\"https://arstechnica.com/gadgets/2021/09/google-com-dark-mode-is-rolling-out-to-everyone/\",\"urlToImage\":\"https://cdn.arstechnica.net/wp-content/uploads/2021/09/28-690x380.jpg\",\"publishedAt\":\"2021-09-10T15:21:59Z\",\"content\":\"63 with 49 posters participating\\r\\n<ul><li>\\r\\n Dark mode! It's finally here. \\r\\n</li><li>\\r\\n</li><li>\\r\\n To turn it on, go to the settings. On Google.com, the settings link is in the bottom-right corner. … [+1856 chars]\"},{\"source\":{\"id\":null,\"name\":\"Motor1 \"},\"author\":\"Anthony Alaniz\",\"title\":\"Honda S2000 Drag Races Acura Integra Type R in RWD-Vs-FWD Fight - Motor1 \",\"description\":\"Watch as the 2002 Honda S2000 is pitted against the 2000 Acura Integra Type R in a series of drag races.\",\"url\":\"https://www.motor1.com/news/532305/honda-s2000-races-acura-integra/\",\"urlToImage\":\"https://cdn.motor1.com/images/mgl/z68Zq/s1/honda-s2000-races-acura-integra-type-r.jpg\",\"publishedAt\":\"2021-09-10T15:17:00Z\",\"content\":\"The Hoonigan YouTube channel is a treasure trove of automotive fun, and the crews latest video is no different. The channel took a step back 20 years into a wildly different performance era, grabbing… [+1612 chars]\"},{\"source\":{\"id\":null,\"name\":\"Kotaku\"},\"author\":\"Ethan Gach\",\"title\":\"The Bloodborne of Samurai Games, Nioh, Is Free On PC - Kotaku\",\"description\":\"Make time for From Software's hack-and-slash game this weekend\",\"url\":\"https://kotaku.com/the-bloodborne-of-samurai-games-nioh-is-free-on-pc-1847651610\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/1c3c02dedf9e85e29f3c468ff5db7962.jpg\",\"publishedAt\":\"2021-09-10T15:15:00Z\",\"content\":\"Nioh takes the action of a Ninja Gaiden and the structure of a Dark Souls and marries them into something elegant, exceptional, and currently free for PC on the Epic Games Store.\\r\\nTeam Ninjas action … [+2121 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Andre Salles\",\"title\":\"How a state-of-the-art optics system will make the Advanced Photon Source upgrade possible - Phys.org\",\"description\":\"To provide X-ray beams that are both very bright and very tightly focused, an Argonne team had to create a new system of mirrors, lenses and equipment for the upgraded Advanced Photon Source.\",\"url\":\"https://phys.org/news/2021-09-state-of-the-art-optics-advanced-photon-source.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/through-the-looking-gl-1.jpg\",\"publishedAt\":\"2021-09-10T15:11:37Z\",\"content\":\"To provide X-ray beams that are both very bright and very tightly focused, an Argonne team had to create a new system of mirrors, lenses and equipment for the upgraded Advanced Photon Source.\\r\\nIn the… [+7313 chars]\"},{\"source\":{\"id\":null,\"name\":\"NDTV News\"},\"author\":\"Jagmeet Singh\",\"title\":\"WhatsApp End-to-End Encrypted Cloud Backups to Roll Out Soon for Android, iOS Users - Gadgets 360\",\"description\":\"WhatsApp on Friday announced that it will soon start rolling out end-to-end encrypted cloud backups on Android and iOS. The new move will allow users to secure their chat backups stored on Apple iCloud and Google Drive with end-to-end encryption — the same le…\",\"url\":\"https://gadgets.ndtv.com/apps/news/whatsapp-end-to-end-encrypted-backups-android-apple-rollout-starts-2536802\",\"urlToImage\":\"https://i.gadgets360cdn.com/large/whatsapp_end_to_end_encrypted_backups_image_1631286061066.jpg\",\"publishedAt\":\"2021-09-10T15:11:15Z\",\"content\":\"WhatsApp is set to soon roll out end-to-end encrypted cloud backups on Android and iOS. The new move will help users keep their chats end-to-end encrypted even when they are a part of WhatsApp backup… [+4275 chars]\"},{\"source\":{\"id\":null,\"name\":\"Forbes\"},\"author\":\"Erik Kain\",\"title\":\"FromSoftware Tweets Bizarre New ‘Elden Ring’ Image - Forbes\",\"description\":\"FromSoftware just tweeted a very peculiar image for the Japanese game developer’s upcoming action-RPG Elden Ring.\",\"url\":\"https://www.forbes.com/sites/erikkain/2021/09/10/fromsoftware-tweets-bizarre-new-elden-ring-image/\",\"urlToImage\":\"https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F613b73a4809e2ac104f345ef%2F0x0.jpg\",\"publishedAt\":\"2021-09-10T15:09:50Z\",\"content\":\"Elden Ring\\r\\nCredit: FromSoftware\\r\\nFromSoftware just tweeted a very peculiar image for the Japanese game developers upcoming action-RPG Elden Ring.\\r\\nIn the wake of the Shattering, a blight claimed the… [+971 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Alex Heath\",\"title\":\"WhatsApp is adding encrypted backups - The Verge\",\"description\":\"WhatsApp is rolling out end-to-end encrypted backups for chat logs on Android and iOS, protecting the file from anyone without the key.\",\"url\":\"https://www.theverge.com/2021/9/10/22665968/whatsapp-backups-end-to-end-encryption-ios-android\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/MD0eT-m-uto-M_MHFTRUrF_FHDk=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22245551/acastro_210119_1777_whatsapp_0003.jpg\",\"publishedAt\":\"2021-09-10T15:05:45Z\",\"content\":\"Going a step further than Apples iMessage\\r\\nIllustration by Alex Castro / The Verge\\r\\nWhatsApp will let its more than 2 billion users fully encrypt the backups of their messages, the Facebook-owned app… [+2774 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Brian Barrett\",\"title\":\"WhatsApp Fixes Its Biggest Encryption Loophole - WIRED\",\"description\":\"The ubiquitous messaging service will add end-to-end encryption to backups, keeping your chats safe no matter whose cloud they're stored in.\",\"url\":\"https://www.wired.com/story/whatsapp-end-to-end-encrypted-backups/\",\"urlToImage\":\"https://media.wired.com/photos/613a742118bb03f66e80f672/191:100/w_1280,c_limit/Security---WhatsApp-Encryption.jpg\",\"publishedAt\":\"2021-09-10T15:05:00Z\",\"content\":\"Few, if any, services have done more to bring secure messaging to more people than WhatsApp. Since 2016, the messaging platform has enabled end-to-end encryptionby default, no lessfor its billions of… [+2909 chars]\"},{\"source\":{\"id\":\"techcrunch\",\"name\":\"TechCrunch\"},\"author\":\"Manish Singh, Zack Whittaker\",\"title\":\"WhatsApp will finally let users encrypt their chat backups in the cloud - TechCrunch\",\"description\":\"The messaging app giant says it will offer users two ways to encrypt their cloud backups, and the feature is optional.\",\"url\":\"http://techcrunch.com/2021/09/10/whatsapp-encrypt-cloud-backup/\",\"urlToImage\":\"https://techcrunch.com/wp-content/uploads/2021/09/GettyImages-1234875399.jpg?w=600\",\"publishedAt\":\"2021-09-10T15:04:29Z\",\"content\":\"WhatsApp said on Friday it will give its two billion users the option to encrypt their chat backups to the cloud, taking a significant step to put a lid on one of the tricky ways private communicatio… [+4990 chars]\"},{\"source\":{\"id\":null,\"name\":\"Android Authority\"},\"author\":null,\"title\":\"Huawei schedules October event, likely for global launch of P50 series - Android Authority\",\"description\":\"Huawei scheduled an October launch event. The company doesn't say so, but it's very likely it's the global launch of the Huawei P50 series.\",\"url\":\"https://www.androidauthority.com/huawei-p50-global-launch-3019643/\",\"urlToImage\":\"https://cdn57.androidauthority.net/wp-content/uploads/2021/07/HUAWEI-P50-Series-Keynote-image-2-scaled.jpg\",\"publishedAt\":\"2021-09-10T15:01:27Z\",\"content\":\"<ul><li>There is a new Huawei event scheduled that is very likely the global launch of the Huawei P50 series.</li><li>The event happens on October 21 in Vienna.</li><li>Huawei already launched the ph… [+1373 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Ian Randall\",\"title\":\"Tech: iRobot's new £900 Roomba vacuum cleaner is trained to avoid dog and cat faeces - Daily Mail\",\"description\":\"The Roomba® j7+ from Massachussets-based iRobot can also avoid getting tangled in cables, automatically clean only when everyone has left the house and learn from the feedback you give it.\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977405/Tech-iRobots-new-900-Roomba-vacuum-cleaner-trained-avoid-dog-cat-faeces.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/17/47756929-0-image-a-2_1631290044645.jpg\",\"publishedAt\":\"2021-09-10T15:00:57Z\",\"content\":\"iRobot's new Roomba robotic vacuum cleaner now avoids cat and dog faeces after customers complained of previous models smearing them all over the house.\\r\\nThese 'poopocalypses' were a disgusting probl… [+7546 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Jay Peters\",\"title\":\"Amazon’s New World could come to its Luna cloud gaming service - The Verge\",\"description\":\"New World, Amazon Games’ upcoming MMO, could be offered on Amazon’s Luna cloud gaming service, Richard Lawrence, studio director at Amazon Games, said in an interview with The Verge.\",\"url\":\"https://www.theverge.com/2021/9/10/22663690/amazon-new-world-luna-cloud-gaming\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/9pjPmIzHHYGu0RH104mDqyyr9i0=/0x32:1920x1037/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22835708/gallery2.jpeg\",\"publishedAt\":\"2021-09-10T15:00:00Z\",\"content\":\"Im fairly confident that at some point youll also be seeing New World on Luna\\r\\nA screenshot from Amazon Games New World.\\r\\nImage: Amazon Games\\r\\nNew World, Amazon Games upcoming MMO, is just a few week… [+2308 chars]\"}]}";
            ResponseTechNews = ResponseJSON;
            MyTechnologyNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
        }
        if(!fileExists(PubView.getContext(), "JSON_BUSINESSNEWS_CACHE.json")) {
            ResponseJSON = "{\"status\":\"ok\",\"totalResults\":641,\"articles\":[{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Matt Mathers\",\"title\":\"UK economic recovery stalled drastically in July, ONS figures show - The Independent\",\"description\":\"Slump comes despite businesses reopening\",\"url\":\"https://www.independent.co.uk/news/business/uk-economy-covid-gdp-ons-b1917648.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/08/PA-61834127.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T16:30:42Z\",\"content\":\"Britains economic recovery from the Covid pandemic is beginning to taper off, with new figures showing GDP rose by just 0.1 per cent in July down from 1 per cent in the previous month.\\r\\nThe Office fo… [+3546 chars]\"},{\"source\":{\"id\":\"techcrunch\",\"name\":\"TechCrunch\"},\"author\":\"Ron Miller\",\"title\":\"DataRobot CEO Dan Wright coming to TC Sessions: SaaS to discuss role of data in machine learning - TechCrunch\",\"description\":\"Just about every company is sitting on vast amounts of data, which they can use to their advantage if they can just learn how to harness it. Data is actually the fuel for machine learning models, and with the proper tools, businesses can learn to process this…\",\"url\":\"http://techcrunch.com/2021/09/10/datarobot-ceo-dan-wright-coming-to-tc-sessions-saas-to-discuss-role-of-data-in-machine-learning/\",\"urlToImage\":\"https://techcrunch.com/wp-content/uploads/2021/09/datarobot_wp.png?w=711\",\"publishedAt\":\"2021-09-10T16:13:15Z\",\"content\":\"Just about every company is sitting on vast amounts of data, which they can use to their advantage if they can just learn how to harness it. Data is actually the fuel for machine learning models, and… [+2343 chars]\"},{\"source\":{\"id\":null,\"name\":\"Sky.com\"},\"author\":\"Alix Culbertson\",\"title\":\"HGV driving tests to be relaxed to help lorry driver shortages ahead of Christmas - Sky News\",\"description\":\"\",\"url\":\"https://news.sky.com/story/hgv-driving-tests-to-be-relaxed-to-help-lorry-driver-shortages-ahead-of-christmas-12404161\",\"urlToImage\":\"https://e3.365dm.com/21/07/1600x900/skynews-hgv-business-grant-shapps_5441499.jpg?20210708194840\",\"publishedAt\":\"2021-09-10T16:10:58Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Philippine Star\"},\"author\":\"Iris Gonzales\",\"title\":\"Index nears 7,000 as gov’t OKs new lockdown rules - Philstar.com\",\"description\":\"Share prices rebounded yesterday, with the main index closing in on the 7,000 level, as investors expected lighter quarantine restrictions by mid-September in NCR and adjacent provinces.\",\"url\":\"https://www.philstar.com/business/2021/09/11/2126281/index-nears-7000-govt-oks-new-lockdown-rules\",\"urlToImage\":\"https://media.philstar.com/photos/2021/09/10/stocks2021-08-1319-26-45_2021-09-10_19-45-15.jpg\",\"publishedAt\":\"2021-09-10T16:00:00Z\",\"content\":\"MANILA, Philippines — Share prices rebounded yesterday, with the main index closing in on the 7,000 level, as investors expected lighter quarantine restrictions by mid-September in NCR and adjacent p… [+1502 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn’t the only potential bad actor out there. Here’s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m… [+5151 chars]\"},{\"source\":{\"id\":\"financial-post\",\"name\":\"Financial Post\"},\"author\":\"Colin McClelland\",\"title\":\"$200 oil possible if climate change policies bring on 'energy starvation,' say energy insiders - Financial Post\",\"description\":\"'It's something that I think many of us, if not all of us, would not like to see happening in the market' — Oman’s energy minister\",\"url\":\"https://financialpost.com/commodities/energy/200-oil-possible-if-climate-change-policies-bring-on-energy-starvation-say-energy-insiders\",\"urlToImage\":\"https://smartcdn.prod.postmedia.digital/financialpost/wp-content/uploads/2021/09/oil0910.jpg\",\"publishedAt\":\"2021-09-10T15:43:39Z\",\"content\":\"'It's something that I think many of us, if not all of us, would not like to see happening in the market' Omans energy minister \\r\\nColin McClelland\\r\\nThe price of oil could shoot up to $100 or $200 a b… [+7700 chars]\"},{\"source\":{\"id\":null,\"name\":\"BBC News\"},\"author\":\"https://www.facebook.com/bbcnews\",\"title\":\"Food shortages could be permanent, warns industry body - BBC News\",\"description\":\"Shoppers are unlikely to have a wide range of products to choose from, due to supply chain issues.\",\"url\":\"https://www.bbc.com/news/business-58519997\",\"urlToImage\":\"https://ichef.bbci.co.uk/news/1024/branded_news/87E5/production/_120498743_food-shortage1.jpg\",\"publishedAt\":\"2021-09-10T15:43:34Z\",\"content\":\"By Esyllt CarrBusiness reporter, BBC News \\r\\nimage source, Getty Images\\r\\nLabour shortages in the food industry means consumers may not be able to find the products they like in supermarkets, an indust… [+3108 chars]\"},{\"source\":{\"id\":\"ars-technica\",\"name\":\"Ars Technica\"},\"author\":\"Jon Brodkin\",\"title\":\"SpaceX calls Amazon’s protest of Starlink plan an irrelevant “diatribe” - Ars Technica\",\"description\":\"SpaceX: \\\"Another week, another objection from Amazon against a competitor.\\\"\",\"url\":\"https://arstechnica.com/tech-policy/2021/09/spacex-calls-amazons-protest-of-starlink-plan-an-irrelevant-diatribe/\",\"urlToImage\":\"https://cdn.arstechnica.net/wp-content/uploads/2021/09/getty-amazon-warehouse-760x380.jpg\",\"publishedAt\":\"2021-09-10T15:42:23Z\",\"content\":\"Enlarge/ Amazon UK warehouse at Leeds Distribution Park on May 27, 2021, in Leeds, England. \\r\\n28 with 24 posters participating\\r\\nOn Thursday, SpaceX called Amazon's latest protest against Starlink pla… [+6232 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"July IIP sees slower YoY growth at 11.5% as low base effect wears off - Moneycontrol.com\",\"description\":\"Industrial production has almost caught up with pre-pandemic levels. Compared to July, 2019, production was just 0,3 percent lower in the latest month.\",\"url\":\"https://www.moneycontrol.com/news/business/economy/industrial-production-grows-11-5-in-july-7453961.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2017/03/metal-iron-steel-steel-furnace-mills-worker-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:42:21Z\",\"content\":\"As the low base effect slowly wears off, industrial production in India expanded by 11.5 percent year-on-year (YoY) in July, down from 13.6 percent in June.\\r\\nMeasured by the Index of Industrial Produ… [+3320 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"Amagi raises $100 million from Accel, Avataar Ventures and others - Moneycontrol\",\"description\":\"Amagi enables content owners to launch, distribute and monetise live linear channels on free-ad-supported television and video services platforms through a suite of solutions.\",\"url\":\"https://www.moneycontrol.com/news/business/amagi-raises-100-million-from-accel-avataar-ventures-and-others-7454311.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2021/03/fundraising_1-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:27:28Z\",\"content\":\"Amagi, a cloud SaaS technology provider for broadcast and streaming television, has raised over $100 million from investors such as Accel, Avataar Ventures, Norwest Venture Partners and existing inve… [+3408 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Cecilia D'Anastasio\",\"title\":\"Twitch Sues Users Over Alleged ‘Hate Raids’ Against Streamers - WIRED\",\"description\":\"The lawsuit accuses two anonymous users of “targeting black and LGBTQIA+ streamers with racist, homophobic, sexist and other harassing content” in violation of its terms of service.\",\"url\":\"https://www.wired.com/story/twitch-sues-users-over-alleged-hate-raids/\",\"urlToImage\":\"https://media.wired.com/photos/613b6d0e6ab67fe10ebfff58/191:100/w_1280,c_limit/Gaming-Twitch-lawsuire-1339094198.jpg\",\"publishedAt\":\"2021-09-10T15:19:00Z\",\"content\":\"Since early August, Twitch has been wrestling with an epidemic of harassment against marginalized streamers known as hate raids. These attacks spam streamers chats with hateful and bigoted language, … [+3837 chars]\"},{\"source\":{\"id\":\"reuters\",\"name\":\"Reuters\"},\"author\":null,\"title\":\"Commodity group Louis Dreyfus completes stake sale to ADQ - Reuters\",\"description\":\"Louis Dreyfus Company (LDC) said on Friday it had completed the sale of a 45% indirect stake to Abu Dhabi holding firm ADQ, marking the arrival of the first non-family shareholder in the agricultural commodity group's 170-year history.\",\"url\":\"https://www.reuters.com/world/middle-east/commodity-group-louis-dreyfus-completes-stake-sale-adq-2021-09-10/\",\"urlToImage\":\"https://www.reuters.com/pf/resources/images/reuters/reuters-default.png?d=52\",\"publishedAt\":\"2021-09-10T15:06:00Z\",\"content\":\"PARIS, Sept 10 (Reuters) - Louis Dreyfus Company (LDC) said on Friday it had completed the sale of a 45% indirect stake to Abu Dhabi holding firm ADQ, marking the arrival of the first non-family shar… [+2002 chars]\"},{\"source\":{\"id\":null,\"name\":\"Motley Fool\"},\"author\":\"Zhiyuan Sun\",\"title\":\"Why Bitcoin, Ethereum, and Dogecoin All Fell This Week - Motley Fool\",\"description\":\"The latest round of fear, uncertainty, and doubt in the crypto realm is beginning to materialize.\",\"url\":\"https://www.fool.com/investing/2021/09/10/why-bitcoin-ethereum-and-dogecoin-all-fell-this-we/\",\"urlToImage\":\"https://g.foolcdn.com/editorial/images/642629/gettyimages-1294303261.jpg\",\"publishedAt\":\"2021-09-10T14:59:52Z\",\"content\":\"What happened\\r\\nBitcoin (CRYPTO:BTC), Ethereum (CRYPTO:ETH), and Dogecoin (CRYPTO:DOGE) are down 9.38%, 14.56%, and 17.44% respectively in the past seven days as of 9:00 a.m. EDT. They are now trading… [+2275 chars]\"},{\"source\":{\"id\":\"fox-news\",\"name\":\"Fox News\"},\"author\":\"Kayla Rivas\",\"title\":\"FDA’s delayed decision on Juul e-cigarettes ‘reckless,’ pediatrics group says - Fox News\",\"description\":\"A delayed decision Thursday from the Food and Drug Administration (FDA) on whether to allow vaping brand Juul to stay on the market was met with strong criticism from the American Academy of Pediatrics.\",\"url\":\"https://www.foxnews.com/health/fda-delayed-decision-juul-reckless-pediatrics-group\",\"urlToImage\":\"https://static.foxnews.com/foxnews.com/content/uploads/2019/12/af92e85b-AP19346504360552.jpg\",\"publishedAt\":\"2021-09-10T14:55:49Z\",\"content\":\"A delayed decision Thursday from the Food and Drug Administration (FDA) on whether to allow vaping brand Juul to stay on the market was met with strong criticism from the American Academy of Pediatri… [+1874 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Michael Wayland\",\"title\":\"GM doubles chip shortage impact to 200,000 vehicles in second half of year, maintains guidance - CNBC\",\"description\":\"Despite the increased impact from the global chip shortage, GM CFO Paul Jacobson said the company is maintaining its guidance for 2021.\",\"url\":\"https://www.cnbc.com/2021/09/10/gm-increases-impact-of-chip-shortage-to-200000-vehicles-in-2nd-half.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106880615-1620662350453-gettyimages-1202279819-GM_LANSING.jpeg?v=1620662418\",\"publishedAt\":\"2021-09-10T14:46:38Z\",\"content\":\"General Motors' vehicle sales and production will be hit harder by the global chip shortage during the second half of the year than it previously expected, its finance chief said Friday.\\r\\nThe shortag… [+1868 chars]\"},{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Rob Merrick\",\"title\":\"Supermarket food shortages will be over by Christmas, Downing Street says - The Independent\",\"description\":\"Shoppers will enjoy a ‘normal Christmas’, Boris Johnson’s spokesman predicts – despite industry warning of ‘permanent’ gaps on shelves\",\"url\":\"https://www.independent.co.uk/news/uk/politics/food-shortages-brexit-covid-boris-johnson-b1917896.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/15/ECONOMY%20Shortages%20%2011185555.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T14:44:19Z\",\"content\":\"Supermarket food shortages will be over by Christmas, No 10 says rejecting industry warnings that shoppers must get used to permanent gaps on shelves.\\r\\nBoris Johnsons spokesman set up the hostage to … [+2474 chars]\"},{\"source\":{\"id\":null,\"name\":\"Jalopnik\"},\"author\":\"Adam Ismail\",\"title\":\"Now The Apple Watch Guy Is Overseeing The Apple Car - Jalopnik\",\"description\":\"Kevin Lynch will replace previous Apple Car lead Doug Field, who recently left for Ford.\",\"url\":\"https://jalopnik.com/now-the-apple-watch-guy-is-overseeing-the-apple-car-1847650113\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/14a8f6afee8d187c80db2a4e11f14b5b.jpg\",\"publishedAt\":\"2021-09-10T14:41:00Z\",\"content\":\"What do smartwatches and cars have in common? Aside from the fact Apple is trying to make both, Im not sure. The tech giants wearable software lead is taking the reins of its turbulent car project. I… [+7780 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Andrew J. Hawkins\",\"title\":\"Tesla Model S Plaid sets production EV record at Germany’s Nürburgring race course - The Verge\",\"description\":\"A Tesla Model S Plaid set a lap time of 7:30.909 minutes at Germany’s famous Nürburgring race track, setting a new record for production electric vehicles.\",\"url\":\"https://www.theverge.com/2021/9/10/22666576/tesla-model-s-plaid-nurburgring-ev-production-record\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/zMMV8rQAPZSVz10geLSMxxKPjNQ=/0x93:2416x1358/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839667/Screen_Shot_2021_09_10_at_9.53.20_AM.png\",\"publishedAt\":\"2021-09-10T14:40:34Z\",\"content\":\"Lap time of 7:30.909\\r\\nScreenshot: Touristen Niko\\r\\nTesla set a new lap record for a production electric vehicle at Germanys famed Nürburgring race course. A brand-new Model S Plaid completed the 12.9-… [+2233 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Ryan Browne\",\"title\":\"British fintechs are jumping into the booming buy now, pay later market - CNBC\",\"description\":\"Monzo and Revolut, two of Britain's best-known financial technology firms, are planning to enter the booming \\\"buy now, pay later\\\" industry.\",\"url\":\"https://www.cnbc.com/2021/09/10/monzo-and-revolut-to-enter-buy-now-pay-later-market.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/105966681-1560426803107monzocard1.jpg?v=1560427006\",\"publishedAt\":\"2021-09-10T14:33:39Z\",\"content\":\"LONDON Monzo and Revolut, two of Britain's best-known financial technology firms, are planning to enter the booming \\\"buy now, pay later\\\" industry.\\r\\nBuy now, pay later, or BNPL, plans are an increasin… [+2934 chars]\"},{\"source\":{\"id\":\"engadget\",\"name\":\"Engadget\"},\"author\":null,\"title\":\"Automakers dial up the wattage on the future of EVs at Munich's auto show - Engadget\",\"description\":\"Find the latest technology news and expert tech product reviews. Learn about the latest gadgets and consumer tech products for entertainment, gaming, lifestyle and more.\",\"url\":\"https://www.engadget.com/\",\"urlToImage\":null,\"publishedAt\":\"2021-09-10T14:31:09Z\",\"content\":\"Just enter your email and we'll take care of the rest:\\r\\nNow available on your smart speaker and wherever you get your podcasts:\"}]}";
            ResponseBusinessNews = ResponseJSON;
            MyBusinessNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
        }

        if(!fileExists(PubView.getContext(), "JSON_POLITICSNEWS_CACHE.json")) {
            ResponseJSON = "{\"status\":\"ok\",\"totalResults\":449,\"articles\":[{\"source\":{\"id\":\"the-wall-street-journal\",\"name\":\"The Wall Street Journal\"},\"author\":\"Tim Higgins\",\"title\":\"Apple Trial Ends in Mixed Verdict After Fortnite-Maker’s Monopoly Allegations - The Wall Street Journal\",\"description\":\"Case pitted Steve Jobs’s vision of a walled garden of Apple hardware against Epic co-founder Tim Sweeney’s desire for an open ecosystem\",\"url\":\"https://www.wsj.com/articles/apple-trial-ends-in-mixed-verdict-after-fortnite-makers-monopoly-allegations-11631289792\",\"urlToImage\":\"https://images.wsj.net/im-386969/social\",\"publishedAt\":\"2021-09-10T16:31:00Z\",\"content\":null},{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Matt Mathers\",\"title\":\"UK economic recovery stalled drastically in July, ONS figures show - The Independent\",\"description\":\"Slump comes despite businesses reopening\",\"url\":\"https://www.independent.co.uk/news/business/uk-economy-covid-gdp-ons-b1917648.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/08/PA-61834127.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T16:30:42Z\",\"content\":\"Britains economic recovery from the Covid pandemic is beginning to taper off, with new figures showing GDP rose by just 0.1 per cent in July down from 1 per cent in the previous month.\\r\\nThe Office fo… [+3546 chars]\"},{\"source\":{\"id\":null,\"name\":\"New York Times\"},\"author\":\"Jack Nicas\",\"title\":\"In Epic vs. Apple Court Fight, a Win for App Developers - The New York Times\",\"description\":\"The decision could have major implications for thousands of businesses that pay Apple billions of dollars each year.\",\"url\":\"https://www.nytimes.com/2021/09/10/technology/epic-apple-app-developers.html\",\"urlToImage\":\"https://static01.nyt.com/images/2021/09/10/business/10epicapple-promo/10epicapple-promo-facebookJumbo.jpg\",\"publishedAt\":\"2021-09-10T16:26:07Z\",\"content\":\"The decision could have a major ripple effect across the digital economy. If Epic prevails after expected appeals, companies would have a new way to avoid the App Store commission, which runs as high… [+1112 chars]\"},{\"source\":{\"id\":null,\"name\":\"Sky.com\"},\"author\":\"Alix Culbertson\",\"title\":\"HGV driving tests to be relaxed to help lorry driver shortages ahead of Christmas - Sky News\",\"description\":\"\",\"url\":\"https://news.sky.com/story/hgv-driving-tests-to-be-relaxed-to-help-lorry-driver-shortages-ahead-of-christmas-12404161\",\"urlToImage\":\"https://e3.365dm.com/21/07/1600x900/skynews-hgv-business-grant-shapps_5441499.jpg?20210708194840\",\"publishedAt\":\"2021-09-10T16:10:58Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Herald Sun\"},\"author\":\"Chris Cavanagh and Lachie Young\",\"title\":\"Player ratings: Which Geelong flop scored zero? - Herald Sun\",\"description\":\"<p>Melbourne captain Max Gawn says his side &ldquo;haven&rsquo;t done anything&rdquo; until they have broken the Demons&rsquo; 57-year premiership drought. </p>\",\"url\":\"https://www.heraldsun.com.au/sport/afl/afl-preliminary-final-melbourne-v-geelong-who-starred-who-flopped-and-every-player-rated/news-story/3cc4bfb7c089a9f4faed61f3b15074c9\",\"urlToImage\":\"https://content.api.news/v3/images/bin/9e5894a000975feb985c29f693a6d4eb\",\"publishedAt\":\"2021-09-10T16:09:15Z\",\"content\":\"It was an annihilation. Geelongs most experienced players failed to show up. Their most prolific didnt deliver. One in particular was abysmal. PLAYER RATINGS.Melbourne captain Max Gawn says his side … [+1506 chars]\"},{\"source\":{\"id\":null,\"name\":\"3dnatives\"},\"author\":null,\"title\":\"The First Ever 3D Printed Stellar Nurseries to be Used to Study Star Creation - 3Dnatives\",\"description\":\"The latest news on 3D Printing and Additive Manufacturing technologies. Find the best stories, prices, interviews, market reports and tests on 3Dnatives\",\"url\":\"https://www.3dnatives.com/en/\",\"urlToImage\":\"https://www.3dnatives.com/en/wp-content/uploads/sites/2/logo_3Dnatives2017_carre.png\",\"publishedAt\":\"2021-09-10T16:05:10Z\",\"content\":\"3Dnatives is the largest international online media platform on 3D printing and its applications. With its in-depth analysis of the market, 3Dnatives gets over 1 million unique visitors per month and… [+276 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Aaron Gregg\",\"title\":\"DoorDash, Grubhub, UberEats sue New York City over fee caps - The Washington Post\",\"description\":\"The coalition of popular food delivery apps says the law infringes on their private contracts with food sellers.\",\"url\":\"https://www.washingtonpost.com/business/2021/09/10/doordash-grubhub-new-york-city-lawsuit/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/4HOQGKAGW4I6ZM6EYRRLD3OPZA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T15:48:00Z\",\"content\":\"In a lawsuit filed late Thursday in the Southern District of New York, the parent companies of six widely used apps DoorDash, Caviar, Grubhub, Seamless, Postmates and UberEats accused the city of imp… [+139 chars]\"},{\"source\":{\"id\":null,\"name\":\"NDTV News\"},\"author\":null,\"title\":\"Brother Of Former Afghan Vice-President Executed By Taliban, Says Family - NDTV\",\"description\":\"The Taliban have executed the brother of Amrullah Saleh, the former Afghan vice president who became one of the leaders of anti-Taliban opposition forces in the Panjshir valley, his nephew said on Friday.\",\"url\":\"https://www.ndtv.com/world-news/former-afghan-vice-president-amrullah-salehs-brother-rohullah-azizi-executed-by-taliban-says-family-2536844\",\"urlToImage\":\"https://c.ndtvimg.com/2021-08/3l70u5t4_amrullah-saleh-reuters_625x300_17_August_21.jpg\",\"publishedAt\":\"2021-09-10T15:46:07Z\",\"content\":\"Amrullah Saleh became one of the leaders of anti-Taliban opposition forces in the Panjshir valley\\r\\nThe Taliban have executed the brother of Amrullah Saleh, the former Afghan vice president who became… [+1156 chars]\"},{\"source\":{\"id\":\"news24\",\"name\":\"News24\"},\"author\":\"Sesona Ngqakamba\",\"title\":\"JUST IN | Crash of several SA government websites 'not due to cyber-attacks' - News24\",\"description\":\"Several government websites, with the gov.za domain, were down on Friday due to fibre cable damage.\",\"url\":\"https://www.news24.com/news24/southafrica/news/just-in-crash-of-several-sa-government-websites-not-due-to-cyber-attacks-20210910\",\"urlToImage\":\"https://cdn.24.co.za/files/Cms/General/d/11566/df55825b175c4e05969fbf3ea4c4ff0c.jpg\",\"publishedAt\":\"2021-09-10T15:45:53Z\",\"content\":\"<ul><li>According to SITA, the issue was caused by fibre cable damage.</li><li>The websites were unreachable for several hours on Friday.</li><li>SITA said it expected services to be restored soon.  … [+1959 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn’t the only potential bad actor out there. Here’s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m… [+5151 chars]\"},{\"source\":{\"id\":\"the-times-of-india\",\"name\":\"The Times of India\"},\"author\":\"Chidanand Rajghatta\",\"title\":\"US dodges a 9/11 poke in the eye by Taliban - Times of India\",\"description\":\"South Asia News: The US on Friday dodged the ignominy of having Taliban install its government on the 20th anniversary of 9/11 after Russia reportedly rained on the ex\",\"url\":\"https://timesofindia.indiatimes.com/world/south-asia/us-dodges-a-9/11-poke-in-the-eye-by-taliban/articleshow/86098077.cms\",\"urlToImage\":\"https://static.toiimg.com/thumb/msid-86098076,width-1070,height-580,imgsize-33954,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg\",\"publishedAt\":\"2021-09-10T15:44:00Z\",\"content\":\"Taliban's new government will upset US, China, India and IranA week ago, anxious Afghans and credulous Biden administration officials were trying to take comfort in reports that Mullah Abdul Ghani Ba… [+151 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"\",\"title\":\"Biden Brushes Off GOP Criticism Of Vaccination Mandate for Teachers - NPR\",\"description\":\"President Biden said he was saddened that some GOP governors are threatening to sue over the new mandates. To them, he said, \\\"Have at it.\\\"\",\"url\":\"https://www.npr.org/2021/09/10/1035869847/biden-brushes-off-gop-threats-to-sue-over-vaccine-mandates\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/10/ap21253539854160_wide-daa8827afff8aca7389512b65f0481a04e13b68b.jpg?s=1400\",\"publishedAt\":\"2021-09-10T15:43:50Z\",\"content\":\"President Biden tours Brookland Middle School in Washington, D.C., on Friday. Biden has encouraged every school district to promote vaccines, including with on-site clinics, to protect students as th… [+2918 chars]\"},{\"source\":{\"id\":\"techradar\",\"name\":\"TechRadar\"},\"author\":\"John Loeffler\",\"title\":\"Apple loses Epic fight: app developers can now avoid App Store payments - TechRadar\",\"description\":\"This is a huge hit to Apple's bottom line\",\"url\":\"https://www.techradar.com/news/developers-can-now-evade-apples-in-app-payment-scheme-to-directly-sell-to-users\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/fWxXJzo6UpWj9RVFTFS5rJ-1200-80.jpg\",\"publishedAt\":\"2021-09-10T15:42:49Z\",\"content\":\"Apple was just hit with a permanent injunction by the judge in its legal battle with Epic Games, preventing the company from forcing developers to use the App Store's payment system for in-app purcha… [+2270 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"July IIP sees slower YoY growth at 11.5% as low base effect wears off - Moneycontrol.com\",\"description\":\"Industrial production has almost caught up with pre-pandemic levels. Compared to July, 2019, production was just 0,3 percent lower in the latest month.\",\"url\":\"https://www.moneycontrol.com/news/business/economy/industrial-production-grows-11-5-in-july-7453961.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2017/03/metal-iron-steel-steel-furnace-mills-worker-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:42:21Z\",\"content\":\"As the low base effect slowly wears off, industrial production in India expanded by 11.5 percent year-on-year (YoY) in July, down from 13.6 percent in June.\\r\\nMeasured by the Index of Industrial Produ… [+3320 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Straits Times\"},\"author\":\"ISABELLE LIEW\",\"title\":\"S'pore reports 568 new local Covid-19 cases and 1 death - The Straits Times\",\"description\":\"Of the locally transmitted cases, 127 were seniors above the age of 60.. Read more at straitstimes.com.\",\"url\":\"https://www.straitstimes.com/singapore/health/568-new-locally-transmitted-covid-19-cases-in-spore-partially-vaccinated-80-year\",\"urlToImage\":\"https://www.straitstimes.com/s3/files/styles/x_large/public/articles/2021/09/10/dw-moh-update-210910.jpg?itok=LIILAcED\",\"publishedAt\":\"2021-09-10T15:36:47Z\",\"content\":\"SINGAPORE - Singapore reported 568 locally transmitted Covid-19 cases and five imported ones on Friday (Sept 10), the Ministry of Health (MOH) said in its daily update.\\r\\nThis makes a total of 573 new… [+2620 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Kif Leswing\",\"title\":\"Apple can no longer force developers to use in-app purchasing, judge rules in Epic Games case - CNBC\",\"description\":\"The trial took place in Oakland, California, in May, and included both company CEOs testifying in open court.\",\"url\":\"https://www.cnbc.com/2021/09/10/epic-games-v-apple-judge-reaches-decision-.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106940080-1631290244185-106940080-1631288561148-gettyimages-1233028006-APPLE-EPIC-TRIAL.jpg?v=1631290254\",\"publishedAt\":\"2021-09-10T15:36:06Z\",\"content\":\"Apple's lucrative App Store business received a major blow Friday thanks to a federal judge's decision in the company's legal battle with Epic Games.\\r\\nJudge Yvonne Gonzalez Rogers on Friday handed do… [+5386 chars]\"},{\"source\":{\"id\":\"news-com-au\",\"name\":\"News.com.au\"},\"author\":null,\"title\":\"Jett Kenny reveals sister’s final moments - NEWS.com.au\",\"description\":\"<p>Jett Kenny has shared the heartbreaking final moments of his sister, Jaimi, who died a year ago aged just 33.</p>\",\"url\":\"https://www.news.com.au/entertainment/celebrity-life/jett-kenny-reveals-sisters-final-moments/news-story/b54489bfbc078ba15ee60ac9a06449dd\",\"urlToImage\":\"https://content.api.news/v3/images/bin/7053a0fc356a850f6931a7b0d92454c0\",\"publishedAt\":\"2021-09-10T15:35:59Z\",\"content\":\"Jett Kenny has opened up about losing his sister, Jaimi, telling how she fought for hours after her life support machine was switched off.Jett Kenny has shared the heartbreaking final moments of his … [+4236 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNA\"},\"author\":null,\"title\":\"US judge in 'Fortnite' case strikes down Apple's in-app payment restrictions - CNA\",\"description\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect payments in apps. The ruling is similar to a move that Apple ma…\",\"url\":\"https://www.channelnewsasia.com/business/us-judge-fortnite-case-strikes-down-apples-app-payment-restrictions-2170116\",\"urlToImage\":\"https://onecms-res.cloudinary.com/image/upload/s--0xar_RRy--/fl_relative%2Cg_south_east%2Cl_one-cms:core:watermark:reuters%2Cw_0.1/f_auto%2Cq_auto/c_fill%2Cg_auto%2Ch_676%2Cw_1200/v1/one-cms/core/2021-09-10t154906z_3_lynxmpeh890pr_rtroptp_3_apple-epic-games.jpg?itok=aiOLk8rM\",\"publishedAt\":\"2021-09-10T15:34:00Z\",\"content\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect pay… [+3342 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Erica Orden, CNN\",\"title\":\"Giuliani associate Igor Fruman pleads guilty to solicitation of a contribution by a foreign national - CNN\",\"description\":\"Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\",\"url\":\"https://www.cnn.com/2021/09/10/politics/igor-fruman-plea/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/191023132912-01-igor-fruman-1023-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:31:00Z\",\"content\":\"New York (CNN)Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\\r\\n… [+2400 chars]\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Deepika Padukone hesitates before explaining extent of her depression to Amitabh Bachchan on Kaun Banega Crorepati - Hindustan Times\",\"description\":\"Kaun Banega Crorepati 13: Deepika Padukone hesitated before confessing to Amitabh Bachchan that there was a time when she didn't feel like living.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/deepika-padukone-hesitates-before-explaining-extent-of-her-depression-to-amitabh-bachchan-on-kaun-banega-crorepati-101631274385385.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/deepika_padukone_amitabh_bachchan_kbc_1631275485941_1631275494835.jpeg\",\"publishedAt\":\"2021-09-10T15:19:58Z\",\"content\":\"Actor Deepika Padukone spoke about her depression in an appearance on Kaun Banega Crorepati 13, as host Amitabh Bachchan listened. Deepika and Farah Khan will appear as celebrity guests on Friday's e… [+1609 chars]\"}]}";
            ResponsePoliticsNews = ResponseJSON;
            MyPoliticsNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());

        }
        if(!fileExists(PubView.getContext(), "JSON_ENTERTAINMENTNEWS_CACHE.json")) {
            ResponseJSON = "{\"status\":\"ok\",\"totalResults\":673,\"articles\":[{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Haroon Siddique, Rowena Mason\",\"title\":\"BLM gives cautious welcome to Queen’s reported backing - The Guardian\",\"description\":\"Anti-racism movement says ‘actions speak louder than words’ after comments attributing royal assent\",\"url\":\"https://amp.theguardian.com/world/2021/sep/10/blm-gives-cautious-welcome-to-queens-reported-backing\",\"urlToImage\":\"https://i.guim.co.uk/img/media/52e28c423b3960da099c907b62cd3b31d992f5b2/0_91_800_480/master/800.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&enable=upscale&s=214e28b2b1f02b34a5b67232ee9fa20d\",\"publishedAt\":\"2021-09-10T16:03:00Z\",\"content\":\"Black Lives Matter UK has expressed surprise after the Queen and the royal family were said to support its cause, but the anti-racism movement stressed that actions speak louder than words.\\r\\nSir Ken … [+3169 chars]\"},{\"source\":{\"id\":null,\"name\":\"Gizmodo.com\"},\"author\":\"James Whitbrook, Rob Bricken, Cheryl Eddy, Germain Lussier, Charles Pulliam-Moore\",\"title\":\"2021 Fall TV Preview: Sci-Fi, Fantasy, and Horror Shows to Get Excited For - Gizmodo\",\"description\":\"From sci-fi bounty hunting to epic fantasy, this autumn's going to be a great time to stay inside for new and returning television.\",\"url\":\"https://gizmodo.com/2021-fall-tv-preview-sci-fi-fantasy-and-horror-shows-1847613798\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/04f981041b9a21c728c25ffca20c5394.png\",\"publishedAt\":\"2021-09-10T16:00:00Z\",\"content\":\"Summer has passed us by, and while it was far from the kind of summer most people expected for 2021for pop culture or otherwisenow that the nights are drawing in theres a whole plethora of genre tele… [+23852 chars]\"},{\"source\":{\"id\":\"polygon\",\"name\":\"Polygon\"},\"author\":\"Roxana Hadadi\",\"title\":\"Kate review: A dumb Netflix action movie elevated by Mary Elizabeth Winstead - Polygon\",\"description\":\"The fight scenes in Netflix’s Kate, handled by Birds of Prey, John Wick, and Black Panther stunt coordinator Jonathan Eusebio and executed by Mary Elizabeth Winstead, almost justify the movie. But the clumsy writing and extremely predictable setup and plot ho…\",\"url\":\"https://www.polygon.com/22666743/kate-review-netflix-mary-elizabeth-winstead\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/4VAEjfAKth9BAecqYsJlml00URM=/576x0:5569x2614/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22840011/KATE_20191111_08777_RC.jpg\",\"publishedAt\":\"2021-09-10T15:56:50Z\",\"content\":\"Whoever makes the next Alien sequel or spinoff should consider casting Mary Elizabeth Winstead as the successor of Sigourney Weavers character, Ellen Ripley. Since her breakthrough in Scott Pilgrim v… [+7361 chars]\"},{\"source\":{\"id\":null,\"name\":\"BuzzFeed News\"},\"author\":\"Ikran Dahir\",\"title\":\"Meet The TikToker That Guessed Kylie Jenner's Pregnancy - BuzzFeed News\",\"description\":\"“So when are all ya’ll manicure detectives starting your new gigs with the FBI?”\",\"url\":\"https://www.buzzfeednews.com/article/ikrd/tiktoker-guessed-kylie-jenners-pregnancy\",\"urlToImage\":\"https://img.buzzfeed.com/buzzfeed-static/static/2021-09/10/16/campaign_images/2d2cae2dbbe1/meet-the-tiktoker-who-correctly-guessed-kylie-jen-2-495-1631290697-23_dblbig.jpg?resize=1200:*\",\"publishedAt\":\"2021-09-10T15:52:00Z\",\"content\":\"In July, a TikToker named Tiffany ONeill posted a video predicting that Kylie Jenner was pregnant.\\r\\nThe next month, she posted another video, this one more in-depth, in which she argued fans could de… [+2214 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Guardian staff reporter\",\"title\":\"The Barber of Seville review – creaky WNO production heralds welcome return - The Guardian\",\"description\":\"The 35-year-old production has lost much of its stylish Italianate precision but Heather Lowe’s Rosina impressed, and other soloists kept the comedy lively\",\"url\":\"https://amp.theguardian.com/music/2021/sep/10/the-barber-of-seville-review-wno-millenium-centre-cardiff\",\"urlToImage\":\"https://i.guim.co.uk/img/media/0311e0093f9a8252653865968bb6ba94c5e11e64/165_212_4730_2839/master/4730.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctcmV2aWV3LTMucG5n&enable=upscale&s=bfb6bfbc33e329c626cb3f8a91e38090\",\"publishedAt\":\"2021-09-10T15:45:00Z\",\"content\":\"Welsh National Opera have been so noticeably absent from their main stages for a long 18 months Julys small-scale Alices Adventures in Wonderland was a National Trust country garden affair their retu… [+1989 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Marianne Garvey, CNN\",\"title\":\"Howard Stern to anti-vaxxers: 'You had the cure and you wouldn't take it' - CNN\",\"description\":\"Howard Stern is over the Covid-19 pandemic and is taking on anti-vaxxers who refuse to get the shot.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/howard-stern-vaccine-comments/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/201208123245-01-howard-stern-file-2015-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:39:00Z\",\"content\":null},{\"source\":{\"id\":\"news-com-au\",\"name\":\"News.com.au\"},\"author\":null,\"title\":\"Jett Kenny reveals sister’s final moments - NEWS.com.au\",\"description\":\"<p>Jett Kenny has shared the heartbreaking final moments of his sister, Jaimi, who died a year ago aged just 33.</p>\",\"url\":\"https://www.news.com.au/entertainment/celebrity-life/jett-kenny-reveals-sisters-final-moments/news-story/b54489bfbc078ba15ee60ac9a06449dd\",\"urlToImage\":\"https://content.api.news/v3/images/bin/7053a0fc356a850f6931a7b0d92454c0\",\"publishedAt\":\"2021-09-10T15:35:59Z\",\"content\":\"Jett Kenny has opened up about losing his sister, Jaimi, telling how she fought for hours after her life support machine was switched off.Jett Kenny has shared the heartbreaking final moments of his … [+4236 chars]\"},{\"source\":{\"id\":null,\"name\":\"Deadline\"},\"author\":\"Nancy Tartaglione\",\"title\":\"‘Shang-Chi’ China Release Unlikely In Wake Of Unearthed Comments By Star Simu Liu; ‘The Eternals’ Hopes In Question - Deadline\",\"description\":\"The prospects for Disney/Marvel’s Shang-Chi And The Legend Of The Ten Rings getting a China release date are dwindling. This in the wake of comments originally made by star Simu Liu in 2017 which resurfaced this week and have made waves on Chinese social medi…\",\"url\":\"https://deadline.com/2021/09/shang-chi-china-release-simu-liu-marvel-1234830474/\",\"urlToImage\":\"https://deadline.com/wp-content/uploads/2021/09/shang-chi-e1630503940200.jpg?w=1024\",\"publishedAt\":\"2021-09-10T15:30:00Z\",\"content\":\"The prospects for Disney/Marvel’s Shang-Chi And The Legend Of The Ten Rings getting a China release date are dwindling. This in the wake of comments originally made by star Simu Liu in 2017 which res… [+1564 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNET\"},\"author\":\"Joan E. Solsman\",\"title\":\"HBO Max is streaming Malignant (but not Shang-Chi nor Candyman, sorry) - CNET\",\"description\":\"HBO Max is streaming  Malignant, this weekend's biggest new movie in theaters -- but you won't have the same luck trying to stream Shang-Chi or Candyman.\",\"url\":\"https://www.cnet.com/tech/services-and-software/hbo-max-is-streaming-malignant-but-not-shang-chi-or-candyman-sorry/\",\"urlToImage\":\"https://www.cnet.com/a/img/WySKkRHbRFtLsy3FgQL4-9jnGyU=/0x189:4000x2435/1200x630/2020/06/14/8498dd90-a776-4cec-8c6e-b313c17397ac/hbo-max-logo-phone-2773.jpg\",\"publishedAt\":\"2021-09-10T15:28:30Z\",\"content\":\"Currently, HBO Max streams new movies from Warner Bros. the same day they're released in theaters. \\r\\nAngela Lang/CNET\\r\\nHBO Max has become synonymous with streaming new theatrical movies, thanks to an… [+6375 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Punch\"},\"author\":\"Agency Report\",\"title\":\"Queen Elizabeth supports ‘Black Lives Matter’ – Official - Punch Newspapers\",\"description\":\"The Queen and the royal family are supporters of the Black Lives Matter movement, one of Her Majesty’s representatives has said.\",\"url\":\"https://punchng.com/queen-elizabeth-supports-black-lives-matter-official/\",\"urlToImage\":\"https://cdn.punchng.com/wp-content/uploads/2021/09/10172121/000_19Z13U-e1631287278290.jpg\",\"publishedAt\":\"2021-09-10T15:23:58Z\",\"content\":\"The Queen and the royal family are supporters of the Black Lives Matter movement, one of Her Majestys representatives has said.\\r\\nThe first black Lord-Lieutenant for London, Sir Ken Olisa, revealed to… [+1405 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":null,\"title\":\"'I can barely hold a drumstick': Phil Collins details health struggles - CNN\",\"description\":\"Phil Collins says he is no longer able to play the drums due to health issues. The musician, who is 70, appeared on BBC Breakfast and said his son, Nic Collins, will be drumming with the band going forward. HLN's Melissa Knowles reports.\",\"url\":\"https://www.cnn.com/videos/us/2021/09/10/genesis-phil-collins-health-issues-mpx-vpx.cnn\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/150630153258-phil-collins-2010-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:21:44Z\",\"content\":\"Morning Express with Robin Meade\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Deepika Padukone hesitates before explaining extent of her depression to Amitabh Bachchan on Kaun Banega Crorepati - Hindustan Times\",\"description\":\"Kaun Banega Crorepati 13: Deepika Padukone hesitated before confessing to Amitabh Bachchan that there was a time when she didn't feel like living.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/deepika-padukone-hesitates-before-explaining-extent-of-her-depression-to-amitabh-bachchan-on-kaun-banega-crorepati-101631274385385.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/deepika_padukone_amitabh_bachchan_kbc_1631275485941_1631275494835.jpeg\",\"publishedAt\":\"2021-09-10T15:19:58Z\",\"content\":\"Actor Deepika Padukone spoke about her depression in an appearance on Kaun Banega Crorepati 13, as host Amitabh Bachchan listened. Deepika and Farah Khan will appear as celebrity guests on Friday's e… [+1609 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Punch\"},\"author\":\"Victoria Edeme\",\"title\":\"BBNaija S6: Why I was happy Saskay got flowers from Jaypaul – Cross - Punch Newspapers\",\"description\":\"Big Brother Naija Shine Ya Eye housemate, Cross, has stated that he was happy when Saskay got flowers from Japypaul.\",\"url\":\"https://punchng.com/bbnaija-s6-why-i-was-happy-saskay-got-flowers-from-jaypaul-cross/\",\"urlToImage\":\"https://cdn.punchng.com/wp-content/uploads/2021/08/19185339/20210819_175228_0000.png\",\"publishedAt\":\"2021-09-10T15:11:15Z\",\"content\":\"Big Brother Naija Shine Ya Eye housemate, Cross, has stated that he was happy when Saskay got flowers from Japypaul even though she was his love interest.\\r\\nCross made this known in a Diary Session on… [+971 chars]\"},{\"source\":{\"id\":null,\"name\":\"REVOLT TV\"},\"author\":\"Tamantha Gunn\",\"title\":\"Black Twitter reacts to Chlöe Bailey’s new music video for “Have Mercy” - REVOLT TV\",\"description\":\"“Chloe is truly Destiny’s child,” tweeted one person.\",\"url\":\"https://www.revolt.tv/news/2021/9/10/22666604/twitter-reactions-chloe-baileys-have-mercy\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/FOf88MiIjWn_GjIVJhscaIPoR2I=/0x0:3978x2083/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839689/chloe_bailey.jpg\",\"publishedAt\":\"2021-09-10T15:09:25Z\",\"content\":\"Chlöe Bailey has finally dropped the highly anticipated video for her new single Have Mercy and Black Twitter is in shambles. \\r\\nBailey first teased the track back in July while celebrating her 23rd b… [+2442 chars]\"},{\"source\":{\"id\":null,\"name\":\"Page Six\"},\"author\":\"Hannah Southwick\",\"title\":\"Kylie Jenner bares bump in sheer lace jumpsuit at NYFW - Page Six\",\"description\":\"The makeup mogul knows how to show off her bump in style. While attending a NYFW show at the Empire State Building, she rocked a barely-there lace look.\",\"url\":\"https://pagesix.com/2021/09/10/kylie-jenner-bares-bump-in-sheer-lace-jumpsuit-at-nyfw/\",\"urlToImage\":\"https://pagesix.com/wp-content/uploads/sites/3/2021/09/kylie-jenner-nyfw-67.jpg?quality=90&strip=all&w=1200\",\"publishedAt\":\"2021-09-10T15:04:00Z\",\"content\":\"Kylie Jenner is one hot mama.\\r\\nJust two days after revealing her pregnancy, the makeup mogul bared her bump at New York Fashion Week on Thursday in a sheer lace jumpsuit.\\r\\nHer curve-hugging LaQuan Sm… [+1490 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Owen Tonks\",\"title\":\"Mel B hints that Victoria Beckham could REUNITE with the Spice Girls - Daily Mail\",\"description\":\"The singer, 46, was a guest host of Steph's Packed Lunch in honour of Channel 4's Black To Front Day, and hinted that all the girls were finally  'on the same page together' with their tour plans.\",\"url\":\"https://www.dailymail.co.uk/tvshowbiz/article-9977781/Mel-B-hints-Victoria-Beckham-REUNITE-Spice-Girls.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/15/47749203-0-image-a-90_1631283183015.jpg\",\"publishedAt\":\"2021-09-10T14:56:27Z\",\"content\":\"Mel B has hinted that Victoria Beckham could join the Spice Girls for a reunion tour as she dropped the date of 2023 for another round of comeback concerts.\\r\\nThe singer, 46, was a guest host of Steph… [+3709 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Toyin Owoseje, CNN\",\"title\":\"Kim Kardashian says she's 'not OK' after son Saint breaks his arm - CNN\",\"description\":\"Kim Kardashian revealed on Instagram that 5-year-old Saint West broke his arm, adding that she was left distraught.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/kim-kardashian-son-broken-arm-intl-scli/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/210910090907-kim-kardashian-file-2020-restricted-super-tease.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"The A.V. Club\"},\"author\":\"Tatiana Tenreyro\",\"title\":\"Skins star Kathryn Prescott is in the ICU after being hit by a cement truck - The A.V. Club\",\"description\":\"Her twin sister and fellow Skins star Megan Prescott shared an Instagram post detailing Kathryn's condition\",\"url\":\"https://www.avclub.com/skins-star-kathryn-prescott-is-in-the-icu-after-being-h-1847650891\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/cd5082a13a051486a95961218e67567e.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":\"Kathryn Prescott, best known for playing fan-favorite Emily Fitch in the show Skins, has been hospitalized with severe injuries after being hit by a cement truck in New York City. Her twin sister Meg… [+1808 chars]\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Krushna Abhishek prays for truce with Govinda and Sunita Ahuja: 'We love each other despite the internal issues' - Hindustan Times\",\"description\":\"Krushna Abhishek has reacted to Govinda's wife Sunita Ahuja's recent interview. Sunita had slammed Krushna and said she doesn't want to see his face again. Krushna is the nephew of Govinda and Sunita.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/krushna-abhishek-prays-for-truce-with-govinda-and-sunita-ahuja-we-love-each-other-despite-the-internal-issues-101631281861066.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/Krushna_Govinda_(1)_1631283221684_1631283230941.jpg\",\"publishedAt\":\"2021-09-10T14:44:27Z\",\"content\":\"Krushna Abhishek hopes that the issues between him and Govinda and Sunita Ahuja are resolved soon. The actor-comedian's relationship with his uncle and aunt soured a few years ago. \\r\\nSpeaking with th… [+1657 chars]\"},{\"source\":{\"id\":null,\"name\":\"Castanet.net\"},\"author\":null,\"title\":\"Actress Kathryn Prescott in critical condition after being hit by cement truck - Entertainment News - Castanet.net\",\"description\":\"Skins actress Kathryn Prescott has been hospitalized with multiple injuries after being hit by a cement truck while crossing a road in New York.\",\"url\":\"https://www.castanet.net/news/Entertainment/345353/Actress-Kathryn-Prescott-in-critical-condition-after-being-hit-by-cement-truck\",\"urlToImage\":\"https://www.castanet.net/content/2021/9/thumbs/wenn_62018_p.jpg\",\"publishedAt\":\"2021-09-10T14:43:00Z\",\"content\":\"Skins actress Kathryn Prescott has been hospitalized with multiple injuries after being hit by a cement truck while crossing a road in New York.\\r\\nThe 30-year-old was rushed to hospital on Tuesday, he… [+1709 chars]\"}]}";
            ResponseEntertainmentNews = ResponseJSON;
            MyEntertainmentNewsPart.WhatToHappenWhenRequestIsProvided(ResponseJSON, getContext());
        }

    }
    public void InitWorldNewsOnRetrievalFailure(){
        ResponseJSON = "{\"status\":\"ok\",\"totalResults\":36,\"articles\":[{\"source\":{\"id\":null,\"name\":\"New York Times\"},\"author\":\"Jack Nicas\",\"title\":\"In Epic vs. Apple Court Fight, a Win for App Developers - The New York Times\",\"description\":\"The decision could have major implications for thousands of businesses that pay Apple billions of dollars each year.\",\"url\":\"https://www.nytimes.com/2021/09/10/technology/epic-apple-app-developers.html\",\"urlToImage\":\"https://static01.nyt.com/images/2021/09/10/business/10epicapple-promo/10epicapple-promo-facebookJumbo.jpg\",\"publishedAt\":\"2021-09-10T16:26:07Z\",\"content\":\"The decision could have a major ripple effect across the digital economy. If Epic prevails after expected appeals, companies would have a new way to avoid the App Store commission, which runs as high… [+1112 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Aaron Gregg\",\"title\":\"DoorDash, Grubhub, UberEats sue New York City over fee caps - The Washington Post\",\"description\":\"The coalition of popular food delivery apps says the law infringes on their private contracts with food sellers.\",\"url\":\"https://www.washingtonpost.com/business/2021/09/10/doordash-grubhub-new-york-city-lawsuit/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/4HOQGKAGW4I6ZM6EYRRLD3OPZA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T15:48:00Z\",\"content\":\"In a lawsuit filed late Thursday in the Southern District of New York, the parent companies of six widely used apps DoorDash, Caviar, Grubhub, Seamless, Postmates and UberEats accused the city of imp… [+139 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn’t the only potential bad actor out there. Here’s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m… [+5151 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"\",\"title\":\"Biden Brushes Off GOP Criticism Of Vaccination Mandate for Teachers - NPR\",\"description\":\"President Biden said he was saddened that some GOP governors are threatening to sue over the new mandates. To them, he said, \\\"Have at it.\\\"\",\"url\":\"https://www.npr.org/2021/09/10/1035869847/biden-brushes-off-gop-threats-to-sue-over-vaccine-mandates\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/10/ap21253539854160_wide-daa8827afff8aca7389512b65f0481a04e13b68b.jpg?s=1400\",\"publishedAt\":\"2021-09-10T15:43:50Z\",\"content\":\"President Biden tours Brookland Middle School in Washington, D.C., on Friday. Biden has encouraged every school district to promote vaccines, including with on-site clinics, to protect students as th… [+2918 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Erica Orden, CNN\",\"title\":\"Giuliani associate Igor Fruman pleads guilty to solicitation of a contribution by a foreign national - CNN\",\"description\":\"Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\",\"url\":\"https://www.cnn.com/2021/09/10/politics/igor-fruman-plea/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/191023132912-01-igor-fruman-1023-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:31:00Z\",\"content\":\"New York (CNN)Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\\r\\n… [+2400 chars]\"},{\"source\":{\"id\":null,\"name\":\"New York Post\"},\"author\":\"Emily Crane\",\"title\":\"China shares supposed video of Taliban using US military planes as toys - New York Post \",\"description\":\"China has mocked the United States’ chaotic withdrawal from Afghanistan by sharing footage that supposedly shows Taliban fighters turning abandoned US military planes into toys.\",\"url\":\"https://nypost.com/2021/09/10/china-shares-supposed-video-of-taliban-using-us-planes-as-toys/\",\"urlToImage\":\"https://nypost.com/wp-content/uploads/sites/2/2021/09/taliban-swings-hp1.jpg?quality=90&strip=all&w=1024\",\"publishedAt\":\"2021-09-10T15:14:00Z\",\"content\":\"China has mocked the United States chaotic withdrawal from Afghanistan by sharing footage that supposedly shows Taliban fighters turning abandoned US military planes into toys.\\r\\nChinese governmental … [+1366 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Alex Heath\",\"title\":\"WhatsApp is adding encrypted backups - The Verge\",\"description\":\"WhatsApp is rolling out end-to-end encrypted backups for chat logs on Android and iOS, protecting the file from anyone without the key.\",\"url\":\"https://www.theverge.com/2021/9/10/22665968/whatsapp-backups-end-to-end-encryption-ios-android\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/MD0eT-m-uto-M_MHFTRUrF_FHDk=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22245551/acastro_210119_1777_whatsapp_0003.jpg\",\"publishedAt\":\"2021-09-10T15:05:45Z\",\"content\":\"Going a step further than Apples iMessage\\r\\nIllustration by Alex Castro / The Verge\\r\\nWhatsApp will let its more than 2 billion users fully encrypt the backups of their messages, the Facebook-owned app… [+2774 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Toyin Owoseje, CNN\",\"title\":\"Kim Kardashian says she's 'not OK' after son Saint breaks his arm - CNN\",\"description\":\"Kim Kardashian revealed on Instagram that 5-year-old Saint West broke his arm, adding that she was left distraught.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/kim-kardashian-son-broken-arm-intl-scli/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/210910090907-kim-kardashian-file-2020-restricted-super-tease.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Deadline\"},\"author\":\"Anthony D'Alessandro\",\"title\":\"Jim Gianopulos Leaving Paramount As Chairman & CEO, Brian Robbins Expected To Take Over - Deadline\",\"description\":\"Jim Gianopulos is leaving Paramount as Chairman and CEO, Deadline has confirmed. A report in the Wall Street Journal said Brian Robbins, the head of ViacomCBS Inc.’s Nickelodeon kids TV empire, will take over Gianopulos’ position as the head of Paramount Pict…\",\"url\":\"https://deadline.com/2021/09/jim-gianopulos-leaving-paramount-as-chairman-ceo-brian-robbins-expected-to-take-over-1234830466/\",\"urlToImage\":\"https://deadline.com/wp-content/uploads/2019/02/jim-gianopulos-paramount.jpg?w=300\",\"publishedAt\":\"2021-09-10T14:18:00Z\",\"content\":\"Jim Gianopulos is leaving Paramount as Chairman and CEO, Deadline has confirmed. A report in the Wall Street Journal said Brian Robbins, the head of ViacomCBS Inc.’s Nickelodeon kids TV empire, will … [+6020 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Robyn Dixon, Reis Thebault\",\"title\":\"Russia begins major military drills with Belarus after moves toward closer integration - The Washington Post\",\"description\":\"Russia’s Vladimir Putin plans to unify Russia’s military space with Belarus, but offered no details on what it means.\",\"url\":\"https://www.washingtonpost.com/world/europe/russia-belarus-zapad-military-drill/2021/09/10/14f3231a-1012-11ec-baca-86b144fc8a2d_story.html\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/A5GICNASHUI6ZPEKRWNFWU2BSQ.jpg&w=1440\",\"publishedAt\":\"2021-09-10T14:17:57Z\",\"content\":\"The Zapad (meaning West) exercise is held regularly, but this iteration comes as Russian relations with NATO are increasingly fraught. Belarus under President Alexander Lukashenko faces Western sanct… [+8166 chars]\"},{\"source\":{\"id\":\"fox-news\",\"name\":\"Fox News\"},\"author\":\"Ryan Gaydos\",\"title\":\"Cowboys' Greg Zuerlein laments missed field goals: 'If I do my job, we win that game' - Fox News\",\"description\":\"Dallas Cowboys kicker Greg Zuerlein was a bit hard on himself after the team’s 31-29 loss to the Tampa Bay Buccaneers on Thursday night.\",\"url\":\"https://www.foxnews.com/sports/cowboys-greg-zuerlein-missed-field-goals\",\"urlToImage\":\"https://static.foxnews.com/foxnews.com/content/uploads/2021/09/Greg-Zuerlein.jpg\",\"publishedAt\":\"2021-09-10T14:11:03Z\",\"content\":\"Dallas Cowboys kicker Greg Zuerlein was a bit hard on himself after the teams 31-29 loss to the Tampa Bay Buccaneers on Thursday night.\\r\\nZuerlein missed two field goals and an extra point attempt tha… [+1564 chars]\"},{\"source\":{\"id\":null,\"name\":\"Fox Business\"},\"author\":\"Jonathan Garber\",\"title\":\"Stocks snap 4-day losing streak, oil nears $70 - Fox Business\",\"description\":\"U.S. stocks bounced around as investors evaluated another hot read on inflation.\",\"url\":\"https://www.foxbusiness.com/markets/stocks-losing-streak-oil-nears-70\",\"urlToImage\":\"https://a57.foxnews.com/static.foxbusiness.com/foxbusiness.com/content/uploads/2018/02/0/0/8815b045-ap16301578658834.jpg?ve=1&tl=1\",\"publishedAt\":\"2021-09-10T13:50:55Z\",\"content\":\"U.S. stock indexes surrendered morning gains sending all three averages lower and diminishing chances that the Dow Jones Industrial Average and the S&amp;P 500 index would snap four-day losing streak… [+2801 chars]\"},{\"source\":{\"id\":\"nbc-news\",\"name\":\"NBC News\"},\"author\":\"Molly Hunter, Petra Cahill\",\"title\":\"At Pakistan border, Afghans find a way out of Taliban rule after a treacherous journey - NBC News\",\"description\":\"The Taliban flag flies on one side of the border. On the other is the Pakistani military— and hope for freedom for terrified Afghans.\",\"url\":\"https://www.nbcnews.com/news/world/pakistan-border-afghans-find-way-out-taliban-rule-after-treacherous-n1278898\",\"urlToImage\":\"https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/newscms/2021_36/3504803/210910-pakistan-taliban-mb-0716.JPG\",\"publishedAt\":\"2021-09-10T13:44:00Z\",\"content\":\"TORKHAM, Pakistan The Taliban flag flies on one side of the border. On the other is the Pakistani military and hope for freedom for terrified Afghans.\\r\\nMursal and Manisha, two teenage sisters from Ka… [+5099 chars]\"},{\"source\":{\"id\":null,\"name\":\"ESPN\"},\"author\":\"Mike Reiss\",\"title\":\"Cam Newton shares surprise at New England Patriots release, didn't think Mac Jones would be comfortable with him as backup - ESPN\",\"description\":\"Free-agent quarterback Cam Newton said he didn't see his release by the Patriots coming and noting that he thought Mac Jones \\\"would have been uncomfortable\\\" with Newton as his backup.\",\"url\":\"https://www.espn.com/nfl/story/_/id/32182159/cam-newton-shares-surprise-new-england-patriots-release-think-mac-jones-comfortable-backup\",\"urlToImage\":\"https://a1.espncdn.com/combiner/i?img=%2Fphoto%2F2021%2F0831%2Fr902858_1296x729_16%2D9.jpg\",\"publishedAt\":\"2021-09-10T13:16:56Z\",\"content\":\"Free-agent quarterback Cam Newton said he didn't see his release from the New England Patriots coming in his first remarks since the team cut him 10 days ago.\\r\\n\\\"Did it catch me by surprise being rele… [+4615 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Allyson Chiu\",\"title\":\"What is ivermectin, and where did people get the idea it can treat covid? - The Washington Post\",\"description\":\"Ivermectin, an anti-parasitic medicine for both humans and animals, is being promoted as a covid treatment despite a lack of evidence.\",\"url\":\"https://www.washingtonpost.com/lifestyle/2021/09/10/ivermectin-covid-humans/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/UPAYQWAL3EI6ZJ6IMG5XWO7WFA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T12:56:15Z\",\"content\":\"Doctors dismayed by patients who fear coronavirus vaccines but clamor for unproven ivermectin\\r\\nInterest in ivermectin has surged during this summers rapid rise in coronavirus infections fueled by the… [+1091 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"David Schaper\",\"title\":\"TSA Timeline: How Travel And Airport Security Changed Since 9/11 - NPR\",\"description\":\"No boarding pass or ID was needed to go to the gate, and 4-inch-blade knives were allowed aboard planes. Now we take off shoes, can't have liquids over 3.4 oz and go through high-tech body scanners.\",\"url\":\"https://www.npr.org/2021/09/10/1035131619/911-travel-timeline-tsa\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/08/2001-09-19t120000z_970614932_pbeahukxucn_rtrmadp_3_world-trade-center1_wide-bf19ae3ccdfbc2952494ae56d30ee65b112f1b4e.jpg?s=1400\",\"publishedAt\":\"2021-09-10T12:46:58Z\",\"content\":\"A traveler at Ronald Reagan Washington National Airport walks to a Transportation Security Administration checkpoint on Nov. 26, 2014.\\r\\nPaul J. Richards/AFP via Getty Images\\r\\nIt's hard to fathom now,… [+20838 chars]\"},{\"source\":{\"id\":null,\"name\":\"Bleeding Green Nation\"},\"author\":\"Brandon Lee Gowton\",\"title\":\"Eagles News: Jason Kelce has a new look - Bleeding Green Nation\",\"description\":\"Philadelphia Eagles news and links for 9/10/21.\",\"url\":\"https://www.bleedinggreennation.com/2021/9/10/22666410/eagles-news-jason-kelce-new-look-blonde-hair-blond-philadelphia-center-analytics-inquirer-falcons\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/J1rUB1dqNmL8_Yg6CHYi-RGXkWM=/0x18:380x217/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839455/Screen_Shot_2021_09_10_at_8.05.37_AM.png\",\"publishedAt\":\"2021-09-10T12:33:47Z\",\"content\":\"Lets get to the Philadelphia Eagles links ...\\r\\nJason Kelce went blonde too, and its blowing our minds - NBCSPEagles tight end Zach Ertz blew the Twitterverse wide open when he reported to training ca… [+12560 chars]\"},{\"source\":{\"id\":null,\"name\":\"Gizmodo.com\"},\"author\":\"Victoria Song\",\"title\":\"You Can Finally Share Dolby Vision Videos Filmed on iPhone 12 - Gizmodo\",\"description\":\"Vimeo, Dolby, and Apple have teamed up so you can finally upload those high-res videos somewhere.\",\"url\":\"https://gizmodo.com/you-can-finally-share-dolby-vision-videos-filmed-on-iph-1847646550\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/3c6d1fe0138f1367b4818e5c069244b1.jpg\",\"publishedAt\":\"2021-09-10T12:30:00Z\",\"content\":\"When Apple trotted out the iPhone 12 lineup last October, it also introduced the ability to record video in Dolby Vision HDR. It was a first for smartphones, not just iPhones. But there was a catch. … [+2127 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Ian Sample\",\"title\":\"Scientists' egos are key barrier to progress, says Covid vaccine pioneer - The Guardian\",\"description\":\"Prof Katalin Karikó of BioNTech says she endured decades of scepticism over her work on mRNA vaccines\",\"url\":\"https://amp.theguardian.com/world/2021/sep/10/scientists-egos-key-barrier-to-progress-covid-vaccine-pioneer-katalin-kariko\",\"urlToImage\":null,\"publishedAt\":\"2021-09-10T11:46:00Z\",\"content\":\"CoronavirusProf Katalin Karikó of BioNTech says she endured decades of scepticism over her work on mRNA vaccines \\r\\nScientists would make swifter progress in solving the worlds problems if they learne… [+5258 chars]\"},{\"source\":{\"id\":null,\"name\":\"Live Science\"},\"author\":\"Stephanie Pappas\",\"title\":\"Spectacular valleys and cliffs hidden beneath the North Sea - Livescience.com\",\"description\":\"Like a bowl of spaghetti noodles spilled across the floor of the North Sea, a vast array of hidden tunnel valleys wind and meander across what was once an ice-covered landscape.&#xA0;These valleys are remnants of ancient rivers that once drained water from me…\",\"url\":\"https://www.livescience.com/north-sea-ice-age-tunnel-valleys.html\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/wtJwXMGsxAhy8ph37Afifb-1200-80.jpg\",\"publishedAt\":\"2021-09-10T11:29:00Z\",\"content\":\"Scientists discovered this esker (a sedimentary cast of a meltwater channel formed beneath an ice sheet), in a tunnel valley beneath the North Sea floor. The landscape is shown in an image based on h… [+6373 chars]\"}]}";
        ResponseWorldNews = ResponseJSON;

        if(!ParseJSONWorldNews(ResponseJSON, "status").get(0).equals("ok")){
            // Something bad happened
            Toast.makeText(getContext(), "The news response is outdated. Maybe today's calls have expired. Please try again later", Toast.LENGTH_SHORT).show();
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(requireContext(),"JSON_WORLDNEWS_CACHE.json", ResponseJSON);
        }
        ResponseJSON = readFromFile(requireContext(), "JSON_WORLDNEWS_CACHE.json");

        System.out.println("RESPONSE READ FROM FILE (WORLD NEWS): " + ResponseJSON);
        DifferentFunctions.writeToFile(getActivity(),"JSON_WORLDNEWS_CACHE.json", ResponseJSON);
        // Toast.makeText(getActivity(), "The information is out of date! One or more files did not exist and the API for news is locked for 24 hours", Toast.LENGTH_LONG).show();

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
                if(isOnline(requireActivity()))
                    Picasso.get().load(MyIMGURLArrayListForWorldNews.get(0)).fit().centerInside().into(ImageNews); // Set Image
                else
                    ImageNews.setImageResource(R.drawable.materialwall);
            }
        }
    }



}