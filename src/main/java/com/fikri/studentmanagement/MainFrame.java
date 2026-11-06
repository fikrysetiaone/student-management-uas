package com.fikri.studentmanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.io.File;

public class MainFrame extends JFrame {
    private final IStudentDAO studentDAO;

    private final JTextField idField = new JTextField(12);
    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Avatar", "Student ID", "Name", "Email"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
        @Override
        public Class<?> getColumnClass(int column) {
            return column == 0 ? ImageIcon.class : Object.class;
        }
    };

    private final JTable studentTable = new JTable(tableModel);
    private final JLabel statusLabel = new JLabel("Ready");

    public MainFrame(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 680);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        setContentPane(createMainPanel());
        configureTable();
        addEventHandlers();
        refreshTable();
    }

    private JPanel createMainPanel() {
        JPanel root = new GradientPanel();
        root.setLayout(new BorderLayout(18, 18));
        root.setBorder(new EmptyBorder(22, 26, 18, 26));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Student Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel subtitle = new JLabel("Manage student records and generate PDF reports");
        subtitle.setForeground(new Color(219, 234, 254));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 5));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);

        JLabel logo = new JLabel(new ImageIcon(getClass().getClassLoader()
                .getResource("images/logo.png")));
        header.add(titleBox, BorderLayout.WEST);
        header.add(logo, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout(14, 14));
        content.setOpaque(false);
        content.add(createFormPanel(), BorderLayout.NORTH);
        content.add(createTablePanel(), BorderLayout.CENTER);
        content.add(createReportPanel(), BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        root.add(statusLabel, BorderLayout.SOUTH);
        statusLabel.setForeground(new Color(219, 234, 254));
        return root;
    }

    private JPanel createFormPanel() {
        JPanel panel = whiteCard();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Student Information");
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 4;
        panel.add(heading, c);

        addField(panel, c, "Student ID", idField, 0, 1);
        addField(panel, c, "Full Name", nameField, 2, 1);
        addField(panel, c, "Email Address", emailField, 0, 2);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setOpaque(false);
        JButton addButton = styledButton("Add Student", new Color(22, 163, 74));
        JButton updateButton = styledButton("Update", new Color(37, 99, 235));
        JButton deleteButton = styledButton("Delete", new Color(220, 38, 38));
        JButton clearButton = styledButton("Clear", new Color(100, 116, 139));

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        c.gridx = 2; c.gridy = 2; c.gridwidth = 2;
        panel.add(buttons, c);

        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearForm());

        return panel;
    }

    private void addField(JPanel panel, GridBagConstraints c, String label,
                          JTextField field, int x, int y) {
        JPanel box = new JPanel(new BorderLayout(0, 5));
        box.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                new EmptyBorder(7, 9, 7, 9)));
        box.add(lbl, BorderLayout.NORTH);
        box.add(field, BorderLayout.CENTER);
        c.gridx = x; c.gridy = y; c.gridwidth = 2;
        c.weightx = 1;
        panel.add(box, c);
        c.weightx = 0;
    }

    private JPanel createTablePanel() {
        JPanel panel = whiteCard();
        panel.setLayout(new BorderLayout(8, 10));
        JLabel heading = new JLabel("Student Directory");
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        panel.add(heading, BorderLayout.NORTH);
        studentTable.setRowHeight(52);
        studentTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(226, 232, 240));
        studentTable.setSelectionBackground(new Color(219, 234, 254));
        studentTable.setSelectionForeground(new Color(15, 23, 42));
        studentTable.setShowVerticalLines(false);
        studentTable.setGridColor(new Color(226, 232, 240));
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = whiteCard();
        panel.setLayout(new BorderLayout(10, 5));
        JLabel label = new JLabel("Student Data Report");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel help = new JLabel("Generate a PDF report by Student ID range.");
        help.setForeground(new Color(100, 116, 139));
        JButton reportButton = styledButton("Generate PDF Report", new Color(124, 58, 237));

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 4));
        text.setOpaque(false);
        text.add(label);
        text.add(help);
        panel.add(text, BorderLayout.WEST);
        panel.add(reportButton, BorderLayout.EAST);
        reportButton.addActionListener(e -> showReportRangeDialog());
        return panel;
    }

    private JPanel whiteCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(12, 14, 12, 14)));
        return panel;
    }

    private JButton styledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(9, 13, 9, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void configureTable() {
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(65);
        studentTable.getColumnModel().getColumn(0).setMaxWidth(75);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(110);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(220);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        studentTable.getColumnModel().getColumn(1).setCellRenderer(center);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() >= 0) {
                int row = studentTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(row, 1).toString());
                nameField.setText(tableModel.getValueAt(row, 2).toString());
                emailField.setText(tableModel.getValueAt(row, 3).toString());
                idField.setEditable(false);
                idField.setBackground(new Color(241, 245, 249));
            }
        });
    }

    private void addEventHandlers() {
        // Event tombol telah dipasang pada saat panel dibuat.
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            for (Student student : studentDAO.getAllStudents()) {
                tableModel.addRow(new Object[]{
                        AvatarUtil.createAvatar(student),
                        student.getStudentId(),
                        student.getName(),
                        student.getEmail()
                });
            }
            statusLabel.setText("Total students: " + tableModel.getRowCount());
        } catch (SQLException e) {
            showError("Tidak dapat mengambil data. Periksa koneksi MySQL.\n" + e.getMessage());
        }
    }

    private Student readForm() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Semua field wajib diisi.");
        }
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Student ID harus berupa angka.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Format email tidak valid.");
        }
        return new Student(id, name, email);
    }

    private void addStudent() {
        try {
            Student student = readForm();
            studentDAO.addStudent(student);
            refreshTable();
            clearForm();
            statusLabel.setText("Student successfully added.");
            CaptureUtil.captureWindow(this, "create.png");
            JOptionPane.showMessageDialog(this, "Student berhasil ditambahkan.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void updateStudent() {
        if (studentTable.getSelectedRow() < 0) {
            showError("Pilih mahasiswa pada tabel yang ingin di-update.");
            return;
        }
        try {
            Student student = readForm();
            studentDAO.updateStudent(student);
            refreshTable();
            clearForm();
            statusLabel.setText("Student successfully updated.");
            CaptureUtil.captureWindow(this, "update.png");
            JOptionPane.showMessageDialog(this, "Student berhasil di-update.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void deleteStudent() {
        int row = studentTable.getSelectedRow();
        if (row < 0) {
            showError("Pilih mahasiswa pada tabel yang ingin dihapus.");
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus student ID " + id + "?", "Konfirmasi Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                studentDAO.deleteStudent(id);
                refreshTable();
                clearForm();
                statusLabel.setText("Student successfully deleted.");
                CaptureUtil.captureWindow(this, "delete.png");
                JOptionPane.showMessageDialog(this, "Student berhasil dihapus.");
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        idField.setEditable(true);
        idField.setBackground(Color.WHITE);
        studentTable.clearSelection();
    }

    private void showReportRangeDialog() {
        JTextField startField = new JTextField("1001", 8);
        JTextField endField = new JTextField("1100", 8);
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.add(new JLabel("Student ID From:"));
        form.add(startField);
        form.add(new JLabel("Student ID To:"));
        form.add(endField);

        int result = JOptionPane.showConfirmDialog(this, form,
                "Select Student ID Range", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int start = Integer.parseInt(startField.getText().trim());
                int end = Integer.parseInt(endField.getText().trim());
                ReportGenerator generator = new ReportGenerator();
                File pdf = generator.generateStudentReport(start, end);
                CaptureUtil.capturePdfFirstPage(pdf, "report.png");
                statusLabel.setText("Report generated: " + pdf.getPath());
                JOptionPane.showMessageDialog(this,
                        "PDF berhasil dibuat:\n" + pdf.getAbsolutePath());
                if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(pdf);
            } catch (Exception e) {
                showError("Gagal membuat report: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, new Color(15, 23, 42),
                    getWidth(), getHeight(), new Color(30, 64, 175)));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}
