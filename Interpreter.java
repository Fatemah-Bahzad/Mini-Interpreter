
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/** Implements interpreter.
 * @author Fatemah Bahzad
 */

public class Interpreter {
	
	/** 
	* stack to use as a holder for values. 
 	* @param Phase1 stack to hold results 
 	*/

	Stack<Integer> phase1=new Stack<>();
	/** 
	* holds values and keys.
 	* @param map1 to get values and keys
 	*/
	HashMap<Integer, Integer> map1=new HashMap<>();

	/** 
	*read the given file of Instruction.
 	* @param filename file of Instructions
 	* @return link list of Instructions
 	* @throws IOException  for reading the file
 	*/
	public static LList<Instruction> readFile(String filename) throws IOException {
		LList<Instruction> answer=new LList<>(); //create a new llist for the Instruction
		Node<Instruction> temp ; //use to insert new nodes
		try {
			Scanner sc = new Scanner(new File(filename)); 
			while (sc.hasNextLine()) {
				Instruction str= new Instruction(sc.nextLine());//read each line as Instructions
				temp=new Node<>(str); //set a new node to insert 
				answer.insertLast(temp);
			}
			
		}
		catch(IOException e) {
	
		}
		return answer; //return the answer
	}
	/** 
	*apply the Instruction on phase1 stack.
 	* @param list of Instruction
 	*/
	public void evaluateInstructions(LList<Instruction> list) {
		int rslt=0;
		int rslt1=0; //used to store values from pop phase1
		Node<Instruction> l=list.getFirst(); //set the new node to the head of the list
		
		//Phase1
		while(l!=null) {
			String str=l.getValue().getOpcode(); //get the value from the node then get the opcode from the value
			//Phase1
			if (str.equals("iconst_0")) { //push 0  in the stack 
				phase1.push(0); 
			}
			else if (str.equals("iconst_1")) { //push 1  in the stack 
				phase1.push(1); 
			}
			else if (str.equals("iconst_2")) {  //push 2  in the stack 
				phase1.push(2); 
			}
			else if (str.equals("iconst_3")) {  //push 3  in the stack 
				phase1.push(3); 
			}
			else if (str.equals("iconst_4")) {  //push 4  in the stack 
				phase1.push(4); 
			}
			else if (str.equals("iconst_5")) {  //push 5  in the stack 
				phase1.push(5);  
			}
			//bipush
			else if (str.equals("bipush")) { //get the param and push it in the stack 
				phase1.push(l.getValue().getParam1());
			}
			//add 
			else if (str.equals("iadd")) { //check if any of them is null 
				rslt=phase1.pop();
				rslt1=phase1.pop();
				phase1.push(rslt1+rslt); //push the addition of both 
			}
			//sub
			else if (str.equals("imul")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();
				phase1.push(rslt1*rslt); //multiply they push the the answer
			}
			else if (str.equals("idiv")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();
				phase1.push(rslt1/rslt); //divide then push 
			}
			else if (str.equals("isub")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();
				phase1.push(rslt1-rslt);// sub then push 
			}
			else if (str.equals("irem")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();
				phase1.push(rslt1%rslt); //get the rem then push in the stack 
			}
			else if (str.equals("print")) {
				System.out.println(phase1.pop()); //pop from the stack then print
			}
			else if (str.equals("return")) {
				return;
			}
			//phase2
			else if (str.equals("iload_0")) { //push from key 0
				phase1.push(map1.get(0));
			}
			else if (str.equals("iload_1")) {//push from key 1
				phase1.push(map1.get(1));
			}
			else if (str.equals("iload_2")) {//push from key 2
				phase1.push(map1.get(2));
			}
			else if (str.equals("iload_3")) {//push from key 3
				phase1.push(map1.get(3));
			}
			else if (str.equals("iload")) {//push from key from param1
				phase1.push(map1.get(l.getValue().getParam1()));
			}
			else if (str.equals("istore_0")) { //pop from stack then store in key 0
				map1.put(0,phase1.pop());
			}
			else if (str.equals("istore_1")) {//pop from stack then store in key 1
				map1.put(1,phase1.pop());
			}
			else if (str.equals("istore_2")) {//pop from stack then store in key 2
				map1.put(2,phase1.pop());
			}
			else if (str.equals("istore_3")) {//pop from stack then store in key 3
				map1.put(3,phase1.pop());
			}
			else if (str.equals("istore")) {//pop from stack then store in key param1
				map1.put(l.getValue().getParam1(),phase1.pop());
			}
			else if (str.equals("iinc")) { //add to value
				int valuee=map1.get(l.getValue().getParam1()); //get the value at the index
				valuee+=l.getValue().getParam2(); //increment
				map1.put(l.getValue().getParam1(),valuee); //change the value 
			}
			else if (str.equals("goto")) { //jump to instructions
				int loc=l.getValue().getParam1();
				Node<Instruction> temp=list.getFirst(); //first node
				while(temp!=null) {
					if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
						break;
					}
					temp=temp.getNext(); //new node
				}
				l=temp;
				continue;
			}
			else if (str.equals("if_icmpeq")) {
				rslt=phase1.pop();
				rslt1=phase1.pop(); //get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning 
				if(rslt==rslt1) { 
					int loc=l.getValue().getParam1(); 
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			else if (str.equals("if_icmpne")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();//get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning 
				if(rslt!=rslt1) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			 
			else if (str.equals("if_icmpge")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();//get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning 
				if(rslt<=rslt1) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			else if (str.equals("if_icmpgt")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();//get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning
				if(rslt<rslt1) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			else if (str.equals("if_icmple")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();//get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning
				if(rslt>=rslt1) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			else if (str.equals("if_icmplt")) {
				rslt=phase1.pop();
				rslt1=phase1.pop();//get val1 and val2 
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning
				if(rslt>rslt1) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
					}
					l=temp;
					continue;
				}
			}
			else if (str.equals("ifne")) {
				rslt=phase1.pop();//get val1
				Node<Instruction> temp=list.getFirst();//loop over all the instruction from the beginning
				if(rslt!=0) {
					int loc=l.getValue().getParam1();
					while(temp!=null) {
						if (temp.getValue().getOffset()==loc) { //move to the next node until the offset is the one we want 
							break;
						}
						temp=temp.getNext();
							
					}
					l=temp;
					continue;
				}
			}
		 	l=l.getNext();//move to the next node
			 
		}
		
		
	}
	
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String[] args) {
	
		if(args.length != 1) {
			System.out.println("Usage: java Interpreter [filename]");
			System.exit(0);
		}
		
		try {
			LList<Instruction> input = readFile(args[0]);		
			new Interpreter().evaluateInstructions(input);		
		}
		catch(IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		

		

	}
}