package com.guberan.faq.controller;

import com.guberan.faq.config.SecurityConfig;
import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.service.FaqService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FaqController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class FaqControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private FaqService faqService;


    @Test
    void shouldAllowAnonymousAccessToAsk() throws Exception {
        when(faqService.ask("test")).thenReturn(new FaqDto());

        mockMvc.perform(get("/api/faq/ask").param("question", "test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAllowAdminToDelete() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/faq/{id}", id).with(csrf()))
                .andExpect(status().isOk());
    }

    @Disabled("Turn on id you want security")
    @Test
    @WithMockUser(roles = "USER")
    void shouldRejectNonAdminDelete() throws Exception {
        UUID id = UUID.randomUUID();

        when(faqService.deleteFaq(id)).thenThrow(new RuntimeException("test"));

        mockMvc.perform(delete("/api/faq/{id}", id).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRejectAnonymousDelete() throws Exception {
        UUID id = UUID.randomUUID();

        when(faqService.deleteFaq(id)).thenThrow(new AccessDeniedException("test"));

        mockMvc.perform(delete("/api/faq/{id}", id).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldValidateFaq() throws Exception {
        FaqDto dto = new FaqDto();
        dto.setQuestion("Q?");

        when(faqService.validateResponse(dto)).thenReturn(dto);

        mockMvc.perform(patch("/api/faq/validate")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Q?"));
    }

}