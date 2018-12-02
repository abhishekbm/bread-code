package shakestudios.traintimer.Fragments;

import android.content.Context;
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

import java.util.List;

import shakestudios.traintimer.ListAdapter.ImageAdapter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.FaresVO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Station_detail_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Station_detail_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Station_detail_fragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Station_detail_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Station_detail_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Station_detail_fragment newInstance(String param1, String param2) {
        Station_detail_fragment fragment = new Station_detail_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_station_detail_fragment, container, false);

        // Inflate the layout for this fragment
        ListView detailList = (ListView) rootView.findViewById(R.id.stationDetailList);
        final Bundle bundle = this.getArguments();
        String station = bundle.getString("station");
        this.getActivity().setTitle(station);
        FaresVO vo = new FaresVO();
        final List<String> details = vo.getStationDetails(this.getContext(),station);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item);
        for (int i = 0; i < details.size(); i++) {
            //adapter1.add(details.get(i));
        }

        adapter1.add("Platforms");
        adapter1.add("Parking");
        adapter1.add("Lifts and escalators");
        adapter1.add("ATM");
        String[] strings = new String[4];
        for (int j = 0; j < adapter1.getCount(); j++) {
            String obj = adapter1.getItem(j);
            strings[j] = obj;
        }

        boolean[] dflags = {true, true, false, false};
        TextView fromTo = (TextView) rootView.findViewById(R.id.fromTo);
        TextView elevation = (TextView) rootView.findViewById(R.id.elevation);
        elevation.setText(details.get(3));
        fromTo.setText(details.get(9));

        //  detailList.setAdapter(adapter1);
        detailList.setAdapter(new ImageAdapter(this.getActivity(), R.layout.image_adapter, R.id.text1, R.id.image1, strings, dflags));

        detailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String string = ((TextView) view.findViewById(R.id.text1)).getText().toString();
                if (string.equalsIgnoreCase("Parking")) {
                    ParkingManagerFragment fragment = new ParkingManagerFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                } else if (string.equalsIgnoreCase("Platforms")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("platform_1", "Platform 1:           " + details.get(0).toString());
                    bundle.putString("platform_2", "Platform 2:           " + details.get(1).toString());
                    PlatformFragment fragment = new PlatformFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                    //  Toast.makeText(getApplicationContext(), details.get(0).toString()+details.get(1).toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        //stationName.setText(station);

        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getFragmentManager();
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
