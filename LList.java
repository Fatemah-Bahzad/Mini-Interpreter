/** generic linked list.
 * @param <T> hold any item to add to the LList
 * @author Fatemah Bahzad
 */

public class LList<T> { 
    /** head of the list.
	 * @param head of the list
	 */
   	private Node<T> head;
	/** tail of the list.
	 * @param tail of the list
	 */
	private Node<T> tail;

	/** 
	 * set an empty list.
	 */
	public LList(){
		this.head=null;
		this.tail=null;
		
	}
	
	/** 
	 * get the head.
	 * @return node of head
	 */
	public Node<T> getFirst() {
		if (this.head==null) {
			return null;
		}
		return this.head;
	}

	/** 
	 * insert value to head.
	 * @param value of head
	 */
	public void insertFirst(T value) {
		Node<T> temp = new Node<>(value);//create a new node and set value to it

		if (head==null) {//if it is empty 
			head=tail=temp;
		}
		else {
			temp.setNext(head);//let it point to head
			head=temp;
		}
		 

	}
	
	/** 
	 * insert a new node as head.
	 * @param newNode of head
	 */
	public void insertFirst(Node<T> newNode) {
		Node<T> temp = new Node<>(newNode.getValue()); //get the value of the node 
		if (head==null) {//if it is empty 
			head=tail=temp;
		}
		else {
			temp.setNext(head);//let it point to head
			head=temp;
		}
		

	}

	/** 
	 * insert a new node as tail.
	 * @param newNode of tail
	 */
	public void insertLast(Node<T> newNode) {		
		Node<T> temp = new Node<>(newNode.getValue()); //get the value of the node 
		if (tail==null) {//if it is empty 
			tail=head=temp;
		}
		else {
			tail.setNext(temp);//the next after tail to point at temp
			tail=temp;//set temp as the last item 
		}
		

	}

	/** 
	 * remove the tail.
	 * @return the removed tail
	 */
	public Node<T> removeFirst(){
		if (head==null) {
			return null;
		}
		Node<T> ans=head;
		head=head.getNext();
		return ans;
	}
	
	/**string representing of list.
	  * @return string of list
	  */
	public String listToString() {  
		String ans=""; //new string to add to return the answer 
		Node<T> current = head;
		
		while(current != null) {
			ans+=current.getValue()+" ";
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
		*class to test the code.
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
		Node<SomeType> n5 = new Node<>(new SomeType(500));
		
		LList<SomeType> list = new LList<>();
		list.insertFirst(item1);
		list.insertFirst(item2);
		list.insertFirst(item3);
		list.insertFirst(item4);		
		if (list.listToString().equals("400 300 200 100")) {
			System.out.println("Yay1");
		}

		list.insertLast(n5);	
		//System.out.println(list.listToString());
		if (list.listToString().equals("400 300 200 100 500")) {
			System.out.println("Yay2");
		}

		list.removeFirst();	
		if (list.listToString().equals("300 200 100 500")) {
			System.out.println("Yay3");
		}

		if (list.getFirst().getValue().toString().equals("300")) {
			System.out.println("Yay4");
		}
		
		list.insertFirst(new SomeType(600));	
		if (list.listToString().equals("600 300 200 100 500")) {
			System.out.println("Yay5");
		}		



	}
}