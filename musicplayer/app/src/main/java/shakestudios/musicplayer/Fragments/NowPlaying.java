package shakestudios.musicplayer.Fragments;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import shakestudios.musicplayer.Adapters.SongAdapater;
import shakestudios.musicplayer.MainScreenActivity;
import shakestudios.musicplayer.POJO.MusicController;
import shakestudios.musicplayer.POJO.Song;
import shakestudios.musicplayer.R;
import shakestudios.musicplayer.Service.MusicService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NowPlaying.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NowPlaying#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlaying extends Fragment implements MediaController.MediaPlayerControl {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Song> songList;
    private ListView songView;
    private BroadcastReceiver onPrepareReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            if (i.getAction().equalsIgnoreCase("MEDIA_PLAYER_PREPARED")) {
                Log.e("error", "error");
                controller.show(0);
            }

        }
    };
    //service
    private MusicService musicSrv;
    private Intent playIntent;
    //binding
    private boolean musicBound = false;
    MainScreenActivity activity;
    //controller
    private MusicController controller;

    //activity and playback pause flags
    private boolean paused = false, playbackPaused = false;
    View rootView;
    int height, itemHeight;
    private OnFragmentInteractionListener mListener;

    public NowPlaying() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlaying.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlaying newInstance(String param1, String param2) {
        NowPlaying fragment = new NowPlaying();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getActivity().setTitle("Now Playing");
        activity = ((MainScreenActivity) getActivity());
        rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        musicSrv = activity.myServices();
        musicBound = true;
        songView = (ListView) rootView.findViewById(R.id.song_list);
        Bundle bundle = this.getArguments();
        String title = null;
        if (bundle != null) {
            title = bundle.getString("Title");
            songList = new ArrayList<Song>();
            getSongList(title);
            musicSrv.setList(songList);
            songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    paused = true;
                    playbackPaused = true;
                    songPicked(view);
                }
            });
            SongAdapater songAdt = new SongAdapater(getActivity().getApplicationContext(), songList);
            songView.setAdapter(songAdt);
            if (true) {
                songView.performItemClick(songView.getAdapter().getView(0, null, null), 0, songView.getAdapter().getItemId(0));
            }

            return rootView;
        }


        //instantiate list
        songList = new ArrayList<Song>();
        //get songs from device
        getSongList(title);
        //sort alphabetically by title
        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        //create and set adapter
        SongAdapater songAdt = new SongAdapater(getActivity().getApplicationContext(), songList);
        songView.setAdapter(songAdt);
        musicSrv.setList(songList);
        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paused = true;
                playbackPaused = true;
                songPicked(view);
            }


        });
        return rootView;
    }
    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //set the controller up
    private void setController() {
        if (controller == null) {
            controller = new MusicController(getActivity());
        }


        //set previous and next button listeners
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        //set and show
        controller.setMediaPlayer(this);
        controller.setAnchorView(rootView.findViewById(R.id.song_list));
        controller.setEnabled(true);
    }

    private void playNext() {
        musicSrv.playNext();
        int height = songView.getHeight();
        int itemHeight = songView.getChildAt(0).getHeight();
        songView.setSelectionFromTop(musicSrv.currentSongPosition(), height / 2 - itemHeight / 2);
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show();
    }

    private void playPrev() {
        musicSrv.playPrev();
        int height = songView.getHeight();
        int itemHeight = songView.getChildAt(0).getHeight();

        songView.setSelectionFromTop(musicSrv.currentSongPosition(), height / 2 - itemHeight / 2);
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    public void songPicked(View view) {
        setController();
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.setController(controller);
        musicSrv.playSong();
        height = songView.getHeight();
        // itemHeight = songView.getChildAt(0).getHeight();
        //  songView.setSelectionFromTop(musicSrv.currentSongPosition(), height / 2 - itemHeight / 2);
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }

    }


    //method to retrieve song info from device
    public void getSongList(String title) {
        if (title == null) {
            //query external audio
            ContentResolver musicResolver = getActivity().getContentResolver();
            Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
            //iterate over results if valid
            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns
                int titleColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                //add songs to list
                do {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    songList.add(new Song(thisId, thisTitle, thisArtist));
                }
                while (musicCursor.moveToNext());
            }

        } else {


            String selection = MediaStore.Audio.Media.TITLE + "= ?";
            String[] selectionArgs = {title};
            String[] projection = {MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST};

            List<String> albumList = new ArrayList<>();
            Cursor musicCursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
            if (musicCursor != null && musicCursor.moveToFirst()) {

                int titleColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                //add songs to list
                do {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    songList.add(new Song(thisId, thisTitle, thisArtist));
                }
                while (musicCursor.moveToNext());


            }
        }
    }


    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null && musicBound)
            return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public int getDuration() {
        if (musicSrv != null && musicBound)
            return musicSrv.getDur();
        else return 0;
    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicSrv.pausePlayer();
        controller.hide();
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.hide();
    }
}
