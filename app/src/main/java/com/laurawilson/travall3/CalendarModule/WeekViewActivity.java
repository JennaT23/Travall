package com.laurawilson.travall3.CalendarModule;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.laurawilson.travall3.CalendarModule.CalendarUtils.daysInWeekArray;
import static com.laurawilson.travall3.CalendarModule.CalendarUtils.monthYearFromDate;

import com.laurawilson.travall3.R;

public class WeekViewActivity extends Fragment implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_view_fragment, container, false);
        initWidgets(view);
        setWeekView();

        Button dailyButton = view.findViewById(R.id.DailyAction);
        Button newEvent = view.findViewById(R.id.NewEventAction);

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WeekViewActivity.this)
                        .navigate(R.id.action_weekViewActivity_to_dailyCalendarActivity);
            }
        });

        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WeekViewActivity.this)
                        .navigate(R.id.action_weekViewActivity_to_eventEditActivity);
            }
        });

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//


//        binding.previousMonthAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                weeklyAction(view);
//                System.out.println("Onclick weekhgd5044");
//                NavHostFragment.findNavController(ItineraryWindowFrag.this)
//                        .navigate(R.id.action_weekViewActivity_to_dailyCalendarActivity);
//            }
//        });
//    }

    private void initWidgets(View view)
    {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(getActivity(), EventEditActivity.class));

//        // view
//        Intent intent = new Intent(getActivity(), EventEditActivity.class);
//        startActivity(intent);
    }

    public void dailyAction(View view)
    {
        //startActivity(new Intent(this, DailyCalendarActivity.class));

        // view
        Intent intent = new Intent(getActivity(), DailyCalendarActivity.class);
        startActivity(intent);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
    }
}