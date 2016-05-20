package bo.bumbay.qrinvoicereader.cursor;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import bo.bumbay.qrinvoicereader.R;

public class FolderCursorAdapter extends CursorAdapter {
    public FolderCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_folder, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameLabel = (TextView) view.findViewById(R.id.folder_label_name);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));

        nameLabel.setText(name);
    }
}
