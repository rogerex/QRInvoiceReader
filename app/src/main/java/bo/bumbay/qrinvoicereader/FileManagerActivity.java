package bo.bumbay.qrinvoicereader;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import bo.bumbay.qrinvoicereader.common.UpdateListener;
import bo.bumbay.qrinvoicereader.cursor.FileCursorAdapter;
import bo.bumbay.qrinvoicereader.cursor.FolderCursorAdapter;
import bo.bumbay.qrinvoicereader.repository.FileManagerRepository;

public class FileManagerActivity extends AppCompatActivity implements UpdateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.file_manager_toolbar);
        setSupportActionBar(myToolbar);

        ListView folderListView = loadFolders();
        ListView fileListView = loadFiles();

        folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(FileManagerActivity.this, FileManagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(FileManagerActivity.this, InvoicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private ListView loadFolders() {
        ListView folderListView = (ListView) findViewById(R.id.folder_list);
        loadFolders(folderListView);

        return folderListView;
    }

    private ListView loadFiles() {
        ListView fileListView = (ListView) findViewById(R.id.file_list);
        loadFiles(fileListView);

        return fileListView;
    }

    private void loadFolders(ListView listView) {
        Cursor cursor = FileManagerRepository.getCursorForFolders(getId());
        CursorAdapter adapter = new FolderCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    private void loadFiles(ListView listView) {
        Cursor cursor = FileManagerRepository.getCursorForInvoiceForms(getId());
        CursorAdapter adapter = new FileCursorAdapter(this, cursor);
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
            case R.id.action_file_manager_add_folder:
                addFolder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addFolder() {
        DialogFragment newFragment = new CreateFolderDialogFragment();
        Bundle args = new Bundle();
        args.putLong("id", getId());
        newFragment.setArguments(args);

        newFragment.show(getSupportFragmentManager(), "createFolder");
    }

    public void addForm(View v) {
        Intent intent = new Intent(this, CreateFormActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFiles();
    }

    private long getId() {
        Bundle bundle = getIntent().getExtras();
        long formId;
        if(bundle != null) {
            formId = bundle.getLong("id");
        } else {
            formId = -1;
            finish();
        }

        return formId;
    }

    @Override
    public void update() {
        loadFolders();
    }

    public void showPopup(View v) {
        long id = (long)v.getTag();
        FolderItemListener listener = new FolderItemListener(id);
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.item_file_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(listener);
        popup.show();
    }

    class FolderItemListener implements PopupMenu.OnMenuItemClickListener {

        private long id;

        FolderItemListener(long id) {
            this.id = id;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_file_rename:
                    rename();
                    return true;
                default:
                    return false;
            }
        }

        public void rename() {
            DialogFragment newFragment = new UpdateFolderDialogFragment();
            Bundle args = new Bundle();
            args.putLong("id", id);
            newFragment.setArguments(args);

            newFragment.show(getSupportFragmentManager(), "updateFolder");
        }
    }
}
