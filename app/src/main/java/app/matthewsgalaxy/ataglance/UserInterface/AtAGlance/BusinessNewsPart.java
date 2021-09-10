package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

    public static ArrayList<String> MyTitlesArrayListForBusinessNews;
    public static ArrayList<String> MyDescriptionsArrayListForBusinessNews;
    public static ArrayList<String> MyURLArrayListForBusinessNews;
    public static ArrayList<String> MyIMGURLArrayListForBusinessNews;
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
                                if (AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews4); // Set Image
                                else
                                    getImageNews4().setImageResource(R.drawable.materialwall);
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
                                if (AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews4); // Set Image
                                else
                                    getImageNews4().setImageResource(R.drawable.materialwall);
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
        System.out.println("RESPONSE READ FROM FILE (BUSINESS NEWS): " + Response);
        if(Response == null || Response == ""){
            Response = "{\"status\":\"ok\",\"totalResults\":641,\"articles\":[{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Matt Mathers\",\"title\":\"UK economic recovery stalled drastically in July, ONS figures show - The Independent\",\"description\":\"Slump comes despite businesses reopening\",\"url\":\"https://www.independent.co.uk/news/business/uk-economy-covid-gdp-ons-b1917648.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/08/PA-61834127.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T16:30:42Z\",\"content\":\"Britains economic recovery from the Covid pandemic is beginning to taper off, with new figures showing GDP rose by just 0.1 per cent in July down from 1 per cent in the previous month.\\r\\nThe Office fo… [+3546 chars]\"},{\"source\":{\"id\":\"techcrunch\",\"name\":\"TechCrunch\"},\"author\":\"Ron Miller\",\"title\":\"DataRobot CEO Dan Wright coming to TC Sessions: SaaS to discuss role of data in machine learning - TechCrunch\",\"description\":\"Just about every company is sitting on vast amounts of data, which they can use to their advantage if they can just learn how to harness it. Data is actually the fuel for machine learning models, and with the proper tools, businesses can learn to process this…\",\"url\":\"http://techcrunch.com/2021/09/10/datarobot-ceo-dan-wright-coming-to-tc-sessions-saas-to-discuss-role-of-data-in-machine-learning/\",\"urlToImage\":\"https://techcrunch.com/wp-content/uploads/2021/09/datarobot_wp.png?w=711\",\"publishedAt\":\"2021-09-10T16:13:15Z\",\"content\":\"Just about every company is sitting on vast amounts of data, which they can use to their advantage if they can just learn how to harness it. Data is actually the fuel for machine learning models, and… [+2343 chars]\"},{\"source\":{\"id\":null,\"name\":\"Sky.com\"},\"author\":\"Alix Culbertson\",\"title\":\"HGV driving tests to be relaxed to help lorry driver shortages ahead of Christmas - Sky News\",\"description\":\"\",\"url\":\"https://news.sky.com/story/hgv-driving-tests-to-be-relaxed-to-help-lorry-driver-shortages-ahead-of-christmas-12404161\",\"urlToImage\":\"https://e3.365dm.com/21/07/1600x900/skynews-hgv-business-grant-shapps_5441499.jpg?20210708194840\",\"publishedAt\":\"2021-09-10T16:10:58Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Philippine Star\"},\"author\":\"Iris Gonzales\",\"title\":\"Index nears 7,000 as gov’t OKs new lockdown rules - Philstar.com\",\"description\":\"Share prices rebounded yesterday, with the main index closing in on the 7,000 level, as investors expected lighter quarantine restrictions by mid-September in NCR and adjacent provinces.\",\"url\":\"https://www.philstar.com/business/2021/09/11/2126281/index-nears-7000-govt-oks-new-lockdown-rules\",\"urlToImage\":\"https://media.philstar.com/photos/2021/09/10/stocks2021-08-1319-26-45_2021-09-10_19-45-15.jpg\",\"publishedAt\":\"2021-09-10T16:00:00Z\",\"content\":\"MANILA, Philippines — Share prices rebounded yesterday, with the main index closing in on the 7,000 level, as investors expected lighter quarantine restrictions by mid-September in NCR and adjacent p… [+1502 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Jessica Dickler\",\"title\":\"The lessons for investors from the trial of Theranos founder Elizabeth Holmes - CNBC\",\"description\":\"Theranos wasn’t the only potential bad actor out there. Here’s how you can protect yourself and your portfolio from corporate fraud.\",\"url\":\"https://www.cnbc.com/2021/09/10/the-lessons-for-investors-from-the-trial-of-theranos-founder-elizabeth-holmes.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/103835087-RTSKKUW.jpg?v=1529452110\",\"publishedAt\":\"2021-09-10T15:44:16Z\",\"content\":\"Sometimes an investment is too good to be true.\\r\\nAs Elizabeth Holmes, founder and former CEO of Theranos, goes on trial on allegations of defrauding investors and patients, her health-care start-up m… [+5151 chars]\"},{\"source\":{\"id\":\"financial-post\",\"name\":\"Financial Post\"},\"author\":\"Colin McClelland\",\"title\":\"$200 oil possible if climate change policies bring on 'energy starvation,' say energy insiders - Financial Post\",\"description\":\"'It's something that I think many of us, if not all of us, would not like to see happening in the market' — Oman’s energy minister\",\"url\":\"https://financialpost.com/commodities/energy/200-oil-possible-if-climate-change-policies-bring-on-energy-starvation-say-energy-insiders\",\"urlToImage\":\"https://smartcdn.prod.postmedia.digital/financialpost/wp-content/uploads/2021/09/oil0910.jpg\",\"publishedAt\":\"2021-09-10T15:43:39Z\",\"content\":\"'It's something that I think many of us, if not all of us, would not like to see happening in the market' Omans energy minister \\r\\nColin McClelland\\r\\nThe price of oil could shoot up to $100 or $200 a b… [+7700 chars]\"},{\"source\":{\"id\":null,\"name\":\"BBC News\"},\"author\":\"https://www.facebook.com/bbcnews\",\"title\":\"Food shortages could be permanent, warns industry body - BBC News\",\"description\":\"Shoppers are unlikely to have a wide range of products to choose from, due to supply chain issues.\",\"url\":\"https://www.bbc.com/news/business-58519997\",\"urlToImage\":\"https://ichef.bbci.co.uk/news/1024/branded_news/87E5/production/_120498743_food-shortage1.jpg\",\"publishedAt\":\"2021-09-10T15:43:34Z\",\"content\":\"By Esyllt CarrBusiness reporter, BBC News \\r\\nimage source, Getty Images\\r\\nLabour shortages in the food industry means consumers may not be able to find the products they like in supermarkets, an indust… [+3108 chars]\"},{\"source\":{\"id\":\"ars-technica\",\"name\":\"Ars Technica\"},\"author\":\"Jon Brodkin\",\"title\":\"SpaceX calls Amazon’s protest of Starlink plan an irrelevant “diatribe” - Ars Technica\",\"description\":\"SpaceX: \\\"Another week, another objection from Amazon against a competitor.\\\"\",\"url\":\"https://arstechnica.com/tech-policy/2021/09/spacex-calls-amazons-protest-of-starlink-plan-an-irrelevant-diatribe/\",\"urlToImage\":\"https://cdn.arstechnica.net/wp-content/uploads/2021/09/getty-amazon-warehouse-760x380.jpg\",\"publishedAt\":\"2021-09-10T15:42:23Z\",\"content\":\"Enlarge/ Amazon UK warehouse at Leeds Distribution Park on May 27, 2021, in Leeds, England. \\r\\n28 with 24 posters participating\\r\\nOn Thursday, SpaceX called Amazon's latest protest against Starlink pla… [+6232 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"July IIP sees slower YoY growth at 11.5% as low base effect wears off - Moneycontrol.com\",\"description\":\"Industrial production has almost caught up with pre-pandemic levels. Compared to July, 2019, production was just 0,3 percent lower in the latest month.\",\"url\":\"https://www.moneycontrol.com/news/business/economy/industrial-production-grows-11-5-in-july-7453961.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2017/03/metal-iron-steel-steel-furnace-mills-worker-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:42:21Z\",\"content\":\"As the low base effect slowly wears off, industrial production in India expanded by 11.5 percent year-on-year (YoY) in July, down from 13.6 percent in June.\\r\\nMeasured by the Index of Industrial Produ… [+3320 chars]\"},{\"source\":{\"id\":null,\"name\":\"Moneycontrol\"},\"author\":null,\"title\":\"Amagi raises $100 million from Accel, Avataar Ventures and others - Moneycontrol\",\"description\":\"Amagi enables content owners to launch, distribute and monetise live linear channels on free-ad-supported television and video services platforms through a suite of solutions.\",\"url\":\"https://www.moneycontrol.com/news/business/amagi-raises-100-million-from-accel-avataar-ventures-and-others-7454311.html\",\"urlToImage\":\"https://images.moneycontrol.com/static-mcnews/2021/03/fundraising_1-770x433.jpg\",\"publishedAt\":\"2021-09-10T15:27:28Z\",\"content\":\"Amagi, a cloud SaaS technology provider for broadcast and streaming television, has raised over $100 million from investors such as Accel, Avataar Ventures, Norwest Venture Partners and existing inve… [+3408 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Cecilia D'Anastasio\",\"title\":\"Twitch Sues Users Over Alleged ‘Hate Raids’ Against Streamers - WIRED\",\"description\":\"The lawsuit accuses two anonymous users of “targeting black and LGBTQIA+ streamers with racist, homophobic, sexist and other harassing content” in violation of its terms of service.\",\"url\":\"https://www.wired.com/story/twitch-sues-users-over-alleged-hate-raids/\",\"urlToImage\":\"https://media.wired.com/photos/613b6d0e6ab67fe10ebfff58/191:100/w_1280,c_limit/Gaming-Twitch-lawsuire-1339094198.jpg\",\"publishedAt\":\"2021-09-10T15:19:00Z\",\"content\":\"Since early August, Twitch has been wrestling with an epidemic of harassment against marginalized streamers known as hate raids. These attacks spam streamers chats with hateful and bigoted language, … [+3837 chars]\"},{\"source\":{\"id\":\"reuters\",\"name\":\"Reuters\"},\"author\":null,\"title\":\"Commodity group Louis Dreyfus completes stake sale to ADQ - Reuters\",\"description\":\"Louis Dreyfus Company (LDC) said on Friday it had completed the sale of a 45% indirect stake to Abu Dhabi holding firm ADQ, marking the arrival of the first non-family shareholder in the agricultural commodity group's 170-year history.\",\"url\":\"https://www.reuters.com/world/middle-east/commodity-group-louis-dreyfus-completes-stake-sale-adq-2021-09-10/\",\"urlToImage\":\"https://www.reuters.com/pf/resources/images/reuters/reuters-default.png?d=52\",\"publishedAt\":\"2021-09-10T15:06:00Z\",\"content\":\"PARIS, Sept 10 (Reuters) - Louis Dreyfus Company (LDC) said on Friday it had completed the sale of a 45% indirect stake to Abu Dhabi holding firm ADQ, marking the arrival of the first non-family shar… [+2002 chars]\"},{\"source\":{\"id\":null,\"name\":\"Motley Fool\"},\"author\":\"Zhiyuan Sun\",\"title\":\"Why Bitcoin, Ethereum, and Dogecoin All Fell This Week - Motley Fool\",\"description\":\"The latest round of fear, uncertainty, and doubt in the crypto realm is beginning to materialize.\",\"url\":\"https://www.fool.com/investing/2021/09/10/why-bitcoin-ethereum-and-dogecoin-all-fell-this-we/\",\"urlToImage\":\"https://g.foolcdn.com/editorial/images/642629/gettyimages-1294303261.jpg\",\"publishedAt\":\"2021-09-10T14:59:52Z\",\"content\":\"What happened\\r\\nBitcoin (CRYPTO:BTC), Ethereum (CRYPTO:ETH), and Dogecoin (CRYPTO:DOGE) are down 9.38%, 14.56%, and 17.44% respectively in the past seven days as of 9:00 a.m. EDT. They are now trading… [+2275 chars]\"},{\"source\":{\"id\":\"fox-news\",\"name\":\"Fox News\"},\"author\":\"Kayla Rivas\",\"title\":\"FDA’s delayed decision on Juul e-cigarettes ‘reckless,’ pediatrics group says - Fox News\",\"description\":\"A delayed decision Thursday from the Food and Drug Administration (FDA) on whether to allow vaping brand Juul to stay on the market was met with strong criticism from the American Academy of Pediatrics.\",\"url\":\"https://www.foxnews.com/health/fda-delayed-decision-juul-reckless-pediatrics-group\",\"urlToImage\":\"https://static.foxnews.com/foxnews.com/content/uploads/2019/12/af92e85b-AP19346504360552.jpg\",\"publishedAt\":\"2021-09-10T14:55:49Z\",\"content\":\"A delayed decision Thursday from the Food and Drug Administration (FDA) on whether to allow vaping brand Juul to stay on the market was met with strong criticism from the American Academy of Pediatri… [+1874 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Michael Wayland\",\"title\":\"GM doubles chip shortage impact to 200,000 vehicles in second half of year, maintains guidance - CNBC\",\"description\":\"Despite the increased impact from the global chip shortage, GM CFO Paul Jacobson said the company is maintaining its guidance for 2021.\",\"url\":\"https://www.cnbc.com/2021/09/10/gm-increases-impact-of-chip-shortage-to-200000-vehicles-in-2nd-half.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106880615-1620662350453-gettyimages-1202279819-GM_LANSING.jpeg?v=1620662418\",\"publishedAt\":\"2021-09-10T14:46:38Z\",\"content\":\"General Motors' vehicle sales and production will be hit harder by the global chip shortage during the second half of the year than it previously expected, its finance chief said Friday.\\r\\nThe shortag… [+1868 chars]\"},{\"source\":{\"id\":\"independent\",\"name\":\"Independent\"},\"author\":\"Rob Merrick\",\"title\":\"Supermarket food shortages will be over by Christmas, Downing Street says - The Independent\",\"description\":\"Shoppers will enjoy a ‘normal Christmas’, Boris Johnson’s spokesman predicts – despite industry warning of ‘permanent’ gaps on shelves\",\"url\":\"https://www.independent.co.uk/news/uk/politics/food-shortages-brexit-covid-boris-johnson-b1917896.html\",\"urlToImage\":\"https://static.independent.co.uk/2021/09/10/15/ECONOMY%20Shortages%20%2011185555.jpg?width=1200&auto=webp&quality=75\",\"publishedAt\":\"2021-09-10T14:44:19Z\",\"content\":\"Supermarket food shortages will be over by Christmas, No 10 says rejecting industry warnings that shoppers must get used to permanent gaps on shelves.\\r\\nBoris Johnsons spokesman set up the hostage to … [+2474 chars]\"},{\"source\":{\"id\":null,\"name\":\"Jalopnik\"},\"author\":\"Adam Ismail\",\"title\":\"Now The Apple Watch Guy Is Overseeing The Apple Car - Jalopnik\",\"description\":\"Kevin Lynch will replace previous Apple Car lead Doug Field, who recently left for Ford.\",\"url\":\"https://jalopnik.com/now-the-apple-watch-guy-is-overseeing-the-apple-car-1847650113\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/14a8f6afee8d187c80db2a4e11f14b5b.jpg\",\"publishedAt\":\"2021-09-10T14:41:00Z\",\"content\":\"What do smartwatches and cars have in common? Aside from the fact Apple is trying to make both, Im not sure. The tech giants wearable software lead is taking the reins of its turbulent car project. I… [+7780 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Andrew J. Hawkins\",\"title\":\"Tesla Model S Plaid sets production EV record at Germany’s Nürburgring race course - The Verge\",\"description\":\"A Tesla Model S Plaid set a lap time of 7:30.909 minutes at Germany’s famous Nürburgring race track, setting a new record for production electric vehicles.\",\"url\":\"https://www.theverge.com/2021/9/10/22666576/tesla-model-s-plaid-nurburgring-ev-production-record\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/zMMV8rQAPZSVz10geLSMxxKPjNQ=/0x93:2416x1358/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839667/Screen_Shot_2021_09_10_at_9.53.20_AM.png\",\"publishedAt\":\"2021-09-10T14:40:34Z\",\"content\":\"Lap time of 7:30.909\\r\\nScreenshot: Touristen Niko\\r\\nTesla set a new lap record for a production electric vehicle at Germanys famed Nürburgring race course. A brand-new Model S Plaid completed the 12.9-… [+2233 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Ryan Browne\",\"title\":\"British fintechs are jumping into the booming buy now, pay later market - CNBC\",\"description\":\"Monzo and Revolut, two of Britain's best-known financial technology firms, are planning to enter the booming \\\"buy now, pay later\\\" industry.\",\"url\":\"https://www.cnbc.com/2021/09/10/monzo-and-revolut-to-enter-buy-now-pay-later-market.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/105966681-1560426803107monzocard1.jpg?v=1560427006\",\"publishedAt\":\"2021-09-10T14:33:39Z\",\"content\":\"LONDON Monzo and Revolut, two of Britain's best-known financial technology firms, are planning to enter the booming \\\"buy now, pay later\\\" industry.\\r\\nBuy now, pay later, or BNPL, plans are an increasin… [+2934 chars]\"},{\"source\":{\"id\":\"engadget\",\"name\":\"Engadget\"},\"author\":null,\"title\":\"Automakers dial up the wattage on the future of EVs at Munich's auto show - Engadget\",\"description\":\"Find the latest technology news and expert tech product reviews. Learn about the latest gadgets and consumer tech products for entertainment, gaming, lifestyle and more.\",\"url\":\"https://www.engadget.com/\",\"urlToImage\":null,\"publishedAt\":\"2021-09-10T14:31:09Z\",\"content\":\"Just enter your email and we'll take care of the rest:\\r\\nNow available on your smart speaker and wherever you get your podcasts:\"}]}";
            DifferentFunctions.writeToFile(MContext,"JSON_BUSINESSNEWS_CACHE.json", Response);
        }
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
            if (AtAGlanceFragment.isOnline(MContext))
                Picasso.get().load(MyIMGURLArrayListForBusinessNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews4); // Set Image
            else
                getImageNews4().setImageResource(R.drawable.materialwall);
        }
    }
}
