package shakestudios.traintimer.ListAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import shakestudios.traintimer.Fragments.CarParkingFragment;
import shakestudios.traintimer.Fragments.TwoWheelParkingFragment;


/**
 * Created by abbm on 10/3/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {


    //integer to count number of tabs
    int tabCount;
    String station;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount, String station) {
        super(fm);
        //Initializing tab count
        this.station = station;
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        Bundle bundle = new Bundle();
        bundle.putString("station", station);
        switch (position) {
            case 0:
                TwoWheelParkingFragment tab2 = new TwoWheelParkingFragment();
                tab2.setArguments(bundle);
                return tab2;

            case 1:
                CarParkingFragment tab1 = new CarParkingFragment();
                tab1.setArguments(bundle);
                return tab1;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

}
