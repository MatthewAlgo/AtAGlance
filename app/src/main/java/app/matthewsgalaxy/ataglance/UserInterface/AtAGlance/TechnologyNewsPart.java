package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;

import android.content.Context;
import android.media.Image;
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
    public Chip PrevArticleChip3, NextArticleChip3, ChipURLLink3, ChipURLLink3ReadLater;
    public TextView NewsDescriptionText3, ChipNewsTitle3;
    public ImageView ImageNews3;
    public int CurrentArticleNumber = 0;

    public static ArrayList<String> MyTitlesArrayListForTechnologyNews;
    public static ArrayList<String> MyDescriptionsArrayListForTechnologyNews;
    public static ArrayList<String> MyURLArrayListForTechnologyNews;
    public static ArrayList<String> MyIMGURLArrayListForTechnologyNews;
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
        ChipURLLink3ReadLater = b.findViewById(R.id.ChipURLLink3ReadLater);
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
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews3); // Set Image
                                else
                                    ImageNews3.setImageResource(R.drawable.materialwall);
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
                                if(AtAGlanceFragment.isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews3); // Set Image
                                else
                                    ImageNews3.setImageResource(R.drawable.materialwall);
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
        System.out.println("RESPONSE READ FROM FILE (TECH NEWS): " + Response);
        if(Response == null || Response == ""){
            Response = "{\"status\":\"ok\",\"totalResults\":702,\"articles\":[{\"source\":{\"id\":\"the-wall-street-journal\",\"name\":\"The Wall Street Journal\"},\"author\":\"Tim Higgins\",\"title\":\"Apple Trial Ends in Mixed Verdict After Fortnite-Maker’s Monopoly Allegations - The Wall Street Journal\",\"description\":\"Case pitted Steve Jobs’s vision of a walled garden of Apple hardware against Epic co-founder Tim Sweeney’s desire for an open ecosystem\",\"url\":\"https://www.wsj.com/articles/apple-trial-ends-in-mixed-verdict-after-fortnite-makers-monopoly-allegations-11631289792\",\"urlToImage\":\"https://images.wsj.net/im-386969/social\",\"publishedAt\":\"2021-09-10T16:31:00Z\",\"content\":null},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Chaim Gartenberg\",\"title\":\"Vimeo adds support for Apple’s iPhone 12 Dolby Vision HDR videos - The Verge\",\"description\":\"Vimeo is adding support for Dolby Vision HDR videos shot with Apple’s iPhone 12 phones, making it the first video website to allow for easy sharing of the higher-quality video format from iPhones.\",\"url\":\"https://www.theverge.com/2021/9/10/22666520/vimeo-apple-iphone-12-pro-dolby-vision-hdr-videos-support\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/8VaEgoUodNr-QJKLnpLJLcWSbjs=/0x4:2050x1077/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22023094/vpavic_4279_20201107_0048.0.jpg\",\"publishedAt\":\"2021-09-10T15:48:35Z\",\"content\":\"A year later, theres finally somewhere to post your Dolby Vision HDR iPhone videos\\r\\nIf you buy something from a Verge link, Vox Media may earn a commission. See our ethics statement.\\r\\nPhoto by Vjeran… [+1789 chars]\"},{\"source\":{\"id\":null,\"name\":\"Digital Trends\"},\"author\":\"Jacob Roach\",\"title\":\"Why Windows 11 is more than just a Windows 10 reskin - Digital Trends\",\"description\":\"Windows 11 is built on the same foundation as Windows 10, but it's more than a visual update. In a new video, Microsoft explains why.\",\"url\":\"https://www.digitaltrends.com/computing/windows-11-optimizations-updates-cpu-drivers/\",\"urlToImage\":\"https://icdn.digitaltrends.com/image/digitaltrends/windows-11-lifestyle-feature.jpg\",\"publishedAt\":\"2021-09-10T15:45:45Z\",\"content\":\"Windows 11 is, for the most part, a visual update to Windows 10. It’s the same operating system under the hood, just with new features and an updated look. Despite being built on the same OS, Microso… [+2981 chars]\"},{\"source\":{\"id\":\"techradar\",\"name\":\"TechRadar\"},\"author\":\"John Loeffler\",\"title\":\"Apple loses Epic fight: app developers can now avoid App Store payments - TechRadar\",\"description\":\"This is a huge hit to Apple's bottom line\",\"url\":\"https://www.techradar.com/news/developers-can-now-evade-apples-in-app-payment-scheme-to-directly-sell-to-users\",\"urlToImage\":\"https://cdn.mos.cms.futurecdn.net/fWxXJzo6UpWj9RVFTFS5rJ-1200-80.jpg\",\"publishedAt\":\"2021-09-10T15:42:49Z\",\"content\":\"Apple was just hit with a permanent injunction by the judge in its legal battle with Epic Games, preventing the company from forcing developers to use the App Store's payment system for in-app purcha… [+2270 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNBC\"},\"author\":\"Kif Leswing\",\"title\":\"Apple can no longer force developers to use in-app purchasing, judge rules in Epic Games case - CNBC\",\"description\":\"The trial took place in Oakland, California, in May, and included both company CEOs testifying in open court.\",\"url\":\"https://www.cnbc.com/2021/09/10/epic-games-v-apple-judge-reaches-decision-.html\",\"urlToImage\":\"https://image.cnbcfm.com/api/v1/image/106940080-1631290244185-106940080-1631288561148-gettyimages-1233028006-APPLE-EPIC-TRIAL.jpg?v=1631290254\",\"publishedAt\":\"2021-09-10T15:36:06Z\",\"content\":\"Apple's lucrative App Store business received a major blow Friday thanks to a federal judge's decision in the company's legal battle with Epic Games.\\r\\nJudge Yvonne Gonzalez Rogers on Friday handed do… [+5386 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNA\"},\"author\":null,\"title\":\"US judge in 'Fortnite' case strikes down Apple's in-app payment restrictions - CNA\",\"description\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect payments in apps. The ruling is similar to a move that Apple ma…\",\"url\":\"https://www.channelnewsasia.com/business/us-judge-fortnite-case-strikes-down-apples-app-payment-restrictions-2170116\",\"urlToImage\":\"https://onecms-res.cloudinary.com/image/upload/s--0xar_RRy--/fl_relative%2Cg_south_east%2Cl_one-cms:core:watermark:reuters%2Cw_0.1/f_auto%2Cq_auto/c_fill%2Cg_auto%2Ch_676%2Cw_1200/v1/one-cms/core/2021-09-10t154906z_3_lynxmpeh890pr_rtroptp_3_apple-epic-games.jpg?itok=aiOLk8rM\",\"publishedAt\":\"2021-09-10T15:34:00Z\",\"content\":\":A U.S. judge on Friday issued a ruling in \\\"Fortnite\\\" creator Epic Games' antitrust lawsuit against Apple Inc's App Store, striking down some of Apple's restrictions on how developers can collect pay… [+3342 chars]\"},{\"source\":{\"id\":null,\"name\":\"9to5Mac\"},\"author\":\"Michael Potuck\",\"title\":\"WhatsApp launching end-to-end encrypted cloud backups for iOS and Android - 9to5Mac\",\"description\":\"Following through with a long-requested feature from users, Facebook-owned WhatsApp is going to make cloud backups end-to-end encrypted to go along with the end-to-end encryption of the messages sent with the service. The app will offer the feature in “the co…\",\"url\":\"http://9to5mac.com/2021/09/10/whatsapp-end-to-end-encrypted-cloud-backups-ios-android/\",\"urlToImage\":\"https://i1.wp.com/9to5mac.com/wp-content/uploads/sites/6/2021/09/whatsapp-encrypted-backups-ios.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1\",\"publishedAt\":\"2021-09-10T15:28:00Z\",\"content\":\"Following through with a long-requested feature from users, Facebook-owned WhatsApp is going to make cloud backups end-to-end encrypted to go along with the end-to-end encryption of the messages sent… [+1802 chars]\"},{\"source\":{\"id\":\"the-times-of-india\",\"name\":\"The Times of India\"},\"author\":\"TIMESOFINDIA.COM\",\"title\":\"Instagram is testing favourite users feature: What it means, how it may affect your feed and more - Times of India\",\"description\":\"Instagram, the photo-sharing app owned by Facebook, is said to be testing a new feature that will allow its users to mark other users as their favouri\",\"url\":\"https://timesofindia.indiatimes.com/gadgets-news/instagram-is-testing-favourite-users-feature-what-it-means-how-it-may-affect-your-feed-and-more/articleshow/86097799.cms\",\"urlToImage\":\"https://static.toiimg.com/thumb/msid-86097792,width-1070,height-580,imgsize-27702,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg\",\"publishedAt\":\"2021-09-10T15:23:00Z\",\"content\":\"#Instagram is working on \\\"Favorites\\\" Posts from your favorites are shown higher in feed. https://t.co/NfBd8v4IHR\\r\\n— Alessandro Paluzzi (@alex193a) 1631184880000\"},{\"source\":{\"id\":\"ars-technica\",\"name\":\"Ars Technica\"},\"author\":\"Ron Amadeo\",\"title\":\"Google.com dark mode is rolling out to everyone - Ars Technica\",\"description\":\"Say goodbye to the blinding-white Google.com start page and search results.\",\"url\":\"https://arstechnica.com/gadgets/2021/09/google-com-dark-mode-is-rolling-out-to-everyone/\",\"urlToImage\":\"https://cdn.arstechnica.net/wp-content/uploads/2021/09/28-690x380.jpg\",\"publishedAt\":\"2021-09-10T15:21:59Z\",\"content\":\"63 with 49 posters participating\\r\\n<ul><li>\\r\\n Dark mode! It's finally here. \\r\\n</li><li>\\r\\n</li><li>\\r\\n To turn it on, go to the settings. On Google.com, the settings link is in the bottom-right corner. … [+1856 chars]\"},{\"source\":{\"id\":null,\"name\":\"Motor1 \"},\"author\":\"Anthony Alaniz\",\"title\":\"Honda S2000 Drag Races Acura Integra Type R in RWD-Vs-FWD Fight - Motor1 \",\"description\":\"Watch as the 2002 Honda S2000 is pitted against the 2000 Acura Integra Type R in a series of drag races.\",\"url\":\"https://www.motor1.com/news/532305/honda-s2000-races-acura-integra/\",\"urlToImage\":\"https://cdn.motor1.com/images/mgl/z68Zq/s1/honda-s2000-races-acura-integra-type-r.jpg\",\"publishedAt\":\"2021-09-10T15:17:00Z\",\"content\":\"The Hoonigan YouTube channel is a treasure trove of automotive fun, and the crews latest video is no different. The channel took a step back 20 years into a wildly different performance era, grabbing… [+1612 chars]\"},{\"source\":{\"id\":null,\"name\":\"Kotaku\"},\"author\":\"Ethan Gach\",\"title\":\"The Bloodborne of Samurai Games, Nioh, Is Free On PC - Kotaku\",\"description\":\"Make time for From Software's hack-and-slash game this weekend\",\"url\":\"https://kotaku.com/the-bloodborne-of-samurai-games-nioh-is-free-on-pc-1847651610\",\"urlToImage\":\"https://i.kinja-img.com/gawker-media/image/upload/c_fill,f_auto,fl_progressive,g_center,h_675,pg_1,q_80,w_1200/1c3c02dedf9e85e29f3c468ff5db7962.jpg\",\"publishedAt\":\"2021-09-10T15:15:00Z\",\"content\":\"Nioh takes the action of a Ninja Gaiden and the structure of a Dark Souls and marries them into something elegant, exceptional, and currently free for PC on the Epic Games Store.\\r\\nTeam Ninjas action … [+2121 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Andre Salles\",\"title\":\"How a state-of-the-art optics system will make the Advanced Photon Source upgrade possible - Phys.org\",\"description\":\"To provide X-ray beams that are both very bright and very tightly focused, an Argonne team had to create a new system of mirrors, lenses and equipment for the upgraded Advanced Photon Source.\",\"url\":\"https://phys.org/news/2021-09-state-of-the-art-optics-advanced-photon-source.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/through-the-looking-gl-1.jpg\",\"publishedAt\":\"2021-09-10T15:11:37Z\",\"content\":\"To provide X-ray beams that are both very bright and very tightly focused, an Argonne team had to create a new system of mirrors, lenses and equipment for the upgraded Advanced Photon Source.\\r\\nIn the… [+7313 chars]\"},{\"source\":{\"id\":null,\"name\":\"NDTV News\"},\"author\":\"Jagmeet Singh\",\"title\":\"WhatsApp End-to-End Encrypted Cloud Backups to Roll Out Soon for Android, iOS Users - Gadgets 360\",\"description\":\"WhatsApp on Friday announced that it will soon start rolling out end-to-end encrypted cloud backups on Android and iOS. The new move will allow users to secure their chat backups stored on Apple iCloud and Google Drive with end-to-end encryption — the same le…\",\"url\":\"https://gadgets.ndtv.com/apps/news/whatsapp-end-to-end-encrypted-backups-android-apple-rollout-starts-2536802\",\"urlToImage\":\"https://i.gadgets360cdn.com/large/whatsapp_end_to_end_encrypted_backups_image_1631286061066.jpg\",\"publishedAt\":\"2021-09-10T15:11:15Z\",\"content\":\"WhatsApp is set to soon roll out end-to-end encrypted cloud backups on Android and iOS. The new move will help users keep their chats end-to-end encrypted even when they are a part of WhatsApp backup… [+4275 chars]\"},{\"source\":{\"id\":null,\"name\":\"Forbes\"},\"author\":\"Erik Kain\",\"title\":\"FromSoftware Tweets Bizarre New ‘Elden Ring’ Image - Forbes\",\"description\":\"FromSoftware just tweeted a very peculiar image for the Japanese game developer’s upcoming action-RPG Elden Ring.\",\"url\":\"https://www.forbes.com/sites/erikkain/2021/09/10/fromsoftware-tweets-bizarre-new-elden-ring-image/\",\"urlToImage\":\"https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F613b73a4809e2ac104f345ef%2F0x0.jpg\",\"publishedAt\":\"2021-09-10T15:09:50Z\",\"content\":\"Elden Ring\\r\\nCredit: FromSoftware\\r\\nFromSoftware just tweeted a very peculiar image for the Japanese game developers upcoming action-RPG Elden Ring.\\r\\nIn the wake of the Shattering, a blight claimed the… [+971 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Alex Heath\",\"title\":\"WhatsApp is adding encrypted backups - The Verge\",\"description\":\"WhatsApp is rolling out end-to-end encrypted backups for chat logs on Android and iOS, protecting the file from anyone without the key.\",\"url\":\"https://www.theverge.com/2021/9/10/22665968/whatsapp-backups-end-to-end-encryption-ios-android\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/MD0eT-m-uto-M_MHFTRUrF_FHDk=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22245551/acastro_210119_1777_whatsapp_0003.jpg\",\"publishedAt\":\"2021-09-10T15:05:45Z\",\"content\":\"Going a step further than Apples iMessage\\r\\nIllustration by Alex Castro / The Verge\\r\\nWhatsApp will let its more than 2 billion users fully encrypt the backups of their messages, the Facebook-owned app… [+2774 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Brian Barrett\",\"title\":\"WhatsApp Fixes Its Biggest Encryption Loophole - WIRED\",\"description\":\"The ubiquitous messaging service will add end-to-end encryption to backups, keeping your chats safe no matter whose cloud they're stored in.\",\"url\":\"https://www.wired.com/story/whatsapp-end-to-end-encrypted-backups/\",\"urlToImage\":\"https://media.wired.com/photos/613a742118bb03f66e80f672/191:100/w_1280,c_limit/Security---WhatsApp-Encryption.jpg\",\"publishedAt\":\"2021-09-10T15:05:00Z\",\"content\":\"Few, if any, services have done more to bring secure messaging to more people than WhatsApp. Since 2016, the messaging platform has enabled end-to-end encryptionby default, no lessfor its billions of… [+2909 chars]\"},{\"source\":{\"id\":\"techcrunch\",\"name\":\"TechCrunch\"},\"author\":\"Manish Singh, Zack Whittaker\",\"title\":\"WhatsApp will finally let users encrypt their chat backups in the cloud - TechCrunch\",\"description\":\"The messaging app giant says it will offer users two ways to encrypt their cloud backups, and the feature is optional.\",\"url\":\"http://techcrunch.com/2021/09/10/whatsapp-encrypt-cloud-backup/\",\"urlToImage\":\"https://techcrunch.com/wp-content/uploads/2021/09/GettyImages-1234875399.jpg?w=600\",\"publishedAt\":\"2021-09-10T15:04:29Z\",\"content\":\"WhatsApp said on Friday it will give its two billion users the option to encrypt their chat backups to the cloud, taking a significant step to put a lid on one of the tricky ways private communicatio… [+4990 chars]\"},{\"source\":{\"id\":null,\"name\":\"Android Authority\"},\"author\":null,\"title\":\"Huawei schedules October event, likely for global launch of P50 series - Android Authority\",\"description\":\"Huawei scheduled an October launch event. The company doesn't say so, but it's very likely it's the global launch of the Huawei P50 series.\",\"url\":\"https://www.androidauthority.com/huawei-p50-global-launch-3019643/\",\"urlToImage\":\"https://cdn57.androidauthority.net/wp-content/uploads/2021/07/HUAWEI-P50-Series-Keynote-image-2-scaled.jpg\",\"publishedAt\":\"2021-09-10T15:01:27Z\",\"content\":\"<ul><li>There is a new Huawei event scheduled that is very likely the global launch of the Huawei P50 series.</li><li>The event happens on October 21 in Vienna.</li><li>Huawei already launched the ph… [+1373 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Ian Randall\",\"title\":\"Tech: iRobot's new £900 Roomba vacuum cleaner is trained to avoid dog and cat faeces - Daily Mail\",\"description\":\"The Roomba® j7+ from Massachussets-based iRobot can also avoid getting tangled in cables, automatically clean only when everyone has left the house and learn from the feedback you give it.\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977405/Tech-iRobots-new-900-Roomba-vacuum-cleaner-trained-avoid-dog-cat-faeces.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/17/47756929-0-image-a-2_1631290044645.jpg\",\"publishedAt\":\"2021-09-10T15:00:57Z\",\"content\":\"iRobot's new Roomba robotic vacuum cleaner now avoids cat and dog faeces after customers complained of previous models smearing them all over the house.\\r\\nThese 'poopocalypses' were a disgusting probl… [+7546 chars]\"},{\"source\":{\"id\":\"the-verge\",\"name\":\"The Verge\"},\"author\":\"Jay Peters\",\"title\":\"Amazon’s New World could come to its Luna cloud gaming service - The Verge\",\"description\":\"New World, Amazon Games’ upcoming MMO, could be offered on Amazon’s Luna cloud gaming service, Richard Lawrence, studio director at Amazon Games, said in an interview with The Verge.\",\"url\":\"https://www.theverge.com/2021/9/10/22663690/amazon-new-world-luna-cloud-gaming\",\"urlToImage\":\"https://cdn.vox-cdn.com/thumbor/9pjPmIzHHYGu0RH104mDqyyr9i0=/0x32:1920x1037/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/22835708/gallery2.jpeg\",\"publishedAt\":\"2021-09-10T15:00:00Z\",\"content\":\"Im fairly confident that at some point youll also be seeing New World on Luna\\r\\nA screenshot from Amazon Games New World.\\r\\nImage: Amazon Games\\r\\nNew World, Amazon Games upcoming MMO, is just a few week… [+2308 chars]\"}]}";
            DifferentFunctions.writeToFile(MyContext,"JSON_TECHNEWS_CACHE.json", Response);
        }
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
            if(AtAGlanceFragment.isOnline(MyContext))
                Picasso.get().load(MyIMGURLArrayListForTechnologyNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews3); // Set Image
            else
                ImageNews3.setImageResource(R.drawable.materialwall);
        }
    }

}

