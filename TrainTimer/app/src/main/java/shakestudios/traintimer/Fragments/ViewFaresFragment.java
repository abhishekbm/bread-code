package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import shakestudios.traintimer.R;
import shakestudios.traintimer.Stations.GreenStationFragment;
import shakestudios.traintimer.Stations.PurpleStationFragments;
import shakestudios.traintimer.ValueObjects.FaresVO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewFaresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewFaresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFaresFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewFaresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewFaresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewFaresFragment newInstance(String param1, String param2) {
        ViewFaresFragment fragment = new ViewFaresFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_view_fares, container, false);

        getActivity().setTitle("Fares");
        TextView card, coin, noOfStations, timeTaken, origin;

        card = (TextView) rootView.findViewById(R.id.textView3);
        coin = (TextView) rootView.findViewById(R.id.textView4);


        TextView cardfare = (TextView) rootView.findViewById(R.id.cardfare);
        TextView coinfare = (TextView) rootView.findViewById(R.id.coinfare);

        TextView stationInfo = (TextView) rootView.findViewById(R.id.staioninfo);


        origin = (TextView) rootView.findViewById(R.id.origin);

        final Bundle bundle = this.getArguments();
        String from = bundle.getString("from");
        String to = bundle.getString("to");
        String line = bundle.getString("line");
        FaresVO vo = new FaresVO();
        origin.setText(from + " - " + to);
        origin.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

        origin.setTypeface(Typeface.DEFAULT_BOLD);
        List<String> list = vo.getFares(from, to, line,getActivity().getApplicationContext());
        card.setTypeface(Typeface.DEFAULT_BOLD);
        coin.setTypeface(Typeface.DEFAULT_BOLD);
        card.setTextSize(30);
        coin.setTextSize(30);
        card.setText("Token cost ");
        coin.setText("Varshik Card ");

        cardfare.setText(getResources().getString(R.string.rs) + " " + list.get(0));
        coinfare.setText(getResources().getString(R.string.rs) + " " + list.get(1));

        cardfare.setTypeface(Typeface.DEFAULT_BOLD);
        coinfare.setTypeface(Typeface.DEFAULT_BOLD);
        cardfare.setTextSize(30);
        coinfare.setTextSize(30);
        noOfStations = (TextView) rootView.findViewById(R.id.noOfStations);

        noOfStations.setTextSize(18);
        noOfStations.setText("Number of stations to Destination ");
        noOfStations.setTypeface(Typeface.DEFAULT_BOLD);
        stationInfo.setText(":           " + list.get(3));
        stationInfo.setTypeface(Typeface.DEFAULT_BOLD);
        stationInfo.setTextSize(18);

        timeTaken = (TextView) rootView.findViewById(R.id.timeTaken);

        timeTaken.setText("Doors open on "+list.get(2));


        Button timingButton = (Button) rootView.findViewById(R.id.timefareButton);

        timingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    String from = bundle.getString("from");
                    String to = bundle.getString("to");
                    String line = bundle.getString("line");

                    if ("green".equalsIgnoreCase(line)) {
                        GreenStationFragment fragment = new GreenStationFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        PurpleStationFragments fragment = new PurpleStationFragments();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.event_frame, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

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
