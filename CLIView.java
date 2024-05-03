/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.util.Scanner;
import Model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author huzai
 */
public class CLIView {
    
    private Board board = new Board();
    private Player player = new Player();
    
    private void displayBoard(Board board){
        
        int[][] b = board.getBoard();
        System.out.println("Live Ships: " + board.getShipsOnBoard());
        System.out.println("       A  B  C  D  E  F  G  H  I  J");
        System.out.println("     --------------------------------");
        for(int i = 0; i < 10; i++){
            
            if(i == 9){
                System.out.print(i+1 + "  |  ");
            }
            else{
                System.out.print(i+1 + "   |  ");
            }
            
            for(int j = 0; j < 10; j++){
                switch(b[i][j]){
                    case 0:
                        System.out.print("*  ");
                        break;
                    case 1:
                        System.out.print("S  ");
                        break;
                    case 2:
                        System.out.print("H  ");
                        break;
                    case 3:
                        System.out.print("M  ");
                        break;
                    default:
                        System.out.print("*  ");
                      
                }
            }
            System.out.println("|");
        }
        System.out.println("     --------------------------------");
    }
    
    private String askPlayerName(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Player Name: ");
        String name = scanner.nextLine();
        return name;
        
    }
    
    private int askShipPlacementMode(){
        int mode;
        do{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select Ship's placement way. ");
            System.out.println("1. Randomly place ship by computer.");
            System.out.println("2. Load file for ship's location.");
            mode = scanner.nextInt();
        }
        while(mode > 2 || mode < 1);
        
        return mode;
    }
    
    private int confirmFileRead(){
        
        int confirm = 0;
        System.out.println("To load ship's configuration from file. There is a file named placement.txt in project's root directory.");
        System.out.println("Add your configuration in that file. There should be 5 lines in the file. each line represent one ship.");
        System.out.println("In each line there should be 4 comma(,) separated values. Like this, 4,2,5,H");
        System.out.println("In given example 4 is x-coordinate, 2 is y-coordinate, 5 is length of the ship and H is the orientation of the ship.");
        System.out.println("Orientation will be H or V. H for horizontal and V for Vertical placement");
        System.out.println("Press 1 if you read the instruction and place your configuration in the file.");
        Scanner scanner = new Scanner(System.in);
        confirm = scanner.nextInt();
        return confirm;
        
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
                    this.board = new Board();
                    return false;
                }
                switch(length){
                    case 5:
                        
                        shipOflengthFive--;
                        if(shipOflengthFive < 0){
                            System.out.println("Only 1 Ship can be place of length 5.");
                            this.board = new Board();
                            return false;
                        }
                        break;
                    case 4:
                        
                        shipOflengthFour--;
                        if(shipOflengthFour < 0){
                            System.out.println("Only 1 Ship can be place of length 4.");
                            this.board = new Board();
                            return false;
                        }
                        break;
                    case 3:
                        
                        shipOflengthThree--;
                        if(shipOflengthThree < 0){
                            System.out.println("Only 1 Ship can be place of length 3.");
                            this.board = new Board();
                            return false;
                        }
                        break;
                    case 2:
                        
                        shipOflengthTwo--;
                        if(shipOflengthTwo < 0){
                            System.out.println("Only 2 Ship can be place of length 2.");
                            this.board = new Board();
                            return false;
                        }
                        break;
                }
                Ship s = new Ship(x,y,length,ort);
                if(!board.placeShip(s)){
                    System.out.println("Wrong Orientation or boundary exceed.");
                    this.board = new Board();
                    return false;
                }
               
            }
        } catch (Exception e) {
            System.out.println("An error occurred. While reading file try again.");
            return false;
        }
        return true;
    }
    
    private void displayGameOver(Player p){
        System.out.println("Game Complete");
        System.out.println("Player Name: " + p.getName());
        System.out.println("Total tries: " + p.getTries());
    }
    private boolean formatCheck(String input){
        
        String regex = "(?i)^[A-J]([1-9]|10)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
    private String askTry(){
        
        boolean correctInput = true;
        String coOrdinates;
        do{
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Co-ordinate to Attack: ");
            coOrdinates = scanner.nextLine();
            if(this.formatCheck(coOrdinates)){
                correctInput = false;
            }

        }
        while(correctInput);
        
        return coOrdinates;
            
        
    }
    
    private int[] convertToCoordinate(String input){
        input = input.toUpperCase();
        char column = input.charAt(0);
        int row = Integer.parseInt(input.substring(1));
        int x = column - 'A';
        int y = row - 1;
        int [] coordinates = {x, y};
        return coordinates;
    }
       
    public static void main(String [] arg){
        
        
        
        Scanner scanner = new Scanner(System.in);
        CLIView cli = new CLIView();        
        String playerName = cli.askPlayerName();
        cli.player.setName(playerName);
        
        int shipPlacement = cli.askShipPlacementMode();
        if(shipPlacement == 2){
            do{
                cli.confirmFileRead();
            }
            while(!cli.loadShipsFromFile());
        }
        else{
            cli.board.placeRandomlyAllShips();
        }
        
        cli.displayBoard(cli.board);
        
        do{
            String attack = cli.askTry();
            int [] coordinates = cli.convertToCoordinate(attack);
            cli.player.setTries(cli.player.getTries() + 1);
            cli.board.attack(coordinates[0], coordinates[1]);
            cli.displayBoard(cli.board);
        }
        while(!cli.board.isGameOver());
        
        cli.displayGameOver(cli.player);
        
        
       
    }
}
