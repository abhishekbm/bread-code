package shakestudios.traintimer.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shakestudios.traintimer.R;
import shakestudios.traintimer.Stations.RouteFragment;

/**
 * Created by rajat on 2/8/2015.
 */
public class FareRecyclerAdapter extends RecyclerView.Adapter<FareRecyclerAdapter.ViewHolder> {
    private String[] dataSource, dataDescription;
    TextView heading, description;
    FragmentActivity activity;
    Bundle bundle1;

    public FareRecyclerAdapter(String[] dataArgs, FragmentActivity context, String[] description, Bundle bundle) {
        dataSource = dataArgs;
        dataDescription = description;
        activity = context;
        bundle1 = bundle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farecardlayout, parent, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layoutlinear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                heading = (TextView) v.findViewById(R.id.heading);
                description = (TextView) v.findViewById(R.id.description);
                handleClickEvent(heading.getText().toString(), view);
            }
        });
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }


    private void handleClickEvent(String text, View view) {
        if ("Plan this Trip".equalsIgnoreCase(text)) {
            RouteFragment fragment = new RouteFragment();
            Bundle bundle = new Bundle();
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
