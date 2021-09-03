package app.matthewsgalaxy.ataglance.ui.ExtendedForecast;

import static android.content.ContentValues.TAG;

import static app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.GlobalHumidityForExtendedForecast;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.GlobalTimeForExtendedForecast;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.GlobalWSpeedForExtendedForecast;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.adapterClasses.RecyclerViewForecastAdapter;
import app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.databinding.FragmentExtendedforecastBinding;

public class extendedForecastFragment extends Fragment {
    private FragmentExtendedforecastBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExtendedforecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        InitRecyclerView();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void InitRecyclerView(){
        Log.d(TAG, "InitRecyclerView: init recyclerview");
        RecyclerView recyclerView = binding.recyclerviewforecast;

        // For debugging purposes
        ArrayList<String> ArrayListConditions;
        ArrayList<String> ImageCodes;
        ArrayList<String> Temperatures;
        ArrayList<String> WindConditions = new ArrayList<>();
        ArrayList<String> CurrentTimeLOC;
        ArrayList<String> MinMaxTemp = new ArrayList<>();
        ArrayList<String> Humidityarray = new ArrayList<>();
        ArrayList<String> Windspeedarr = new ArrayList<>();


        // Init the array of conditions
        ArrayListConditions = DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), "description");
        for(int i=0;i<ArrayListConditions.size();++i){
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

    }


}