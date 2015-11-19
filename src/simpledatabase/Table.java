package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;
	String attL="";
	String typeL="";
	String tupleL="";

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		try {
			if(getAttribute==false){
				attL = br.readLine();
				typeL = br.readLine();
				getAttribute=true;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			tupleL = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(tupleL==null){
			return null;
		}
		tuple = new Tuple(attL,typeL,tupleL);
		tuple.setAttributeName();
		tuple.setAttributeType();
		tuple.setAttributeValue();
		return tuple;
		
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}