package com.resume.analyzer.service;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
// PDDocument is used to load and read PDF files using Apache PDFBox.

import org.apache.pdfbox.text.PDFTextStripper;
// PDFTextStripper extracts text content from the PDF document.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.resume.analyzer.model.Resume;
import com.resume.analyzer.repository.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public Resume uploadResume(String userId, MultipartFile file) throws IOException {

        // Load PDF
        PDDocument document = PDDocument.load(file.getInputStream());

        // Extract text
        PDFTextStripper pdfStripper = new PDFTextStripper();

        String extractedText = pdfStripper.getText(document);
        // Extract all readable text from PDF file.

        // Clean formatting
        extractedText = extractedText
                .replace("\r", "")
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();

        document.close();

        Resume resume = new Resume();

        resume.setUserId(userId);
        resume.setFileName(file.getOriginalFilename());
        resume.setExtractedText(extractedText);

        // Save to MongoDB
        return resumeRepository.save(resume);
    }
}