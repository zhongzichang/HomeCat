package com.osgsquare.homecat.rest;

import android.os.AsyncTask;

/**
 * Created by zhongzichang on 8/20/14.
 */
public class RequestTask<T> extends AsyncTask<RequestParams, Void, Action<T>> {



    @Override
    protected Action<T> doInBackground(RequestParams... paramses) {
        return null;
    }

    @Override
    protected void onPostExecute(Action<T> action) {
        action.run();
    }
}
