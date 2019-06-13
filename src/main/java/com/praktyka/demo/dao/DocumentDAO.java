package com.praktyka.demo.dao;

import com.praktyka.demo.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDAO extends JpaRepository <Document, Integer> {
}
