package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.databinding.FragmentAtaglanceBinding;

public class EntertainmentNewsPart {

    public Chip PrevArticleChip6, NextArticleChip6,ChipURLLink6;
    public TextView NewsDescriptionText6,ChipNewsTitle6;
    public ImageView ImageNews6;
    public int CurrentArticleNumber=0;

    public ArrayList<String> MyTitlesArrayListForEntertainmentNews;
    public ArrayList<String> MyDescriptionsArrayListForEntertainamentNews;
    public ArrayList<String> MyURLArrayListForEntertainmentNews;
    public ArrayList<String> MyIMGURLArrayListForEntertainmentNews;
    public String ResponseEntertainmentJSON;

    public Chip getPrevArticleChip6() {
        return PrevArticleChip6;
    }

    public void setPrevArticleChip6(Chip prevArticleChip6) {
        PrevArticleChip6 = prevArticleChip6;
    }

    public Chip getNextArticleChip6() {
        return NextArticleChip6;
    }

    public void setNextArticleChip6(Chip nextArticleChip6) {
        NextArticleChip6 = nextArticleChip6;
    }

    public Chip getChipURLLink6() {
        return ChipURLLink6;
    }

    public void setChipURLLink6(Chip chipURLLink6) {
        ChipURLLink6 = chipURLLink6;
    }

    public TextView getNewsDescriptionText6() {
        return NewsDescriptionText6;
    }

    public void setNewsDescriptionText6(TextView newsDescriptionText6) {
        NewsDescriptionText6 = newsDescriptionText6;
    }

    public TextView getChipNewsTitle6() {
        return ChipNewsTitle6;
    }

    public void setChipNewsTitle6(TextView chipNewsTitle6) {
        ChipNewsTitle6 = chipNewsTitle6;
    }

    public ImageView getImageNews6() {
        return ImageNews6;
    }

    public void setImageNews6(ImageView imageNews6) {
        ImageNews6 = imageNews6;
    }

    public int getCurrentArticleNumber() {
        return CurrentArticleNumber;
    }

    public void setCurrentArticleNumber(int currentArticleNumber) {
        CurrentArticleNumber = currentArticleNumber;
    }

    public ArrayList<String> getMyTitlesArrayListForEntertainmentNews() {
        return MyTitlesArrayListForEntertainmentNews;
    }

    public void setMyTitlesArrayListForEntertainmentNews(ArrayList<String> myTitlesArrayListForEntertainmentNews) {
        MyTitlesArrayListForEntertainmentNews = myTitlesArrayListForEntertainmentNews;
    }

    public ArrayList<String> getMyDescriptionsArrayListForEntertainamentNews() {
        return MyDescriptionsArrayListForEntertainamentNews;
    }

    public void setMyDescriptionsArrayListForEntertainamentNews(ArrayList<String> myDescriptionsArrayListForEntertainamentNews) {
        MyDescriptionsArrayListForEntertainamentNews = myDescriptionsArrayListForEntertainamentNews;
    }

    public ArrayList<String> getMyURLArrayListForEntertainmentNews() {
        return MyURLArrayListForEntertainmentNews;
    }

    public void setMyURLArrayListForEntertainmentNews(ArrayList<String> myURLArrayListForEntertainmentNews) {
        MyURLArrayListForEntertainmentNews = myURLArrayListForEntertainmentNews;
    }

    public ArrayList<String> getMyIMGURLArrayListForEntertainmentNews() {
        return MyIMGURLArrayListForEntertainmentNews;
    }

    public void setMyIMGURLArrayListForEntertainmentNews(ArrayList<String> myIMGURLArrayListForEntertainmentNews) {
        MyIMGURLArrayListForEntertainmentNews = myIMGURLArrayListForEntertainmentNews;
    }

    public String getResponseEntertainmentJSON() {
        return ResponseEntertainmentJSON;
    }

    public void setResponseEntertainmentJSON(String responseEntertainmentJSON) {
        ResponseEntertainmentJSON = responseEntertainmentJSON;
    }

    public EntertainmentNewsPart(FragmentAtaglanceBinding b) {
        PrevArticleChip6 = b.PrevArticleChip6;
        NextArticleChip6 = b.NextArticleChip6;
        ChipURLLink6 = b.ChipURLLink6;
        NewsDescriptionText6 = b.NewsDescriptionText6;
        ChipNewsTitle6 = b.ChipNewsTitle6;
        ImageNews6 = b.ImageNews6;
        SetOnClickListeners();
    }

    private void SetOnClickListeners() {
        PrevArticleChip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForEntertainmentNews != null) {
                    if (CurrentArticleNumber > 0) {
                        CurrentArticleNumber--;

                        ImageNews6.setImageResource(R.drawable.materialwall);
                        if (!MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber).equals("null") && MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews6); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews6.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews6.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle6.setText(MyTitlesArrayListForEntertainmentNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText6.setText(MyDescriptionsArrayListForEntertainamentNews.get(CurrentArticleNumber)); // Set Description
                    }
                }
            }
        });

        NextArticleChip6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForEntertainmentNews != null) {
                    if (CurrentArticleNumber < MyTitlesArrayListForEntertainmentNews.size() - 1) {
                        CurrentArticleNumber++;
                        // Initially set Image Value to a nice resource
                        ImageNews6.setImageResource(R.drawable.materialwall);

                        if (MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber) != "null" && MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews6); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews6.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews6.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle6.setText(MyTitlesArrayListForEntertainmentNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText6.setText(MyDescriptionsArrayListForEntertainamentNews.get(CurrentArticleNumber)); // Set Description
                    }
                }

            }
        });
    }


    public void WhatToHappenWhenRequestIsProvided(String JsonRequest, Context Mcontext) {
        // Write the cached file here
        if(DifferentFunctions.ParseJSONWorldNews(JsonRequest,"news_title").get(0)==null){
            // Something bad happened
            Toast.makeText(Mcontext, "There was a problem in the response for TechNews. Please try again later", Toast.LENGTH_LONG).show();
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(Mcontext,"JSON_ENTERTAINMENTNEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(Mcontext, "JSON_ENTERTAINMENTNEWS_CACHE.json");

        MyTitlesArrayListForEntertainmentNews = ParseJSONWorldNews(Response, "news_title");
        ChipNewsTitle6.setText(MyTitlesArrayListForEntertainmentNews.get(0));

        MyDescriptionsArrayListForEntertainamentNews = ParseJSONWorldNews(JsonRequest, "news_descr");
        if (MyDescriptionsArrayListForEntertainamentNews.get(0) != null) {
            NewsDescriptionText6.setText(MyDescriptionsArrayListForEntertainamentNews.get(0));
        }

        MyURLArrayListForEntertainmentNews = ParseJSONWorldNews(JsonRequest, "news_url");

        // To load the first corresponding image
        MyIMGURLArrayListForEntertainmentNews = ParseJSONWorldNews(JsonRequest, "news_url_to_img");
        if(MyIMGURLArrayListForEntertainmentNews.get(0) == null || MyIMGURLArrayListForEntertainmentNews.get(0) == "null"){
            ImageNews6.setImageResource(R.drawable.materialwall);
        }else {
            Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(0)).fit().centerInside().into(ImageNews6); // Set Image
        }
    }


}
