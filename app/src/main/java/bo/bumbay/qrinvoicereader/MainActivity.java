package bo.bumbay.qrinvoicereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import bo.bumbay.qrinvoicereader.entity.InvoiceForm;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        final ListView listView = (ListView) findViewById(R.id.form_list);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        ActiveAndroid.initialize(this);

        InvoiceForm form = new InvoiceForm("Form 01", new Date(), 1600);
        form.save();

        String[] values = getInvoiceForms();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemValue = (String)listView.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, InvoicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("formId", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private String[] getInvoiceForms() {
        List<InvoiceForm> invoices = new Select()
                .from(InvoiceForm.class)
                .orderBy("Name ASC")
                .execute();

        String[] array = new String[invoices.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = invoices.get(i).name;
        }
        return array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_add_form:
                addForm();
                return true;
            case R.id.action_main_settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addForm() {
    }

    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
