package com.jux.juxbar.model;


import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class DrinkImage {

    protected String drinkName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] image;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    protected byte[] preview;

}
