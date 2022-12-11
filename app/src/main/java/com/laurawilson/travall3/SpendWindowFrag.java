package com.laurawilson.travall3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.laurawilson.travall3.databinding.PopupBinding;
import com.laurawilson.travall3.databinding.SpendWindowFragmentBinding;

import java.util.List;

public class SpendWindowFrag extends Fragment {

    public Button add;
    public TextView item;
    ExpenseBox eB = new ExpenseBox();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.spend_window_fragment, container, false);
       add = view.findViewById(R.id.AddExpenseButton);
       item = view.findViewById(R.id.textView);



       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ExpenseBox expenseBox = new ExpenseBox();
               expenseBox.show(getParentFragmentManager(), "ExpenseBox");

           }
       });

       return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        com.laurawilson.travall3.databinding.SpendWindowFragmentBinding binding = null;
    }



}

