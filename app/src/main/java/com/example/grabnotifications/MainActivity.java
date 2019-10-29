package com.example.grabnotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.main_text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StringPostAPI stringPostAPI = retrofit.create(StringPostAPI.class);

        Call<List<Transaction>> call = stringPostAPI.getTransactions();

        call.enqueue(new Callback<List<Transaction>>(){
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code " + response.code());
                    return;
                }

                List<Transaction> transactions = response.body();

                for (Transaction transaction : transactions) {
                    String content = "";
                    content += "Account: " + transaction.getAccount() + "\n";
                    content += "Amount: " + transaction.getAmount() + "\n";
                    content += "Date: " + transaction.getDate() + "\n";
                    content += "Category: " + transaction.getCategory() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
