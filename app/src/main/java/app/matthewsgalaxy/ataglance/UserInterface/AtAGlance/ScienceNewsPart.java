package app.matthewsgalaxy.ataglance.UserInterface.AtAGlance;

import static android.content.ContentValues.TAG;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.ParseJSONWorldNews;
import static app.matthewsgalaxy.ataglance.AdditionalClasses.DifferentFunctions.readFromFile;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.MyTitlesArrayListForWorldNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.ResponseScienceNews;
import static app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment.isOnline;

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


public class ScienceNewsPart {
    public Chip PrevArticleChip2, NextArticleChip2,ChipURLLink2,ChipURLLink2ReadLater;
    public TextView NewsDescriptionText2,ChipNewsTitle2;
    public ImageView ImageNews2;
    public int CurrentArticleNumber=0;

    public static ArrayList<String> MyTitlesArrayListForScienceNews;
    public static ArrayList<String> MyDescriptionsArrayListForScienceNews;
    public static ArrayList<String> MyURLArrayListForScienceNews;
    public static ArrayList<String> MyIMGURLArrayListForScienceNews;
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
        ChipURLLink2ReadLater = b.findViewById(R.id.ChipURLLink2ReadLater);
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
                                if(isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews2); // Set Image
                                else
                                    ImageNews2.setImageResource(R.drawable.materialwall);
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
                                if(isOnline(v.getContext()))
                                    Picasso.get().load(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews2); // Set Image
                                else
                                    ImageNews2.setImageResource(R.drawable.materialwall);
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
        System.out.println("RESPONSE READ FROM FILE (SCIENCE NEWS): " + Response);
        if(Response == null || Response == ""){
            Response = "{\"status\":\"ok\",\"totalResults\":744,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Inverse\"},\"author\":\"Jon Kelvey\",\"title\":\"The James Webb Telescope is delayed again. Here's why. - Inverse\",\"description\":\"NASA announced Wednesday that the Jams Webb telescope will launch on December 18, rather than October 31 as previously scheduled.\",\"url\":\"https://www.inverse.com/science/why-nasa-has-bumped-the-launch-of-the-james-webb-telescope-again\",\"urlToImage\":\"https://imgix.bustle.com/uploads/getty/2021/9/9/e71b592b-e74a-4aac-8113-4ed579dc32c5-getty-620501012.jpg?w=1200&h=630&fit=crop&crop=faces&fm=jpg\",\"publishedAt\":\"2021-09-10T16:30:25Z\",\"content\":\"The bad news: The launch of the James Webb Space Telescope\\r\\n, the decades in the making, big-freaking-mirror-equipped successor to the Hubble, has been delayed yet again. \\r\\nThe good news: Webb is sti… [+3269 chars]\"},{\"source\":{\"id\":null,\"name\":\"3dnatives\"},\"author\":null,\"title\":\"The First Ever 3D Printed Stellar Nurseries to be Used to Study Star Creation - 3Dnatives\",\"description\":\"The latest news on 3D Printing and Additive Manufacturing technologies. Find the best stories, prices, interviews, market reports and tests on 3Dnatives\",\"url\":\"https://www.3dnatives.com/en/\",\"urlToImage\":\"https://www.3dnatives.com/en/wp-content/uploads/sites/2/logo_3Dnatives2017_carre.png\",\"publishedAt\":\"2021-09-10T16:05:10Z\",\"content\":\"3Dnatives is the largest international online media platform on 3D printing and its applications. With its in-depth analysis of the market, 3Dnatives gets over 1 million unique visitors per month and… [+276 chars]\"},{\"source\":{\"id\":null,\"name\":\"SciTechDaily\"},\"author\":null,\"title\":\"Personality Matters, Even for Wildlife: Social Skills Give Ground Squirrels an Advantage - SciTechDaily\",\"description\":\"Humans acknowledge that personality goes a long way, at least for our species. But scientists have been more hesitant to ascribe personality—defined as consistent behavior over time—to other animals. A study from the University of California, Davis is the fir…\",\"url\":\"https://scitechdaily.com/personality-matters-even-for-wildlife-social-skills-give-ground-squirrels-an-advantage/\",\"urlToImage\":\"https://scitechdaily.com/images/Golden-Mantled-Ground-Squirrel-Close-Up.jpg\",\"publishedAt\":\"2021-09-10T16:02:47Z\",\"content\":\"Humans acknowledge that personality goes a long way, at least for our species. But scientists have been more hesitant to ascribe personality—defined as consistent behavior over time—to other animals.… [+5302 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Chris Young\",\"title\":\"New Images of the \\\"Dog Bone\\\" Asteroid Show It May Soon Fall Apart - Interesting Engineering\",\"description\":\"The rapidly-spinning asteroid is shaped like a dog bone and has at least two orbiting moons.\",\"url\":\"https://interestingengineering.com/new-images-of-the-dog-bone-asteroid-show-it-may-soon-fall-apart\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/BxG2x3qDw9/sizes/kleopatra-asteroid_md.jpg\",\"publishedAt\":\"2021-09-10T15:50:00Z\",\"content\":\"An international team of astronomers captured new images of a rapidly-rotating asteroid between Mars and Jupiter. The asteroid, called Kleopatra, was first discovered orbiting our Sun twenty years ag… [+2841 chars]\"},{\"source\":{\"id\":null,\"name\":\"CNET\"},\"author\":\"CNET staff\",\"title\":\"Save 19% on this Lego kit for the International Space Station - CNET\",\"description\":\"You'll need to supply your own space debris if you want to recreate scenes from Gravity, though.\",\"url\":\"https://www.cnet.com/news/save-19-on-this-lego-kit-for-the-international-space-station/\",\"urlToImage\":\"https://www.cnet.com/a/img/sElBLVWScBcLePjaWQkk2pFwbww=/1200x630/2021/09/10/b3c396a3-1e7e-4839-a025-30b739ed04d2/81nx5db7zil-ac-sl1500.jpg\",\"publishedAt\":\"2021-09-10T15:44:00Z\",\"content\":\"Lego\\r\\nIt's not often you find a good deal on any Lego kit, but that is doubly true when dealing with any of the bigger kits. There are so many different pieces involved in any larger kit, but the mor… [+889 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Dan Avery\",\"title\":\"T-Rexes tore into each other's faces in vicious competitions for mates - Daily Mail\",\"description\":\"Scientists in Canada examined 200 tyrannosaur skulls and found similar scars in half of adults. They believe the dinos were biting each other on the face to establish dominance in ritual fights\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977773/T-Rexes-tore-faces-vicious-competitions-mates.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/17/47755865-0-image-a-4_1631289977994.jpg\",\"publishedAt\":\"2021-09-10T15:37:03Z\",\"content\":\"We know Tyrannosaurus rex was the terror of the Late Cretaceous, but this apex predator would also engage in dino flight clubs with other T-rexes, according to a new report.\\r\\nScientists in Canada exa… [+3848 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Margo Rosenbaum\",\"title\":\"What will the planet look like in 50 years? Here's how climate scientists figure it out - Phys.org\",\"description\":\"Climate change scientists don't like to use the term \\\"prediction.\\\" Rather, they're making \\\"projections\\\" about the future of the planet as sea levels rise, wildfires sweep the West and hurricanes become more ferocious.\",\"url\":\"https://phys.org/news/2021-09-planet-years-climate-scientists-figure.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2019/3-climate.jpg\",\"publishedAt\":\"2021-09-10T15:20:01Z\",\"content\":\"Climate change scientists don't like to use the term \\\"prediction.\\\" Rather, they're making \\\"projections\\\" about the future of the planet as sea levels rise, wildfires sweep the West and hurricanes beco… [+11387 chars]\"},{\"source\":{\"id\":null,\"name\":\"Inverse\"},\"author\":\"Passant Rabie\",\"title\":\"What happens if a solar storm hits Earth? 4 critical questions answered - Inverse\",\"description\":\"How would our modern day technology react to a geomagnetic storm?\",\"url\":\"https://www.inverse.com/science/a-recent-uptick-in-solar-storms-has-scientists-worried\",\"urlToImage\":\"https://imgix.bustle.com/uploads/image/2021/9/8/64fa77b3-fa08-4ce9-93f4-d2fa81701e29-solarfury_cover.jpg?w=1200&h=630&fit=crop&crop=faces&fm=jpg\",\"publishedAt\":\"2021-09-10T15:00:25Z\",\"content\":\"On August 26, \\r\\na flare-up erupted from the Sun and set off a solar tsunami.\\r\\nThis event sent a giant wave of hot particles flowing through the Solar System at speeds of up to 560 miles per hour, and… [+4006 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Chris Young\",\"title\":\"Quantum Gas Experiment Creates the Coldest Temperature Ever - Interesting Engineering\",\"description\":\"The researchers say their lab was momentarily one of the \\\"coldest places in the universe.\\\"\",\"url\":\"https://interestingengineering.com/quantum-gas-broke-the-low-temperature-record-after-a-sharp-free-fall\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/y5wWZ2lY6X/sizes/quantum-gas-broke-the-low-temperature-record-after-a-sharp-free-fall_md.jpg\",\"publishedAt\":\"2021-09-10T14:52:00Z\",\"content\":\"Physicists at the University of Bremen, Germany produced the coldest temperature ever recorded, an incredibly precisely measured 38 trillionths of a degree above absolute zero. They did so as part of… [+2893 chars]\"},{\"source\":{\"id\":null,\"name\":\"The Mercury News\"},\"author\":\"CNN.com Wire Service\",\"title\":\"Growing risk of once-in-a-century solar superstorm that could knock out internet, study says - The Mercury News\",\"description\":\"Imagine if one day the internet was down not just in your neighborhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm. It sounds like science fiction, but a…\",\"url\":\"https://www.mercurynews.com/2021/09/10/growing-risk-of-once-in-a-century-solar-superstorm-that-could-knock-out-internet-study-says/\",\"urlToImage\":\"https://www.mercurynews.com/wp-content/uploads/2016/08/20120125__flare3.jpg?w=400&h=324\",\"publishedAt\":\"2021-09-10T14:44:47Z\",\"content\":\"TORONTO – Imagine if one day the internet was down not just in your neighborhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm.\\r\\nIt sounds like science ficti… [+9825 chars]\"},{\"source\":{\"id\":null,\"name\":\"Interesting Engineering\"},\"author\":\"Ameya Paleja\",\"title\":\"Physicists Say That a Fifth Dimension Could Be on the Horizon - Interesting Engineering\",\"description\":\"In the 1920s, the five-dimensional theory was proposed to explain how nature works, and now, a century later, the theory might be revived again.\",\"url\":\"https://interestingengineering.com/physicists-say-that-a-fifth-dimension-could-be-on-the-horizon\",\"urlToImage\":\"https://inteng-storage.s3.amazonaws.com/img/iea/XD6KJrVzGv/sizes/physicists-say-that-a-fifth-dimension-could-be-on-the-horizon_md.jpg\",\"publishedAt\":\"2021-09-10T14:13:59Z\",\"content\":\"Scientists often get asked if they do new experiments in the lab or keep repeating older ones that they know the results for sure. While most scientists do the former, the progress of science also de… [+2632 chars]\"},{\"source\":{\"id\":null,\"name\":\"Euronews\"},\"author\":null,\"title\":\"Dutch scientist uncovers an old recording of a talking duck saying 'you bloody fool!' - Euronews\",\"description\":\"A Dutch scientist has uncovered old recordings of a musk duck mimicking the phrase \\\"You bloody fool!\\\" in an Australian accent.\",\"url\":\"https://www.euronews.com/next/2021/09/10/you-bloody-fool-scientists-uncover-an-old-recording-of-a-talking-duck-during-a-mating-disp\",\"urlToImage\":\"https://static.euronews.com/articles/stories/06/05/01/90/1000x563_cmsv2_e4480ef1-6f39-5655-90cb-ea2d67f70907-6050190.jpg\",\"publishedAt\":\"2021-09-10T14:13:54Z\",\"content\":\"A Dutch scientist has uncovered old recordings of a musk duck mimicking the phrase, \\\"You bloody fool!\\\" - learnt when it was raised by humans in an Australian bird park.\\r\\nLeiden University scientist C… [+1411 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Hayley Dunning\",\"title\":\"Insight into power generation in photosynthesis may lead to more resilient crops - Phys.org\",\"description\":\"A study into the energy-making process in plants could help engineer crops more resistant to stress or bacteria that produce pharmaceuticals.\",\"url\":\"https://phys.org/news/2021-09-insight-power-photosynthesis-resilient-crops.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/insight-into-power-gen.jpg\",\"publishedAt\":\"2021-09-10T14:04:44Z\",\"content\":\"A study into the energy-making process in plants could help engineer crops more resistant to stress or bacteria that produce pharmaceuticals.\\r\\nA team of researchers, led by Imperial College London an… [+4211 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Peter Genzer\",\"title\":\"Catalyst study advances carbon-dioxide-to-ethanol conversion - Phys.org\",\"description\":\"An international collaboration of scientists has taken a significant step toward the realization of a nearly \\\"green\\\" zero-net-carbon technology that will efficiently convert carbon dioxide, a major greenhouse gas, and hydrogen into ethanol, which is useful as…\",\"url\":\"https://phys.org/news/2021-09-catalyst-advances-carbon-dioxide-to-ethanol-conversion.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/2021/catalyst-study-advance.jpg\",\"publishedAt\":\"2021-09-10T13:55:07Z\",\"content\":\"An international collaboration of scientists has taken a significant step toward the realization of a nearly \\\"green\\\" zero-net-carbon technology that will efficiently convert carbon dioxide, a major g… [+5248 chars]\"},{\"source\":{\"id\":null,\"name\":\"Dailynewsegypt.com\"},\"author\":\"Daily News Egypt\",\"title\":\"Relatively massive animal species discovered in half-billion-year-old Burgess Shale - Daily News Egypt\",\"description\":\"massive animal - Royal Ontario Museum palaeontologists unearth one of largest radiodonts of Cambrian explosion\",\"url\":\"https://dailynewsegypt.com/2021/09/10/relatively-massive-animal-species-discovered-in-half-billion-year-old-burgess-shale/\",\"urlToImage\":\"https://dneegypt.nyc3.digitaloceanspaces.com/2021/09/Titanokorys_ROMIP_65745-1-scaled.jpeg\",\"publishedAt\":\"2021-09-10T13:52:47Z\",\"content\":\"Palaeontologists at the Royal Ontario Museum (ROM) have uncovered the remains of a huge new fossil species belonging to an extinct animal group in half-a-billion-year-old Cambrian rocks from Kootenay… [+3390 chars]\"},{\"source\":{\"id\":null,\"name\":\"KTVZ\"},\"author\":\"CNN Newsource\",\"title\":\"Growing risk of once-in-a-century solar superstorm that could knock out internet, study says - KTVZ\",\"description\":\"By Alexandra Mae Jones Click here for updates on this story     TORONTO (CTV Network) — Imagine if one day the internet was down not just in your neighbourhood, but across the globe, knocked out by a threat from space: an enormous solar superstorm. It sounds …\",\"url\":\"https://ktvz.com/cnn-regional/2021/09/10/growing-risk-of-once-in-a-century-solar-superstorm-that-could-knock-out-internet-study-says/\",\"urlToImage\":\"https://ktvz.b-cdn.net/2021/06/KTVZ-USworld2.png\",\"publishedAt\":\"2021-09-10T13:43:39Z\",\"content\":\"By Alexandra Mae Jones\\r\\nClick here for updates on this story\\r\\n    TORONTO (CTV Network) — Imagine if one day the internet was down not just in your neighbourhood, but across the globe, knocked out by… [+9936 chars]\"},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Liu Jia\",\"title\":\"Scientists discover mechanism of bone growth - Phys.org\",\"description\":\"In a study published in Cell Stem Cell, the research group led by Dr. Zhou Bo from the Center for Excellence in Molecular Cell Science, Shanghai Institute of Biochemistry and Cell Biology of the Chinese Academy of Sciences reported the transition of skeletal …\",\"url\":\"https://phys.org/news/2021-09-scientists-mechanism-bone-growth.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/2021/scientists-discover-me.jpg\",\"publishedAt\":\"2021-09-10T13:40:01Z\",\"content\":\"In a study published in Cell Stem Cell, the research group led by Dr. Zhou Bo from the Center for Excellence in Molecular Cell Science, Shanghai Institute of Biochemistry and Cell Biology of the Chin… [+2751 chars]\"},{\"source\":{\"id\":null,\"name\":\"NASA\"},\"author\":null,\"title\":\"Hubble Captures a Sparkling Cluster - NASA\",\"description\":\"This star-studded image from the NASA/ESA Hubble Space Telescope depicts NGC 6717, which lies more than 20,000 light-years from Earth in the constellation Sagittarius.\",\"url\":\"https://www.nasa.gov/image-feature/goddard/2021/hubble-captures-a-sparkling-cluster/\",\"urlToImage\":\"http://www.nasa.gov/sites/default/files/thumbnails/image/potw2136a_0.jpg\",\"publishedAt\":\"2021-09-10T13:39:02Z\",\"content\":null},{\"source\":{\"id\":null,\"name\":\"Phys.Org\"},\"author\":\"Science X staff\",\"title\":\"Image: Volcanic trenches on Mars - Phys.org\",\"description\":\"This image of the young volcanic region of Elysium Planitia on Mars [10.3°N, 159.5°E] was taken on 14 April 2021 by the CaSSIS camera on the ESA-Roscosmos ExoMars Trace Gas Orbiter (TGO).\",\"url\":\"https://phys.org/news/2021-09-image-volcanic-trenches-mars.html\",\"urlToImage\":\"https://scx2.b-cdn.net/gfx/news/hires/2021/volcanic-trenches-on-m.jpg\",\"publishedAt\":\"2021-09-10T13:26:55Z\",\"content\":\"This image of the young volcanic region of Elysium Planitia on Mars [10.3°N, 159.5°E] was taken on 14 April 2021 by the CaSSIS camera on the ESA-Roscosmos ExoMars Trace Gas Orbiter (TGO).\\r\\nThe two bl… [+1423 chars]\"},{\"source\":{\"id\":null,\"name\":\"Daily Mail\"},\"author\":\"Ryan Morrison\",\"title\":\"Driveway where Winchcombe meteorite crashed will go on display - Daily Mail\",\"description\":\"The meteorite fell to Earth in a fireball seen from across the UK, tracked by doorbell cameras, eventually landing in the Cotswold town of Winchcombe back in February.\",\"url\":\"https://www.dailymail.co.uk/sciencetech/article-9977375/Driveway-Winchcombe-meteorite-crashed-display.html\",\"urlToImage\":\"https://i.dailymail.co.uk/1s/2021/09/10/14/47750397-0-image-a-39_1631281581750.jpg\",\"publishedAt\":\"2021-09-10T13:25:15Z\",\"content\":\"A piece of the driveway where an 'extremely rare' meteorite worth £100,000 crashed will go on display at the Natural History Museum along with the space rock.\\r\\nThe meteorite fell to Earth in a fireba… [+7185 chars]\"}]}";
            DifferentFunctions.writeToFile(Mcontext,"JSON_SCIENCENEWS_CACHE.json", Response);
        }
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
                if(isOnline(Mcontext))
                    Picasso.get().load(MyIMGURLArrayListForScienceNews.get(CurrentArticleNumber)).fit().centerInside().into(ImageNews2); // Set Image
                else
                    ImageNews2.setImageResource(R.drawable.materialwall);
            }
        }
    }
}
