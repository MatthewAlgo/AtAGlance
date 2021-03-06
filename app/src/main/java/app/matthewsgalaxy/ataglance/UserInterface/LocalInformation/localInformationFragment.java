package app.matthewsgalaxy.ataglance.UserInterface.LocalInformation;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ToCamelCaseWord;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.APIKEY;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseJsonForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseJsonWeather;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunriseGlobalHourString;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewHeadlinesAdapter;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.MainActivity;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.BusinessNewsPart;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.EntertainmentNewsPart;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.PoliticsNewsPart;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.ScienceNewsPart;
import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.TechnologyNewsPart;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class localInformationFragment extends Fragment implements View.OnClickListener{
    private static String GlobalResp;

    private View PubView;
    EditText InputText;
    Chip ConfirmButton;
    String LocationInserted, APISTRING_WEATHERCURRENT, APISTRING_WEATHERFORECAST;
    private ImageView ImageHour1, ImageHour2, ImageHour3, ImageHour4, ImageHour5, ImageHour6, ImageHour7, ImageHour8,
            ImageHour9;
    private Chip ChipHour1, ChipHour2, ChipHour3, ChipHour4, ChipHour5, ChipHour6, ChipHour7, ChipHour8, ChipHour9;
    private Chip ChipHourUpperText1, ChipHourUpperText2, ChipHourUpperText3, ChipHourUpperText4, ChipHourUpperText5, ChipHourUpperText6, ChipHourUpperText7, ChipHourUpperText8, ChipHourUpperText9;
    private Chip chipLastUpdated, chipCurrentWeatherPromptJava, chipWind, TempChip, chipHILO,MainConditionTextJava,chipHumidity;

    ImageView MainCurrentCondition;

    String SunriseGlobalHourString;
    String SunsetGlobalHourString;


    boolean isDaylightAtCall;
    boolean doneLoadingWeather = false;

    // FOR PERSONALIZED NEWS ARTICLES

    private Context MContext;
    private RecyclerView recyclerView;
    private Chip ChipURLLink;


    public ArrayList<String> ArrayListURLValues;
    public ArrayList<String> TitlesArrayList;
    public ArrayList<String> DescriptionsArrayList;
    public ArrayList<String> ImageURLArrayList;

    private static String JSONNewsResponse, JsonWeatherResponse, JsonForecastResponse;
    private String Country;

    // ---------------------------

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PubView = inflater.inflate(R.layout.fragment_searchinformationfrag, container, false);

        InputText = PubView.findViewById(R.id.Inputtext);
        ConfirmButton = PubView.findViewById(R.id.confirm_button);
        View view = PubView; MContext = view.getContext();

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
        chipCurrentWeatherPromptJava = view.findViewById(R.id.chipCurrentWeatherPrompt);
        MainConditionTextJava = view.findViewById(R.id.MainConditionText);
        chipHumidity = view.findViewById(R.id.chipHumidity);
        MainCurrentCondition = view.findViewById(R.id.MainImageView);

        chipWind = view.findViewById(R.id.chipWind);
        TempChip = view.findViewById(R.id.chipTemperature);
        chipHILO = view.findViewById(R.id.chipHILO);

        try {

            ConfirmButton.setOnClickListener(this);

            // Check Internet Availability
            if (!isOnline(PubView.getContext())) {
                Toast.makeText(PubView.getContext(), "You are not connected! Please Check Your Internet Connection And Try Again!" + InputText.getText().toString(), Toast.LENGTH_LONG).show();
            }

            // ~~~~~~~~~~~~~~~~~ To be used together with the recview for local news
            recyclerView = PubView.findViewById(R.id.LocalNewsRecView);
            recyclerView.setNestedScrollingEnabled(false);

            ChipURLLink = PubView.findViewById(R.id.ChipURLLink);
            //

            InitRecyclerView();

            // Load weather conditions
            loadDefaultWeatherConds();
            loadDefaultWeatherForecast();

        }catch (Exception exception){ // Catches errors if needed
            System.out.println(exception.getMessage().toString());
        }
        return PubView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }

    private String CallNewsApiLocal(String CityPrefix){
        OkHttpClient client = new OkHttpClient();
        String API_CALL_CITY = "https://newsapi.org/v2/top-headlines?country=" + CityPrefix + "&apiKey=82de6527ef904da08c127287e4044c27";
        // For Getting our response
        Request request = new Request.Builder()
                .url(API_CALL_CITY)
                .build();

        Call callf = client.newCall(request);
        callf.enqueue(new Callback() {

            public void onResponse(@NonNull Call call, @NonNull Response response){
                // We got the response -> We have to set everything up
                // ~~~~~~~~~~~ TRY TO UPDATE THE NEWS SECTION
                try {
                    String responseData = response.body().string();
                    // ~~~~~~~~~~~~~~~~ TRY TO UPDATE THE NEWS SECTION AFTER THE WEATHER CALL
                    requireActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("Country: "+CityPrefix);
                            System.out.println("Global Response: "+responseData);
                            System.out.println(responseData);

                            InitRecyclerViewWithCountry(responseData);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            public void onFailure(Call call, IOException e) {
                // Failed request
            }
        });
        // Return the result after the request was made

        return null;
    }

    @Override
    public void onClick(View view) {
        LocationInserted = InputText.getText().toString();
        if(LocationInserted != null && !LocationInserted.equals("")) {
            Toast.makeText(PubView.getContext(), "Showing Weather Conditions For " + InputText.getText().toString(), Toast.LENGTH_LONG).show();
            // Start Initializing the API and stuff
            if (LocationInserted != null) {
                // Functions
                APISTRING_WEATHERCURRENT = "https://api.openweathermap.org/data/2.5/weather?q=" + LocationInserted + "&appid=" + APIKEY;
                APISTRING_WEATHERFORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=" + LocationInserted + "&appid=" + APIKEY;

                // We make a call using the string
                try {
                    DoInBackgroundRequestLocal("weather");
                    DoInBackgroundRequestLocal("forecast");
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }

            }

        }else{
            Toast.makeText(PubView.getContext(), "Location field can't be empty", Toast.LENGTH_SHORT).show();
        }

    }
    public void DoInBackgroundRequestLocal(String REQUEST_TYPE_LOC) {
        // Makes HTTP Request That returns a json with the current weather conditions
        OkHttpClient client = new OkHttpClient();
        Request request = null;

        if (REQUEST_TYPE_LOC.equals( "weather")) {
            request = new Request.Builder().url(APISTRING_WEATHERCURRENT).build();
        } else if (REQUEST_TYPE_LOC.equals("forecast")) {
            request = new Request.Builder().url(APISTRING_WEATHERFORECAST).build();
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
                                String ResponseJSON = MyResp;

                                // Parse JSON and Get an ArrayList of conditions
                                if (REQUEST_TYPE_LOC.equals("forecast")) {

                                    if (ResponseJSON != null && !ResponseJSON.equals("")) {
                                        // Now we read the file and put the response inside
                                        JsonForecastResponse = ResponseJSON;

                                        // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
                                        ArrayList<String> ConditionsInJson = ParseJSONForecast(ResponseJSON, new String("id_icon"));
                                        ArrayList<String> TimeStamps = ParseJSONForecast(ResponseJSON, new String("time_stamp"));

                                        ArrayList<String> GlobalTimeForExtendedForecast = ParseJSONForecast(ResponseJSON, new String("time_stamp"));
                                        ArrayList<String> GlobalHumidityForExtendedForecast = ParseJSONForecast(ResponseJSON, "humidity");
                                        ArrayList<String> GlobalWSpeedForExtendedForecast = DifferentFunctions.ParseJSONForecast(ResponseJSON, "w_speed");

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
                                        ChipHour1.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(0)) - 273.15) + "??C");
                                        ChipHour2.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(1)) - 273.15) + "??C");
                                        ChipHour3.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(2)) - 273.15) + "??C");
                                        ChipHour4.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(3)) - 273.15) + "??C");
                                        ChipHour5.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(4)) - 273.15) + "??C");
                                        ChipHour6.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(5)) - 273.15) + "??C");
                                        ChipHour7.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(6)) - 273.15) + "??C");
                                        ChipHour8.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(7)) - 273.15) + "??C");
                                        ChipHour9.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(8)) - 273.15) + "??C");

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
                                    if (ResponseJSON != null && !ResponseJSON.equals("")) {
                                        JsonWeatherResponse = ResponseJSON;

                                        // Get the current date and time and set the upper chip
                                        Date CurrentDate = new Date((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                        Time CurrentTime = new Time((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
                                        chipLastUpdated.setText("Updated At: " + CurrentDate.toString() + " " + CurrentTime.toString());

                                        // SAVE THE JSON REQUEST LOCALLY
                                        // try { WriteStringToFile(ResponseJSON, "FILE_JSON_WEATHER.json"); } catch (IOException e) { e.printStackTrace(); }

                                        // MODIFY THE DESCRIPTION TEXTVIEW
                                        // System.out.println(Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod"))); -> Call returns 200 if OK
                                        String ToModifyCurrentConds = ParseJSONCurrentWeather(ResponseJSON, "description");

                                        String LocationByLatAndLong = ParseJSONCurrentWeather(ResponseJSON, "city_name");
                                        // Try to fetch city name - This should not be equal to "Globe"
                                        assert LocationByLatAndLong != null;
                                        if (LocationByLatAndLong.equals("Globe")) {
                                            Toast.makeText(getContext(), "Error while fetching your current location. Please try again later.", Toast.LENGTH_LONG).show();
                                        }

                                        String TimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "time");
                                        String SunriseTimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "sunrise");
                                        String SunsetTimeStampUnix = ParseJSONCurrentWeather(ResponseJSON, "sunset");
                                        // TIMESTAMP VALUES IN GMT!

                                        /// Process the timestamps
                                        Pair<Integer, Integer> SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext());
                                        Pair<Integer, Integer> SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext());
                                        SunriseGlobalHourString = SunriseTimeStampUnix;
                                        SunsetGlobalHourString = SunsetTimeStampUnix;
                                        // We have sunrise / sunset stored globally

                                        System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()).first);
                                        System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()).second);

                                        System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()).first);
                                        System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()).second);

                                        System.out.println("Minutes: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()).first);
                                        System.out.println("Hours: " + DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()).second);

                                        isDaylightAtCall = DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(TimeStampUnix, requireContext()),
                                                DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext()), DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext()));

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
                                        // Modify the wind icon
                                        chipWind.setText("Speed - " + String.format("%.2f", (Double) WindSpeed) + "km/h" + " - " + WindConditions);

                                        // MODIFY THE MAIN ICON
                                        ModifyImageToConditions(MainCurrentCondition, isDaylightAtCall, ToModifyCurrentCondsImage);

                                        // MODIFY THE TEMPERATURE
                                        String ToModifyCurrentTemperature = ParseJSONCurrentWeather(ResponseJSON, "temperature");
                                        TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "??C");

                                        chipHILO.setText("High - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmax")) - 273.15) + "??C" + " | Low - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmin")) - 273.15) + "??C");
                                        doneLoadingWeather = true;
                                        /*

                                        // ~~~~~~~~~~~ TRY TO UPDATE THE NEWS SECTION
                                        try {

                                            // ~~~~~~~~~~~~~~~~ TRY TO UPDATE THE NEWS SECTION AFTER THE WEATHER CALL
                                            Country = ParseJSONCurrentWeather(localInformationFragment.JsonWeatherResponse, "country");
                                            System.out.println("Country: " + Country);
                                            JSONNewsResponse = CallNewsApiLocal(Country);
                                            System.out.println("Global Response: "+ GlobalResp);
                                            System.out.println(JSONNewsResponse);
                                            InitRecyclerViewWithCountry(JSONNewsResponse);

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        */

                                        try{
                                            Country = ParseJSONCurrentWeather(localInformationFragment.JsonWeatherResponse, "country");
                                            CallNewsApiLocal(Country);

                                        }catch(Exception exc){
                                            exc.printStackTrace();
                                        }



                                    }

                                }
                            }

                        });
                    } else {
                        // Most likely we are offline and the request failed
                        // Toast.makeText(requireContext(), "Error while processing the request. Loading backup data...", Toast.LENGTH_SHORT);
                        System.out.println("Error while processing the request. Loading backup data...");
                    }

                }
            });

    }


    // ~~~~~~~~~~~~~~~~~~~
    // For recyclerView
    public static void shrinkListTo(ArrayList<String> list, int newSize) {
        for (int i = list.size() - 1; i >= newSize; --i)
            list.remove(i);
    }
    public void InitRecyclerView(){

        // Append The values of each and every Title from the list
        try {
            TitlesArrayList = AtAGlanceFragment.MyTitlesArrayListForWorldNews;

            DescriptionsArrayList = AtAGlanceFragment.MyDescriptionsArrayListForWorldNews;

            ArrayListURLValues = AtAGlanceFragment.MyURLArrayListForWorldNews;

            ImageURLArrayList = AtAGlanceFragment.MyIMGURLArrayListForWorldNews;

            if(!TitlesArrayList.isEmpty()) {
                if(TitlesArrayList.size()>20) { // Only 20 articles
                    shrinkListTo(TitlesArrayList, 20);
                    shrinkListTo(ArrayListURLValues, 20);
                    shrinkListTo(DescriptionsArrayList, 20);
                    shrinkListTo(ImageURLArrayList, 20);
                }
                if(TitlesArrayList.size() !=0) {
                    RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                }
            }else{
                    Toast.makeText(MContext, "There has been an error while loading the news. Please try again", Toast.LENGTH_SHORT).show();
                    String LinearTopUS = "{ \"status\": \"ok\", \"totalResults\": 70, -\"articles\": [ -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nora Naughton, Mike Colias\", \"title\": \"Hurricane Ida???s Destruction Adds to Car Market???s Woes - The Wall Street Journal\", \"description\": \"The storm damaged vehicles across several states, sending shoppers to dealers already struggling because of the chip shortage\", \"url\": \"https://www.wsj.com/articles/hurricane-idas-destruction-adds-to-car-markets-woes-11631266201\", \"urlToImage\": \"https://images.wsj.net/im-398444/social\", \"publishedAt\": \"2021-09-10T12:09:00Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Alex Sherman\", \"title\": \"Tinder CEO Jim Lanzone will be next CEO of Yahoo following Apollo acquisition - CNBC\", \"description\": \"Apollo closed its acquisition of Yahoo from Verizon on Sept. 1.\", \"url\": \"https://www.cnbc.com/2021/09/10/tinder-ceo-jim-lanzone-will-be-next-ceo-of-yahoo-following-apollo-acquisition.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106939945-1631275692128-gettyimages-1059190354-fiuza-websummi181108_npW66.jpeg?v=1631275773\", \"publishedAt\": \"2021-09-10T12:04:13Z\", \"content\": \"Tinder CEO Jim Lanzone will be the new chief of Yahoo, according to a company memo obtained by CNBC Friday.\\r\\nYou can read the full memo from former Yahoo boss Guru Gowrappan announcing the change bel??? [+2248 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Matthew J. Belvedere\", \"title\": \"5 things to know before the stock market opens Friday - CNBC\", \"description\": \"U.S. stock futures bounced after the Dow and S&P 500 posted their fourth session of losses.\", \"url\": \"https://www.cnbc.com/2021/09/10/5-things-to-know-before-the-stock-market-opens-friday-sept-10.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106931833-1629812138310-106931833-1629738864558-gettyimages-1234833912-US_STOCKS.jpg?v=1631122596\", \"publishedAt\": \"2021-09-10T12:02:39Z\", \"content\": \"Here are the most important news, trends and analysis that investors need to start their trading day:\\r\\n1. Dow, S&amp;P 500 set to bounce after four sessions of losses\\r\\nA trader works on the floor of ??? [+4374 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Peter Schacknow\", \"title\": \"Stocks making the biggest moves premarket: Affirm, Toyota, Endo, Bausch Health and others - CNBC\", \"description\": \"These are the stocks posting the largest moves before the bell.\", \"url\": \"https://www.cnbc.com/2021/09/10/stocks-making-the-biggest-moves-premarket-affirm-toyota-endo-bausch-health-and-others.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106342643-15792830265032020toyotahighlander.jpg?v=1579283101\", \"publishedAt\": \"2021-09-10T11:55:22Z\", \"content\": \"Check out the companies making headlines before the bell:\\r\\nAffirm Holdings (AFRM) Affirm soared 22.4% in the premarket as the \\\"buy now, pay later\\\" company's revenue easily topped estimates. Active me??? [+3173 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"New York Post\" }, \"author\": \"Will Feuer\", \"title\": \"Apple fires manager for alleged leaks after she claimed harassment - New York Post \", \"description\": \"Apple has reportedly fired Ashley Gj??vik ??? months after she went public with accusations of harassment, sexism and intimidation ??? for allegedly leaking private company information.\", \"url\": \"https://nypost.com/2021/09/10/apple-fires-manager-for-alleged-leaks-after-sexism-claims/\", \"urlToImage\": \"https://nypost.com/wp-content/uploads/sites/2/2021/09/ashley-gjovik-apple-16.jpg?quality=90&strip=all&w=1024\", \"publishedAt\": \"2021-09-10T11:34:00Z\", \"content\": \"Apple has reportedly fired Ashley Gj??vik, a senior engineering program manager, for allegedly leaking private company information.\\r\\nGj??vik, who???s been at the company since 2015, told The Verge that s??? [+3010 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"nj.com\" }, \"author\": \"Jeff Goldman | NJ Advance Media for NJ.com\", \"title\": \"Police ID man, woman killed when car crashed into parked dump truck - NJ.com\", \"description\": \"The residents of the Sicklerville section of Winslow were both in their late 70s.\", \"url\": \"https://www.nj.com/camden/2021/09/police-id-man-woman-killed-when-car-crashed-into-parked-dump.html\", \"urlToImage\": \"https://www.nj.com/resizer/4tBBbnDg03a4ZU7iYdlyMqCrBEs=/1280x0/filters:focal(263x122:273x112)/cloudfront-us-east-1.images.arcpublishing.com/advancelocal/UXXXTZQJ6BBOHCKF7FQR236CWA.jpg\", \"publishedAt\": \"2021-09-10T11:30:00Z\", \"content\": \"Authorities have identified the two people killed Wednesday when their car crashed into a parked dump truck in Gloucester Township.\\r\\nDaniel Hammond, 79, of Sicklerville, was driving on Berlin Cross K??? [+657 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Joe Wallace\", \"title\": \"Stock Futures Rise; Metals Rally on Hopes of Reduced U.S.-China Tensions - The Wall Street Journal\", \"description\": \"Oil and metals prices rise on hopes of reduced geopolitical tensions after a call between President Biden and China???s Xi Jinping\", \"url\": \"https://www.wsj.com/articles/global-stock-markets-dow-update-09-10-2021-11631259867\", \"urlToImage\": \"https://images.wsj.net/im-398810/social\", \"publishedAt\": \"2021-09-10T11:23:00Z\", \"content\": null }, -{ -\"source\": { \"id\": \"fox-news\", \"name\": \"Fox News\" }, \"author\": \"Gary Gastelu\", \"title\": \"See it: Tesla Model S Plaid claims electric car lap record at benchmark track - Fox News\", \"description\": \"The Tesla Model S Plaid has set an unofficial electric car lap record at the Nurburgring Nordschleife race track. Its 7:30 time was over 11 seconds better than the Porsche Taycans.\", \"url\": \"https://www.foxnews.com/auto/tesla-model-s-plaid-electric-car-record-track\", \"urlToImage\": \"https://static.foxnews.com/foxnews.com/content/uploads/2021/09/tesla.gif\", \"publishedAt\": \"2021-09-10T11:21:14Z\", \"content\": \"The Tesla Model S Plaid has taken the Porsche Taycan Turbo's electric car lap record at Germany's Nurburgring Nordschleife race track.\\r\\nElon Musk posted a timeslip on Twitter Thursday night indicatin??? [+1395 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"MarketWatch\" }, \"author\": \"Steve Goldstein\", \"title\": \"This Wall Street firm is sticking to its S&P 500 price target. Here's why it says a correction is overdue. - MarketWatch\", \"description\": \"Critical information for the U.S. trading day\", \"url\": \"https://www.marketwatch.com/story/this-wall-street-firm-is-sticking-to-its-s-p-500-price-target-heres-why-it-says-a-correction-is-overdue-11631270767\", \"urlToImage\": \"https://images.mktw.net/im-390998/social\", \"publishedAt\": \"2021-09-10T10:46:00Z\", \"content\": \"Theres nothing like higher prices to change the minds of investors and Wall Street analysts.Witness the recent upward flurry of S&amp;P 500 \\r\\n SPX,\\r\\n -0.46%\\r\\nprice targets. Wells Fargo went from bein??? [+3922 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Yahoo Entertainment\" }, \"author\": \"Jared Blikre\", \"title\": \"Where bitcoin could become legal tender next: Grayscale CEO - Yahoo Finance\", \"description\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager...\", \"url\": \"https://finance.yahoo.com/news/where-bitcoin-may-become-legal-tender-next-grayscale-investments-ceo-103527930.html\", \"urlToImage\": \"https://s.yimg.com/ny/api/res/1.2/QwcftghAr.XOO60bVE4iNg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD04MDA-/https://s.yimg.com/os/creatr-uploaded-images/2021-09/4a8e3400-11bc-11ec-aebe-c80ebf81a461\", \"publishedAt\": \"2021-09-10T10:35:27Z\", \"content\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager believes other countries in emerging markets a??? [+2840 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"BioNTech to seek approval soon for vaccine for 5-11 year olds-Spiegel - Reuters\", \"description\": \"BioNTech <a href=\\\"https://www.reuters.com/companies/22UAy.DE\\\" target=\\\"_blank\\\">(22UAy.DE)</a> is set to request approval across the globe to use its COVID-19 vaccine in children as young as five over the next few weeks and preparations for a launch are on trac???\", \"url\": \"https://www.reuters.com/business/healthcare-pharmaceuticals/biontech-seek-approval-soon-vaccine-5-11-year-olds-spiegel-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/Cw7zdd6Cad5faTeNFsrxbWn2XnQ=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/XA3ISOF3EROIZE3YJWGU3K4IF4.jpg\", \"publishedAt\": \"2021-09-10T10:35:00Z\", \"content\": \"Syringes are seen in front of a displayed Biontech logo in this illustration taken November 10, 2020. REUTERS/Dado Ruvic/IllustrationFRANKFURT, Sept 10 (Reuters) - BioNTech (22UAy.DE) is set to reque??? [+2215 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Fox Business\" }, \"author\": \"Jack Durschlag\", \"title\": \"Bitcoin struggles above $45,000 after tumble earlier in the week - Fox Business\", \"description\": \"Bitcoin was trading 1.02% lower on Friday morning at approximately $45,900 per coin.\", \"url\": \"https://www.foxbusiness.com/markets/bitcoin-struggles-above-45000-after-tumble-earlier-in-the-week\", \"urlToImage\": \"https://a57.foxnews.com/static.foxbusiness.com/foxbusiness.com/content/uploads/2021/05/0/0/Bitcoin-Volatility.jpg?ve=1&tl=1\", \"publishedAt\": \"2021-09-10T09:36:50Z\", \"content\": \"Bitcoin was trading 1.02% lower on Friday morning.\\r\\nThe price was around $45,910 per coin, while rivals Ethereum and Dogecoin were trading around $3,370 (-4.62%) and 24.6 cents (-4.46%) per coin, res??? [+1170 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nick Timiraos\", \"title\": \"Fed Officials Prepare for November Reduction in Bond Buying - The Wall Street Journal\", \"description\": \"Phasing out the Fed???s pandemic-era stimulus by the middle of 2022 could clear the path for an interest-rate increase\", \"url\": \"https://www.wsj.com/articles/fed-officials-prepare-for-november-reduction-in-bond-buying-11631266200\", \"urlToImage\": \"https://images.wsj.net/im-398460/social\", \"publishedAt\": \"2021-09-10T09:30:00Z\", \"content\": \"Federal Reserve officials will seek to forge agreement at their coming meeting to begin scaling back their easy money policies in November.\\r\\nMany of them have said in recent interviews and public sta??? [+352 chars]\" }, -{ -\"source\": { \"id\": \"fortune\", \"name\": \"Fortune\" }, \"author\": \"Bernhard Warner\", \"title\": \"Stocks and futures rebound, crypto flat as the markets head for a down week - Fortune\", \"description\": \"U.S. futures are on the rise, following Europe and Asia higher.\", \"url\": \"https://fortune.com/2021/09/10/stocks-futures-rebound-crypto-flat-ecb-tapering/\", \"urlToImage\": \"https://content.fortune.com/wp-content/uploads/2021/09/GettyImages-1235142477.jpg?resize=1200,600\", \"publishedAt\": \"2021-09-10T09:20:47Z\", \"content\": \"Skip to Content\" }, -{ -\"source\": { \"id\": null, \"name\": \"Teslarati\" }, \"author\": \"Maria Merano\", \"title\": \"Tesla sidesteps New Mexico ban by building service center in tribal land - Teslarati\", \"description\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NM???s ban by building a service center on Native American tribal land. For the longe???\", \"url\": \"https://www.teslarati.com/tesla-outsmarts-ban-tribal-land-service-center/\", \"urlToImage\": \"https://www.teslarati.com/wp-content/uploads/2021/09/tesla-service-center-tribal-land-1024x768.jpeg\", \"publishedAt\": \"2021-09-10T08:04:31Z\", \"content\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NMs ban by building a s??? [+2038 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Harvard University to end investment in fossil fuels - Reuters\", \"description\": \"Harvard University is ending its investments in fossil fuels, the school's president said on Thursday, drawing praise from divestment activists who had long pressed the leading university to exit such holdings.\", \"url\": \"https://www.reuters.com/world/us/harvard-university-will-allow-fossil-fuel-investments-expire-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/fhLu9JYqDn4zVDewYKbNTvVw10w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/LQTLH5D4VBPPRF43TJVXNTSGGY.jpg\", \"publishedAt\": \"2021-09-10T07:57:00Z\", \"content\": \"Lawrence Bacow speaks during his inauguration as the 29th President of Harvard University in Cambridge, Massachusetts, U.S., October 5, 2018. REUTERS/Brian SnyderBOSTON, Sept 9 (Reuters) - Harvard Un??? [+1934 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Atlanta Journal Constitution\" }, \"author\": \"Kelly Yamanouchi\", \"title\": \"After announcing penalty for unvaccinated employees, Delta says more workers are getting inoculated - Atlanta Journal Constitution\", \"description\": \"Delta says more employees have gotten vaccinated after announcing a penalty for those who are unvaccinated.\", \"url\": \"https://www.ajc.com/news/business/more-delta-employees-get-vaccinated-after-insurance-surcharge-announced/QBVIXWEPGVC7FCCUTNHNW7HYTA/\", \"urlToImage\": \"https://www.ajc.com/resizer/3YhRi8O3iJ_4pd7raiyACSHHAkM=/1200x630/cloudfront-us-east-1.images.arcpublishing.com/ajc/L77V3DRMZZ62GEFHL6AMFWJDB4.jpg\", \"publishedAt\": \"2021-09-10T06:38:32Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Elliot Smith\", \"title\": \"European markets inch higher as global sentiment rebounds; Rubis down 6% - CNBC\", \"description\": \"European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\", \"url\": \"https://www.cnbc.com/2021/09/10/european-markets-investors-digest-ecb-slowdown-economic-data.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106872794-1619155878914-gettyimages-1232435505-GERMANY_FRANKFURT.jpeg?v=1619180974\", \"publishedAt\": \"2021-09-10T06:07:28Z\", \"content\": \"LONDON European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\\r\\nThe pan-European Stoxx 600 edged 0.3% higher by noon, but is sti??? [+2907 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Asian shares stem recent losses, attention on cenbank tapering - Reuters\", \"description\": \"Asian shares rallied on Friday after two days of losses, but were still in a nervous mood as global investors grapple with how best to interpret central banks' cautious moves to end stimulus, which also left currency markets quiet.\", \"url\": \"https://www.reuters.com/business/global-markets-wrapup-1-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/-RP9Ou_9bp2NAz9roikGN7wkP2w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/PKBZ37CJKFKSPNMCK7QI774IXA.jpg\", \"publishedAt\": \"2021-09-10T05:58:00Z\", \"content\": \"A men wearing a mask walk at the Shanghai Stock Exchange building at the Pudong financial district in Shanghai, China, as the country is hit by an outbreak of a new coronavirus, February 3, 2020. REU??? [+3264 chars]\" }, -{ -\"source\": { \"id\": \"google-news\", \"name\": \"Google News\" }, \"author\": null, \"title\": \"World Business Watch: US car giant Ford to cease making cars in India | Latest World English News - WION\", \"description\": null, \"url\": \"https://news.google.com/__i/rss/rd/articles/CBMiK2h0dHBzOi8vd3d3LnlvdXR1YmUuY29tL3dhdGNoP3Y9eDdURERWRFVmWU3SAQA?oc=5\", \"urlToImage\": null, \"publishedAt\": \"2021-09-10T05:17:29Z\", \"content\": null } ] }";

                    TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_title");
                    DescriptionsArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_descr");
                    ArrayListURLValues = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url");
                    ImageURLArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url_to_img");

                    RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            Toast.makeText(MContext, "Something went wrong. Loading backup from caches", Toast.LENGTH_SHORT).show();

            LoadBackupCallStringsAndLoadThem();

        }


    }
    public void InitRecyclerViewWithCountry(String ResponseJSON){
        // Append The values of each and every Title from the list
        try {
            this.TitlesArrayList.clear();
            this.TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "news_title");

            this.DescriptionsArrayList.clear();
            this.DescriptionsArrayList = DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "news_descr");

            this.ArrayListURLValues.clear();
            this.ArrayListURLValues = DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "news_url");

            this.ImageURLArrayList.clear();
            this.ImageURLArrayList = DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "news_url_to_img");


            if(DifferentFunctions.ParseJSONWorldNews(ResponseJSON, "status").get(0).equals("ok")) {
                if(TitlesArrayList.size()>20) { // Only 20 articles
                    shrinkListTo(TitlesArrayList, 20);
                    shrinkListTo(ArrayListURLValues, 20);
                    shrinkListTo(DescriptionsArrayList, 20);
                    shrinkListTo(ImageURLArrayList, 20);
                }
                if(TitlesArrayList.size() !=0) {
                    RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                }

            }else{

                Toast.makeText(MContext, "There has been an error while loading the news. Please try again", Toast.LENGTH_SHORT).show();

                // Load the backup call

                String LinearTopUS = "{ \"status\": \"ok\", \"totalResults\": 70, -\"articles\": [ -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nora Naughton, Mike Colias\", \"title\": \"Hurricane Ida???s Destruction Adds to Car Market???s Woes - The Wall Street Journal\", \"description\": \"The storm damaged vehicles across several states, sending shoppers to dealers already struggling because of the chip shortage\", \"url\": \"https://www.wsj.com/articles/hurricane-idas-destruction-adds-to-car-markets-woes-11631266201\", \"urlToImage\": \"https://images.wsj.net/im-398444/social\", \"publishedAt\": \"2021-09-10T12:09:00Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Alex Sherman\", \"title\": \"Tinder CEO Jim Lanzone will be next CEO of Yahoo following Apollo acquisition - CNBC\", \"description\": \"Apollo closed its acquisition of Yahoo from Verizon on Sept. 1.\", \"url\": \"https://www.cnbc.com/2021/09/10/tinder-ceo-jim-lanzone-will-be-next-ceo-of-yahoo-following-apollo-acquisition.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106939945-1631275692128-gettyimages-1059190354-fiuza-websummi181108_npW66.jpeg?v=1631275773\", \"publishedAt\": \"2021-09-10T12:04:13Z\", \"content\": \"Tinder CEO Jim Lanzone will be the new chief of Yahoo, according to a company memo obtained by CNBC Friday.\\r\\nYou can read the full memo from former Yahoo boss Guru Gowrappan announcing the change bel??? [+2248 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Matthew J. Belvedere\", \"title\": \"5 things to know before the stock market opens Friday - CNBC\", \"description\": \"U.S. stock futures bounced after the Dow and S&P 500 posted their fourth session of losses.\", \"url\": \"https://www.cnbc.com/2021/09/10/5-things-to-know-before-the-stock-market-opens-friday-sept-10.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106931833-1629812138310-106931833-1629738864558-gettyimages-1234833912-US_STOCKS.jpg?v=1631122596\", \"publishedAt\": \"2021-09-10T12:02:39Z\", \"content\": \"Here are the most important news, trends and analysis that investors need to start their trading day:\\r\\n1. Dow, S&amp;P 500 set to bounce after four sessions of losses\\r\\nA trader works on the floor of ??? [+4374 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Peter Schacknow\", \"title\": \"Stocks making the biggest moves premarket: Affirm, Toyota, Endo, Bausch Health and others - CNBC\", \"description\": \"These are the stocks posting the largest moves before the bell.\", \"url\": \"https://www.cnbc.com/2021/09/10/stocks-making-the-biggest-moves-premarket-affirm-toyota-endo-bausch-health-and-others.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106342643-15792830265032020toyotahighlander.jpg?v=1579283101\", \"publishedAt\": \"2021-09-10T11:55:22Z\", \"content\": \"Check out the companies making headlines before the bell:\\r\\nAffirm Holdings (AFRM) Affirm soared 22.4% in the premarket as the \\\"buy now, pay later\\\" company's revenue easily topped estimates. Active me??? [+3173 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"New York Post\" }, \"author\": \"Will Feuer\", \"title\": \"Apple fires manager for alleged leaks after she claimed harassment - New York Post \", \"description\": \"Apple has reportedly fired Ashley Gj??vik ??? months after she went public with accusations of harassment, sexism and intimidation ??? for allegedly leaking private company information.\", \"url\": \"https://nypost.com/2021/09/10/apple-fires-manager-for-alleged-leaks-after-sexism-claims/\", \"urlToImage\": \"https://nypost.com/wp-content/uploads/sites/2/2021/09/ashley-gjovik-apple-16.jpg?quality=90&strip=all&w=1024\", \"publishedAt\": \"2021-09-10T11:34:00Z\", \"content\": \"Apple has reportedly fired Ashley Gj??vik, a senior engineering program manager, for allegedly leaking private company information.\\r\\nGj??vik, who???s been at the company since 2015, told The Verge that s??? [+3010 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"nj.com\" }, \"author\": \"Jeff Goldman | NJ Advance Media for NJ.com\", \"title\": \"Police ID man, woman killed when car crashed into parked dump truck - NJ.com\", \"description\": \"The residents of the Sicklerville section of Winslow were both in their late 70s.\", \"url\": \"https://www.nj.com/camden/2021/09/police-id-man-woman-killed-when-car-crashed-into-parked-dump.html\", \"urlToImage\": \"https://www.nj.com/resizer/4tBBbnDg03a4ZU7iYdlyMqCrBEs=/1280x0/filters:focal(263x122:273x112)/cloudfront-us-east-1.images.arcpublishing.com/advancelocal/UXXXTZQJ6BBOHCKF7FQR236CWA.jpg\", \"publishedAt\": \"2021-09-10T11:30:00Z\", \"content\": \"Authorities have identified the two people killed Wednesday when their car crashed into a parked dump truck in Gloucester Township.\\r\\nDaniel Hammond, 79, of Sicklerville, was driving on Berlin Cross K??? [+657 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Joe Wallace\", \"title\": \"Stock Futures Rise; Metals Rally on Hopes of Reduced U.S.-China Tensions - The Wall Street Journal\", \"description\": \"Oil and metals prices rise on hopes of reduced geopolitical tensions after a call between President Biden and China???s Xi Jinping\", \"url\": \"https://www.wsj.com/articles/global-stock-markets-dow-update-09-10-2021-11631259867\", \"urlToImage\": \"https://images.wsj.net/im-398810/social\", \"publishedAt\": \"2021-09-10T11:23:00Z\", \"content\": null }, -{ -\"source\": { \"id\": \"fox-news\", \"name\": \"Fox News\" }, \"author\": \"Gary Gastelu\", \"title\": \"See it: Tesla Model S Plaid claims electric car lap record at benchmark track - Fox News\", \"description\": \"The Tesla Model S Plaid has set an unofficial electric car lap record at the Nurburgring Nordschleife race track. Its 7:30 time was over 11 seconds better than the Porsche Taycans.\", \"url\": \"https://www.foxnews.com/auto/tesla-model-s-plaid-electric-car-record-track\", \"urlToImage\": \"https://static.foxnews.com/foxnews.com/content/uploads/2021/09/tesla.gif\", \"publishedAt\": \"2021-09-10T11:21:14Z\", \"content\": \"The Tesla Model S Plaid has taken the Porsche Taycan Turbo's electric car lap record at Germany's Nurburgring Nordschleife race track.\\r\\nElon Musk posted a timeslip on Twitter Thursday night indicatin??? [+1395 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"MarketWatch\" }, \"author\": \"Steve Goldstein\", \"title\": \"This Wall Street firm is sticking to its S&P 500 price target. Here's why it says a correction is overdue. - MarketWatch\", \"description\": \"Critical information for the U.S. trading day\", \"url\": \"https://www.marketwatch.com/story/this-wall-street-firm-is-sticking-to-its-s-p-500-price-target-heres-why-it-says-a-correction-is-overdue-11631270767\", \"urlToImage\": \"https://images.mktw.net/im-390998/social\", \"publishedAt\": \"2021-09-10T10:46:00Z\", \"content\": \"Theres nothing like higher prices to change the minds of investors and Wall Street analysts.Witness the recent upward flurry of S&amp;P 500 \\r\\n SPX,\\r\\n -0.46%\\r\\nprice targets. Wells Fargo went from bein??? [+3922 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Yahoo Entertainment\" }, \"author\": \"Jared Blikre\", \"title\": \"Where bitcoin could become legal tender next: Grayscale CEO - Yahoo Finance\", \"description\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager...\", \"url\": \"https://finance.yahoo.com/news/where-bitcoin-may-become-legal-tender-next-grayscale-investments-ceo-103527930.html\", \"urlToImage\": \"https://s.yimg.com/ny/api/res/1.2/QwcftghAr.XOO60bVE4iNg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD04MDA-/https://s.yimg.com/os/creatr-uploaded-images/2021-09/4a8e3400-11bc-11ec-aebe-c80ebf81a461\", \"publishedAt\": \"2021-09-10T10:35:27Z\", \"content\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager believes other countries in emerging markets a??? [+2840 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"BioNTech to seek approval soon for vaccine for 5-11 year olds-Spiegel - Reuters\", \"description\": \"BioNTech <a href=\\\"https://www.reuters.com/companies/22UAy.DE\\\" target=\\\"_blank\\\">(22UAy.DE)</a> is set to request approval across the globe to use its COVID-19 vaccine in children as young as five over the next few weeks and preparations for a launch are on trac???\", \"url\": \"https://www.reuters.com/business/healthcare-pharmaceuticals/biontech-seek-approval-soon-vaccine-5-11-year-olds-spiegel-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/Cw7zdd6Cad5faTeNFsrxbWn2XnQ=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/XA3ISOF3EROIZE3YJWGU3K4IF4.jpg\", \"publishedAt\": \"2021-09-10T10:35:00Z\", \"content\": \"Syringes are seen in front of a displayed Biontech logo in this illustration taken November 10, 2020. REUTERS/Dado Ruvic/IllustrationFRANKFURT, Sept 10 (Reuters) - BioNTech (22UAy.DE) is set to reque??? [+2215 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Fox Business\" }, \"author\": \"Jack Durschlag\", \"title\": \"Bitcoin struggles above $45,000 after tumble earlier in the week - Fox Business\", \"description\": \"Bitcoin was trading 1.02% lower on Friday morning at approximately $45,900 per coin.\", \"url\": \"https://www.foxbusiness.com/markets/bitcoin-struggles-above-45000-after-tumble-earlier-in-the-week\", \"urlToImage\": \"https://a57.foxnews.com/static.foxbusiness.com/foxbusiness.com/content/uploads/2021/05/0/0/Bitcoin-Volatility.jpg?ve=1&tl=1\", \"publishedAt\": \"2021-09-10T09:36:50Z\", \"content\": \"Bitcoin was trading 1.02% lower on Friday morning.\\r\\nThe price was around $45,910 per coin, while rivals Ethereum and Dogecoin were trading around $3,370 (-4.62%) and 24.6 cents (-4.46%) per coin, res??? [+1170 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nick Timiraos\", \"title\": \"Fed Officials Prepare for November Reduction in Bond Buying - The Wall Street Journal\", \"description\": \"Phasing out the Fed???s pandemic-era stimulus by the middle of 2022 could clear the path for an interest-rate increase\", \"url\": \"https://www.wsj.com/articles/fed-officials-prepare-for-november-reduction-in-bond-buying-11631266200\", \"urlToImage\": \"https://images.wsj.net/im-398460/social\", \"publishedAt\": \"2021-09-10T09:30:00Z\", \"content\": \"Federal Reserve officials will seek to forge agreement at their coming meeting to begin scaling back their easy money policies in November.\\r\\nMany of them have said in recent interviews and public sta??? [+352 chars]\" }, -{ -\"source\": { \"id\": \"fortune\", \"name\": \"Fortune\" }, \"author\": \"Bernhard Warner\", \"title\": \"Stocks and futures rebound, crypto flat as the markets head for a down week - Fortune\", \"description\": \"U.S. futures are on the rise, following Europe and Asia higher.\", \"url\": \"https://fortune.com/2021/09/10/stocks-futures-rebound-crypto-flat-ecb-tapering/\", \"urlToImage\": \"https://content.fortune.com/wp-content/uploads/2021/09/GettyImages-1235142477.jpg?resize=1200,600\", \"publishedAt\": \"2021-09-10T09:20:47Z\", \"content\": \"Skip to Content\" }, -{ -\"source\": { \"id\": null, \"name\": \"Teslarati\" }, \"author\": \"Maria Merano\", \"title\": \"Tesla sidesteps New Mexico ban by building service center in tribal land - Teslarati\", \"description\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NM???s ban by building a service center on Native American tribal land. For the longe???\", \"url\": \"https://www.teslarati.com/tesla-outsmarts-ban-tribal-land-service-center/\", \"urlToImage\": \"https://www.teslarati.com/wp-content/uploads/2021/09/tesla-service-center-tribal-land-1024x768.jpeg\", \"publishedAt\": \"2021-09-10T08:04:31Z\", \"content\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NMs ban by building a s??? [+2038 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Harvard University to end investment in fossil fuels - Reuters\", \"description\": \"Harvard University is ending its investments in fossil fuels, the school's president said on Thursday, drawing praise from divestment activists who had long pressed the leading university to exit such holdings.\", \"url\": \"https://www.reuters.com/world/us/harvard-university-will-allow-fossil-fuel-investments-expire-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/fhLu9JYqDn4zVDewYKbNTvVw10w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/LQTLH5D4VBPPRF43TJVXNTSGGY.jpg\", \"publishedAt\": \"2021-09-10T07:57:00Z\", \"content\": \"Lawrence Bacow speaks during his inauguration as the 29th President of Harvard University in Cambridge, Massachusetts, U.S., October 5, 2018. REUTERS/Brian SnyderBOSTON, Sept 9 (Reuters) - Harvard Un??? [+1934 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Atlanta Journal Constitution\" }, \"author\": \"Kelly Yamanouchi\", \"title\": \"After announcing penalty for unvaccinated employees, Delta says more workers are getting inoculated - Atlanta Journal Constitution\", \"description\": \"Delta says more employees have gotten vaccinated after announcing a penalty for those who are unvaccinated.\", \"url\": \"https://www.ajc.com/news/business/more-delta-employees-get-vaccinated-after-insurance-surcharge-announced/QBVIXWEPGVC7FCCUTNHNW7HYTA/\", \"urlToImage\": \"https://www.ajc.com/resizer/3YhRi8O3iJ_4pd7raiyACSHHAkM=/1200x630/cloudfront-us-east-1.images.arcpublishing.com/ajc/L77V3DRMZZ62GEFHL6AMFWJDB4.jpg\", \"publishedAt\": \"2021-09-10T06:38:32Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Elliot Smith\", \"title\": \"European markets inch higher as global sentiment rebounds; Rubis down 6% - CNBC\", \"description\": \"European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\", \"url\": \"https://www.cnbc.com/2021/09/10/european-markets-investors-digest-ecb-slowdown-economic-data.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106872794-1619155878914-gettyimages-1232435505-GERMANY_FRANKFURT.jpeg?v=1619180974\", \"publishedAt\": \"2021-09-10T06:07:28Z\", \"content\": \"LONDON European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\\r\\nThe pan-European Stoxx 600 edged 0.3% higher by noon, but is sti??? [+2907 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Asian shares stem recent losses, attention on cenbank tapering - Reuters\", \"description\": \"Asian shares rallied on Friday after two days of losses, but were still in a nervous mood as global investors grapple with how best to interpret central banks' cautious moves to end stimulus, which also left currency markets quiet.\", \"url\": \"https://www.reuters.com/business/global-markets-wrapup-1-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/-RP9Ou_9bp2NAz9roikGN7wkP2w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/PKBZ37CJKFKSPNMCK7QI774IXA.jpg\", \"publishedAt\": \"2021-09-10T05:58:00Z\", \"content\": \"A men wearing a mask walk at the Shanghai Stock Exchange building at the Pudong financial district in Shanghai, China, as the country is hit by an outbreak of a new coronavirus, February 3, 2020. REU??? [+3264 chars]\" }, -{ -\"source\": { \"id\": \"google-news\", \"name\": \"Google News\" }, \"author\": null, \"title\": \"World Business Watch: US car giant Ford to cease making cars in India | Latest World English News - WION\", \"description\": null, \"url\": \"https://news.google.com/__i/rss/rd/articles/CBMiK2h0dHBzOi8vd3d3LnlvdXR1YmUuY29tL3dhdGNoP3Y9eDdURERWRFVmWU3SAQA?oc=5\", \"urlToImage\": null, \"publishedAt\": \"2021-09-10T05:17:29Z\", \"content\": null } ] }";

                TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_title");
                DescriptionsArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_descr");
                ArrayListURLValues = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url");
                ImageURLArrayList = DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url_to_img");

                Toast.makeText(MContext, "ERROR: " + TitlesArrayList.get(0), Toast.LENGTH_SHORT).show();

                RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);


            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            // Toast.makeText(MContext, "Something went wrong. Loading backup from caches", Toast.LENGTH_SHORT).show();

            LoadBackupCallStringsAndLoadThem();

        }
    }
    public void LoadBackupCallStringsAndLoadThem(){
        String LinearApple = "{ \"status\": \"ok\", \"totalResults\": 2258, -\"articles\": [ -{ -\"source\": { \"id\": \"techcrunch\", \"name\": \"TechCrunch\" }, \"author\": \"Darrell Etherington\", \"title\": \"Apple has reportedly appointed wearable chief Kevin Lynch to lead its car division\", \"description\": \"Apple has reportedly appointed a new executive to lead the development of its secretive self-driving car division. According to Bloomberg, the company has tapped Kevin Lynch to oversee Project Titan.\", \"url\": \"http://techcrunch.com/2021/09/09/apple-has-reportedly-appointed-wearable-chief-kevin-lynch-to-lead-its-car-division/\", \"urlToImage\": \"https://techcrunch.com/wp-content/uploads/2021/09/GettyImages-1147877209.jpg?w=614\", \"publishedAt\": \"2021-09-09T22:42:50Z\", \"content\": \"Apple has reportedly appointed a new executive to lead the development of its secretive self-driving car division. According to Bloomberg, the company has tapped Kevin Lynch to oversee Project Titan ??? [+798 chars]\" }, -{ -\"source\": { \"id\": \"techcrunch\", \"name\": \"TechCrunch\" }, \"author\": \"Kirsten Korosec\", \"title\": \"The 2022 Chevrolet Silverado gets a tech upgrade, hands-free trailering and a new ZR2 off-road flagship\", \"description\": \"GM unveiled Thursday the 2022 Chevrolet Silverado, a full-sized pickup truck that received a major technology upgrade, including its hands-free Super Cruise advanced driver assistance system and an infotainment system with embedded Google services as well as ???\", \"url\": \"http://techcrunch.com/2021/09/09/the-2022-chevrolet-silverado-gets-a-tech-upgrade-hands-free-trailering-and-a-new-zr2-off-road-flagship/\", \"urlToImage\": \"https://techcrunch.com/wp-content/uploads/2021/09/2022-Chevrolet-Silverado-ZR2-005.jpg?w=600\", \"publishedAt\": \"2021-09-09T22:09:39Z\", \"content\": \"GM unveiled Thursday the 2022 Chevrolet Silverado, a full-sized pickup truck that received a major technology upgrade, including its hands-free Super Cruise advanced driver assistance system and an i??? [+4858 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Gizmodo.com\" }, \"author\": \"Victoria Song\", \"title\": \"What to Expect From Apple's iPhone 13 Event (Hint: iPhones)\", \"description\": \"Product launch season is here, and that means it???s about time to gear up for Apple???s annual September event. Like last year, ongoing production issues and supply chain woes probably mean we???re going to see multiple launch events this fall. That said, we???re pr???\", \"url\": \"https://gizmodo.com/what-to-expect-from-apples-iphone-13-event-hint-iphon-1847634514\", \"urlToImage\": \"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/1c4711affe65414fe04fae2cb712fc60.png\", \"publishedAt\": \"2021-09-09T12:30:00Z\", \"content\": \"Product launch season is here, and that means its about time to gear up for Apples annual September event. Like last year, ongoing production issues and supply chain woes probably mean were going to ??? [+7619 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Gizmodo.com\" }, \"author\": \"Florence Ion\", \"title\": \"Google???s Plan to Use 120% Less Water Doesn???t Quite Add Up\", \"description\": \"Just as oil companies laud their plans to address climate change, tech companies also tend to sound off about their respective efforts to be more environmentally sound. Sometimes they don???t wholly add up either.Read more...\", \"url\": \"https://gizmodo.com/google-s-plan-to-use-120-less-water-doesn-t-quite-add-1847647300\", \"urlToImage\": \"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/936120e0d30eb01cb35b0cd49e681470.png\", \"publishedAt\": \"2021-09-09T22:55:00Z\", \"content\": \"Just as oil companies laud their plans to address climate change, tech companies also tend to sound off about their respective efforts to be more environmentally sound. Sometimes they dont wholly add??? [+4017 chars]\" }, -{ -\"source\": { \"id\": \"cnn\", \"name\": \"CNN\" }, \"author\": \"Chelsea Stone and Kristin Magaldi\", \"title\": \"The best sales to shop today: Dyson, Anker, Bed Threads and more\", \"description\": \"Today, you'll find a deal on our favorite Apple laptop, a discounted Vitamix Foodcycler and savings on a range of Anker charging accessories. All that and more below.\", \"url\": \"https://www.cnn.com/2021/09/09/cnn-underscored/best-online-sales-right-now/index.html\", \"urlToImage\": \"https://media.cnn.com/api/v1/images/stellar/prod/210909094731-0909dealslead.jpg?q=x_0,y_0,h_1080,w_1920,c_fill/w_800\", \"publishedAt\": \"2021-09-09T14:17:30Z\", \"content\": \"Today, youll find a deal on our favorite Apple laptop, a discounted Vitamix Foodcycler and savings on a range of Anker charging accessories. All that and more below.\\r\\nWhether you have a beloved furry??? [+8609 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": \"Noel Randewich\", \"title\": \"US STOCKS-S&P 500 ends down after jobless claims hit 18-month low - Reuters\", \"description\": \"The S&P 500 ended lower on Thursday after weekly jobless claims fell to a near 18-month low, allaying fears of a slowing economic recovery, but also stoking worries the Fed could move sooner than expected to scale back its accommodative policies.\", \"url\": \"https://www.reuters.com/article/usa-stocks-idUSL1N2QB2IW\", \"urlToImage\": \"https://s1.reutersmedia.net/resources_v2/images/rcom-default.png?w=800\", \"publishedAt\": \"2021-09-09T20:01:00Z\", \"content\": \"(Updates with close of market)\\r\\n* Gaming stocks drop on Beijing crackdown\\r\\n* Lululemon jumps on strong earnings forecast\\r\\n* Amazon, Apple, Microsoft weigh on indexes\\r\\nSept 9 (Reuters) - The S&amp;P 5??? [+2485 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"German-Chinese startup Agile Robots raises $220 million from investors - Reuters\", \"description\": \"German-Chinese startup Agile Robots said on Thursday that it had raised $220 million from investors led by the SoftBank Vision Fund 2 <a href=\\\"https://www.reuters.com/companies/9984.T\\\" target=\\\"_blank\\\">(9984.T)</a> and would invest the proceeds in expanding it???\", \"url\": \"https://www.reuters.com/technology/german-chinese-startup-agile-robots-raises-220-million-investors-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/pf/resources/images/reuters/reuters-default.png?d=52\", \"publishedAt\": \"2021-09-09T15:07:00Z\", \"content\": \"BERLIN, Sept 9 (Reuters) - German-Chinese startup Agile Robots said on Thursday that it had raised $220 million from investors led by the SoftBank Vision Fund 2 (9984.T) and would invest the proceeds??? [+1336 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Fed officials to sell stocks to avoid apparent conflict of interest - Reuters\", \"description\": \"Two Federal Reserve officials said on Thursday they would sell their individual stock holdings by the end of the month to address the appearance of conflicts of interest.\", \"url\": \"https://www.reuters.com/business/finance/fed-officials-sell-stocks-avoid-apparent-conflict-interest-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/resizer/be2CxZECucnfKWqKpzyjUexNVdc=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/OIN774DIAVN2ZM74PLRNBSGDIE.jpg\", \"publishedAt\": \"2021-09-09T22:45:00Z\", \"content\": \"Sept 9 (Reuters) - Two Federal Reserve officials said on Thursday they would sell their individual stock holdings by the end of the month to address the appearance of conflicts of interest.\\r\\nDallas F??? [+1905 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"JPMorgan appoints Allison Beer as CEO of Chase's card business - Reuters\", \"description\": \"JPMorgan Chase & Co <a href=\\\"https://www.reuters.com/companies/JPM.N\\\" target=\\\"_blank\\\">(JPM.N)</a> on Thursday appointed Allison Beer as the chief executive officer of cards at Chase, making her the third woman in a row to lead the cards business at the retail???\", \"url\": \"https://www.reuters.com/business/finance/jpmorgan-appoints-allison-beer-ceo-chases-card-business-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/resizer/XjNrTD_W99-hvebTAfbU4huOf14=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/AUAT6PUXMZITHKKOGSTR36AC4M.jpg\", \"publishedAt\": \"2021-09-09T17:07:00Z\", \"content\": \"A view of the exterior of the JP Morgan Chase &amp; Co. corporate headquarters in New York City May 20, 2015. REUTERS/Mike Segar//File PhotoSept 9 (Reuters) - JPMorgan Chase &amp; Co (JPM.N) on Thurs??? [+1467 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": \"Jonnelle Marte\", \"title\": \"UPDATE 1-Fed officials to sell stocks to avoid apparent conflict of interest - Reuters\", \"description\": \"Two Federal Reserve officials said on Thursday they would sell their individual stock holdings by the end of the month to address the appearance of conflicts of interest.\", \"url\": \"https://www.reuters.com/article/usa-fed-investments-idUSL1N2QB39H\", \"urlToImage\": \"https://s1.reutersmedia.net/resources_v2/images/rcom-default.png?w=800\", \"publishedAt\": \"2021-09-09T22:16:00Z\", \"content\": \"(Adds background)\\r\\nSept 9 (Reuters) - Two Federal Reserve officials said on Thursday they would sell their individual stock holdings by the end of the month to address the appearance of conflicts of ??? [+1876 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Facebook developing machine learning chip - The Information - Reuters\", \"description\": \"Facebook Inc <a href=\\\"https://www.reuters.com/companies/FB.O\\\" target=\\\"_blank\\\">(FB.O)</a> is developing a machine learning chip to handle tasks such as content recommendation to users, The Information reported on Thursday, citing two people familiar with the p???\", \"url\": \"https://www.reuters.com/technology/facebook-developing-machine-learning-chip-information-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/resizer/d0h2VGieUoa8rWcphU5zvwQoS_s=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/L2JN4NFPXBJGPJNAA2A7ECHILE.jpg\", \"publishedAt\": \"2021-09-09T14:28:00Z\", \"content\": \"A 3D-printed Facebook logo is seen placed on a keyboard in this illustration taken March 25, 2020. REUTERS/Dado Ruvic/IllustrationSept 9 (Reuters) - Facebook Inc (FB.O) is developing a machine learni??? [+1628 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": \"Reuters Staff\", \"title\": \"Futures drop ahead of jobless claims data as recovery concerns weigh - Reuters\", \"description\": \"U.S. stock index futures fell on Thursday ahead of weekly jobless claims data, as uncertainty around Federal Reserve's monetary tapering timeline and signs of slowing economic growth were a drag on investor sentiment.\", \"url\": \"https://www.reuters.com/article/usa-stocks-idUSKBN2G5143\", \"urlToImage\": \"https://static.reuters.com/resources/r/?m=02&d=20210909&t=2&i=1574298108&r=LYNXMPEH880JB&w=800\", \"publishedAt\": \"2021-09-09T11:40:00Z\", \"content\": \"By Reuters Staff\\r\\n(Reuters) - U.S. stock index futures fell on Thursday ahead of weekly jobless claims data, as uncertainty around Federal Reserves monetary tapering timeline and signs of slowing eco??? [+1957 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Futures drop ahead of jobless claims data as recovery concerns weigh - Reuters\", \"description\": \"U.S. stock index futures fell on Thursday ahead of weekly jobless claims data, as uncertainty around Federal Reserve's monetary tapering timeline and signs of slowing economic growth were a drag on investor sentiment.\", \"url\": \"https://www.reuters.com/business/futures-drop-ahead-jobless-claims-data-recovery-concerns-weigh-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/resizer/phF82-TLV_-GG5xOfUjk3AumQcQ=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/YI5K4IH4I5MGZKCYHLPETHES4Y.jpg\", \"publishedAt\": \"2021-09-09T11:39:00Z\", \"content\": \"A trader works at the New York Stock Exchange (NYSE) in Manhattan, New York City, U.S., August 19, 2021. REUTERS/Andrew KellySept 9 (Reuters) - U.S. stock index futures fell on Thursday ahead of week??? [+2199 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Nobel laureate Doudna's biotech Mammoth hits over $1 bln in valuation - Reuters\", \"description\": \"Mammoth Biosciences, co-founded by Nobel laureate Jennifer Doudna, touched a valuation of more than $1 billion after raising $150 million in its latest funding round.\", \"url\": \"https://www.reuters.com/business/healthcare-pharmaceuticals/nobel-laureate-doudnas-biotech-mammoth-hits-over-1-bln-valuation-2021-09-09/\", \"urlToImage\": \"https://www.reuters.com/resizer/0RbTow4kFYV7t98nq_zP7MM-EDQ=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/QHVQL2BFERL3DHE7NTCQDSDFNE.jpg\", \"publishedAt\": \"2021-09-09T13:02:00Z\", \"content\": \"Nobel Prize in chemistry winner, American scientist Jennifer Doudna poses with her medal outside her house in Berkeley, California, U.S., in this undated handout image obtained by Reuters on December??? [+2120 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": \"Marc Jones\", \"title\": \"Stocks extend slide as ECB weighs up taper plan - Reuters\", \"description\": \"World share markets dipped for a third straight day on Thursday as the European Central Bank took its first tentative step in withdrawing stimulus and Beijing took another swipe at its big tech firms.\", \"url\": \"https://www.reuters.com/article/global-markets-idUSKBN2G505A\", \"urlToImage\": \"https://static.reuters.com/resources/r/?m=02&d=20210909&t=2&i=1574283474&r=LYNXMPEH880CM&w=800\", \"publishedAt\": \"2021-09-09T13:00:00Z\", \"content\": \"LONDON (Reuters) - World share markets dipped for a third straight day on Thursday as the European Central Bank took its first tentative step in withdrawing stimulus and Beijing took another swipe at??? [+4085 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Android Central\" }, \"author\": \"Babu Mohan\", \"title\": \"Google faces new EU probe over forcing Android OEMs to use Google Assistant\", \"description\": \"Google may have to pay another hefty fine if it is found guilty.\\n\\n\\n\\nWhat you need to know\\n\\n\\nGoogle is reportedly under renewed EU antitrust investigation for forcing Android OEMs to use Google Assistant as the default voice assistant on their devices.\\nEU regu???\", \"url\": \"https://www.androidcentral.com/google-faces-new-eu-probe-over-forcing-android-oems-use-google-assistant\", \"urlToImage\": \"https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2021/07/google-assistant-on-shelf.jpg\", \"publishedAt\": \"2021-09-09T12:38:30Z\", \"content\": \"Google is facing a renewed antitrust investigation from EU regulators over \\\"possibly forcing\\\" device makers into using Google Assistant as the default voice assistant on Android devices, according to??? [+1779 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Android Central\" }, \"author\": \"Chris Wedel\", \"title\": \"Android Auto is OK, but Google Assistant integration would make it better\", \"description\": \"Bringing some of Google Assistant Driving mode features into Android Auto would be a big boost for the automobile infotainment software.\\n\\n\\n\\nSoftware in automobiles that restricts phone usage while driving is a good thing ??? period. However, not all vehicles ar???\", \"url\": \"https://www.androidcentral.com/google-assistant-driving-mode-replacing-android-auto-phones-are-cars-next\", \"urlToImage\": \"https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2021/09/android-auto-lifestyle-07.jpg\", \"publishedAt\": \"2021-09-09T10:30:02Z\", \"content\": \"Software in automobiles that restricts phone usage while driving is a good thing period. However, not all vehicles are compatible with Apple Car Play or Android Auto. That's what Android Auto for pho??? [+3779 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Android Central\" }, \"author\": \"Christine Persaud\", \"title\": \"Here are the best outdoor smart Wi-Fi plugs you can buy in 2021\", \"description\": \"Smart Wi-Fi plugs are a dime a dozen, and they are wonderfully affordable devices. The best smart Wi-Fi plugs let you effectively turn just about anything plugged into them \\\"smart.\\\" Once plugged in, you can control the device, be it a coffee maker, fan, or la???\", \"url\": \"https://www.androidcentral.com/best-outdoor-smart-wi-fi-plugs\", \"urlToImage\": \"https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2021/05/ring-outdoor-smart-plug-outlets.jpg\", \"publishedAt\": \"2021-09-09T12:00:03Z\", \"content\": \"Best\\r\\noutdoor smart Wi-Fi plugs\\r\\nAndroid Central2021\\r\\nSmart Wi-Fi plugs are a dime a dozen, and they are wonderfully affordable devices. The best smart Wi-Fi plugs let you effectively turn just about??? [+5193 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"The Guardian\" }, \"author\": \"Presented by Max Rushden with Faye Carruthers, Rory Smith, Jonathan Liew and Jim Burke\", \"title\": \"World Cup qualifiers, subs and the 3pm blackout ??? Football Weekly Extra\", \"description\": \"Max Rushden is joined by Faye Carruthers, Jonathan Liew, Rory Smith and Jim Burke to review the midweek internationals and preview the weekendRate, review, share on Apple Podcasts, Soundcloud, Audioboom, Mixcloud, Acast and Stitcher, and join the conversation???\", \"url\": \"https://www.theguardian.com/football/audio/2021/sep/09/world-cup-qualifiers-premier-league-football-weekly-podcast\", \"urlToImage\": \"https://i.guim.co.uk/img/media/2d894f42082162f4e038d2a89d191760fb9e0933/0_168_5078_3048/master/5078.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&enable=upscale&s=d99a65e5cd78dfd0371a7fb9a26e9802\", \"publishedAt\": \"2021-09-09T13:22:06Z\", \"content\": \"Rate, review, share on Apple Podcasts, Soundcloud, Audioboom, Mixcloud, Acast and Stitcher, and join the conversation on Facebook, Twitter and email.\\r\\n Finally, the end of the international break! Ha??? [+645 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"MacRumors\" }, \"author\": \"Juli Clover\", \"title\": \"Apple Watch Chief Kevin Lynch Now Leading Apple Car Efforts\", \"description\": \"Apple VP of technology Kevin Lynch is taking over the Apple Car project and will be replacing Doug Field, who is moving to Ford, reports Bloomberg.\\n\\n\\n\\n\\n\\nLynch first joined the Apple Car team in July, with reports suggesting he had been called on to help lead ???\", \"url\": \"https://www.macrumors.com/2021/09/09/kevin-lynch-apple-car-project/\", \"urlToImage\": \"https://images.macrumors.com/t/D62d9vSY9oVhZtKI88-ipNircXY=/2500x/https://images.macrumors.com/article-new/2020/12/Apple-car-wheel-icon-feature-yellow.jpg\", \"publishedAt\": \"2021-09-09T19:28:11Z\", \"content\": \"Apple VP of technology Kevin Lynch is taking over the Apple Car project and will be replacing Doug Field, who is moving to Ford, reports Bloomberg.\\r\\nLynch first joined the Apple Car team in July, wit??? [+1680 chars]\" } ] }";
        String LinearTopUS = "{ \"status\": \"ok\", \"totalResults\": 70, -\"articles\": [ -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nora Naughton, Mike Colias\", \"title\": \"Hurricane Ida???s Destruction Adds to Car Market???s Woes - The Wall Street Journal\", \"description\": \"The storm damaged vehicles across several states, sending shoppers to dealers already struggling because of the chip shortage\", \"url\": \"https://www.wsj.com/articles/hurricane-idas-destruction-adds-to-car-markets-woes-11631266201\", \"urlToImage\": \"https://images.wsj.net/im-398444/social\", \"publishedAt\": \"2021-09-10T12:09:00Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Alex Sherman\", \"title\": \"Tinder CEO Jim Lanzone will be next CEO of Yahoo following Apollo acquisition - CNBC\", \"description\": \"Apollo closed its acquisition of Yahoo from Verizon on Sept. 1.\", \"url\": \"https://www.cnbc.com/2021/09/10/tinder-ceo-jim-lanzone-will-be-next-ceo-of-yahoo-following-apollo-acquisition.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106939945-1631275692128-gettyimages-1059190354-fiuza-websummi181108_npW66.jpeg?v=1631275773\", \"publishedAt\": \"2021-09-10T12:04:13Z\", \"content\": \"Tinder CEO Jim Lanzone will be the new chief of Yahoo, according to a company memo obtained by CNBC Friday.\\r\\nYou can read the full memo from former Yahoo boss Guru Gowrappan announcing the change bel??? [+2248 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Matthew J. Belvedere\", \"title\": \"5 things to know before the stock market opens Friday - CNBC\", \"description\": \"U.S. stock futures bounced after the Dow and S&P 500 posted their fourth session of losses.\", \"url\": \"https://www.cnbc.com/2021/09/10/5-things-to-know-before-the-stock-market-opens-friday-sept-10.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106931833-1629812138310-106931833-1629738864558-gettyimages-1234833912-US_STOCKS.jpg?v=1631122596\", \"publishedAt\": \"2021-09-10T12:02:39Z\", \"content\": \"Here are the most important news, trends and analysis that investors need to start their trading day:\\r\\n1. Dow, S&amp;P 500 set to bounce after four sessions of losses\\r\\nA trader works on the floor of ??? [+4374 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Peter Schacknow\", \"title\": \"Stocks making the biggest moves premarket: Affirm, Toyota, Endo, Bausch Health and others - CNBC\", \"description\": \"These are the stocks posting the largest moves before the bell.\", \"url\": \"https://www.cnbc.com/2021/09/10/stocks-making-the-biggest-moves-premarket-affirm-toyota-endo-bausch-health-and-others.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106342643-15792830265032020toyotahighlander.jpg?v=1579283101\", \"publishedAt\": \"2021-09-10T11:55:22Z\", \"content\": \"Check out the companies making headlines before the bell:\\r\\nAffirm Holdings (AFRM) Affirm soared 22.4% in the premarket as the \\\"buy now, pay later\\\" company's revenue easily topped estimates. Active me??? [+3173 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"New York Post\" }, \"author\": \"Will Feuer\", \"title\": \"Apple fires manager for alleged leaks after she claimed harassment - New York Post \", \"description\": \"Apple has reportedly fired Ashley Gj??vik ??? months after she went public with accusations of harassment, sexism and intimidation ??? for allegedly leaking private company information.\", \"url\": \"https://nypost.com/2021/09/10/apple-fires-manager-for-alleged-leaks-after-sexism-claims/\", \"urlToImage\": \"https://nypost.com/wp-content/uploads/sites/2/2021/09/ashley-gjovik-apple-16.jpg?quality=90&strip=all&w=1024\", \"publishedAt\": \"2021-09-10T11:34:00Z\", \"content\": \"Apple has reportedly fired Ashley Gj??vik, a senior engineering program manager, for allegedly leaking private company information.\\r\\nGj??vik, who???s been at the company since 2015, told The Verge that s??? [+3010 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"nj.com\" }, \"author\": \"Jeff Goldman | NJ Advance Media for NJ.com\", \"title\": \"Police ID man, woman killed when car crashed into parked dump truck - NJ.com\", \"description\": \"The residents of the Sicklerville section of Winslow were both in their late 70s.\", \"url\": \"https://www.nj.com/camden/2021/09/police-id-man-woman-killed-when-car-crashed-into-parked-dump.html\", \"urlToImage\": \"https://www.nj.com/resizer/4tBBbnDg03a4ZU7iYdlyMqCrBEs=/1280x0/filters:focal(263x122:273x112)/cloudfront-us-east-1.images.arcpublishing.com/advancelocal/UXXXTZQJ6BBOHCKF7FQR236CWA.jpg\", \"publishedAt\": \"2021-09-10T11:30:00Z\", \"content\": \"Authorities have identified the two people killed Wednesday when their car crashed into a parked dump truck in Gloucester Township.\\r\\nDaniel Hammond, 79, of Sicklerville, was driving on Berlin Cross K??? [+657 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Joe Wallace\", \"title\": \"Stock Futures Rise; Metals Rally on Hopes of Reduced U.S.-China Tensions - The Wall Street Journal\", \"description\": \"Oil and metals prices rise on hopes of reduced geopolitical tensions after a call between President Biden and China???s Xi Jinping\", \"url\": \"https://www.wsj.com/articles/global-stock-markets-dow-update-09-10-2021-11631259867\", \"urlToImage\": \"https://images.wsj.net/im-398810/social\", \"publishedAt\": \"2021-09-10T11:23:00Z\", \"content\": null }, -{ -\"source\": { \"id\": \"fox-news\", \"name\": \"Fox News\" }, \"author\": \"Gary Gastelu\", \"title\": \"See it: Tesla Model S Plaid claims electric car lap record at benchmark track - Fox News\", \"description\": \"The Tesla Model S Plaid has set an unofficial electric car lap record at the Nurburgring Nordschleife race track. Its 7:30 time was over 11 seconds better than the Porsche Taycans.\", \"url\": \"https://www.foxnews.com/auto/tesla-model-s-plaid-electric-car-record-track\", \"urlToImage\": \"https://static.foxnews.com/foxnews.com/content/uploads/2021/09/tesla.gif\", \"publishedAt\": \"2021-09-10T11:21:14Z\", \"content\": \"The Tesla Model S Plaid has taken the Porsche Taycan Turbo's electric car lap record at Germany's Nurburgring Nordschleife race track.\\r\\nElon Musk posted a timeslip on Twitter Thursday night indicatin??? [+1395 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"MarketWatch\" }, \"author\": \"Steve Goldstein\", \"title\": \"This Wall Street firm is sticking to its S&P 500 price target. Here's why it says a correction is overdue. - MarketWatch\", \"description\": \"Critical information for the U.S. trading day\", \"url\": \"https://www.marketwatch.com/story/this-wall-street-firm-is-sticking-to-its-s-p-500-price-target-heres-why-it-says-a-correction-is-overdue-11631270767\", \"urlToImage\": \"https://images.mktw.net/im-390998/social\", \"publishedAt\": \"2021-09-10T10:46:00Z\", \"content\": \"Theres nothing like higher prices to change the minds of investors and Wall Street analysts.Witness the recent upward flurry of S&amp;P 500 \\r\\n SPX,\\r\\n -0.46%\\r\\nprice targets. Wells Fargo went from bein??? [+3922 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Yahoo Entertainment\" }, \"author\": \"Jared Blikre\", \"title\": \"Where bitcoin could become legal tender next: Grayscale CEO - Yahoo Finance\", \"description\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager...\", \"url\": \"https://finance.yahoo.com/news/where-bitcoin-may-become-legal-tender-next-grayscale-investments-ceo-103527930.html\", \"urlToImage\": \"https://s.yimg.com/ny/api/res/1.2/QwcftghAr.XOO60bVE4iNg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD04MDA-/https://s.yimg.com/os/creatr-uploaded-images/2021-09/4a8e3400-11bc-11ec-aebe-c80ebf81a461\", \"publishedAt\": \"2021-09-10T10:35:27Z\", \"content\": \"Cryptocurrency bulls are lauding the decision by El Salvador to become the first country to accept bitcoin as legal tender. And one bitcoin fund manager believes other countries in emerging markets a??? [+2840 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"BioNTech to seek approval soon for vaccine for 5-11 year olds-Spiegel - Reuters\", \"description\": \"BioNTech <a href=\\\"https://www.reuters.com/companies/22UAy.DE\\\" target=\\\"_blank\\\">(22UAy.DE)</a> is set to request approval across the globe to use its COVID-19 vaccine in children as young as five over the next few weeks and preparations for a launch are on trac???\", \"url\": \"https://www.reuters.com/business/healthcare-pharmaceuticals/biontech-seek-approval-soon-vaccine-5-11-year-olds-spiegel-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/Cw7zdd6Cad5faTeNFsrxbWn2XnQ=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/XA3ISOF3EROIZE3YJWGU3K4IF4.jpg\", \"publishedAt\": \"2021-09-10T10:35:00Z\", \"content\": \"Syringes are seen in front of a displayed Biontech logo in this illustration taken November 10, 2020. REUTERS/Dado Ruvic/IllustrationFRANKFURT, Sept 10 (Reuters) - BioNTech (22UAy.DE) is set to reque??? [+2215 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Fox Business\" }, \"author\": \"Jack Durschlag\", \"title\": \"Bitcoin struggles above $45,000 after tumble earlier in the week - Fox Business\", \"description\": \"Bitcoin was trading 1.02% lower on Friday morning at approximately $45,900 per coin.\", \"url\": \"https://www.foxbusiness.com/markets/bitcoin-struggles-above-45000-after-tumble-earlier-in-the-week\", \"urlToImage\": \"https://a57.foxnews.com/static.foxbusiness.com/foxbusiness.com/content/uploads/2021/05/0/0/Bitcoin-Volatility.jpg?ve=1&tl=1\", \"publishedAt\": \"2021-09-10T09:36:50Z\", \"content\": \"Bitcoin was trading 1.02% lower on Friday morning.\\r\\nThe price was around $45,910 per coin, while rivals Ethereum and Dogecoin were trading around $3,370 (-4.62%) and 24.6 cents (-4.46%) per coin, res??? [+1170 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Nick Timiraos\", \"title\": \"Fed Officials Prepare for November Reduction in Bond Buying - The Wall Street Journal\", \"description\": \"Phasing out the Fed???s pandemic-era stimulus by the middle of 2022 could clear the path for an interest-rate increase\", \"url\": \"https://www.wsj.com/articles/fed-officials-prepare-for-november-reduction-in-bond-buying-11631266200\", \"urlToImage\": \"https://images.wsj.net/im-398460/social\", \"publishedAt\": \"2021-09-10T09:30:00Z\", \"content\": \"Federal Reserve officials will seek to forge agreement at their coming meeting to begin scaling back their easy money policies in November.\\r\\nMany of them have said in recent interviews and public sta??? [+352 chars]\" }, -{ -\"source\": { \"id\": \"fortune\", \"name\": \"Fortune\" }, \"author\": \"Bernhard Warner\", \"title\": \"Stocks and futures rebound, crypto flat as the markets head for a down week - Fortune\", \"description\": \"U.S. futures are on the rise, following Europe and Asia higher.\", \"url\": \"https://fortune.com/2021/09/10/stocks-futures-rebound-crypto-flat-ecb-tapering/\", \"urlToImage\": \"https://content.fortune.com/wp-content/uploads/2021/09/GettyImages-1235142477.jpg?resize=1200,600\", \"publishedAt\": \"2021-09-10T09:20:47Z\", \"content\": \"Skip to Content\" }, -{ -\"source\": { \"id\": null, \"name\": \"Teslarati\" }, \"author\": \"Maria Merano\", \"title\": \"Tesla sidesteps New Mexico ban by building service center in tribal land - Teslarati\", \"description\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NM???s ban by building a service center on Native American tribal land. For the longe???\", \"url\": \"https://www.teslarati.com/tesla-outsmarts-ban-tribal-land-service-center/\", \"urlToImage\": \"https://www.teslarati.com/wp-content/uploads/2021/09/tesla-service-center-tribal-land-1024x768.jpeg\", \"publishedAt\": \"2021-09-10T08:04:31Z\", \"content\": \"Tesla has effectively sidestepped a New Mexico law that was barring the company from operating a service center for EV owners in the state. The company was able to work around NMs ban by building a s??? [+2038 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Harvard University to end investment in fossil fuels - Reuters\", \"description\": \"Harvard University is ending its investments in fossil fuels, the school's president said on Thursday, drawing praise from divestment activists who had long pressed the leading university to exit such holdings.\", \"url\": \"https://www.reuters.com/world/us/harvard-university-will-allow-fossil-fuel-investments-expire-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/fhLu9JYqDn4zVDewYKbNTvVw10w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/LQTLH5D4VBPPRF43TJVXNTSGGY.jpg\", \"publishedAt\": \"2021-09-10T07:57:00Z\", \"content\": \"Lawrence Bacow speaks during his inauguration as the 29th President of Harvard University in Cambridge, Massachusetts, U.S., October 5, 2018. REUTERS/Brian SnyderBOSTON, Sept 9 (Reuters) - Harvard Un??? [+1934 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Atlanta Journal Constitution\" }, \"author\": \"Kelly Yamanouchi\", \"title\": \"After announcing penalty for unvaccinated employees, Delta says more workers are getting inoculated - Atlanta Journal Constitution\", \"description\": \"Delta says more employees have gotten vaccinated after announcing a penalty for those who are unvaccinated.\", \"url\": \"https://www.ajc.com/news/business/more-delta-employees-get-vaccinated-after-insurance-surcharge-announced/QBVIXWEPGVC7FCCUTNHNW7HYTA/\", \"urlToImage\": \"https://www.ajc.com/resizer/3YhRi8O3iJ_4pd7raiyACSHHAkM=/1200x630/cloudfront-us-east-1.images.arcpublishing.com/ajc/L77V3DRMZZ62GEFHL6AMFWJDB4.jpg\", \"publishedAt\": \"2021-09-10T06:38:32Z\", \"content\": null }, -{ -\"source\": { \"id\": null, \"name\": \"CNBC\" }, \"author\": \"Elliot Smith\", \"title\": \"European markets inch higher as global sentiment rebounds; Rubis down 6% - CNBC\", \"description\": \"European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\", \"url\": \"https://www.cnbc.com/2021/09/10/european-markets-investors-digest-ecb-slowdown-economic-data.html\", \"urlToImage\": \"https://image.cnbcfm.com/api/v1/image/106872794-1619155878914-gettyimages-1232435505-GERMANY_FRANKFURT.jpeg?v=1619180974\", \"publishedAt\": \"2021-09-10T06:07:28Z\", \"content\": \"LONDON European markets were cautiously higher on Friday, tracking global counterparts as sentiment rebounded following a rocky week.\\r\\nThe pan-European Stoxx 600 edged 0.3% higher by noon, but is sti??? [+2907 chars]\" }, -{ -\"source\": { \"id\": \"reuters\", \"name\": \"Reuters\" }, \"author\": null, \"title\": \"Asian shares stem recent losses, attention on cenbank tapering - Reuters\", \"description\": \"Asian shares rallied on Friday after two days of losses, but were still in a nervous mood as global investors grapple with how best to interpret central banks' cautious moves to end stimulus, which also left currency markets quiet.\", \"url\": \"https://www.reuters.com/business/global-markets-wrapup-1-2021-09-10/\", \"urlToImage\": \"https://www.reuters.com/resizer/-RP9Ou_9bp2NAz9roikGN7wkP2w=/1200x628/smart/filters:quality(80)/cloudfront-us-east-2.images.arcpublishing.com/reuters/PKBZ37CJKFKSPNMCK7QI774IXA.jpg\", \"publishedAt\": \"2021-09-10T05:58:00Z\", \"content\": \"A men wearing a mask walk at the Shanghai Stock Exchange building at the Pudong financial district in Shanghai, China, as the country is hit by an outbreak of a new coronavirus, February 3, 2020. REU??? [+3264 chars]\" }, -{ -\"source\": { \"id\": \"google-news\", \"name\": \"Google News\" }, \"author\": null, \"title\": \"World Business Watch: US car giant Ford to cease making cars in India | Latest World English News - WION\", \"description\": null, \"url\": \"https://news.google.com/__i/rss/rd/articles/CBMiK2h0dHBzOi8vd3d3LnlvdXR1YmUuY29tL3dhdGNoP3Y9eDdURERWRFVmWU3SAQA?oc=5\", \"urlToImage\": null, \"publishedAt\": \"2021-09-10T05:17:29Z\", \"content\": null } ] }";
        String LinearTesla = "{ \"status\": \"ok\", \"totalResults\": 5834, -\"articles\": [ -{ -\"source\": { \"id\": null, \"name\": \"Adafruit.com\" }, \"author\": \"Caeley\", \"title\": \"Engineering YouTuber Ian Charnas builds a real-life Thor Hammer (and you can win it!) @reinventedmag\", \"description\": \"There???s something special about those cosplayers who go the extra mile. Or in this case, the extra 400,000 volts!! Engineering YouTuber Ian Charnas did a cosplay with real lightning provided by twin musical tesla coils. His video includes a high-voltage cover???\", \"url\": \"https://blog.adafruit.com/2021/09/10/engineering-youtuber-ian-charnas-builds-a-real-life-thor-hammer-and-you-can-win-it-reinventedmag/\", \"urlToImage\": \"https://cdn-blog.adafruit.com/uploads/2021/09/Untitled-69.png\", \"publishedAt\": \"2021-09-10T13:20:57Z\", \"content\": \"September 10, 2021 AT 9:20 am\\r\\nEngineering YouTuber Ian Charnas builds a real-life Thor Hammer (and you can win it!) @reinventedmag\\r\\nThere???s something special about those cosplayers who go the extra ??? [+3265 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Forbes\" }, \"author\": \"Trefis Team, Contributor, \\n Trefis Team, Contributor\\n https://www.forbes.com/sites/greatspeculations/people/trefis/\", \"title\": \"How Will Toyota???s Big Battery Bet Impact QuantumScape Stock\", \"description\": \"QuantumScape, a startup that is working on solid-state lithium metal batteries for electric vehicles, has seen its stock decline by about 6% over the last week (five trading days) compared to the S&P 500 which remained roughly flat over the same period. The d???\", \"url\": \"https://www.forbes.com/sites/greatspeculations/2021/09/10/how-will-toyotas-big-battery-bet-impact-quantumscape-stock/\", \"urlToImage\": \"https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F6062ce859cc606542a1e11ad%2F0x0.jpg\", \"publishedAt\": \"2021-09-10T13:00:35Z\", \"content\": \"UKRAINE - 2021/03/25: In this photo illustration the QuantumScape logo is seen on a smartphone and a ... [+] pc screen. (Photo Illustration by Pavlo Gonchar/SOPA Images/LightRocket via Getty Images)\\r??? [+18596 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Seeking Alpha\" }, \"author\": \"Steven Fiorillo\", \"title\": \"Tesla: Sometimes A Great Company Isn't A Great Stock\", \"description\": \"Sometimes there's a disconnect between a company that is doing great things and reporting good numbers and its stock price. Read why Tesla is an example of this.\", \"url\": \"https://seekingalpha.com/article/4454347-tesla-sometimes-a-great-company-isnt-a-great-stock\", \"urlToImage\": \"https://static.seekingalpha.com/cdn/s3/uploads/getty_images/1140204814/large_image_1140204814.jpg\", \"publishedAt\": \"2021-09-10T13:00:00Z\", \"content\": \"jetcityimage/iStock Editorial via Getty Images\\r\\nIt's hard to deny that Tesla (TSLA) isn't a revolutionary company pushing the boundaries of evolution. In a short period, TSLA has created a complete e??? [+21063 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"The Conversation Africa\" }, \"author\": \"Tom Stacey, Senior Lecturer in Operations and Supply Chain Management, Anglia Ruskin University\", \"title\": \"China is on course to build the best cars in the world\", \"description\": \"China used to only make Soviet cars under licence. Now it???s taking on Tesla.\", \"url\": \"https://theconversation.com/china-is-on-course-to-build-the-best-cars-in-the-world-167661\", \"urlToImage\": \"https://images.theconversation.com/files/420477/original/file-20210910-24-tx4hiu.jpeg?ixlib=rb-1.1.0&rect=88%2C280%2C3586%2C1790&q=45&auto=format&w=1356&h=668&fit=crop\", \"publishedAt\": \"2021-09-10T12:55:09Z\", \"content\": \"Europeans and other western nations have dominated automotive excellence for over a century. Whether it is the satisfying thud of the door closing on a Volkswagen from Wolfsburg, or the beauty of a F??? [+6425 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Telegraph.co.uk\" }, \"author\": \"Telegraph Money\", \"title\": \"How to buy cryptocurrency with PayPal - and the mistakes to avoid\", \"description\": \"Bitcoin and other cryptocurrencies have become popular among investors\", \"url\": \"https://www.telegraph.co.uk/money/consumer-affairs/how-buy-cryptocurrency-paypal-work-mistakes-avoid/\", \"urlToImage\": \"https://www.telegraph.co.uk/content/dam/money/Spark/etoro/graphic-image-showing-hand-reaching-down-to-select-bitcoin-symbol-from-among-other-currencies_trans_NvBQzQNjv4Bq9LxyVVUQ_NBpVuUUWLgYBLYqlijD46vURuylHWnsnAE.jpg?impolicy=logo-overlay\", \"publishedAt\": \"2021-09-10T12:51:15Z\", \"content\": \"Cryptocurrency is a kind of digital money that is designed to be secure and, in many cases, anonymous. It can be bought and sold using the hundreds of online exchanges that are available to investors??? [+5550 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Adafruit.com\" }, \"author\": \"Anne Barela\", \"title\": \"New superconducting magnet breaks magnetic field strength records, paving the way for fusion energy #Science @physorg_com\", \"description\": \"On Sept. 5, for the first time, a large high-temperature superconducting electromagnet was ramped up to a field strength of 20 tesla, the most powerful magnetic field of its kind ever created on Earth. That successful demonstration helps resolve the greatest ???\", \"url\": \"https://blog.adafruit.com/2021/09/10/new-superconducting-magnet-breaks-magnetic-field-strength-records-paving-the-way-for-fusion-energy-science-physorg_com/\", \"urlToImage\": \"https://cdn-blog.adafruit.com/uploads/2021/09/Untitled-71.png\", \"publishedAt\": \"2021-09-10T12:50:52Z\", \"content\": \"September 10, 2021 AT 8:50 am\\r\\nNew superconducting magnet breaks magnetic field strength records, paving the way for fusion energy #Science @physorg_com\\r\\nOn Sept. 5, for the first time, a large high-??? [+4355 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"24/7 Wall St.\" }, \"author\": \"247chrislange\", \"title\": \"Cathie Wood???s ARK Invest Dumps Over 65,000 Shares of Iridium Communications Stock\", \"description\": \"A couple of Cathie Wood's ARK Invest funds sold over 65,000 shares of Iridium Communications on September 9.\", \"url\": \"https://247wallst.com/investing/2021/09/10/cathie-woods-ark-invest-dumps-over-65000-shares-of-iridium-communications-stock/\", \"urlToImage\": \"https://247wallst.com/wp-content/uploads/2019/03/imageforentry15-mou.jpg\", \"publishedAt\": \"2021-09-10T12:35:09Z\", \"content\": \"A couple of ARK Invest exchange-traded funds run by ETF star Cathie Wood made a huge sale on Thursday. Specifically, these funds sold over 65,000 shares of Iridium Communications Inc. (NASDAQ: IRDM) ??? [+2411 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"CarScoops\" }, \"author\": \"Sam D. Smith\", \"title\": \"New 2022 Chevrolet Silverado, Tesla Breaks ???Ring EV Record, And Ford Exits India After Bleeding $2Bn: Your Morning Brief\", \"description\": \"Plus, New York States bans new ICE vehicles from 2035, and VW's ID Sedan mule spied.\", \"url\": \"https://www.carscoops.com/2021/09/new-2022-chevrolet-silverado-tesla-breaks-ring-ev-record-and-ford-exits-india-after-bleeding-2bn-your-morning-brief/\", \"urlToImage\": \"https://www.carscoops.com/wp-content/uploads/2021/09/Sep-10th.jpg\", \"publishedAt\": \"2021-09-10T12:33:53Z\", \"content\": \"Good morning and welcome to our daily digest of automotive news from around the globe, starting with\\r\\nNew York State To Ban Sales Of New ICE-Powered Cars And Light-Duty Trucks By 2035\\r\\nIn another dea??? [+5133 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Forbes\" }, \"author\": \"Alistair Charlton, Senior Contributor, \\n Alistair Charlton, Senior Contributor\\n https://www.forbes.com/sites/alistaircharlton/\", \"title\": \"Watch The Tesla Model S Plaid Set A New EV Nurburgring Lap Record\", \"description\": \"A Tesla Model S Plaid has set a new lap record for electric production cars at the Nurburgring, with an official lap time of 7min 30.909secs.\", \"url\": \"https://www.forbes.com/sites/alistaircharlton/2021/09/10/watch-the-tesla-model-s-plaid-set-a-new-ev-nurburgring-lap-record/\", \"urlToImage\": \"https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F613b4f9d4a5032c30b37aa28%2F0x0.jpg\", \"publishedAt\": \"2021-09-10T12:32:14Z\", \"content\": \"The Tesla Model S Plaid has a three-motor drivetrain producing over 1,000 horsepower\\r\\nTesla\\r\\nA Tesla Model S Plaid has set a new lap record for electric production cars at the Nurburgring, with an of??? [+1395 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"1000mg.jp\" }, \"author\": \"tomato\", \"title\": \"??????????????????????????????????????????Model S Plaid???????????????????????????????????????EV????????????????????????\", \"description\": \"??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????S???????????????????????????????????????????????????????????????7???30???909???7???35???579??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????260km/h????????????????????????????????????????????????????????????????????????????????????????????? Model S Plaid at N??rburgring ???Twitter??? Tesla Model S Plaid just set official ???\", \"url\": \"http://1000mg.jp/172359/\", \"urlToImage\": \"http://1000mg.jp/wp-content/uploads/2021/09/7f9d65cdf0ce59f2d1a7340f373d3b27.jpg\", \"publishedAt\": \"2021-09-10T12:30:37Z\", \"content\": \"Tesla Model S Plaid just set official world speed record for a production electric car at Nurburgring. Completely unmodified, directly from factory. pic.twitter.com/AaiFtfW5Ht\\r\\n??? Elon Musk (@elonmusk??? [+18 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Motley Fool\" }, \"author\": \"newsfeedback@fool.com (Rekha Khandelwal)\", \"title\": \"2 Electric Vehicle Stocks That Turned $10,000 Into More Than $60,000\", \"description\": \"One of them turned $10,000 into $130,000.\", \"url\": \"https://www.fool.com/investing/2021/09/10/2-electric-vehicle-stocks-that-turned-10000-into-m/\", \"urlToImage\": \"https://g.foolcdn.com/editorial/images/642171/man-charging-electric-car-in-domestic-garden-woman-and-boy-looking-at-him-and-smiling.jpg\", \"publishedAt\": \"2021-09-10T12:17:00Z\", \"content\": \"Electric vehicle (EV) stocks have seen some correction in this year. Despite that, investors who bought the right stocks have seen their investments rise multiple times. Let's look at two EV stocks t??? [+4056 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Globalsecuritymag.com\" }, \"author\": null, \"title\": \"August 2021's Most Wanted Malware: Formbook Climbs into First Place\", \"description\": \"Check Point Research (CPR), the Threat Intelligence arm of Check Point?? Software Technologies Ltd. has published its latest Global Threat Index for August 2021. Researchers report that Formbook is now the most prevalent malware, taking over Trickbot, which ha???\", \"url\": \"https://www.globalsecuritymag.com/August-2021-s-Most-Wanted-Malware,20210910,115902.html\", \"urlToImage\": \"http://www.globalsecuritymag.com/squelettes/media/logo.png\", \"publishedAt\": \"2021-09-10T12:15:00Z\", \"content\": \"Check Point Research (CPR), the Threat Intelligence arm of Check Point?? Software Technologies Ltd. has published its latest Global Threat Index for August 2021. Researchers report that Formbook is no??? [+5531 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Motley Fool\" }, \"author\": \"newsfeedback@fool.com (Trevor Jennewine)\", \"title\": \"Why This Pricey Growth Stock Is a Smart Buy\", \"description\": \"Tesla is miles ahead of its rivals, and it just hit the accelerator.\", \"url\": \"https://www.fool.com/investing/2021/09/10/this-pricey-growth-stock-is-a-smart-buy-tesla/\", \"urlToImage\": \"https://g.foolcdn.com/editorial/images/642441/0x0-roadster_03.jpg\", \"publishedAt\": \"2021-09-10T12:10:00Z\", \"content\": \"Tesla(NASDAQ:TSLA) is one of the more controversial stocks currently trading on the market. From devoted fans to dedicated critics, the company inspires a wide range of emotions, and that's understan??? [+5001 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Yahoo Entertainment\" }, \"author\": \"Divya Balji\", \"title\": \"Cathie Wood Sells $139 Million in Tesla Shares After Rally\", \"description\": \"(Bloomberg) -- Cathie Wood???s exchange-traded funds have sold some of their Tesla Inc. shares in the past two days, taking advantage of the recent rally as...\", \"url\": \"https://finance.yahoo.com/news/cathie-wood-sells-139-million-120543510.html\", \"urlToImage\": \"https://s.yimg.com/uu/api/res/1.2/9G_sC2x8_Oqs0cm1emir0Q--~B/aD02NzU7dz0xMjAwO2FwcGlkPXl0YWNoeW9u/https://media.zenfs.com/en/bloomberg_markets_842/4feb8b99374063d0ef91dce711d02a89\", \"publishedAt\": \"2021-09-10T12:05:43Z\", \"content\": \"(Bloomberg) -- Cathie Woods exchange-traded funds have sold some of their Tesla Inc. shares in the past two days, taking advantage of the recent rally as the stock climbs for the third week in a row.??? [+1532 chars]\" }, -{ -\"source\": { \"id\": \"the-washington-post\", \"name\": \"The Washington Post\" }, \"author\": null, \"title\": \"Musk dominating space race. Bezos trying to fight back...\", \"description\": \"Musk dominating space race. Bezos trying to fight back...\\r\\n\\n \\n \\n \\n (Second column, 10th story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n Related stories:Feud escalates...\", \"url\": \"https://www.washingtonpost.com/technology/2021/09/10/musk-bezos-space-rivalry/\", \"urlToImage\": \"https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AAOi9Me.img?h=630&w=1200&m=6&q=60&o=t&l=f&f=jpg\", \"publishedAt\": \"2021-09-10T12:01:42Z\", \"content\": \"For years, Jeff Bezos and Elon Musk have sparred over the performance of their rockets and space companies in a simmering feud that flared during a fight over who could use a NASA launchpad and which??? [+11393 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Electrek\" }, \"author\": \"Michelle Lewis\", \"title\": \"EGEB: US airlines boost their 2030 target for sustainable aviation fuel by 50%\", \"description\": \"In today???s Electrek Green Energy Brief (EGEB):\\n<ul>\\n<li>US airlines boost their 2030 SAF target from 2 billion to 3 billion gallons.</li>\\n<li>Clean energy has a diversity problem, a new study confirms.</li>\\n<li>UnderstandSolar is a free service that links you???\", \"url\": \"https://electrek.co/2021/09/10/egeb-us-airlines-boost-their-2030-target-for-sustainable-aviation-fuel-by-50/\", \"urlToImage\": \"https://i1.wp.com/electrek.co/wp-content/uploads/sites/3/2021/09/American-Airlines-creative-commons.jpg?resize=1200%2C628&ssl=1\", \"publishedAt\": \"2021-09-10T12:00:00Z\", \"content\": \"In todays Electrek Green Energy Brief (EGEB):\\r\\n<ul><li>US airlines boost their 2030 SAF target from 2 billion to 3 billion gallons.</li><li>Clean energy has a diversity problem, a new study confirms.??? [+4710 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Scoopyweb.com\" }, \"author\": \"unewsy\", \"title\": \"'This is not hype, this is reality': Nuclear fusion gets a step closer to reality as scientists successfully test a magnet 12 times as powerful as those used in MRIs - with a working reactor slated within the next decade\", \"description\": \"MIT researchers created a new high temperature superconducting magnet\\nThis allows for it to be smaller than magnets used in other fusion experiments\", \"url\": \"http://www.scoopyweb.com/2021/09/this-is-not-hype-this-is-reality.html\", \"urlToImage\": \"https://lh3.googleusercontent.com/proxy/9kjAeREXxVem9iiZkHZqnfgvoipSaHOterzVAfQpPkm2HVj4DSQRuZfSeLOCZgLR3dYvFEMTlFzB4pXP4p9CPh9sXGJ_3A9pa5pfH86jcb1BHW8qMvPlHvkYeUH4wwl6iMisxCJepAvJ8K0l3VzTtCxJctTp2AbHChIZnIpAogBTSn8dlV2GrH9T7yM0CHpFOu854p6YXqwVO9osoivbVHC_TtBDN53K=w1200-h630-p-k-no-nu\", \"publishedAt\": \"2021-09-10T11:34:49Z\", \"content\": \"Nuclear fusion is a step closer to reality after scientists tested a magnet 12 times as powerful as those used for MRIs, with the hope of working reactors by the 2030s. \\r\\nUnlike current nuclear fissi??? [+16008 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Motley Fool\" }, \"author\": \"newsfeedback@fool.com (Neha Chamaria)\", \"title\": \"3 Top U.S. Stocks to Watch in September\", \"description\": \"From earnings to mega-product events, these stocks are gearing up for some big days ahead.\", \"url\": \"https://www.fool.com/investing/2021/09/10/3-top-us-stocks-to-watch-in-september/\", \"urlToImage\": \"https://g.foolcdn.com/editorial/images/642356/a-person-concentrating-on-data-on-a-computer-screen.jpg\", \"publishedAt\": \"2021-09-10T11:34:00Z\", \"content\": \"September may be infamous for stock markets, but some stocks could chart their own paths nonetheless this month regardless of what the broader market does, thanks to big company-specific catalysts ah??? [+6215 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Zerohedge.com\" }, \"author\": \"Mike Rivero\", \"title\": \"Following Mass Outrage, Fed Presidents Kaplan, Rosengren Will Sell All Their Stocks Due To \\\"Ethics Concerns\\\"\", \"description\": \"Tuesday's news that Dallas Fed president (and former Goldman vice chair of I-banking) Robert Kaplan had made multiple million-dollar trades in his massive portfolio in 2020, which in addition to $1MM+ holdings of stocks such as Apple, Tesla, Amazon, Google Ch???\", \"url\": \"https://www.zerohedge.com/markets/following-mass-outrage-fed-presidents-kaplan-rosengren-will-sell-all-their-stocks-due\", \"urlToImage\": \"https://assets.zerohedge.com/s3fs-public/styles/16_9_max_700/public/2021-09/kaplan%20on%20the%20phone.jpg?itok=lyKryhz1\", \"publishedAt\": \"2021-09-10T11:31:47Z\", \"content\": \"Tuesday's news that Dallas Fed president (and former Goldman vice chair of I-banking) Robert Kaplan had made multiple million-dollar trades in his massive portfolio in 2020, which in addition to $1MM??? [+3405 chars]\" }, -{ -\"source\": { \"id\": null, \"name\": \"Cointelegraph\" }, \"author\": \"Cointelegraph By Marie Huillet\", \"title\": \"Nasdaq to provide price feeds for tokenized stock trades on DeFiChain\", \"description\": \"The purchase of a tokenized stock does not confer ownership of the underlying asset but enables hodlers to profit from price movements.\", \"url\": \"https://cointelegraph.com/news/nasdaq-to-provide-price-feeds-for-tokenized-stock-trades-on-defichain\", \"urlToImage\": \"https://images.cointelegraph.com/images/1200_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDkvY2E5ZTViMjctMDZlYy00ODUzLTgwMGEtMjZhM2RiNDY1ZGNkLmpwZw==.jpg\", \"publishedAt\": \"2021-09-10T11:30:04Z\", \"content\": \"Tokenized stocks have had a shaky few months from a regulatory perspective, but that seemingly hasn't stopped legacy financial giants and decentralized finance (DeFi) advocates from inking new deals.??? [+2140 chars]\" } ] }";
        String LinearWSJ = "{ \"status\": \"ok\", \"totalResults\": 548, -\"articles\": [ -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Benjamin Mullin\", \"title\": \"TINDER CEO Tapped for Top YAHOO Job...\", \"description\": \"TINDER CEO Tapped for Top YAHOO Job...\\r\\n\\n \\n \\n \\n (Second column, 5th story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n \\n Drudge Report Feed needs your support! Become a Patron\", \"url\": \"https://www.wsj.com/articles/apollo-taps-tinder-ceo-jim-lanzone-for-top-yahoo-job-11631274301\", \"urlToImage\": \"https://images.wsj.net/im-398769/social\", \"publishedAt\": \"2021-09-10T12:46:38Z\", \"content\": \"Apollo Global Management Inc. is naming Tinder Chief Executive Jim Lanzone the new CEO of Yahoo, the digital-media business it acquired from Verizon Communications Inc. earlier this year, according t??? [+308 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Candace Taylor\", \"title\": \"Palm Beach Island to Ask $120 Million -- $35 Million More Than Sold for in July...\", \"description\": \"Palm Beach Island to Ask $120 Million -- $35 Million More Than Sold for in July...\\r\\n\\n \\n \\n \\n (Third column, 10th story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n Related stories:BOOM: Oahu median home sale price tops $1 million for first time...\", \"url\": \"https://www.wsj.com/articles/private-palm-beach-island-to-ask-120-million-35-million-more-than-it-sold-for-in-july-11631214993\", \"urlToImage\": \"https://images.wsj.net/im-398260/social\", \"publishedAt\": \"2021-09-10T11:37:48Z\", \"content\": \"In the sizzling-hot, pandemic-fueled Palm Beach real-estate market, Todd Michael Glaser may have hit the jackpot: He is relisting a private island for $120 millionor $35 million more than he and his ??? [+399 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Rachel Louise Ensign\", \"title\": \"Crypto Fans Borrow to Buy Homes, Cars -- and More Crypto...\", \"description\": \"Crypto Fans Borrow to Buy Homes, Cars -- and More Crypto...\\r\\n\\n \\n \\n \\n (First column, 4th story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n Related stories:Six reasons Cramer concerned about stock market...\", \"url\": \"https://www.wsj.com/articles/crypto-fans-borrow-to-buy-homes-carsand-more-crypto-11631266200\", \"urlToImage\": \"https://images.wsj.net/im-398727/social\", \"publishedAt\": \"2021-09-10T11:37:43Z\", \"content\": \"Michael Anderson mined bitcoin in his dorm room and left a corporate job to invest in cryptocurrency projects. When he bought his first home in San Francisco this year, he didnt turn to a bank. Inste??? [+255 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Heather Haddon And Preetika Rana\", \"title\": \"Grubhub, DoorDash, Uber Eats Sue New York City Over Fee Caps\", \"description\": \"Food-delivery companies contend the cap is harmful, constitutes government overreach\", \"url\": \"https://www.wsj.com/articles/grubhub-doordash-uber-eats-sue-new-york-city-over-fee-caps-11631237227\", \"urlToImage\": \"https://images.wsj.net/im-398389/social\", \"publishedAt\": \"2021-09-10T01:29:58Z\", \"content\": \"DoorDash Inc., Grubhub Inc. and Uber Technologies Inc.s Eats division are suing New York City over its law permanently capping the amount of commissions the apps can charge restaurants to use their s??? [+384 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Yoree Koh\", \"title\": \"Los Angeles School District Requires Students to Get Covid-19 Vaccine - The Wall Street Journal\", \"description\": \"<ol><li>Los Angeles School District Requires Students to Get Covid-19 Vaccine The Wall Street Journal\\r\\n</li><li>Los Angeles school board votes to mandate COVID-19 vaccines for students 12 and older CBS News\\r\\n</li><li>Majority of L.A. Unified board either fa???\", \"url\": \"https://www.wsj.com/articles/los-angeles-school-district-requires-students-to-get-covid-19-vaccine-11631230734\", \"urlToImage\": \"https://images.wsj.net/im-398724/social\", \"publishedAt\": \"2021-09-09T23:38:00Z\", \"content\": \"Los Angeles public schools are requiring students ages 12 and older to get the Covid-19 vaccine by January to continue attending school in person, making the school system the first large district in??? [+283 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"The Editorial Board\", \"title\": \"Big Business for Big Government - The Wall Street Journal\", \"description\": \"AT&T wants the FCC to hobble its 5G competitor, T-Mobile.\", \"url\": \"https://www.wsj.com/articles/big-business-for-big-government-att-t-mobile-11631194923\", \"urlToImage\": \"https://images.wsj.net/im-398672/social\", \"publishedAt\": \"2021-09-09T22:48:00Z\", \"content\": \"The biggest threat to competition and consumers in our time is the collusion of big business and big government. As a case in point, see how AT&amp;T is urging the Federal Communications Commission t??? [+310 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Anne Tergesen, Richard Rubin\", \"title\": \"Democrats Advance Plan to Require Employers to Offer Retirement Plans\", \"description\": \"Companies with more than five employees that fail to comply would face fines\", \"url\": \"https://www.wsj.com/articles/democrats-advance-plan-to-require-employers-to-offer-retirement-plans-11631225199\", \"urlToImage\": \"https://images.wsj.net/im-397974/social\", \"publishedAt\": \"2021-09-09T22:12:00Z\", \"content\": \"Democrats have included a provision in their $3.5 trillion healthcare, education and climate bill that would require companies without retirement plans to automatically enroll workers in individual r??? [+178 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Joseph C. Sternberg\", \"title\": \"Why Beijing Wants BlackRock but Not Capitalism\", \"description\": \"Economists know China needs financial reform, but the Party won???t loosen political control.\", \"url\": \"https://www.wsj.com/articles/soros-beijing-blackrock-mutual-fund-savings-housing-bubble-sse-hkex-ccp-xi-jinping-china-11631221987\", \"urlToImage\": \"https://images.wsj.net/im-397910/social\", \"publishedAt\": \"2021-09-09T21:49:00Z\", \"content\": \"George Soros is creating an emperors-new-clothes moment for Wall Street by raising some provocative questions about why foreign finance companies would trip over themselves to rush into China. As Bla??? [+678 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Ben Eisen\", \"title\": \"Wells Fargo Fined $250 Million for Problems in Its Mortgage Business - The Wall Street Journal\", \"description\": \"Wells Fargo Fined $250 Million for Problems in Its Mortgage Business The Wall Street Journal\", \"url\": \"https://www.wsj.com/articles/wells-fargo-fined-250-million-for-problems-in-its-mortgage-business-11631223671\", \"urlToImage\": \"https://images.wsj.net/im-398608/social\", \"publishedAt\": \"2021-09-09T21:41:00Z\", \"content\": \"Regulators fined Wells Fargo &amp; Co. $250 million for lack of progress in addressing longstanding issues in its mortgage business.\\r\\nThe Office of the Comptroller of the Currency, one of the banks t??? [+292 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Sara Castellanos\", \"title\": \"IT Leaders Adjust Hiring Strategies With Tech Talent Even More in Demand - The Wall Street Journal\", \"description\": \"IT Leaders Adjust Hiring Strategies With Tech Talent Even More in Demand The Wall Street Journal\", \"url\": \"https://www.wsj.com/articles/it-leaders-adjust-hiring-strategies-with-tech-talent-even-more-in-demand-11631218276\", \"urlToImage\": \"https://images.wsj.net/im-398462/social\", \"publishedAt\": \"2021-09-09T20:11:00Z\", \"content\": \"Information-technology leaders say they are boosting compensation packages and flexible work options to widen the pool of prospective job candidates, as demand surges for tech talent.The IT job marke??? [+4656 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"wsj\", \"title\": \"White House Pulls ATF Pick After Gun Groups Oppose...\", \"description\": \"Nominee was opposed by Republicans and struggled to lock down support of centrist Democrats\", \"url\": \"https://www.wsj.com/articles/white-house-plans-to-pull-atf-pick-david-chipman-11631195734\", \"urlToImage\": \"https://images.wsj.net/im-398209/social\", \"publishedAt\": \"2021-09-09T19:00:20Z\", \"content\": \"White House Pulls ATF Pick David Chipman\\nWASHINGTON???The White House withdrew the nomination of David Chipman to lead the Bureau of Alcohol, Tobacco, Firearms and Explosives, in the face of Republican??? [+1305 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"AnnaMaria Andriotis, Benjamin Mullin\", \"title\": \"WSJ News Exclusive | JPMorgan to Buy Restaurant-Discovery Service the Infatuation\", \"description\": \"America's biggest bank is buying the company behind the Zagat brand. JPMorgan Chase & Co. has reached a deal to acquire the Infatuation, which owns websites and apps that guide diners to eat like locals in cities around the world, according to a person famili???\", \"url\": \"https://www.wsj.com/articles/jpmorgan-to-buy-restaurant-discovery-service-the-infatuation-11631205033\", \"urlToImage\": \"https://images.wsj.net/im-398262/social\", \"publishedAt\": \"2021-09-09T18:25:06Z\", \"content\": \"Americas biggest bank is buying the company behind the Zagat brand. \\r\\nJPMorgan Chase &amp; Co. said on Thursday that it has reached a deal to acquire the Infatuation, which owns websites and apps tha??? [+214 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Joanna Stern\", \"title\": \"SPY GAMES: RAY-BAN, FACEBOOK roll-out camera shades...\", \"description\": \"SPY GAMES: RAY-BAN, FACEBOOK roll-out camera shades...\\r\\n\\n \\n \\n \\n (Third column, 2nd story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n \\n Drudge Report Feed needs your support! Become a Patron\", \"url\": \"https://www.wsj.com/articles/rayban-stories-facebook-review-11631193687\", \"urlToImage\": \"https://images.wsj.net/im-397977/social\", \"publishedAt\": \"2021-09-09T17:19:39Z\", \"content\": \"Look, did I feel creepy recording my mother and my aunt without them knowing? Or my barista? Or a random couple at the coffee shop? Or my Uber driver? Oran aggressive squirrel in Central Park? I sure??? [+297 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Aaron Tilley\", \"title\": \"Microsoft Delays Full Headquarters Reopening to Undetermined Future Date\", \"description\": \"Microsoft Delays Full Headquarters Reopening to Undetermined Future Datewsj.com\", \"url\": \"https://www.wsj.com/articles/microsoft-delays-full-headquarters-reopening-to-undetermined-future-date-11631199600\", \"urlToImage\": \"https://images.wsj.net/im-398186/social\", \"publishedAt\": \"2021-09-09T15:04:35Z\", \"content\": \"Microsoft Corp. is abandoning plans to fully reopen its headquarters and other U.S. sites early next month and wont set a new date, for now, given uncertainty around Covid-19.\\r\\nWeve decided against a??? [+375 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Chip Cutter\", \"title\": \"Amazon Dangles a New Perk in Fight for U.S. Workers: Free Bachelor???s Degrees\", \"description\": \"E-commerce giant will expand education benefit as it battles for workers in tight U.S. labor market\", \"url\": \"https://www.wsj.com/articles/amazon-dangles-a-new-perk-in-fight-for-u-s-workers-free-bachelors-degrees-11631197800\", \"urlToImage\": \"https://images.wsj.net/im-397727/social\", \"publishedAt\": \"2021-09-09T14:31:47Z\", \"content\": \"The battle for hourly workers is escalating beyond minimum wage across the U.S., as retailers, restaurant chains, garbage haulers and meat processors increasingly dangle the prospect of a free colleg??? [+395 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": null, \"title\": \"Cyber Daily: Overworked Hackers Are Vulnerable. That's When Police Should Pounce. - The Wall Street Journal\", \"description\": \"Most cybercriminals are simply cogs in a sprawling network of services that support those who launch attacks. And that has important implications for how to police cybercrime, The Wall Street Journal reports.\", \"url\": \"https://www.wsj.com/articles/cyber-daily-overworked-hackers-are-vulnerable-thats-when-police-should-pounce-11631192769\", \"urlToImage\": \"https://s.wsj.net/img/meta/wsj-social-share.png\", \"publishedAt\": \"2021-09-09T14:19:00Z\", \"content\": \"Hello. Most cybercriminals are simply cogs in a sprawling network of services that support those who launch attacks. And that has important implications for how to police cybercrime, The Wall Street ??? [+4546 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Jennifer Maloney\", \"title\": \"FDA to Seek More Time to Decide Fate of Juul\", \"description\": \"Regulator has told some smaller e-cigarette makers that their e-cigarettes and vaping products can???t remain on the U.S. market\", \"url\": \"https://www.wsj.com/articles/fda-to-seek-more-time-to-decide-fate-of-juul-11631196029\", \"urlToImage\": \"https://images.wsj.net/im-397641/social\", \"publishedAt\": \"2021-09-09T14:01:35Z\", \"content\": \"The Food and Drug Administration is expected to seek more time before deciding whether e-cigarettes from market leader Juul Labs Inc. can remain on the U.S. market, according to people familiar with ??? [+286 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Sebastian Herrera\", \"title\": \"Amazon to Launch Television Lineup in Push to Cement Itself in Living Rooms\", \"description\": \"Amazon to Launch Television Lineup in Push to Cement Itself in Living Roomswsj.com\", \"url\": \"https://www.wsj.com/articles/amazon-to-launch-television-lineup-in-push-to-cement-itself-in-living-rooms-11631192401\", \"urlToImage\": \"https://images.wsj.net/im-398066/social\", \"publishedAt\": \"2021-09-09T13:12:06Z\", \"content\": \"Amazon.com Inc. plans to roll out Fire TV sets that will feature its Alexa voice assistant, an expansion of its private-label products that also showcases a growing ambition to place itself at the ce??? [+313 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Zusha Elinson\", \"title\": \"LA Gang-Intervention Worker Killed...\", \"description\": \"LA Gang-Intervention Worker Killed...\\r\\n\\n \\n \\n \\n (First column, 21st story, link)\\r\\n\\n \\r\\n\\n \\r\\n\\n \\n Related stories:GAVIN REMAINS: Betting markets swing in favor of Newsom...\\r\\nSchwarzenegger warns 'very dangerous'...\\r\\nKamala rallies at critical time...\\r\\nElder egged ???\", \"url\": \"https://www.wsj.com/articles/los-angeles-gang-intervention-worker-is-killed-11631192400\", \"urlToImage\": \"https://images.wsj.net/im-398098/social\", \"publishedAt\": \"2021-09-09T13:07:37Z\", \"content\": \"Craig Big Batiste, a former gang member turned neighborhood peacekeeper, was killed Wednesday morning near Los Angeles, authorities said.\\r\\nMr. Batiste, 54 years old, was the subject of a 2017 Wall St??? [+173 chars]\" }, -{ -\"source\": { \"id\": \"the-wall-street-journal\", \"name\": \"The Wall Street Journal\" }, \"author\": \"Sarah Nassauer\", \"title\": \"Walmart to End Quarterly Bonuses for Store Workers\", \"description\": \"Retailer says the benefit, part of potential compensation of hundreds of thousands of workers, would be rolled into their hourly wages\", \"url\": \"https://www.wsj.com/articles/walmart-to-end-quarterly-bonuses-for-store-workers-11631190896\", \"urlToImage\": \"https://images.wsj.net/im-398169/social\", \"publishedAt\": \"2021-09-09T12:41:00Z\", \"content\": \"Walmart Inc. is phasing out its decades-old quarterly bonuses for store workers, according to the company, as it implements hourly wage increases for hundreds of thousands of its employees.\\r\\nWalmart ??? [+250 chars]\" } ] }";


        // TODO: Resolve performance issues with local fragment

        try {
            TitlesArrayList = DifferentFunctions.ParseJSONWorldNews(LinearApple, "news_title");
            TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_title"));
            TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTesla, "news_title"));
            TitlesArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearWSJ, "news_title"));

            DescriptionsArrayList = DifferentFunctions.ParseJSONWorldNews(LinearApple, "news_descr");
            DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_descr"));
            DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTesla, "news_descr"));
            DescriptionsArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearWSJ, "news_descr"));

            ArrayListURLValues = DifferentFunctions.ParseJSONWorldNews(LinearApple, "news_url");
            ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url"));
            ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTesla, "news_url"));
            ArrayListURLValues.addAll(DifferentFunctions.ParseJSONWorldNews(LinearWSJ, "news_url"));

            ImageURLArrayList = DifferentFunctions.ParseJSONWorldNews(LinearApple, "news_url_to_img");
            ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTopUS, "news_url_to_img"));
            ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearTesla, "news_url_to_img"));
            ImageURLArrayList.addAll(DifferentFunctions.ParseJSONWorldNews(LinearWSJ, "news_url_to_img"));


            if (!TitlesArrayList.isEmpty()) {
                RecyclerViewHeadlinesAdapter adapter = new RecyclerViewHeadlinesAdapter(ArrayListURLValues, TitlesArrayList, DescriptionsArrayList, ImageURLArrayList); // Call the Constructor for the adapter
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(false);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MContext);
                recyclerView.setLayoutManager(layoutManager);
            }
        }catch(Exception e){
            // Toast.makeText(MContext, "Could not find cache or information is invalid. Please reload the app when Internet is available", Toast.LENGTH_LONG).show();
            System.out.println(LinearApple);
            System.out.println(LinearTopUS);
            System.out.println(LinearTesla);
            System.out.println(LinearWSJ);
        }
    }
    public void loadDefaultWeatherForecast(){

        String ResponseJSON = ResponseJsonForecast;
        // Here we will check for the availability of the file
        if(Integer.parseInt(ParseJSONForecast(ResponseJSON, "cod").get(0)) != 200){
            // Something bad happened
            Toast.makeText(MContext, "There was a problem in the response for Forecast. Please try again later", Toast.LENGTH_LONG).show();
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(requireContext(),"JSON_FORECAST_CACHE.json", ResponseJSON);
        }
        String ResponseJsonForecast = ResponseJSON;
        if(ResponseJSON != null && ResponseJSON != "") {
            // Now we read the file and put the response inside

            // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
            ArrayList<String> ConditionsInJson = ParseJSONForecast(ResponseJSON, new String("id_icon"));
            ArrayList<String> TimeStamps = ParseJSONForecast(ResponseJSON, new String("time_stamp"));

            ArrayList<String> GlobalTimeForExtendedForecast = ParseJSONForecast(ResponseJSON, new String("time_stamp"));
            ArrayList<String> GlobalHumidityForExtendedForecast = ParseJSONForecast(ResponseJSON, "humidity");
            ArrayList<String> GlobalWSpeedForExtendedForecast = DifferentFunctions.ParseJSONForecast(ResponseJSON, "w_speed");

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
            ChipHour1.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(0)) - 273.15) + "??C");
            ChipHour2.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(1)) - 273.15) + "??C");
            ChipHour3.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(2)) - 273.15) + "??C");
            ChipHour4.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(3)) - 273.15) + "??C");
            ChipHour5.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(4)) - 273.15) + "??C");
            ChipHour6.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(5)) - 273.15) + "??C");
            ChipHour7.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(6)) - 273.15) + "??C");
            ChipHour8.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(7)) - 273.15) + "??C");
            ChipHour9.setText(String.format("%.2f", (Double) Double.parseDouble(ConditionsInJson.get(8)) - 273.15) + "??C");

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
    }
    public void loadDefaultWeatherConds(){
        String ResponseJSON = ResponseJsonWeather;
        // Here we will check for the availability of the file
        if(Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "cod")) != 200){
            // Something bad happened
            Toast.makeText(getContext(), "There was a problem in the response for Weather. Please try again later", Toast.LENGTH_LONG).show();
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(requireContext(),"JSON_WEATHER_CACHE.json", ResponseJsonWeather);
        }
        ResponseJSON = readFromFile(requireContext(), "JSON_WEATHER_CACHE.json");
        ResponseJsonWeather = ResponseJSON;

        // IF THE REQUEST WAS MADE ONLY FOR THE CURRENT WEATHER///////////////////////////////////////////////////////////////////
        if(ResponseJSON != null && ResponseJSON != "") {
            // Get the current date and time and set the upper chip
            Date CurrentDate = new Date((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
            Time CurrentTime = new Time((long) 1000 * Integer.parseInt(ParseJSONCurrentWeather(ResponseJSON, "time")));
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
            Pair<Integer, Integer> SunriseGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseTimeStampUnix, requireContext());
            Pair<Integer, Integer> SunsetGlobalHourPair = DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetTimeStampUnix, requireContext());
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
            TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "??C");

            chipHILO.setText("High - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmax")) - 273.15) + "??C" + " | Low - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmin")) - 273.15) + "??C");
        }
    }
}
