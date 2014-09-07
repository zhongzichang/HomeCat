package com.osgsquare.homecat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;
import com.osgsquare.homecat.Config;
import com.osgsquare.homecat.R;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.fragment.PagerFragment;
import com.osgsquare.homecat.net.RestHelper;
import com.osgsquare.homecat.net.StatefullRestTemplate;

import org.apache.http.client.CookieStore;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import roboguice.activity.RoboFragmentActivity;
import roboguice.util.Ln;


public class MainActivity extends RoboFragmentActivity {

    @Inject
    IAuthAgent authAgent;
    @Inject
    RestTemplate restTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreCookie();

    }

    @Override
    protected void onStart() {
        super.onStart();
        new AuthCheckTask().execute();
    }



    @Override
    public void onStop() {
        //storeCookie();
        super.onStop();
    }

    @Override
    public void onDestroy(){
        storeCookie();
        super.onDestroy();
    }



    private class AuthCheckTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return authAgent.check();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Ln.i("MainActivity %s", result.toString());
            if (!result) {
                // 还没有登录，跳转到登录页面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                // 已经登录
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PagerFragment pagerFragment = new PagerFragment();
                fragmentTransaction.replace(R.id.container, pagerFragment);
                fragmentTransaction.commit();
            }

        }
    }

    private class LogoutTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return authAgent.logout();
            } catch (Exception e) {
                Ln.e(e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // 退出后，跳转到登录页面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        }
    }

    private void storeCookie() {
        try {
            CookieStore cookieStore = ((StatefullRestTemplate) restTemplate).getCookieStore();
            FileOutputStream fos = openFileOutput(Config.COOKIE_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutput output = new ObjectOutputStream(fos);
            RestHelper.storeCookies(cookieStore, output);
            output.close();
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    private void restoreCookie() {
        try {
            File file = new File(getFilesDir(), Config.COOKIE_FILE_NAME);
            if (file.exists()) {
                CookieStore cookieStore = ((StatefullRestTemplate) restTemplate).getCookieStore();
                FileInputStream fis = openFileInput(Config.COOKIE_FILE_NAME);
                ObjectInput input = new ObjectInputStream(fis);
                RestHelper.restoreCookies(cookieStore, input);
                input.close();
            }
        } catch (Exception e) {
            Ln.e(e);
        }
    }


    // ! menu ------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            return true;
        } else if (id == R.id.action_logout) {
            new LogoutTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // menu ------


}