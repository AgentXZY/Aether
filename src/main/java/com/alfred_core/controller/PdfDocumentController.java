package com.alfred_core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alfred_core.entity.PdfDocument;
import com.alfred_core.service.PdfDocumentService;

@RestController
@RequestMapping("/api/pdfs")
public class PdfDocumentController {

    private final PdfDocumentService pdfService;

    public PdfDocumentController(PdfDocumentService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PdfDocument> uploadPdf(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        PdfDocument savedPdf = pdfService.savePdf(file);

        return ResponseEntity.ok(savedPdf);
    }
    
    @GetMapping("/all")
    public List<PdfDocument> getAllPdfs() {
        return pdfService.getAllPdfs();
    }
    
}