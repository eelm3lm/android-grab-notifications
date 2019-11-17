package com.example.grabnotifications;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionFragment extends Fragment {

    private static final String ARG_TEXT1 = "argText1";
    private static final String ARG_TEXT2 = "argText2";
    private static final String ARG_TEXT3 = "argText3";
    private static final String ARG_TEXT4 = "argText4";
    private static final String ARG_TEXT5 = "argText5";
    private static final String ARG_TEXT6 = "argText6";

    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String text6;


    public static TransactionFragment newInstance(String text1, String text2, String text3, String text4, String text5, String text6) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT1, text1);
        args.putString(ARG_TEXT2, text2);
        args.putString(ARG_TEXT3, text3);
        args.putString(ARG_TEXT4, text4);
        args.putString(ARG_TEXT5, text5);
        args.putString(ARG_TEXT6, text6);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaction_fragment, container, false);

        TextView textView1 = v.findViewById(R.id.fragment_date);
        TextView textView2 = v.findViewById(R.id.fragment_account);
        TextView textView3 = v.findViewById(R.id.fragment_amount);
        TextView textView4 = v.findViewById(R.id.fragment_payee);
        TextView textView5 = v.findViewById(R.id.fragment_category);
        TextView textView6 = v.findViewById(R.id.fragment_notes);

        if (getArguments() != null) {
            text1 = getArguments().getString(ARG_TEXT1);
            text2 = getArguments().getString(ARG_TEXT2);
            text3 = getArguments().getString(ARG_TEXT3);
            text4 = getArguments().getString(ARG_TEXT4);
            text5 = getArguments().getString(ARG_TEXT5);
            text6 = getArguments().getString(ARG_TEXT6);
        }

        textView1.setText(text1);
        textView2.setText(text2);
        textView3.setText(text3);
        textView4.setText(text4);
        textView5.setText(text5);
        textView6.setText(text6);

        Button button = v.findViewById(R.id.fragment_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                StringPostAPI stringPostAPI = retrofit.create(StringPostAPI.class);

                TextView date_text = getView().findViewById(R.id.fragment_date);
                String date = date_text.getText().toString();

                TextView account_text = getView().findViewById(R.id.fragment_account);
                String account = account_text.getText().toString();

                EditText amount_text = getView().findViewById(R.id.fragment_amount);
                Editable amount_edit = amount_text.getText();
                String amount = amount_edit.toString();

                EditText payee_text = getView().findViewById(R.id.fragment_payee);
                Editable payee_edit = payee_text.getText();
                String payee = payee_edit.toString();

                EditText category_text = getView().findViewById(R.id.fragment_category);
                Editable category_edit = category_text.getText();
                String category = category_edit.toString();

                EditText notes_text = getView().findViewById(R.id.fragment_notes);
                Editable notes_edit = notes_text.getText();
                String notes = notes_edit.toString();

                Transaction transaction = new Transaction(account, date, amount, payee, category, notes);

                Call<Transaction> call = stringPostAPI.update(transaction);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        return v;
    }
}
