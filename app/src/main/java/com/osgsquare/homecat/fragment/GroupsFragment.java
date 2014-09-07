package com.osgsquare.homecat.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.inject.Inject;
import com.osgsquare.homecat.R;
import com.osgsquare.homecat.agents.IGroupAgent;
import com.osgsquare.homecat.model.Group;

import java.util.List;

import roboguice.fragment.RoboListFragment;
import roboguice.util.Ln;

/**
 * Created by zzc on 8/31/14.
 */
public class GroupsFragment extends RoboListFragment {

    @Inject
    private IGroupAgent groupAgent;


    // This is the Adapter being used to display the list's data
    ArrayAdapter<Group> groupArrayAdapter;


    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GroupListTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        Activity activity = getActivity();

        // Create a progress bar to display while the list loads
        mProgress = (ProgressBar) activity.findViewById(R.id.progress_bar);

        mProgress.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        mProgress.setIndeterminate(true);




        return view;
    }

    // {!end loader}

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GroupListTask extends AsyncTask<Void, Void, List<Group>> {

        private List<Group> groups;


        @Override
        protected List<Group> doInBackground(Void... params) {

            try {
                return groupAgent.all();
            } catch (Exception e) {
                Ln.e(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<Group> groups) {

            if(groups != null) {
                //groupArrayAdapter.addAll(groups);
                groupArrayAdapter = new ArrayAdapter<Group>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        android.R.id.text1, groups);
                setListAdapter(groupArrayAdapter);
            }
        }
    }
}
