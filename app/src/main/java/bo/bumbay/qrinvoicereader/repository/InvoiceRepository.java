package bo.bumbay.qrinvoicereader.repository;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;

import java.util.List;

import bo.bumbay.qrinvoicereader.entity.Invoice;

public class InvoiceRepository {
    public static List<Invoice> getInvoices(long formId) {
        return new Select()
                .from(Invoice.class)
                .where("InvoiceForm = ?", formId)
                .orderBy("EmissionDate ASC")
                .execute();
    }

    public static Cursor getCursorForInvoices(long formId) {
        String tableName = Cache.getTableInfo(Invoice.class).getTableName();
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(Invoice.class).
                where("InvoiceForm = ?", formId).
                orderBy("EmissionDate ASC").
                toSql();

        return Cache.openDatabase().rawQuery(resultRecords, new String[]{ formId + "" });
    }

    public static void save(Invoice invoice) {
        invoice.save();
    }
}
