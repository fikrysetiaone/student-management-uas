package com.fikri.studentmanagement;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                IStudentDAO studentDAO = new StudentDAO();
                MainFrame frame = new MainFrame(studentDAO);
                frame.setVisible(true);
                CaptureUtil.captureWindow(frame, "read.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
