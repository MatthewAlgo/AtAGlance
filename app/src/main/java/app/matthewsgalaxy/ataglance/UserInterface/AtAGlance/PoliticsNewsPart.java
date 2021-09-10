package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;

public class PoliticsNewsPart {
    public Chip PrevArticleChip5, NextArticleChip5,ChipURLLink5;
    public TextView NewsDescriptionText5,ChipNewsTitle5;
    public ImageView ImageNews5;
    public int CurrentArticleNumber=0;

    public static ArrayList<String> MyTitlesArrayListForPoliticsNews;
    public static ArrayList<String> MyDescriptionsArrayListForPoliticsNews;
    public static ArrayList<String> MyURLArrayListForPoliticsNews;
    public static ArrayList<String> MyIMGURLArrayListForPoliticsNews;
    public String ResponsePoliticsJSON;

    public Chip getPrevArticleChip5() {
        return PrevArticleChip5;
    }

    public void setPrevArticleChip5(Chip prevArticleChip5) {
        PrevArticleChip5 = prevArticleChip5;
    }

    public Chip getNextArticleChip5() {
        return NextArticleChip5;
    }

    public void setNextArticleChip5(Chip nextArticleChip5) {
        NextArticleChip5 = nextArticleChip5;
    }

    public Chip getChipURLLink5() {
        return ChipURLLink5;
    }

    public void setChipURLLink5(Chip chipURLLink5) {
        ChipURLLink5 = chipURLLink5;
    }

    public TextView getNewsDescriptionText5() {
        return NewsDescriptionText5;
    }

    public void setNewsDescriptionText5(TextView newsDescriptionText5) {
        NewsDescriptionText5 = newsDescriptionText5;
    }

    public TextView getChipNewsTitle5() {
        return ChipNewsTitle5;
    }

    public void setChipNewsTitle5(TextView chipNewsTitle5) {
        ChipNewsTitle5 = chipNewsTitle5;
    }

    public ImageView getImageNews5() {
        return ImageNews5;
    }

    public void setImageNews5(ImageView imageNews5) {
        ImageNews5 = imageNews5;
    }

    public int getCurrentArticleNumber() {
        return CurrentArticleNumber;
    }

    public void setCurrentArticleNumber(int currentArticleNumber) {
        CurrentArticleNumber = currentArticleNumber;
    }

    public ArrayList<String> getMyTitlesArrayListForPoliticsNews() {
        return MyTitlesArrayListForPoliticsNews;
    }

    public void setMyTitlesArrayListForPoliticsNews(ArrayList<String> myTitlesArrayListForPoliticsNews) {
        MyTitlesArrayListForPoliticsNews = myTitlesArrayListForPoliticsNews;
    }

    public ArrayList<String> getMyDescriptionsArrayListForPoliticsNews() {
        return MyDescriptionsArrayListForPoliticsNews;
    }

    public void setMyDescriptionsArrayListForPoliticsNews(ArrayList<String> myDescriptionsArrayListForPoliticsNews) {
        MyDescriptionsArrayListForPoliticsNews = myDescriptionsArrayListForPoliticsNews;
    }

    public ArrayList<String> getMyURLArrayListForPoliticsNews() {
        return MyURLArrayListForPoliticsNews;
    }

    public void setMyURLArrayListForPoliticsNews(ArrayList<String> myURLArrayListForPoliticsNews) {
        MyURLArrayListForPoliticsNews = myURLArrayListForPoliticsNews;
    }

    public ArrayList<String> getMyIMGURLArrayListForPoliticsNews() {
        return MyIMGURLArrayListForPoliticsNews;
    }

    public void setMyIMGURLArrayListForPoliticsNews(ArrayList<String> myIMGURLArrayListForPoliticsNews) {
        MyIMGURLArrayListForPoliticsNews = myIMGURLArrayListForPoliticsNews;
    }

    public String getResponsePoliticsJSON() {
        return ResponsePoliticsJSON;
    }

