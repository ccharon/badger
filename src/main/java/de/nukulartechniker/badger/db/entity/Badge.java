package de.nukulartechniker.badger.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Entity
@Table(name="BADGES")
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "CREATED")
    private Calendar created;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE")
    private byte[] image;
}
