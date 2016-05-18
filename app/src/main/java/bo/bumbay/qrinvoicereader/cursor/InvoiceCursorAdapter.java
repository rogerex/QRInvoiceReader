package bo.bumbay.qrinvoicereader.cursor;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import bo.bumbay.qrinvoicereader.R;
import bo.bumbay.qrinvoicereader.common.DateFormatter;

public class InvoiceCursorAdapter extends CursorAdapter {
    public InvoiceCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_invoice, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView amountLabel = (TextView) view.findViewById(R.id.invoice_value_amount);
        TextView nitLabel = (TextView) view.findViewById(R.id.invoice_value_nit);
        TextView numberLabel = (TextView) view.findViewById(R.id.invoice_value_number);
        TextView authorizationLabel = (TextView) view.findViewById(R.id.invoice_value_authorization);
        TextView controlCodeLabel = (TextView) view.findViewById(R.id.invoice_value_control_code);
        TextView emissionDateLabel = (TextView) view.findViewById(R.id.invoice_value_emission_date);
        TextView ownerLabel = (TextView) view.findViewById(R.id.invoice_value_owner_nit);

        String amount = cursor.getString(cursor.getColumnIndexOrThrow("Amount"));
        String nit = cursor.getString(cursor.getColumnIndexOrThrow("Nit"));
        String number = cursor.getString(cursor.getColumnIndexOrThrow("Number"));
        String authorization = cursor.getString(cursor.getColumnIndexOrThrow("Authorization"));
        String controlCode = cursor.getString(cursor.getColumnIndexOrThrow("ControlCode"));
        String emissionDate = DateFormatter.format(cursor.getString(cursor.getColumnIndexOrThrow("EmissionDate")));
        String owner = cursor.getString(cursor.getColumnIndexOrThrow("Owner"));

        amountLabel.setText(amount);
        nitLabel.setText(nit);
        numberLabel.setText(number);
        authorizationLabel.setText(authorization);
        controlCodeLabel.setText(controlCode);
        emissionDateLabel.setText(emissionDate);
        ownerLabel.setText(owner);
    }
}