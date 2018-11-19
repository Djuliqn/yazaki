package com.yazaki.yazaki.domain.scheduled.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SimpleReportExporter {

    private JasperPrint jasperPrint;

    public void exportToXlsx() {
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("статистика-".concat(LocalDate.now().toString()).concat(".xlsx")));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[] { "Информация" });

        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }
}
