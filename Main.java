/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Model.*;
import View.*;
import Controller.*;
/**
 *
 * @author huzai
 */
public class Main {
    public static void main(String[] args){
        // Models
        Player player = new Player();
        Board  board = new Board();
        
        // Controller 
        GameController controller = new GameController(board, player);
        
        // Views
        GUIView  view = new GUIView(controller);
        
        view.showMainMenu();
        player.addObserver(view);
        board.addObserver(view);
        
    
    }
}
