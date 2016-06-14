package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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

    boolean fromSpinner1, toSpinner1;
    boolean fromSpinner2, toSpinner2;
    int positionSpinner, positionSpinner1;
    String from, to,line;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fares, container, false);

        RadioGroup timingGroup = (RadioGroup) rootView.findViewById(R.id.TimingGroup);
        RadioButton greenRadio = (RadioButton) rootView.findViewById(R.id.GreenRadio);
        final RadioButton purpleRadio = (RadioButton) rootView.findViewById(R.id.PurpleRadio);

       int checked= timingGroup.getCheckedRadioButtonId();

       final Button getFares = (Button) rootView.findViewById(R.id.fare_button);
        timingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if(checkedId==R.id.PurpleRadio) {
                    TextView origin= (TextView) rootView.findViewById(R.id.textView5);
                    origin.setVisibility(View.VISIBLE);
                    TextView desti= (TextView) rootView.findViewById(R.id.textView6);
                    desti.setVisibility(View.VISIBLE);
                    getFares.setVisibility(View.VISIBLE);
                    initiliazeSpinnerOne(savedInstanceState, rootView);
                    initializeSpinnerTwo(savedInstanceState, rootView);
                }else if(checkedId==R.id.GreenRadio) {
                    TextView origin= (TextView) rootView.findViewById(R.id.textView5);
                    origin.setVisibility(View.VISIBLE);
                    TextView desti= (TextView) rootView.findViewById(R.id.textView6);
                    desti.setVisibility(View.VISIBLE);
                    getFares.setVisibility(View.VISIBLE);
                    initiliazeSpinnerThree(savedInstanceState, rootView);
                    initializeSpinnerFour(savedInstanceState, rootView);
                }
                else{
                    fromSpinner1 = false;
                    fromSpinner2 = true;
                    if (savedInstanceState != null) {
                        positionSpinner = savedInstanceState.getInt("posSpinner");
                        if (positionSpinner != 0) fromSpinner2 = false;
                    }


                    final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
                    spinner.setVisibility(View.VISIBLE);
// Create an ArrayAdapter using the string array and a default spinner layout
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item) {

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);
                            if (position == getCount()) {
                                ((TextView) v.findViewById(android.R.id.text1)).setText("Select Line");
                                ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                            }

                            return v;
                        }

                        @Override
                        public int getCount() {
                            return super.getCount() - 1; // you dont display last item. It is used as hint.
                        }

                    };

                    adapter.add("Select Line");

                    spinner.setAdapter(adapter);
                    spinner.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {

                            boolean spinnerUsable = (fromSpinner1 && fromSpinner2);
                            if (!fromSpinner1) {
                                fromSpinner1 = true;
                            } else if (!fromSpinner2) {
                                fromSpinner2 = true;
                            }
                            if (spinnerUsable) {

                                from = spinner.getSelectedItem().toString();
                                line="purple";
                            }

                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            // Another interface callback
                        }
                    });
