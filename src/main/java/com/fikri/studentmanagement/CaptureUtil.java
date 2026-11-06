package com.fikri.studentmanagement;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public final class CaptureUtil {
    private CaptureUtil() {}

    // Screenshot otomatis disimpan ke folder /captures setelah aksi CRUD.
    public static void captureWindow(JFrame frame, String fileName) {
        SwingUtilities.invokeLater(() -> {
            javax.swing.Timer timer = new javax.swing.Timer(700, event -> {
                try {
                    File folder = new File("captures");
                    if (!folder.exists()) folder.mkdirs();

                    Rectangle bounds = frame.getBounds();
                    BufferedImage image = new Robot().createScreenCapture(bounds);
                    ImageIO.write(image, "png", new File(folder, fileName));
                    System.out.println("Screenshot tersimpan: captures/" + fileName);
                } catch (Exception ex) {
                    System.err.println("Gagal mengambil screenshot: " + ex.getMessage());
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    public static void capturePdfFirstPage(File pdfFile, String outputName) {
        try (org.apache.pdfbox.pdmodel.PDDocument document =
                     org.apache.pdfbox.pdmodel.PDDocument.load(pdfFile)) {
            if (document.getNumberOfPages() == 0) return;
            org.apache.pdfbox.rendering.PDFRenderer renderer =
                    new org.apache.pdfbox.rendering.PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 120);
            File folder = new File("captures");
            if (!folder.exists()) folder.mkdirs();
            ImageIO.write(image, "png", new File(folder, outputName));
            System.out.println("Screenshot laporan tersimpan: captures/" + outputName);
        } catch (Exception ex) {
            System.err.println("Gagal membuat preview report: " + ex.getMessage());
        }
    }
}
