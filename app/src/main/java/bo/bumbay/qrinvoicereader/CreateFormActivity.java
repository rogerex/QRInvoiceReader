package bo.bumbay.qrinvoicereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import bo.bumbay.qrinvoicereader.entity.InvoiceForm;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;

public class CreateFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.create_form_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_form_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        EditText formNameControl = (EditText)findViewById(R.id.form_name);
        EditText targetAmountControl = (EditText)findViewById(R.id.form_target_amount);
        CheckBox blockedControl = (CheckBox)findViewById(R.id.form_blocked);
        DatePicker presentationDateControl = (DatePicker)findViewById(R.id.form_presentation_date);

        // Validations
        if(TextUtils.isEmpty(formNameControl.getText().toString())) {
            formNameControl.setError("The field is required");
            return;
        }
        if(TextUtils.isEmpty(targetAmountControl.getText().toString())) {
            targetAmountControl.setError("The field is required");
            return;
        }

        String formName = formNameControl.getText().toString();
        int targetAmount = Integer.parseInt(targetAmountControl.getText().toString());
        boolean blocked = blockedControl.isChecked();

        int day = presentationDateControl.getDayOfMonth();
        int month = presentationDateControl.getMonth() + 1;
        int year = presentationDateControl.getYear();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month - 1, day, 0, 0);
        Date presentationDate = cal.getTime();

        InvoiceForm form = new InvoiceForm(formName, presentationDate, targetAmount, blocked);
        FileManagerRepository.save(form);

        finish();
    }
}
