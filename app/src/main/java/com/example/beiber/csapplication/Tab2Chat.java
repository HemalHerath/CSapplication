package com.example.beiber.csapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab2Chat extends Fragment{

    FloatingActionButton fab;//floating button for send
    FirebaseAuth firebaseAuth;//to check user logging
    private DatabaseReference databaseReference;//to save messages
    private DatabaseReference databaseReferenceC;//to remove data when deleting user
    ListView listViewMessages;//show messages in a lst view
    List<ChatMessage> messageList;//array list to save messages

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_chat, container, false);

        //saving messages in message child
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        //list view and array list
        listViewMessages = (ListView)rootView.findViewById(R.id.list_of_message);
        messageList = new ArrayList<>();
        //get current user and check
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        //floating send button action
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText input = (EditText)rootView.findViewById(R.id.input);
                String text = input.getText().toString().trim();
                //message field empty stop executing futher
                if(TextUtils.isEmpty(text))
                {
                    return;
                }

                FirebaseDatabase.getInstance().getReference("Message").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });


        return rootView;
    }

    @Override
    //when starts the page retrieve the messages
    public void onStart()
    {
        super.onStart();
        //Value listener for retreve data
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                messageList.clear();
                //for loop for retreve all the children
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren())
                {
                    ChatMessage chatMessage = messageSnapshot.getValue(ChatMessage.class);
                    messageList.add(chatMessage);
                }
                ChatAdapter adapter = new ChatAdapter(getActivity(),messageList);
                listViewMessages.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

}
