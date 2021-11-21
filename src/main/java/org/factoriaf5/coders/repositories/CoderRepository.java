package org.factoriaf5.coders.repositories;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CoderRepository {

    private List<Coder> coders;

    public CoderRepository() {
        this.coders = new ArrayList<>();
    }

    public Coder save(Coder coder) {
        coders.add(coder);
        return coder;
    }

    public void deleteAll() {
        coders.clear();
    }

    public List<Coder> findAll() {
        return coders;
    }

    public Optional<Coder> findByIndex(int index) {
        if (index > 0 && index < coders.size()) {
            return Optional.of(coders.get(index));
        }
        return Optional.empty();
    }
}
