package pl.zayer.hmd.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pl.zayer.hmd.R;
import pl.zayer.hmd.database.Deadline;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class MainActivityFragmentListAdapter extends ArrayAdapter<Deadline> {

    private static String TAG = MainActivityFragmentListAdapter.class.getSimpleName();

    public MainActivityFragmentListAdapter(Context context) {
        super(context, R.layout.fragment_main_item);
    }

    public MainActivityFragmentListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MainActivityFragmentListAdapter(Context context, ArrayList<Deadline> items) {
        super(context, R.layout.fragment_main_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView()");

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_main_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.colorBarRL = (RelativeLayout) convertView.findViewById(R.id.color_bar_rl);
            viewHolder.iconIV = (ImageView) convertView.findViewById(R.id.icon_iv);
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.dateTV = (TextView) convertView.findViewById(R.id.date_tv);
            viewHolder.daysTV = (TextView) convertView.findViewById(R.id.days_tv);
            convertView.setTag(viewHolder);
        }
        Deadline item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();

        //TODO: set object in holder
        // holder.colorBarRL.setBackgroundColor();
        // holder.iconIV.setImageDrawable();
        Log.i(TAG, "name " + item.name);
        holder.nameTV.setText(item.name);
        // holder.dateTV.setText(item.endsAt);
        // until holder.daysTV.setText();

        return convertView;
    }

    static class ViewHolder {
        public RelativeLayout colorBarRL;
        public ImageView iconIV;
        public TextView nameTV;
        public TextView dateTV;
        public TextView daysTV;
    }

}
