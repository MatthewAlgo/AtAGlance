package app.matthewsgalaxy.ataglance.AdapterClasses;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONCurrentWeather;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ProcessTimeStamp;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ReturnForecastResponseJSON;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalTimeForExtendedForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunriseGlobalHourString;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunsetGlobalHourString;

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
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;

public class RecyclerViewForecastAdapter extends RecyclerView.Adapter<RecyclerViewForecastAdapter.ViewHolder> {
    private static final String TAG = "Cannot invoke method length() on null object";

    private ArrayList<String> ArrayListConditions;
    private ArrayList<String> ImageCodes;
    private ArrayList<String> TemperaturesArray;
    private ArrayList<String> WindConditions;
    private ArrayList<String> CurrentTime;
    private ArrayList<String> MinMaxTemp = new ArrayList<>();
    private ArrayList<String> HumidityArr = new ArrayList<>();


    private Context mContext;

    public RecyclerViewForecastAdapter(ArrayList<String> arrayListConditions, ArrayList<String> imageCodes,ArrayList<String> temperaturesArray,
                                       ArrayList<String> windConditions, ArrayList<String> currentTime, ArrayList<String> minMaxTemp, ArrayList<String> humidityArr,
                                       Context mContext) {
        ArrayListConditions = arrayListConditions;
        ImageCodes = imageCodes;
        TemperaturesArray = temperaturesArray;
        WindConditions = windConditions;
        CurrentTime = currentTime;
        MinMaxTemp = minMaxTemp;
        HumidityArr = humidityArr;
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
        ModifyImageToConditions(holder.MainImageViewForecast, DifferentFunctions.isDaylightFunction(DifferentFunctions.GetHourAndMinutesFromTimeStamp(GlobalTimeForExtendedForecast.get(position),  mContext.getApplicationContext()),
                DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunriseGlobalHourString, mContext.getApplicationContext()),DifferentFunctions.GetHourAndMinutesFromTimeStamp(SunsetGlobalHourString, mContext.getApplicationContext())), ImageCodes.get(position));

        // Modify the temperature chip text according to the temperatures array
        holder.chipTemperatureForecast.setText(String.format("%.2f", (Double) Double.parseDouble(TemperaturesArray.get(position)) - 273.15) + "°C");

        // Modify the timestamp chip text according to the array
        holder.chipLastUpdatedForecast.setText(ProcessTimeStamp(GlobalTimeForExtendedForecast.get(position))); // PROCESSED DIRECTLY FROM GLOBAL VARIABLE

        // Modify the humidity array
        double ValueOfHumidity = Double.parseDouble(HumidityArr.get(position)); String AdditionalConditions = null;
        if (ValueOfHumidity < 20) { AdditionalConditions = "Dry"; } else if (ValueOfHumidity >= 20 && ValueOfHumidity <= 60) { AdditionalConditions = "Comfortable"; } else if (ValueOfHumidity > 60) { AdditionalConditions = "Humid"; }
        holder.chipHumidityForecast.setText(HumidityArr.get(position) + "% - " + AdditionalConditions);

        // Modify the wind conditions Chip
        String WindConditionsStr = null; double WindSpeed = Double.parseDouble(WindConditions.get(position))*0.001*3600 ;
        if(WindSpeed == 0){ WindConditionsStr = "No Wind"; }if(WindSpeed < 5 && WindSpeed > 0){ WindConditionsStr = "Light Breeze"; }if(WindSpeed >= 5 && WindSpeed <20){ WindConditionsStr = "Light Wind"; }if(WindSpeed >= 20 && WindSpeed <30){ WindConditionsStr = "Moderate Wind"; }if(WindSpeed >= 30){ WindConditionsStr = "Strong Wind"; }
        holder.chipWindForecast.setText("Speed - "+ String.format("%.2f", (Double) WindSpeed) + "km/h" + " - " + WindConditionsStr);

        // Set the High / Low Temperature Chip
        holder.chipHILOForecast.setText("High - "+ String.format("%.1f",Double.parseDouble(ParseJSONForecast(ReturnForecastResponseJSON(), "tmax").get(position))-273.15)+"°C"+ " | Low - "+String.format("%.1f",Double.parseDouble(DifferentFunctions.ParseJSONForecast(DifferentFunctions.ReturnForecastResponseJSON(), "tmin").get(position))-273.15)+"°C");
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
