package org.example.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerOnPanelExample extends JPanel {
    private JLabel timerLabel;
    private Timer timer;
    private int secondsPassed;

    public TimerOnPanelExample() {

        setOpaque(false); // Делаем панель прозрачной
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Устанавливаем компоновку для выравнивания

        // Создаем метку для таймера и добавляем её на панель
        timerLabel = new JLabel("Seconds passed: 0");
        add(timerLabel);

        // Создаем и настраиваем таймер
        secondsPassed = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                timerLabel.setText("Seconds passed: " + secondsPassed); // Обновляем текст метки таймера
            }
        });

        // Запускаем таймер
        timer.start();
    }
}
