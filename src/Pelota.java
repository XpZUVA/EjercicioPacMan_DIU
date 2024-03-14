import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Pelota extends JFrame{
    private final int w = 300;
    private final int  h = 150;
    private Timer t, t2;
    private int x  = 200;
    private int y = 100;
    private int step = 5;
    private ImageIcon ball;
    private boolean isRight = true;
    private boolean isDown = true;
    private int ballW, ballH;
    private final DrawCanvas canvas;
    private int rebotes = 0;
    private JLabel label;

    public Pelota(){
        //OPCIONES BOLA
        ball = new ImageIcon(getClass().getResource("/main/resources/pacman.gif"));
        ballW = ball.getIconWidth();
        ballH = ball.getIconHeight();
        canvas = new DrawCanvas();



        // OPCIONES ETIQUETA
        label = new JLabel("Rebotes: " + rebotes);
        label.setHorizontalAlignment(JLabel.LEADING);

        //OPCIONES PANEL
        setTitle("Pelota");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(label, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        setSize(500, 300);
        setVisible(true);

        //EVENTOS TECLADO
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if(t.isRunning()){
                            t.stop();
                            t2.start();
                            isDown = false;
                        }else{
                            isDown = false;
                        }

                        canvas.repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(t.isRunning()){
                        t.stop();
                        t2.start();
                        isDown = true;
                    }else{
                        isDown = true;
                    }

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(t2.isRunning()){
                        t2.stop();
                        t.start();
                        isRight = false;
                    }else{
                        isRight = false;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(t2.isRunning()){
                        t2.stop();
                        t.start();
                        isRight = true;
                    }
                    else{
                        isRight = true;
                    }

                }
                repaint();
            }
        });

    }

    //INICIALIZAR TIMER
    public void init() {
        //TIMER HORIZONTAL
        t = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isRight) {
                    if (x < w - ballW - step)
                        x += step;
                    else {
                        isRight = false;
                        rebotes++;
                        label.setText("Rebotes: " + rebotes);
                    }
                } else {
                    if (x >= step)
                        x -= step;
                    else {
                        isRight = true;
                        rebotes++;
                        label.setText("Rebotes: " + rebotes);
                    }
                }
                repaint();
            }
        });

        //TIMER VERTICAL
        t2 = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isDown) {
                    if (y < h - ballH - step)
                        y += step;
                    else {
                        isDown = false;
                        rebotes++;
                        label.setText("Rebotes: " + rebotes);
                    }
                } else {
                    if (y >= step)
                        y -= step;
                    else {
                        isDown = true;
                        rebotes++;
                        label.setText("Rebotes: " + rebotes);
                    }
                }
                repaint();
            }
        });

        t.start();
    }

    //PINTAR BOLA
    class DrawCanvas extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            this.setBackground(Color.gray);
            g.setColor(Color.black);
            g.fillRect(0, 0, w, h);
            ball.paintIcon(this, g, x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Pelota pelota = new Pelota();
                pelota.init(); // Iniciamos la animación
            }
        });
    }
}
