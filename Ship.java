/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author huzai
 */
public class Ship {
    
    private int headX;              // ship's starting position's x-cordinate
    private int headY;              // ship's starting position's y-cordinate
    private int length;             // length of the ship.
    private String title;           // title is optional you can name the ship.
    private char orientation;       // orientation of the ship H for horizontal and V for vertical
    private int health;             // health for determining is ship sunk or not.
    
    
    public Ship(){
    
    }
    
    public Ship(int x, int y, int length, char ort){
        this.headX = x;
        this.headY = y;
        if( length >=2 && length <=5){
            this.length = length;
            this.health = length;
        }
        else{
            this.length = 0;
            this.health = 0;
        }
        this.orientation = ort;
    }
    
    public void setHeadX(int x){
        this.headX = x;
    }
    
    public int getHeadX(){
        return this.headX;
    }
    
    public void setHeadY(int y){
        this.headY = y;
    }
    
    public int getHeadY(){
        return this.headY;
    }
    
    // Length can't be less than 2 and greater than 5
    // Whenever Ship's len
    public void setLength(int len){
        
        if( len >= 2 && len <=5){
            this.length = len;
        }
        else{
            this.length = 0;
        }
            
    }
    
    public int getLength(){
        return this.length;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setOrientation(char ort){
        this.orientation = ort;
    }
    
    public char getOrientation(){
        return this.orientation;
    }
    
    // health can't be greater than length
    public void setHealth(int health){
        if(health <= this.length){
            this.health = health;
        }
        else{
            this.health = 0;
        }
    }
    
    public int getHealth(){
        return this.health;
    }
    
    // isSunk function checks the ships health. If health is zero, it means ship is sunk.
    // it return true if ship is sunk else it return false.
    public boolean isSunk(){
        return this.health == 0;
    }
    
    public boolean isHit(int x, int y){
        if(this.orientation == 'H'){
            if(this.headY == y){
                if(x >= this.headX && x < (this.headX + this.length)){
                    this.health--;
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            if(this.headX == x){
                
                if(y >= this.headY && y < (this.headY + this.length)){
                    this.health--;
                    return true;
                }
                else{
                    return false;
                }
            
            }
            else{
                return false;
            }
        }
    }
    
}
