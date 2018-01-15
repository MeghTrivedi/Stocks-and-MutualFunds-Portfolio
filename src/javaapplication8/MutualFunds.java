/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

/**
 *A subclass of investment
 * @author MeghTrivedi
 */

public class MutualFunds extends Investment{

	
	public MutualFunds(){
	   super();
	}
  	/**
   	 * @author MeghTrivedi
   	 * 
   	 * 
   	 * @param symbol Symbol of Object 
   	 * @param name Name of object
   	 * @param quantity Quantity of Object
   	 * @param price Price of Object
   	 * 
   	 * @throws Exception Throws exception if problem with input
   	 */    
    public MutualFunds(String symbol, String name, String quantity, String price) throws Exception 
    { 
        super("mutual funds", symbol, name, quantity, price);
        buy(Double.parseDouble(price), Integer.parseInt(quantity));
        //sellMF(this.price, this.quantity);
        //getGainMF(this.price);

    }
    
	/**
   	 * @author MeghTrivedi
   	 * 
   	 * @param symbol Symbol of Object 
   	 * @param name Name of object
   	 * @param quantity Quantity of Object
   	 * @param price Price of Object
   	 * @param bookValue bookValue of Object
   	 * @throws Exception Throws exception if problem with input
   	 */
    public MutualFunds(String symbol, String name, String quantity, String price, String bookValue) throws Exception 
    { 
    	super("mutual funds", symbol, name, quantity, price); 
        buy(Double.parseDouble(price), Integer.parseInt(quantity));
    }
   
    /**
    * 
    * @param newPrice gets new Price
    * @param quantity gets new quantity
    */
    
    public void buy(double newPrice, int quantity){
        
        quantity = quantity * super.quantity; 
        super.price = newPrice; 
        
        super.bookValue = (quantity * newPrice) + 45; 
        
    }
    
    /**
     * @return returns the Gain 
     */
    public double getGain(){
        double gainMF = 0.0;
        
        gain = gainMF; 
        gainMF = ((quantity * price) - 45.00) - bookValue; 
        
        return gainMF; 
    }

    /**
     * @author MeghTrivedi
     * @param newPrice price to sell
     * @param sellQuantity quantity to sell       
     * 
     * @throws Exception Throws exception if problem with input 
     */
    public void sell(String newPrice, String sellQuantity) throws Exception{
        
        double total = quantity; 
        if(sellQuantity.length() == 0 || !sellQuantity.matches("^[0-9]*") ){
        	throw new Exception("Quantity must be a number and not blank!");
        }
        
        int convertQuantity = Integer.parseInt(sellQuantity); 
        
        if(newPrice.length() == 0 || !newPrice.matches("\\d+(\\.\\d{1,2})?")){
        	throw new Exception("Price must be a number and not blank!");        	
        }
		double convertPrice = Double.parseDouble(newPrice); 
		
        if(convertQuantity > total){
        	throw new Exception("Cannot sell that many");
        }
        price = convertPrice; 
        quantity = quantity - convertQuantity;
        payment = convertQuantity * convertPrice - 45.00;
        bookValue = (double)bookValue * ((double)quantity / (double)total);                      
    }

    /**
     * @param newPrice update price 
     * 
     * @throws Exception Throws exception for incorrect input 
     */
    public void updatePrice(String newPrice) throws Exception{
       	
    	if(newPrice.length() == 0 || !newPrice.matches("\\d+(\\.\\d{1,2})?")){
        	throw new Exception("Price must be a number and not blank!");        	
        }
    	
    	double convertPrice = Double.parseDouble(newPrice); 
    	
    	this.price = convertPrice;  
    	 
        
        gain = (quantity * price - 45.00) - bookValue; 

    }
    /**
     * 
     * @param newQuantity adding more quantity
     * 
     * @throws Exception Throws exception for incorrect input 
     */
    public void addQuantity(String newQuantity) throws Exception{
    	
    	if(newQuantity.length() == 0 || !newQuantity.matches("^[0-9]*") ){
        	throw new Exception("Quantity must be a number and not blank!");
        }
    	int convertQuantity = Integer.parseInt(newQuantity);
    	if(convertQuantity < 0){
    		throw new Exception("Quantity must be greater than 0!");
    	}
    	this.quantity += convertQuantity;  
    }
    
    /**
     * @return super.saveFile returns type of investment
     */
    public String saveFile(){
    	return super.saveFile() + " mf \n";
    }
    
   
}
    

