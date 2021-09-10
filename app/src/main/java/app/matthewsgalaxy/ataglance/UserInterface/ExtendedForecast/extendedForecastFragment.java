package app.matthewsgalaxy.ataglance.UserInterface.ExtendedForecast;

import static android.content.ContentValues.TAG;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalHumidityForExtendedForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalWSpeedForExtendedForecast;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;

public class extendedForecastFragment extends Fragment {
    private View PubView;
    private RecyclerView recyclerView;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PubView = inflater.inflate(R.layout.fragment_weatherforecastfrag, container, false);
        recyclerView = PubView.findViewById(R.id.recyclerviewforecast);

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

        // For debugging purposes
        ArrayList<String> ArrayListConditions;
        ArrayList<String> ImageCodes;
        ArrayList<String> Temperatures;
        ArrayList<String> WindConditions = new ArrayList<>();
        ArrayList<String> CurrentTimeLOC;
        ArrayList<String> MinMaxTemp = new ArrayList<>();
        ArrayList<String> Humidityarray = new ArrayList<>();
        ArrayList<String> Windspeedarr = new ArrayList<>();

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

            RecyclerViewForecastAdapter adapter = new RecyclerViewForecastAdapter(ArrayListConditions,
                    ImageCodes, Temperatures, Windspeedarr, CurrentTimeLOC, MinMaxTemp, Humidityarray, getContext()); // Call the Constructor for the adapter

            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

        }catch(Exception e){
            Toast.makeText(PubView.getContext(), "Error while retrieving data. Please try again when you activate your internet connection", Toast.LENGTH_LONG);
        }
    }


}