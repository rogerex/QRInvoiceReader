package bo.bumbay.qrinvoicereader.cursor;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import bo.bumbay.qrinvoicereader.R;

public class FileCursorAdapter extends CursorAdapter {
    public FileCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameLabel = (TextView) view.findViewById(R.id.file_label_name);
        TextView invoicesLabel = (TextView) view.findViewById(R.id.file_label_invoices_amount);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));

        nameLabel.setText(name);
        invoicesLabel.setText(("(0)"));
    }
}