    public void setResponsePoliticsJSON(String responsePoliticsJSON) {
        ResponsePoliticsJSON = responsePoliticsJSON;
    }

    public PoliticsNewsPart(View b) {
        PrevArticleChip5 = b.findViewById(R.id.PrevArticleChip5);
        NextArticleChip5 = b.findViewById(R.id.NextArticleChip5);
        ChipURLLink5 = b.findViewById(R.id.ChipURLLink5);
        NewsDescriptionText5 = b.findViewById(R.id.NewsDescriptionText5);
        ChipNewsTitle5 = b.findViewById(R.id.ChipNewsTitle5);
        ImageNews5 = b.findViewById(R.id.ImageNews5);
        SetOnClickListeners();
    }

    private void SetOnClickListeners() {
        PrevArticleChip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForPoliticsNews != null) {
                    if (CurrentArticleNumber > 0) {
                        CurrentArticleNumber--;

                        ImageNews5.setImageResource(R.drawable.materialwall);
                        if (!MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber).equals("null") && MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber) != null) {
                            try {
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews5); // Set Image
                                else
                                    ImageNews5.setImageResource(R.drawable.materialwall);
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews5.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews5.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle5.setText(MyTitlesArrayListForPoliticsNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText5.setText(MyDescriptionsArrayListForPoliticsNews.get(CurrentArticleNumber)); // Set Description
                    }
                }
            }
        });

        NextArticleChip5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForPoliticsNews != null) {
                    if (CurrentArticleNumber < MyTitlesArrayListForPoliticsNews.size() - 1) {
                        CurrentArticleNumber++;
                        // Initially set Image Value to a nice resource
                        ImageNews5.setImageResource(R.drawable.materialwall);

                        if (MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber) != "null" && MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber) != null) {
                            try {
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews5); // Set Image
                                else
                                    ImageNews5.setImageResource(R.drawable.materialwall);
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews5.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews5.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle5.setText(MyTitlesArrayListForPoliticsNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText5.setText(MyDescriptionsArrayListForPoliticsNews.get(CurrentArticleNumber)); // Set Description
                    }
                }

            }
        });
    }


    public void WhatToHappenWhenRequestIsProvided(String JsonRequest, Context Mcontext) {
        // Write the cached file here
        if(!ParseJSONWorldNews(JsonRequest, "status").get(0).equals("ok")){
            // Something bad happened
            // Toast.makeText(Mcontext, "There was a problem in the response for PoliticsNews. Please try again later", Toast.LENGTH_LONG).show();
            Log.d(TAG, "WhatToHappenWhenRequestIsProvided: Response Error");
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(Mcontext,"JSON_POLITICSNEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(Mcontext, "JSON_POLITICSNEWS_CACHE.json");

        ResponsePoliticsJSON = Response;
        MyTitlesArrayListForPoliticsNews = ParseJSONWorldNews(Response, "news_title");
        ChipNewsTitle5.setText(MyTitlesArrayListForPoliticsNews.get(0));

        MyDescriptionsArrayListForPoliticsNews = ParseJSONWorldNews(JsonRequest, "news_descr");
        if (MyDescriptionsArrayListForPoliticsNews.get(0) != null) {
            NewsDescriptionText5.setText(MyDescriptionsArrayListForPoliticsNews.get(0));
        }

        MyURLArrayListForPoliticsNews = ParseJSONWorldNews(JsonRequest, "news_url");

        // To load the first corresponding image
        MyIMGURLArrayListForPoliticsNews = ParseJSONWorldNews(JsonRequest, "news_url_to_img");
        if(MyIMGURLArrayListForPoliticsNews.get(0) == null || MyIMGURLArrayListForPoliticsNews.get(0) == "null"){
            ImageNews5.setImageResource(R.drawable.materialwall);
        }else {
            if(AtAGlanceFragment.isOnline(Mcontext))
                Picasso.get().load(MyIMGURLArrayListForPoliticsNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews5); // Set Image
            else
                ImageNews5.setImageResource(R.drawable.materialwall);
        }
    }

}
