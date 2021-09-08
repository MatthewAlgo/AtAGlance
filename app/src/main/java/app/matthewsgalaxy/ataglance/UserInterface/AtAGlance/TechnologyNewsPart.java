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


public class TechnologyNewsPart {
    public Chip PrevArticleChip3, NextArticleChip3, ChipURLLink3;
    public TextView NewsDescriptionText3, ChipNewsTitle3;
    public ImageView ImageNews3;
    public int CurrentArticleNumber = 0;

    public ArrayList<String> MyTitlesArrayListForTechnologyNews;
    public ArrayList<String> MyDescriptionsArrayListForTechnologyNews;
    public ArrayList<String> MyURLArrayListForTechnologyNews;
    public ArrayList<String> MyIMGURLArrayListForTechnologyNews;
    public String ResponseTechnologyJSON;

    public Chip getPrevArticleChip3() {
        return PrevArticleChip3;
    }

    public void setPrevArticleChip3(Chip prevArticleChip3) {
        PrevArticleChip3 = prevArticleChip3;
    }

    public Chip getNextArticleChip3() {
        return NextArticleChip3;
    }

    public void setNextArticleChip33(Chip nextArticleChip3) {
        NextArticleChip3 = nextArticleChip3;
    }

    public Chip getChipURLLink3() {
        return ChipURLLink3;
    }

    public void setChipURLLink3(Chip chipURLLink3) {
        ChipURLLink3 = chipURLLink3;
    }

    public TextView getNewsDescriptionText3() {
        return NewsDescriptionText3;
    }

    public void setNewsDescriptionText3(TextView newsDescriptionText3) {
        NewsDescriptionText3 = newsDescriptionText3;
    }

    public TextView getChipNewsTitle3() {
        return ChipNewsTitle3;
    }

    public void setChipNewsTitle3(TextView chipNewsTitle3) {
        ChipNewsTitle3 = chipNewsTitle3;
    }

    public ImageView getImageNews3() {
        return ImageNews3;
    }

    public void setImageNews3(ImageView imageNews3) {
        ImageNews3 = imageNews3;
    }

    public int getCurrentArticleNumber() {
        return CurrentArticleNumber;
    }

    public void setCurrentArticleNumber(int currentArticleNumber) {
        CurrentArticleNumber = currentArticleNumber;
    }

    public ArrayList<String> getMyTitlesArrayListForTechnologyNews() {
        return MyTitlesArrayListForTechnologyNews;
    }

    public void setMyTitlesArrayListForTechnologyNews(ArrayList<String> myTitlesArrayListForTechnologyNews) {
        MyTitlesArrayListForTechnologyNews = myTitlesArrayListForTechnologyNews;
    }

    public ArrayList<String> getMyDescriptionsArrayListForTechnologyNews() {
        return MyDescriptionsArrayListForTechnologyNews;
    }

    public void setMyDescriptionsArrayListForTechnologyNews(ArrayList<String> myDescriptionsArrayListForTechnologyNews) {
        MyDescriptionsArrayListForTechnologyNews = myDescriptionsArrayListForTechnologyNews;
    }

    public ArrayList<String> getMyURLArrayListForTechnologyNews() {
        return MyURLArrayListForTechnologyNews;
    }

    public void setMyURLArrayListForTechnologyNews(ArrayList<String> myURLArrayListForTechnologyNews) {
        MyURLArrayListForTechnologyNews = myURLArrayListForTechnologyNews;
    }

    public ArrayList<String> getMyIMGURLArrayListForTechnologyNews() {
        return MyIMGURLArrayListForTechnologyNews;
    }

    public void setMyIMGURLArrayListForTechnologyNews(ArrayList<String> myIMGURLArrayListForTechnologyNews) {
        MyIMGURLArrayListForTechnologyNews = myIMGURLArrayListForTechnologyNews;
    }

    public String getResponseTechnologyJSON() {
        return ResponseTechnologyJSON;
    }

    public void setResponseTechnologyJSON(String responseTechnologyJSON) {
        ResponseTechnologyJSON = responseTechnologyJSON;
    }

    public TechnologyNewsPart(View b) {
        PrevArticleChip3 = b.findViewById(R.id.PrevArticleChip3);
        NextArticleChip3 = b.findViewById(R.id.NextArticleChip3);
        ChipURLLink3 = b.findViewById(R.id.ChipURLLink3);
        NewsDescriptionText3 = b.findViewById(R.id.NewsDescriptionText3);
        ChipNewsTitle3 = b.findViewById(R.id.ChipNewsTitle3);
        ImageNews3 = b.findViewById(R.id.ImageNews3);
        SetOnClickListeners();
    }

    private void SetOnClickListeners() {
        PrevArticleChip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForTechnologyNews != null) {
                    if (CurrentArticleNumber > 0) {
                        CurrentArticleNumber--;

                        ImageNews3.setImageResource(R.drawable.materialwall);
                        if (!MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber).equals("null") && MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews3); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews3.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews3.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle3.setText(MyTitlesArrayListForTechnologyNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText3.setText(MyDescriptionsArrayListForTechnologyNews.get(CurrentArticleNumber)); // Set Description
                    }
                }
            }
        });

        NextArticleChip3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForTechnologyNews != null) {
                    if (CurrentArticleNumber < MyTitlesArrayListForTechnologyNews.size() - 1) {
                        CurrentArticleNumber++;
                        // Initially set Image Value to a nice resource
                        ImageNews3.setImageResource(R.drawable.materialwall);

                        if (MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber) != "null" && MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews3); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews3.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews3.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle3.setText(MyTitlesArrayListForTechnologyNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText3.setText(MyDescriptionsArrayListForTechnologyNews.get(CurrentArticleNumber)); // Set Description
                    }
                }

            }
        });
    }


    public void WhatToHappenWhenRequestIsProvided(String JsonRequest, Context MyContext) {
        // Write the cached file here
        if(!ParseJSONWorldNews(JsonRequest, "status").get(0).equals("ok")){
            // Something bad happened
            // Toast.makeText(MyContext, "There was a problem in the response for TechNews. Please try again later", Toast.LENGTH_LONG).show();
            Log.d(TAG, "WhatToHappenWhenRequestIsProvided: Response Error");
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(MyContext,"JSON_TECHNEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(MyContext, "JSON_TECHNEWS_CACHE.json");
        ResponseTechnologyJSON = Response;
        MyTitlesArrayListForTechnologyNews = ParseJSONWorldNews(Response, "news_title");
        ChipNewsTitle3.setText(MyTitlesArrayListForTechnologyNews.get(0));

        MyDescriptionsArrayListForTechnologyNews = ParseJSONWorldNews(JsonRequest, "news_descr");
        if (MyDescriptionsArrayListForTechnologyNews.get(0) != null) {
            NewsDescriptionText3.setText(MyDescriptionsArrayListForTechnologyNews.get(0));
        }

        MyURLArrayListForTechnologyNews = ParseJSONWorldNews(JsonRequest, "news_url");

        // To load the first corresponding image
        MyIMGURLArrayListForTechnologyNews = ParseJSONWorldNews(JsonRequest, "news_url_to_img");
        if(MyIMGURLArrayListForTechnologyNews.get(0) == null || MyIMGURLArrayListForTechnologyNews.get(0) == "null"){
            ImageNews3.setImageResource(R.drawable.materialwall);
        }else {
            Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(0)).fit().centerInside().into(ImageNews3); // Set Image
        }
    }

}

