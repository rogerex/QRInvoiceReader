package bo.bumbay.qrinvoicereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, FileManagerRepository.getInvoiceForms());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(FileManagerActivity.this, InvoicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("formId", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.file_manager, menu);
        return true;
    }
}
