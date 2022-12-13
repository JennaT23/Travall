package com.laurawilson.travall3.ExpenseModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.laurawilson.travall3.ExpenseModule.ScanPriceModule.TextViewModel;
import com.laurawilson.travall3.R;
import com.laurawilson.travall3.databinding.CurrencyWindowFragmentBinding;


public class CurrencyWindowFrag extends Fragment {

    private CurrencyWindowFragmentBinding binding;

    private double amount;
    private String countryFrom;
    private String countryTo;
    private double convertedAmount;
    private TextViewModel viewModel;
    private ArrayList<String> countries = new ArrayList<>();
    private Dictionary<String, Double> rates = new Hashtable<String, Double>();
    private int returnAction = R.id.action_selectTextFrag_to_CurrencyWindowFrag;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CurrencyWindowFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    // when this fragment is displayed
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TextViewModel.class);

        Button scanPriceButton = view.findViewById(R.id.scanPrice_btn);
        Button submitButton = view.findViewById(R.id.submitButton_btn);

        EditText amount_et   = view.findViewById(R.id.amountToConvert_et);
        TextView convertedAmount_tv = view.findViewById(R.id.convertedAmount_tv);
        convertedAmount_tv.setText("");


        // send api call to get list of countries (theoretically)
        getCountryRates();

        // initiate drop-down menus
        Spinner countriesFrom_sp = view.findViewById(R.id.fromCountries_sp);
        countriesFrom_sp.setOnItemSelectedListener(new FromCountriesSpinnerClass());
        ArrayAdapter<String> ad = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countriesFrom_sp.setAdapter(ad);

        Spinner countriesTo_sp = view.findViewById(R.id.toCountries_sp);
        countriesTo_sp.setOnItemSelectedListener(new ToCountriesSpinnerClass());
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countriesTo_sp.setAdapter(ad2);

        // when scan price button is hit, go to scan price fragment
        binding.scanPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setReturnAction(returnAction);

                NavHostFragment.findNavController(CurrencyWindowFrag.this)
                        .navigate(R.id.action_CurrencyWindowFrag_to_selectImageFrag);
            }
        });

        getParentFragmentManager().setFragmentResultListener("amountRequestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String result = bundle.getString("amountKey");

                // parsed the amount selected to exclude currency symbols
                Pattern p = Pattern.compile("((\\d+\\s\\d+)+\\.\\d+,\\d+)|((\\d+,\\d+)+\\.\\d+)|((\\d+\\.\\d+)+,\\d+)|((\\d+\\s\\d+)+,\\d+)+|(\\d+\\.\\d+)+|(\\d+,\\d+)+|(\\d+\\s\\d+)+");
                Matcher m = p.matcher(result);
                if(m.find())
                {
                    amount = Double.parseDouble(m.group());
                }

                amount_et.setText(String.valueOf(amount));
                convertedAmount_tv.setText("");
            }
        });

        amount_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                convertedAmount_tv.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        // gets all values when submit button clicked, converts, and displays results
        submitButton.setOnClickListener(
                new View.OnClickListener()
                {

                    public void onClick(View view)
                    {
                        amount = Double.parseDouble(amount_et.getText().toString()); // get amount
                        convertedAmount = convert(amount); // convert amount
                        convertedAmount_tv.setText(String.valueOf(convertedAmount)); // display converted amount
                    }
                });

    }


    class FromCountriesSpinnerClass implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            countryFrom = countries.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class ToCountriesSpinnerClass implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            countryTo = countries.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


    private double convert(double amount)
    {
        double rateFrom = rates.get(countryFrom);
        double usd = amount/rateFrom;
        double rateTo = rates.get(countryTo);
        double convertAmount = usd*rateTo;

        DecimalFormat df = new DecimalFormat("#.##");
        convertAmount = Double.parseDouble(df.format(convertAmount));

        return convertAmount;
    }


    private void getCountryRates()
    {
        rates.put("USA", 1.0);
        rates.put("EUR", 0.95);
        rates.put("CAD", 1.36);
        rates.put("MXN", 19.64);

        Enumeration<String> enu = rates.keys();
        for (int i = 0; i < rates.size(); i++)
        {
            countries.add(enu.nextElement());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
