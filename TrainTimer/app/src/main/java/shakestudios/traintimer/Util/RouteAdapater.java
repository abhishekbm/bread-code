package shakestudios.traintimer.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.LinkedHashMap;

import shakestudios.traintimer.Fragments.Station_detail_fragment;
import shakestudios.traintimer.Fragments.ViewFaresFragment;
import shakestudios.traintimer.R;

/**
 * Created by abbm on 2/12/2016.
 */
public class RouteAdapater extends RecyclerView.Adapter<RouteAdapater.ViewHolder> {
    private String[] dataSource, dataDescription;
    TextView heading, description;
    FragmentActivity activity;
    ViewGroup parent1;
    SimpleAdapter stationNames1;
    Bundle bundle1;

    public RouteAdapater(String[] dataArgs, FragmentActivity context, String[] description, SimpleAdapter stationNames, Bundle bundle) {
        dataSource = dataArgs;
        dataDescription = description;
        activity = context;
        stationNames1 = stationNames;
        bundle1 = bundle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routelist, parent, false);
        parent1 = parent;
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layoutlinear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                heading = (TextView) v.findViewById(R.id.heading);
                description = (TextView) v.findViewById(R.id.description);
                handleClickEvent(heading.getText().toString(), view, parent1);
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RouteAdapater.ViewHolder holder, int position) {
        holder.heading.setText(dataSource[position]);
        holder.description.setText(dataDescription[position]);
    }

    private void handleClickEvent(String text, View view, ViewGroup parent) {

        if ("Take me to the station".equalsIgnoreCase(text)) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

            final View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stationdialog, null);

            alertDialog.setView(convertView);
            alertDialog.setTitle("Boo, We can't have everything, can we?");
            alertDialog.setMessage("We are continuously improving our app to get you to the station. For now, you are lost !!! ");
            final AlertDialog alert = alertDialog.show();
        } else if (text.contains("Stations to destination:")) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            View convertView = (View) LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.stationdialog, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Stations");
            ListView lv = (ListView) convertView.findViewById(R.id.listView1);
            lv.setAdapter(stationNames1);

            final AlertDialog alert = alertDialog.show();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) parent.getItemAtPosition(position);
                    String station = map.get("name");
                    Station_detail_fragment fragment = new Station_detail_fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("station", station);
                    fragment.setArguments(bundle);


                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.event_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    alert.dismiss();
                }
            });

        }
        else  if ("Get the fares".equalsIgnoreCase(text)) {
            ViewFaresFragment fragment = new ViewFaresFragment();
            fragment.setArguments(bundle1);
            replaceFragment(fragment);

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