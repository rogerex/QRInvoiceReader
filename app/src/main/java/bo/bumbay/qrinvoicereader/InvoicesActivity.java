package bo.bumbay.qrinvoicereader;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import bo.bumbay.qrinvoicereader.common.DateFormatter;
import bo.bumbay.qrinvoicereader.cursor.InvoiceCursorAdapter;
import bo.bumbay.qrinvoicereader.entity.Invoice;
import bo.bumbay.qrinvoicereader.entity.InvoiceForm;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;
import bo.bumbay.qrinvoicereader.repository.InvoiceRepository;

public class InvoicesActivity extends AppCompatActivity {

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoices);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.invoices_toolbar);
        setSupportActionBar(myToolbar);

        ListView listView = (ListView) findViewById(R.id.invoice_list);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        Cursor todoCursor = InvoiceRepository.getCursorForInvoices(getId());
        InvoiceCursorAdapter todoAdapter = new InvoiceCursorAdapter(this, todoCursor);
        listView.setAdapter(todoAdapter);
    }

    private long getId() {
        Bundle bundle = getIntent().getExtras();
        long formId = -1;
        if(bundle != null)
            formId = bundle.getLong("formId");

        return formId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.invoices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_invoices_qr_code:
                readQRCode();
                return true;
            case R.id.action_invoices_form_edit:
                editForm();
                return true;
            case R.id.action_invoices_write_file:
                writeFile();
                return true;
            case R.id.action_invoices_send_file:
                sendFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void readQRCode() {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException ex) {
            showDialog(InvoicesActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private void editForm() {
    }

    private void writeFile() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            writeToFile();
        }
    }

    private void sendFile() {
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException ex) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG).show();

                InvoiceForm form = FileManagerRepository.getInvoiceForm(getId());
                String[] parts = contents.split("[|]");

                Invoice newInvoice = new Invoice(parts[2], parts[0], parts[1], DateFormatter.parse(parts[3]), getAmount(parts[5]), parts[6], parts[7], contents, form);
                InvoiceRepository.save(newInvoice);
            }
        }
    }

    private int getAmount(String part) {
        int newAmount;
        double amount = Double.parseDouble(part);
        if (0.5 <= (amount - (int)amount)) {
            newAmount = (int)(amount + 1);
        } else {
            newAmount = (int)amount;
        }
        return newAmount;
    }

    private String getInvoicesData() {
        String data = new String();
        for (Invoice invoice : InvoiceRepository.getInvoices(getId())) {
            data += getInvoiceFormat(invoice) + "\n";
        }
        return data;
    }

    private String getInvoiceFormat(Invoice invoice) {
        String separator = "\t";
        String row = invoice.nit + separator;
        row += invoice.number + separator;
        row += invoice.authorization + separator;
        row += DateFormatter.format(invoice.emissionDate) + separator;
        row += invoice.amount + separator;
        row += invoice.controlCode;
        return row;
    }

    private void writeToFile() {
        try {
            String data = getInvoicesData();
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "invoices.txt");
            file.createNewFile();

            if (file.exists()) {
                FileOutputStream outputFile = new FileOutputStream(file);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputFile);
                outputStreamWriter.write(data);
                outputStreamWriter.close();
            }
        }
        catch (IOException e) {
            Toast.makeText(this, "The app was not able to save the file.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeToFile();
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
