package bo.bumbay.qrinvoicereader.repository;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;

import bo.bumbay.qrinvoicereader.entity.InvoiceForm;

public class FileManagerRepository {
    public static InvoiceForm getInvoiceForm(long formId) {
        return new Select()
                .from(InvoiceForm.class)
                .where("Id = ?", formId)
                .executeSingle();
    }

    public static Cursor getCursorForInvoiceForms() {
        String tableName = Cache.getTableInfo(InvoiceForm.class).getTableName();
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(InvoiceForm.class).
                orderBy("Name ASC").
                toSql();

        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);
        return resultCursor;
    }

    public static void save(InvoiceForm form) {
        form.save();
    }
}
