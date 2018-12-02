package shakestudios.traintimer.Stations;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import shakestudios.traintimer.ListAdapter.TimingListViewAdpter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.TimeSplitterGreen;
import shakestudios.traintimer.main_fragment;

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
    Runnable runnableCode = null;
    Calendar cal = Calendar.getInstance();
    int handlecount=0;
    String handleFlag="true";


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

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_green_station, container, false);


        final TextView timer = (TextView) rootView.findViewById(R.id.timergreen);

        final TextView boarding = (TextView) rootView.findViewById(R.id.boarding);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerGreen);

        final TextView timingsView = (TextView) rootView.findViewById(R.id.timingView);

        Bundle bundle = this.getArguments();
        final Button home = (Button) rootView.findViewById(R.id.home);
        if (null != bundle) {

            String from = bundle.getString("from");

            home.setVisibility(View.VISIBLE);
            timingsView.setVisibility(View.VISIBLE);
            home.setText("Home");
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    main_fragment fragment = new main_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                        fragmentManager.popBackStack();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.event_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            boarding.setTypeface(Typeface.DEFAULT_BOLD);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(50, 200, 0, 0);
            layoutParams2.gravity = Gravity.TOP;
            timingsView.setVisibility(View.VISIBLE);
            timingsView.setLayoutParams(layoutParams2);
            timingsView.setTypeface(Typeface.DEFAULT_BOLD);
            timingsView.setText("Next train arrives at " + from + " at ");
            timingsView.setTextSize(30);
            boarding.setVisibility(View.GONE);
            long response = prepareListData("first", "",handleFlag);

            Date date = new Date(response);

            String hour = String.valueOf(date.getHours());
            String minute = String.valueOf(date.getMinutes());
            if (Integer.parseInt(minute) < 10) {
                minute = "0" + minute;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(hour).append(" : ").append(minute);
            timer.setVisibility(View.VISIBLE);
            timer.setText(builder.toString());


            FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(250, 600, 0, 0);
            layoutParams1.gravity = Gravity.TOP;
            timer.setLayoutParams(layoutParams1);
            timer.setTypeface(Typeface.DEFAULT_BOLD);
            timer.setText(builder.toString());
            spinner.setVisibility(View.GONE);
            timer.setTextSize(45);
            return rootView;
        }

        home.setVisibility(View.GONE);
        timer.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(rootView.getContext(), R.layout.support_simple_spinner_dropdown_item);
        adapter1.add("Choose Station");
        adapter1.add("Nagasandra");
        adapter1.add("Dasarahalli");
        adapter1.add("Jalahalli");
        adapter1.add("Peenya Industry");
        adapter1.add("Peenya");
        adapter1.add("Yeshwanthpur Industry");
        adapter1.add("Yeshwanthpur");
        adapter1.add("Yesvantpur railway station");
        adapter1.add("Sandal Soap Factory (Orion Mall)");
        adapter1.add("Mahalakshmi");
        adapter1.add("Rajajinagar");
        adapter1.add("Kuvempu Road");
        adapter1.add("Srirampura");
        adapter1.add("Sampige Road");
        spinner.setAdapter(adapter1);
        handler = new Handler();

        runnableCode = new Runnable() {

            @Override
            public void run() {
                // Do something here on the main thread


                String selected = spinner.getSelectedItem().toString();
                handleFlag="false";
                long time = prepareListData("first", selected,handleFlag);

                Date date = new Date(time);

                String hour = String.valueOf(date.getHours());
                String minute = String.valueOf(date.getMinutes());
                if (Integer.parseInt(minute) < 10) {
                    minute = "0" + minute;
                }
                StringBuilder builder = new StringBuilder();
                builder.append(hour).append(" : ").append(minute);
                timer.setVisibility(View.VISIBLE);
                timer.setText(builder.toString());

                handler.postDelayed(runnableCode, 20000);
            }
        };
        handler.postDelayed(runnableCode, 20000);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (count >= 1) {
                    String from1 = (String) spinner.getSelectedItem();
                    timingsView.setVisibility(View.VISIBLE);
                    timingsView.setTypeface(Typeface.DEFAULT_BOLD);
                    timingsView.setText("Next train arrives at " + from1 + " at ");
                    timingsView.setTextSize(30);


                    FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(250, 650, 0, 0);
                    layoutParams1.gravity = Gravity.TOP;
                    timer.setLayoutParams(layoutParams1);
                    timer.setTypeface(Typeface.DEFAULT_BOLD);
                    timer.setTextColor(getResources().getColor(android.R.color.black));
                    timer.setTextSize(45);
                    String selected = spinner.getSelectedItem().toString();
                    long time = prepareListData("first", selected,handleFlag);

                    Date date = new Date(time);

                    String hour = String.valueOf(date.getHours());
                    String minute = String.valueOf(date.getMinutes());
                    if (Integer.parseInt(minute) < 10) {
                        minute = "0" + minute;
                    }
                    StringBuilder builder = new StringBuilder();
                    builder.append(hour).append(" : ").append(minute);
                    timer.setVisibility(View.VISIBLE);
                    timer.setText(builder.toString());
                   /* } else {
                        Snackbar.make(rootView, "Please Choose a line", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        timer.setVisibility(View.GONE);
                        timingsView.setVisibility(View.GONE);
                    }*/
                }

                count++;
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

/*    private HashMap<String, List<Calendar>> method(HashMap<String, List<Calendar>> listDataChild) {

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
    }*/

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

    private long prepareListData(String flag, String selectedStation, String handler) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Calendar>>();

        long response = getJSON(flag, selectedStation,handler);
       /* Iterator it = response.keys();
        while (it.hasNext()) {
            String station = (String) it.next();
            listDataHeader.add(station);
        }

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


        }*/
        return response;
    }

    // Create the Handler object (on the main thread by default)
    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private long getJSON(String flag, String selectedStation, String handler) {


        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<String>();
        if (!"false".equalsIgnoreCase(handler)) {
            cal = Calendar.getInstance();
        }
        TimeSplitterGreen purple = new TimeSplitterGreen();
        LinkedHashMap<Integer, LinkedHashMap<String, Long>> ultimate = new LinkedHashMap<>();
        LinkedHashMap<String, Long> timings;
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
                List<Long> calList = getCalenders(cal);
                Calendar cali = Calendar.getInstance();
                for (int i = 0; i < calList.size(); i++) {
                    cali.setTimeInMillis(calList.get(i));
                    timings = purple.TimeSplitterGreen(new Timestamp(cali.getTimeInMillis() + 60000));
                    ultimate.put(i, timings);
                }
                timings = purple.TimeSplitterGreen(new Timestamp(cal.getTimeInMillis() + 60000));
            } else {
                List<Long> calList = getCalenders(first);
                Calendar cali = Calendar.getInstance();
                for (int i = 0; i < calList.size(); i++) {
                    cali.setTimeInMillis(calList.get(i));
                    timings = purple.TimeSplitterGreen(new Timestamp(cali.getTimeInMillis() + 60000));
                    ultimate.put(i, timings);
                }
                timings = purple.TimeSplitterGreen(new Timestamp(first.getTimeInMillis() + 60000));
            }
        } else {
            timings = purple.TimeSplitterGreen(new Timestamp(cal.getTimeInMillis() + 60000));
        }
        LinkedHashMap<String, String> stations = new LinkedHashMap<String, String>();
        stations.put("14", "Nagasandra");
        stations.put("13", "Dasarahalli");
        stations.put("12", "Jalahalli");
        stations.put("11", "Peenya Industry");
        stations.put("10", "Peenya");
        stations.put("9", "Yeshwanthpur Industry");
        stations.put("8", "Yeshwanthpur");
        stations.put("7", "Yesvantpur railway station");
        stations.put("6", "Sandal Soap Factory");
        stations.put("5", "Mahalakshmi");
        stations.put("4", "Rajajinagar");
        stations.put("3", "Kuvempu Road");
        stations.put("2", "Srirampura");
        stations.put("1", "Sampige Road");
        String stationNumber = null;
        for (Map.Entry<String, String> entry : stations.entrySet()) {
            if (selectedStation.equalsIgnoreCase(entry.getValue())) {
                stationNumber = entry.getKey();
                break;
            }
        }
      /*  Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if ("southNorth".equalsIgnoreCase(key)) {
            Collections.reverse(stations);
        }*/
        long timereqd = 0;
        try {
            List<Long> longTimeList = new ArrayList<>();
            for (int i = 0; i < ultimate.size(); i++) {
                LinkedHashMap<String, Long> map = ultimate.get(i);
                long time = map.get(stationNumber);
                longTimeList.add(time);
            }

            Collections.sort(longTimeList);

            Date date = new Date();
            for (int j = 0; j < longTimeList.size(); j++) {
                if (longTimeList.get(j) > date.getTime()) {
                    timereqd = longTimeList.get(j);
                    break;
                }
            }

        } catch (Exception e) {
        }
        return timereqd;
    }


    private List<Long> getCalenders(Calendar cal) {
        int thirty = -38;
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.MINUTE, thirty);
        long long1 = cal.getTimeInMillis();
        long long2 = calender.getTimeInMillis();
        List<Long> calList = getCalenders(long1, long2);
        return calList;
    }

    private List<Long> getCalenders(long long1, long long2) {
        List<Long> calList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(long1);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(long2);
        int seven = 7;
        while (cal1.before(cal)) {
            calList.add(cal1.getTimeInMillis());
            cal1.add(Calendar.MINUTE, 7);
            cal1.getTimeInMillis();
        }
        return calList;
    }
}
