package org.factoriaf5.coders;

import org.aspectj.apache.bcel.classfile.Code;
import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CodersApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoderRepository coderRepository;

    @AfterEach
    void tearDown() {
        coderRepository.deleteAll();
    }

    @Test
    void returnsTheExistingCoders() throws Exception {

        addTestCoders();

        mockMvc.perform(get("/coders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo("Yeraldin")))
                .andExpect(jsonPath("$[0].favouriteLanguage", equalTo("Java")))
                .andExpect(jsonPath("$[1].name", equalTo("Marta")))
                .andExpect(jsonPath("$[1].favouriteLanguage", equalTo("Kotlin")))
                .andExpect(jsonPath("$[2].name", equalTo("Daniela")))
                .andExpect(jsonPath("$[2].favouriteLanguage", equalTo("Python")))
                .andDo(print());
    }

    @Test
    void allowsToCreateANewCoder() throws Exception {
        mockMvc.perform(post("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Laura\", \"favouriteLanguage\": \"PHP\" }")
        ).andExpect(status().isOk());

        List<Coder> coders = coderRepository.findAll();
        assertThat(coders, contains(allOf(
                hasProperty("name", is("Laura")),
                hasProperty("favouriteLanguage", is("PHP"))
        )));
    }

    @Test
    void allowsToFindACoderById() throws Exception {

        Coder coder = coderRepository.save(new Coder("Marta", "Kotlin"));

        mockMvc.perform(get("/coders/" + coder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Marta")))
                .andExpect(jsonPath("$.favouriteLanguage", equalTo("Kotlin")));
    }

    @Test
    void returnsAnErrorIfTryingToGetACoderThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/coders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToDeleteACoderById() throws Exception {
        Coder coder = coderRepository.save(new Coder("Marta", "Kotlin"));

        mockMvc.perform(delete("/coders/"+ coder.getId()))
                .andExpect(status().isOk());


        List<Coder> coders = coderRepository.findAll();
        assertThat(coders, not(contains(allOf(
                hasProperty("name", is("Marta")),
                hasProperty("favouriteLanguage", is("Kotlin"))
        ))));

    }

    @Test
    void returnsAnErrorIfTryingToDeleteACoderThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/coders/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void allowsToModifyACoder() throws Exception {
        Coder coder = coderRepository.save(new Coder("Yeraldin", "Java"));

        mockMvc.perform(put("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + coder.getId() + "\", \"name\": \"Yeraldin\", \"favouriteLanguage\": \"Ruby\" }")
        ).andExpect(status().isOk());

        List<Coder> coders = coderRepository.findAll();

        assertThat(coders, hasSize(1));
        assertThat(coders.get(0).getName(), equalTo("Yeraldin"));
        assertThat(coders.get(0).getFavouriteLanguage(), equalTo("Ruby"));
    }

    @Test
    void returnsAnErrorWhenTryingToModifyACoderThatDoesNotExist() throws Exception {
        addTestCoders();

        mockMvc.perform(put("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + -1 + "\", \"name\": \"Pepita\", \"favouriteLanguage\": \"C++\" }")
        ).andExpect(status().isNotFound());
    }

    private void addTestCoders() {
        List<Coder> coders = List.of(
                new Coder("Yeraldin", "Java"),
                new Coder("Marta", "Kotlin"),
                new Coder("Daniela", "Python")
        );

        coders.forEach(coderRepository::save);
    }
}
