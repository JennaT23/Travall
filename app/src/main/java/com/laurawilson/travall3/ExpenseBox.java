package com.laurawilson.travall3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class ExpenseBox extends DialogFragment {

    public OnInputSelected mOnInputSelected;

    //widgets
    public EditText item, amount;
    public Button save, cancel;
    public SpendWindowFrag spendFrag;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);


        cancel = view.findViewById(R.id.cancel);
        save = view.findViewById(R.id.save);
        spendFrag = (SpendWindowFrag) getParentFragmentManager().findFragmentById(R.id.SpendWindowFrag);

        item = view.findViewById(R.id.itemText);
        amount = view.findViewById(R.id.amountBox);


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
                System.out.println("input: " + input);
                System.out.println("price: " + price);

                if(!input.equals("") && !price.equals(""))
                {
                    // pass input and price back to spend frag
                    mOnInputSelected.sendInput(input, Double.parseDouble(price));
                }
                getDialog().dismiss();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(requireActivity().toString()
                    + " must implement OnDialogDismissListener");
        }
    }

    public interface OnInputSelected{
        void sendInput(String name, double price);

    }



}
