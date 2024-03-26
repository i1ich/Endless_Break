package org.example.panels;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class DrawingPanel extends JPanel {


    public Game game;
    //private Graphics2D g2d = null;
    public DrawingPanel() {
        game = new Game();
    }
    void setBack(Graphics2D g2d, Color c) {
        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g2d.setColor(c);
        g2d.fill(rect);
        g2d.draw(rect);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setBack(g2d, new Color(0.3f, 0.5f, 0.7f));

        game.drawField(g2d);
    }
    // re paint panel func
    public void updatePanel(int delay) {
        game.update(delay);
        repaint(); // Вызываем repaint() для перерисовки панели

    }

}
