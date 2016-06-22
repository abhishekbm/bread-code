package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link About.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link About#newInstance} factory method to
 * create an instance of this fragment.
 */
public class About extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<String> adapter;

    private AdView mAdView;

    public About() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment About.
     */
    // TODO: Rename and change types and number of parameters
    public static About newInstance(String param1, String param2) {
        About fragment = new About();
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
        // Inflate the layout for this fragment
        View rootViw = inflater.inflate(R.layout.fragment_about, container, false);


// Here you'll append the new AdView
        final AdView adView = (AdView) rootViw.findViewById(R.id.adView);
        final AdRequest.Builder adReq = new AdRequest.Builder();
// You should include a line like this for testing purposes,
// but only after you've tested whether your AdView works!
// This will prevent your ad being loaded each time you test
// your ad, so it will prevent you being blocked from AdMob.
// You'll find your device_id in the LogCat.
        adReq.addTestDevice("D856599299D1B987A5902D9FC9B85641");

        final AdRequest adRequest = adReq.build();
        adView.loadAd(adRequest);



        /*mAdView = (AdView) rootViw.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D856599299D1B987A5902D9FC9B85641")
                .build();

        mAdView.loadAd(adRequest);*/

        getActivity().setTitle("About");
        ListView text = (ListView) rootViw.findViewById(R.id.About);
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);
        adapter.add("Version 1.0.0");
        adapter.add("All data provided by this application is subject to change and the developer is not responsible for any damage of any sort.");
        adapter.add("Suggestions are welcome email us at abhishek_bm@yahoo.com");
        text.setAdapter(adapter);
        return rootViw;
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
}
