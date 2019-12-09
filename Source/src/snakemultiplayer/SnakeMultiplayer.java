
package snakemultiplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeMultiplayer implements ActionListener, KeyListener
{

    public static SnakeMultiplayer snake;
    public renderSnake render;
    public Rectangle food;
    public ArrayList<Rectangle> snakeBody;

    Random rand = new Random();
    public int xfood, yfood, pieces, currentMotion = 0, currentMotion2, previousMotion = 0, xtemp, ytemp, player1score, player2score, addscore;

    boolean started = false, gameOver = false;

    public final int WIDTH = 1000, HEIGHT = 800;

    public SnakeMultiplayer()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

        render = new renderSnake();

        jframe.add(render);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setTitle("Snake X-zenia : Multiplayer");
        jframe.setVisible(true);

        xfood = rand.nextInt(700);
        yfood = rand.nextInt(700);

        food = new Rectangle(xfood, yfood, 20, 20);
        snakeBody = new ArrayList<Rectangle>();

        restart();

        timer.start();
    }

    public static void main(String[] args)
    {
        snake = new SnakeMultiplayer();
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        xtemp = snakeBody.get(0).x;
        ytemp = snakeBody.get(0).y;

        if (started && !gameOver)
        {

            if (currentMotion == 1)
            {
                snakeBody.get(0).y -= 11;
            } else if (currentMotion == 2)
            {
                snakeBody.get(0).x -= 11;
            } else if (currentMotion == 3)
            {
                snakeBody.get(0).y += 11;
            } else if (currentMotion == 4)
            {
                snakeBody.get(0).x += 11;
            }

            if (currentMotion2 == 6)
            {
                {
                    food.x -= 5;
                    player2score++;
                }
            }
            if (currentMotion2 == 7)
            {
                //  while(currentMotion2!=11)
                {
                    food.y += 5;
                    player2score++;
                }
            }
            if (currentMotion2 == 5)
            {
                // while(currentMotion2!=9)
                {
                    food.y -= 5;
                    player2score++;
                }
            }
            if (currentMotion2 == 8)
            {
                //  while(currentMotion2!=12)
                {
                    food.x += 5;
                    player2score++;
                }
            }

            if (currentMotion > 0)
            {
                for (int i = 1; i < snakeBody.size(); i++)
                {

                    int temp = xtemp;
                    xtemp = snakeBody.get(i).x;
                    snakeBody.get(i).x = temp;

                    temp = ytemp;
                    ytemp = snakeBody.get(i).y;
                    snakeBody.get(i).y = temp;

                }
            }
            if (snakeBody.get(0).intersects(food))
            {
                addPiece();
                addPiece();
                addPiece();
                addPiece();
                xfood = 10 + rand.nextInt(700);
                yfood = 10 + rand.nextInt(700);
                food.x = xfood;
                food.y = yfood;
            }

            if (snakeBody.get(0).x > WIDTH - 210)
            {
                snakeBody.get(0).x = 0;
            }
            if (snakeBody.get(0).x < 0)
            {
                snakeBody.get(0).x = WIDTH - 210;
            }
            if (snakeBody.get(0).y > HEIGHT - 50)
            {
                snakeBody.get(0).y = 0;
            }
            if (snakeBody.get(0).y < 0)
            {
                snakeBody.get(0).y = HEIGHT - 50;
            }

            if (food.x > WIDTH - 210)
            {
                food.x = WIDTH - 210;
                player2score -= 10;
            }
            if (food.x < 0)
            {
                food.x = 0;
                player2score -= 10;
            }
            if (food.y > HEIGHT - 50)
            {
                food.y = HEIGHT - 50;
                player2score -= 10;
            }
            if (food.y < 0)
            {
                food.y = 0;
                player2score -= 10;
            }

            player1score = snakeBody.size() - 4;
            for (int i = 1; i < snakeBody.size(); i++)
            {
                if (snakeBody.get(0).intersects(snakeBody.get(i)))
                {
                    currentMotion = 0;
                    previousMotion = 0;

                    restart();

                    gameOver = true;
                }

            }

        }

        render.repaint();
    }

    public void repaint(Graphics g)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.GRAY);
        g.fillRect(WIDTH - 200, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 4, 20));
        g.drawString("Player 1 Score = " + player1score, WIDTH - 195, HEIGHT / 2 - 200);
        g.drawString("Player 2 Score = " + (player2score / 30), WIDTH - 195, HEIGHT / 2 - 160);
        g.setFont(new Font("Arial", 4, 15));
        g.drawString("Snake X-Zenia ", WIDTH - 170, 50);
        g.drawString("Copright @ AMS", WIDTH - 172, 75);
        if (!started)
        {
            g.setFont(new Font("Arial", 4, 100));
            g.setColor(Color.WHITE);
            g.drawString("Snake X-Zenia", 100, HEIGHT / 2 - 50);
            g.setFont(new Font("Arial", 4, 50));
            g.drawString("Press to Play", 300, HEIGHT / 2 + 100);
            g.setFont(new Font("Arial", 4, 30));
            g.drawString("Copyright @ AMS", 325, HEIGHT / 2 + 200);
            g.setFont(new Font("Arial", 4, 20));
            g.drawString("Player 1 plays using w,a,s,d keys", 325, HEIGHT / 2 + 300);
            g.setFont(new Font("Arial", 4, 20));
            g.drawString("Player 2 plays using arrow keys", 325, HEIGHT / 2 + 320);

        }
        if (started && gameOver == false)
        {
            g.setColor(Color.green.darker());
            g.fillRoundRect(food.x, food.y, food.width, food.height, 10, 10);
            g.setColor(Color.white);
            g.fillOval(food.x + 5, food.y + 5, food.width / 2, food.height / 2);
            paintSnake(g);
        }

        if (gameOver)
        {
            g.setFont(new Font("Arial", 1, 100));
            g.setColor(Color.WHITE);
            g.drawString("GameOver", 200, HEIGHT / 2 - 50);
            g.setFont(new Font("Arial", 4, 50));
            g.drawString("Press to Play", 300, HEIGHT / 2 + 100);
        }
    }

    public void addPiece()
    {
        if (snakeBody.size() == 0)
        {
            snakeBody.add(new Rectangle(WIDTH / 2, HEIGHT / 2, 10, 10));
        } else
        {
            snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y, 10, 10));
        }
    }

    void paintSnake(Graphics g)
    {
        g.setColor(Color.red.darker());
        for (int i = 1; i < snakeBody.size(); i++)
        {
            g.fillRect(snakeBody.get(i).x, snakeBody.get(i).y, snakeBody.get(i).width, snakeBody.get(i).height);
        }
        g.setColor(Color.red.darker());
        g.fillOval(snakeBody.get(0).x - 4, snakeBody.get(0).y - 4, snakeBody.get(0).width + 8, snakeBody.get(0).height + 8);

    }

    void restart()
    {
        snakeBody.clear();
        snakeBody.add(new Rectangle(WIDTH / 2, HEIGHT / 2, 10, 10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size() - 1).x + 11, snakeBody.get(snakeBody.size() - 1).y, 10, 10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size() - 1).x + 11, snakeBody.get(snakeBody.size() - 1).y, 10, 10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size() - 1).x + 11, snakeBody.get(snakeBody.size() - 1).y, 10, 10));
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ae)
    {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ae.getKeyCode() == KeyEvent.VK_W)
        {
            if (started && previousMotion != 3)
            {
                currentMotion = 1;
                previousMotion = currentMotion;
            }
            if (!started)
            {
                started = true;
            }
            if (gameOver)
            {
                player2score = 0;
                gameOver = false;
            }

        } else if (ae.getKeyCode() == KeyEvent.VK_A)
        {
            if (started && previousMotion != 4)
            {
                currentMotion = 2;
                previousMotion = currentMotion;
            }
            if (!started)
            {
                started = true;
            }
            if (gameOver)
            {
                player2score = 0;
                gameOver = false;
            }

        } else if (ae.getKeyCode() == KeyEvent.VK_S)
        {
            if (started && previousMotion != 1)
            {
                currentMotion = 3;
                previousMotion = currentMotion;
            }
            if (!started)
            {
                started = true;
            }
            if (gameOver)
            {
                player2score = 0;
                gameOver = false;
            }

        } else if (ae.getKeyCode() == KeyEvent.VK_D)
        {

            if (currentMotion > 0)
            {
                if (started && previousMotion != 2)
                {
                    currentMotion = 4;
                    previousMotion = currentMotion;
                }
            }
            if (!started)
            {
                started = true;
            }
            if (gameOver)
            {
                player2score = 0;
                gameOver = false;
            }

        } else if (ae.getKeyCode() == KeyEvent.VK_P)
        {
            if (!started)
            {
                started = true;
            }
            if (gameOver)
            {
                gameOver = false;
            }

        } else if (ae.getKeyCode() == KeyEvent.VK_UP)
        {
            currentMotion2 = 5;
        } else if (ae.getKeyCode() == KeyEvent.VK_LEFT)
        {
            currentMotion2 = 6;
        } else if (ae.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentMotion2 = 7;
        } else if (ae.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            currentMotion2 = 8;
        }
    }

    @Override
    public void keyReleased(KeyEvent ae)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
