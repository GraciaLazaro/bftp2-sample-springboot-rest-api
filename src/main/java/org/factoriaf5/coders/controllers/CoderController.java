package org.factoriaf5.coders.controllers;

import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoderController {

    private CoderRepository coderRepository;

    @Autowired
    public CoderController(CoderRepository coderRepository) {
        this.coderRepository = coderRepository;
    }

    @GetMapping("/coders")
    public List<Coder> allCoders() {
        return coderRepository.findAll();
    }

    @GetMapping("/coders/{index}")
    public Coder findCoder(@PathVariable int index) {
        return coderRepository.findByIndex(index).orElseThrow(CoderNotFoundException::new);
    }

    @PostMapping("/coders")
    public Coder addCoder(@RequestBody Coder coder) {
        coderRepository.save(coder);
        return coder;
    }

    @DeleteMapping("/coders/{index}")
    public Coder deleteCoderByIndex(@PathVariable int index) {
        return coderRepository.deleteByIndex(index).orElseThrow(CoderNotFoundException::new);
    }

    @PutMapping("/coders")
    public Coder updateCoderByName(@RequestBody Coder coder) {
        return coderRepository.update(coder);
    }

}
