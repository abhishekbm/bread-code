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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link faqFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link faqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class faqFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public faqFragment() {
        // Required empty public constructor
    }

    HashMap<String, Integer> map;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment faqFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static faqFragment newInstance(String param1, String param2) {
        faqFragment fragment = new faqFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        final Bundle bundle = this.getArguments();
        String category = bundle.getString("Category");
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        initilizehashmap();
        final ArrayList list = new ArrayList();
        if (category.equalsIgnoreCase("Recharge")) {

            this.getActivity().setTitle("Recharge FAQ's");
            list.add("Which website am I visiting ?");
            list.add("Where can I get username and password ?");
            list.add("How do I know if my card is recharged ?");
            list.add("Whom do I contact if the transaction fails while recharging ?");
            list.add("How do I navigate and Recharge my Smart Card after logging in?");
        } else if (category.equalsIgnoreCase("Stations")) {
            this.getActivity().setTitle("Station FAQ's");
            list.add("What facilities are available at a station ?");
            list.add("What should I do if I want to leave a station after buying a ticket ?");
            list.add("What should I do if I find a suspicious item at the station ?");
            list.add("When do metro stations open and close ?");

        } else {

        }
        ListAdapter adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position).toString();
                int itemId = getidforBundle(item);
                bundle.putInt("ItemId", itemId);
                bundle.putString("question", item);
                faq_expand fragment = new faq_expand();
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            }
        });
        return rootView;
    }


    private void initilizehashmap() {

        map = new HashMap();

        map.put("Which website am I visiting ?", 1);
        map.put("Where can I get username and password ?", 2);
        map.put("How do I know if my card is recharged ?", 3);
        map.put("Whom do I contact if the transaction fails while recharging ?", 4);
        map.put("What facilities are available at a station ?", 5);
        map.put("What should I do if I want to leave a station after buying a ticket ?", 6);
        map.put("What should I do if I find a suspicious item at the station ?", 7);
        map.put("When do metro stations open and close ?", 8);
        map.put("How do I navigate and Recharge my Smart Card after logging in?", 9);


    }

    private int getidforBundle(String item) {
        int itemId = map.get(item);
        return itemId;
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;


        FragmentManager manager = this.getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null)

        { //fragment not in back stack, create it.
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
