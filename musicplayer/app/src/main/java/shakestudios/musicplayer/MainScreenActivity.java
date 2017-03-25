package shakestudios.musicplayer;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
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

import java.util.List;

import shakestudios.musicplayer.Fragments.NowPlaying;
import shakestudios.musicplayer.Fragments.albumFragment;
import shakestudios.musicplayer.Fragments.artistFragment;
import shakestudios.musicplayer.Service.MusicService;

public class MainScreenActivity extends AppCompatActivity implements albumFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, artistFragment.OnFragmentInteractionListener, NowPlaying.OnFragmentInteractionListener {
    //song list variables

    boolean musicBound = false;
    NavigationView navigationView = null;
    Toolbar toolbar = null;
    private MusicService musicSrv;
    /*  private AdView mAdView;
  */
    public static MediaPlayer mediaPlayer;
    private ServiceConnection musicConnection;
    private Intent playIntent;
    private boolean doubleBackToExitPressedOnce = false;


    private void askforRating() {

        try {

            // Get the app's shared preferences
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            // Get the value for the run counter
            int counter = app_preferences.getInt("counter", 0);

            int runEvery = app_preferences.getInt("runEvery", 3);

            // Do every x times
            int RunEvery = 3;
            RunEvery = runEvery;
            if (counter != 0 && counter % RunEvery == 0) {
                //   Toast.makeText(this, "This app has been started " + counter + " times.", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        MainScreenActivity.this);
                alert.setTitle("Rate the application");
                alert.setIcon(R.mipmap.ic_launcher); //app icon here
                alert.setMessage("Thanks for using our app. Please take a moment to rate it and let us know your review");

                alert.setPositiveButton("Rate it",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

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
                RunEvery = runEvery;
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        });
                alert.setCancelable(false);
                alert.show();

            }

            // Increment the counter
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("counter", ++counter);
            editor.putInt("runEvery", 6);
            editor.commit(); // Very important

        } catch (Exception e) {
            //Do nothing, don't run but don't break
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {


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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Context context = this;
        int id = item.getItemId();
        if (id == R.id.NowPlaying) {
            NowPlaying fragment = new NowPlaying();
            replaceFragment(fragment);
        } else if (id == R.id.Artists) {
            artistFragment fragment = new artistFragment();
            replaceFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void removebackStackFragments() {

        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> listfrag = fm.getFragments();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        askforRating();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void pause() {
        musicSrv.pausePlayer();
    }

    public MusicService myServices() {

        return musicSrv;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            startService(playIntent);
            createMusicConnection();
            bindService(playIntent, musicConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case R.id.action_shuffle:
              /*  songList = musicSrv.setShuffle();
                SongAdapater songAdt = new SongAdapater(getBaseContext(), songList);
                songView.setAdapter(songAdt);
                songView.setSelectionFromTop(musicSrv.currentSongPosition(), height / 2 - itemHeight / 2);*/
                break;
            case R.id.action_end:
               /* stopService(playIntent);
                musicSrv = null;
                System.exit(0);*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
      /*  paused = true;
        if (musicSrv.isPng()) {
            Log.v("MUSIC PLAYER", "flag set");
            playbackPaused = true;
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  LocalBroadcastManager.getInstance(this).registerReceiver(onPrepareReceiver,
                new IntentFilter("MEDIA_PLAYER_PREPARED"));


        */
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            startService(playIntent);
            createMusicConnection();
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
        }

    }

    private void createMusicConnection() {
        musicConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                musicSrv = ((MusicService.MusicBinder) service).getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName className) {
                musicSrv = null;
            }
        };
    }

    @Override
    protected void onStop() {
        //controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
