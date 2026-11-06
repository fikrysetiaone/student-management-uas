package com.fikri.studentmanagement;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    public File generateStudentReport(int startId, int endId) throws Exception {
        if (startId > endId) {
            throw new IllegalArgumentException("ID awal tidak boleh lebih besar dari ID akhir.");
        }

        File outputDirectory = new File("reports");
        if (!outputDirectory.exists()) outputDirectory.mkdirs();

        String template = getClass().getClassLoader()
                .getResource("reports/student_report.jrxml").getPath();

        JasperReport jasperReport =
                JasperCompileManager.compileReport(template);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("START_ID", startId);
        parameters.put("END_ID", endId);
        parameters.put("LOGO_PATH", getClass().getClassLoader()
                .getResource("images/logo.png").getPath());

        try (Connection connection = DatabaseConnector.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, connection);

            File pdfFile = new File(outputDirectory,
                    "StudentReport_" + startId + "_to_" + endId + ".pdf");

            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());
            return pdfFile;
        }
    }
}
