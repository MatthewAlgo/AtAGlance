package app.matthewsgalaxy.ataglance.AdapterClasses;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;

public class RecyclerViewHeadlinesAdapter extends RecyclerView.Adapter<RecyclerViewHeadlinesAdapter.ViewHolder>{

    private static final String TAG = "Cannot invoke method length() on null object";

    private ArrayList<String> ArrayListURLValues;
    private ArrayList<String> TitlesArrayList;
    private ArrayList<String> DescriptionsArrayList;
    private ArrayList<String> ImageURLArrayList;

    private Context mContext;

    public RecyclerViewHeadlinesAdapter(ArrayList<String> arrayListURLValues, ArrayList<String> titlesArrayList,ArrayList<String> descriptionsArrayList,
                                       ArrayList<String> imageURLArrayList) {
        ArrayListURLValues = arrayListURLValues;
        TitlesArrayList=titlesArrayList;
        DescriptionsArrayList = descriptionsArrayList;
        ImageURLArrayList = imageURLArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHeadlinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_extheadlines, parent, false);
        RecyclerViewHeadlinesAdapter.ViewHolder holder = new RecyclerViewHeadlinesAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHeadlinesAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        // Load The Features

        // Modify The Text for Titles
        holder.ChipNewsTitle.setText(TitlesArrayList.get(position));

        // Modify the Descriptions Text for Descriptions
        holder.NewsDescriptionText.setText(DescriptionsArrayList.get(position));


        // Modify Image According to URL -> Picasso
        Picasso.get().load(ImageURLArrayList.get(position)).fit().centerInside().into(holder.ImageNews);

    }

    @Override
    public int getItemCount() {
        return TitlesArrayList.size(); // How many list items are in my list
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // Holding Views

       RelativeLayout RelativeLatestNews,RellayoutWithNewsImage;
       CardView NewsCardView;
       ImageView ImageNews;
       Chip ChipURLLink;
       TextView ChipNewsTitle,NewsDescriptionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RelativeLatestNews = itemView.findViewById(R.id.RelativeLatestNews);
            // Our layout
            RellayoutWithNewsImage = itemView.findViewById(R.id.RellayoutWithNewsImage);

            NewsCardView = itemView.findViewById(R.id.NewsCardView);
            ImageNews = itemView.findViewById(R.id.ImageNews);
            ChipURLLink = itemView.findViewById(R.id.ChipURLLink);
            ChipNewsTitle = itemView.findViewById(R.id.ChipNewsTitle);
            NewsDescriptionText = itemView.findViewById(R.id.NewsDescriptionText);

        }
    }

}
