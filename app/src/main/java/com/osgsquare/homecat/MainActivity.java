package com.osgsquare.homecat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.osgsquare.homecat.agents.IAuthAgent;
import com.osgsquare.homecat.model.Greeting;
import com.osgsquare.homecat.net.RestHelper;
import com.osgsquare.homecat.net.StatefullRestTemplate;

import org.apache.http.client.CookieStore;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import roboguice.activity.RoboFragmentActivity;
import roboguice.util.Ln;


public class MainActivity extends RoboFragmentActivity {

    @Inject IAuthAgent authAgent;
    @Inject RestTemplate restTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        restoreCookie();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
        new AuthCheckTask().execute();
    }

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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        storeCookie();
        super.onDestroy();
        Ln.i("onDestroy==");
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
                //Log.e("MainActivity", e.getMessage(), e);
                Ln.e(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getContent());
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
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Ln.i("MainActivity %s", result.toString());
            if( !result ) {
                // 还没有登录，跳转到登录页面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        }

    }

    private void storeCookie() {
        try {

            CookieStore cookieStore = ((StatefullRestTemplate)restTemplate).getCookieStore();

            FileOutputStream fos = openFileOutput(Config.COOKIE_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutput output = new ObjectOutputStream(fos);
            RestHelper.storeCookies(cookieStore, output);
            Ln.i("cookieStore.toString():"+cookieStore.toString());
            /*
            List<Cookie> cookies = cookieStore.getCookies();
            for(Cookie cookie : cookies) {
                Ln.i("Store cookie === " + cookie.getName() + ":" + cookie.getValue());
                output.writeObject(cookie);
            }*/

            output.close();

        } catch (Exception e) {
            Ln.e(e);
        }
    }

    private void restoreCookie(){
        try {

            CookieStore cookieStore = ((StatefullRestTemplate)restTemplate).getCookieStore();

            FileInputStream fis = openFileInput(Config.COOKIE_FILE_NAME);
            ObjectInput input = new ObjectInputStream(fis);
            RestHelper.restoreCookies(cookieStore, input);

            Ln.i("cookieStore.toString():"+cookieStore.toString());
            /*
            while(input.available() > 0) {
                Cookie cookie = (Cookie) (input.readObject());
                Ln.i("Restore cookie === " + cookie.getName() + ":" + cookie.getValue());
                cookieStore.addCookie((cookie));
            }*/

            input.close();
        } catch (Exception e) {
            Ln.e(e);
        }
    }


}