// Apply the adapter to the spinner


                    toSpinner1 = false;
                    toSpinner2 = true;
                    if (savedInstanceState != null) {
                        positionSpinner1 = savedInstanceState.getInt("posSpinner");
                        if (positionSpinner1 != 0) toSpinner2 = false;
                    }

                }
            }
        });



        getFares.setOnClickListener(new View.OnClickListener() {
            boolean set = false;

            @Override
            public void onClick(View arg0) {

                if (null==from ||null==to){
                    Snackbar.make(arg0, "Please Choose Stations", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                }
                else if(from.equalsIgnoreCase(to)){
                    Snackbar.make(arg0, "Origin and Destination can't be same", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                }
                if (!set) {
                    ViewFaresFragment fragment = new ViewFaresFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from", from);
                    bundle.putString("to", to);
                    bundle.putString("line",line);
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

    private void initiliazeSpinnerOne(Bundle savedInstanceState, final View view) {

        fromSpinner1 = false;
        fromSpinner2 = true;
        if (savedInstanceState != null) {
            positionSpinner = savedInstanceState.getInt("posSpinner");
            if (positionSpinner != 0) fromSpinner2 = false;
        }


        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("Select Station");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
        adapter.add("Byappanhalli");
        adapter.add("Swami Vivekananda Road");
        adapter.add("Indiranagar");
        adapter.add("Halasuru");
        adapter.add("Trinity");
        adapter.add("Mahatma Gandhi Road");
        adapter.add("Cubbon Park");
        adapter.add("Vidhana Soudha");
        adapter.add("Sir M. Visveshwaraya");
        adapter.add("Majestic");
        adapter.add("City Railway Station");
        adapter.add("Magadi Road");
        adapter.add("Hosahalli");
        adapter.add("Vijayanagar");
        adapter.add("Attiguppe");
        adapter.add("Deepanjali Nagar");
        adapter.add("Mysore Road");
        adapter.add("Select Station");

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                boolean spinnerUsable = (fromSpinner1 && fromSpinner2);
                if (!fromSpinner1) {
                    fromSpinner1 = true;
                } else if (!fromSpinner2) {
                    fromSpinner2 = true;
                }
                if (spinnerUsable) {

                    from = spinner.getSelectedItem().toString();
                    line="purple";
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
// Apply the adapter to the spinner


        toSpinner1 = false;
        toSpinner2 = true;
        if (savedInstanceState != null) {
            positionSpinner1 = savedInstanceState.getInt("posSpinner");
            if (positionSpinner1 != 0) toSpinner2 = false;
        }


    }


    private void initializeSpinnerTwo(Bundle savedInstanceState, final View view) {
        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner2);
        spinner1.setVisibility(View.VISIBLE);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("Select Station");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
        adapter1.add("Byappanhalli");
        adapter1.add("Swami Vivekananda Road");
        adapter1.add("Indiranagar");
        adapter1.add("Halasuru");
        adapter1.add("Trinity");
        adapter1.add("Mahatma Gandhi Road");
        adapter1.add("Cubbon Park");
        adapter1.add("Vidhana Soudha");
        adapter1.add("Sir M. Visveshwaraya");
        adapter1.add("Majestic");
        adapter1.add("City Railway Station");
        adapter1.add("Magadi Road");
        adapter1.add("Hosahalli");
        adapter1.add("Vijayanagar");
        adapter1.add("Attiguppe");
        adapter1.add("Deepanjali Nagar");
        adapter1.add("Mysore Road");
        adapter1.add("Select Station");

        spinner1.setAdapter(adapter1);
        spinner1.setSelection(adapter1.getCount()); //set the hint the default selection so it appears on launch.
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
              /*  adapter1.add("Select Station");*/
                boolean spinnerUsable6 = (toSpinner1 && toSpinner2);
                if (!toSpinner1) {
                    toSpinner1 = true;
                } else if (!toSpinner2) {
                    toSpinner2 = true;
                }
                if (spinnerUsable6) {
                    to = parent.getItemAtPosition(pos).toString();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }



    private void initiliazeSpinnerThree(Bundle savedInstanceState, final View view) {

        fromSpinner1 = false;
        fromSpinner2 = true;
        if (savedInstanceState != null) {
            positionSpinner = savedInstanceState.getInt("posSpinner");
            if (positionSpinner != 0) fromSpinner2 = false;
        }


        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("Select Station");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
        adapter.add("Nagasandra");
        adapter.add("Dasarahalli");
        adapter.add("Jalahalli");
        adapter.add("Peenya Industry");
        adapter.add("Peenya");
        adapter.add("Yeshwanthpur Industry");
        adapter.add("Yeshwanthpur");
        adapter.add("Yesvantpur railway station");
        adapter.add("Sandal Soap Factory");
        adapter.add("Mahalakshmi");
        adapter.add("Rajajinagar");
        adapter.add("Kuvempu Road");
        adapter.add("Srirampura");
        adapter.add("Sampige Road");
        adapter.add("Select Station");

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //set the hint the default selection so it appears on launch.
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                boolean spinnerUsable = (fromSpinner1 && fromSpinner2);
                if (!fromSpinner1) {
                    fromSpinner1 = true;
                } else if (!fromSpinner2) {
                    fromSpinner2 = true;
                }
                if (spinnerUsable) {

                    from = spinner.getSelectedItem().toString();
                    line="green";
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
// Apply the adapter to the spinner


        toSpinner1 = false;
        toSpinner2 = true;
        if (savedInstanceState != null) {
            positionSpinner1 = savedInstanceState.getInt("posSpinner");
            if (positionSpinner1 != 0) toSpinner2 = false;
        }


    }

    private void initializeSpinnerFour(Bundle savedInstanceState, final View view) {
        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner2);
        spinner1.setVisibility(View.VISIBLE);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("Select Station");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };
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
        adapter1.add("Select Station");

        spinner1.setAdapter(adapter1);
        spinner1.setSelection(adapter1.getCount()); //set the hint the default selection so it appears on launch.
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
              /*  adapter1.add("Select Station");*/
                boolean spinnerUsable6 = (toSpinner1 && toSpinner2);
                if (!toSpinner1) {
                    toSpinner1 = true;
                } else if (!toSpinner2) {
                    toSpinner2 = true;
                }
                if (spinnerUsable6) {
                    to = parent.getItemAtPosition(pos).toString();
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }
}
