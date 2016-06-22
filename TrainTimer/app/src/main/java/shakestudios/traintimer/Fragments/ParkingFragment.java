package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shakestudios.traintimer.ListAdapter.TimingListViewAdpter;
import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParkingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingFragment extends Fragment {


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

    public ParkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParkingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParkingFragment newInstance(String param1, String param2) {
        ParkingFragment fragment = new ParkingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_parking, container, false);
        getActivity().setTitle("Parking");
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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        String station = null;
        JSONArray response = getJSON();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject json = response.getJSONObject(i);
                station = json.getString("Station");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listDataHeader.add(station);
        }

        List<String> details = null;
        for (int i = 0; i < listDataHeader.size(); i++) {

            for (int j = 0; j < response.length(); j++) {
                try {
                    JSONObject json = response.getJSONObject(j);
                    details = new ArrayList<String>();
                    details.add("Parking " + json.getString("Parking"));
                    details.add("Covered " + json.getString("Covered"));
                    details.add("Paid " + json.getString("Paid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listDataChild.put(listDataHeader.get(i), details);
            }
        }
    }

    private JSONArray getJSON() {


        String input ="{\n\t\"parking\": [{\n\t\t\"Station\": \"Byappanhalli\",\n\t\t\"Parking\": \"Yes\",\n\t\t\"Covered\": \"No\",\n\t\t\"Paid\": \"Yes\"\n\t}, {\n\t\t\"Station\": \"Swami Vivekananda Road\",\n\t\t\"Parking\": \"Yes\",\n\t\t\"Covered\": \"No\",\n\t\t\"Paid\": \"Yes\"\n\t}, {\n\t\t\"Station\": \"Mysore Road\",\n\t\t\"Parking\": \"Yes\",\n\t\t\"Covered\": \"No\",\n\t\t\"Paid\": \"Yes\"\n\t}, {\n\t\t\"Station\": \"Magadi Road\",\n\t\t\"Parking\": \"Yes\",\n\t\t\"Covered\": \"No\",\n\t\t\"Paid\": \"Yes\"\n\t}]\n}";
        JSONObject json = null;
        JSONArray array=null;
        try {
            json = new JSONObject(input);
            array =json.getJSONArray("parking");
        } catch (JSONException e) {
            e.printStackTrace();
        }

      /*  Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if ("southNorth".equalsIgnoreCase(key)) {
            Collections.reverse(stations);
        }*/

        return array;
    }
}
