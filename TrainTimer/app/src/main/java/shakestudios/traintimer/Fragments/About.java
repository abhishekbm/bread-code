package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import com.google.android.gms.ads.AdView;

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


    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<String> adapter;

   // private AdView mAdView;

    public About() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static About newInstance(String param1, String param2) {
        About fragment = new About();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootViw = inflater.inflate(R.layout.fragment_about, container, false);


/*
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
*/



        /*mAdView = (AdView) rootViw.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D856599299D1B987A5902D9FC9B85641")
                .build();

        mAdView.loadAd(adRequest);*/

        getActivity().setTitle("About");
        ListView text = (ListView) rootViw.findViewById(R.id.About);
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);

        adapter.add("All data provided by this application is subject to change.");
        adapter.add("The developer is not responsible for any loss or damage of any sort.");
        adapter.add("Suggestions are welcome.");
        adapter.add("Mail us at: abhishek_bm@yahoo.com");
        adapter.add("Version: 2.3.0");
        text.setAdapter(adapter);
        text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                if(item.equalsIgnoreCase("Mail us at: abhishek_bm@yahoo.com")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("message/rfc822");
                    Uri data = Uri.parse("mailto:abhishek_bm@yahoo.com?subject=" + "Suggestions for My Metro" + "&body=" + "");
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });
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
