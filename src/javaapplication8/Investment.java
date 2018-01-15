/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication8;

/**
 * A super class for Investment 
 * @author MeghTrivedi
 */

public abstract class Investment {
    
   
    protected String type = ""; 
    protected String symbol = ""; 
    protected String name = ""; 
    protected int quantity = -1; 
    protected double price = -1; 
    protected double bookValue = -1; 
    protected double gain = 0;
    protected double payment = 0;
    
   public Investment(){
	   
   }
   	/**
   	 * @author MeghTrivedi
   	 * 
   	 * @param type Type of object
   	 * @param symbol Symbol of Object 
   	 * @param name Name of object
   	 * @param quantity Quantity of Object
   	 * @param price Price of Object
   	 * 
   	 * @throws Exception Throws exception if problem with input
   	 */
    public Investment(String type, String symbol, String name, String quantity, String price) throws Exception{
        this.type = type;
        if(symbol.equals("Enter the Symbol    ") || symbol.isEmpty())
        	throw new Exception("Symbol can not be blank or default text");
        if(name.equals("Enter the Name   ") || name.isEmpty())
        	throw new Exception("Name can not be blank or default text");
        this.symbol = symbol;
        this.name = name;
        if(quantity.length() == 0 || !quantity.matches("^[0-9]*") ){
        	throw new Exception("Quantity must be a number and not blank!");
        }
        this.quantity = Integer.parseInt(quantity);
    
        if(price.length() == 0 || !price.matches("\\d+(\\.\\d{1,2})?")){
        	throw new Exception("Price must be a number and not blank!");
        	
        }
        this.price = Double.parseDouble(price);  
        
    } 
    /**
     * 
     * @author MeghTrivedi
     * 
     * @return returns format for displaying objects
     */ 
    @Override
    public String toString(){
        
        String formatOfPrice = String.format("%.2f", this.price); 
        String formatOfBookValue = String.format("%.2f", this.bookValue); 
        
       
        System.out.println("\n|    symbol    |   "+this.symbol);
        System.out.println("|     name     |   "+this.name);
        System.out.println("|   quantity   |   "+this.quantity);
        System.out.println("|     price    |   "+formatOfPrice);
        System.out.println("|   bookValue  |   "+formatOfBookValue);
        System.out.println();  
        
        //System.out.println(this.symbol+ ","+this.name+","+this.quantity","+formatOfPrice+","+formatOfBookValue);
        
        return this.symbol+ " , " +this.name+ " , "+this.quantity+" , "+String.format("%.2f", this.price)+" , "+String.format("%.2f", this.bookValue);   
    }
//    public void print(){
//    
//        System.out.println("Symbol: "+ symbol);
//        System.out.println("Name: "+ name);
//        System.out.println("Quantity: "+ quantity);
//        System.out.println("Price: "+ price);
//        System.out.println("BookValue: "+ bookValue);
//    } 
    
    public abstract double getGain();    

    public abstract void sell(String price, String quantity) throws Exception;
    
    
    public abstract void updatePrice(String price) throws Exception; 
    
    public abstract void addQuantity(String newQuantity) throws Exception; 
    
    /**
     * 
     * @return returns the type of object
     */
    public String getType(){
        return type; 
    }
    
    /**
     * 
     * @return returns the symbol of object
     */
    public String getSymbol(){
        return symbol; 
    }
    
    /**
     * 
     * @return returns the name of object
     */
    public String getName()
    {
            return name;
    }
    
    /**
     * 
     * @return returns the quantity of object
     */
    public int getQuantity()
    {
            return quantity;
    }
    
    /**
     * @author MeghTrivedi
     * @return returns the price of object
     */
    public double getPrice()
    {
            return price;
    }
    
    /**
     * 
     * @return returns the bookValue of object
     */
    public double getBookValue()
    {
            return bookValue;
    }
    
    /**
     * 
     * @return returns the payment of object
     */
    public double getPayment()
    {
            return payment;
    }
    
    /**
     * 
     * @return returns the output to save to file
     */
    public String saveFile(){
    	String output = name + " " + symbol + " " + price + " " + quantity + " " + bookValue;
    	  	
    	return output;
    }
}
