package com.guberan.faq.config;

import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.mapper.FaqMapper;
import com.guberan.faq.model.Faq;
import com.guberan.faq.repository.FaqRepository;
import com.guberan.faq.service.FaqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final FaqService faqService;
    private final FaqRepository faqRepository;
    private final FaqMapper faqMapper;


    private FaqDto saveInDb(FaqDto dto) {
        Faq savedFaq = faqRepository.save(faqMapper.toFaq(dto));
        return faqMapper.toDto(savedFaq);
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (faqRepository.count() == 0) {
            List<FaqDto> initialFaqDtos = List.of(
                    new FaqDto("Is Spring AI production-ready?",
                            "Spring AI is currently in milestone releases. While it's promising, always evaluate stability for production use. This answer may require validation."),

                    new FaqDto("What is the purpose of the springai-faq-rag project?",
                            "The project 'springai-faq-rag' demonstrates a RAG (Retrieval-Augmented Generation) architecture using Spring Boot, pgvector, and Spring AI."),

                    new FaqDto("How does pgvector integrate with Spring AI?",
                            "Spring AI supports pgvector as a VectorStore, enabling similarity search on embedded documents stored in a PostgreSQL database."),

                    new FaqDto("Can I use OpenAI embeddings with Spring AI?",
                            "Yes, Spring AI supports OpenAI's embedding models like 'text-embedding-ada-002' through a clean abstraction."),

                    new FaqDto("What is the role of similaritySearch in Spring AI?",
                            "The method similaritySearch retrieves documents from a vector database that are semantically close to a given query embedding."),

                    new FaqDto("Is there a way to validate AI-generated answers?",
                            "Yes, the system includes a validation flag on each answer. Administrators can review and validate answers manually."),

                    new FaqDto("What is the recommended number of vector dimensions for OpenAI embeddings?",
                            "The 'text-embedding-ada-002' model produces 1536-dimensional vectors, which is also the default setting in the springai-faq-rag project.")
            );
            initialFaqDtos.stream().map(this::saveInDb).map(faqService::validateResponse).forEach(faqDto -> log.info("Added document: {} - {}", faqDto.getId(), faqDto.getAnswer()));
            
        }
    }
}