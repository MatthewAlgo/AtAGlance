package app.matthewsgalaxy.ataglance;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

import app.matthewsgalaxy.ataglance.UserInterface.AtAGlance.AtAGlanceFragment;
import app.matthewsgalaxy.ataglance.UserInterface.ExtendedForecast.extendedForecastFragment;
import app.matthewsgalaxy.ataglance.UserInterface.ExtendedHeadlines.extendedHeadlinesFragment;
import app.matthewsgalaxy.ataglance.UserInterface.LocalInformation.localInformationFragment;
import app.matthewsgalaxy.ataglance.UserInterface.SearchInterests.searchInterestsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // We open our ataglance fragment immediately
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AtAGlanceFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AtAGlanceFragment()).commit();
                break;
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new extendedHeadlinesFragment()).commit();
                break;
            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new extendedForecastFragment()).commit();
                break;
            case R.id.nav_local:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new localInformationFragment()).commit();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new searchInterestsFragment()).commit();
                break;

            case R.id.Aboutme:
                Toast.makeText(this, "Hi! I Am MatthewAlgo, A Developer And Linux Enthusiast. I Developed This App Mainly To Learn The Structure" +
                        " Of An Android Application. And To Have Fun \ud83d\ude01 ", Toast.LENGTH_LONG).show();
                break;
            case R.id.Refresh:
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                finish();
                AtAGlanceFragment.numberOfInflations=0;
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



}