package shakestudios.traintimer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import shakestudios.traintimer.Fragments.CarParkingFragment;
import shakestudios.traintimer.Fragments.ParkingFragment;
import shakestudios.traintimer.Fragments.ParkingManagerFragment;
import shakestudios.traintimer.Fragments.PlatformFragment;
import shakestudios.traintimer.Fragments.TwoWheelParkingFragment;
import shakestudios.traintimer.ListAdapter.ImageAdapter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.FaresVO;

public class Station_Detail extends AppCompatActivity implements ParkingFragment.OnFragmentInteractionListener, TwoWheelParkingFragment.OnFragmentInteractionListener,CarParkingFragment.OnFragmentInteractionListener, PlatformFragment.OnFragmentInteractionListener,ParkingManagerFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station__detail);

        Toolbar appbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_keyboard_arrow_right_black_24dp);
        //TextView stationName = (TextView) findViewById(R.id.stationName);
        ListView detailList = (ListView) findViewById(R.id.stationDetailList);
        Intent intent = this.getIntent();

        final Bundle bundle = intent.getExtras();
        String station = bundle.getString("station");
        this.setTitle(station);
        FaresVO vo = new FaresVO();
        final List<String> details = vo.getStationDetails(getApplicationContext());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        for (int i = 0; i < details.size(); i++) {
              //adapter1.add(details.get(i));
        }

        adapter1.add("Platforms");
        adapter1.add("Parking");
        adapter1.add("Lifts and escalators");
        adapter1.add("ATM");
        String[] strings = new String[4];
        for (int j = 0; j < adapter1.getCount(); j++) {
            String obj = adapter1.getItem(j);
            strings[j] = obj;
        }

        boolean[] dflags = {true, true, true,false};
        TextView fromTo = (TextView) findViewById(R.id.fromTo);
        TextView elevation = (TextView) findViewById(R.id.elevation);
        elevation.setText(details.get(3));
        fromTo.setText(details.get(9));

        //  detailList.setAdapter(adapter1);
        detailList.setAdapter(new ImageAdapter(this, R.layout.image_adapter, R.id.text1, R.id.image1, strings, dflags));

        detailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String string = ((TextView) view.findViewById(R.id.text1)).getText().toString();
                if (string.equalsIgnoreCase("Parking")) {
                    ParkingManagerFragment fragment = new ParkingManagerFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                } else if (string.equalsIgnoreCase("Platforms")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("platform_1", "Platform 1:           " + details.get(0).toString());
                    bundle.putString("platform_2", "Platform 2:           " + details.get(1).toString());
                    PlatformFragment fragment = new PlatformFragment();
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                    //  Toast.makeText(getApplicationContext(), details.get(0).toString()+details.get(1).toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        //stationName.setText(station);
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.relLayout, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
