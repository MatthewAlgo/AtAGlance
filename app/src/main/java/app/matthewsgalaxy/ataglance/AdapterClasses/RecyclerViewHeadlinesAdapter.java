package app.matthewsgalaxy.ataglance.AdapterClasses;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ModifyImageToConditions;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONForecast;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ProcessTimeStamp;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ReturnForecastResponseJSON;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.GlobalTimeForExtendedForecast;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SetOnClickListenersForUrlInBrowser;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunriseGlobalHourString;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SunsetGlobalHourString;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private Chip ChipURLLink;

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
        try {
            // Modify The Text for Titles
            if (TitlesArrayList.get(position) != null && TitlesArrayList.get(position) != "" && TitlesArrayList.get(position) != "null") {
                holder.ChipNewsTitle.setText(TitlesArrayList.get(position));
            } else {
                holder.ChipNewsTitle.setText("This Article Has No Title");
            }

            // Modify the Descriptions Text for Descriptions
            if (DescriptionsArrayList.get(position) != null && DescriptionsArrayList.get(position) != "" && DescriptionsArrayList.get(position) != "null") {
                holder.NewsDescriptionText.setText(DescriptionsArrayList.get(position));
            } else {
                holder.NewsDescriptionText.setText("This article has no description");
            }

            // Modify Image According to URL -> Picasso
            if (isOnline(mContext)) {
                try {
                    Picasso.get().load(ImageURLArrayList.get(position)).fit().centerInside().into(holder.ImageNews);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    holder.ImageNews.setImageResource(R.drawable.materialwall); // Load Backup image
                }
            } else {
                holder.ImageNews.setImageResource(R.drawable.materialwall);
            }
            ChipURLLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetOnClickListenersForUrlInBrowser(ArrayListURLValues.get(holder.getPosition()), view.getContext());
                }
            });
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            Toast.makeText(mContext.getApplicationContext(), "There Was A Problem While Displaying the News List", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return TitlesArrayList.size(); // How many list items are in my list
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // Holding Views

       RelativeLayout RelativeLatestNews,RellayoutWithNewsImage;
       CardView NewsCardView;
       ImageView ImageNews;
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
