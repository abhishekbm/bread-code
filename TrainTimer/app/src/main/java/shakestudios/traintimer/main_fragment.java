package shakestudios.traintimer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shakestudios.traintimer.Util.RecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link main_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link main_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public main_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static main_fragment newInstance(String param1, String param2) {
        main_fragment fragment = new main_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem items) {
        int id = items.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }
        return super.onOptionsItemSelected(items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        final Context context = rootView.getContext();
        Button Timing, Fares, parking;

        getActivity().setTitle("My Metro");
/*
        Timing = (Button) rootView.findViewById(R.id.TimingButton);

        Fares = (Button) rootView.findViewById(R.id.FaresButton);

        parking = (Button) rootView.findViewById(R.id.Parking);


        Timing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().setTitle("Timings");
                RouteFragment fragment = new RouteFragment();
                replaceFragment(fragment);
               *//* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*//*
            }

        });

        Fares.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                getActivity().setTitle("Fares");

                FaresFragment fragment = new FaresFragment();
                replaceFragment(fragment);
               *//* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*//*

            }

        });

        parking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                getActivity().setTitle("Parking");
                ParkingFragment fragment = new ParkingFragment();
                replaceFragment(fragment);
               *//* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*//*
            }

        });*/
/*

        CardView TimingsCard = (CardView) rootView.findViewById(R.id.TimingsCard);
        CardView RouteCard = (CardView) rootView.findViewById(R.id.RouteCard);
        CardView FareCard = (CardView) rootView.findViewById(R.id.FareCard);

        TextView route = (TextView) rootView.findViewById(R.id.routeView);
        TextView timing = (TextView) rootView.findViewById(R.id.timingsView);
        TextView fares = (TextView) rootView.findViewById(R.id.faresView);

        route.setText("route");
        timing.setText("timing");
        fares.setText("fares");

        TimingsCard.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View arg0) {

                                         getActivity().setTitle("Timings");
                                         TimingsFragment fragment = new TimingsFragment();
                                         replaceFragment(fragment);

                                     }
                                 }

        );

        RouteCard.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View arg0) {

                                         getActivity().setTitle("Route");
                                         RouteFragment fragment = new RouteFragment();
                                         replaceFragment(fragment);

                                     }
                                 }

        );

        FareCard.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View arg0) {

                                         getActivity().setTitle("Fares");
                                         FaresFragment fragment = new FaresFragment();
                                         replaceFragment(fragment);

                                     }
                                 }

        );
*/


        String[] dataArray = new String[]{"Fares","Trip Planner","Timings","Parking"};
        String[] dataDescription = new String[]{"Find the fare between 2 stations","Plan a Journey and get the details","Know when the next train arrives","Know which stations have parking facility"};
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(dataArray,this.getActivity(),dataDescription);
        recyclerView.setAdapter(adapter);


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
