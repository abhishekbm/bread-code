package shakestudios.musicplayer.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import shakestudios.musicplayer.MainScreenActivity;
import shakestudios.musicplayer.POJO.MusicController;
import shakestudios.musicplayer.R;
import shakestudios.musicplayer.Service.MusicService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link artistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link artistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class artistFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> artistList;
    private ListView songView;
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

    public artistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment artistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static artistFragment newInstance(String param1, String param2) {
        artistFragment fragment = new artistFragment();
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

        this.getActivity().setTitle("Artists");
        activity = ((MainScreenActivity) getActivity());
        rootView = inflater.inflate(R.layout.fragment_artist, container, false);
        musicSrv = activity.myServices();
        musicBound = true;
        songView = (ListView) rootView.findViewById(R.id.song_list);

        //instantiate list
        artistList = new ArrayList<String>();
        //get songs from device
        getArtistList();
        //sort alphabetically by title
        Collections.sort(artistList, new Comparator<String>() {
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        //create and set adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, artistList);
        songView.setAdapter(adapter);
        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paused = true;
                playbackPaused = true;
                String artist = adapter.getItem(position);
                artistPicked(view, artist);
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

    public void artistPicked(View view, String artist) {

      /*  musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.setController(controller);
        musicSrv.playSong();
        height = songView.getHeight();
        itemHeight = songView.getChildAt(0).getHeight();
        songView.setSelectionFromTop(musicSrv.currentSongPosition(), height / 2 - itemHeight / 2);
        if (playbackPaused) {
            playbackPaused = false;
        }
*/

        String selection = MediaStore.Audio.Media.ARTIST + "= ?";
        String[] selectionArgs = {artist};
        String[] projection = {MediaStore.Audio.Media.ALBUM};

        List<String> albumList = new ArrayList<>();
        Cursor musicCursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            do {
                String thisAlbum = musicCursor.getString(titleColumn);
                if (!(albumList.contains(thisAlbum))) {
                    albumList.add(thisAlbum);
                }
            }
            while (musicCursor.moveToNext());
           /* int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);*/
            //add songs to list
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, albumList);
        songView.setAdapter(adapter);
        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = adapter.getItem(position);
                albumFragment fragment = new albumFragment();
                Bundle bundle1 =new Bundle();
                bundle1.putString("albumName",title);
                fragment.setArguments(bundle1);
                replaceFragment(fragment);
            }
        });
    }

    public Cursor getAlbumCursor(Context context, Cursor cursor) {
        String where = null;
        ContentResolver cr = context.getContentResolver();
        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String album_id = MediaStore.Audio.Albums.ALBUM_ID;/*
        final String album_name = MediaStore.Audio.Albums.ALBUM;*/
        final String artist = MediaStore.Audio.Albums.ARTIST;
        final String[] columns = {_id, artist};
        cursor = cr.query(uri, columns, where, null, null);
        return cursor;
    }
    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.event_frame, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    public void getArtistList() {
        //query external audio
        ContentResolver musicResolver = getActivity().getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = null;
        musicCursor = getAlbumCursor(getActivity().getApplicationContext(), musicCursor);
        //iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
           /* int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);*/
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                // String thisAlbum= musicCursor.getString(albumColumn);
                if (!(artistList.contains(thisArtist))) {
                    artistList.add(thisArtist);
                }
            }
            while (musicCursor.moveToNext());
        }
    }

}
