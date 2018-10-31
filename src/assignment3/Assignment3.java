/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 2924796
 */
public class Assignment3 extends PApplet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("assignment3.Assignment3");
    }

   
    PImage bg;

    ArrayList<Ninja> ninjas;
    private static int ninjaNumber = 10;
    private static int benchs = 3;

    public void settings() {
        fullScreen();

    }

    public void setup() {
        ninjas = new ArrayList<>();
        for (int i = 0; i < ninjaNumber; i++) {
            Ninja n = new Ninja(i);
            ninjas.add(n);
        }

        bg = loadImage("guernica.jpg");
        bg.resize(width, height);
    }

    public void draw() {
        background(bg);

        for (Ninja n : ninjas) {
            n.drawNinja();
            n.moveNinja();
        }

    }

    public void keyPressed() {
        if (key == 's') {
            for (Ninja n : ninjas) {
                n.raiseHand = true;
            }

        }

        if (key == 'a') {
            for (Ninja n : ninjas) {
                n.moveLeft = true;
                n.moveRight = false;
            }
        }
        if (key == 'd') {
            for (Ninja n : ninjas) {
                n.moveRight = true;
                n.moveLeft = false;
            }
        }

        if (key == 'w') {
            for (Ninja n : ninjas) {
                if (!n.jump) {

                    n.jump = true;
                    n.initialY = n.y;
                    n.fall = false;
                    n.speed = n.jumpHeight / 13;
                }
            }

        }
    }

    public void keyReleased() {
        if (key == 's') {
            for (Ninja n : ninjas) {
                n.raiseHand = false;
            }

        }

        if (key == 'a') {
            for (Ninja n : ninjas) {
                n.moveLeft = false;
                n.moveRight = false;
            }
        }
        if (key == 'd') {
            for (Ninja n : ninjas) {
                n.moveRight = false;
                n.moveLeft = false;
            }
        }
        
        if (key == 'w')
            for (Ninja n : ninjas){
                n.offset = 15;
            }

    }

    /*public void keyPressed() {

 

}

public void keyReleased() {
  if (key == 'a') {
    moveLeft = false;
  } else if (key == 'd') {
    moveRight = false;
   else if (key == 's'){
    raiseHand = false;
  }
}*/
    class Ninja {

        float y = height - 350;
        float x = 400;
        float r = 13;

        boolean moveRight;
        boolean moveLeft;
        boolean jump;
        boolean fall;
        boolean raiseHand;

        float xDir = 5;
        float initialY;
        float offset;
        
         float gravity = 0.1f;
        float speed = 0;
        float jumpHeight = 180;

        Random rng = new Random();
        int red;
        int green;
        int blue;

        public Ninja(int i) {
            x = (width / ninjaNumber) * i + 50;
            red = rng.nextInt(255);
            green = rng.nextInt(255);
            blue = rng.nextInt(255);
        }

        public void drawNinja() {

            fill(red, green, blue);
            ellipse(x, y - 25 - r, r * 2, r * 2);

            if (moveRight || moveLeft) {
                fill(red, green, blue);
                strokeWeight(10);
                stroke(red, green, blue);
                line(x, y, x + offset, y + 50); //left leg
                line(x, y, x - offset, y + 50); //right leg
                line(x, y, x, y - 25); //Body
                line(x, y - 25, x + offset, y); //left arm
                line(x, y - 25, x - offset, y); //right arm
                //noStroke();
                if (moveRight) {
                    fill(255);
                    ellipse(x + r / 2, y - 23 - r, 5, 5);
                } else if (moveLeft) {
                    fill(255);
                    ellipse(x - r / 2, y - 23 - r, 5, 5);
                }
            } else {
                fill(red, green, blue);
                strokeWeight(10);
                stroke(red, green, blue);
                line(x, y, x + 15, y + 50);
                line(x, y, x - 15, y + 50);
                line(x, y, x, y - 25);
                if (raiseHand) {
                    line(x, y - 25, x + 15, y - 50); //left arm
                } else {
                    line(x, y - 25, x + 15, y); //left arm
                }

                line(x, y - 25, x - 15, y);
                fill(255);
                //noStroke();
                ellipse(x - r / 2, y - 23 - r, 5, 5);
                ellipse(x + r / 2, y - 23 - r, 5, 5);
            }
        }

        public void moveNinja() {
            if (moveRight) {
                if (offset >= 25) {
                    xDir *= -1;
                    offset = 25;
                }
                if (offset <= -25) {
                    xDir *= -1;
                    offset = -25;
                }

                offset += xDir;
                if (x < height - 25) {
                    x += 5;
                }
            } else if (moveLeft) {
                if (offset > 25 || offset < -25) {
                    xDir *= -1;
                }

                offset -= xDir;
                if (x > 25) {
                    x -= 5;
                }
            }
            if (jump) {
                y = y - speed;
                if (speed > 1) {
                    speed = speed - gravity;
                }

                if (y <= initialY - jumpHeight) {
                    speed = speed * -1;
                    fall = true;
                } else if (fall && y >= initialY) {
                    y = initialY;
                    jump = false;
                }
            }

        }
    }
}
