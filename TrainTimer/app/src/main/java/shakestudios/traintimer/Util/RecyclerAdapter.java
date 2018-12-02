package shakestudios.traintimer.Util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shakestudios.traintimer.Fragments.FaresFragment;
import shakestudios.traintimer.Fragments.ParkingFragment;
import shakestudios.traintimer.Fragments.StationListFragment;
import shakestudios.traintimer.Fragments.TimingsFragment;
import shakestudios.traintimer.Fragments.newsFragment;
import shakestudios.traintimer.Fragments.rechargeFragment;
import shakestudios.traintimer.R;
import shakestudios.traintimer.Stations.RouteFragment;

/**
 * Created by abhishek on 2/12/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] dataSource, dataDescription;
    TextView heading, description;
    FragmentActivity activity;
    ViewGroup parent1;

    public RecyclerAdapter(String[] dataArgs, FragmentActivity context, String[] description) {
        dataSource = dataArgs;
        dataDescription = description;
        activity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        parent1 = parent;
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layoutlinear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                heading = (TextView) v.findViewById(R.id.heading);
                description = (TextView) v.findViewById(R.id.description);
                handleClickEvent(heading.getText().toString(), view,parent1);
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    private void handleClickEvent(String text, View view,ViewGroup parent) {
        if ("Fares".equalsIgnoreCase(text)) {
            FaresFragment fragment = new FaresFragment();
            replaceFragment(fragment);
        } else if ("Timings".equalsIgnoreCase(text)) {
            TimingsFragment fragment = new TimingsFragment();
            replaceFragment(fragment);
        } else if ("Trip Planner".equalsIgnoreCase(text)) {
            RouteFragment fragment = new RouteFragment();
            replaceFragment(fragment);
        } else if ("Parking".equalsIgnoreCase(text)) {
            ParkingFragment fragment = new ParkingFragment();
            replaceFragment(fragment);
        } else if ("Stations".equalsIgnoreCase(text)) {

            StationListFragment fragment = new StationListFragment();
            replaceFragment(fragment);


        } else if ("News".equalsIgnoreCase(text)) {
            newsFragment fragment = new newsFragment();
            replaceFragment(fragment);
        } else if ("Recharge".equalsIgnoreCase(text)) {
            rechargeFragment fragment = new rechargeFragment();
            replaceFragment(fragment);
        } else if ("Take me to the station".equalsIgnoreCase(text)) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            final View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stationdialog, null);

            alertDialog.setView(convertView);
            alertDialog.setTitle("Boo, We can't have everything can we?");
            alertDialog.setMessage("We are continuously improving our app to get you to the station. For now you are lost!!! ");
            final AlertDialog alert = alertDialog.show();
        }

    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = activity.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.event_frame, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.heading.setText(dataSource[position]);
        holder.description.setText(dataDescription[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView heading, description;

        public ViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            description = (TextView) itemView.findViewById(R.id.description);

        }

    }
}