package shakestudios.traintimer.Stations;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RouteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static LinkedHashMap<String, String> purpleLine = new LinkedHashMap<String, String>();
    private static LinkedHashMap<String, String> greenLine = new LinkedHashMap<String, String>();
    private LinkedHashMap<Integer, String> stationsPurple = new LinkedHashMap<Integer, String>();
    SimpleAdapter adapter;
    private LinkedHashMap<Integer, String> stationsGreen = new LinkedHashMap<Integer, String>();


    private ArrayAdapter<String> routeStations;
    private OnFragmentInteractionListener mListener;

    public RouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteFragment newInstance(String param1, String param2) {
        RouteFragment fragment = new RouteFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_route, container, false);

        final AutoCompleteTextView origin = (AutoCompleteTextView) rootView.findViewById(R.id.textView5);
        final AutoCompleteTextView desti = (AutoCompleteTextView) rootView.findViewById(R.id.textView6);

        final Button route = (Button) rootView.findViewById(R.id.routeButton);

        final ListView stationsView = (ListView) rootView.findViewById(R.id.routeStation);
        routeStations = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);


        origin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
            }
        });

        desti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
            }
        });

        initilizeList(origin, desti, rootView);

        initializeStationList(greenLine, purpleLine);
        route.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         stationsView.setAdapter(null);
                                         routeStations.clear();
                                         String from = origin.getText().toString();
                                         String to = desti.getText().toString();
                                         int indexFrom, indexTo;
                                         if (purpleLine.containsKey(from)) {
                                             indexFrom = Integer.valueOf(purpleLine.get(from));
                                             if (purpleLine.containsKey(to)) {

                                                 indexTo = Integer.valueOf(purpleLine.get(to));
                                                 if (indexFrom < indexTo) {
                                                     for (int i = indexFrom; i <= indexTo; i++) {
                                                         routeStations.add(stationsPurple.get(i));

                                                     }
                                                     adapter = setlineFLag(routeStations);
                                                     stationsView.setAdapter(adapter);
                                                 } else {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i <= indexFrom; i++) {
                                                         list.add(stationsPurple.get(i));


                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);
                                                     adapter = setlineFLag(routeStations);
                                                     stationsView.setAdapter(adapter);

                                                 }
                                             } else {
                                                 int tillMajesticPurple = 7, tillMajesticGreen = 10;
                                                 if (indexFrom < tillMajesticPurple) {
                                                     for (int i = indexFrom; i <= tillMajesticPurple; i++) {
                                                         routeStations.add(stationsPurple.get(i));
                                                     }
                                                 } else {
                                                     for (int i = indexFrom; i >= tillMajesticPurple; i--) {
                                                         routeStations.add(stationsPurple.get(i));
                                                     }
                                                 }

                                                 indexTo = Integer.valueOf(greenLine.get(to));
                                                 if (indexTo < tillMajesticGreen) {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i < tillMajesticGreen; i++) {
                                                         list.add(stationsGreen.get(i));

                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);
                                                 } else {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i > tillMajesticGreen; i--) {
                                                         list.add(stationsGreen.get(i));

                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);
                                                 }

                                                 adapter = setlineFLag(routeStations);
                                                 stationsView.setAdapter(adapter);
                                             }

                                         } else {
                                             indexFrom = Integer.valueOf(greenLine.get(from));
                                             if (greenLine.containsKey(to)) {
                                                 indexTo = Integer.valueOf(greenLine.get(to));
                                                 if (indexFrom < indexTo) {
                                                     for (int i = indexFrom; i <= indexTo; i++) {
                                                         routeStations.add(stationsPurple.get(i));

                                                     }
                                                     adapter = setlineFLag(routeStations);
                                                     stationsView.setAdapter(adapter);
                                                 } else {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i <= indexFrom; i++) {
                                                         list.add(stationsPurple.get(i));


                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);


                                                 }
                                             } else {
                                                 int tillMajesticPurple = 7, tillMajesticGreen = 10;
                                                 if (indexFrom < tillMajesticGreen) {
                                                     for (int i = indexFrom; i <= tillMajesticGreen; i++) {
                                                         routeStations.add(stationsGreen.get(i));
                                                     }
                                                 } else {
                                                     for (int i = indexFrom; i >= tillMajesticGreen; i--) {
                                                         routeStations.add(stationsGreen.get(i));
                                                     }
                                                 }

                                                 indexTo = Integer.valueOf(purpleLine.get(to));
                                                 if (indexTo < tillMajesticPurple) {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i < tillMajesticPurple; i++) {
                                                         list.add(stationsPurple.get(i));

                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);
                                                 } else {
                                                     List<String> list = new LinkedList<String>();
                                                     for (int i = indexTo; i > tillMajesticPurple; i--) {
                                                         list.add(stationsPurple.get(i));

                                                     }
                                                     Collections.reverse(list);
                                                     routeStations.addAll(list);
                                                 }

                                                 adapter = setlineFLag(routeStations);
                                                 stationsView.setAdapter(adapter);
                                             }
                                         }
                                     }
                                 }

        );


        return rootView;
    }

    private SimpleAdapter setlineFLag(ArrayAdapter<String> routeStations) {
        String[] from = {"name", "image"};
        int[] to = {R.id.textView, R.id.imageView};
        LinkedList<LinkedHashMap<String, String>> aList = new LinkedList<LinkedHashMap<String, String>>();


        for (int i = 0; i < routeStations.getCount(); i++) {

            String station = routeStations.getItem(i);

            if (purpleLine.containsKey(station)) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();//create a hashmap to store the data in key value pair
                map.put("name", station);
                map.put("image", Integer.toString(R.drawable.purple));
                aList.add(map);
            } else {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();//create a hashmap to store the data in key value pair
                map.put("name", station);
                map.put("image", Integer.toString(R.drawable.green));
                aList.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.route_image_layout, from, to);

        return adapter;
    }

    private void initilizeList(AutoCompleteTextView origin, AutoCompleteTextView desti, View rootView) {


        origin.setText("");
        desti.setText("");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1);
        adapter.add("Byappanhalli");
        adapter.add("Swami Vivekananda Road");
        adapter.add("Indiranagar");
        adapter.add("Halasuru");
        adapter.add("Trinity");
        adapter.add("Mahatma Gandhi Road");
        adapter.add("Cubbon Park");
        adapter.add("Vidhana Soudha");
        adapter.add("Sir M. Visveshwaraya");
        adapter.add("Majestic (Inter Change)");
        adapter.add("City Railway Station");
        adapter.add("Magadi Road");
        adapter.add("Hosahalli");
        adapter.add("Vijayanagar");
        adapter.add("Attiguppe");
        adapter.add("Deepanjali Nagar");
        adapter.add("Mysore Road");

        adapter.add("Nagasandra");
        adapter.add("Dasarahalli");
        adapter.add("Jalahalli");
        adapter.add("Peenya Industry");
        adapter.add("Peenya");
        adapter.add("Yeshwanthpur Industry");
        adapter.add("Yeshwanthpur");
        adapter.add("Sandal Soap Factory");
        adapter.add("Mahalakshmi");
        adapter.add("Rajajinagar");
        adapter.add("Kuvempu Road");
        adapter.add("Srirampura");
        adapter.add("Sampige Road");
        adapter.add("Majestic (Inter Change)");
        adapter.add("Chickpet");
        adapter.add("Krishna Rajendra Market");
        adapter.add("National College");
        adapter.add("Lalbagh");
        adapter.add("Southend Circle");
        adapter.add("Jayanagar");
        adapter.add("Rashtreeya Vidyalaya Road");
        adapter.add("Banashankari");
        adapter.add("Jayaprakash Nagar");
        adapter.add("Puttenahalli");

        origin.setAdapter(adapter);
        desti.setAdapter(adapter);


        stationsPurple.put(16, "Byappanhalli");
        stationsPurple.put(15, "Swami Vivekananda Road");
        stationsPurple.put(14, "Indiranagar");
        stationsPurple.put(13, "Halasuru");
        stationsPurple.put(12, "Trinity");
        stationsPurple.put(11, "Mahatma Gandhi Road");
        stationsPurple.put(10, "Cubbon Park");
        stationsPurple.put(9, "Vidhana Soudha");
        stationsPurple.put(8, "Sir M. Visveshwaraya");
        stationsPurple.put(7, "Majestic (Inter Change)");
        stationsPurple.put(6, "City Railway Station");
        stationsPurple.put(5, "Magadi Road");
        stationsPurple.put(4, "Hosahalli");
        stationsPurple.put(3, "Vijayanagar");
        stationsPurple.put(2, "Attiguppe");
        stationsPurple.put(1, "Deepanjali Nagar");
        stationsPurple.put(0, "Mysore Road");


        stationsGreen.put(23, "Nagasandra");
        stationsGreen.put(22, "Dasarahalli");
        stationsGreen.put(21, "Jalahalli");
        stationsGreen.put(20, "Peenya Industry");
        stationsGreen.put(19, "Peenya");
        stationsGreen.put(18, "Yeshwanthpur Industry");
        stationsGreen.put(17, "Yeshwanthpur");
        stationsGreen.put(16, "Sandal Soap Factory");
        stationsGreen.put(15, "Mahalakshmi");
        stationsGreen.put(14, "Rajajinagar");
        stationsGreen.put(13, "Kuvempu Road");
        stationsGreen.put(12, "Srirampura");
        stationsGreen.put(11, "Sampige Road");
        stationsGreen.put(10, "Majestic (Inter Change)");
        stationsGreen.put(9, "Chickpet");
        stationsGreen.put(8, "Krishna Rajendra Market");
        stationsGreen.put(7, "National College");
        stationsGreen.put(6, "Lalbagh");
        stationsGreen.put(5, "Southend Circle");
        stationsGreen.put(4, "Jayanagar");
        stationsGreen.put(3, "Rashtreeya Vidyalaya Road");
        stationsGreen.put(2, "Banashankari");
        stationsGreen.put(1, "Jayaprakash Nagar");
        stationsGreen.put(0, "Puttenahalli");

    }


    private void initializeStationList(LinkedHashMap<String, String> greenLine, LinkedHashMap<String, String> purpleLine) {

        purpleLine.put("Byappanhalli", "16");
        purpleLine.put("Swami Vivekananda Road", "15");
        purpleLine.put("Indiranagar", "14");
        purpleLine.put("Halasuru", "13");
        purpleLine.put("Trinity", "12");
        purpleLine.put("Mahatma Gandhi Road", "11");
        purpleLine.put("Cubbon Park", "10");
        purpleLine.put("Vidhana Soudha", "9");
        purpleLine.put("Sir M. Visveshwaraya", "8");
        purpleLine.put("Majestic (Inter Change)", "7");
        purpleLine.put("City Railway Station", "6");
        purpleLine.put("Magadi Road", "5");
        purpleLine.put("Hosahalli", "4");
        purpleLine.put("Vijayanagar", "3");
        purpleLine.put("Attiguppe", "2");
        purpleLine.put("Deepanjali Nagar", "1");
        purpleLine.put("Mysore Road", "0");


        greenLine.put("Nagasandra", "23");
        greenLine.put("Dasarahalli", "22");
        greenLine.put("Jalahalli", "21");
        greenLine.put("Peenya Industry", "20");
        greenLine.put("Peenya", "19");
        greenLine.put("Yeshwanthpur Industry", "18");
        greenLine.put("Yeshwanthpur", "17");
        greenLine.put("Sandal Soap Factory", "16");
        greenLine.put("Mahalakshmi", "15");
        greenLine.put("Rajajinagar", "14");
        greenLine.put("Kuvempu Road", "13");
        greenLine.put("Srirampura", "12");
        greenLine.put("Sampige Road", "11");
        greenLine.put("Majestic (Inter Change)", "10");
        greenLine.put("Chickpet", "9");
        greenLine.put("Krishna Rajendra Market", "8");
        greenLine.put("National College", "7");
        greenLine.put("Lalbagh", "6");
        greenLine.put("Southend Circle", "5");
        greenLine.put("Jayanagar", "4");
        greenLine.put("Rashtreeya Vidyalaya Road", "3");
        greenLine.put("Banashankari", "2");
        greenLine.put("Jayaprakash Nagar", "1");
        greenLine.put("Puttenahalli", "0");
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
