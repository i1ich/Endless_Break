package org.example.panels;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Отрисовываем прямоугольник фоном
        g2d.setColor(new Color(0.3f, 0.5f, 0.7f));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
