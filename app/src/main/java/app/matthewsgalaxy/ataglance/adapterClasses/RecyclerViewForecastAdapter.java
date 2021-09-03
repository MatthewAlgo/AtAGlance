package app.matthewsgalaxy.ataglance.adapterClasses;

import static app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions.ProcessTimeStamp;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.GlobalTimeForExtendedForecast;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.SunriseGlobalHourString;
import static app.matthewsgalaxy.ataglance.ui.AtAGlance.AtAGlanceFragment.SunsetGlobalHourString;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions;

public class RecyclerViewForecastAdapter extends RecyclerView.Adapter<RecyclerViewForecastAdapter.ViewHolder> {
    private static final String TAG = "Cannot invoke method length() on null object";

    private ArrayList<String> ArrayListConditions;
    private ArrayList<String> ImageCodes;
    private ArrayList<String> TemperaturesArray;
    private ArrayList<String> WindConditions = new ArrayList<>();
    private ArrayList<String> CurrentTime;
    private ArrayList<String> MinMaxTemp = new ArrayList<>();


    private Context mContext;

    public RecyclerViewForecastAdapter(ArrayList<String> arrayListConditions, ArrayList<String> imageCodes,ArrayList<String> temperaturesArray, ArrayList<String> windConditions, ArrayList<String> currentTime, ArrayList<String> minMaxTemp, Context mContext) {
        ArrayListConditions = arrayListConditions;
        ImageCodes = imageCodes;
        TemperaturesArray = temperaturesArray;
        WindConditions = windConditions;
        CurrentTime = currentTime;
        MinMaxTemp = minMaxTemp;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_extforecast, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        // Load The Features

        // Modify The Text for conditions
        holder.MainConditionTextForecast.setText(ArrayListConditions.get(position));
        // Modify Image According to conditions
        ModifyImageToConditions(holder.MainImageViewForecast, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(GlobalTimeForExtendedForecast.get(position)),
                DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString)), ImageCodes.get(position));

        // Modify the temperature chip text according to the temperatures array
        holder.chipTemperatureForecast.setText(String.format("%.2f", (Double) Double.parseDouble(TemperaturesArray.get(position)) - 273.15) + "°C");

        // Modify the timestamp chip text according to the array
        holder.chipLastUpdatedForecast.setText(ProcessTimeStamp(GlobalTimeForExtendedForecast.get(position))); // PROCESSED DIRECTLY FROM GLOBAL VARIABLE
    }

    @Override
    public int getItemCount() {
        return ArrayListConditions.size(); // How many list items are in my list
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // Holding Views

        ConstraintLayout ConstraintForecastRecView;
        Chip chipLastUpdatedForecast, MainConditionTextForecast, chipTemperatureForecast, chipHumidityForecast,
                chipWindForecast,chipHILOForecast;
        ImageView MainImageViewForecast;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ConstraintForecastRecView = itemView.findViewById(R.id.ConstraintForecastRecView);
            // Our layout
            MainConditionTextForecast = itemView.findViewById(R.id.MainConditionTextForecast);
            chipTemperatureForecast = itemView.findViewById(R.id.chipTemperatureForecast);
            chipHumidityForecast = itemView.findViewById(R.id.chipHumidityForecast);
            chipLastUpdatedForecast = itemView.findViewById(R.id.chipLastUpdatedForecast);
            chipWindForecast = itemView.findViewById(R.id.chipWindForecast);
            chipHILOForecast = itemView.findViewById(R.id.chipHILOForecast);
            MainImageViewForecast = itemView.findViewById(R.id.MainImageViewForecast);

        }
    }
}
