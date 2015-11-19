package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	private boolean isSorted=false;
	private int cnt = -1;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){

		if (isSorted==false){
			
			while((child.next())!=null){
				tuplesResult.add(new Tuple(new ArrayList<Attribute>(getAttributeList())));
			}
			
			Collections.sort(tuplesResult,new Comparator<Tuple>(){
				@Override
				public int compare(Tuple a, Tuple b) {
					for(int i =0;i<tuplesResult.get(0).getAttributeList().size();i++){
						if(a.getAttributeName(i).equals(orderPredicate) && b.getAttributeName(i).equals(orderPredicate)){
							
							switch(a.getAttributeType(i).type){

							case INTEGER:
								return ((Integer)a.getAttributeValue(i)).compareTo((Integer)b.getAttributeValue(i));
							case DOUBLE:
								return ((Double)a.getAttributeValue(i)).compareTo((Double)b.getAttributeValue(i));
							case LONG:
								return ((Long)a.getAttributeValue(i)).compareTo((Long)b.getAttributeValue(i));
							case SHORT:
								return ((Short)a.getAttributeValue(i)).compareTo((Short)b.getAttributeValue(i));
							case FLOAT:
								return ((Float)a.getAttributeValue(i)).compareTo((Float)b.getAttributeValue(i));
							case STRING:
								return ((String)a.getAttributeValue(i)).compareTo((String)b.getAttributeValue(i));
							case BOOLEAN:
								return ((Boolean)a.getAttributeValue(i)).compareTo((Boolean)b.getAttributeValue(i));
							case CHAR:
								return ((Character)a.getAttributeValue(i)).compareTo((Character)b.getAttributeValue(i));
							case BYTE:
								return ((Byte)a.getAttributeValue(i)).compareTo((Byte)b.getAttributeValue(i));
						}		
					}
					}
					return 0;
					
				}
			});
			
		isSorted=true;
		}
		if(cnt!=tuplesResult.size()-1){
			newAttributeList.clear();
			cnt++;
			newAttributeList.addAll(tuplesResult.get(cnt).getAttributeList());
			getAttributeList().clear();
			getAttributeList().addAll(newAttributeList);
			return tuplesResult.get(cnt);
		}
		return null;
		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}