package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoWheelParkingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoWheelParkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoWheelParkingFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TwoWheelParkingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoWheelParkingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoWheelParkingFragment newInstance(String param1, String param2) {
        TwoWheelParkingFragment fragment = new TwoWheelParkingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tablayout, container, false);
        Bundle bundle = this.getArguments();
        String station = bundle.getString("station");
        TableLayout layout = (TableLayout) rootView.findViewById(R.id.tableLayout1);

        TextView textView1 = (TextView) rootView.findViewById(R.id.textView1);
        TextView textView2 = (TextView) rootView.findViewById(R.id.textView2);
        TextView textView3 = (TextView) rootView.findViewById(R.id.textView3);
        TextView textView4 = (TextView) rootView.findViewById(R.id.textView4);
        TextView textView5 = (TextView) rootView.findViewById(R.id.textView5);
        TextView textView6 = (TextView) rootView.findViewById(R.id.textView6);

        textView1.setText("First four Hours: ");
        textView2.setText(" 15");
        textView3.setText("For every subsequent hour after 4 hours: ");
        textView4.setText(" +5");
        textView5.setText("All day");
        textView6.setText(" 30");


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
