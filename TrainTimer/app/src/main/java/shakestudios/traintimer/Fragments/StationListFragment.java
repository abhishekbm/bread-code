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

import shakestudios.traintimer.Activities.Station_Detail;
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
        ArrayAdapter array = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1);
        array.add("Byappanhalli");
        array.add("Swami Vivekananda Road");
        array.add("Indiranagar");
        array.add("Halasuru");
        array.add("Trinity");
        array.add("Mahatma Gandhi Road");
        array.add("Cubbon Park");
        array.add("Vidhana Soudha");
        array.add("Sir M. Visveshwaraya");
        array.add("Majestic (Inter Change)");
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
        view.setAdapter(array);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String stationName= (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), Station_Detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("station",stationName);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

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
}
