package com.guberan.faq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.service.FaqService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FaqController.class)
class FaqControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FaqService faqService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAsk() throws Exception {
        String question = "What is Spring Boot?";
        FaqDto response = new FaqDto(UUID.randomUUID().toString(), question, "Spring Boot is...", true, true, LocalDateTime.now(), 0.9, List.of("Snippet 1"));

        Mockito.when(faqService.ask(question)).thenReturn(response);

        mockMvc.perform(get("/api/faq/ask").param("question", question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value("Spring Boot is..."));
    }

    @Test
    void testListFaq() throws Exception {
        FaqDto faq = new FaqDto(UUID.randomUUID().toString(), "Q?", "A!", true, true, LocalDateTime.now(), 0.9, List.of("Context"));
        Mockito.when(faqService.getValidated(true)).thenReturn(List.of(faq));

        mockMvc.perform(get("/api/faq").param("validated", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("Q?"));
    }

    @Test
    void testValidateFaq() throws Exception {
        FaqDto faq = new FaqDto(UUID.randomUUID().toString(), "Q?", "A!", false, true, LocalDateTime.now(), 0.9, List.of());
        Mockito.when(faqService.validateResponse(Mockito.any(FaqDto.class))).thenReturn(faq);

        mockMvc.perform(patch("/api/faq/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Q?"));
    }

    @Test
    void testDeleteFaq() throws Exception {
        UUID faqId = UUID.randomUUID();

        mockMvc.perform(delete("/api/faq/{faqId}", faqId))
                .andExpect(status().isNoContent());

        Mockito.verify(faqService).deleteFaq(faqId);
    }
}