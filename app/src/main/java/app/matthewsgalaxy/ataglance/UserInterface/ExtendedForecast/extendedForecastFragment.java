package app.matthewsgalaxy.ataglance.UserInterface.ExtendedForecast;

import static android.content.ContentValues.TAG;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ToCamelCaseWord;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.APIKEY;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalHumidityForExtendedForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalWSpeedForExtendedForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.UserInterface.LocalInformation.localInformationFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class extendedForecastFragment extends Fragment implements View.OnClickListener {
    private View PubView;
    private RecyclerView recyclerView;

    private String LocationInserted;
    private EditText InputText;
    private Chip ConfirmButton;

    private String APISTRING_WEATHERFORECAST;

    // The data itself
    ArrayList<String> ArrayListConditions= new ArrayList<>();
    ArrayList<String> ImageCodes= new ArrayList<>();
    ArrayList<String> Temperatures= new ArrayList<>();
    ArrayList<String> WindConditions = new ArrayList<>();
    ArrayList<String> CurrentTimeLOC= new ArrayList<>();
    ArrayList<String> MinMaxTemp = new ArrayList<>();
    ArrayList<String> Humidityarray = new ArrayList<>();
    ArrayList<String> Windspeedarr = new ArrayList<>();


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PubView = inflater.inflate(R.layout.fragment_weatherforecastfrag, container, false);
        recyclerView = PubView.findViewById(R.id.recyclerviewforecast);

        InputText = PubView.findViewById(R.id.EditText);
        ConfirmButton = PubView.findViewById(R.id.ConfirmChip);

        ConfirmButton.setOnClickListener(this);

        // Check Internet Availability
        if (!isOnline(PubView.getContext())) {
            Toast.makeText(PubView.getContext(), "You are not connected! Please Check Your Internet Connection And Try Again!" + InputText.getText().toString(), Toast.LENGTH_LONG).show();
        }


        InitRecyclerView();
        return PubView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PubView = null;
    }

    private void InitRecyclerView(){
        Log.d(TAG, "InitRecyclerView: init recyclerview");


        try {
            // Init the array of conditions
            ArrayListConditions = DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), "description");
            for (int i = 0; i < ArrayListConditions.size(); ++i) {
                ArrayListConditions.set(i, DifferentFunctions.ToCamelCaseWord(ArrayListConditions.get(i)));
            }

            // Init the image codes array
            ImageCodes = DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), "id_icon");

            // Init the Temperatures array
            Temperatures = DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), new String("temperature"));

            // Init the timestamps array
            CurrentTimeLOC = DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), new String("time"));

            // Init the Humidity array
            Humidityarray = GlobalHumidityForExtendedForecast;

            // Init the Wind Speed Array
            Windspeedarr = GlobalWSpeedForExtendedForecast;

            InputText.setHint("Try, For Example, \"Bucharest\"");

            RecyclerViewForecastAdapter adapter = new RecyclerViewForecastAdapter(ArrayListConditions,
                    ImageCodes, Temperatures, Windspeedarr, CurrentTimeLOC, MinMaxTemp, Humidityarray, getContext()); // Call the Constructor for the adapter

            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

        }catch(Exception e){
            Toast.makeText(PubView.getContext(), "Error while retrieving data. Please try again when you activate your internet connection", Toast.LENGTH_LONG);
        }
    }


    @Override
    public void onClick(View view) {
        LocationInserted = InputText.getText().toString();
        if(LocationInserted != null && !LocationInserted.equals("")) {
            Toast.makeText(PubView.getContext(), "Showing Weather Conditions For " + InputText.getText().toString(), Toast.LENGTH_LONG).show();
            // Start Initializing the API and stuff
            if (LocationInserted != null) {
                // Functions
                APISTRING_WEATHERFORECAST = "https://api.openweathermap.org/data/2.5/forecast?q=" + LocationInserted + "&appid=" + APIKEY;

                // We make a call using the string
                try {
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

        if (REQUEST_TYPE_LOC.equals("forecast")) {
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
                                    if(!ParseJSONForecast(ResponseJSON, "cod").equals("ok")) {
                                        // IF THE REQUEST WAS MADE FOR THE WEATHER FORECAST////////////////////////////////////////////////////////////////////////
                                        ArrayListConditions = DifferentFunctions.ParseJSONForecast(ResponseJSON, "description");
                                        for (int i = 0; i < ArrayListConditions.size(); ++i) {
                                            ArrayListConditions.set(i, DifferentFunctions.ToCamelCaseWord(ArrayListConditions.get(i)));
                                        }

                                        // Init the image codes array
                                        ImageCodes = DifferentFunctions.ParseJSONForecast(ResponseJSON, "id_icon");

                                        // Init the Temperatures array
                                        Temperatures = DifferentFunctions.ParseJSONForecast(ResponseJSON, new String("temperature"));

                                        // Init the timestamps array
                                        CurrentTimeLOC = DifferentFunctions.ParseJSONForecast(ResponseJSON, new String("time"));

                                        // Init the Humidity array
                                        Humidityarray = ParseJSONForecast(ResponseJSON, "humidity");;

                                        // Init the Wind Speed Array
                                        Windspeedarr = DifferentFunctions.ParseJSONForecast(ResponseJSON, "w_speed");
                                        try {
                                            RecyclerViewForecastAdapter adapter = new RecyclerViewForecastAdapter(ArrayListConditions,
                                                    ImageCodes, Temperatures, Windspeedarr, CurrentTimeLOC, MinMaxTemp, Humidityarray, getContext()); // Call the Constructor for the adapter

                                            recyclerView.setAdapter(adapter);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                            recyclerView.setLayoutManager(layoutManager);
                                        }catch(Exception e){
                                            e.printStackTrace();
                                            Toast.makeText(requireContext(), "Your location is invalid or something bad happened. Please try again later", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(requireContext(), "Your location is invalid or something bad happened. Please try again later", Toast.LENGTH_SHORT).show();
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

}