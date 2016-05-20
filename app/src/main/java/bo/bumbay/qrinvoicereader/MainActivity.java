package bo.bumbay.qrinvoicereader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import java.util.Date;

import bo.bumbay.qrinvoicereader.entity.Folder;
import bo.bumbay.qrinvoicereader.entity.InvoiceForm;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String PREFERENCE_FIRST_RUN = "partitionsCreated";
    private static final String PREFERENCE_DOCUMENTS_ID = "documentsId";
    private static final String PREFERENCE_RECYCLE_BIN_ID = "recycleBinId";
    private static final String PREFERENCE_BUCKET_ID = "bucketId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        final ListView listView = (ListView) findViewById(R.id.item_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, options());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        createStaticItems();
    }

    private String[] options() {
        return new String[] {
                "Documents",
                "Recycle Trash",
                "Bucket"
        };
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
            case R.id.action_main_insights:
                insights();
                return true;
            case R.id.action_main_settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void insights() {
    }

    public void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void createStaticItems() {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = p.getBoolean(PREFERENCE_FIRST_RUN, true);
        p.edit().putBoolean(PREFERENCE_FIRST_RUN, false).commit();

        if (firstRun) {
            Folder documents = new Folder("Documents");
            FileManagerRepository.save(documents);

            Folder recycleBin = new Folder("Recycle Bin");
            FileManagerRepository.save(recycleBin);

            Folder bucketFolder = new Folder("Bucket Folder");
            FileManagerRepository.save(bucketFolder);

            InvoiceForm bucket = new InvoiceForm("Bucket", new Date(), 0, false, bucketFolder);
            FileManagerRepository.save(bucket);

            p.edit().putLong(PREFERENCE_DOCUMENTS_ID, documents.getId()).commit();
            p.edit().putLong(PREFERENCE_RECYCLE_BIN_ID, recycleBin.getId()).commit();
            p.edit().putLong(PREFERENCE_BUCKET_ID, bucket.getId()).commit();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        switch (position) {
            case 0: // Documents
                Intent intent1 = new Intent(MainActivity.this, FileManagerActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putLong("id", preferences.getLong(PREFERENCE_DOCUMENTS_ID, 0));
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case 1: // Recycle Trash
                Intent intent2 = new Intent(MainActivity.this, FileManagerActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putLong("id", preferences.getLong(PREFERENCE_RECYCLE_BIN_ID, 0));
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case 2: // Bucket
                Intent intent3 = new Intent(MainActivity.this, InvoicesActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putLong("id", preferences.getLong(PREFERENCE_BUCKET_ID, 0));
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
        }
    }
}
