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

package com.laurawilson.travall3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 *
 * Provide views to RecyclerView with data from mDataSet.
 */
public class PricesCustomAdapter extends RecyclerView.Adapter<PricesCustomAdapter.ViewHolder> {

    private static ArrayList<Double> prices;

    public ArrayList<Double> getPrices()
    {
        return prices;
    }

    public PricesCustomAdapter(ArrayList<Double> prices, Context context)
    {
        this.prices = prices;
    }

    @NonNull
    @Override
    public PricesCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
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
    public void onBindViewHolder(@NonNull PricesCustomAdapter.ViewHolder holder, int position) {
        // on below line we are setting text to our text view.
        holder.lngTV.setText(prices.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return prices.size();
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


                }
            });
        }
    }

}
