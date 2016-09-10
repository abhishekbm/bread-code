package shakestudios.traintimer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import shakestudios.traintimer.Fragments.ParkingFragment;
import shakestudios.traintimer.ListAdapter.ImageAdapter;
import shakestudios.traintimer.R;
import shakestudios.traintimer.ValueObjects.FaresVO;

public class Station_Detail extends AppCompatActivity implements ParkingFragment.OnFragmentInteractionListener {

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

        FaresVO vo = new FaresVO();
        List<String> details = vo.getStationDetails(getApplicationContext());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        for (int i = 0; i < details.size(); i++) {
            //  adapter1.add(details.get(i));
        }

        adapter1.add("Platforms");
        adapter1.add("Parking");
        adapter1.add("Lifts and escalators");
        String[] strings = new String[3];
        for (int j = 0; j < adapter1.getCount(); j++) {
            String obj = adapter1.getItem(j);
            strings[j] = obj;
        }

        boolean[] dflags = {true, true, true};

        //  detailList.setAdapter(adapter1);
        detailList.setAdapter(new ImageAdapter(this, R.layout.image_adapter, R.id.text1, R.id.image1, strings, dflags));

        detailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = ((TextView) view).getText().toString();
                if (string.equalsIgnoreCase("Parking")) {
                    ParkingFragment fragment = new ParkingFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(ParkingFragment.newInstance("", ""), null);
                    fragmentTransaction.replace(R.id.relLayout, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        //stationName.setText(station);
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
