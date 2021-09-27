package pk.farrakhj.countrypickerlibrary.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pk.farrakhj.countrypickerlibrary.R;
import pk.farrakhj.countrypickerlibrary.adapters.CountryAdapter;
import pk.farrakhj.countrypickerlibrary.dto.Country;
import pk.farrakhj.countrypickerlibrary.utils.Util;

public class CountriesActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        this.attachLayoutViews(this);
        loadCountries();
    }

    private void attachLayoutViews(Context context) {
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        recyclerViewCountries = (RecyclerView) findViewById(R.id.recyclerViewCountries);

        setUpSearchListener();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageViewBack) {
            performImageViewBackClickAction();
        }
    }

    private void performImageViewBackClickAction() {
        finish();
    }

    private void loadCountries() {
        countries = getCountriesList();

        if(countries==null || countries.size()==0)
            return;

        setUpRecyclerView();
    }

    private ArrayList<Country> getCountriesList() {
        String countriesJson = Util.getJsonFromAssets(CountriesActivity.this, "countries.json");

        if(countriesJson==null || countriesJson.isEmpty())
            return null;

        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Country>>(){}.getType();
        ArrayList<Country> countries = gson.fromJson(countriesJson, listType);

        return countries;
    }

    private void setUpRecyclerView() {
        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(CountriesActivity.this, LinearLayoutManager.VERTICAL, false));
        countryAdapter = new CountryAdapter(CountriesActivity.this, countries);
        countryAdapter.setProviderOnItemClickListener((country) -> {
            setResult(country);
        });
        recyclerViewCountries.setAdapter(countryAdapter);
    }

    private void setUpSearchListener() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(countryAdapter==null || countries==null || countries.size()==0)
                    return;

                countryAdapter.getFilter().filter(editTextSearch.getText().toString());
            }
        });
    }

    private void setResult(Country country) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(COUNTRY, country);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    ImageView imageViewBack;
    EditText editTextSearch;
    RecyclerView recyclerViewCountries;
    CountryAdapter countryAdapter;
    ArrayList<Country> countries = null;
    public static final String COUNTRY = "COUNTRY";
}
