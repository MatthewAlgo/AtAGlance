package app.matthewsgalaxy.ataglance.UserInterface.LocalInformation;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ToCamelCaseWord;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.APIKEY;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunriseGlobalHourString;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

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

import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class localInformationFragment extends Fragment implements View.OnClickListener{
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


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PubView = inflater.inflate(R.layout.fragment_searchinformationfrag, container, false);

        InputText = PubView.findViewById(R.id.Inputtext);
        ConfirmButton = PubView.findViewById(R.id.confirm_button);
        View view = PubView;

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

        ConfirmButton.setOnClickListener(this);

        // Check Internet Availability
        if(!isOnline(PubView.getContext())){
            Toast.makeText(PubView.getContext(),"You are not connected! Please Check Your Internet Connection And Try Again!" + InputText.getText().toString(),Toast.LENGTH_LONG).show();
        }

        return PubView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }

    @Override
    public void onClick(View view) {
        LocationInserted = InputText.getText().toString();
        Toast.makeText(PubView.getContext(),"Showing Weather Conditions For " + InputText.getText().toString(),Toast.LENGTH_LONG).show();
        // Start Initializing the API and stuff
        if(LocationInserted != null){
            // Functions
            APISTRING_WEATHERCURRENT = "https://api.openweathermap.org/data/2.5/weather?q=" + LocationInserted + "&appid=" + APIKEY;
            APISTRING_WEATHERFORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=" + LocationInserted + "&appid=" + APIKEY;

            // We make a call using the string
            try {
                DoInBackgroundRequestLocal("weather");
                DoInBackgroundRequestLocal("forecast");
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }

        }

    }
    public void DoInBackgroundRequestLocal(String REQUEST_TYPE_LOC) {
        // Makes HTTP Request That returns a json with the current weather conditions
        OkHttpClient client = new OkHttpClient();
        Request request = null;

        if (REQUEST_TYPE_LOC == "weather") {
            request = new Request.Builder().url(APISTRING_WEATHERCURRENT).build();
        } else if (REQUEST_TYPE_LOC == "forecast") {
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

                                    if (ResponseJSON != null && ResponseJSON != "") {
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
                                    if (ResponseJSON != null && ResponseJSON != "") {
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
                                        TempChip.setText(String.format("%.2f", (Double) Double.parseDouble(ToModifyCurrentTemperature) - 273.15) + "°C");

                                        chipHILO.setText("High - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmax")) - 273.15) + "°C" + " | Low - " + String.format("%.1f", Double.parseDouble(ParseJSONCurrentWeather(ResponseJSON, "tmin")) - 273.15) + "°C");
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
}
