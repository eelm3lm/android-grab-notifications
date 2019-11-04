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

    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;


    public static TransactionFragment newInstance(String text1, String text2, String text3, String text4, String text5) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT1, text1);
        args.putString(ARG_TEXT2, text2);
        args.putString(ARG_TEXT3, text3);
        args.putString(ARG_TEXT4, text4);
        args.putString(ARG_TEXT5, text5);
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

        if (getArguments() != null) {
            text1 = getArguments().getString(ARG_TEXT1);
            text2 = getArguments().getString(ARG_TEXT2);
            text3 = getArguments().getString(ARG_TEXT3);
            text4 = getArguments().getString(ARG_TEXT4);
            text5 = getArguments().getString(ARG_TEXT5);
        }

        textView1.setText(text1);
        textView2.setText(text2);
        textView3.setText(text3);
        textView4.setText(text4);
        textView5.setText(text5);

        Button button = v.findViewById(R.id.fragment_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                StringPostAPI stringPostAPI2 = retrofit.create(StringPostAPI.class);

                EditText payee_text = getView().findViewById(R.id.fragment_payee);
                EditText category_text = getView().findViewById(R.id.fragment_category);

                Editable payee_edit = payee_text.getText();
                String payee = payee_edit.toString();
                Editable category_edit = category_text.getText();
                String category = category_edit.toString();

                //TextView date_text = getView().findViewById(R.id.fragment_date);
                //String date = date_text.toString();

                TextView date_text = getView().findViewById(R.id.fragment_date);
                String date = date_text.getText().toString();

                Transaction transaction = new Transaction(date, payee, category);

                Call<Transaction> call = stringPostAPI2.update(transaction);

                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        return v;
    }
}
