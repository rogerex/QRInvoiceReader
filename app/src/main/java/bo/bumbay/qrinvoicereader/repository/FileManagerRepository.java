package bo.bumbay.qrinvoicereader.repository;

import com.activeandroid.query.Select;

import java.util.List;

import bo.bumbay.qrinvoicereader.entity.InvoiceForm;

public class FileManagerRepository {
    public static String[] getInvoiceForms() {
        List<InvoiceForm> invoices = new Select()
                .from(InvoiceForm.class)
                .orderBy("Name ASC")
                .execute();

        String[] array = new String[invoices.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = invoices.get(i).name;
        }
        return array;
    }
}
