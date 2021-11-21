package org.factoriaf5.coders.repositories;

import java.util.Objects;

public class Coder {

    private final String name;
    private String favouriteLanguage;

    public Coder(String name, String favouriteLanguage) {
        this.name = name;
        this.favouriteLanguage = favouriteLanguage;
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
