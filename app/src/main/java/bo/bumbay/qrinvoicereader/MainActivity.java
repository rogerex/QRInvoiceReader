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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        final ListView listView = (ListView) findViewById(R.id.form_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, options());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0: // Documents
                        Intent intent = new Intent(MainActivity.this, FileManagerActivity.class);
                        startActivity(intent);
                        break;
                    case 1: // Recycle Trash
                        break;
                    case 2: // Bucket
                        break;
                }
            }
        });
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
}
