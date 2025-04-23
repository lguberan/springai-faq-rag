package com.guberan.faq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class FaqServiceTest {

    @Autowired
    private FaqService faqService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
/*
    @BeforeEach
    void setup() {


        jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS vector");
        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS vector_store (
                        id UUID PRIMARY KEY,
                        content TEXT,
                        metadata JSONB,
                        embedding vector(1536)
                    )
                """);

        faqService.("What is Spring AI?", "Spring AI is a project for integrating AI into Spring apps.", true);
        faqService.addFaq("What is RAG?", "RAG stands for Retrieval Augmented Generation.", true);
        faqService.addFaq("Test unvalidated", "This should not be matched", false);
    }
*//*
    @Test
    void testFindSimilarValidatedFaq() {
        List<Document> results = faqService.findSimilarValidatedFaq("Tell me about Spring AI", 3, 0.5);
        assertThat(results).isNotEmpty();
        assertThat(results).allSatisfy(doc ->
                assertThat(doc.getMetadata().get("validated")).isEqualTo("true")
        );
    }
    */
}
