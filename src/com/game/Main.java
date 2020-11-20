package com.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Tamagotchi extends JFrame {

    int lvl_Jedzenie = 5;
    int lvl_Zabawa = 5;
    int lvl_Spanie = 5;


    public int getLvl() {
        return Math.min(Math.min(lvl_Spanie, lvl_Jedzenie), lvl_Zabawa);
    }

    public void wypiszInformacje() {
        System.out.println("=========================================");
        System.out.println("Jedzenie: " + lvl_Jedzenie);
        System.out.println("Zabawa: " + lvl_Zabawa);
        System.out.println("Spanie: " + lvl_Spanie);
        System.out.println("=========================================");
    }

    class OknoGry extends JPanel {
        public OknoGry() {
            setLayout(null);
            dodajKlawisze();
        }

        private void dodajKlawisze() {
            try {
                ImageIcon ikonaKlawisza = new ImageIcon(ImageIO.read(new File("grafika/klawisz.jpg")));

                JButton klawiszJedzenia = new JButton();
                klawiszJedzenia.setIcon(ikonaKlawisza);
                klawiszJedzenia.setBounds(152, 290, 50, 25);
                klawiszJedzenia.setBorderPainted(false);
                klawiszJedzenia.addActionListener(e -> {
                    if(lvl_Jedzenie < 5) lvl_Jedzenie++;
                    repaint();
                });


                JButton klawiszZabawy = new JButton();
                klawiszZabawy.setIcon(ikonaKlawisza);
                klawiszZabawy.setBounds(205, 310, 50, 25);
                klawiszZabawy.setBorderPainted(false);
                klawiszZabawy.addActionListener(e -> {
                    if(lvl_Zabawa < 5) lvl_Zabawa++;
                    repaint();
                });


                JButton klawiszSpania= new JButton();
                klawiszSpania.setIcon(ikonaKlawisza);
                klawiszSpania.setBounds(258, 290, 50, 25);
                klawiszSpania.setBorderPainted(false);
                klawiszSpania.addActionListener(e -> {
                    if(lvl_Spanie < 5) lvl_Spanie++;
                    repaint();
                });

                add(klawiszSpania);
                add(klawiszJedzenia);
                add(klawiszZabawy);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(450, 450);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage obrazek;

            try {
                obrazek = ImageIO.read(new File("grafika/" + getLvl() + ".jpg"));

                g.drawImage(obrazek, 0, 0, this);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public Tamagotchi(String nazwa) {
        super(nazwa);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OknoGry okno = new OknoGry();

        add(okno);
        setVisible(true);
        pack();

        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean czyPrzegral = false;
                while(!czyPrzegral) {
                    try {
                        Thread.sleep(2000);
                        int coZmieniamy = new Random().nextInt(3) + 1;

                        if(coZmieniamy == 1) lvl_Jedzenie--;
                        if(coZmieniamy == 2) lvl_Zabawa--;
                        if(coZmieniamy == 3) lvl_Spanie--;

                        wypiszInformacje();
                        if(getLvl() > 0)
                            repaint();
                        else
                            czyPrzegral = true;
                    }catch(Exception ex) {}
                }

                JOptionPane.showMessageDialog(null, "Przegrales");
            }
        }).start();
    }

    public static void main(String[] args) {
        new Tamagotchi("Tamagotchi");
    }



}
