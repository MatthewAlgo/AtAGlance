package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.MyTitlesArrayListForWorldNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseScienceNews;

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


public class ScienceNewsPart {
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

    public ScienceNewsPart(View b){
        PrevArticleChip2 = b.findViewById(R.id.PrevArticleChip2);
        NextArticleChip2 = b.findViewById(R.id.NextArticleChip2);
        ChipURLLink2 = b.findViewById(R.id.ChipURLLink2);
        NewsDescriptionText2 = b.findViewById(R.id.NewsDescriptionText2);
        ChipNewsTitle2 = b.findViewById(R.id.ChipNewsTitle2);
        ImageNews2 = b.findViewById(R.id.ImageNews2);
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

    public void WhatToHappenWhenRequestIsProvided(String JsonRequest, Context Mcontext){
        // Write the cached file here
        if(!ParseJSONWorldNews(JsonRequest, "status").get(0).equals("ok")){
            // Something bad happened
            //Toast.makeText(Mcontext, "There was a problem in the response for ScienceNews. Please try again later", Toast.LENGTH_LONG).show();
            Log.d(TAG, "WhatToHappenWhenRequestIsProvided: Response Error");
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(Mcontext,"JSON_SCIENCENEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(Mcontext, "JSON_SCIENCENEWS_CACHE.json");
        MyTitlesArrayListForScienceNews = ParseJSONWorldNews(Response,"news_title");
        ResponseScienceNews = Response;
        if(Response != null && Response != "") {

            ChipNewsTitle2.setText(MyTitlesArrayListForScienceNews.get(0));

            MyDescriptionsArrayListForScienceNews = ParseJSONWorldNews(Response, "news_descr");
            if (MyDescriptionsArrayListForScienceNews.get(0) != null) {
                NewsDescriptionText2.setText(MyDescriptionsArrayListForScienceNews.get(0));
            }

            MyURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url");

            // To load the first corresponding image
            MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url_to_img");
            MyIMGURLArrayListForScienceNews = ParseJSONWorldNews(Response, "news_url_to_img");
            if (MyIMGURLArrayListForScienceNews.get(0) == null || MyIMGURLArrayListForScienceNews.get(0) == "null") {
                ImageNews2.setImageResource(R.drawable.materialwall);
            } else {
                Picasso.get().load(MyIMGURLArrayListForScienceNews.get(0)).fit().centerInside().into(ImageNews2); // Set Image
            }
        }
    }
}
