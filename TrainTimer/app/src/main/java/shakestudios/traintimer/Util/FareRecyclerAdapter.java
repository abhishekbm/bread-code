package shakestudios.traintimer.Util;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shakestudios.traintimer.R;

/**
 * Created by rajat on 2/8/2015.
 */
public class FareRecyclerAdapter extends RecyclerView.Adapter<FareRecyclerAdapter.ViewHolder> {
    private String[] dataSource, dataDescription;
    TextView heading, description;
    FragmentActivity activity;

    public FareRecyclerAdapter(String[] dataArgs, FragmentActivity context, String[] description) {
        dataSource = dataArgs;
        dataDescription = description;
        activity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farecardlayout, parent, false);
        LinearLayout linearlayout = (LinearLayout) view.findViewById(R.id.farelinear);
        RelativeLayout layout = (RelativeLayout) linearlayout.findViewById(R.id.layoutlinear);
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
