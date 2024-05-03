/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import Model.*;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 *
 * @author huzai
 */
public class GameController {
    private Board board;
    private Player player;
    
    public GameController(Board b, Player p){
        this.board = b;
        this.player = p;
    }
    
    public void reset(){
        this.board.resetBoard();
        this.player.setTries(0);
    }
    
    public void setPlayerName(String name){
        this.player.setName(name);
    }
    
    // if mode is 1 mean randomly place ships and if mode 2 place ships using file.
    public boolean shipPlacementMode(int mode){
        if(mode == 1){
            return this.board.placeRandomlyAllShips();
        }
        else{
            return this.loadShipsFromFile();
        }        
    }
    
    public boolean attack(int x, int y){
        this.player.setTries(this.player.getTries() + 1);
        return board.attack(x, y);
    }
    
    private boolean loadShipsFromFile(){
        String filePath = "placement.txt";
        
        int shipOflengthFive = 1;
        int shipOflengthFour = 1;
        int shipOflengthThree = 1;
        int shipOflengthTwo = 2;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String [] values = line.split(",");
                
                int x = Integer.parseInt( values[0]);
                int y = Integer.parseInt( values[1]);
                int length = Integer.parseInt( values[2]);
                char ort = values[3].charAt(0);
                ort = Character.toUpperCase(ort);
                if(ort != 'H' && ort != 'V'){
                    System.out.println("Orientation can only be H or V");
                    this.board.resetBoard();
                    return false;
                }
                switch(length){
                    case 5:
                        
                        shipOflengthFive--;
                        if(shipOflengthFive < 0){
                            System.out.println("Only 1 Ship can be place of length 5.");
                            this.board.resetBoard();
                            return false;
                        }
                        break;
                    case 4:
                        
                        shipOflengthFour--;
                        if(shipOflengthFour < 0){
                            System.out.println("Only 1 Ship can be place of length 4.");
                            this.board.resetBoard();
                            return false;
                        }
                        break;
                    case 3:
                        
                        shipOflengthThree--;
                        if(shipOflengthThree < 0){
                            System.out.println("Only 1 Ship can be place of length 3.");
                            this.board.resetBoard();
                            return false;
                        }
                        break;
                    case 2:
                        
                        shipOflengthTwo--;
                        if(shipOflengthTwo < 0){
                            System.out.println("Only 2 Ship can be place of length 2.");
                            this.board.resetBoard();
                            return false;
                        }
                        break;
                }
                Ship s = new Ship(x,y,length,ort);
                if(!board.placeShip(s)){
                    System.out.println("Wrong Orientation or boundary exceed.");
                    this.board.resetBoard();
                    return false;
                }
               
            }
        } catch (Exception e) {
            System.out.println("An error occurred. While reading file try again.");
            return false;
        }
        return true;
    }
}
