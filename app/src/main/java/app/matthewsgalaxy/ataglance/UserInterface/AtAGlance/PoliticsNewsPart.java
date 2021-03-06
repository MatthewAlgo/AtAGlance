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
    public Chip PrevArticleChip5, NextArticleChip5,ChipURLLink5,ChipURLLink5ReadLater;
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
        ChipURLLink5ReadLater = b.findViewById(R.id.ChipURLLink5ReadLater);

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
        System.out.println("RESPONSE READ FROM FILE (POLITICS NEWS): " + Response);
        if(Response == null || Response == ""){
            Response = "{\"status\":\"ok\",\"totalResults\":449,\"articles\":[{\"source\":{\"id\":\"the-wall-street-journal\",\"name\":\"The Wall Street Journal\"},\"author\":\"Tim Higgins\",\"title\":\"Apple Trial Ends in Mixed Verdict After Fortnite-Maker???s Monopoly Allegations - The Wall Street Journal\",\"description\":\"Case pitted Steve Jobs???s vision of a walled garden of Apple hardware against Epic co-founder Tim Sweeney???s desire for an open ecosystem\",\"url\":\"https://www.wsj.com/articles/apple-trial-ends-in-mixed-verdict-after-fortnite-makers-monopoly-allegations-11631289792\",\"urlToImage\":\"https://images.wsj.net/im-386969/social\",\"publishedAt\":\"2021-09-10T16:31:00Z\",\"content\":null},{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Matt Mathers\",\"title\":\"UK economic recovery stalled drastically in July, ONS figures show - The Independent\",\"description\":\"Slump comes despite businesses reopening\",\"url\":\"https://www.independent.co.uk/news/business/uk-economy-covid-gdp-ons-b1917648.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/08/PA-61834127.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T16:30:42Z\",\"content\":\"Britains economic recovery from the Covid pandemic is beginning to taper off, with new figures showing GDP rose by just 0.1 per cent in July down from 1 per cent in the previous month.\\r\\nThe Office fo??? [+3546 chars]\"},{\"source\":{\"id\":null,\"name\":\"New York Times\"},\"author\":\"Jack Nicas\",\"title\":\"In Epic vs. Apple Court Fight, a Win for App Developers - The New York Times\",\"description\":\"The decision could have major implications for thousands of businesses that pay Apple billions of dollars each year.\",\"url\":\"https://www.nytimes.com/2021/09/10/technology/epic-apple-app-developers.html\",\"urlToImage\":\"https://static01.nyt.com/images/2021/09/10/business/10epicapple-promo/10epicapple-promo-facebookJumbo.jpg\",\"publishedAt\":\"2021-09-10T16:26:07Z\",\"content\":\"The decision could have a major ripple effect across the digital economy. If Epic prevails after expected appeals, companies would have a new way to avoid the App Store commission, which runs as high??? [+1112 chars]\"},{\"source\":{\"id\":null,\"name\":\"Sky.com\"},\"author\":\"Alix Culbertson\",\"title\":\"HGV driving tests to be relaxed to help lorry driver shortages ahead of Christmas - Sky News\",\"description\":\"\",\"url\":\"https://news.sky.com/story/hgv-driving-tests-to-be-relaxed-to-help-lorry-driver-shortages-ahead-of-christmas-12404161\",\"urlToImage\":\"https://e3.365dm.com/21/07/1600x900/skynews-hgv-business-grant-shapps_5441499.jpg?20210708194840\",\"publishedAt\":\"2021-09-10T16:10:58Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Herald Sun\"},\"author\":\"Chris Cavanagh and Lachie Young\",\"title\":\"Player ratings: Which Geelong flop scored zero? - Herald Sun\",\"description\":\"<p>Melbourne captain Max Gawn says his side &ldquo;haven&rsquo;t done anything&rdquo; until they have broken the Demons&rsquo; 57-year premiership drought. </p>\",\"url\":\"https://www.heraldsun.com.au/sport/afl/afl-preliminary-final-melbourne-v-geelong-who-starred-who-flopped-and-every-player-rated/news-story/3cc4bfb7c089a9f4faed61f3b15074c9\",\"urlToImage\":\"https://content.api.news/v3/images/bin/9e5894a000975feb985c29f693a6d4eb\",\"publishedAt\":\"2021-09-10T16:09:15Z\",\"content\":\"It was an annihilation. Geelongs most experienced players failed to show up. Their most prolific didnt deliver. One in particular was abysmal. PLAYER RATINGS.Melbourne captain Max Gawn says his side ??? [+1506 chars]\"},{\"source\":{\"id\":null,\"name\":\"3dnatives\"},\"author\":null,\"title\":\"The First Ever 3D Printed Stellar Nurseries to be Used to Study Star Creation - 3Dnatives\",\"description\":\"The latest news on 3D Printing and Additive Manufacturing technologies. Find the best stories, prices, interviews, market reports and tests on 3Dnatives\",\"url\":\"https://www.3dnatives.com/en/\",\"urlToImage\":\"https://www.3dnatives.com/en/wp-content/uploads/sites/2/logo_3Dnatives2017_carre.png\",\"publishedAt\":\"2021-09-10T16:05:10Z\",\"content\":\"3Dnatives is the largest international online media platform on 3D printing and its applications. With its in-depth analysis of the market, 3Dnatives gets over 1 million unique visitors per month and??? [+276 chars]\"},{\"source\":{\"id\":\"the-washington-post\",\"name\":\"The Washington Post\"},\"author\":\"Aaron Gregg\",\"title\":\"DoorDash, Grubhub, UberEats sue New York City over fee caps - The Washington Post\",\"description\":\"The coalition of popular food delivery apps says the law infringes on their private contracts with food sellers.\",\"url\":\"https://www.washingtonpost.com/business/2021/09/10/doordash-grubhub-new-york-city-lawsuit/\",\"urlToImage\":\"https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/4HOQGKAGW4I6ZM6EYRRLD3OPZA.jpg&w=1440\",\"publishedAt\":\"2021-09-10T15:48:00Z\",\"content\":\"In a lawsuit filed late Thursday in the Southern District of New York, the parent companies of six widely used apps DoorDash, Caviar, Grubhub, Seamless, Postmates and UberEats accused the city of imp??? [+139 chars]\"},{\"source\":{\"id\":null,\"name\":\"NDTV News\"},\"author\":null,\"title\":\"Brother Of Former Afghan Vice-President Executed By Taliban, Says Family - NDTV\",\"description\":\"The Taliban have executed the brother of Amrullah Saleh, the former Afghan vice president who became one of the leaders of anti-Taliban opposition forces in the Panjshir valley, his nephew said on Friday.\",\"url\":\"https://www.ndtv.com/world-news/former-afghan-vice-president-amrullah-salehs-brother-rohullah-azizi-executed-by-taliban-says-family-2536844\",\"urlToImage\":\"https://c.ndtvimg.com/2021-08/3l70u5t4_amrullah-saleh-reuters_625x300_17_August_21.jpg\",\"publishedAt\":\"2021-09-10T15:46:07Z\",\"content\":\"Amrullah Saleh became one of the leaders of anti-Taliban opposition forces in the Panjshir valley\\r\\nThe Taliban have executed the brother of Amrullah Saleh, the former Afghan vice president who became??? [+1156 chars]\"},{\"source\":{\"id\":\"news24\",\"name\":\"News24\"},\"author\":\"Sesona Ngqakamba\",\"title\":\"JUST IN | Crash of several SA government websites 'not due to cyber-attacks' - News24\",\"description\":\"Several government websites, with the gov.za domain, were down on Friday due to fibre cable damage.\",\"url\":\"https://www.news24.com/news24/southafrica/news/just-in-crash-of-several-sa-government-websites-not-due-to-cyber-attacks-20210910\",\"urlToImage\":\"https://cdn.24.co.za/files/Cms/General/d/11566/df55825b175c4e05969fbf3ea4c4ff0c.jpg\",\"publishedAt\":\"2021-09-10T15:45:53Z\",\"content\":\"<ul><li>According to SITA, the issue was caused by fibre cable damage.</li><li>The websites were unreachable for several hours on Friday.</li><li>SITA said it expected services to be restored soon.??????? [+1959 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn???t the only potential bad actor out there. Here???s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m??? [+5151 chars]\"},{\"source\":{\"id\":\"the-times-of-india\",\"name\":\"The Times of India\"},\"author\":\"Chidanand Rajghatta\",\"title\":\"US dodges a 9/11 poke in the eye by Taliban - Times of India\",\"description\":\"South Asia News: The US on Friday dodged the ignominy of having Taliban install its government on the 20th anniversary of 9/11 after Russia reportedly rained on the ex\",\"url\":\"https://timesofindia.indiatimes.com/world/south-asia/us-dodges-a-9/11-poke-in-the-eye-by-taliban/articleshow/86098077.cms\",\"urlToImage\":\"https://static.toiimg.com/thumb/msid-86098076,width-1070,height-580,imgsize-33954,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg\",\"publishedAt\":\"2021-09-10T15:44:00Z\",\"content\":\"Taliban's new government will upset US, China, India and IranA week ago, anxious Afghans and credulous Biden administration officials were trying to take comfort in reports that Mullah Abdul Ghani Ba??? [+151 chars]\"},{\"source\":{\"id\":null,\"name\":\"NPR\"},\"author\":\"\",\"title\":\"Biden Brushes Off GOP Criticism Of Vaccination Mandate for Teachers - NPR\",\"description\":\"President Biden said he was saddened that some GOP governors are threatening to sue over the new mandates. To them, he said, \\\"Have at it.\\\"\",\"url\":\"https://www.npr.org/2021/09/10/1035869847/biden-brushes-off-gop-threats-to-sue-over-vaccine-mandates\",\"urlToImage\":\"https://media.npr.org/assets/img/2021/09/10/ap21253539854160_wide-daa8827afff8aca7389512b65f0481a04e13b68b.jpg?s=1400\",\"publishedAt\":\"2021-09-10T15:43:50Z\",\"content\":\"President Biden tours Brookland Middle School in Washington, D.C., on Friday. Biden has encouraged every school district to promote vaccines, including with on-site clinics, to protect students as th??? [+2918 chars]\"},{\"source\":{\"id\":\"techradar\",\"name\":\"TechRadar\"},\"author\":\"John Loeffler\",\"title\":\"Apple loses Epic fight: app developers can now avoid App Store payments - TechRadar\",\"description\":\"This is a huge hit to Apple's bottom line\",\"url\":\"https://www.techradar.com/news/developers-can-now-evade-apples-in-app-payment-scheme-to-directly-sell-to-users\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/fWxXJzo6UpWj9RVFTFS5rJ-1200-80.jpg\",\"publishedAt\":\"2021-09-10T15:42:49Z\",\"content\":\"Apple was just hit with a permanent injunction by the judge in its legal battle with Epic Games, preventing the company from forcing developers to use the App Store's payment system for in-app purcha??? [+2270 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"July IIP sees slower YoY growth at 11.5% as low base effect wears off - Moneycontrol.com\",\"description\":\"Industrial production has almost caught up with pre-pandemic levels. Compared to July, 2019, production was just 0,3 percent lower in the latest month.\",\"url\":\"https://www.moneycontrol.com/news/business/economy/industrial-production-grows-11-5-in-july-7453961.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2017/03/metal-iron-steel-steel-furnace-mills-worker-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:42:21Z\",\"content\":\"As the low base effect slowly wears off, industrial production in India expanded by 11.5 percent year-on-year (YoY) in July, down from 13.6 percent in June.\\r\\nMeasured by the Index of Industrial Produ??? [+3320 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Straits Times\"},\"author\":\"ISABELLE LIEW\",\"title\":\"S'pore reports 568 new local Covid-19 cases and 1 death - The Straits Times\",\"description\":\"Of the locally transmitted cases, 127 were seniors above the age of 60.. Read more at straitstimes.com.\",\"url\":\"https://www.straitstimes.com/singapore/health/568-new-locally-transmitted-covid-19-cases-in-spore-partially-vaccinated-80-year\",\"urlToImage\":\"https://www.straitstimes.com/s3/files/styles/x_large/public/articles/2021/09/10/dw-moh-update-210910.jpg?itok=LIILAcED\",\"publishedAt\":\"2021-09-10T15:36:47Z\",\"content\":\"SINGAPORE - Singapore reported??568 locally transmitted Covid-19 cases and??five imported ones on Friday (Sept 10), the Ministry of Health (MOH) said in its daily update.\\r\\nThis makes a total of 573 new??? [+2620 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Kif Leswing\",\"title\":\"Apple can no longer force developers to use in-app purchasing, judge rules in Epic Games case - CNBC\",\"description\":\"The trial took place in Oakland, California, in May, and included both company CEOs testifying in open court.\",\"url\":\"https://www.cnbc.com/2021/09/10/epic-games-v-apple-judge-reaches-decision-.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106940080-1631290244185-106940080-1631288561148-gettyimages-1233028006-APPLE-EPIC-TRIAL.jpg?v=1631290254\",\"publishedAt\":\"2021-09-10T15:36:06Z\",\"content\":\"Apple's lucrative App Store business received a major blow Friday thanks to a federal judge's decision in the company's legal battle with Epic Games.\\r\\nJudge Yvonne Gonzalez Rogers on Friday handed do??? [+5386 chars]\"},{\"source\":{\"id\":\"news-com-au\",\"name\":\"News.com.au\"},\"author\":null,\"title\":\"Jett Kenny reveals sister???s final moments - NEWS.com.au\",\"description\":\"<p>Jett Kenny has shared the heartbreaking final moments of his sister, Jaimi, who died a year ago aged just 33.</p>\",\"url\":\"https://www.news.com.au/entertainment/celebrity-life/jett-kenny-reveals-sisters-final-moments/news-story/b54489bfbc078ba15ee60ac9a06449dd\",\"urlToImage\":\"https://content.api.news/v3/images/bin/7053a0fc356a850f6931a7b0d92454c0\",\"publishedAt\":\"2021-09-10T15:35:59Z\",\"content\":\"Jett Kenny has opened up about losing his sister, Jaimi, telling how she fought for hours after her life support machine was switched off.Jett Kenny has shared the heartbreaking final moments of his ??? [+4236 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNA\"},\"author\":null,\"title\":\"US judge in 'Fortnite' case strikes down Apple's in-app payment restrictions - CNA\",\"description\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect payments in apps. The ruling is similar to a move that Apple ma???\",\"url\":\"https://www.channelnewsasia.com/business/us-judge-fortnite-case-strikes-down-apples-app-payment-restrictions-2170116\",\"urlToImage\":\"https://onecms-res.cloudinary.com/image/upload/s--0xar_RRy--/fl_relative%2Cg_south_east%2Cl_one-cms:core:watermark:reuters%2Cw_0.1/f_auto%2Cq_auto/c_fill%2Cg_auto%2Ch_676%2Cw_1200/v1/one-cms/core/2021-09-10t154906z_3_lynxmpeh890pr_rtroptp_3_apple-epic-games.jpg?itok=aiOLk8rM\",\"publishedAt\":\"2021-09-10T15:34:00Z\",\"content\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect pay??? [+3342 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Erica Orden, CNN\",\"title\":\"Giuliani associate Igor Fruman pleads guilty to solicitation of a contribution by a foreign national - CNN\",\"description\":\"Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\",\"url\":\"https://www.cnn.com/2021/09/10/politics/igor-fruman-plea/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/191023132912-01-igor-fruman-1023-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:31:00Z\",\"content\":\"New York (CNN)Igor Fruman, an associate of Rudy Giuliani, pleaded guilty Friday in New York federal court to a charge stemming from a case alleging he funneled foreign money to US campaign coffers.\\r\\n??? [+2400 chars]\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Deepika Padukone hesitates before explaining extent of her depression to Amitabh Bachchan on Kaun Banega Crorepati - Hindustan Times\",\"description\":\"Kaun Banega Crorepati 13: Deepika Padukone hesitated before confessing to Amitabh Bachchan that there was a time when she didn't feel like living.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/deepika-padukone-hesitates-before-explaining-extent-of-her-depression-to-amitabh-bachchan-on-kaun-banega-crorepati-101631274385385.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/deepika_padukone_amitabh_bachchan_kbc_1631275485941_1631275494835.jpeg\",\"publishedAt\":\"2021-09-10T15:19:58Z\",\"content\":\"Actor Deepika Padukone spoke about her depression in an appearance on Kaun Banega Crorepati 13, as host Amitabh Bachchan listened. Deepika and Farah Khan will appear as celebrity guests on Friday's e??? [+1609 chars]\"}]}";
            DifferentFunctions.writeToFile(Mcontext,"JSON_POLITICSNEWS_CACHE.json", Response);
        }
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
