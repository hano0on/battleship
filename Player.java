/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Observable;

/**
 *
 * @author huzai
 */
public class Player extends Observable{
    
    private String name;
    private int tries;
    
    public Player(){
        this.name = "";
        this.tries = 0;
    }
    
    // Getters 
    public String getName(){
        return this.name;
    }
    
    public int getTries(){
        return this.tries;
    }
    
    
    // Setters
    public void setName(String name){
        this.name = name;
        
        setChanged();
        // Notify Observers if name set
        notifyObservers();
    }
    
    public void setTries(int tries){
        this.tries = tries;
        setChanged();
        // Notify Observers if tries set
        notifyObservers();
    }
    
}
