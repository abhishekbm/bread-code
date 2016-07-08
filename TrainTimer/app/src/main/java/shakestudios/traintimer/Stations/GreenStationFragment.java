package shakestudios.traintimer.Stations;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import shakestudios.traintimer.ListAdapter.TimingListViewAdpter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.TimeSplitterGreen;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GreenStationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GreenStationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GreenStationFragment extends Fragment {


    TimingListViewAdpter listAdapter;
    ExpandableListView expListView;
    Handler handler;
    List<String> listDataHeader;
    HashMap<String, List<Calendar>> listDataChild;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GreenStationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GreenStationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GreenStationFragment newInstance(String param1, String param2) {
        GreenStationFragment fragment = new GreenStationFragment();
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

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_green_station, container, false);
/*
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp1);*/

        final TextView timer = (TextView) rootView.findViewById(R.id.timer);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerGreen);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(rootView.getContext(), R.layout.support_simple_spinner_dropdown_item);
        adapter1.add("Nagasandra");
        adapter1.add("Dasarahalli");
        adapter1.add("Jalahalli");
        adapter1.add("Peenya Industry");
        adapter1.add("Peenya");
        adapter1.add("Yeshwanthpur Industry");
        adapter1.add("Yeshwanthpur");
        adapter1.add("Yesvantpur railway station");
        adapter1.add("Sandal Soap Factory");
        adapter1.add("Mahalakshmi");
        adapter1.add("Rajajinagar");
        adapter1.add("Kuvempu Road");
        adapter1.add("Srirampura");
        adapter1.add("Sampige Road");
        spinner.setAdapter(adapter1);
      /*  handler = new Handler();*/
        // Define the code block to be executed

       /* listAdapter = new TimingListViewAdpter(rootView.getContext(), listDataHeader, listDataChild);*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prepareListData("first");
                String selected = spinner.getSelectedItem().toString();
                if (listDataChild.containsKey(selected)) {
                    List<Calendar> list = listDataChild.get(selected);

                    Calendar cal = list.get(0);
                    String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
                    String minute = String.valueOf(cal.get(Calendar.MINUTE));
                    StringBuilder builder = new StringBuilder();
                    builder.append(hour).append(" : ").append(minute);

                    timer.setText(builder.toString());
                } else {
                    Snackbar.make(rootView, "Please Choose a line", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     /*   // setting list adapter
        expListView.setAdapter(listAdapter);

// Start the initial runnable task by posting through the handler

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
              *//*  Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*//*
                return false;
            }
        });*//*
        boolean fla = handler.post(runnableCode);*/
        return rootView;
    }
/*
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            // Do something here on the main thread
            Calendar cal = Calendar.getInstance();
            Calendar first = Calendar.getInstance();
            first.set(Calendar.HOUR_OF_DAY, 8);
            if (cal.after(first)) {

                prepareListData("first");
                HashMap<String, List<Calendar>> newdata = method(listDataChild);
                listAdapter = new TimingListViewAdpter(rootView.getContext(), listDataHeader, newdata);

                // setting list adapter
                expListView.setAdapter(listAdapter);
            }
            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, 2000000);
        }
    };*/

    private HashMap<String, List<Calendar>> method(HashMap<String, List<Calendar>> listDataChild) {

        ArrayList<List<Calendar>> lisIterator = new ArrayList<List<Calendar>>(listDataChild.values());
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < lisIterator.size(); i++) {
            List<Calendar> timings = lisIterator.get(i);
            if (cal.after(timings.get(0))) {

                Calendar current = timings.get(0);
                current.add(Calendar.MINUTE, 7);
                timings.remove(0);
                timings.add(current);
            }

            listDataChild.put(listDataHeader.get(i), timings);
        }

        return listDataChild;
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

    private void prepareListData(String flag) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Calendar>>();

        JSONObject response = getJSON(flag);
        Iterator it = response.keys();
        while (it.hasNext()) {
            String station = (String) it.next();
            listDataHeader.add(station);
        }

/*        Calendar cal = Calendar.getInstance();*/
        for (int i = 0; i < listDataHeader.size(); i++) {

            try {

                Date date = new Date();
                date.setTime(response.getLong(listDataHeader.get(i)));
                Calendar cal = DateToCalendar(date);
                List<Calendar> timings = new ArrayList<Calendar>();
                timings.add(cal);
                listDataChild.put(listDataHeader.get(i), timings);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    // Create the Handler object (on the main thread by default)
    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private JSONObject getJSON(String flag) {


        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        TimeSplitterGreen purple = new TimeSplitterGreen();
        HashMap<String, Long> timings;
        if ("first".equalsIgnoreCase(flag)) {
            Calendar first = Calendar.getInstance();
            first.set(Calendar.HOUR_OF_DAY, 8);
            first.set(Calendar.MINUTE, 00);
            int diff = (int) ((cal.getTimeInMillis() / 60000) - (first.getTimeInMillis() / 60000));

            int number = diff / 7;

            int minutes = (int) (7 * (Math.ceil(Math.abs(diff / 7))));

            first.add(Calendar.MINUTE, minutes);
            if (first.before(cal)) {
                int diff1 = (int) ((cal.getTimeInMillis() / 60000) - (first.getTimeInMillis() / 60000));
                cal.add(Calendar.MINUTE, diff1);
                timings = purple.TimeSplitterGreen(new Timestamp(cal.getTimeInMillis()));
            } else {

                timings = purple.TimeSplitterGreen(new Timestamp(first.getTimeInMillis()));
            }
        } else {
            timings = purple.TimeSplitterGreen(new Timestamp(cal.getTimeInMillis()));
        }

        List<String> stations = new ArrayList<String>();
        stations.add("Nagasandra");
        stations.add("Dasarahalli");
        stations.add("Jalahalli");
        stations.add("Peenya Industry");
        stations.add("Peenya");
        stations.add("Yeshwanthpur Industry");
        stations.add("Yeshwanthpur");
        stations.add("Yesvantpur railway station");
        stations.add("Sandal Soap Factory");
        stations.add("Mahalakshmi");
        stations.add("Rajajinagar");
        stations.add("Kuvempu Road");
        stations.add("Srirampura");
        stations.add("Sampige Road");
      /*  Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if ("southNorth".equalsIgnoreCase(key)) {
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
