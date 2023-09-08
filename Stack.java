/** generic stack using link list and node.
 * @param <T> hold any item to add to the stack
 * @author Fatemah Bahzad
 */
public class Stack<T> {  
	/** generic stack using link list and node.
 	* @param elements a link list to hold the elements 
 	*/
     
	private LList<T> elements;
	/** size of the elements in the stack.
 	* @param size keep track of what is in the llist
 	*/
	private int size;
	/**
	  * create an empty stack.
	  */
	public Stack() {
		this.elements= new LList<T>();
	}
	/**
	  * insert element at the beginning of the list.
	  * @param e value to insert
	  * @return 
	  */
	public void push(T e) {
		elements.insertFirst(e); 
		size++; //count the elements added 
		
	}
	/**
	  * remove element at the beginning of the list.
	  * @return removed element
	  */
	public T pop() {
		if (size==0) { //if it is an empty stack 
			return null;
		}
		size--;
		return elements.removeFirst().getValue(); //remove it and get its value
	}
	/**
	  * return element at the beginning.
	  * @return element at beginning of the list
	  */
	public T peek() {
		if (size==0) { //if it is an empty stack 
			return null;
		}
		return elements.getFirst().getValue();
	}

	/**
	  * checks if the stack is empty or not.
	  * @return true if the stack is empty, false if it is not
	  */
	public boolean isEmpty() {	
		if (size==0) {
			return true;
		}
		return false;
	}

	/**
	  * get the size of the stack. 
	  * @return size of the stack 
	  */
	public int getSize() {
		return size;
	}
	/**
	  * starting from the top, get a string representing the values in the stack.
	  * @return string representing stack values
	  */

	public String toString() { 
		String ans=""; //new string to add to return the answer 
		Node<T> current = elements.getFirst(); //get first node
		
		while(current != null) { //loop over all the nodes 
			ans+=current.getValue()+" "; //add it to the string 
			current=current.getNext(); //move the next value
		}
		return ans.trim(); //remove additional space from the end and begging 
	}

	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	
	public static void main(String[] args) {
		/**
	*class to test and debug the code.
	*/
		class SomeType {
			private int value;

			public SomeType(int value) { this.value = value; }
			public String toString() { return "" + value; }
			/**
	*check if two objects are equal.
	* @param o Object to compare
	* @return true if they are equal, false if there are not
	*/
			public boolean equals(Object o) {
				if (!(o instanceof SomeType)) return false;
				return ((SomeType)o).value == value;
			}	
		}
		
		SomeType item1 = new SomeType(100);
		SomeType item2 = new SomeType(200);
		SomeType item3 = new SomeType(300);
		SomeType item4 = new SomeType(400);
		
		Stack<SomeType> s = new Stack<>();
		s.push(item1);
		s.push(item2);

		if (s.getSize() == 2) {
			System.out.println("Yay1");
		}

		if (s.peek().toString().equals("200")) {
			System.out.println("Yay2");
		}
		if (s.pop().toString().equals("200")) {
			System.out.println("Yay3");
		}
	
		s.push(item3);
		s.push(item4);
		if (s.toString().equals("400 300 100")) {
			System.out.println("Yay4");
		}
		
		s.pop();
		s.pop();
		if (s.toString().equals("100")) {
			System.out.println("Yay5");
		}

		s.pop();
		if (s.isEmpty()) {
			System.out.println("Yay6");
		}

	}
}