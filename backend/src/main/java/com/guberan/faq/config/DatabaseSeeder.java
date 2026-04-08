package com.guberan.faq.config;

import com.guberan.faq.dto.FaqDto;
import com.guberan.faq.mapper.FaqMapper;
import com.guberan.faq.model.Faq;
import com.guberan.faq.repository.FaqRepository;
import com.guberan.faq.service.FaqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

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

            Authentication auth = new UsernamePasswordAuthenticationToken("admin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            SecurityContextHolder.getContext().setAuthentication(auth);

            Stream.of(
                            new FaqDto("Why not just use a language model without RAG?", "A standalone language model can hallucinate or provide outdated information. RAG reduces these risks by grounding responses in real, retrievable data."),
                            new FaqDto("What is an embedding?", "An embedding is a vector representation of text that captures its semantic meaning. Similar texts produce similar vectors, enabling similarity search."),
                            new FaqDto("How does similarity search work?", "The system converts a query into an embedding and compares it to stored embeddings using a distance metric such as cosine similarity. The closest matches are retrieved."),
                            new FaqDto("Why use pgvector instead of a dedicated vector database?", "pgvector allows vector search directly inside PostgreSQL. This simplifies architecture by combining relational data and vector search without introducing another system."),
                            new FaqDto("How does Spring AI help in this project?", "Spring AI provides abstractions for embeddings, chat models, and vector stores. It simplifies integration with providers like OpenAI and reduces vendor lock-in."),
                            new FaqDto("Which OpenAI embedding models should I use?", "The recommended models are 'text-embedding-3-small' for most use cases and 'text-embedding-3-large' when maximum accuracy is required."),
                            new FaqDto("What is the optimal embedding dimension size?", "Default dimensions (1536 for small, 3072 for large) provide the best accuracy. Lower dimensions such as 512 or 256 can reduce cost and improve performance with a small trade-off in precision."),
                            new FaqDto("Can I reduce embedding dimensions safely?", "Yes. Many applications perform well with reduced dimensions. It is recommended to benchmark 256, 512, and 1024 dimensions to find the best trade-off for your dataset."),
                            new FaqDto("Why are my similarity search results not relevant?", "Common causes include inconsistent embedding models, too little data, or overly aggressive dimension reduction. Ensure consistent embeddings and sufficient content coverage."),
                            new FaqDto("What happens if the OpenAI API quota is exceeded?", "Embedding and generation requests will fail with an error. The application should handle this with retries, fallback strategies, or by disabling AI features temporarily."),
                            new FaqDto("Should embeddings be generated at application startup?", "No. Generating embeddings at startup can cause failures if the API is unavailable or quota is exceeded. It is safer to generate them asynchronously or on demand."),
                            new FaqDto("Can AI-generated answers be validated?", "Yes. This project includes a validation mechanism where answers can be reviewed and approved by an administrator before being considered reliable."),
                            new FaqDto("How can I improve RAG answer quality?", "Improve document quality, increase dataset size, ensure consistent embeddings, and tune retrieval parameters such as top-K results and similarity thresholds."),
                            new FaqDto("What is Retrieval-Augmented Generation (RAG)?", "RAG is an architecture where a system retrieves relevant documents from a database and provides them to a language model to generate more accurate and context-aware responses."),
                            new FaqDto("What problem does the springai-faq-rag project solve?", "The project demonstrates how to build a system that answers questions using both a language model and a knowledge base. It improves answer accuracy by retrieving relevant data instead of relying only on the model's internal knowledge.")
                    )
                    .map(this::saveInDb)
                    .map(faqDto -> {
                        try {
                            return faqService.validateResponse(faqDto);
                        } catch (Exception e) {
                            log.error("Error validating FAQ: {}", faqDto.getQuestion(), e);
                            throw e;
                        }
                    })
                    .forEach(faqDto -> log.info("Added document: {} - {}", faqDto.getId(), faqDto.getAnswer()));
        }
    }
}