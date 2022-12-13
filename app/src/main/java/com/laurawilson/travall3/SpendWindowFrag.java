package com.laurawilson.travall3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laurawilson.travall3.databinding.SpendWindowFragmentBinding;

import java.util.ArrayList;


public class SpendWindowFrag extends Fragment implements ExpenseBox.OnInputSelected{


    private SpendWindowFragmentBinding binding;
    private Button add;
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<Double> prices = new ArrayList<>();
    private static NamesCustomAdapter mNameAdapter;
    private static RecyclerView mNamesRecyclerView;
    private static PricesCustomAdapter mPriceAdapter;
    private static RecyclerView mPricesRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ///////////////////
//    private static SpendAdapter spendAdapter;
    /////////////////

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = SpendWindowFragmentBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mNamesRecyclerView = view.findViewById(R.id.recyclerViewNames);
        mPricesRecyclerView = view.findViewById(R.id.recyclerViewPrices);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mNamesRecyclerView.setLayoutManager(mLayoutManager);

        System.out.println("names size: " + names.size());
        mPriceAdapter = new PricesCustomAdapter(prices, getActivity());
        mNameAdapter = new NamesCustomAdapter(names, SpendWindowFrag.this, getParentFragmentManager(), mPriceAdapter);


        mNamesRecyclerView.setAdapter(mNameAdapter);
        mPricesRecyclerView.setAdapter(mPriceAdapter);

//        ////////////////////
//        spendAdapter = new SpendAdapter(names, prices, SpendWindowFrag.this, getParentFragmentManager());
//        mPricesRecyclerView.setAdapter(spendAdapter);
//        mNamesRecyclerView.setAdapter(spendAdapter);
        /////////////////////////


        add = view.findViewById(R.id.AddExpenseButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExpenseBox expenseBox = new ExpenseBox();
                expenseBox.setTargetFragment(SpendWindowFrag.this, 1);
                expenseBox.show(getParentFragmentManager(), "ExpenseBox");

            }
        });

    }


    // on below line we are creating
    // a new function to add item.
    private void addItemNames(String item) {
        // on below line we are checking
        // if item is empty or not.
        if (!item.isEmpty()) {
            // on below line we are adding
            // item to our list
            names.add(item);
            // on below line we are notifying
            // adapter that data has updated.
            mNameAdapter.notifyDataSetChanged();
        }
    }

    // on below line we are creating
    // a new function to add item.
    private void addItemPrices(Double item) {
        // on below line we are checking
        // if item is empty or not.
        if (item >= 0) {
            // on below line we are adding
            // item to our list
            prices.add(item);
            // on below line we are notifying
            // adapter that data has updated.
            mPriceAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void sendInput(String name, double price) {
        System.out.println("got input");
        if(names.size()==0 || prices.size()==0)
        {
            names.add(name);
            prices.add(price);

            mPriceAdapter = new PricesCustomAdapter(prices, getActivity());
            mNameAdapter = new NamesCustomAdapter(names, SpendWindowFrag.this, getParentFragmentManager(), mPriceAdapter);


            mNamesRecyclerView.setAdapter(mNameAdapter);
            mPricesRecyclerView.setAdapter(mPriceAdapter);
        }
        else
        {
            addItemNames(name);
            addItemPrices(price);
        }
    }

    // on below line we are creating
    // a new function to add item.
    private void changeItemNames(String item) {
        // on below line we are checking
        // if item is empty or not.
        if (!item.isEmpty()) {
            // on below line we are adding
            // item to our list
            names.add(item);
            // on below line we are notifying
            // adapter that data has updated.
            mNameAdapter.notifyDataSetChanged();
        }
    }

    // on below line we are creating
    // a new function to add item.
    private void changeItemPrices(Double item) {
        // on below line we are checking
        // if item is empty or not.
        if (item >= 0) {
            // on below line we are adding
            // item to our list
            prices.add(item);
            // on below line we are notifying
            // adapter that data has updated.
            mPriceAdapter.notifyDataSetChanged();
        }
    }
}

