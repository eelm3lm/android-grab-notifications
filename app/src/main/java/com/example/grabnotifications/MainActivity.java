package com.example.grabnotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    //List items = new ArrayList<>();

    public ArrayList<Transaction> transactions2 = new ArrayList<>();

    Handler handler;
    private static final int UPDATE = 100;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StringPostAPI stringPostAPI = retrofit.create(StringPostAPI.class);

        Call<List<Transaction>> call = stringPostAPI.getTransactions();

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Transaction> transactions = response.body();

                for (Transaction transaction : transactions) {
                    String content = "";
                    content += "Account: " + transaction.getAccount() + "\n";
                    content += "Amount: " + transaction.getAmount() + "\n";
                    content += "Date: " + transaction.getDate() + "\n";
                    content += "Category: " + transaction.getCategory() + "\n\n";

                    //items.add(content);

                    transactions2.add(transaction);

                    handler.sendEmptyMessage(UPDATE);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                return;
            }
        });

        //final ArrayAdapter<List> adapter = new ArrayAdapter<List>(this, android.R.layout.simple_list_item_1, items);
        final TransactionAdapter adapter = new TransactionAdapter(this, transactions2);

        ListView listView = findViewById(R.id.trans_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, items.get(position).toString(), Toast.LENGTH_LONG).show();
                //TransactionFragment fragment = TransactionFragment.newInstance(items.get(position).toString(), items.get(position).toString());
                TransactionFragment fragment = TransactionFragment.newInstance(transactions2.get(position).getAmount(), transactions2.get(position).getPayee());
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == UPDATE){
                    adapter.notifyDataSetChanged();
                }
            }

        };

    }
}
