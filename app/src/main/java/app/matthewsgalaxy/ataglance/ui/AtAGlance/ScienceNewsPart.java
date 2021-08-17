package app.matthewsgalaxy.ataglance.ui.AtAGlance;

import static app.matthewsgalaxy.ataglance.additionalClasses.DifferentFunctions.ParseJSONWorldNews;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.R;
import app.matthewsgalaxy.ataglance.databinding.FragmentAtaglanceBinding;

public class ScienceNewsPart extends AtAGlanceFragment {
    public Chip PrevArticleChip2, NextArticleChip2,ChipURLLink2;
    public TextView NewsDescriptionText2,ChipNewsTitle2;
    public ImageView ImageNews2;
    public int CurrentArticleNumber=0;

    public ArrayList<String> MyTitlesArrayListForScienceNews;
    public ArrayList<String> MyDescriptionsArrayListForScienceNews;
    public ArrayList<String> MyURLArrayListForScienceNews;
    public ArrayList<String> MyIMGURLArrayListForScienceNews;
    public String ResponseScienceJSON;

    public Chip getPrevArticleChip2() {
        return PrevArticleChip2;
    }

    public void setPrevArticleChip2(Chip prevArticleChip2) {
        PrevArticleChip2 = prevArticleChip2;
    }

    public Chip getNextArticleChip2() {
        return NextArticleChip2;
    }

    public void setNextArticleChip2(Chip nextArticleChip2) {
        NextArticleChip2 = nextArticleChip2;
    }

    public Chip getChipURLLink2() {
        return ChipURLLink2;
    }

    public void setChipURLLink2(Chip chipURLLink2) {
        ChipURLLink2 = chipURLLink2;
    }

    public TextView getNewsDescriptionText2() {
        return NewsDescriptionText2;
    }

    public void setNewsDescriptionText2(TextView newsDescriptionText2) {
        NewsDescriptionText2 = newsDescriptionText2;
    }

    public TextView getChipNewsTitle2() {
        return ChipNewsTitle2;
    }

    public void setChipNewsTitle2(TextView chipNewsTitle2) {
        ChipNewsTitle2 = chipNewsTitle2;
    }

    public ImageView getImageNews2() {
        return ImageNews2;
    }

    public void setImageNews2(ImageView imageNews2) {
        ImageNews2 = imageNews2;
    }

    public int getCurrentArticleNumber() {
        return CurrentArticleNumber;
    }

    public void setCurrentArticleNumber(int currentArticleNumber) {
        CurrentArticleNumber = currentArticleNumber;
    }

    public ArrayList<String> getMyTitlesArrayListForScienceNews() {
        return MyTitlesArrayListForScienceNews;
    }

    public void setMyTitlesArrayListForScienceNews(ArrayList<String> myTitlesArrayListForScienceNews) {
        MyTitlesArrayListForScienceNews = myTitlesArrayListForScienceNews;
    }

    public ArrayList<String> getMyDescriptionsArrayListForScienceNews() {
        return MyDescriptionsArrayListForScienceNews;
    }

    public void setMyDescriptionsArrayListForScienceNews(ArrayList<String> myDescriptionsArrayListForScienceNews) {
        MyDescriptionsArrayListForScienceNews = myDescriptionsArrayListForScienceNews;
    }

    public ArrayList<String> getMyURLArrayListForScienceNews() {
        return MyURLArrayListForScienceNews;
    }

    public void setMyURLArrayListForScienceNews(ArrayList<String> myURLArrayListForScienceNews) {
        MyURLArrayListForScienceNews = myURLArrayListForScienceNews;
    }

    public ArrayList<String> getMyIMGURLArrayListForScienceNews() {
        return MyIMGURLArrayListForScienceNews;
    }

    public void setMyIMGURLArrayListForScienceNews(ArrayList<String> myIMGURLArrayListForScienceNews) {
        MyIMGURLArrayListForScienceNews = myIMGURLArrayListForScienceNews;
    }

    public String getResponseScienceJSON() {
        return ResponseScienceJSON;
    }

    public void setResponseScienceJSON(String responseScienceJSON) {
        ResponseScienceJSON = responseScienceJSON;
    }

    public ScienceNewsPart(FragmentAtaglanceBinding b){
        PrevArticleChip2 = b.PrevArticleChip2;
        NextArticleChip2 = b.NextArticleChip2;
        ChipURLLink2 = b.ChipURLLink2;
        NewsDescriptionText2 = b.NewsDescriptionText2;
        ChipNewsTitle2 = b.ChipNewsTitle2;
        ImageNews2 = b.ImageNews2;
        SetOnClickListeners();
    }
    private void SetOnClickListeners(){
        PrevArticleChip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyTitlesArrayListForScienceNews !=null) {
                    if (CurrentArticleNumber > 0) {
                        CurrentArticleNumber--;

                        ImageNews2.setImageResource(R.drawable.materialwall);
                        if(!MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber).equals("null") && MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)!=null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews2); // Set Image
                            }catch(Exception e) {
                                e.printStackTrace();
                                ImageNews2.setImageResource(R.drawable.materialwall);
                            }
                        }else{
                            ImageNews2.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle2.setText(MyTitlesArrayListForScienceNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText2.setText(MyDescriptionsArrayListForScienceNews.get(CurrentArticleNumber)); // Set Description
                    }
                }
            }
        });

        NextArticleChip2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(MyTitlesArrayListForWorldNews !=null) {
                    if (CurrentArticleNumber < MyTitlesArrayListForWorldNews.size() - 1 ) {
                        CurrentArticleNumber++;
                        // Initially set Image Value to a nice resource
                        ImageNews2.setImageResource(R.drawable.materialwall);

                        if(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber) != "null" && MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)!=null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews2); // Set Image
                            }catch(Exception e){
                                e.printStackTrace();
                                ImageNews2.setImageResource(R.drawable.materialwall);
                            }
                        }else{
                            ImageNews2.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle2.setText(MyTitlesArrayListForScienceNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText2.setText(MyDescriptionsArrayListForScienceNews.get(CurrentArticleNumber)); // Set Description
                    }
                }

            }
        });
    }

    public void WhatToHappenWhenRequestIsProvided(String JsonRequest){
        MyTitlesArrayListForScienceNews = ParseJSONWorldNews(JsonRequest,"news_title");
        ChipNewsTitle2.setText(MyTitlesArrayListForScienceNews.get(0));

        MyDescriptionsArrayListForScienceNews = ParseJSONWorldNews(JsonRequest,"news_descr");
        if(MyDescriptionsArrayListForScienceNews.get(0)!=null) {
            NewsDescriptionText2.setText(MyDescriptionsArrayListForScienceNews.get(0));
        }

        MyURLArrayListForScienceNews = ParseJSONWorldNews(JsonRequest,"news_url");

        // To load the first corresponding image
        MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(JsonRequest,"news_url_to_img");
        MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(JsonRequest, "news_url_to_img");
        if(MyIMGURLArrayListForScienceNews.get(0) == null || MyIMGURLArrayListForScienceNews.get(0) == "null"){
            ImageNews2.setImageResource(R.drawable.materialwall);
        }else {
            Picasso.get().load(MyIMGURLArrayListForScienceNews.get(0)).fit().centerInside().into(ImageNews2); // Set Image
        }
    }

}
