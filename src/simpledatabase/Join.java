package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	private boolean first = true;
	private boolean end = false;
	private ArrayList<Attribute> temp= new ArrayList<Attribute>();
	private int cnt = -1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){

		
		if(first==true){
			Tuple lTuple;
			Tuple rTuple;
			int lPosition = 0;
			int rPosition = 0;
			ArrayList<Tuple> lTupleList = new ArrayList<Tuple>();
			ArrayList<Tuple> rTupleList = new ArrayList<Tuple>();
		
			while((lTuple=leftChild.next())!=null){
			
				lTupleList.add(lTuple);
			}
			while((rTuple=rightChild.next())!=null){
				rTupleList.add(rTuple);
			}
	
			if (lTupleList.size()==0 || rTupleList.size()==0){
				first = false;
				end = true;
			}
			
			else{
			for (int i=0; i<lTupleList.get(0).getAttributeList().size();i++){
				for (int j=0; j<rTupleList.get(0).getAttributeList().size();j++){
					if(lTupleList.get(0).getAttributeName(i).equals(rTupleList.get(0).getAttributeName(j))){
						lPosition=i;
						rPosition=j;
						break;
					}
					
				}	
			}
			for(int j =0;j<rTupleList.size();j++)
				for(int i =0;i<lTupleList.size();i++)
					if(rTupleList.get(j).getAttributeValue(rPosition).equals(lTupleList.get(i).getAttributeValue(lPosition))){
						temp.clear();
						temp.addAll(lTupleList.get(i).getAttributeList());
						temp.addAll(rTupleList.get(j).getAttributeList());
						temp.remove((lTupleList.get(i).getAttributeList().size()+(rPosition)));
						tuples1.add(new Tuple(new ArrayList<Attribute>(temp)));	
					}				

			first = false;	
			}
		}
		
		if(end==true){
			return null;
		}
		if(cnt!=tuples1.size()-1){
			newAttributeList.clear();
			cnt++;
			newAttributeList.addAll(tuples1.get(cnt).getAttributeList());
			return tuples1.get(cnt);
		}
		return null;
	}

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}