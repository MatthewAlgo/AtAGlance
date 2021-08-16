package app.matthewsgalaxy.ataglance.ui.AtAGlance;

import static app.matthewsgalaxy.ataglance.DifferentFunctions.ParseJSONWorldNews;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.databinding.FragmentAtaglanceBinding;

public class TechnologyNewsPart extends AtAGlanceFragment {
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

    public TechnologyNewsPart(FragmentAtaglanceBinding b) {
        PrevArticleChip3 = b.PrevArticleChip3;
        NextArticleChip3 = b.NextArticleChip3;
        ChipURLLink3 = b.ChipURLLink3;
        NewsDescriptionText3 = b.NewsDescriptionText3;
        ChipNewsTitle3 = b.ChipNewsTitle3;
        ImageNews3 = b.ImageNews3;
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


    public void WhatToHappenWhenRequestIsProvided(String JsonRequest) {
        MyTitlesArrayListForTechnologyNews = ParseJSONWorldNews(JsonRequest, "news_title");
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

