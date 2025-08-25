package br.com.desafio.banco;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContaControladorTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void criarConta201() throws Exception {
        var body = mapper.writeValueAsString(Map.of("numero_conta", 234, "saldo", 180.37));
        mvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.numero_conta").value(234)).andExpect(jsonPath("$.saldo").value(180.37));
    }

    @Test
    void criarContaDuplicada409() throws Exception {
        var body = mapper.writeValueAsString(Map.of("numero_conta", 999, "saldo", 100));
        mvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
        mvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isConflict());
    }

    @Test
    void getInexistente404() throws Exception {
        mvc.perform(get("/conta").param("numero_conta", "777")).andExpect(status().isNotFound());
    }
}
