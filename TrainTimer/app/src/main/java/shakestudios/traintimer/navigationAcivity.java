package shakestudios.traintimer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import shakestudios.traintimer.Fragments.About;
import shakestudios.traintimer.Fragments.CarParkingFragment;
import shakestudios.traintimer.Fragments.FaresFragment;
import shakestudios.traintimer.Fragments.GreenLineFragment;
import shakestudios.traintimer.Fragments.ParkingFragment;
import shakestudios.traintimer.Fragments.ParkingManagerFragment;
import shakestudios.traintimer.Fragments.PurpleLineFragment;
import shakestudios.traintimer.Fragments.StationListFragment;
import shakestudios.traintimer.Fragments.TimingsFragment;
import shakestudios.traintimer.Fragments.TwoWheelParkingFragment;
import shakestudios.traintimer.Fragments.ViewFaresFragment;
import shakestudios.traintimer.Fragments.newsFragment;
import shakestudios.traintimer.Fragments.rechargeFragment;
import shakestudios.traintimer.Stations.GreenStationFragment;
import shakestudios.traintimer.Stations.PurpleStationFragments;
import shakestudios.traintimer.Stations.RouteFragment;

public class navigationAcivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GreenLineFragment.OnFragmentInteractionListener, GreenStationFragment.OnFragmentInteractionListener
        , PurpleLineFragment.OnFragmentInteractionListener, PurpleStationFragments.OnFragmentInteractionListener, TimingsFragment.OnFragmentInteractionListener
        , FaresFragment.OnFragmentInteractionListener, ViewFaresFragment.OnFragmentInteractionListener, main_fragment.OnFragmentInteractionListener, About.OnFragmentInteractionListener
        , ParkingFragment.OnFragmentInteractionListener, RouteFragment.OnFragmentInteractionListener, newsFragment.OnFragmentInteractionListener, CarParkingFragment.OnFragmentInteractionListener
        , TwoWheelParkingFragment.OnFragmentInteractionListener, ParkingManagerFragment.OnFragmentInteractionListener, StationListFragment.OnFragmentInteractionListener, rechargeFragment.OnFragmentInteractionListener {

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

        askforRating();
        main_fragment fragment = new main_fragment();
        String backStateName= fragment.getClass().getName();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.event_frame, fragment);
      //  fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void askforRating() {

        try {

            // Get the app's shared preferences
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            // Get the value for the run counter
            int counter = app_preferences.getInt("counter", 0);

            // Do every x times
            int RunEvery = 10;
            if (counter != 0 && counter % RunEvery == 0) {
                //   Toast.makeText(this, "This app has been started " + counter + " times.", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        navigationAcivity.this);
                alert.setTitle("Rate the application");
                alert.setIcon(R.mipmap.ic_launcher); //app icon here
                alert.setMessage("Thanks for using the app. Please take a moment to rate it and let us know your review");

                alert.setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //Do nothing
                            }
                        });

                alert.setNegativeButton("Rate it",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                final String appName = getApplicationContext().getPackageName();
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id="
                                                    + appName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id="
                                                    + appName)));
                                }

                            }
                        });
                alert.show();

            }

            // Increment the counter
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("counter", ++counter);
            editor.commit(); // Very important

        } catch (Exception e) {
            //Do nothing, don't run but don't break
        }
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
                Toast.makeText(this, "Press BACK again to exit.", Toast.LENGTH_SHORT).show();

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
            RouteFragment fragment = new RouteFragment();
            replaceFragment(fragment);

        } else if (id == R.id.Fares) {
            FaresFragment fragment = new FaresFragment();
            replaceFragment(fragment);

        } else if (id == R.id.Parking) {
            ParkingFragment fragment = new ParkingFragment();
            replaceFragment(fragment);

        } else if (id == R.id.Home) {

            removebackStackFragments();
            main_fragment fragment = new main_fragment();
            replaceFragment(fragment);

        } else if (id == R.id.HelpLine) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1800-425-12345"));
            startActivity(intent);
        } else if (id == R.id.EmailBMRCL) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("message/rfc822");
            Uri data = Uri.parse("mailto:travelhelp@bmrc.co.in?subject=" + "Suggestion for Better Travel" + "&body=" + "");
            intent.setData(data);
            startActivity(intent);
        } else if (id == R.id.emailDev) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("message/rfc822");
            Uri data = Uri.parse("mailto:abhishek_bm@yahoo.com?subject=" + "Suggestions for My Metro" + "&body=" + "");
            intent.setData(data);
            startActivity(intent);
        } else if (id == R.id.faq) {
            ParkingFragment fragment = new ParkingFragment();
            replaceFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removebackStackFragments() {

        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> listfrag = fm.getFragments();

        for (int i = 0; i < listfrag.size(); i++) {
            if (listfrag.get(i) instanceof main_fragment) {

            } else {
                fm.popBackStack();
            }
        }
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
