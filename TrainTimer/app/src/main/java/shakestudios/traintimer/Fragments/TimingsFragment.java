package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import shakestudios.traintimer.R;
import shakestudios.traintimer.Stations.GreenStationFragment;
import shakestudios.traintimer.Stations.PurpleStationFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimingsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TimingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimingsFragment newInstance(String param1, String param2) {
        TimingsFragment fragment = new TimingsFragment();

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
        final View rootView = inflater.inflate(R.layout.fragment_timings, container, false);

        getActivity().setTitle("Timings");
        final Context context = rootView.getContext();

        Bundle bundle = this.getArguments();


        if (bundle != null) {
            String from = bundle.getString("from");
            String to = bundle.getString("to");
            String line = bundle.getString("line");

            if ("green".equalsIgnoreCase(line)) {
                GreenStationFragment fragment = new GreenStationFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                PurpleStationFragments fragment = new PurpleStationFragments();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            return rootView;
        }

        RadioGroup timingGroup = (RadioGroup) rootView.findViewById(R.id.TimingGroup);

        final RadioButton purpleRaio = (RadioButton) rootView.findViewById(R.id.PurpleRadio);

        final RadioButton greenRadio = (RadioButton) rootView.findViewById(R.id.GreenRadio);

        int checked = timingGroup.getCheckedRadioButtonId();

        timingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                setTheButtons(checkedId, rootView, purpleRaio, greenRadio);

            }
        });


        return rootView;
    }

    private void setTheButtons(int checkdId, View rootView, RadioButton purpleRaio, RadioButton greenRadio) {

        final Button towardsA, towardsB;

        if (checkdId == R.id.PurpleRadio) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(100, 150, 150, 0);

            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(30, 150, 0, 0);
            layoutParams1.gravity = Gravity.TOP;

            purpleRaio.setLayoutParams(layoutParams);
            greenRadio.setLayoutParams(layoutParams1);
            towardsA = (Button) rootView.findViewById(R.id.eastWest);
            towardsA.setVisibility(View.VISIBLE);
            towardsA.setText("Towards Mysore Road");
            towardsA.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Calendar cal = Calendar.getInstance();
                    if (cal.get(Calendar.HOUR_OF_DAY) > 22) {
                        Snackbar.make(arg0, "Boo, trains run only till 22:00 Hrs", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        PurpleStationFragments fragment = new PurpleStationFragments();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }

            });
            towardsB = (Button) rootView.findViewById(R.id.westEast);
            towardsB.setVisibility(View.VISIBLE);
            towardsB.setText("Towards Byappanhalli");

            towardsB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Calendar cal = Calendar.getInstance();
                    if (cal.get(Calendar.HOUR_OF_DAY) > 22) {
                        Snackbar.make(arg0, "Boo, trains run only till 22:00 Hrs", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        PurpleStationFragments fragment = new PurpleStationFragments();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                }
            });
        } else if (checkdId == R.id.GreenRadio) {
            towardsA = (Button) rootView.findViewById(R.id.eastWest);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(100, 150, 150, 0);

            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(30, 150, 0, 0);
            layoutParams1.gravity = Gravity.TOP;


            purpleRaio.setLayoutParams(layoutParams);
            greenRadio.setLayoutParams(layoutParams1);
            towardsA.setVisibility(View.VISIBLE);
            towardsA.setText("Towards Samigae Road");

            towardsA.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Calendar cal = Calendar.getInstance();
                 //  if (cal.get(Calendar.HOUR_OF_DAY) > 23) {
                   //     Snackbar.make(arg0, "Boo, trains run only till 22:00 Hrs", Snackbar.LENGTH_SHORT)
                    //            .setAction("Action", null).show();
                //    } else {
                        GreenStationFragment fragment = new GreenStationFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                   // }
                }
            });
            towardsB = (Button) rootView.findViewById(R.id.westEast);
            towardsB.setVisibility(View.VISIBLE);
            towardsB.setText("Towards Nagasandra");

            towardsB.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Calendar cal = Calendar.getInstance();
                 //   if (cal.get(Calendar.HOUR_OF_DAY) > 23) {
                 //       Snackbar.make(arg0, "Boo, trains run only till 22:00 Hrs", Snackbar.LENGTH_SHORT)
                    //            .setAction("Action", null).show();
                   // } else {
                        GreenStationFragment fragment = new GreenStationFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.event_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                //    }
                }
            });
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
