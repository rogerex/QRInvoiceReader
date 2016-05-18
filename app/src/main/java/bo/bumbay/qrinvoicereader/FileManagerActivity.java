package bo.bumbay.qrinvoicereader;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import bo.bumbay.qrinvoicereader.cursor.FileCursorAdapter;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;

public class FileManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.file_manager_toolbar);
        setSupportActionBar(myToolbar);

        ListView listView = (ListView) findViewById(R.id.file_list);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        loadFiles(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(FileManagerActivity.this, InvoicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("formId", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadFiles(ListView listView) {
        Cursor cursor = FileManagerRepository.getCursorForInvoiceForms();
        FileCursorAdapter adapter = new FileCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.file_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_file_manager_add_form:
                addForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addForm() {
        Intent intent = new Intent(this, CreateFormActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.file_list);
        loadFiles(listView);
    }
}
