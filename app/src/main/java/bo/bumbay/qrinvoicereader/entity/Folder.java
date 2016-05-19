package bo.bumbay.qrinvoicereader.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Folders")
public class Folder extends Model{
    @Column(name = "Name")
    public String name;

    @Column(name = "CreatedDate")
    public Date createdDate;

    @Column(name = "ModifiedDate")
    public Date modifiedDate;

    @Column(name = "IsFavorite")
    public boolean isFavorite;

    @Column(name = "Folder")
    public Folder parent;

    public Folder() {
        super();
    }

    public Folder(String name) {
        super();
        this.name = name;
        this.isFavorite = false;
        this.createdDate = new Date();
        this.modifiedDate = createdDate;
    }
}
