package pl.zayer.hmd.activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import pl.zayer.hmd.R;
import pl.zayer.hmd.components.MessageView;
import pl.zayer.hmd.database.sqlite.DatabaseAccess;
import pl.zayer.hmd.database.Deadline;
import pl.zayer.hmd.database.primitives.PrimitiveDatabaseAccess;
import pl.zayer.hmd.database.primitives.SelectCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static String TAG = MainActivityFragment.class.getSimpleName();

    private ListView listView;
    private ProgressBar progressBar;
    private MessageView message;

    private MainActivityFragmentListAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_lv);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_pb);
        message = (MessageView) rootView.findViewById(R.id.message_mv);

        adapter = new MainActivityFragmentListAdapter(getContext());
        listView.setAdapter(adapter);

        showViewProgressBar();
        loadData();

        return rootView;
    }

    private void loadData() {
        DatabaseAccess databaseAccess = new DatabaseAccess(getContext().getContentResolver());
        SelectCallback<Deadline> callback = new SelectCallback<Deadline>() {
            @Override
            public void onSuccess(ArrayList<Deadline> objects) {
                Log.i(TAG, "objects: " + objects);
                if (objects.size() == 0) {
                    showViewMessage(R.string.fragment_main_message_no_deadlines);
                } else {
                    showViewList(objects);
                }
            }

            @Override
            public void onFailure(int errorCode) {
                showViewMessage(R.string.fragment_main_message_database_connection_error);
            }
        };
        databaseAccess.getEvents(1, PrimitiveDatabaseAccess.Sorting.TITLE_ASC, callback);
    }

    private void showViewList(ArrayList<Deadline> objects) {
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        message.setVisibility(View.GONE);

        adapter.clear();
        adapter.addAll(objects);
        adapter.notifyDataSetChanged();
    }

    private void showViewProgressBar() {
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        message.setVisibility(View.GONE);
    }

    private void showViewMessage(int text) {
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        message.setVisibility(View.VISIBLE);

        message.setMessage(getContext().getResources().getDrawable(R.drawable.main_fab_add), text, new Color());
    }
}
