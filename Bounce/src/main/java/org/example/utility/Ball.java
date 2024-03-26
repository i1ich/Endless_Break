package org.example.utility;

import org.example.panels.Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;


public class Ball {
    public float velocityX;
    public float velocityY;
    public float positionX;
    public float positionY;
    public int size;
    public Color color;
    public Ellipse2D oval;
    private int cellSize = 20;
    public final int sizeW;
    public final int sizeH;

    public Ball(float positionX, float positionY, int size, float velocityX, float velocityY, Color color, int sizeW, int sizeH) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
        this.color = color;
        this.sizeW = sizeW;
        this.sizeH = sizeH;
        this.oval = new Ellipse2D.Double(positionX - (double) size / 2,
                positionY - (double) size / 2,
                size, size);
    }

    public void update(float dt, Rectangle2D border, Color[][] colors) {
        boolean[] a = collideBorder(border); // LRUD
        bump(a);
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                if (Objects.equals(colors[i][j], color)) {
                    Rectangle2D box = new Rectangle2D.Double(i * cellSize, j * cellSize, cellSize, cellSize);
                    boolean[] b = checkIntersection(oval.getX(), oval.getY(), oval.getHeight(),
                            box.getX(), box.getY(),
                            box.getX() + box.getWidth(), box.getY() + box.getWidth());
                    bump(b);
                    if (b == null || (b[0] || b[1] || b[2] || b[3])) {
                        if (Objects.equals(new Color(255, 100, 100), color)) {
                            colors[i][j] = new Color(0, 0, 0);
                        } else {
                            colors[i][j] = new Color(255, 100, 100);
                        }
                    }

                }

            }
        }
        // Обновляем координаты мяча в соответствии с его скоростью
        positionX += velocityX * dt;
        positionY += velocityY * dt;
    }

    public void draw(Graphics2D g2d) {
        this.oval = new Ellipse2D.Double(positionX - (double) size / 2,
                positionY - (double) size / 2,
                size, size);

        g2d.setColor(color);
        oval.setFrame(positionX - (double) size / 2,
                positionY - (double) size / 2,
                size, size);
        g2d.fill(oval);
        g2d.draw(oval);
    }

    public void redraw() {
        System.out.println(positionX);
        oval.setFrame(positionX - (double) size / 2,
                positionY - (double) size / 2,
                size, size);
    }

    private boolean[] collideBorder(Rectangle2D border) {
        boolean isLeft = positionX - border.getX() <= (double) size / 2;
        boolean isRight = (border.getX() + border.getWidth()) - positionX <= (double) size / 2;
        boolean isUp = positionY - border.getY() <= (double) size / 2;
        boolean isDown = (border.getY() + border.getHeight()) - positionY <= (double) size / 2;
        int isLeftInt = Boolean.compare(isLeft, false);
        int isRightInt = Boolean.compare(isRight, false);
        int isUpInt = Boolean.compare(isUp, false);
        int isDownInt = Boolean.compare(isDown, false);
        return new boolean[]{isLeft, isRight, isUp, isDown};
        //return 8 * isLeftInt + 4 * isRightInt + 2 * isUpInt + 1 * isDownInt;// LRUD___ to binary
    }

    private boolean[] collideBox(Rectangle2D box) {
        boolean isLeft = box.getX() - positionX <= (double) size / 2;
        boolean isRight = positionX - (box.getX() + box.getWidth()) <= (double) size / 2;
        boolean isUp = box.getY() - positionY <= (double) size / 2;
        boolean isDown = positionY - (box.getY() + box.getHeight()) <= (double) size / 2;
        return new boolean[]{isLeft, isRight, isUp, isDown};
    }

    private void bump(boolean[] params) {
        positionX = (float) clamp(positionX, 5, cellSize*sizeW - 5);
        positionY = (float) clamp(positionY, 5, cellSize*sizeW - 5);

        Random random = new Random();
        if (params == null) {
            velocityX = -velocityX;
            velocityY = -velocityY;
            double[] res = rotateVector(velocityX, velocityY, ((random.nextFloat() * 2) - 1) * 10);
            velocityX = (float) res[0];
            velocityY = (float) res[1];
            return;
        }

        if (params[0] || params[1]) {
            velocityX = -velocityX;
            double[] res = rotateVector(velocityX, velocityY, ((random.nextFloat() * 2) - 1)  * 10);
            velocityX = (float) res[0];
            velocityY = (float) res[1];
        }
        if (params[2] || params[3]) {
            velocityY = -velocityY;
            double[] res = rotateVector(velocityX, velocityY, ((random.nextFloat() * 2) - 1)  * 10);
            velocityX = (float) res[0];
            velocityY = (float) res[1];
        }
    }

    public static boolean[] checkIntersection(double circleX, double circleY, double radius,
                                              double squareX1, double squareY1, double squareX2, double squareY2) {
        // Проверяем, находится ли центр круга внутри квадрата
        if (circleX >= squareX1 && circleX <= squareX2 && circleY >= squareY1 && circleY <= squareY2) {
            return null; // Круг внутри квадрата
        }

        // Находим ближайшую точку круга к границам квадрата
        double closestX = clamp(circleX, squareX1, squareX2);
        double closestY = clamp(circleY, squareY1, squareY2);

        // Рассчитываем расстояние от ближайшей точки круга до центра круга
        double distanceX = circleX - closestX;
        double distanceY = circleY - closestY;
        double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

        // Проверяем, пересекаются ли круг и квадрат
        if (distanceSquared <= (radius * radius)) {
            // Определяем с какой стороны было пересечение
            return new boolean[]{closestX == squareX1,
                    closestX == squareX2,
                    closestY == squareY1,
                    closestY == squareY2};
        }

        return new boolean[]{false, false, false, false,};
    }

    // Метод для ограничения значения value в диапазоне [min, max]
    private static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    public static double[] rotateVector(double x, double y, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double[][] rotationMatrix = {
                {Math.cos(angleRadians), -Math.sin(angleRadians)},
                {Math.sin(angleRadians), Math.cos(angleRadians)}
        };

        double[] rotatedVector = new double[2];
        rotatedVector[0] = rotationMatrix[0][0] * x + rotationMatrix[0][1] * y;
        rotatedVector[1] = rotationMatrix[1][0] * x + rotationMatrix[1][1] * y;

        return rotatedVector;
    }
}