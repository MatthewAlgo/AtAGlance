package app.matthewsgalaxy.ataglance.adapterClasses;

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

public class RecyclerViewForecastAdapter extends RecyclerView.Adapter<RecyclerViewForecastAdapter.ViewHolder> {
    private static final String TAG = "Cannot invoke method length() on null object";

    private ArrayList<String> ArrayListConditions = new ArrayList<String>();
    private ArrayList<String> ImageCodes = new ArrayList<String>();
    private Context mContext;

    public RecyclerViewForecastAdapter(ArrayList<String> arrayListConditions, ArrayList<String> imageCodes, Context mContext) {
        ArrayListConditions = arrayListConditions;
        ImageCodes = imageCodes;
        this.mContext = mContext;
    }
    public RecyclerViewForecastAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_extforecast, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        // Load The Features

        ArrayListConditions.add("Foggy"); ArrayListConditions.add("Windy");
        ImageCodes.add("233");  ImageCodes.add("233");


        holder.MainConditionTextForecast.setText(ArrayListConditions.get(position));


    }

    @Override
    public int getItemCount() {
        return 2; // How many list items are in my list
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
