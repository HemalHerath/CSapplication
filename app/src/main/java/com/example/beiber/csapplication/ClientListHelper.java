package com.example.beiber.csapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BEIBER on 6/20/2017.
 */

public class ClientListHelper extends ArrayAdapter<userInformation> {

    private Activity context;
    private List<userInformation> clientList;

    public ClientListHelper(Activity context, List<userInformation> clientList){
        super(context , R.layout.list_layout,clientList);
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViwItem = inflater.inflate(R.layout.list_layout , null ,true);

        TextView textViewName =(TextView) listViwItem.findViewById(R.id.textViewName);
        TextView textViewAddress =(TextView) listViwItem.findViewById(R.id.textViewAddress);
        TextView textViewTel =(TextView) listViwItem.findViewById(R.id.textViewTel);
        TextView textViewAccNo =(TextView) listViwItem.findViewById(R.id.textViewAccNo);
        TextView textViewCardNo =(TextView) listViwItem.findViewById(R.id.textViewCardNo);
        TextView textViewBranch =(TextView) listViwItem.findViewById(R.id.textViewBranch);

        userInformation userInformation = clientList.get(position);

        textViewName.setText(userInformation.getFirstName());
        textViewAddress.setText(userInformation.getAddress());
        textViewTel.setText(userInformation.getTel());
        textViewAccNo.setText(userInformation.getAccNo());
        textViewCardNo.setText(userInformation.getCardNo());
        textViewBranch.setText(userInformation.getBranch());

        return listViwItem;

    }
}
