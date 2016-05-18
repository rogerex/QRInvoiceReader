package bo.bumbay.qrinvoicereader.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

@Table(name = "InvoiceForms")
public class InvoiceForm extends Model {
    @Column(name = "Name")
    public String name;

    @Column(name = "CreatedDate")
    public Date createdDate;

    @Column(name = "ModifiedDate")
    public Date modifiedDate;

    @Column(name = "PresentationDate")
    public Date presentationDate;

    @Column(name = "TargetAmount")
    public int targetAmount;

    @Column(name = "Blocked")
    public boolean blocked;

    public InvoiceForm() {
        super();
    }

    public InvoiceForm(String name, Date dueDate, int targetTotal, boolean blocked) {
        super();
        this.name = name;
        this.presentationDate = dueDate;
        this.createdDate = new Date();
        this.modifiedDate = createdDate;
        this.targetAmount = targetTotal;
        this.blocked = blocked;
    }

    public List<Invoice> invoices() {
        return getMany(Invoice.class, "InvoiceForm");
    }
}
