package com.example.grabnotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public ArrayList<Transaction> transactions = new ArrayList<>();

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

                List<Transaction> transaction = response.body();

                for (Transaction for_transaction : transaction) {
                    transactions.add(for_transaction);
                    handler.sendEmptyMessage(UPDATE);
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                return;
            }
        });

        final TransactionAdapter adapter = new TransactionAdapter(this, transactions);

        ListView listView = findViewById(R.id.trans_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TransactionFragment fragment = TransactionFragment.newInstance(transactions.get(position).getDate(), transactions.get(position).getAccount(), transactions.get(position).getAmount(), transactions.get(position).getPayee(), transactions.get(position).getCategory(), transactions.get(position).getNotes());
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                Fragment fragment = TransactionFragment.newInstance(transactions.get(position).getDate(), transactions.get(position).getAccount(), transactions.get(position).getAmount(), transactions.get(position).getPayee(), transactions.get(position).getCategory(), transactions.get(position).getNotes());
                FragmentTransaction tfragment = getSupportFragmentManager().beginTransaction();
                tfragment.replace(R.id.container, fragment);
                tfragment.addToBackStack(null);
                tfragment.commit();
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

        final Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AddFragment fragment2 = AddFragment.newInstance();
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                Fragment fragment = AddFragment.newInstance();
                FragmentTransaction afragment = getSupportFragmentManager().beginTransaction();
                afragment.replace(R.id.container, fragment);
                afragment.addToBackStack(null);
                afragment.commit();
            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.pullToRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                StringPostAPI stringPostAPI = retrofit.create(StringPostAPI.class);

                final Call<List<Transaction>> call = stringPostAPI.getTransactions();

                call.enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        List<Transaction> transaction = response.body();

                        transactions.clear();

                        for (Transaction for_transaction : transaction) {
                            transactions.add(for_transaction);
                            handler.sendEmptyMessage(UPDATE);
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        return;
                    }
                });
            }
        });

    }
}