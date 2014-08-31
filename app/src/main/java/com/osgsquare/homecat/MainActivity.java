package com.osgsquare.homecat;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.osgsquare.homecat.adapter.TabsPagerAdapter;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.model.Greeting;
import com.osgsquare.homecat.net.RestHelper;
import com.osgsquare.homecat.net.StatefullRestTemplate;

import org.apache.http.client.CookieStore;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


public class MainActivity extends RoboFragmentActivity implements ActionBar.TabListener {

    @Inject
    IAuthAgent authAgent;
    @Inject
    RestTemplate restTemplate;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Groups", "Top Rated", "Movies"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Ln.d("=onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    @Override
    protected void onStart() {
        restoreCookie();
        super.onStart();
        //new HttpRequestTask().execute();
        new AuthCheckTask().execute();
    }


    @Override
    public void onStop() {
        storeCookie();
        super.onStop();
        Ln.d("== onDestroy");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Ln.e(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            if (greeting != null) {
                TextView greetingIdText = (TextView) findViewById(R.id.id_value);
                TextView greetingContentText = (TextView) findViewById(R.id.content_value);
                greetingIdText.setText(greeting.getId());
                greetingContentText.setText(greeting.getContent());
            }
        }
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
            new HttpRequestTask().execute();
            return true;
        } else if (id == R.id.action_logout) {
            new LogoutTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // menu ------

    // !tab listener ------

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    // tab listener ------

}