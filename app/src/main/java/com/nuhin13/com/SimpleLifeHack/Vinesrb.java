package com.nuhin13.com.SimpleLifeHack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nuhin13 on 2/25/2017.
 */
public class Vinesrb extends AppCompatActivity {
    private static final String[] YOUTUBE_PLAYLISTS = {
            "PLyeM3AVKaJfOEXpOYAJXL3YeGMfn6-yDi",
            "PLyeM3AVKaJfPFEb41YQiJxarcoAHXDr-e"
            // "9ikuGsZOrlU", "i5aXHtibS3Q", "kiqqqoBAsNw","gLB-flAtVPo", "a7HCVmpSP4Y","Vf93mgo8rto","5x4WCButw2U","3f_xexfmiJc","EQRMjAy0TmI"
    };
    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    ProgressDialog mProgress;

    public boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        setTitle("Vinesrb");


        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_48dp));

            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        AdView ad = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);

        initAd();

        mProgress = ProgressDialog.show(this, "Loading", "Please Wait.......");
        mProgress.setCanceledOnTouchOutside(true);

        if(internet_connection()){

            if (ApiKey.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                        .setTitle("Missing API Key")
                        .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else if (savedInstanceState == null) {
                if(isOnline()) {
                    mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                            .setApplicationName(getResources().getString(R.string.app_name))
                            .build();

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                            .commit();

                    //Toast.makeText(getApplicationContext(),"internet", Toast.LENGTH_SHORT).show();
                }else{
                    //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "No Internet Available",
                            Snackbar.LENGTH_INDEFINITE);
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorAccent));
                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //recheck internet connection and call DownloadJson if there is internet
                            onBackPressed();
                        }
                    }).show();

                }
            }
        }else{
            //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection",
                    Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.colorAccent));
            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //recheck internet connection and call DownloadJson if there is internet
                    onBackPressed();
                }
            }).show();
            flag=false;}

        if(flag==false){
            if (mProgress.isShowing()) {
                mProgress.dismiss();
            }
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mProgress.dismiss();
                }
            }, 3000);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if((mProgress != null) && mProgress.isShowing() ){
            mProgress.dismiss();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.you_tube, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(getResources().getString(R.string.rate_this_app_link)));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean internet_connection(){
        //Check if connected to internet, output accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public Boolean isOnline() {

        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com/").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(500); //choose your own timeframe
            urlc.setReadTimeout(500); //choose your own timeframe
            urlc.connect();
            int networkcode2 = urlc.getResponseCode();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e)
        {
            return (false);  //connectivity exists, but no internet.
        }
    }


    int count = 1;

    InterstitialAd mInterstitialAd;

    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

        if (mInterstitialAd.isLoaded() == false) {
            LoadAdd();
        }
        // Get the Camera instance as the activity achieves full user focu
        if (count ==1) {
            displayAd();

        }
    }

    private void initAd() {
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(this);
        // Defined in values/strings.xml
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstatial_ad_unit_id));
        //mInterstitialAd.setAdUnitId("ca-app-pub-9971154848057782/1986029758");
    }

    private void displayAd() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }
    }

    private void LoadAdd() {
        // initAd();
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

}
