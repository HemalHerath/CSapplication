package com.example.beiber.csapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by BEIBER on 6/27/2017.
 */

public class ChatHelper extends ArrayAdapter<ChatMessage> {
    private Activity context;
    private List<ChatMessage> messageList;

    public ChatHelper(Activity context, List<ChatMessage> messageList) {
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

        ChatMessage chatMessage = messageList.get(position);

        message_user.setText(chatMessage.getMessageUser());
        message_text.setText(chatMessage.getMessageText());

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        message_time.setText(currentDateTimeString);

        return listViwItem;
    }
}