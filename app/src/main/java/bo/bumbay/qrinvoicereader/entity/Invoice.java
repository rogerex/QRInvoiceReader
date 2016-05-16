package bo.bumbay.qrinvoicereader.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Invoices")
public class Invoice extends Model {
    @Column(name = "Authorization")
    public String authorization;

    @Column(name = "Nit")
    public String nit;

    @Column(name = "Number")
    public String number;

    @Column(name = "Date")
    public Date date;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "Control")
    public String control;

    @Column(name = "InvoiceForm", onDelete = Column.ForeignKeyAction.CASCADE)
    public InvoiceForm form;

    public Invoice() {
        super();
    }

    public Invoice(String authorization, String nit, String number, Date date, int amount, String control, InvoiceForm form) {
        super();
        this.authorization = authorization;
        this.nit = nit;
        this.number = number;
        this.date = date;
        this.amount = amount;
        this.control = control;
        this.form = form;
    }
}
