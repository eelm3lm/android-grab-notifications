package com.example.grabnotifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TransactionFragment extends Fragment {

    private static final String ARG_TEXT1 = "argText1";
    private static final String ARG_TEXT2 = "argText2";

    private String text1;
    private String text2;

    public static TransactionFragment newInstance(String text1, String text2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT1, text1);
        args.putString(ARG_TEXT2, text2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaction_fragment, container, false);
        TextView textView = v.findViewById(R.id.fragment_text);

        if (getArguments() != null) {
            text1 = getArguments().getString(ARG_TEXT1);
            text2 = getArguments().getString(ARG_TEXT2);
        }

        textView.setText(text1 + "\n" + text2);

        return v;
    }
}
