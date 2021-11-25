package org.factoriaf5.coders.repositories;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coders")
public class Coder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String favouriteLanguage;

    public Coder() {

    }

    public Coder(String name, String favouriteLanguage) {
        this.name = name;
        this.favouriteLanguage = favouriteLanguage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFavouriteLanguage() {
        return favouriteLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coder coder = (Coder) o;
        return Objects.equals(name, coder.name) && Objects.equals(favouriteLanguage, coder.favouriteLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, favouriteLanguage);
    }
}
