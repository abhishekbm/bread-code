package shakestudios.traintimer.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import shakestudios.traintimer.R;
import shakestudios.traintimer.Util.CheckNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rechargeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rechargeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rechargeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String PREFS_NAME = "Test";
    private OnFragmentInteractionListener mListener;

    public rechargeFragment() {
        // Required empty public constructor
    }

    public CheckBox check;
    WebView webView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rechargeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static rechargeFragment newInstance(String param1, String param2) {
        rechargeFragment fragment = new rechargeFragment();
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };

    private void webViewGoBack(){
        webView.goBack();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootview = inflater.inflate(R.layout.fragment_recharge, container, false);

        AlertDialog.Builder adb = new AlertDialog.Builder(this.getContext());
        LayoutInflater adbInflater =
                LayoutInflater.from(this.getContext());
        View eulaLayout = adbInflater.inflate(R.layout.dialoglayout, null);
        final CheckBox check = (CheckBox) eulaLayout.findViewById(R.id.skip);
        final ProgressBar bar = (ProgressBar) rootview.findViewById(R.id.pB1);
        adb.setView(eulaLayout);
        adb.setTitle("Example:");
        adb.setMessage(Html.fromHtml("This is a test"));
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (check.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("noshow", checkBoxResult);
                // Commit the edits!

                editor.commit();
                if (CheckNetwork.isInternetAvailable(getContext())) //returns true if internet available
                {


                    webView = (WebView) rootview.findViewById(R.id.webview);
                    webView.setWebViewClient(new myWebViewClient(bar));
                    webView.requestFocus(View.FOCUS_DOWN);
                    webView.canGoBackOrForward(10);
                    webView.setOnKeyListener(new View.OnKeyListener(){

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK
                                    && event.getAction() == MotionEvent.ACTION_UP
                                    && webView.canGoBack()) {
                                handler.sendEmptyMessage(1);
                                return true;
                            }

                            return false;
                        }

                    });

                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
                    webView.getSettings().setUserAgentString(newUA);
                    webView.loadUrl("https://www.mobile.karnataka.gov.in/goken/login.aspx");
                }
                return;
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (check.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("noshow", checkBoxResult);
                // Commit the edits!
                editor.commit();
                return;
            }
        });
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

        String noshow = settings.getString ("noshow", "NOT checked");
        if (noshow != "checked") {
            adb.show();
        }


        return rootview;
    }

    public  boolean canGoBack(){
        return webView.canGoBack();
    }

    public  void goBack(){
        webView.goBack();
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
