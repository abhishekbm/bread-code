package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shakestudios.traintimer.ListAdapter.PagerAdapter;
import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParkingManagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParkingManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingManagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private OnFragmentInteractionListener mListener;

    public ParkingManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParkingManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParkingManagerFragment newInstance(String param1, String param2) {
        ParkingManagerFragment fragment = new ParkingManagerFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_parking_manager, container, false);
        Bundle bundle = this.getArguments();
        String station = bundle.getString("station");
        //Initializing the tablayout
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.VISIBLE);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Two-Wheeler"));
        tabLayout.addTab(tabLayout.newTab().setText("Car"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        //Creating our pager adapter
        PagerAdapter adapter1 = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount(), station);

        //Adding adapter to pager
        viewPager.setAdapter(adapter1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
