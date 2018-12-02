package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FaresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaresFragment extends Fragment {


    String from, to, line;


    private OnFragmentInteractionListener mListener;
    private static List<String> stations= new ArrayList<>();
    public FaresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaresFragment newInstance(String param1, String param2) {
        FaresFragment fragment = new FaresFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fares, container, false);

        getActivity().setTitle("Fares");
        final AutoCompleteTextView origin = (AutoCompleteTextView) rootView.findViewById(R.id.textView5);
        final AutoCompleteTextView desti = (AutoCompleteTextView) rootView.findViewById(R.id.textView6);
        stationList();
        origin.setVisibility(View.VISIBLE);
        origin.setThreshold(0);
        desti.setThreshold(0);
        TextView textView = (TextView) rootView.findViewById(R.id.station_header);
        textView.setText("Select stations to view the fares");
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        desti.setSelected(true);
        origin.setSelected(true);
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
        adapter.add("Sandal Soap Factory (Orion Mall)");
        adapter.add("Mahalakshmi");
        adapter.add("Rajajinagar");
        adapter.add("Kuvempu Road");
        adapter.add("Srirampura");
        adapter.add("Mantri Square (Sampige Road)");
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


       /* timingGroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                origin.setText("");
                desti.setText("");
            }
        });*/

        final Button getFares = (Button) rootView.findViewById(R.id.fare_button);


        getFares.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                boolean set = false;

                from = origin.getText().toString();
                to = desti.getText().toString();

                if (from.equalsIgnoreCase(to)) {
                    Snackbar.make(arg0, "Origin and Destination can't be same", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                }

                else if(!(stations.contains(from) &&stations.contains(to))){
                    Snackbar.make(arg0, "Please choose valid stations", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                }
                if (!set) {
                    ViewFaresFragment fragment = new ViewFaresFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", from);
                    bundle.putString("to", to);
                    bundle.putString("line", line);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.event_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
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

    private void stationList(){
        stations.add("Byappanhalli");
        stations.add("Swami Vivekananda Road");
        stations.add("Indiranagar");
        stations.add("Halasuru");
        stations.add("Trinity");
        stations.add("Mahatma Gandhi Road");
        stations.add("Cubbon Park");
        stations.add("Vidhana Soudha");
        stations.add("Sir M. Visveshwaraya");
        stations.add("Majestic (Inter Change)");
        stations.add("City Railway Station");
        stations.add("Magadi Road");
        stations.add("Hosahalli");
        stations.add("Vijayanagar");
        stations.add("Attiguppe");
        stations.add("Deepanjali Nagar");
        stations.add("Mysore Road");

        stations.add("Nagasandra");
        stations.add("Dasarahalli");
        stations.add("Jalahalli");
        stations.add("Peenya Industry");
        stations.add("Peenya");
        stations.add("Yeshwanthpur Industry");
        stations.add("Yeshwanthpur");
        stations.add("Sandal Soap Factory (Orion Mall)");
        stations.add("Mahalakshmi");
        stations.add("Rajajinagar");
        stations.add("Kuvempu Road");
        stations.add("Srirampura");
        stations.add("Mantri Square (Sampige Road)");
        stations.add("Majestic (Inter Change)");
        stations.add("Chickpet");
        stations.add("Krishna Rajendra Market");
        stations.add("National College");
        stations.add("Lalbagh");
        stations.add("Southend Circle");
        stations.add("Jayanagar");
        stations.add("Rashtreeya Vidyalaya Road");
        stations.add("Banashankari");
        stations.add("Jayaprakash Nagar");
        stations.add("Puttenahalli");

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
