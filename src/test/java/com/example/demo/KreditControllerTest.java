package com.example.demo;

import com.example.demo.kredit.KreditController;
import com.example.demo.kredit.KreditService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KreditController.class)
public class KreditControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KreditService kreditService;

    @Test
    public void testGetStatusKredit() throws Exception {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 250);
        response.put("statusKredit", "Belum dibayar.");
        when(kreditService.getStatusKredit(any(Integer.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:7003/kredit/statusKredit")
                        .param("nomorRekening", "0"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
