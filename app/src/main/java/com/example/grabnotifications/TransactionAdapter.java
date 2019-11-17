package com.example.grabnotifications;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        Transaction currentTransaction = getItem(position);

        TextView account = listItemView.findViewById(R.id.list_account);
        account.setText(currentTransaction.getAccount());

        TextView amount = listItemView.findViewById(R.id.list_amount);
        amount.setText(currentTransaction.getAmount());

        TextView payee = listItemView.findViewById(R.id.list_payee);
        payee.setText(currentTransaction.getPayee());

        TextView date = listItemView.findViewById(R.id.list_date);
        date.setText(currentTransaction.getDate());

        TextView category = listItemView.findViewById(R.id.list_category);
        category.setText(currentTransaction.getCategory());

        TextView notes = listItemView.findViewById(R.id.list_notes);
        notes.setText(currentTransaction.getNotes());

        return listItemView;
    }
}
