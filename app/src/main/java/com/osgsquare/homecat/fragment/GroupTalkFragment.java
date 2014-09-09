package com.osgsquare.homecat.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.osgsquare.homecat.R;
import com.osgsquare.homecat.model.ws.Message;
import com.osgsquare.homecat.service.MessageService;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

public class GroupTalkFragment extends RoboFragment {


    @InjectView(R.id.messageEditText)
    EditText messageEditText;
    @InjectView(R.id.sendButton)
    Button sendButton;

    private MessageService service;
    private boolean bound = false;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            service = ((MessageService.LocalBinder) binder).getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    public GroupTalkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        messageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
            }
        });
        return inflater.inflate(R.layout.fragment_group_talk, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(getActivity(), MessageService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }



    @Override
    public void onStop(){
        super.onStop();
        // Unbind from the service
        if (bound) {
            getActivity().unbindService(connection);
            bound = false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Intent intent = new Intent(activity, MessageService.class);
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Unbind from the service
        if (bound) {
            getActivity().unbindService(connection);
            bound = false;
        }
    }

    private void sendMessage(){
        String text = messageEditText.getText().toString();
        Message message = new Message();

        service.send(message);
    }

}
