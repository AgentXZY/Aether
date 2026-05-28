package com.alfred_core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfred_core.entity.PdfDocument;

public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {

}