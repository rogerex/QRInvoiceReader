package bo.bumbay.qrinvoicereader.repository;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;

import bo.bumbay.qrinvoicereader.entity.Folder;
import bo.bumbay.qrinvoicereader.entity.InvoiceForm;

public class FileManagerRepository {
    public static Folder getFolder(long id) {
        return new Select()
                .from(Folder.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static InvoiceForm getInvoiceForm(long formId) {
        return new Select()
                .from(InvoiceForm.class)
                .where("Id = ?", formId)
                .executeSingle();
    }

    public static Cursor getCursorForInvoiceForms(long id) {
        String tableName = Cache.getTableInfo(InvoiceForm.class).getTableName();
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(InvoiceForm.class).
                where("Folder = ?", id).
                orderBy("Name ASC").
                toSql();

        return Cache.openDatabase().rawQuery(resultRecords, new String[]{ id + "" });
    }

    public static Cursor getCursorForFolders(long id) {
        String tableName = Cache.getTableInfo(Folder.class).getTableName();
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(Folder.class).
                where("Folder = ?", id).
                orderBy("Name ASC").
                toSql();

        return Cache.openDatabase().rawQuery(resultRecords, new String[]{ id + "" });
    }

    public static void save(InvoiceForm form) {
        form.save();
    }

    public static void save(Folder folder) {
        folder.save();
    }
}
