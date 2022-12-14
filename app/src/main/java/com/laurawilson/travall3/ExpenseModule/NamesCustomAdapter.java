/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.laurawilson.travall3.ExpenseModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.laurawilson.travall3.R;

import java.util.ArrayList;


/**
 *
 * Provide views to RecyclerView with data from mDataSet.
 */
public class NamesCustomAdapter extends RecyclerView.Adapter<NamesCustomAdapter.ViewHolder> {

    private static ArrayList<String> names;
    private Fragment fragment;
    private FragmentManager fragMang;
    private PricesCustomAdapter pca;

    public NamesCustomAdapter(ArrayList<String> names)
    {
        this.names = names;
        this.fragment = fragment;
        this.fragMang = fragMang;
        this.pca = pca;
    }
//    public NamesCustomAdapter(ArrayList<String> names, Fragment fragment, FragmentManager fragMang, PricesCustomAdapter pca)
//    {
//        this.names = names;
//        this.fragment = fragment;
//        this.fragMang = fragMang;
//        this.pca = pca;
//    }

    @NonNull
    @Override
    public NamesCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lng_rv_item, parent, false);

        // at last we are returning our view holder
        // class with our item View File.
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NamesCustomAdapter.ViewHolder holder, int position) {
        // on below line we are setting text to our text view.
        holder.lngTV.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private TextView lngTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initialing our variable.
            lngTV = itemView.findViewById(R.id.idTVLngName);

            // Define click listener for the ViewHolder's View.
            lngTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("clicked item");

//                    String name = names.get(getAdapterPosition());
//                    double price = pca.getPrices().get(getAdapterPosition());
//
//
//                    ExpenseBox expenseBox = new ExpenseBox();
//                    expenseBox.item = v.findViewById(R.id.itemText);
//                    expenseBox.item.setText(name);
//                    expenseBox.amount = v.findViewById(R.id.amountBox);
//                    expenseBox.amount.setText(Double.valueOf(price).toString());
//                    expenseBox.setTargetFragment(fragment, 1);
//                    expenseBox.show(fragMang, "ExpenseBox");

                }
            });
        }
    }

}
