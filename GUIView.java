/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Model.*;
import Controller.GameController;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author huzai
 */
public class GUIView implements Observer,  ActionListener{
    
    private JFrame gameFrame;
    private JFrame mainMenuFrame;
    private JPanel boardPanel;
    private JPanel menuPanel;
    private JButton [][] boardButtons;
    private JTextField playername;
    private JLabel playerLabel;
    private JRadioButton randomPlacement;
    private JRadioButton manualPlacement;
    private ButtonGroup shipPlacementGroup;
    private JButton startGame;
    private JLabel totalTries;
    private JLabel liveShips;
    private JButton restart;
    
    
    private String playerName;
    private int playerTried;
    
    
    private GameController controller;
    
   
    
    public GUIView(GameController gc){
        this.controller = gc;
        this.totalTries = new JLabel("Total Tries:  0");
        this.liveShips = new JLabel("Ships Live: 0");
    }
    
    private void reset(){
        this.controller.reset();
    }
    
    private void showBoard(){
    
        this.gameFrame = new JFrame("Battleship");
        this.gameFrame.setLayout(new BorderLayout());
        
        this.restart = new JButton("Restart");
        restart.addActionListener(this);
        this.totalTries = new JLabel("Total Tries:  0");
        JPanel infoPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 3; 
        gbc.anchor = GridBagConstraints.NORTH; 
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel heading = new JLabel("Battleship");
        heading.setFont(new Font("Arial", Font.BOLD, 24)); 
        infoPanel.add(heading, gbc);
        
        gbc.gridy++; 
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 50);
        infoPanel.add(this.totalTries, gbc);
        
        
        gbc.gridx++;
        infoPanel.add(liveShips, gbc);
        
        gbc.gridx = 2;
        gbc.insets = new Insets(10, 50, 10, 10);
        infoPanel.add(this.restart, gbc);
        
        this.boardPanel = new JPanel(new GridLayout(10,10));
        this.boardButtons = new JButton[10][10];

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.boardButtons[i][j] = new JButton();
                Font font = new Font(this.boardButtons[i][j].getFont().getName(), Font.PLAIN, 10);
                this.boardButtons[i][j].setFont(font);
                this.boardButtons[i][j].addActionListener(this);
                this.boardPanel.add(this.boardButtons[i][j]);
            }
        }
        
        this.gameFrame.add(infoPanel, BorderLayout.NORTH);
        this.gameFrame.add(boardPanel, BorderLayout.CENTER);
        this.gameFrame.setSize(450,500);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
        
    }
    
    public void showMainMenu(){
        
        this.mainMenuFrame = new JFrame("Battleship | Main Menu");
        
        JPanel panel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel heading = new JLabel("Battleship");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(heading, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy++;
        
        this.randomPlacement = new JRadioButton("Randomly Place Ships", true);
        this.manualPlacement = new JRadioButton("Manual Configuration of Ships");
        this.manualPlacement.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JOptionPane.showMessageDialog(menuPanel, "To load ship's configuration from file. There is a file named placement.txt in project's root directory.\n Add your configuration in that file. There should be 5 lines in the file. Each line represent one ship.\n In each line there should be 4 comma(,) separated values. Like this, 4,2,5,H \n In given example 4 is x-coordinate, 2 is y-coordinate, 5 is length of the ship and H is the orientation of the ship. \n Orientation will be H or V. H for horizontal and V for Vertical placement. ", "File Input Format", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        this.shipPlacementGroup = new ButtonGroup();
        this.shipPlacementGroup.add(this.randomPlacement);
        this.shipPlacementGroup.add(this.manualPlacement);
        
        panel.add(this.randomPlacement, gbc);
        gbc.gridy++;
        panel.add(this.manualPlacement, gbc);
        
        gbc.gridy++;
        gbc.gridwidth = 1; 
        this.playerLabel = new JLabel("Player Name");
        panel.add(this.playerLabel, gbc);
        gbc.gridx++;
        this.playername = new JTextField(10);
        panel.add(this.playername, gbc);
        
        
        gbc.gridx = 0; 
        gbc.gridy++; 
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        this.startGame = new JButton("Start Game");
        this.startGame.addActionListener(this);
        panel.add(this.startGame, gbc);
        
        this.mainMenuFrame.add(panel);
        
        
        this.mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainMenuFrame.setSize(350,250);
        this.mainMenuFrame.setLocationRelativeTo(null);
        this.mainMenuFrame.setVisible(true);
        
    }
    
    
    private void disableBoard(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.boardButtons[i][j].setEnabled(false);
            }
        }   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.startGame){
            
            String playerName = this.playername.getText();
            if(playerName.isEmpty()){
                JOptionPane.showMessageDialog(this.mainMenuFrame, "Player Name is missing", "Name missing", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.controller.setPlayerName(playerName);
            
            if(this.randomPlacement.isSelected()){
                this.controller.shipPlacementMode(1);
                this.mainMenuFrame.dispose();
                this.showBoard();
            }
            else{
                if(this.controller.shipPlacementMode(2)){
                    this.mainMenuFrame.dispose();
                    this.showBoard();
                }
                else{
                    JOptionPane.showMessageDialog(mainMenuFrame, "There is some configuration in file. Please verify.","Wrong Configuration", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(e.getSource() == this.restart){
            this.gameFrame.dispose();
            this.reset();
            this.showMainMenu();
        }
        else{
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    if(e.getSource() == this.boardButtons[i][j]){
                        if(controller.attack(j, i)){
                            this.boardButtons[i][j].setBackground(Color.RED);
                        }
                        else{
                            this.boardButtons[i][j].setBackground(Color.GREEN);
                        }
                        this.boardButtons[i][j].setEnabled(false);
                        
                    }
                }
            }
        }
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            
            Player player = (Player) o;
            this.playerName = player.getName();
            this.playerTried = player.getTries();
            this.totalTries.setText("Total Tries:  " + this.playerTried);
        }
        if (o instanceof Board) {
            Board b = (Board) o;
            int liveShips = b.getShipsOnBoard();
            this.liveShips.setText("Ships Live: " + liveShips);
            if(arg != null && arg.equals("gameover")){
                this.disableBoard();
                JOptionPane.showMessageDialog(this.gameFrame, "Game complete\n Player Name: " + this.playerName + "\n Total Tries: " + this.playerTried, "Game Complete", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    
    }
    
}
