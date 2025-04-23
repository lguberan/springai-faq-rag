package com.guberan.faq.repository;

import com.guberan.faq.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface FaqRepository extends JpaRepository<Faq, UUID> {

    List<Faq> findByValidated(boolean validated);
    
    List<Faq> findByQuestionContainingIgnoreCase(String keyword);
}