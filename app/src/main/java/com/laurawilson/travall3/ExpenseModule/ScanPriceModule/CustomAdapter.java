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

package com.laurawilson.travall3.ExpenseModule.ScanPriceModule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.laurawilson.travall3.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Fragment fragment;
    private ArrayList<String> textList;
    private ViewModelStoreOwner activity;
    private int resId;
    private Context activity_context;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View v, Fragment fragment, ArrayList<String> textList, ViewModelStoreOwner activity, Context activity_context, int resId) {
            super(v);

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String price = textList.get(getAdapterPosition());

                    // parsed the amount selected to exclude currency symbols
                    Pattern p = Pattern.compile("((\\d+\\s\\d+)+\\.\\d+,\\d+)|((\\d+,\\d+)+\\.\\d+)|((\\d+\\.\\d+)+,\\d+)|((\\d+\\s\\d+)+,\\d+)+|(\\d+\\.\\d+)+|(\\d+,\\d+)+|(\\d+\\s\\d+)+");
                    Matcher m = p.matcher(price);
                    if(!m.find())
                    {
                        // runs but doesn't display either toast
                        Toast.makeText(activity_context, "invalid type: must have a number", Toast.LENGTH_LONG).show();
                        Toast.makeText(v.getContext(), "invalid type: must have a number", Toast.LENGTH_LONG).show();
                        System.out.println("toast");
                    }
                    else
                    {
                        Bundle result = new Bundle();
                        result.putString("amountKey", price);
                        fragment.getParentFragmentManager().setFragmentResult("amountRequestKey", result);

                        NavHostFragment.findNavController(fragment)//thirdFragment.this
                                .navigate(resId);
                    }

                }
            });

            textView = v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }

    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(ArrayList<String> dataSet, Fragment frag, ViewModelStoreOwner activity, Context activity_context, int resId) {
        fragment = frag;
        textList = dataSet;
        this.activity = activity;
        this.resId = resId;
        this.activity_context = activity_context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(v, fragment, textList, activity, activity_context, resId);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTextView().setText(textList.get(position));

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return textList.size();
    }
}
