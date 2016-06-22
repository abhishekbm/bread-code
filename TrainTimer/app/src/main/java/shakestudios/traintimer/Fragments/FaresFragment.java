package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

        getActivity().setTitle("Fares");
        final AutoCompleteTextView origin = (AutoCompleteTextView) rootView.findViewById(R.id.textView5);
        final AutoCompleteTextView desti = (AutoCompleteTextView) rootView.findViewById(R.id.textView6);

        origin.setVisibility(View.VISIBLE);

        final RadioGroup timingGroup = (RadioGroup) rootView.findViewById(R.id.TimingGroup);
        final RadioButton greenRadio = (RadioButton) rootView.findViewById(R.id.GreenRadio);
        final RadioButton purpleRadio = (RadioButton) rootView.findViewById(R.id.PurpleRadio);
        origin.setThreshold(1);
        desti.setThreshold(1);
        timingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.PurpleRadio) {
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
                    adapter.add("Majestic");
                    adapter.add("City Railway Station");
                    adapter.add("Magadi Road");
                    adapter.add("Hosahalli");
                    adapter.add("Vijayanagar");
                    adapter.add("Attiguppe");
                    adapter.add("Deepanjali Nagar");
                    adapter.add("Mysore Road");
                    line = "purple";
                    origin.setAdapter(adapter);

                    desti.setAdapter(adapter);

                } else if (checkedId == R.id.GreenRadio) {

                    origin.setText("");
                    desti.setText("");
                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1);
                    adapter1.add("Nagasandra");
                    adapter1.add("Dasarahalli");
                    adapter1.add("Jalahalli");
                    adapter1.add("Peenya Industry");
                    adapter1.add("Peenya");
                    adapter1.add("Yeshwanthpur Industry");
                    adapter1.add("Yeshwanthpur");
                    adapter1.add("Sandal Soap Factory");
                    adapter1.add("Mahalakshmi");
                    adapter1.add("Rajajinagar");
                    adapter1.add("Kuvempu Road");
                    adapter1.add("Srirampura");
                    adapter1.add("Sampige Road");
                    line = "green";
                    origin.setAdapter(adapter1);

                    desti.setAdapter(adapter1);
                }
            }
        });

        /*origin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int check = timingGroup.getCheckedRadioButtonId();
                if (check == -1) {

                    Snackbar.make(v, "Please Choose a Line Above", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });*/
        desti.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (timingGroup.getCheckedRadioButtonId() == -1) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
                    Snackbar.make(v, "Please Choose a line", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                }
                else{
                    desti.showDropDown();

                }
                return false;
            }
        });

        origin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (timingGroup.getCheckedRadioButtonId() == -1) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
                    Snackbar.make(v, "Please Choose a line", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    return true;
                }  else{
                    origin.showDropDown();

                }

                return false;
            }
        });
        origin.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        desti.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
            }
        });
        origin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        desti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), 0);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

                to = origin.getText().toString();
                from = desti.getText().toString();
                if (timingGroup.getCheckedRadioButtonId() == -1) {
                    Snackbar.make(arg0, "Please Choose a Line", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                } else if (null == from || null == to || to.isEmpty() || from.isEmpty()) {
                    Snackbar.make(arg0, "Please Choose Stations", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    set = true;
                } else if (from.equalsIgnoreCase(to)) {
                    Snackbar.make(arg0, "Origin and Destination can't be same", Snackbar.LENGTH_SHORT)
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
