package com.example.grabnotifications;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public TransactionAdapter(@NonNull Activity context, ArrayList<Transaction> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_transaction_item, parent, false);
        }

        //Toast.makeText(getContext(), "yay2", Toast.LENGTH_LONG).show();

        Transaction currentTransaction = getItem(position);

        TextView text = listItemView.findViewById(R.id.item_text);
        text.setText(currentTransaction.getAmount());

        return listItemView;
    }
}
