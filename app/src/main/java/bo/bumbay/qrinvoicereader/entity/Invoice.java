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

    @Column(name = "EmissionDate")
    public Date emissionDate;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "ControlCode")
    public String controlCode;

    @Column(name = "Owner")
    public String owner;

    @Column(name = "OriginalText")
    public String originalText;

    @Column(name = "InvoiceForm", onDelete = Column.ForeignKeyAction.CASCADE)
    public InvoiceForm form;

    public Invoice() {
        super();
    }

    public Invoice(String authorization, String nit, String number, Date emissionDate, int amount, String controlCode, String owner, String originalText, InvoiceForm form) {
        super();
        this.authorization = authorization;
        this.nit = nit;
        this.number = number;
        this.emissionDate = emissionDate;
        this.amount = amount;
        this.controlCode = controlCode;
        this.form = form;
        this.owner = owner;
        this.originalText = originalText;
    }
}
