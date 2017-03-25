package shakestudios.musicplayer.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import shakestudios.musicplayer.MainScreenActivity;
import shakestudios.musicplayer.POJO.MusicController;
import shakestudios.musicplayer.POJO.Song;
import shakestudios.musicplayer.R;

/**
 * Created by abbm on 3/9/2017.
 */
public class MusicService extends Service implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener

{
    //media player
    private MediaPlayer player;
    //song list
    private ArrayList<Song> songs;
    //current position
    private int songPosn;
    //binder
    private final IBinder musicBind = new MusicBinder();
    //title of current song
    private String songTitle = "";

    private String artist;
    //notification id
    private static final int NOTIFY_ID = 1;
    //shuffle flag and random
    private boolean shuffle = true;

    MusicController controller;
    long currSong;

    public void onCreate() {
        //create the service
        super.onCreate();
        //initialize position
        songPosn = 0;
        //create player
        player = new MediaPlayer();
        //initialize
        initMusicPlayer();
    }

    public MediaPlayer getPlayer() {
        if (player == null) {

            initMusicPlayer();
        }

        return player;
    }

    public void initMusicPlayer() {
        //set player properties
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        //set listeners
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    //pass song list
    public void setList(ArrayList<Song> theSongs) {
        songs = theSongs;
    }

    public void setController(MusicController contorl) {
        controller = contorl;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        player.start();
        controller.show();
        Intent notIntent = new Intent(getApplicationContext(), MainScreenActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notIntent.setAction(Intent.ACTION_MAIN);
        notIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendInt = PendingIntent.getActivity(getApplicationContext(), 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        String message = songTitle + "\n" + artist;
        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(message))
                .setContentText(message);

        Notification not = builder.build();
        not.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        Intent onPreparedIntent = new Intent("MEDIA_PLAYER_PREPARED");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(onPreparedIntent);
        startForeground(NOTIFY_ID, not);
    }

    //binder
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    //activity will bind to service
    @Override
    public IBinder onBind(Intent intent) {

        Log.e("this is in ", "bind");
        return musicBind;
    }

    //release resources when unbind
    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        player = null;
        return false;
    }

    //play a song
    public void playSong() {
        //play
        player.reset();
        //get song
        Song playSong = songs.get(songPosn);
        //get title
        songTitle = playSong.getTitle();

        artist = playSong.getArtist();
        //get id
        currSong = playSong.getID();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        //set the data source
        try {
            player.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    //set the song
    public void setSong(int songIndex) {
        songPosn = songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //check if playback has reached the end of a track
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.v("MUSIC PLAYER", "Playback Error");
        mp.reset();
        return false;
    }


    //playback methods
    public int getPosn() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPng() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
    }

    public void seek(int posn) {
        player.seekTo(posn);
    }

    public void go() {
        player.start();
    }

    //skip to previous track
    public void playPrev() {
        songPosn--;
        if (songPosn < 0) songPosn = songs.size() - 1;
        playSong();
    }

    //skip to next
    public void playNext() {

        songPosn++;
        if (songPosn >= songs.size())
            songPosn = 0;

        playSong();
    }

    public int currentSongPosition() {
        return songPosn;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    //toggle shuffle
    public ArrayList<Song> setShuffle() {
        if (shuffle) {
            shuffle = false;
            String currSongName = songs.get(songPosn).getTitle();
            Collections.shuffle(songs);
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getTitle().equalsIgnoreCase(currSongName)) {
                    songPosn = i;
                    break;
                }

            }

        } else {
            shuffle = true;
            String currSongName = songs.get(songPosn).getTitle();
            Collections.sort(songs, new Comparator<Song>() {
                public int compare(Song a, Song b) {
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getTitle().equalsIgnoreCase(currSongName)) {
                    songPosn = i;
                    break;
                }

            }

        }
        return songs;
    }


}
