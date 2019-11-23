package com.example.grabnotifications;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFragment extends Fragment {

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_trans_fragment, container, false);

        Toast.makeText(getContext(), "Add Trans Fragment", Toast.LENGTH_SHORT).show();

        Button button = v.findViewById(R.id.fragment_add_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Transaction transaction;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                StringPostAPI stringPostAPI = retrofit.create(StringPostAPI.class);

                EditText account_text = getView().findViewById(R.id.add_account);
                Editable account_edit = account_text.getText();
                String account = account_edit.toString();

                EditText date_text = getView().findViewById(R.id.add_date);
                Editable date_edit = date_text.getText();
                String date = date_edit.toString();

                EditText amount_text = getView().findViewById(R.id.add_amount);
                Editable amount_edit = amount_text.getText();
                String amount = amount_edit.toString();

                EditText payee_text = getView().findViewById(R.id.add_payee);
                Editable payee_edit = payee_text.getText();
                String payee = payee_edit.toString();

                EditText category_text = getView().findViewById(R.id.add_category);
                Editable category_edit = category_text.getText();
                String category = category_edit.toString();

                EditText notes_text = getView().findViewById(R.id.add_notes);
                Editable notes_edit = notes_text.getText();
                String notes = notes_edit.toString();

                if (date.equals("Date")) {
                    transaction = new Transaction(account, amount, payee, category, notes);
                } else {
                    transaction = new Transaction(account, date, amount, payee, category, notes);
                }

                Call<Transaction> call = stringPostAPI.create(transaction);

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
