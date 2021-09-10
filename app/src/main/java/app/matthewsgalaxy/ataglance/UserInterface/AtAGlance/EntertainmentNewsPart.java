package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions;
import app.matthewsgalaxy.ataglance.R;

public class EntertainmentNewsPart {

    public Chip PrevArticleChip6, NextArticleChip6,ChipURLLink6;
    public TextView NewsDescriptionText6,ChipNewsTitle6;
    public ImageView ImageNews6;
    public int CurrentArticleNumber=0;

    public static ArrayList<String> MyTitlesArrayListForEntertainmentNews;
    public static ArrayList<String> MyDescriptionsArrayListForEntertainamentNews;
    public static ArrayList<String> MyURLArrayListForEntertainmentNews;
    public static ArrayList<String> MyIMGURLArrayListForEntertainmentNews;
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

    public EntertainmentNewsPart(View b) {
        PrevArticleChip6 = b.findViewById(R.id.PrevArticleChip6);
        NextArticleChip6 = b.findViewById(R.id.NextArticleChip6);
        ChipURLLink6 = b.findViewById(R.id.ChipURLLink6);
        NewsDescriptionText6 = b.findViewById(R.id.NewsDescriptionText6);
        ChipNewsTitle6 = b.findViewById(R.id.ChipNewsTitle6);
        ImageNews6 = b.findViewById(R.id.ImageNews6);
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
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews6); // Set Image
                                else
                                    ImageNews6.setImageResource(R.drawable.materialwall);
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
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews6); // Set Image
                                else
                                    ImageNews6.setImageResource(R.drawable.materialwall);
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
        if(!ParseJSONWorldNews(JsonRequest, "status").get(0).equals("ok")){
            // Something bad happened
            // Toast.makeText(Mcontext, "There was a problem in the response for TechNews. Please try again later", Toast.LENGTH_LONG).show();
            Log.d(TAG, "WhatToHappenWhenRequestIsProvided: Response Error");
        }else {
            // SAVE THE JSON REQUEST LOCALLY
            // THE REQUEST WENT WELL
            DifferentFunctions.writeToFile(Mcontext,"JSON_ENTERTAINMENTNEWS_CACHE.json", JsonRequest);
        }
        String Response = readFromFile(Mcontext, "JSON_ENTERTAINMENTNEWS_CACHE.json");
        System.out.println("RESPONSE READ FROM FILE (ENTERTAINMENT NEWS): " + Response);
        if(Response == null || Response == ""){
            Response = "{\"status\":\"ok\",\"totalResults\":673,\"articles\":[{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Haroon Siddique, Rowena Mason\",\"title\":\"BLM gives cautious welcome to Queen’s reported backing - The Guardian\",\"description\":\"Anti-racism movement says ‘actions speak louder than words’ after comments attributing royal assent\",\"url\":\"https://amp.theguardian.com/world/2021/sep/10/blm-gives-cautious-welcome-to-queens-reported-backing\",\"urlToImage\":\"https://i.guim.co.uk/img/media/52e28c423b3960da099c907b62cd3b31d992f5b2/0_91_800_480/master/800.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&enable=upscale&s=214e28b2b1f02b34a5b67232ee9fa20d\",\"publishedAt\":\"2021-09-10T16:03:00Z\",\"content\":\"Black Lives Matter UK has expressed surprise after the Queen and the royal family were said to support its cause, but the anti-racism movement stressed that actions speak louder than words.\\r\\nSir Ken … [+3169 chars]\"},{\"source\":{\"id\":null,\"name\":\"Gizmodo.com\"},\"author\":\"James Whitbrook, Rob Bricken, Cheryl Eddy, Germain Lussier, Charles Pulliam-Moore\",\"title\":\"2021 Fall TV Preview: Sci-Fi, Fantasy, and Horror Shows to Get Excited For - Gizmodo\",\"description\":\"From sci-fi bounty hunting to epic fantasy, this autumn's going to be a great time to stay inside for new and returning television.\",\"url\":\"https://gizmodo.com/2021-fall-tv-preview-sci-fi-fantasy-and-horror-shows-1847613798\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/04f981041b9a21c728c25ffca20c5394.png\",\"publishedAt\":\"2021-09-10T16:00:00Z\",\"content\":\"Summer has passed us by, and while it was far from the kind of summer most people expected for 2021for pop culture or otherwisenow that the nights are drawing in theres a whole plethora of genre tele… [+23852 chars]\"},{\"source\":{\"id\":\"polygon\",\"name\":\"Polygon\"},\"author\":\"Roxana Hadadi\",\"title\":\"Kate review: A dumb Netflix action movie elevated by Mary Elizabeth Winstead - Polygon\",\"description\":\"The fight scenes in Netflix’s Kate, handled by Birds of Prey, John Wick, and Black Panther stunt coordinator Jonathan Eusebio and executed by Mary Elizabeth Winstead, almost justify the movie. But the clumsy writing and extremely predictable setup and plot ho…\",\"url\":\"https://www.polygon.com/22666743/kate-review-netflix-mary-elizabeth-winstead\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/4VAEjfAKth9BAecqYsJlml00URM=/576x0:5569x2614/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22840011/KATE_20191111_08777_RC.jpg\",\"publishedAt\":\"2021-09-10T15:56:50Z\",\"content\":\"Whoever makes the next Alien sequel or spinoff should consider casting Mary Elizabeth Winstead as the successor of Sigourney Weavers character, Ellen Ripley. Since her breakthrough in Scott Pilgrim v… [+7361 chars]\"},{\"source\":{\"id\":null,\"name\":\"BuzzFeed News\"},\"author\":\"Ikran Dahir\",\"title\":\"Meet The TikToker That Guessed Kylie Jenner's Pregnancy - BuzzFeed News\",\"description\":\"“So when are all ya’ll manicure detectives starting your new gigs with the FBI?”\",\"url\":\"https://www.buzzfeednews.com/article/ikrd/tiktoker-guessed-kylie-jenners-pregnancy\",\"urlToImage\":\"https://img.buzzfeed.com/buzzfeed-static/static/2021-09/10/16/campaign_images/2d2cae2dbbe1/meet-the-tiktoker-who-correctly-guessed-kylie-jen-2-495-1631290697-23_dblbig.jpg?resize=1200:*\",\"publishedAt\":\"2021-09-10T15:52:00Z\",\"content\":\"In July, a TikToker named Tiffany ONeill posted a video predicting that Kylie Jenner was pregnant.\\r\\nThe next month, she posted another video, this one more in-depth, in which she argued fans could de… [+2214 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Guardian\"},\"author\":\"Guardian staff reporter\",\"title\":\"The Barber of Seville review – creaky WNO production heralds welcome return - The Guardian\",\"description\":\"The 35-year-old production has lost much of its stylish Italianate precision but Heather Lowe’s Rosina impressed, and other soloists kept the comedy lively\",\"url\":\"https://amp.theguardian.com/music/2021/sep/10/the-barber-of-seville-review-wno-millenium-centre-cardiff\",\"urlToImage\":\"https://i.guim.co.uk/img/media/0311e0093f9a8252653865968bb6ba94c5e11e64/165_212_4730_2839/master/4730.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctcmV2aWV3LTMucG5n&enable=upscale&s=bfb6bfbc33e329c626cb3f8a91e38090\",\"publishedAt\":\"2021-09-10T15:45:00Z\",\"content\":\"Welsh National Opera have been so noticeably absent from their main stages for a long 18 months Julys small-scale Alices Adventures in Wonderland was a National Trust country garden affair their retu… [+1989 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Marianne Garvey, CNN\",\"title\":\"Howard Stern to anti-vaxxers: 'You had the cure and you wouldn't take it' - CNN\",\"description\":\"Howard Stern is over the Covid-19 pandemic and is taking on anti-vaxxers who refuse to get the shot.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/howard-stern-vaccine-comments/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/201208123245-01-howard-stern-file-2015-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:39:00Z\",\"content\":null},{\"source\":{\"id\":\"news-com-au\",\"name\":\"News.com.au\"},\"author\":null,\"title\":\"Jett Kenny reveals sister’s final moments - NEWS.com.au\",\"description\":\"<p>Jett Kenny has shared the heartbreaking final moments of his sister, Jaimi, who died a year ago aged just 33.</p>\",\"url\":\"https://www.news.com.au/entertainment/celebrity-life/jett-kenny-reveals-sisters-final-moments/news-story/b54489bfbc078ba15ee60ac9a06449dd\",\"urlToImage\":\"https://content.api.news/v3/images/bin/7053a0fc356a850f6931a7b0d92454c0\",\"publishedAt\":\"2021-09-10T15:35:59Z\",\"content\":\"Jett Kenny has opened up about losing his sister, Jaimi, telling how she fought for hours after her life support machine was switched off.Jett Kenny has shared the heartbreaking final moments of his … [+4236 chars]\"},{\"source\":{\"id\":null,\"name\":\"Deadline\"},\"author\":\"Nancy Tartaglione\",\"title\":\"‘Shang-Chi’ China Release Unlikely In Wake Of Unearthed Comments By Star Simu Liu; ‘The Eternals’ Hopes In Question - Deadline\",\"description\":\"The prospects for Disney/Marvel’s Shang-Chi And The Legend Of The Ten Rings getting a China release date are dwindling. This in the wake of comments originally made by star Simu Liu in 2017 which resurfaced this week and have made waves on Chinese social medi…\",\"url\":\"https://deadline.com/2021/09/shang-chi-china-release-simu-liu-marvel-1234830474/\",\"urlToImage\":\"https://deadline.com/wp-content/uploads/2021/09/shang-chi-e1630503940200.jpg?w=1024\",\"publishedAt\":\"2021-09-10T15:30:00Z\",\"content\":\"The prospects for Disney/Marvel’s Shang-Chi And The Legend Of The Ten Rings getting a China release date are dwindling. This in the wake of comments originally made by star Simu Liu in 2017 which res… [+1564 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNET\"},\"author\":\"Joan E. Solsman\",\"title\":\"HBO Max is streaming Malignant (but not Shang-Chi nor Candyman, sorry) - CNET\",\"description\":\"HBO Max is streaming  Malignant, this weekend's biggest new movie in theaters -- but you won't have the same luck trying to stream Shang-Chi or Candyman.\",\"url\":\"https://www.cnet.com/tech/services-and-software/hbo-max-is-streaming-malignant-but-not-shang-chi-or-candyman-sorry/\",\"urlToImage\":\"https://www.cnet.com/a/img/WySKkRHbRFtLsy3FgQL4-9jnGyU=/0x189:4000x2435/1200x630/2020/06/14/8498dd90-a776-4cec-8c6e-b313c17397ac/hbo-max-logo-phone-2773.jpg\",\"publishedAt\":\"2021-09-10T15:28:30Z\",\"content\":\"Currently, HBO Max streams new movies from Warner Bros. the same day they're released in theaters. \\r\\nAngela Lang/CNET\\r\\nHBO Max has become synonymous with streaming new theatrical movies, thanks to an… [+6375 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Punch\"},\"author\":\"Agency Report\",\"title\":\"Queen Elizabeth supports ‘Black Lives Matter’ – Official - Punch Newspapers\",\"description\":\"The Queen and the royal family are supporters of the Black Lives Matter movement, one of Her Majesty’s representatives has said.\",\"url\":\"https://punchng.com/queen-elizabeth-supports-black-lives-matter-official/\",\"urlToImage\":\"https://cdn.punchng.com/wp-content/uploads/2021/09/10172121/000_19Z13U-e1631287278290.jpg\",\"publishedAt\":\"2021-09-10T15:23:58Z\",\"content\":\"The Queen and the royal family are supporters of the Black Lives Matter movement, one of Her Majestys representatives has said.\\r\\nThe first black Lord-Lieutenant for London, Sir Ken Olisa, revealed to… [+1405 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":null,\"title\":\"'I can barely hold a drumstick': Phil Collins details health struggles - CNN\",\"description\":\"Phil Collins says he is no longer able to play the drums due to health issues. The musician, who is 70, appeared on BBC Breakfast and said his son, Nic Collins, will be drumming with the band going forward. HLN's Melissa Knowles reports.\",\"url\":\"https://www.cnn.com/videos/us/2021/09/10/genesis-phil-collins-health-issues-mpx-vpx.cnn\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/150630153258-phil-collins-2010-super-tease.jpg\",\"publishedAt\":\"2021-09-10T15:21:44Z\",\"content\":\"Morning Express with Robin Meade\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Deepika Padukone hesitates before explaining extent of her depression to Amitabh Bachchan on Kaun Banega Crorepati - Hindustan Times\",\"description\":\"Kaun Banega Crorepati 13: Deepika Padukone hesitated before confessing to Amitabh Bachchan that there was a time when she didn't feel like living.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/deepika-padukone-hesitates-before-explaining-extent-of-her-depression-to-amitabh-bachchan-on-kaun-banega-crorepati-101631274385385.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/deepika_padukone_amitabh_bachchan_kbc_1631275485941_1631275494835.jpeg\",\"publishedAt\":\"2021-09-10T15:19:58Z\",\"content\":\"Actor Deepika Padukone spoke about her depression in an appearance on Kaun Banega Crorepati 13, as host Amitabh Bachchan listened. Deepika and Farah Khan will appear as celebrity guests on Friday's e… [+1609 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Punch\"},\"author\":\"Victoria Edeme\",\"title\":\"BBNaija S6: Why I was happy Saskay got flowers from Jaypaul – Cross - Punch Newspapers\",\"description\":\"Big Brother Naija Shine Ya Eye housemate, Cross, has stated that he was happy when Saskay got flowers from Japypaul.\",\"url\":\"https://punchng.com/bbnaija-s6-why-i-was-happy-saskay-got-flowers-from-jaypaul-cross/\",\"urlToImage\":\"https://cdn.punchng.com/wp-content/uploads/2021/08/19185339/20210819_175228_0000.png\",\"publishedAt\":\"2021-09-10T15:11:15Z\",\"content\":\"Big Brother Naija Shine Ya Eye housemate, Cross, has stated that he was happy when Saskay got flowers from Japypaul even though she was his love interest.\\r\\nCross made this known in a Diary Session on… [+971 chars]\"},{\"source\":{\"id\":null,\"name\":\"REVOLT TV\"},\"author\":\"Tamantha Gunn\",\"title\":\"Black Twitter reacts to Chlöe Bailey’s new music video for “Have Mercy” - REVOLT TV\",\"description\":\"“Chloe is truly Destiny’s child,” tweeted one person.\",\"url\":\"https://www.revolt.tv/news/2021/9/10/22666604/twitter-reactions-chloe-baileys-have-mercy\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/FOf88MiIjWn_GjIVJhscaIPoR2I=/0x0:3978x2083/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22839689/chloe_bailey.jpg\",\"publishedAt\":\"2021-09-10T15:09:25Z\",\"content\":\"Chlöe Bailey has finally dropped the highly anticipated video for her new single Have Mercy and Black Twitter is in shambles. \\r\\nBailey first teased the track back in July while celebrating her 23rd b… [+2442 chars]\"},{\"source\":{\"id\":null,\"name\":\"Page Six\"},\"author\":\"Hannah Southwick\",\"title\":\"Kylie Jenner bares bump in sheer lace jumpsuit at NYFW - Page Six\",\"description\":\"The makeup mogul knows how to show off her bump in style. While attending a NYFW show at the Empire State Building, she rocked a barely-there lace look.\",\"url\":\"https://pagesix.com/2021/09/10/kylie-jenner-bares-bump-in-sheer-lace-jumpsuit-at-nyfw/\",\"urlToImage\":\"https://pagesix.com/wp-content/uploads/sites/3/2021/09/kylie-jenner-nyfw-67.jpg?quality=90&strip=all&w=1200\",\"publishedAt\":\"2021-09-10T15:04:00Z\",\"content\":\"Kylie Jenner is one hot mama.\\r\\nJust two days after revealing her pregnancy, the makeup mogul bared her bump at New York Fashion Week on Thursday in a sheer lace jumpsuit.\\r\\nHer curve-hugging LaQuan Sm… [+1490 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Owen Tonks\",\"title\":\"Mel B hints that Victoria Beckham could REUNITE with the Spice Girls - Daily Mail\",\"description\":\"The singer, 46, was a guest host of Steph's Packed Lunch in honour of Channel 4's Black To Front Day, and hinted that all the girls were finally  'on the same page together' with their tour plans.\",\"url\":\"https://www.dailymail.co.uk/tvshowbiz/article-9977781/Mel-B-hints-Victoria-Beckham-REUNITE-Spice-Girls.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/15/47749203-0-image-a-90_1631283183015.jpg\",\"publishedAt\":\"2021-09-10T14:56:27Z\",\"content\":\"Mel B has hinted that Victoria Beckham could join the Spice Girls for a reunion tour as she dropped the date of 2023 for another round of comeback concerts.\\r\\nThe singer, 46, was a guest host of Steph… [+3709 chars]\"},{\"source\":{\"id\":\"cnn\",\"name\":\"CNN\"},\"author\":\"Toyin Owoseje, CNN\",\"title\":\"Kim Kardashian says she's 'not OK' after son Saint breaks his arm - CNN\",\"description\":\"Kim Kardashian revealed on Instagram that 5-year-old Saint West broke his arm, adding that she was left distraught.\",\"url\":\"https://www.cnn.com/2021/09/10/entertainment/kim-kardashian-son-broken-arm-intl-scli/index.html\",\"urlToImage\":\"https://cdn.cnn.com/cnnnext/dam/assets/210910090907-kim-kardashian-file-2020-restricted-super-tease.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"The A.V. Club\"},\"author\":\"Tatiana Tenreyro\",\"title\":\"Skins star Kathryn Prescott is in the ICU after being hit by a cement truck - The A.V. Club\",\"description\":\"Her twin sister and fellow Skins star Megan Prescott shared an Instagram post detailing Kathryn's condition\",\"url\":\"https://www.avclub.com/skins-star-kathryn-prescott-is-in-the-icu-after-being-h-1847650891\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/cd5082a13a051486a95961218e67567e.jpg\",\"publishedAt\":\"2021-09-10T14:50:00Z\",\"content\":\"Kathryn Prescott, best known for playing fan-favorite Emily Fitch in the show Skins, has been hospitalized with severe injuries after being hit by a cement truck in New York City. Her twin sister Meg… [+1808 chars]\"},{\"source\":{\"id\":null,\"name\":\"Hindustan Times\"},\"author\":\"HT Entertainment Desk\",\"title\":\"Krushna Abhishek prays for truce with Govinda and Sunita Ahuja: 'We love each other despite the internal issues' - Hindustan Times\",\"description\":\"Krushna Abhishek has reacted to Govinda's wife Sunita Ahuja's recent interview. Sunita had slammed Krushna and said she doesn't want to see his face again. Krushna is the nephew of Govinda and Sunita.\",\"url\":\"https://www.hindustantimes.com/entertainment/tv/krushna-abhishek-prays-for-truce-with-govinda-and-sunita-ahuja-we-love-each-other-despite-the-internal-issues-101631281861066.html\",\"urlToImage\":\"https://images.hindustantimes.com/img/2021/09/10/1600x900/Krushna_Govinda_(1)_1631283221684_1631283230941.jpg\",\"publishedAt\":\"2021-09-10T14:44:27Z\",\"content\":\"Krushna Abhishek hopes that the issues between him and Govinda and Sunita Ahuja are resolved soon. The actor-comedian's relationship with his uncle and aunt soured a few years ago. \\r\\nSpeaking with th… [+1657 chars]\"},{\"source\":{\"id\":null,\"name\":\"Castanet.net\"},\"author\":null,\"title\":\"Actress Kathryn Prescott in critical condition after being hit by cement truck - Entertainment News - Castanet.net\",\"description\":\"Skins actress Kathryn Prescott has been hospitalized with multiple injuries after being hit by a cement truck while crossing a road in New York.\",\"url\":\"https://www.castanet.net/news/Entertainment/345353/Actress-Kathryn-Prescott-in-critical-condition-after-being-hit-by-cement-truck\",\"urlToImage\":\"https://www.castanet.net/content/2021/9/thumbs/wenn_62018_p.jpg\",\"publishedAt\":\"2021-09-10T14:43:00Z\",\"content\":\"Skins actress Kathryn Prescott has been hospitalized with multiple injuries after being hit by a cement truck while crossing a road in New York.\\r\\nThe 30-year-old was rushed to hospital on Tuesday, he… [+1709 chars]\"}]}";
            DifferentFunctions.writeToFile(Mcontext,"JSON_ENTERTAINMENTNEWS_CACHE.json", Response);
        }
        ResponseEntertainmentJSON = Response;
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
            if(AtAGlanceFragment.isOnline(Mcontext))
                Picasso.get().load(MyIMGURLArrayListForEntertainmentNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews6); // Set Image
            else
                ImageNews6.setImageResource(R.drawable.materialwall);
        }
    }


}
