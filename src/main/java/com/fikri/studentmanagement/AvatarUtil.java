package com.fikri.studentmanagement;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class AvatarUtil {
    private static final Color[] COLORS = {
            new Color(37, 99, 235), new Color(5, 150, 105),
            new Color(124, 58, 237), new Color(219, 39, 119),
            new Color(217, 119, 6), new Color(8, 145, 178),
            new Color(79, 70, 229), new Color(190, 24, 93)
    };

    private AvatarUtil() {}

    // Warna dan inisial dibuat berdasarkan data masing-masing mahasiswa.
    public static ImageIcon createAvatar(Student student) {
        int index = Math.floorMod(student.getStudentId(), COLORS.length);
        Color color = COLORS[index];

        BufferedImage image = new BufferedImage(42, 42, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(1, 1, 40, 40);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));

        String[] parts = student.getName().trim().split("\\s+");
        String initials = parts.length > 1
                ? (parts[0].substring(0, 1) + parts[1].substring(0, 1)).toUpperCase()
                : parts[0].substring(0, 1).toUpperCase();

        FontMetrics fm = g.getFontMetrics();
        g.drawString(initials, (42 - fm.stringWidth(initials)) / 2,
                (42 - fm.getHeight()) / 2 + fm.getAscent());
        g.dispose();
        return new ImageIcon(image);
    }
}
