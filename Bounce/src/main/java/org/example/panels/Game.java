package org.example.panels;


import org.example.utility.Ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class Game {
    public final int size_w = 20;
    public final int size_h = 20;
    private Color[][] colors = new Color[size_w][size_h];
    private int cellSize = 20;
    private Timer timer;
    private Ball ball1;
    private Ball ball2;
    private Rectangle2D border;
    public final Color leftColor = new Color(255, 100, 100);

    public Game() {
        ball1 = new Ball(100, 200, cellSize, 500, 500,  leftColor, size_w, size_h);

        ball2 = new Ball(300, 200, cellSize, -500, -500, new Color(0, 0, 0), size_w, size_h);


        for (int i = 0; i < size_w / 2; i++) {
            for (int j = 0; j < size_h; j++) {
                colors[i][j] = new Color(0, 0, 0);
            }
        }


        for (int i = size_w / 2; i < size_w; i++) {
            for (int j = 0; j < size_h; j++) {
                colors[i][j] = leftColor;
            }
        }

        border = new Rectangle(0, 0, cellSize * size_w, cellSize * size_h);
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void drawBalls(Graphics2D g2d) {
        ball1.draw(g2d);

        ball2.draw(g2d);
    }

    public void drawField(Graphics2D g2d) {
        for (int i = 0; i < size_w; i++) {
            for (int j = 0; j < size_h; j++) {
                g2d.setColor(colors[i][j]);
                Rectangle2D rect = new Rectangle2D.Double(i * cellSize, j * cellSize, cellSize, cellSize);
                g2d.fill(rect);
                g2d.draw(rect);
            }
        }

        drawBalls(g2d);
    }

    public void update(int delay) {
        ball1.update((float) delay / 1000, border, colors);
        ball2.update((float) delay / 1000, border, colors);
        /*ball1.redraw();
        ball2.redraw();*/
        /*ball1.oval*/
    }
}


