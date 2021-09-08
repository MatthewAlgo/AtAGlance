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

public class BusinessNewsPart {
    public Chip PrevArticleChip4, NextArticleChip4,ChipURLLink4;
    public TextView NewsDescriptionText4,ChipNewsTitle4;
    public ImageView ImageNews4;
    public int CurrentArticleNumber=0;

    public ArrayList<String> MyTitlesArrayListForBusinessNews;
    public ArrayList<String> MyDescriptionsArrayListForBusinessNews;
    public ArrayList<String> MyURLArrayListForBusinessNews;
    public ArrayList<String> MyIMGURLArrayListForBusinessNews;
    public String ResponseBusinessJSON;

    public Chip getPrevArticleChip4() {
        return PrevArticleChip4;
    }

    public void setPrevArticleChip4(Chip prevArticleChip4) {
        PrevArticleChip4 = prevArticleChip4;
    }

    public Chip getNextArticleChip4() {
        return NextArticleChip4;
    }

    public void setNextArticleChip4(Chip nextArticleChip4) {
        NextArticleChip4 = nextArticleChip4;
    }

    public Chip getChipURLLink4() {
        return ChipURLLink4;
    }

    public void setChipURLLink4(Chip chipURLLink4) {
        ChipURLLink4 = chipURLLink4;
    }

    public TextView getNewsDescriptionText4() {
        return NewsDescriptionText4;
    }

    public void setNewsDescriptionText4(TextView newsDescriptionText4) {
        NewsDescriptionText4 = newsDescriptionText4;
    }

    public TextView getChipNewsTitle4() {
        return ChipNewsTitle4;
    }

    public void setChipNewsTitle4(TextView chipNewsTitle4) {
        ChipNewsTitle4 = chipNewsTitle4;
    }

    public ImageView getImageNews4() {
        return ImageNews4;
    }

    public void setImageNews4(ImageView imageNews4) {
        ImageNews4 = imageNews4;
    }

    public int getCurrentArticleNumber() {
        return CurrentArticleNumber;
    }

    public void setCurrentArticleNumber(int currentArticleNumber) {
        CurrentArticleNumber = currentArticleNumber;
    }

    public ArrayList<String> getMyTitlesArrayListForBusinessNews() {
        return MyTitlesArrayListForBusinessNews;
    }

    public void setMyTitlesArrayListForBusinessNews(ArrayList<String> myTitlesArrayListForBusinessNews) {
        MyTitlesArrayListForBusinessNews = myTitlesArrayListForBusinessNews;
    }

    public ArrayList<String> getMyDescriptionsArrayListForBusinessNews() {
        return MyDescriptionsArrayListForBusinessNews;
    }

    public void setMyDescriptionsArrayListForBusinessNews(ArrayList<String> myDescriptionsArrayListForBusinessNews) {
        MyDescriptionsArrayListForBusinessNews = myDescriptionsArrayListForBusinessNews;
    }

    public ArrayList<String> getMyURLArrayListForBusinessNews() {
        return MyURLArrayListForBusinessNews;
    }

    public void setMyURLArrayListForBusinessNews(ArrayList<String> myURLArrayListForBusinessNews) {
        MyURLArrayListForBusinessNews = myURLArrayListForBusinessNews;
    }

    public ArrayList<String> getMyIMGURLArrayListForBusinessNews() {
        return MyIMGURLArrayListForBusinessNews;
    }

    public void setMyIMGURLArrayListForBusinessNews(ArrayList<String> myIMGURLArrayListForBusinessNews) {
        MyIMGURLArrayListForBusinessNews = myIMGURLArrayListForBusinessNews;
    }

    public String getResponseBusinessJSON() {
        return ResponseBusinessJSON;
    }

    public void setResponseBusinessJSON(String responseBusinessJSON) {
        ResponseBusinessJSON = responseBusinessJSON;
    }

    public BusinessNewsPart(View b) {
        PrevArticleChip4 = b.findViewById(R.id.PrevArticleChip4);
        NextArticleChip4 = b.findViewById(R.id.NextArticleChip4);
        ChipURLLink4 = b.findViewById(R.id.ChipURLLink4);
        NewsDescriptionText4 = b.findViewById(R.id.NewsDescriptionText4);
        ChipNewsTitle4 = b.findViewById(R.id.ChipNewsTitle4);
        ImageNews4 = b.findViewById(R.id.ImageNews4);
        SetOnClickListeners();
    }

    private void SetOnClickListeners() {
        PrevArticleChip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForBusinessNews != null) {
                    if (CurrentArticleNumber > 0) {
                        CurrentArticleNumber--;

                        ImageNews4.setImageResource(R.drawable.materialwall);
                        if (!MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber).equals("null") && MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews4); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews4.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews4.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle4.setText(MyTitlesArrayListForBusinessNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText4.setText(MyDescriptionsArrayListForBusinessNews.get(CurrentArticleNumber)); // Set Description
                    }
                }
            }
        });

        NextArticleChip4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (MyTitlesArrayListForBusinessNews != null) {
                    if (CurrentArticleNumber < MyTitlesArrayListForBusinessNews.size() - 1) {
                        CurrentArticleNumber++;
                        // Initially set Image Value to a nice resource
                        ImageNews4.setImageResource(R.drawable.materialwall);

                        if (MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber) != "null" && MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber) != null) {
                            try {
                                Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews4); // Set Image
                            } catch (Exception e) {
                                e.printStackTrace();
                                ImageNews4.setImageResource(R.drawable.materialwall);
                            }
                        } else {
                            ImageNews4.setImageResource(R.drawable.materialwall);
                        }
                        ChipNewsTitle4.setText(MyTitlesArrayListForBusinessNews.get(CurrentArticleNumber)); // Set title
                        NewsDescriptionText4.setText(MyDescriptionsArrayListForBusinessNews.get(CurrentArticleNumber)); // Set Description
                    }
                }

            }
        });
    }


    public void WhatToHappenWhenRequestIsProvided(String JsonRequest, Context MContext) {
        // Write the cached file here
        if(!ParseJSONWorldNews(JsonRequest, "status").get(0).equals("ok")){
            // Something bad happened
            // Toast.makeText(MContext, "There was a problem in the response for BusinessNews. Please try again later", Toast.LENGTH_LONG).show();
            Log.d(TAG, "WhatToHappenWhenRequestIsProvided: Response Error");
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(MContext,"JSON_BUSINESSNEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(MContext, "JSON_BUSINESSNEWS_CACHE.json");
        ResponseBusinessJSON = Response;
        MyTitlesArrayListForBusinessNews = ParseJSONWorldNews(Response, "news_title");
        ChipNewsTitle4.setText(MyTitlesArrayListForBusinessNews.get(0));

        MyDescriptionsArrayListForBusinessNews = ParseJSONWorldNews(JsonRequest, "news_descr");
        if (MyDescriptionsArrayListForBusinessNews.get(0) != null) {
            NewsDescriptionText4.setText(MyDescriptionsArrayListForBusinessNews.get(0));
        }

        MyURLArrayListForBusinessNews = ParseJSONWorldNews(JsonRequest, "news_url");

        // To load the first corresponding image
        MyIMGURLArrayListForBusinessNews = ParseJSONWorldNews(JsonRequest, "news_url_to_img");
        if(MyIMGURLArrayListForBusinessNews.get(0) == null || MyIMGURLArrayListForBusinessNews.get(0) == "null"){
            ImageNews4.setImageResource(R.drawable.materialwall);
        }else {
            Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(0)).fit().centerInside().into(ImageNews4); // Set Image
        }
    }
}
