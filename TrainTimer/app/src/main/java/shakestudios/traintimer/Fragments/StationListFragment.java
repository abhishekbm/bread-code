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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StationListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StationListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StationListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StationListFragment newInstance(String param1, String param2) {
        StationListFragment fragment = new StationListFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_station_list, container, false);
        getActivity().setTitle("Stations");
        ListView view = (ListView) rootView.findViewById(R.id.stationList);
        TextView textView = (TextView) rootView.findViewById(R.id.station_header);
        textView.setText("Select a station to view facilities");
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        ArrayList array = new ArrayList();
        array.add("Byappanhalli");
        array.add("Swami Vivekananda Road");
        array.add("Indiranagar");
        array.add("Halasuru");
        array.add("Trinity");
        array.add("Mahatma Gandhi Road");
        array.add("Cubbon Park");
        array.add("Vidhana Soudha");
        array.add("Sir M. Visveshwaraya");
        array.add("City Railway Station");
        array.add("Magadi Road");
        array.add("Hosahalli");
        array.add("Vijayanagar");
        array.add("Attiguppe");
        array.add("Deepanjali Nagar");
        array.add("Mysore Road");

        array.add("Nagasandra");
        array.add("Dasarahalli");
        array.add("Jalahalli");
        array.add("Peenya Industry");
        array.add("Peenya");
        array.add("Yeshwanthpur Industry");
        array.add("Yeshwanthpur");
        array.add("Sandal Soap Factory");
        array.add("Mahalakshmi");
        array.add("Rajajinagar");
        array.add("Kuvempu Road");
        array.add("Srirampura");
        array.add("Sampige Road");
        array.add("Majestic (Inter Change)");
        array.add("Chickpet");
        array.add("Krishna Rajendra Market");
        array.add("National College");
        array.add("Lalbagh");
        array.add("Southend Circle");
        array.add("Jayanagar");
        array.add("Rashtreeya Vidyalaya Road");
        array.add("Banashankari");
        array.add("Jayaprakash Nagar");
        array.add("Puttenahalli");
        Collections.sort(array);
        ArrayAdapter array1 = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, array);
        view.setAdapter(array1);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stationName = (String) parent.getItemAtPosition(position);

                Station_detail_fragment fragment = new Station_detail_fragment();

                Bundle bundle = new Bundle();
                bundle.putString("station", stationName);
                fragment.setArguments(bundle);
                replaceFragment(fragment);

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
