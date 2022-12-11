package com.laurawilson.travall3;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseBox extends DialogFragment {


    //widgets

    public EditText item, amount;
    public Button save, cancel;
    public SpendWindowFrag spendFrag;
    ArrayList <String> expense = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);

        save = view.findViewById(R.id.cancel);
        cancel = view.findViewById(R.id.save);
        spendFrag = (SpendWindowFrag) getParentFragmentManager().findFragmentById(R.id.SpendWindowFrag);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = item.getText().toString();
                String price = amount.getText().toString();
                if(!input.equals("") && !price.equals("")) {

                    spendFrag.item = view.findViewById(R.id.textView);
                    spendFrag.item.setText(input);
                }


            }
        });

        return view;
    }
}
