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

    @Column(name = "DueDate")
    public Date dueDate;

    @Column(name = "TargetTotal")
    public int targetTotal;

    public InvoiceForm() {
        super();
    }

    public InvoiceForm(String name, Date dueDate, int targetTotal) {
        super();
        this.name = name;
        this.dueDate = dueDate;
        this.createdDate = new Date();
        this.modifiedDate = createdDate;
        this.targetTotal = targetTotal;
    }

    public List<Invoice> invoices() {
        return getMany(Invoice.class, "InvoiceForm");
    }
}
