/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcoTester;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Umair Nazir
 */
public class calcoTester {
    
    boolean gameIsOn = true;
    Player userAsPlayer;
    Player computerAsPlayer;
    JTextField yourSpeed;
    int xPos = 300, yPos = 260;
    int YABOVE = 1, YBELOW = 260;
    int additionalSetDistance = 0;
    JPanel yourBouncingPanel;
    JLabel yourColorLabel;
    movingBallPanel centralPanel;
    JButton startYourTurn;
    boolean goButtonClicked = false;
    JPanel computersBouncingPanel;
    JLabel computersBouncingLabel;
    Color computersBouncingColor;
    Color usersBouncingColor;
    int distanceToTravel;
    int maxDistance  = 260;
    boolean colorChosen = false;
    JButton chooseYourColorButton;

    void start() {
        
        userAsPlayer = new Player();
        computerAsPlayer = new Player();
        
        buildGUI();
        
        
        
        while(gameIsOn){
            
            userAsPlayer.yourTurn();
            computerAsPlayer.computersTurn();
            
        }
        
    }

    private void buildGUI() {
        
        //main window
        JFrame mainFrame= new JFrame("CalcoTester");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        //users portion at bottom
        JPanel yourPanel = new JPanel();
        yourPanel.setLayout(new BoxLayout(yourPanel, BoxLayout.Y_AXIS));
        
        yourBouncingPanel = new JPanel();
        yourBouncingPanel.setBackground(Color.black);
        
        //your panel where your controls will go
        JPanel yourControlPanel = new JPanel();
        yourControlPanel.setBackground(Color.orange);
        JLabel setSpeed = new JLabel("Add Distance: ");
        JSlider speedChangeSlider   = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 45);
        speedChangeSlider.setPaintLabels(true);
        speedChangeSlider.setPaintTicks(true);
        speedChangeSlider.setPaintTrack(true);
        speedChangeSlider.setSnapToTicks(true);
        speedChangeSlider.setMinorTickSpacing(1);
        speedChangeSlider.setMajorTickSpacing(20);
        
        speedChangeSlider.addChangeListener(new ChangeListener(){
           
            @Override
            public void stateChanged(ChangeEvent ce) {
                
                yourSpeed.setText(speedChangeSlider.getValue()+"");
                
            }
        });
        
        yourSpeed= new JTextField("45");
        
