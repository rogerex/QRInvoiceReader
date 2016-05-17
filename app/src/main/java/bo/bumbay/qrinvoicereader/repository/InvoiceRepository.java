package bo.bumbay.qrinvoicereader.repository;

import com.activeandroid.query.Select;

import java.util.List;

import bo.bumbay.qrinvoicereader.entity.Invoice;

public class InvoiceRepository {
    public static List<Invoice> getInvoices(int formId) {
        return new Select()
                .from(Invoice.class)
                .where("InvoiceForm = ?", formId)
                .orderBy("Number ASC")
                .execute();
    }
}
