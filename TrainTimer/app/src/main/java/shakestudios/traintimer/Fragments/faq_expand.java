package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.util.HashMap;

import shakestudios.traintimer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link faq_expand.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link faq_expand#newInstance} factory method to
 * create an instance of this fragment.
 */
public class faq_expand extends Fragment {

    private OnFragmentInteractionListener mListener;
    HashMap<Integer, String> map;

    public faq_expand() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment faq_expand.
     */
    // TODO: Rename and change types and number of parameters
    public static faq_expand newInstance(String param1, String param2) {
        faq_expand fragment = new faq_expand();

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
        View rootView = inflater.inflate(R.layout.fragment_faq_expand, container, false);
        Bundle bundle = this.getArguments();
        String category = bundle.getString("Category");
        int itemId = bundle.getInt("ItemId");
        String question1 = bundle.getString("question");
        initilizehashmap();
        TextView question = (TextView) rootView.findViewById(R.id.ques);
        question.setText(question1);
        TextView answer = (TextView) rootView.findViewById(R.id.ans);
        answer.setClickable(true);
        answer.setMovementMethod(LinkMovementMethod.getInstance());
        String answer1 = null;
        if (category.equalsIgnoreCase("Recharge")) {
            this.getActivity().setTitle("Recharge FAQ's");
            answer1 = map.get(itemId);
        } else if (category.equalsIgnoreCase("Stations")) {
            this.getActivity().setTitle("Station FAQ's");
            answer1 = map.get(itemId);
        }
        String final1 = question1 + "<br> <br> <br><br>" + answer1;
        answer.setText(Html.fromHtml(answer1));

        return rootView;
    }

    private void initilizehashmap() {
        map = new HashMap();

        map.put(1, "A government website called Karnataka Mobile One. Developed and maintained by the Government of Karnataka for the e- Services provided by them. ");
        map.put(2, "Registering in the website an OTP is sent to your mobile phone. Please note that the password is always a number and no alphabets can be used in the password. Also while logging in via the app, to enter details please use the numeric keys as alphabets will not be validated and displayed in the site.");
        map.put(3, "The card is recharged when you visit the station and swipe your card at the entry booth the next time. BMRCL would updated the balance stored in the card at the time of next entry.");
        map.put(4, "If there are any issues please use the help from within the website and register a complaint. Alternatively you can tweet to the CPRO using the following twitter Handle. <a href='https://twitter.com/cpronammametro/'> @cpronammametro </a>. They would help in resolving recharge issues.");
        map.put(5,
                "    &#8226; Every station has drinking water and restroom facilities made available at free of cost.<br> <br>" +
                        "    &#8226; All stations also provide ATM facilities to withdraw cash.<br> <br>" +
                        "    &#8226; All stations except the underground stations have cellular network. <br> <br>" +
                        "    &#8226; All stations have the facility to recharge your card and also have customer care booths to resolve any queries.<br> <br>" +
                        "    &#8226; All stations have a stolen goods reporting center. You can fill out a form and track the item. If found the lost item will be given back after through questioning.");

        map.put(6, "Go back to the counter and exchange the token back to cash. Or if you have swiped the metro card. Just exit the same station within 10 minutes after swiping in, else a nominal fee of 50 would be deducted from your card as the metro station premises is a paid area.");
        map.put(7, "Report it to the security guard and bring it to the notice of the station master. Never attempt to check the back. Please be aware that all stations are CCTV enabled.");
        map.put(8, "All metro stations are opened from 5:45 AM in the morning to 9:55 PM in the evening. Last train leaves the terminal stations on both sides of either lines at 10 PM and reaches their respective destinations. However, during certain times where the general public gather, like cricket matches and other occasions, the metro services are extended up to 1 AM. Public notices are issued a day in advance in all leading news papers. ");
        map.put(9,  "    &#8226; In order to get to the recharge screen First log in<br> <br>" +
                "    &#8226; Select Transport from the next screen.<br> <br>" +
                "    &#8226; Next choose BMRCL in the next screen and then click on the smart card top up. <br> <br>" +
                "    &#8226; Enter your phone number and metro card number which is present on the back of the Smart card.<br> <br>" +
                "    &#8226; You would be redirected to your bank account to pay the recharge amount.<br> <br>"+
        "    &#8226; The the physical card would be updated with the balance only when you tap it at the metro entry gates the next time. BMRCL would have updated its records with the amount after a successful payment..<br> <br>" );
     }

    private String getidforBundle(int item) {
        String itemString = map.get(item);
        return itemString;
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

    public class UlTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if (tag.equals("ul") && !opening) output.append("\n");
            if (tag.equals("li") && opening) output.append("\n\t•");
        }
    }
}
