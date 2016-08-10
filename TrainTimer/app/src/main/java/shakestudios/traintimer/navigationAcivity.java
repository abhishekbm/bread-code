package shakestudios.traintimer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import shakestudios.traintimer.Fragments.About;
import shakestudios.traintimer.Fragments.FaresFragment;
import shakestudios.traintimer.Fragments.GreenLineFragment;
import shakestudios.traintimer.Fragments.ParkingFragment;
import shakestudios.traintimer.Fragments.PurpleLineFragment;
import shakestudios.traintimer.Fragments.TimingsFragment;
import shakestudios.traintimer.Fragments.ViewFaresFragment;
import shakestudios.traintimer.Stations.GreenStationFragment;
import shakestudios.traintimer.Stations.PurpleStationFragments;

public class navigationAcivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GreenLineFragment.OnFragmentInteractionListener, GreenStationFragment.OnFragmentInteractionListener
        , PurpleLineFragment.OnFragmentInteractionListener, PurpleStationFragments.OnFragmentInteractionListener, TimingsFragment.OnFragmentInteractionListener
        , FaresFragment.OnFragmentInteractionListener, ViewFaresFragment.OnFragmentInteractionListener, main_fragment.OnFragmentInteractionListener, About.OnFragmentInteractionListener
        , ParkingFragment.OnFragmentInteractionListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    /*  private AdView mAdView;
  */

    private boolean doubleBackToExitPressedOnce = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_acivity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // doMySearch(query);
        }
        main_fragment fragment = new main_fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.event_frame, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // getSupportFragmentManager().addOnBackStackChangedListener(getListener());

    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.timingfrag);
                if (currentFragment instanceof TimingsFragment) {
                    currentFragment.onResume();
                }
            }
        };

        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Checking for fragment count on backstack
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_acivity, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            About fragment = new About();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.event_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Context context = this;
        int id = item.getItemId();

        if (id == R.id.Timings) {
            TimingsFragment fragment = new TimingsFragment();
            replaceFragment(fragment);
          /*  android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.event_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/

        } else if (id == R.id.Fares) {
            FaresFragment fragment = new FaresFragment();
            replaceFragment(fragment);
           /* android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.event_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/

        } else if (id == R.id.Parking) {
            ParkingFragment fragment = new ParkingFragment();
            replaceFragment(fragment);
         /*   android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.event_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }*/
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.event_frame, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    /* @Override
     public void onPause() {
         if (mAdView != null) {
             mAdView.pause();
         }
         super.onPause();
     }
     @Override
     public void onResume() {
         super.onResume();
         if (mAdView != null) {
             mAdView.resume();
         }
     }

     @Override
     public void onDestroy() {
         if (mAdView != null) {
             mAdView.destroy();
         }
         super.onDestroy();
     }*/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
