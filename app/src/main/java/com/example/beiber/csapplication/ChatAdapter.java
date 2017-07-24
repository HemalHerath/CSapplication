package com.example.beiber.csapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private Activity context;
    private List<ChatMessage> messageList;

    public ChatAdapter(Activity context, List<ChatMessage> messageList) {
        super(context, R.layout.activity_chat, messageList);
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViwItem = inflater.inflate(R.layout.list_item, null, true);

        TextView message_user =(TextView) listViwItem.findViewById(R.id.message_user);
        TextView message_text =(TextView) listViwItem.findViewById(R.id.message_text);
        TextView message_time =(TextView) listViwItem.findViewById(R.id.message_time);
        TextView letter =(TextView)listViwItem.findViewById(R.id.letter);

        ChatMessage chatMessage = messageList.get(position);

        message_user.setText(chatMessage.getMessageUser());
        message_text.setText(chatMessage.getMessageText());
        letter.setText(chatMessage.getMessageUser());

        message_time.setText(DateFormat.format("dd-MM-yyyy-hh:mm a",chatMessage.getMessageTime()));

        return listViwItem;
    }



}