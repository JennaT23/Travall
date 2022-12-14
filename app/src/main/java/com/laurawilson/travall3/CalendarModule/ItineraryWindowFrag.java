package com.laurawilson.travall3.CalendarModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.laurawilson.travall3.CalendarModule.CalendarUtils.daysInMonthArray;
import static com.laurawilson.travall3.CalendarModule.CalendarUtils.monthYearFromDate;

import com.laurawilson.travall3.CalendarModule.CalendarAdapter;
import com.laurawilson.travall3.CalendarModule.CalendarUtils;
import com.laurawilson.travall3.CalendarModule.WeekViewActivity;
import com.laurawilson.travall3.ExpenseModule.CurrencyWindowFrag;
import com.laurawilson.travall3.R;
import com.laurawilson.travall3.databinding.ItineraryWindowFragmentBinding;

public class ItineraryWindowFrag extends Fragment implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ItineraryWindowFragmentBinding binding;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        View view = inflater.inflate(R.layout.itinerary_window_fragment, container, false);
//
//        return view;

        binding = ItineraryWindowFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
//        super.onCreate(savedInstanceState);
        initWidgets(view);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        Button weeklyButton = view.findViewById(R.id.WeeklyAction);

        // weekly view button
        binding.WeeklyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(ItineraryWindowFrag.this)
                        .navigate(R.id.action_ItineraryWindowFrag_to_weekViewActivity);
            }
        });

    }


    private void initWidgets(View view)
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }


    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(getActivity(), WeekViewActivity.class));
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        System.out.println("onitemclick");
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

}