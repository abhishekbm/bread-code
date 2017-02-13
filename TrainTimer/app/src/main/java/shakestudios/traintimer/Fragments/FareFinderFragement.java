package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.FaresVO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FareFinderFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FareFinderFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FareFinderFragement extends Fragment {

    private static LinkedHashMap<String, String> purpleLine = new LinkedHashMap<String, String>();
    private static LinkedHashMap<String, String> greenLine = new LinkedHashMap<String, String>();
    private LinkedHashMap<Integer, String> stationsPurple = new LinkedHashMap<Integer, String>();
    private LinkedHashMap<Integer, String> stationsGreen = new LinkedHashMap<Integer, String>();

    String to, from;
    private OnFragmentInteractionListener mListener;

    public FareFinderFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FareFinderFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static FareFinderFragement newInstance(String param1, String param2) {
        FareFinderFragement fragment = new FareFinderFragement();

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
        View rootView = inflater.inflate(R.layout.fragment_fare_finder_fragement, container, false);
        Bundle bundle = this.getArguments();
        to = bundle.getString("to");
        from = bundle.getString("from");

        // initilizeList(origin, desti, rootView);

        initializeStationList(greenLine, purpleLine);

        int indexFrom, indexTo;
        if (purpleLine.containsKey(from)) {
            indexFrom = Integer.valueOf(purpleLine.get(from));
            if (purpleLine.containsKey(to)) {

                indexTo = Integer.valueOf(purpleLine.get(to));
                if (indexFrom < indexTo) {
                    for (int i = indexFrom; i <= indexTo; i++) {

                        FaresVO fares = new FaresVO();
                        fares.getFares(from, to, "", this.getContext());
                    }


                } else {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i <= indexFrom; i++) {
                        list.add(stationsPurple.get(i));


                    }


                }
            } else {
                int tillMajesticPurple = 7, tillMajesticGreen = 10;
                if (indexFrom < tillMajesticPurple) {
                    for (int i = indexFrom; i <= tillMajesticPurple; i++) {

                    }
                } else {
                    for (int i = indexFrom; i >= tillMajesticPurple; i--) {

                    }
                }

                indexTo = Integer.valueOf(greenLine.get(to));
                if (indexTo < tillMajesticGreen) {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i < tillMajesticGreen; i++) {
                        list.add(stationsGreen.get(i));

                    }
                } else {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i > tillMajesticGreen; i--) {
                        list.add(stationsGreen.get(i));

                    }
                    Collections.reverse(list);

                }


            }

        } else {
            indexFrom = Integer.valueOf(greenLine.get(from));
            if (greenLine.containsKey(to)) {
                indexTo = Integer.valueOf(greenLine.get(to));
                if (indexFrom < indexTo) {
                    for (int i = indexFrom; i <= indexTo; i++) {


                    }

                } else {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i <= indexFrom; i++) {
                        list.add(stationsPurple.get(i));


                    }


                }
            } else {
                int tillMajesticPurple = 7, tillMajesticGreen = 10;
                if (indexFrom < tillMajesticGreen) {
                    for (int i = indexFrom; i <= tillMajesticGreen; i++) {
                    }
                } else {
                    for (int i = indexFrom; i >= tillMajesticGreen; i--) {

                    }
                }

                indexTo = Integer.valueOf(purpleLine.get(to));
                if (indexTo < tillMajesticPurple) {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i < tillMajesticPurple; i++) {
                        list.add(stationsPurple.get(i));

                    }

                } else {
                    List<String> list = new LinkedList<String>();
                    for (int i = indexTo; i > tillMajesticPurple; i--) {
                        list.add(stationsPurple.get(i));

                    }

                }


            }
        }
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
}
