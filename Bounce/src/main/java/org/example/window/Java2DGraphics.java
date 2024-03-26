package org.example.window;

import org.example.panels.DrawingPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class Java2DGraphics extends JFrame {
    public Java2DGraphics() {
        setTitle("Main Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(410, 437);

        // Создаем основной контейнер
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        //
        DrawingPanel drawingPanel = new DrawingPanel();
        mainPanel.add(drawingPanel, BorderLayout.CENTER);


        // Отображаем фрейм
        setVisible(true);
        Timer timer = null;
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Вызываем метод перерисовки панели
                drawingPanel.updatePanel(16);
            }
        });
        timer.start();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Java2DGraphics::new);
    }
}
