package shakestudios.traintimer.Stations;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import shakestudios.traintimer.ListAdapter.TimingListViewAdpter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.TimeSplitterPurple;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PurpleStationFragments.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PurpleStationFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurpleStationFragments extends Fragment {

    TimingListViewAdpter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PurpleStationFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurpleStationFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static PurpleStationFragments newInstance(String param1, String param2) {
        PurpleStationFragments fragment = new PurpleStationFragments();
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
        View rootView = inflater.inflate(R.layout.fragment_purple_station_fragments, container, false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp1);

        // preparing list data

        prepareListData();

        listAdapter = new TimingListViewAdpter(rootView.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
              /*  Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
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


    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        JSONObject response = getJSON();
        Iterator it = response.keys();
        while (it.hasNext()) {
            String station = (String) it.next();
            listDataHeader.add(station);
        }


        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < listDataHeader.size(); i++) {

            try {
                cal.setTimeInMillis(response.getLong(listDataHeader.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);
            StringBuilder build = new StringBuilder();
            build.append(hour).append(":").append(minute);
            List<String> timings = new ArrayList<String>();
            timings.add(build.toString());
            listDataChild.put(listDataHeader.get(i), timings);
        }
    }

    private JSONObject getJSON() {

        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        TimeSplitterPurple purple = new TimeSplitterPurple();
        HashMap<String, Long> timings = purple.TimeSplitterPurple(new Timestamp(cal.getTimeInMillis()));
        List<String> stations = new ArrayList<String>();
        stations.add("Byappanhalli");
        stations.add("Swami Vivekananda Road");
        stations.add("Indiranagar");
        stations.add("Halasuru");
        stations.add("Trinity");
        stations.add("Mahatma Gandhi Road");
        stations.add("Cubbon Park");
        stations.add("Vidhana Soudha");
        stations.add("Sir M. Visveshwaraya");
        stations.add("Majestic");
        stations.add("City Railway Station");
        stations.add("Magadi Road");
        stations.add("Hosahalli");
        stations.add("Vijayanagar");
        stations.add("Attiguppe");
        stations.add("Deepanjali Nagar");
        stations.add("Mysore Road");
        /*Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if("eastToWest".equalsIgnoreCase(key)){
            Collections.reverse(stations);
        }*/
        try {

            for (int i = 0; i < stations.size(); i++) {
                String j = String.valueOf(i + 1);
                json.put(stations.get(i), timings.get(j));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
