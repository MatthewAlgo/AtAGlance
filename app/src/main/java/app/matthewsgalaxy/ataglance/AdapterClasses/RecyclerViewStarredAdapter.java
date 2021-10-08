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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.UserInterface.StarredArticles.starredArticlesFragment;

public class RecyclerViewStarredAdapter extends RecyclerView.Adapter<RecyclerViewStarredAdapter.ViewHolder>{
    private static final String TAG = "Cannot invoke method length() on null object";

    private ArrayList<String> ArrayListURLValues;
    private ArrayList<String> TitlesArrayList;
    private ArrayList<String> DescriptionsArrayList;
    private ArrayList<String> ImageURLArrayList;

    private Chip ChipURLLink;
    private Chip ChipRemoveItem;

    private Context mContext;

    public RecyclerViewStarredAdapter(ArrayList<String> arrayListURLValues, ArrayList<String> titlesArrayList,ArrayList<String> descriptionsArrayList,
                                        ArrayList<String> imageURLArrayList) {
        ArrayListURLValues = MyURLFaves;
        TitlesArrayList=MyTitlesFaves;
        DescriptionsArrayList = MyDescriptionsFaves;
        ImageURLArrayList = MyImagesURLFaves;
    }


    @NonNull
    @Override
    public RecyclerViewStarredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_starredheads, parent, false);
        RecyclerViewStarredAdapter.ViewHolder holder = new RecyclerViewStarredAdapter.ViewHolder(view);
        this.mContext = view.getContext();
        try{
            MyTitlesFaves = ReadJSONWithStarred(mContext).get(0);
            MyDescriptionsFaves = ReadJSONWithStarred(mContext).get(1);
            MyURLFaves = ReadJSONWithStarred(mContext).get(2);
            MyImagesURLFaves = ReadJSONWithStarred(mContext).get(3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewStarredAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        // Load The Features
        try {
            // Modify The Text for Titles
            if (MyTitlesFaves.get(holder.getAdapterPosition()) != null && MyTitlesFaves.get(holder.getAdapterPosition()) != "" && MyTitlesFaves.get(holder.getAdapterPosition()) != "null") {
                holder.ChipNewsTitle.setText(MyTitlesFaves.get(holder.getAdapterPosition()));
            } else {
                holder.ChipNewsTitle.setText("This Article Has No Title");
            }

            // Modify the Descriptions Text for Descriptions
            if (MyDescriptionsFaves.get(holder.getAdapterPosition()) != null && MyDescriptionsFaves.get(holder.getAdapterPosition()) != "" && MyDescriptionsFaves.get(holder.getAdapterPosition()) != "null") {
                holder.NewsDescriptionText.setText(MyDescriptionsFaves.get(holder.getAdapterPosition()));
            } else {
                holder.NewsDescriptionText.setText("This article has no description");
            }

            // Modify Image According to URL -> Picasso
            if (isOnline(mContext)) {
                try {
                    Picasso.get().load(MyImagesURLFaves.get(holder.getAdapterPosition())).fit().centerInside().into(holder.ImageNews);
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
                    SetOnClickListenersForUrlInBrowser(MyURLFaves.get(holder.getAdapterPosition()), view.getContext());
                }
            });
            ChipRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(holder.getAdapterPosition() == -1){
                            DeleteElementFromFavesArray(0);

                            notifyItemRemoved(holder.getAdapterPosition()+1);
                        }else {
                            DeleteElementFromFavesArray(holder.getAdapterPosition());

                            WriteJSONWithStarred(mContext,MyTitlesFaves,MyDescriptionsFaves,MyURLFaves,MyImagesURLFaves);

                            /*
                             TODO:
                             ArrayList<ArrayList<String>> Updated = ReadJSONWithStarred(mContext);
                             MyTitlesFaves = Updated.get(0);
                             MyDescriptionsFaves = Updated.get(1);
                             MyURLFaves = Updated.get(2);
                             MyImagesURLFaves = Updated.get(3);
                             Toast.makeText(mContext, "Deleted item at position: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            */

                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(mContext, "Successfully deleted item ", Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mContext, "Failed To Delete Item. Tried to delete item at position: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
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
            ChipRemoveItem = itemView.findViewById(R.id.ChipRemoveItem);
            ChipNewsTitle = itemView.findViewById(R.id.ChipNewsTitle);
            NewsDescriptionText = itemView.findViewById(R.id.NewsDescriptionText);
        }
    }
}
