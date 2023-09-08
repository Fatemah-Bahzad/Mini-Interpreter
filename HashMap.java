/** implements a hash map.
 * @param <K> hold the generic key in map
 * @param <V> hold the generic value in map
 * @author Fatemah Bahzad
 */
public class HashMap<K, V> 
{
	/** combine the two generic parameters to be used in LList and Node.
 	* @param <K> hold the generic key in map
 	* @param <V> hold the generic value in map
 	*/
	
	class Pair<K,V> {
		/** the generic key in map.
	 	* @param key the generic key in map
	 	*/
		private K key;
		/** the generic value in map.
	 	* @param value the generic value in map
	 	*/
		private V value;
		/** set the key and value in pair as the given values.
	 	* @param key the generic key in map
	 	* @param value the generic value in map
	 	*/
		public Pair(K key, V value){
			this.key = key;
			this.value = value;
		}
		/** returning the key in a pair.
	 	* @return key in a pair
	 	*/
		public K getKey(){ return key; }
		/** returning the value in a pair.
	 	* @return value in a pair
	 	*/
		public V getValue(){ return value; }
		/** set key.
	 	* @param key the new value of key in pair 
	 	*/
		public void setKey(K key){ this.key = key; }
		/** set value.
	 	* @param value the new value of key in pair 
	 	*/
		public void setValue(V value){ this.value = value; }
		/** get the hashCode.
	 	* @return value of the hashCode
	 	*/
		@Override public int hashCode() {  
			return key.hashCode(); 
		}
		/**check if two objects are equal.
		* @param obj Object to compare
		* @return true if they are equal, false if there are not
		*/
		@Override public boolean equals(Object obj) {  
			if (obj == null) return false;
			if (!(obj instanceof Pair)) return false;
			Pair pair = (Pair)obj;
			return pair.key.equals(key); 
		}
	}

    /** array of link list to hold node of pair.
 	* @param buckets array to hold link list of pair
 	*/
    private LList<Pair>[] buckets;

    /**fixed capacity.
 	* @param DEFAULT_CAPACITY of 20 buckets 
 	*/
    final static private int DEFAULT_CAPACITY = 20;

    /**number if elements in hashmap.
 	* @param size of hashmap
 	*/
    private int size = 0;
    /** 
     * set the DEFAULT_CAPACITY.
 	*/
    public HashMap() {
        this(DEFAULT_CAPACITY);
    }
    /** 
     * set buckets to capacity of LList.
     * @param capacity of buckets
 	*/
    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
    	buckets = (LList<Pair>[])new LList[capacity];
    }
    /** 
     * size of buckets.
     * @return  size of buckets
 	*/
    public int size() {
        return size;
    }
    /** 
     * Capacity of buckets.
     * @return  Capacity of buckets
 	*/
    private int getCapacity() {
        return buckets.length;
    }
    /** 
     * hashcode of key.
     * @param key to get the hashcode
     *@return hashcode of key
 	*/
    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }
    /** 
     *string representing of buckets.
     * @return  string representing
 	*/
    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (LList<Pair> list : buckets) {	
			sb.append("[");	
			if (list != null) {
				sb.append(list.listToString());
			}
			sb.append(", ");	  
			sb.append("]");
		}
		return "{" + sb.toString() + "}";
	}

    /** 
     *put the key and value in there right position in the map.
     * @param key to insert in the map
     * @param value to insert with the key
 	*/
    @SuppressWarnings("unchecked")
   public void put(K key, V value) {
    	Pair<K,V> l= new Pair<>(key,value); //create a pair with the new value and key
    	Node<Pair> n=new Node<>(l); //new node with the new pair
    	int index= key.hashCode()%DEFAULT_CAPACITY; //get the index for the new key
    	if ( buckets[index]==null) {//if there is nothing just add a new node with the new values 
    		buckets[index]=new LList<>(); //insert an empty linked list
    		buckets[index].insertFirst(n); //insert the node to head 
    		size++; //add to the size
    	}
    	
    	else {
    		Node<Pair> current=buckets[index].getFirst(); //the first node of the link list 
    		while(current!=null) { //stop at the end 
    			if (current.getValue().getKey().equals(key)) { //if they have the same key 
    				current.getValue().setValue(value); //change the value 
    			}
    			else {
    				buckets[index].insertLast(n); //if there are not the same just insert it to the end of the link list
    				size++; //add to the size
    			}
    			current=current.getNext(); //move to the next node
    			return;
    		}	
    	}
    }
   

	/** 
     *get the value at key.
     * @param key get the value with it 
     * @return value at key 
 	*/
	@SuppressWarnings("unchecked")
	public V get(K key) {
		int index= key.hashCode()%DEFAULT_CAPACITY; // get the index of the link list 
		if (buckets[index]==null) { //if it is empty 
			return null;
		}
		Node<Pair> current=buckets[index].getFirst();//the first node of the link list 
		while(current!=null) { //loop until the end
			if(current.getValue().getKey().equals(key)) { //if it is the same key 
				return (V) current.getValue().getValue(); //return the value
			}
			current=current.getNext(); //move to the next node
		}
		return null;
    	
	}
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String args[]) {
		HashMap<Integer, String> map = new HashMap<>();
		for (int i = 0; i < 100000; i++) {
			map.put(i, "Val" + i);
		}

		if (map.size() == 100000) {
			System.out.println("Yay1");
		}
		
		if (map.get(5).equals("Val5") && map.get(500).equals("Val500") &&
			map.get(5000).equals("Val5000") && map.get(9999).equals("Val9999")) {
			System.out.println("Yay2");
		}
		
		map.put(0, "Val" + 0);
		if (map.size() == 100000) {
			System.out.println("Yay3");
		}
		
	}

}