package pk.farrakhj.countrypickerexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import pk.farrakhj.countrypickerlibrary.dto.Country;
import pk.farrakhj.countrypickerlibrary.views.CountriesActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPickCountry = (Button) findViewById(R.id.buttonPickCountry);
        buttonPickCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CountriesActivity.class), 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 101) {
                if(data==null || !data.hasExtra(CountriesActivity.COUNTRY))
                    return;

                Country country = (Country) data.getSerializableExtra(CountriesActivity.COUNTRY);

                Toast.makeText(MainActivity.this, country.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}