package org.factoriaf5.coders.repositories;

public class Coder {

    private final String name;
    private final String favouriteLanguage;

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
}
