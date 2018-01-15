/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

/**
 *
 * @author MeghTrivedi
 */

public class Portfolio {

	/**
	 * 
	 * @param args for command line arguments
	 */
   	public static void main(String[] args) {
   		
		GUI gui;
		if(args.length == 0){
			gui = new GUI();
		}else{
			gui = new GUI(args[0]);
		}
		gui.runGUI("");
	}
   

 }   