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

    @Column(name = "IsFavorite")
    public boolean isFavorite;

    @Column(name = "Folder", onDelete = Column.ForeignKeyAction.CASCADE)
    public Folder folder;

    public InvoiceForm() {
        super();
    }

    public InvoiceForm(String name, Date presentationDate, int targetTotal, boolean blocked, Folder folder) {
        super();
        this.name = name;
        this.presentationDate = presentationDate;
        this.createdDate = new Date();
        this.modifiedDate = createdDate;
        this.targetAmount = targetTotal;
        this.blocked = blocked;
        this.isFavorite = false;
        this.folder = folder;
    }

    public List<Invoice> invoices() {
        return getMany(Invoice.class, "InvoiceForm");
    }
}