        startYourTurn = new JButton("Go");
        startYourTurn.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                          goButtonClicked = true;              
                }
            });
        
        chooseYourColorButton = new JButton("My Turn");
        yourColorLabel = new JLabel("");
        chooseYourColorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                colorChosen = true;
                usersBouncingColor = userAsPlayer.calculateColor();
                yourBouncingPanel.setBackground(usersBouncingColor);
                yourColorLabel.setText("("+usersBouncingColor.getRed()+", "+usersBouncingColor.getGreen()+", "+usersBouncingColor.getBlue()+")");
            }
        });
        
        
        yourControlPanel.add(setSpeed);
        yourControlPanel.add(speedChangeSlider);
        yourControlPanel.add(yourSpeed);
        yourControlPanel.add(startYourTurn);
        yourControlPanel.add(chooseYourColorButton);
        yourControlPanel.add(yourColorLabel);
        
        yourPanel.add(yourBouncingPanel);
        yourPanel.add(yourControlPanel);
        
        mainFrame.add(BorderLayout.SOUTH, yourPanel);
        
        //computers panel
        JPanel computersPanel = new JPanel();
        computersPanel.setLayout(new BoxLayout(computersPanel, BoxLayout.Y_AXIS));
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.orange);
        JLabel computersLabel = new JLabel("AI Agent");
        labelPanel.add(computersLabel);
        labelPanel.add(new JLabel("     "));
        labelPanel.add(new JLabel("Bouncing Color Chosen:"));
        computersBouncingPanel = new JPanel();
        computersBouncingLabel = new JLabel("(0, 0, 0)");
        labelPanel.add(computersBouncingLabel);
        
        computersBouncingPanel.setBackground(Color.black);
        
        computersPanel.add(labelPanel);
        computersPanel.add(computersBouncingPanel);
        
        mainFrame.add(BorderLayout.NORTH,computersPanel);
        
        //central gui component a moving ball
        centralPanel = new movingBallPanel();
        
        mainFrame.add(BorderLayout.CENTER, centralPanel);
        
        mainFrame.setSize(700, 500);
        mainFrame.setVisible(true);
        
        
        
        
        
        
        
        
    }

    private class Player {
        
        
        public Player() {
        }

       
        private void yourTurn() {
            
            
            while(true){
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                yPos = 260;
                centralPanel.repaint();
               
                boolean flag = true;
                
                computersBouncingColor = computerAsPlayer.calculateColor();
                computersBouncingPanel.setBackground(computersBouncingColor);
                computersBouncingLabel.setText("("+computersBouncingColor.getRed()+", "+computersBouncingColor.getGreen()+", "+computersBouncingColor.getBlue()+")");
                
                additionalSetDistance = Integer.parseInt(yourSpeed.getText());
                distanceToTravel = (int) ((computersBouncingColor.getRed() + computersBouncingColor.getGreen() + computersBouncingColor.getBlue() + additionalSetDistance)*2.6);
                
            for (int count = 0; count < distanceToTravel; count++ ){
                
                if(!goButtonClicked){
                           
                            count = 0;
                            
                            try {
                                Thread.sleep(1000);
                                //do nothing and wait for button to be clicked
                                
                            } catch (InterruptedException ex) {
                                Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                            }
                } else {
                    
                    if(yPos == YABOVE){
                        
                        flag = false;
                        
                    }
                    
                    if(yPos == YBELOW){
                        
                        flag = true;
                        
                    }
                    
                    if(flag){

                        yPos--;
                    
                            
                    } else {
                    
                        yPos++;
                    }
                
                    centralPanel.repaint();
                            
                    try {
                        
                        Thread.sleep(5);
                                
                    } catch (InterruptedException ex) {
                        
                        Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                        
                    
                    
                }   
                
            }
            
            if((yPos) > 180){
                
                goButtonClicked = false;
                
                //continue playing
                
            } else { 
                
                chooseYourColorButton.setText("Choose Color");
                chooseYourColorButton.setEnabled(true);
                computersBouncingPanel.setBackground(Color.BLACK);
                computersBouncingLabel.setText("");
                goButtonClicked = false;
                break;
                
            }
            
            }
            
        }

        private void computersTurn() {
            
            
            while(true){
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                yPos = 1;
                centralPanel.repaint();
                
                boolean flag = true;
                
                while(!colorChosen){
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                yourColorLabel.setText("("+usersBouncingColor.getRed()+", "+usersBouncingColor.getGreen()+", "+usersBouncingColor.getBlue()+")");
                yourBouncingPanel.setBackground(usersBouncingColor);
                additionalSetDistance = (int) (Math.random()*100);
                distanceToTravel = (int) ((usersBouncingColor.getRed() + usersBouncingColor.getGreen() + usersBouncingColor.getBlue() + additionalSetDistance)*2.6);
                
            for (int count = 0; count < distanceToTravel; count++ ){
                
                    
                    if(yPos == YABOVE){
                        
                        flag = true;
                        
                    }
                    
                    if(yPos == YBELOW){
                        
                        flag = false;
                        
                    }
                    
                    if(flag){

                        yPos++;
                    
                            
                    } else {
                    
                        yPos--;
                    }
                
                    centralPanel.repaint();
                            
                    try {
                        
                        Thread.sleep(05);
                                
                    } catch (InterruptedException ex) {
                        
                        Logger.getLogger(calcoTester.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                        
                    
                    
                }   
            
            if(yPos+100 > 180){
                
                chooseYourColorButton.setText("Your Turn");
                chooseYourColorButton.setEnabled(false);
                yourBouncingPanel.setBackground(Color.BLACK);
                yourColorLabel.setText("");
                colorChosen = false;
                break;
                
                
                
            } else {
                
                 
                //continue playing
                 colorChosen = false;
      
            }
            
            }
            
            
        }
        
        public Color calculateColor(){
            
            int red = (int) (Math.random()*255);
            int green = (int) (Math.random()*255);
            int blue = (int) (Math.random()*255);
            
            return (new Color(red, green, blue));
            
        }
        
        public void updateLabelAndBouncingColors(Color usersColor, Color compsColor){
            
            
            
        }
    }

    private class movingBallPanel extends JPanel{

        public movingBallPanel() {
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            GradientPaint gradient= new GradientPaint(100, 100, Color.BLUE, 400, 400,Color.RED);
           
            g2d.setPaint(gradient);
            
            g2d.drawLine(0, 180, 700, 180);
            
            g2d.fillOval(xPos, yPos, 100, 100);
            
            
        }
    }
    
}
