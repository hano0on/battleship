/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Observable;
import java.util.Random;

/**
 *
 * @author huzai
 */
public class Board extends Observable{
    
    private final int[][] board;
    private int shipsOnBoard;
    private Ship[] ships;
    
    public Board(){
        this.board = new int[10][10];
        this.shipsOnBoard = 0;
        this.ships = new Ship[5];
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.board[i][j] = 0;
            }
        }
    }
    
    private Ship createShip(int length){
        // generate Random number for orientation
        // if number is 0 than ship will be horizontally oriented 
        // else ship will be vertically oriented 
        
        char shipsOrientation;
        Random rand = new Random();
        int randOrientation = rand.nextInt(2);
        if(randOrientation == 0){
            shipsOrientation = 'H';
        }
        else{
            shipsOrientation = 'V';
        }
        
        // Generate Random co-ordinate also check they don't exceed board boundaries
        int x;
        int y;
        if(shipsOrientation == 'H'){
            x = rand.nextInt(10 - length);
            y = rand.nextInt(10);
        }
        else{
            x = rand.nextInt(10);
            y = rand.nextInt(10 - length);
        }
        Ship s = new Ship(x, y, length, shipsOrientation);
        return s;
    }
    
    public boolean placeRandomlyAllShips(){
        
        Ship s = this.createShip(5);
        this.placeShip(s);
        do{
            s = this.createShip(4);
        }
        while(!this.placeShip(s));
        do{
            s = this.createShip(3);
        }
        while(!this.placeShip(s));
        
        do{
            s = this.createShip(2);
        }
        while(!this.placeShip(s));
        
        do{
            s = this.createShip(2);
        }
        while(!this.placeShip(s));
        
        
        
        return true;
        
    }
    
    public boolean placeShip(Ship s){
        
        boolean isSuccess = true;
        
        if(this.shipsOnBoard > 5){
            return false;
        }
        
        // if V it mean vertically aligned and H it means horizontally aligned
        if(s.getOrientation() == 'V'){
            
            // Check ship is not going outside the bondary.
            if((s.getHeadY() + s.getLength()) > 10){
                return false;
            }
            // Check if overlapping is possible
            for(int i = 0; i < s.getLength(); i++){
                if(this.board[s.getHeadY() + i][s.getHeadX()] != 0){
                    return false;
                }
            }
            // Place the ship
            for(int i = 0; i < s.getLength(); i++){
                this.board[s.getHeadY() + i][s.getHeadX()] = 1;
            }
            
            this.shipsOnBoard++;
            
        }
        else if (s.getOrientation() == 'H'){
            
            // Check ship is not going outside the bondary.
            if((s.getHeadX() + s.getLength()) > 10){
                return false;
            }
            // Check if overlapping is possible
            for(int i = 0; i < s.getLength(); i++){
                if(this.board[s.getHeadY()][s.getHeadX() + i] != 0){
                    return false;
                }
            }
            // Place the ship
            for(int i = 0; i < s.getLength(); i++){
                this.board[s.getHeadY()][s.getHeadX() + i] = 1;
            }
            
            this.shipsOnBoard++;
        }
        this.ships[this.shipsOnBoard - 1] = s;
        
        setChanged();
        notifyObservers();
        return isSuccess;
        
    }
    
    public boolean attack(int x, int y){
        
        if(this.board[y][x] == 2 || this.board[y][x] == 3){
            return false;
        }
        for(int i = 0; i < 5; i++){
            if(this.ships[i].isHit(x, y)){
                if(this.ships[i].isSunk()){
                    this.shipsOnBoard--;
                    setChanged();
                    notifyObservers();
                }
                if(this.isGameOver()){
                    setChanged();
                    notifyObservers("gameover");
                }
                this.board[y][x] = 2;
                return true;
            }
        }
        this.board[y][x] = 3;
        return false;
    }
    
    public int getShipsOnBoard(){
        return this.shipsOnBoard;
    }
    public void resetBoard(){
        this.shipsOnBoard = 0;
        this.ships = new Ship[5];
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                this.board[i][j] = 0;
            }
        }
    }
    
    public boolean isGameOver(){
        
        boolean gameOver = true;
        for(int i = 0; i < 5; i++){
            if(!this.ships[i].isSunk()){
                return false;
            }
        }
            
        return gameOver;
    }
    
    public int[][] getBoard(){
        return this.board;
    }
    
}
