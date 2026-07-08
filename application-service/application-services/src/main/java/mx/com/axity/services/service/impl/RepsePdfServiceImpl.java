package mx.com.axity.services.service.impl;

import mx.com.axity.model.RepseClientDO;
import mx.com.axity.model.RepseComplianceDO;
import mx.com.axity.persistence.RepseClientDAO;
import mx.com.axity.persistence.RepseComplianceDAO;
import mx.com.axity.services.service.IRepsePdfService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RepsePdfServiceImpl implements IRepsePdfService {

    static final Logger LOG = LogManager.getLogger(RepsePdfServiceImpl.class);

    private static final float MARGIN = 40f;
    private static final float ROW_HEIGHT = 18f;
    private static final float COL_CLIENT = 180f;
    private static final float COL_PERIOD = 60f;
    private static final float COL_REQ = 50f;
    private static final float COL_SENT = 50f;
    private static final float COL_VAL = 50f;
    private static final float COL_REJ = 50f;
    private static final float COL_SEM = 70f;

    @Autowired private RepseComplianceDAO repseComplianceDAO;
    @Autowired private RepseClientDAO repseClientDAO;

    @Override
    public byte[] exportCompliancePdf(String tenantId, String period) {
        LOG.info("RepsePdfServiceImpl: generando PDF tenant={} period={}", tenantId, period);
        List<RepseComplianceDO> rows = repseComplianceDAO.findAllByTenantIdAndPeriod(tenantId, period);

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float yStart = pageHeight - MARGIN;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Title
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
                cs.newLineAtOffset(MARGIN, yStart);
                cs.showText("Reporte de Cumplimiento REPSE — Período: " + period);
                cs.endText();

                yStart -= 20f;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.newLineAtOffset(MARGIN, yStart);
                cs.showText("Generado: " + LocalDate.now() + "   |   Tenant: " + tenantId + "   |   Registros: " + rows.size());
                cs.endText();

                yStart -= 25f;

                // Table header line
                cs.setLineWidth(0.5f);
                cs.moveTo(MARGIN, yStart);
                cs.lineTo(pageWidth - MARGIN, yStart);
                cs.stroke();

                yStart -= 4f;

                // Column headers
                float x = MARGIN;
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 9);
                cs.newLineAtOffset(x, yStart);
                cs.showText("Cliente");
                cs.endText();
                x += COL_CLIENT;
                drawHeaderCell(cs, x, yStart, "Período"); x += COL_PERIOD;
                drawHeaderCell(cs, x, yStart, "Req."); x += COL_REQ;
                drawHeaderCell(cs, x, yStart, "Env."); x += COL_SENT;
                drawHeaderCell(cs, x, yStart, "Val."); x += COL_VAL;
                drawHeaderCell(cs, x, yStart, "Rech."); x += COL_REJ;
                drawHeaderCell(cs, x, yStart, "Semáforo");

                yStart -= 4f;
                cs.moveTo(MARGIN, yStart);
                cs.lineTo(pageWidth - MARGIN, yStart);
                cs.stroke();
                yStart -= (ROW_HEIGHT - 4f);

                // Data rows
                for (RepseComplianceDO compliance : rows) {
                    if (yStart < MARGIN + ROW_HEIGHT) break; // avoid overflow (single page)

                    String razonSocial = "—";
                    if (compliance.getIdRepseClient() != null) {
                        Optional<RepseClientDO> client = repseClientDAO.findById(compliance.getIdRepseClient());
                        razonSocial = client.map(RepseClientDO::getRazonSocial).orElse("—");
                    }
                    if (razonSocial.length() > 28) razonSocial = razonSocial.substring(0, 25) + "...";

                    x = MARGIN;
                    drawCell(cs, x, yStart, razonSocial, PDType1Font.HELVETICA, 8); x += COL_CLIENT;
                    drawCell(cs, x, yStart, nvl(compliance.getPeriod()), PDType1Font.HELVETICA, 8); x += COL_PERIOD;
                    drawCell(cs, x, yStart, nvl(compliance.getDocumentsRequired()), PDType1Font.HELVETICA, 8); x += COL_REQ;
                    drawCell(cs, x, yStart, nvl(compliance.getDocumentsSubmitted()), PDType1Font.HELVETICA, 8); x += COL_SENT;
                    drawCell(cs, x, yStart, nvl(compliance.getDocumentsValidated()), PDType1Font.HELVETICA, 8); x += COL_VAL;
                    drawCell(cs, x, yStart, nvl(compliance.getDocumentsRejected()), PDType1Font.HELVETICA, 8); x += COL_REJ;
                    String semaforo = compliance.getSemaforo() != null ? compliance.getSemaforo() : "—";
                    drawCell(cs, x, yStart, semaforo, PDType1Font.HELVETICA_BOLD, 8);

                    yStart -= ROW_HEIGHT;
                }

                // Footer line
                cs.moveTo(MARGIN, yStart + 4f);
                cs.lineTo(pageWidth - MARGIN, yStart + 4f);
                cs.stroke();
            }

            doc.save(baos);
            LOG.info("RepsePdfServiceImpl: PDF generado {} bytes, {} registros", baos.size(), rows.size());
            return baos.toByteArray();

        } catch (Exception e) {
            LOG.error("RepsePdfServiceImpl: error generando PDF — {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar reporte PDF REPSE", e);
        }
    }

    private void drawHeaderCell(PDPageContentStream cs, float x, float y, String text) throws Exception {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 9);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private void drawCell(PDPageContentStream cs, float x, float y, String text, PDType1Font font, float size) throws Exception {
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private String nvl(Object val) {
        return val != null ? val.toString() : "0";
    }
}
