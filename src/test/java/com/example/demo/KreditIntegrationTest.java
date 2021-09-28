package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KreditIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetStatusKredit() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:7003/kredit/statusKredit")
                        .param("nomorRekening", "0"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
