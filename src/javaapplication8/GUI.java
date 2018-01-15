package javaapplication8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.HashMap;
/**
 * A class for user interface 
 * @author MeghTrivedi
 *
 */
public class GUI {
	
	static ArrayList<Investment> storage = new ArrayList<Investment>();
	static HashMap<String,ArrayList<Integer>> index = new HashMap<String, ArrayList<Integer>>();
	int counter = 0; 
	static String fileName;
	
	/**
	 * 
	 * @param filePath path to input file
	 */
	public GUI(String filePath){
		fileName = filePath;
		try {
			loadFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GUI(){
		
	}
	/**
	 * 
	 * @param inputFile input file 
	 */
	void runGUI(String inputFile){
		
		JFrame window = new JFrame("Stock and Funds");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    /*Panel TextFields*/
	    JTextField symbol, name, quantity, price, lowPrice, highPrice, symbolUpdate, nameUpdate, totalGains; 
	    symbol = new JTextField("Enter the Symbol    ");
	    symbol.addFocusListener(focusListen(symbol, symbol.getText())); 
	    name = new JTextField("Enter the Name   "); 
	    name.addFocusListener(focusListen(name, name.getText()));	
	    quantity = new JTextField("Enter the Quantity   "); 
	    quantity.addFocusListener(focusListen(quantity, quantity.getText()));	    
	    price = new JTextField("Enter the Price:    "); 
	    price.addFocusListener(focusListen(price, price.getText()));
	    lowPrice = new JTextField("Enter Low Price:     ");
	    lowPrice.addFocusListener(focusListen(lowPrice, lowPrice.getText()));
	    highPrice = new JTextField("Enter High Price:     ");
	    highPrice.addFocusListener(focusListen(highPrice, highPrice.getText()));
	    symbolUpdate = new JTextField("Symbol");
	    symbolUpdate.setEditable(false); 
	    nameUpdate = new JTextField("Name"); 
	    nameUpdate.setEditable(false);
	    totalGains = new JTextField(); 
	    totalGains.setEditable(false);
	    
	    /*Labels*/
	    JLabel getGainL, welcomeMess; 
	    getGainL = new JLabel("Total Gains"); 
	    welcomeMess = new JLabel("WELCOME TO YOUR INVESTMENT PORTFOLIO");
	    
	    
	    /*Text Area*/
	    JTextArea messages = new JTextArea();
	    messages.setAutoscrolls(true);
	    JScrollPane scroll = new JScrollPane(messages);
	    scroll.setHorizontalScrollBarPolicy(32);
	    scroll.setVerticalScrollBarPolicy(22);
	    messages.setEditable(false);
	    

	    
	    //buyPanel.set
	    String [] items = {"Stocks", "MutualFunds"}; 
	    JComboBox<String> comboBox = new JComboBox<>(items); 
		
		/*Buttons*/
		JButton resetB, searchB, buyB, sellB, saveB, prevB, nextB; 
		resetB = new JButton("Reset");

		resetB.addActionListener(new ActionListener() {
			
			@Override

			public void actionPerformed(ActionEvent e) {
				symbol.setText("Enter the Symbol    ");
				name.setText("Enter the Name   ");
				price.setText("Enter the Price:    ");
				quantity.setText("Enter the Quantity   ");
				lowPrice.setText("Enter Low Price:     ");
				highPrice.setText("Enter High Price:     ");
			}
		});
		
		buyB = new JButton("Buy");
		buyB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String tSymbol, tName, tPrice, tQuant;
					tSymbol = symbol.getText();
					tName = name.getText(); 
					tPrice = price.getText(); 
					tQuant = quantity.getText(); 
					Investment newItem;
					Investment check = checkSymbol(tSymbol);
					if(check == null){
					
						if(comboBox.getSelectedIndex() == 0){
							newItem = new Stocks(tSymbol, tName, tQuant, tPrice);
						}else{
							newItem = new MutualFunds(tSymbol, tName, tQuant, tPrice);
						}
						
						storage.add(newItem);
						addHash(newItem.getName());
						
						messages.append(tQuant + " of " + tSymbol+ " was successfully bought at $" + tPrice + " a share\n");
					}else{
						//messages.append("This already Exists!");
						check.addQuantity(tQuant);
						check.updatePrice(tPrice);
						messages.append("Quantity and Price Updated\n"); 
					}
				}catch (Exception f){
					if(f.getMessage().equals("SymbolAlreadyExist")){
						messages.append("This works\n");
					}
					else
						messages.append("Error: " + f.getMessage() + "\n");
				}
				
			}
		});
		
		sellB = new JButton("Sell");
		sellB.addActionListener(new ActionListener(){
				
			@Override 
			public void actionPerformed(ActionEvent e){
				
				String tSymbol, tPrice, tQuantity; 
				tSymbol = symbol.getText(); 
				tPrice = price.getText(); 
				tQuantity = quantity.getText(); 
				int i= 0; 
				try {
					
					while(!storage.get(i).getSymbol().equals(tSymbol) && i < storage.size()){
					
						i++; 
					}
					Investment find = storage.get(i); 
					
					if(find.getSymbol().equals(tSymbol)){

						find.sell(tPrice, tQuantity);	
						if(find.getQuantity() == 0){
							storage.remove(i);
							updateHash(); 
							
						}
					}
					else{
						messages.append("Symbol not found");
					}
				}catch(Exception f){
					messages.append(f.getMessage()+"\n");
				}
				
				
				
				
			}
		});
		
		saveB = new JButton("Save"); 
		saveB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
			
				Investment item = storage.get(counter); 
				try {
					item.updatePrice(price.getText());
					messages.append("Price Updated.");
				} catch (Exception e1) {
					messages.append("Incorrect input.");
				} 
				
			}
		});
		
		prevB = new JButton("Prev"); 
		prevB.addActionListener(new ActionListener(){
			
			@Override 
			public void actionPerformed(ActionEvent e){
				if (counter > 0){
					counter--; 
					Investment item = storage.get(counter); 
					symbolUpdate.setText(item.getSymbol());
					nameUpdate.setText(item.getName());
					price.setText(""+item.getPrice()); 
				}
			}
		});
		
		nextB = new JButton("Next");
		nextB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				if(counter < storage.size() -1){
					counter++;
					Investment item = storage.get(counter);
					symbolUpdate.setText(item.getSymbol());
					nameUpdate.setText(item.getName());
					price.setText(""+item.getPrice()); 

				}

			}
		});
		
		searchB = new JButton("Search"); 
		searchB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Integer> results = search(name.getText(), lowPrice.getText(), highPrice.getText(), symbol.getText());
					
					for(int i = 0; i < results.size(); i++){
						messages.append(storage.get(results.get(i)).toString() + "\n");
					}
					
				} catch (Exception e1) {
					messages.append(e1.getMessage() + "\n");
				}
				
			}
		});

		
		
		/*Panels*/
		JPanel mainPanel = new JPanel(), buyPanel, sellPanel, updatePanel, getGainPanel, searchPanel, containerPanel, componentPanel;
		mainPanel.setLayout(new BorderLayout());
		containerPanel = new JPanel(new GridLayout(2, 1, 0 ,0));
		componentPanel = new JPanel(new GridLayout(1,1,0,0));
		buyPanel = new JPanel(); 
		sellPanel = new JPanel(); 
		updatePanel = new JPanel(); 
		getGainPanel = new JPanel(); 
		searchPanel = new JPanel(); 
				
		/*Menu*/
		JMenuBar menuBar;
		JMenu menu;
	    JMenuItem buy, sell, update, getGain, search, exit;  	    
	    

	    
	    /*Panel Layouts*/
	    buyPanel.setLayout(new GridLayout(7,1,0,0));
	    sellPanel.setLayout(new GridLayout(7,1,0,0));
	    updatePanel.setLayout(new GridLayout(7,1,0,0));
	    getGainPanel.setLayout(new GridLayout(7,1,0,0));
	    searchPanel.setLayout(new GridLayout(7,1,0,0));
	    
	   
	    
    
	    //comboBox.setPreferredSize(new Dimension(600, 600));
	    
	    containerPanel.add(componentPanel);
	    containerPanel.add(scroll); 
	    mainPanel.add(containerPanel);
	    
	    componentPanel.add(welcomeMess);
	    welcomeMess.setHorizontalAlignment(JLabel.CENTER);


	    
		window.add(mainPanel);
		menuBar = new JMenuBar();
		menu = new JMenu("Commands");
		
		buy = new JMenuItem("Buy");
		buy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			    buyPanel.add(comboBox);
			    buyPanel.add(symbol);
			    buyPanel.add(name);
			    buyPanel.add(quantity);
			    buyPanel.add(price);
			    buyPanel.add(buyB);
			    buyPanel.add(resetB);
			    
				componentPanel.removeAll();
				componentPanel.add(buyPanel);
				componentPanel.revalidate();
				componentPanel.repaint();
			}
		});
		
		sell = new JMenuItem("Sell");
		sell.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			    sellPanel.add(symbol);
			    sellPanel.add(quantity);
			    sellPanel.add(price);
			    sellPanel.add(sellB);
			    sellPanel.add(resetB);

			    
				componentPanel.removeAll(); 
				componentPanel.add(sellPanel); 
				componentPanel.revalidate();  
				componentPanel.repaint(); 
				
			}
		});
		
		update = new JMenuItem("Update");
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				

				updatePanel.add(symbolUpdate);
				updatePanel.add(nameUpdate);
				updatePanel.add(price);
				updatePanel.add(saveB);
				updatePanel.add(prevB);
				updatePanel.add(nextB);

				componentPanel.removeAll(); 
				componentPanel.add(updatePanel);
				componentPanel.revalidate();
				componentPanel.repaint(); 
				
			}
		
		});

		
		getGain = new JMenuItem("Get Gain");
		getGain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double total = 0; 
				getGainPanel.add(getGainL);
				getGainPanel.add(totalGains);
				

				for (int i = 0; i < storage.size(); i++){
					messages.append(storage.get(i).getSymbol()+ " "  + storage.get(i).getGain());
					messages.append("\n"); 
					total += storage.get(i).getGain();
				}
				
				totalGains.setText("" +total);
				
				componentPanel.removeAll(); 
				componentPanel.add(getGainPanel); 
				componentPanel.revalidate(); 
				componentPanel.repaint(); 

				
			}
		});
		
		search = new JMenuItem("Search");
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				searchPanel.add(symbol);
				searchPanel.add(name);
				searchPanel.add(lowPrice);
				searchPanel.add(highPrice);
				searchPanel.add(searchB);
				searchPanel.add(resetB);
 
				componentPanel.removeAll();
				componentPanel.add(searchPanel);
				componentPanel.revalidate();
				componentPanel.repaint(); 
			}
		});
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					savetoFile();
				}catch(Exception f){
					
				}
				System.exit(0);
			}
		});
		
		menu.add(buy);
		menu.add(sell); 
		menu.add(update);
		menu.add(getGain);
		menu.add(search);
		menu.add(exit);
		menuBar.add(menu);
	    mainPanel.add(menuBar, BorderLayout.PAGE_START);

		window.setPreferredSize(new Dimension(600, 700));
		window.pack();
		window.setVisible(true);
		
	}
    /*Add Hash Function*/
    public static void addHash(String string){
    	
    	String temp[]; 
    	temp = string.split(" "); 

          for(int x = 0; x<temp.length; x++){

              if(index.containsKey(temp[x].toLowerCase())){

                  index.get(temp[x].toLowerCase()).add(storage.size() - 1);

              }else{

                  ArrayList<Integer> newList = new ArrayList<Integer>();
                  newList.add(storage.size() - 1); 
                  index.put(temp[x].toLowerCase(), newList); 

              }

          }

    }
    public ArrayList<Integer> search(String searchTerm, String lowPrice, String highPrice, String Symbol) throws Exception{

    	double tLow, tHigh;
    	if((!lowPrice.matches("^[0-9]*") && !lowPrice.matches("Enter Low Price:     "))){
    		throw new Exception("Price must be a number!"); 		
    	}
    	if(!highPrice.matches("^[0-9]*") && !highPrice.matches("Enter High Price:     ")){
    		throw new Exception("Price must be a number!");
    	}

    	if(lowPrice.equals("Enter Low Price:     ")){
    		tLow = 0.00;
    	} else{
    		tLow = Double.parseDouble(lowPrice);
    	}
    	if(highPrice.equals("Enter High Price:     ")){
    		tHigh = 9007199254740990.00;
    	}else{
    		tHigh = Double.parseDouble(highPrice);
    	}

	    ArrayList <Integer> result = new ArrayList<Integer>();
    	if(!searchTerm.equals("Enter the Name   ") && !searchTerm.trim().isEmpty()){
		   	String temp[];
		   	temp = searchTerm.toLowerCase().split(" ");
		   	ArrayList<ArrayList<Integer>> tempList = new ArrayList<ArrayList<Integer>>(); 
		   	
		   	for(int i = 0; i < temp.length; i++){
		   		tempList.add(index.get(temp[i]));
		   	}
		   	if(tempList.size() == 0){
		   		throw new Exception ("No results found");
		   	}
		   	result = tempList.get(0);
		   	
		   	if (tempList.size() > 1){
		   		
		   		for(int x = 1; x < tempList.size(); x++){
		   			result.retainAll(tempList.get(x)); 
		   		}
		   	}
		   	if(tempList.size() == 0){
		   		throw new Exception("No results found");
		   	}
    	} else{
    		for(int i = 0; i < storage.size(); i++){
    			result.add(i);
    		}
    	}
    
	   	if(!Symbol.equals("Enter the Symbol    ")){
	   		ArrayList<Integer> tArray = new ArrayList<Integer>();
		   	for(int i = 0; i < result.size(); i++){
		   		if(storage.get(result.get(i)).getSymbol().equals(Symbol)) 
		   			tArray.add(result.get(i));
		   	}
		   	result.retainAll(tArray);
	   	}

	   	

	   		ArrayList<Integer> tArray = new ArrayList<Integer>();
	   		
	   		if(tHigh <= 0 || tHigh <= 0){
	   			throw new Exception("Price must be greater than 0");
	   		}
	   		System.out.println(result.size() + " " + tLow + " " + tHigh);
	   		for(int i = 0; i < result.size(); i++){
	   			if((tLow <= storage.get(result.get(i)).getPrice()) && (storage.get(result.get(i)).getPrice() <= tHigh) ){
	   				System.out.println(storage.get(result.get(i)).getPrice());
		   			tArray.add(result.get(i));
		   			System.out.println("item added");
	   			}
	   			
	   		}
	   		result.retainAll(tArray);
	   	
	   	return result;
    } 


	private static FocusListener focusListen(JTextField field, String text){
		
		FocusListener listener = new FocusListener(){

			@Override
			public void focusLost(FocusEvent e) {
				if(field.getText().isEmpty()){
					field.setText(text);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if(field.getText().equals(text)){
					field.setText("");
				}
				
			}
			
		};
		return listener;
		
	}
	
	/**
	 * 
	 * @throws Exception Throws exception if file input is incorrect 
	 */
	private static void loadFile() throws Exception{

		try {
			BufferedReader fileRead = new BufferedReader(new FileReader(fileName));
			String name, symbol, price, quantity, bookValue, type; 
			name = symbol = price = quantity = bookValue = type = "";
			while(fileRead.ready()){
				String line;
				line = fileRead.readLine();
				String object[] = line.split(" ");
				System.out.println(object[1]);
				
				name = object[0]; 
				symbol = object[1]; 
				price = object[2]; 
				quantity = object[3];
				bookValue = object[4];
				
				Investment input;
				
				if (type.equals("s")){					
					input = new Stocks(symbol, name, quantity, price, bookValue); 
				}else{
					input = new MutualFunds(symbol, name, quantity, price, bookValue); 
				}
				storage.add(input);
				addHash(input.getName()); 
			}
						
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 
	 * @throws Exception Throws exception if saving to file is incorrect
	 */
	private static void savetoFile() throws Exception {

		try{
			PrintWriter fileOut = new PrintWriter(fileName);
			File file;
			
			file = new File(fileName);
			
			if(!file.exists()){
				file.createNewFile();
			}
			
			for(int i = 0; i < storage.size(); i++){
				fileOut.write(storage.get(i).saveFile());
			}
			fileOut.write("\n");
			fileOut.close();
		}
		catch(Exception e){
			throw new Exception("Error when writing to file");
		}
	}

	private void updateHash(){
		index.clear();
		for(int i = 0; i < storage.size(); i++){
			addHash(storage.get(i).getName());
		}
		
	}
	
	/**
	 * 
	 * @param checkSymbol
	 * @return returns symbol from object 
	 * @return returns null 
	 */
	private Investment checkSymbol(String checkSymbol) {
		
		for(int i = 0; i < storage.size(); i++){
			if(storage.get(i).getSymbol().equals(checkSymbol)){
				 return storage.get(i);
			}
		}
		return null;
	}
	
}
