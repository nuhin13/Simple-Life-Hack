package com.nuhin13.com.SimpleLifeHack;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<AlbumOfPlaylist> albumList;
    private DrawerLayout mDrawerLayout;
    private AdView ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ad = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);
        initAd();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menubar1, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.one:
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.nuhin13.com.SimpleLifeHack"));
                                startActivity(intent);

                                return true;
                            case R.id.two:
                                Intent intentMore = new Intent(Intent.ACTION_VIEW);
                                intentMore.setData(Uri.parse("http://play.google.com/store/apps/dev?id=6260928650655670392"));
                                startActivity(intentMore);

                                return true;

                            case R.id.three:
                                Facebook fb = new Facebook();
                                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                String facebookUrl = fb.getFacebookPageURL(MainActivity.this);
                                facebookIntent.setData(Uri.parse(facebookUrl));
                                startActivity(facebookIntent);
                                return true;
                            default:
                                Toast.makeText(getBaseContext(), "Undefined Click", Toast.LENGTH_SHORT).show();
                        }
                        // TODO: handle navigation

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewforplaylist);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.co1).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }




    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.y_o,
                R.drawable.mrgear,
                R.drawable.thaitrick,
                R.drawable.min_5,
                R.drawable.wearex,
                R.drawable.lxg_design,
                R.drawable.sago,
                R.drawable.indian_life_hack1,
                R.drawable.life_hack_icon1,
                R.drawable.slivki_show,
                R.drawable.amr_mci,
                R.drawable.cray_russian,
                R.drawable.american_hackers1,
                R.drawable.be_amazed1,
                R.drawable.wengie2,
                R.drawable.life_hack_icon1,
                R.drawable.vinesrb1,
                R.drawable.list251,
                R.drawable.adrienne_finch,
                R.drawable.newkew,
                R.drawable.protect_ur_mind,
                R.drawable.adology,
                R.drawable.mr_hacke1r,
                R.drawable.made_my_day

        };

        AlbumOfPlaylist a = new AlbumOfPlaylist("American Hacker", "300+", covers[12]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Yuri Ostr", "250+", covers[0]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Mr.Gear", "200+", covers[1]);
        albumList.add(a);

        a = new AlbumOfPlaylist("5-Minute Crafts", "200+", covers[3]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Indian Life Hack", "82", covers[7]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Crazy Russian Hacker", "900+", covers[11]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Thaitrick", "150+", covers[2]);
        albumList.add(a);

        a = new AlbumOfPlaylist("WEAREX", "140+", covers[4]);
        albumList.add(a);

        a = new AlbumOfPlaylist("List25", "1000+", covers[17]);
        albumList.add(a);

        a = new AlbumOfPlaylist("LXG Design", "200+", covers[5]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Mr Sagoo", "42", covers[6]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Wow Show", "70", covers[8]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Slivki Show EN", "60", covers[9]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Amr Mci", "80", covers[10]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Be Amazed", "150+", covers[13]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Wengie", "250+", covers[14]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Household Hacker", "400+", covers[15]);
        albumList.add(a);

        a = new AlbumOfPlaylist("VINESRB", "100+", covers[16]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Adrienne Finch", "50+", covers[18]);
        albumList.add(a);

        a = new AlbumOfPlaylist("New Kew", "100+", covers[19]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Project your mind", "50+", covers[20]);
        albumList.add(a);

        a = new AlbumOfPlaylist("ADDYOLOGY", "50", covers[21]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Mr. Hacker", "47", covers[22]);
        albumList.add(a);

        a = new AlbumOfPlaylist("Made My Day", "800+", covers[23]);
        albumList.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit the program", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
            // Toast.makeText(MainActivity.this, count, Toast.LENGTH_SHORT)
            // .show();
            displayAd();

        }
    }

    private void initAd() {
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        // Defined in values/strings.xml
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstatial_ad_unit_id));
    }

    private void displayAd() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }
    }

    private void LoadAdd() {
        // Hide the retry button, load the ad, and start the timer.

        // initAd();
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        // Toast.makeText(MainActivity.this, "loading",
        // Toast.LENGTH_SHORT).show();
    }

}

