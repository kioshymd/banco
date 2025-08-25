package br.com.desafio.banco;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TransacaoControladorTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    void setup() throws Exception {
        var body = mapper.writeValueAsString(Map.of("numero_conta", 234, "saldo", 180.37));
        mvc.perform(post("/conta").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated());
    }

    @Test
    void debito201() throws Exception {
        var tx = mapper.writeValueAsString(Map.of("forma_pagamento", "DEBITO", "numero_conta", 234, "valor", 10));
        mvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON).content(tx))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.numero_conta").value(234)).andExpect(jsonPath("$.saldo").value(closeTo(170.07, 0.001)));
    }

    @Test
    void credito201() throws Exception {
        var tx = mapper.writeValueAsString(Map.of("forma_pagamento","CREDITO","numero_conta",234,"valor",10));
        mvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON).content(tx))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo").value(closeTo(169.87, 0.001))); // 180.37 - (10 + 0.50) = 169.87
    }

    @Test
    void pix201() throws Exception {
        var tx = mapper.writeValueAsString(Map.of("forma_pagamento", "PIX", "numero_conta", 234, "valor", 10));
        mvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON).content(tx))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.saldo").value(closeTo(170.37, 0.001)));
    }

    @Test
    void saldoInsuficiente404() throws Exception {
        var tx = mapper.writeValueAsString(Map.of("forma_pagamento", "CREDITO", "numero_conta", 234, "valor", 1000));
        mvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON).content(tx)).andExpect(status().isNotFound());
    }
}
