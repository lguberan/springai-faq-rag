package com.guberan.faq.repository;

import com.guberan.faq.model.ContextItem;
import com.guberan.faq.model.Faq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "com.guberan.faq.model")
@EnableJpaRepositories(basePackages = "com.guberan.faq.repository")
class FaqRepositoryTest {

    @Autowired
    private FaqRepository faqRepository;

    @Test
    @DisplayName("Should find FAQs by validated flag")
    void testFindByValidated() {
        UUID docId = UUID.randomUUID();
        Faq faq1 = new Faq(UUID.randomUUID(), "Q1", "A1", true, false, LocalDateTime.now(),
                List.of(new ContextItem("The projext is fine", 0.8, docId)));
        Faq faq2 = new Faq(UUID.randomUUID(), "Q2", "A2", false, false, LocalDateTime.now(), List.of());
        faqRepository.saveAll(List.of(faq1, faq2));

        List<Faq> validatedFaqs = faqRepository.findByValidated(true);

        assertThat(validatedFaqs).hasSize(1);
        assertThat(validatedFaqs.get(0).getQuestion()).isEqualTo("Q1");
        assertThat(validatedFaqs.get(0).getContextItems().get(0).getText()).isEqualTo("The projext is fine");
        assertThat(validatedFaqs.get(0).getContextItems().get(0).getScore()).isEqualTo(0.8);
        assertThat(validatedFaqs.get(0).getContextItems().get(0).getDocId()).isEqualTo(docId);
    }

    @Test
    @DisplayName("Should find FAQs containing keyword (case insensitive)")
    void testFindByQuestionContainingIgnoreCase() {
        Faq faq = new Faq(UUID.randomUUID(), "What is AI?", "Artificial Intelligence", true, false, LocalDateTime.now(), List.of());
        faqRepository.save(faq);

        List<Faq> result = faqRepository.findByQuestionContainingIgnoreCase("ai");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getQuestion()).containsIgnoringCase("AI");
    }
}