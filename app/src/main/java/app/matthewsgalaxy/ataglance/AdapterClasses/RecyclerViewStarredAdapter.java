package app.matthewsgalaxy.ataglance.AdapterClasses;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.DeleteElementFromFavesArray;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyDescriptionsFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyImagesURLFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyTitlesFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.MyURLFaves;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ReadJSONWithStarred;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.WriteJSONWithStarred;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.deleteFile;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.SetOnClickListenersForUrlInBrowser;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.CaseMap;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.UserInterface.StarredArticles.starredArticlesFragment;

public class RecyclerViewStarredAdapter extends RecyclerView.Adapter<RecyclerViewStarredAdapter.ViewHolder>{
    private static final String TAG = "Cannot invoke method length() on null object";
    private long OnBindCalls;
    private ArrayList<String> ArrayListURLValues = new ArrayList<>();
    private ArrayList<String> TitlesArrayList = new ArrayList<>();
    private ArrayList<String> DescriptionsArrayList = new ArrayList<>();
    private ArrayList<String> ImageURLArrayList = new ArrayList<>();

    private Chip ChipURLLink;
    private Chip ChipRemoveItem;

    private Context mContext;
    private TextView TextCrickets;

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public RecyclerViewStarredAdapter(ArrayList<String> arrayListURLValues, ArrayList<String> titlesArrayList,ArrayList<String> descriptionsArrayList,
                                        ArrayList<String> imageURLArrayList) {
        ArrayListURLValues = arrayListURLValues;
        TitlesArrayList=titlesArrayList;
        DescriptionsArrayList = descriptionsArrayList;
        ImageURLArrayList = imageURLArrayList;


    }


    @NonNull
    @Override
    public RecyclerViewStarredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_starredheads, parent, false);
        RecyclerViewStarredAdapter.ViewHolder holder = new RecyclerViewStarredAdapter.ViewHolder(view);
        this.mContext = view.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewStarredAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // holder.setIsRecyclable(false);
        Log.d(TAG, "onBindViewHolder: called");
        holder.position = position;
        OnBindCalls++;
        try {
            if (getItemCount() <= 1) {
                TextCrickets.setText("Nothing here but crickets\n??\\_(???)_/??");
            } else {
                TextCrickets.setText("");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        // Load The Features
        try {
            // Modify The Text for Titles
            if (TitlesArrayList.get(holder.position) != null && TitlesArrayList.get(holder.position) != "" && TitlesArrayList.get(holder.position) != "null") {
                holder.ChipNewsTitle.setText(TitlesArrayList.get(holder.position));
            } else {
                holder.ChipNewsTitle.setText("This Article Has No Title");
            }

            // Modify the Descriptions Text for Descriptions
            if (DescriptionsArrayList.get(holder.position) != null && DescriptionsArrayList.get(holder.position) != "" && DescriptionsArrayList.get(holder.position) != "null") {
                holder.NewsDescriptionText.setText(DescriptionsArrayList.get(holder.position));
            } else {
                holder.NewsDescriptionText.setText("This article has no description");
            }

            // Modify Image According to URL -> Picasso
            if (isOnline(mContext)) {
                try {
                    Picasso.get().load(ImageURLArrayList.get(holder.position)).fit().centerInside().into(holder.ImageNews);
                    if(ImageURLArrayList.isEmpty()) {
                        holder.ImageNews.setImageResource(R.drawable.materialwall);
                    }
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
                    try {
                        SetOnClickListenersForUrlInBrowser(ArrayListURLValues.get(holder.position), view.getContext());
                    }catch (Exception e){
                        Toast.makeText(mContext, "Could not access the link. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ChipRemoveItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    try {

                        // Remove item at position from arraylist
                        TitlesArrayList.remove(holder.position);
                        DescriptionsArrayList.remove(holder.position);
                        ArrayListURLValues.remove(holder.position);
                        ImageURLArrayList.remove(holder.position);

                        WriteJSONWithStarred(mContext,TitlesArrayList,DescriptionsArrayList,ArrayListURLValues,ImageURLArrayList);

                        ArrayList<ArrayList<String>> Updated = ReadJSONWithStarred(mContext);
                        TitlesArrayList = Updated.get(0);
                        DescriptionsArrayList = Updated.get(1);
                        ArrayListURLValues= Updated.get(2);
                        ImageURLArrayList = Updated.get(3);

                        // Update the URL Faves

                        MyTitlesFaves = ReadJSONWithStarred(mContext).get(0);
                        MyDescriptionsFaves = ReadJSONWithStarred(mContext).get(1);
                        MyURLFaves = ReadJSONWithStarred(mContext).get(2);
                        MyImagesURLFaves = ReadJSONWithStarred(mContext).get(3);

                        notifyItemRemoved(holder.position);
                        notifyItemRangeChanged(0, getItemCount()+1);

                        if(getItemCount() == 1){
                            TextCrickets.setText("Nothing here but crickets\n??\\_(???)_/??");
                        }else{
                            TextCrickets.setText("");
                        }
                        // notifyDataSetChanged();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch(Exception exc){
            System.out.println(exc.getMessage());
            Toast.makeText(mContext.getApplicationContext(), "There Was A Problem While Displaying the News List", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return TitlesArrayList.size()-1; // How many list items are in my list
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public int position; // Holding Views

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
            ChipRemoveItem = itemView.findViewById(R.id.ChipRemoveItem);
            ChipNewsTitle = itemView.findViewById(R.id.ChipNewsTitle);
            NewsDescriptionText = itemView.findViewById(R.id.NewsDescriptionText);

            TextCrickets = itemView.findViewById(R.id.TextCrickets);

        }
    }
}
