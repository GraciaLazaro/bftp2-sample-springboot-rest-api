package org.factoriaf5.coders.repositories;

import org.factoriaf5.coders.controllers.CoderNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (isValidIndex(index)) {
            return Optional.of(coders.get(index));
        }
        return Optional.empty();
    }

    public Optional<Coder> deleteByIndex(int index) {
        if (isValidIndex(index)) {
            return Optional.of(coders.remove(index));
        }
        return Optional.empty();
    }

    private boolean isValidIndex(int index) {
        return index > 0 && index < coders.size();
    }

    public Coder update(Coder coder) {

        coders.stream()
                .filter(c -> c.getName().equals(coder.getName()))
                .findAny()
                .orElseThrow(CoderNotFoundException::new);

        coders = coders.stream().map(originalCoder -> {
            if (Objects.equals(originalCoder.getName(), coder.getName())) {
                return coder;
            } else {
                return originalCoder;
            }
        }).collect(Collectors.toList());

        return coder;
    }
}
