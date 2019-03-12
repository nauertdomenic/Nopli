/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package projectanimal.whatever.jpa;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author simon
 */
@Entity
@Table(name = "Tierart")
public class Tierart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tierart_ids")
    @TableGenerator(name = "tierart_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Tierart muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Spezies category;

    @Column(length = 50)
    @NotNull(message = "Der Tierartname darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Der Tierartname muss zwischen ein und 50 Zeichen lang sein.")
    private String tierartname;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Tierart() {
    }

    public Tierart(User owner, Spezies spezies, String tierartname) {
        this.owner = owner;
        this.category = spezies;
        this.tierartname = tierartname;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Spezies getCategory() {
        return category;
    }

    public void setCategory(Spezies category) {
        this.category = category;
    }

    public String getTierartname() {
        return tierartname;
    }

    public void setTierartname(String tierartname) {
        this.tierartname = tierartname;
    }
     //</editor-fold>

}

