package app.matthewsgalaxy.ataglance.AdditionalClasses;

import android.widget.ImageView;

import app.matthewsgalaxy.ataglance.R;

public class SetImagesForImageViewIcon {
    SetImagesForImageViewIcon(ImageView ImageViewConds, String CONDS, boolean IsDayTime){
        FunctionThatDoesThat(ImageViewConds, CONDS, IsDayTime);
    }
    public static void FunctionThatDoesThat(ImageView ImageViewConds, String CONDS, boolean IsDayTime) {
        if (IsDayTime) {
            if (CONDS.equals("800")) {
                ImageViewConds.setImageResource(R.drawable.clear_day);
            } else if (CONDS.equals("801")) {
                ImageViewConds.setImageResource(R.drawable.mostly_clear_day);
            } else if (CONDS.equals("802")) {
                ImageViewConds.setImageResource(R.drawable.partly_cloudy_day);
            } else if (CONDS.equals("803")) {
                ImageViewConds.setImageResource(R.drawable.mostly_cloudy_day);
            } else if (CONDS.equals("804")) {
                ImageViewConds.setImageResource(R.drawable.cloudy_night_2);


            } else if (CONDS.equals("701")) { //Mist
                ImageViewConds.setImageResource(R.drawable.fog_day);
            } else if (CONDS.equals("711")) { //Smoke
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("721")) { //Haze
                ImageViewConds.setImageResource(R.drawable.fog_day);
            } else if (CONDS.equals("731")) { //Dust
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("741")) { //Fog
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("751")) { //Sand
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("761")) { //Dust
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("762")) { //Ash
                ImageViewConds.setImageResource(R.drawable.ash);
            } else if (CONDS.equals("771")) { //Squall
                ImageViewConds.setImageResource(R.drawable.squall);


            } else if (CONDS.equals("500")) { //Light Rain
                ImageViewConds.setImageResource(R.drawable.light_rain);
            } else if (CONDS.equals("501")) { //Moderate Rain
                ImageViewConds.setImageResource(R.drawable.rain_night);
            } else if (CONDS.equals("502")) { //Heavy Rain
                ImageViewConds.setImageResource(R.drawable.rain_night);
            } else if (CONDS.equals("503")) { // Very Heavy Rain
                ImageViewConds.setImageResource(R.drawable.very_heavy_rain_night);
            } else if (CONDS.equals("504")) { // Extreme Rain
                ImageViewConds.setImageResource(R.drawable.extreme_rain_night);
            } else if (CONDS.equals("511")) { // Freezing Rain
                ImageViewConds.setImageResource(R.drawable.freezing_rain);
            } else if (CONDS.equals("520")) { // Light intensity shower Rain
                ImageViewConds.setImageResource(R.drawable.light_rain_shower_day);
            } else if (CONDS.equals("521")) { // Shower Rain
                ImageViewConds.setImageResource(R.drawable.scattered_showers_day);
            } else if (CONDS.equals("522")) { // Heavy intensity Shower Rain
                ImageViewConds.setImageResource(R.drawable.heavy_shower_rain_day);
            } else if (CONDS.equals("531")) { // Ragged Shower Rain
                ImageViewConds.setImageResource(R.drawable.ragged_shower_rain_day);

            } else if (CONDS.equals("300")) { // Light Intensity Drizzle
                ImageViewConds.setImageResource(R.drawable.light_drizzle);
            } else if (CONDS.equals("301")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle);
            } else if (CONDS.equals("302")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle);
            } else if (CONDS.equals("310")) { // Drizzle Rain
                ImageViewConds.setImageResource(R.drawable.drizzle_rain);
            } else if (CONDS.equals("312")) { // Drizzle Rain Heavy
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
            } else if (CONDS.equals("313")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle_rain);
            } else if (CONDS.equals("314")) { // Heavy Shower Rain And Drizzle
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
            } else if (CONDS.equals("321")) { // Shower Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle);


            } else if (CONDS.equals("600")) { // Light Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_snow);
            } else if (CONDS.equals("601")) { // Snow
                ImageViewConds.setImageResource(R.drawable.snow_day);
            } else if (CONDS.equals("602")) { // HeavySnow
                ImageViewConds.setImageResource(R.drawable.heavy_snow);
            } else if (CONDS.equals("611")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.sleet_day);
            } else if (CONDS.equals("612")) { // Light Shower Sleet
                ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
            } else if (CONDS.equals("613")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.shower_sleet);
            } else if (CONDS.equals("615")) { // Light Rain And Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
            } else if (CONDS.equals("616")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.sleet_day);
            } else if (CONDS.equals("620")) { // Light Shower Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_snow);
            } else if (CONDS.equals("621")) { // Shower Snow
                ImageViewConds.setImageResource(R.drawable.snow_day);
            } else if (CONDS.equals("622")) { // Sleet



                ImageViewConds.setImageResource(R.drawable.heavy_snow);
            } else if (CONDS.equals("200")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
            } else if (CONDS.equals("201")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_day);
            } else if (CONDS.equals("202")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_rain);
            } else if (CONDS.equals("210")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("211")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("212")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("221")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
            } else if (CONDS.equals("230")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_drizzle);
            } else if (CONDS.equals("231")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_drizzle);
            } else if (CONDS.equals("232")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_drizzle);
            }


        } else {
            // It's nighttime
            if (CONDS.equals("800")) {
                ImageViewConds.setImageResource(R.drawable.clear_night);
            } else if (CONDS.equals("801")) {
                ImageViewConds.setImageResource(R.drawable.mostly_clear_night);
            } else if (CONDS.equals("802")) {
                ImageViewConds.setImageResource(R.drawable.partly_cloudy_night);
            } else if (CONDS.equals("803")) {
                ImageViewConds.setImageResource(R.drawable.mostly_cloudy_night);
            } else if (CONDS.equals("804")) {
                ImageViewConds.setImageResource(R.drawable.cloudy_night_2);


            } else if (CONDS.equals("701")) { //Mist
                ImageViewConds.setImageResource(R.drawable.fog_day);
            } else if (CONDS.equals("711")) { //Smoke
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("721")) { //Haze
                ImageViewConds.setImageResource(R.drawable.fog_day);
            } else if (CONDS.equals("731")) { //Dust
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("741")) { //Fog
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("751")) { //Sand
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("761")) { //Dust
                ImageViewConds.setImageResource(R.drawable.fog_night);
            } else if (CONDS.equals("762")) { //Ash
                ImageViewConds.setImageResource(R.drawable.ash);
            } else if (CONDS.equals("771")) { //Squall
                ImageViewConds.setImageResource(R.drawable.squall);


            } else if (CONDS.equals("500")) { //Light Rain
                ImageViewConds.setImageResource(R.drawable.light_rain);
            } else if (CONDS.equals("501")) { //Moderate Rain
                ImageViewConds.setImageResource(R.drawable.rain_night);
            } else if (CONDS.equals("502")) { //Heavy Rain
                ImageViewConds.setImageResource(R.drawable.rain_night);
            } else if (CONDS.equals("503")) { // Very Heavy Rain
                ImageViewConds.setImageResource(R.drawable.very_heavy_rain_night);
            } else if (CONDS.equals("504")) { // Extreme Rain
                ImageViewConds.setImageResource(R.drawable.extreme_rain_night);
            } else if (CONDS.equals("511")) { // Freezing Rain
                ImageViewConds.setImageResource(R.drawable.freezing_rain);
            } else if (CONDS.equals("520")) { // Light intensity shower Rain
                ImageViewConds.setImageResource(R.drawable.light_rain_shower_night);
            } else if (CONDS.equals("521")) { // Shower Rain
                ImageViewConds.setImageResource(R.drawable.scattered_showers_night);
            } else if (CONDS.equals("522")) { // Heavy intensity Shower Rain
                ImageViewConds.setImageResource(R.drawable.heavy_shower_rain_night);
            } else if (CONDS.equals("531")) { // Ragged Shower Rain
                ImageViewConds.setImageResource(R.drawable.ragged_shower_rain_night);

            } else if (CONDS.equals("300")) { // Light Intensity Drizzle
                ImageViewConds.setImageResource(R.drawable.light_drizzle);
            } else if (CONDS.equals("301")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle);
            } else if (CONDS.equals("302")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle);
            } else if (CONDS.equals("310")) { // Drizzle Rain
                ImageViewConds.setImageResource(R.drawable.drizzle_rain);
            } else if (CONDS.equals("312")) { // Drizzle Rain Heavy
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
            } else if (CONDS.equals("313")) { // Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle_rain);
            } else if (CONDS.equals("314")) { // Heavy Shower Rain And Drizzle
                ImageViewConds.setImageResource(R.drawable.heavy_intensity_drizzle_rain);
            } else if (CONDS.equals("321")) { // Shower Drizzle
                ImageViewConds.setImageResource(R.drawable.drizzle);


            } else if (CONDS.equals("600")) { // Light Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_snow);
            } else if (CONDS.equals("601")) { // Snow
                ImageViewConds.setImageResource(R.drawable.snow_day);
            } else if (CONDS.equals("602")) { // HeavySnow
                ImageViewConds.setImageResource(R.drawable.heavy_snow);
            } else if (CONDS.equals("611")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.sleet_day);
            } else if (CONDS.equals("612")) { // Light Shower Sleet
                ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
            } else if (CONDS.equals("613")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.shower_sleet);
            } else if (CONDS.equals("615")) { // Light Rain And Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_sleet);
            } else if (CONDS.equals("616")) { // Sleet
                ImageViewConds.setImageResource(R.drawable.sleet_day);
            } else if (CONDS.equals("620")) { // Light Shower Snow
                ImageViewConds.setImageResource(R.drawable.light_shower_snow);
            } else if (CONDS.equals("621")) { // Shower Snow
                ImageViewConds.setImageResource(R.drawable.snow_day);
            } else if (CONDS.equals("622")) { // Sleet



                ImageViewConds.setImageResource(R.drawable.heavy_snow);
            } else if (CONDS.equals("200")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
            } else if (CONDS.equals("201")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_day);
            } else if (CONDS.equals("202")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_rain);
            } else if (CONDS.equals("210")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("211")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("212")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.thunder_day);
            } else if (CONDS.equals("221")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_rain);
            } else if (CONDS.equals("230")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_light_drizzle);
            } else if (CONDS.equals("231")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_drizzle);
            } else if (CONDS.equals("232")) { // ThunderStorm
                ImageViewConds.setImageResource(R.drawable.tstorm_with_heavy_drizzle);
            }
        }
    }

}
