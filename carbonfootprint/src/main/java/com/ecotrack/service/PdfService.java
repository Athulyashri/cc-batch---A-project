package com.ecotrack.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private FootprintService footprintService;
    
    @Autowired
    private RecommendationService recommendationService;

    public ByteArrayInputStream generateFootprintReport(Long userId, String username) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("EcoTrack AI - Carbon Footprint Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // User Info
            document.add(new Paragraph("User: " + username));
            document.add(Chunk.NEWLINE);

            // Total Footprint
            double total = footprintService.getTotalFootprint(userId);
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Total Carbon Footprint: " + String.format("%.2f", total) + " kg CO2", totalFont));
            document.add(Chunk.NEWLINE);

            // Recommendations
            document.add(new Paragraph("Sustainability Recommendations:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            List<String> recommendations = recommendationService.getRecommendations(userId);
            com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
            for (String rec : recommendations) {
                list.add(new ListItem(rec));
            }
            document.add(list);

            document.close();
        } catch (DocumentException ex) {
            System.err.println("Error generating PDF: " + ex.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
