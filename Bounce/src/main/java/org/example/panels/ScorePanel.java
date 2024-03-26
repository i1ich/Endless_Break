package org.example.panels;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score;

    public ScorePanel() {
        this.score = 0;
    }

    public ScorePanel(int initialScore) {
        this.score = initialScore;
    }

    // Метод для установки значения score
    public void setScore(int score) {
        this.score = score;
        // Перерисовываем панель, чтобы отобразить новое значение
        repaint();
    }

    // Метод для получения текущего значения score
    public int getScore() {
        return score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Рисуем текст с текущим значением score
        String scoreText = "Score: " + score;
        g2d.drawString(scoreText, 10, getHeight() / 2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 30); // Устанавливаем размеры панели
    }
}
