package com.guberan.faq.controller;

import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;


    @GetMapping("/ask")
    @Operation(
            summary = "Ask a question to the System",
            parameters = {
                    @Parameter(
                            name = "question",
                            description = "The question to ask",
                            required = true,
                            example = "How does OpenAI integrate with the FAQ system?"
                    )
            }
    )
    public FaqDto ask(@RequestParam String question) {
        return faqService.ask(question);
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN','RAG_BUILDER')")
    public List<FaqDto> listFaq(@RequestParam(required = false) Boolean validated) {
        return faqService.getValidated(validated);
    }

    @PatchMapping("/validate")
    public FaqDto validateFaq(@RequestBody FaqDto faqDto) {
        return faqService.validateResponse(faqDto);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{faqId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaq(@PathVariable UUID faqId) {
        faqService.deleteFaq(faqId);
    }
}