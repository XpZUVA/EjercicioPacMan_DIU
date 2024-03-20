import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

public class Pelota extends JFrame{
    //PANEL
    private final int w = 300;
    private final int  h = 150;
    //TIMER
    private Timer tGeneral, tAnimaciones;
    private boolean isRight = true, isDown = false, isHorizontal = true;
    //POSICIONES
    private int x  = 200, ballX = 100;
    private int y = 100, ballY = 100;
    private int step = 2;
    //IMAGENES
    private ImageIcon pacman, ball;
    private Rectangle pacmanRect, ballRect;
    private int pacmanW, pacmanH, ballW, ballH;
    private final DrawCanvas canvas;
    //ETIQUETA
    private int puntos = 0;
    private JLabel label;
    //ANIMACIONES
    ArrayList<ImageIcon> derecha;
    ArrayList<ImageIcon> izquierda;
    ArrayList<ImageIcon> arriba;
    ArrayList<ImageIcon> abajo;
    private int contador = 0;






    public Pelota(){
        //OPCIONES PACMAN Y BOLA
        pacman = new ImageIcon(getClass().getResource("/main/resources/pacman.gif"));
        cargarAnimaciones();
        pacmanW = pacman.getIconWidth();
        pacmanH = pacman.getIconHeight();
        ball = new ImageIcon(getClass().getResource("/main/resources/ball.gif"));
        canvas = new DrawCanvas();
        ballW = ball.getIconWidth();
        ballH = ball.getIconHeight();

        //RECTANGULOS
        pacmanRect = new Rectangle(x, y, pacmanW, pacmanH);
        ballRect = new Rectangle(ballX, ballY, ballW, ballH);

        // OPCIONES ETIQUETA
        label = new JLabel("Puntos: " + puntos);
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
                    isDown = false;
                    isHorizontal = false;

                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isDown = true;
                    isHorizontal = false;

                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isRight = false;
                    isHorizontal = true;

                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRight = true;
                    isHorizontal = true;


                }
                repaint();
            }
        });

    }

    //INICIALIZAR TIMER
    public void init() {
        tGeneral = new Timer(0, new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if(isHorizontal){
                if (isRight){
                    if (x < w - pacmanW - step){
                        x += step;
                        y += 0;
                    }
                } else {
                    if (x >= step){
                        x -= step;
                        y += 0;
                    }

                }
                }else if (isDown){
                    if (y < h - pacmanH - step){
                        y += step;
                        x += 0;
                    }
                } else {
                    if (y >= step){
                        y -= step;
                        x += 0;
                    }
                }

                //COLISIONES
                pacmanRect.setBounds(x, y, pacmanW, pacmanH);
                ballRect.setBounds(ballX, ballY, ballW/4, ballH/4);

                if (pacmanRect.intersects(ballRect)){
                    puntos++;
                    label.setText("Puntos: " + puntos);
                    Random random = new Random();
                    ballX = random.nextInt(w - ballW);
                    ballY = random.nextInt(h - ballH);
                }
                repaint();
            }
        });

        tAnimaciones = new Timer(85, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(isHorizontal){
                    if(isRight){
                        pacman = derecha.get(contador);
                    }else{
                        pacman = izquierda.get(contador);
                    }
                }else{
                    if(isDown){
                        pacman = abajo.get(contador);
                    }else{
                        pacman = arriba.get(contador);
                    }
                }
                contador++;
                if(contador == 3){
                    contador = 0;
                }
                repaint();
            }
        });

        tGeneral.start();
        tAnimaciones.start();
    }

    //PINTAR PACMAN Y BOLA
    class DrawCanvas extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            this.setBackground(Color.gray);
            g.setColor(Color.black);
            g.fillRect(0, 0, w, h);
            pacman.paintIcon(this, g, x, y);
            g.drawImage(ball.getImage(), ballX, ballY, 12,12,this);

        }
    }

    public void cargarAnimaciones(){
        derecha = new ArrayList<ImageIcon>();
        izquierda = new ArrayList<ImageIcon>();
        arriba = new ArrayList<ImageIcon>();
        abajo = new ArrayList<ImageIcon>();

        derecha.add(new ImageIcon(getClass().getResource("/main/resources/right1.gif")));
        derecha.add(new ImageIcon(getClass().getResource("/main/resources/right2.gif")));
        derecha.add(new ImageIcon(getClass().getResource("/main/resources/right3.gif")));

        izquierda.add(new ImageIcon(getClass().getResource("/main/resources/left1.gif")));
        izquierda.add(new ImageIcon(getClass().getResource("/main/resources/left2.gif")));
        izquierda.add(new ImageIcon(getClass().getResource("/main/resources/left3.gif")));

        arriba.add(new ImageIcon(getClass().getResource("/main/resources/up1.gif")));
        arriba.add(new ImageIcon(getClass().getResource("/main/resources/up2.gif")));
        arriba.add(new ImageIcon(getClass().getResource("/main/resources/up3.gif")));

        abajo.add(new ImageIcon(getClass().getResource("/main/resources/down1.gif")));
        abajo.add(new ImageIcon(getClass().getResource("/main/resources/down2.gif")));
        abajo.add(new ImageIcon(getClass().getResource("/main/resources/down3.gif")));

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
