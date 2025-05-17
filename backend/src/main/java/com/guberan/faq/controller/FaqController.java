package com.guberan.faq.controller;

import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.service.FaqService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/faq")
@OpenAPIDefinition(info = @Info(title = "springai-faq-rag API", version = "v1"))
@Tag(name = "FAQ", description = "Endpoints to manage FAQs")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;


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
    @GetMapping("/ask")
    public FaqDto ask(@RequestParam String question) {
        return faqService.ask(question);
    }

    @Operation(summary = "List all FAQs", description = "Returns a list of FAQs, optionally filtered by validation status")
    @GetMapping
    public List<FaqDto> listFaq(@RequestParam(required = false) Boolean validated) {
        return faqService.getValidated(validated);
    }

    @Operation(
            summary = "Validate or correct a generated answer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Manual correction",
                                            summary = "Validate with a corrected answer",
                                            value = """
                                                    {
                                                            "id": "480db602-1ea6-4ed6-9e0a-096b1bea310e",
                                                            "answer": "Spring AI supports pgvector as a VectorStore",
                                                            "validated": true,
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @PatchMapping("/validate")
    //@PreAuthorize("hasRole('ADMIN')")
    public FaqDto validateFaq(@RequestBody FaqDto faqDto) {
        return faqService.validateResponse(faqDto);
    }

    @Operation(
            summary = "Delete a FAQ",
            description = "Deletes a specific FAQ by its ID",
            parameters = {
                    @Parameter(
                            name = "faqId",
                            description = "ID of the FAQ to delete",
                            required = true,
                            example = "a77caa6d-15d1-4677-baaa-a79c6ad4b29e"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns true if deleted, false if not found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @DeleteMapping("/{faqId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public boolean deleteFaq(@PathVariable UUID faqId) {
        return faqService.deleteFaq(faqId);
    }
}