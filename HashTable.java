import java.util.NoSuchElementException;
import java.util.Iterator;
import java.io.Serializable;

/** The implementation of a hash table using linear probing.
 * @param <K> hold the generic key in map
 * @param <V> hold the generic value in map
 * @author Fatemah Bahzad
 */
public class HashTable<K, V> implements Serializable{

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	/** combine the two generic parameters to be used as a table entry.
 	* @param <K> hold the generic key in map
 	* @param <V> hold the generic value in map
 	*/
	
	private class TableEntry<K,V> implements Serializable{
		/** the generic key in map.
	 	* @param key the generic key in map
	 	*/
		private K key;
		/** the generic value in map.
	 	* @param value the generic value in map
	 	*/
		private V value;
		/** set the key and value in TableEntry as the given values.
	 	* @param key the generic key in map
	 	* @param value the generic value in map
	 	*/
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		/** returning the key in a pair.
	 	* @return key in a pair
	 	*/
		public K getKey() {
			return key;
		}
		/** returning the value in a pair.
	 	* @return value in a pair
	 	*/
		public V getValue() {
			return value;
		}
		/** create a string with the key and value in TableEntry.
	 	* @return string with the key and value in TableEntry
	 	*/
		public String toString() {
			return key.toString()+":"+value.toString();
		}
	}
	/** an array of Table Entrys.
 	* @return storage an array of TableEntrys
 	*/
	private TableEntry<K,V>[] storage;

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------

	/** number of items in the hash table.
 	* @param items is the number of elements in the hash table  
 	*/
	private int items=0;
	/** the Capacity of hash table.
 	* @param  Capacity of the hash table
 	*/
	private int capacity=0;
	
	/** set the Capacity of the hash table.
 	* @param size is the new capacity of the hash table
 	*/
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		capacity=size;
		items=0;
		storage= new TableEntry[size];

	}
	/** get the Capacity of the hash table.
 	* @return Capacity of the hash table 
 	*/
	public int getCapacity() {
		return capacity; 
	}
	/** get the number of items in the hash table.
 	* @return items, the number of items in the hash table
 	*/
	public int size() {
		return items; 
	}
	/** add the TableEntry of key and val in the hash table if it is not in it.
	* and replace the value if the key is in the hash table 
	* @param key of the TableEntry
	* @param val of the TableEntry
	*  @return false if the new TableEntry is not added, true if it is 
 	*/
	public boolean put(K key, V val) {
		if (key==null || val==null) { 
			return false;
		}
		int index=Math.abs(key.hashCode()) % capacity;
		if(index>capacity) {
			return false;
		}

		
		if (storage[index]==null||isTombstone(index)) { //if there is nothing create a new entry 
			storage[index]= new TableEntry<>(key,val);
			items++; //count the items
		}
		if (storage[index]!=null) { //if there is a value in the index 

			if (storage[index].getKey().equals(key)) { //if they have the same key 
				storage[index]=new TableEntry<>(key,val);	
			}
			
			if (!storage[index].getKey().equals(key)) { //if they are not the same, look for an empty place
				
				int j=index+1;//start right after the index 
				if (index==capacity-1) {//if it is the last index in the table
					j=0; //start from the index 0
				}
				while(j!=index) {
					if (storage[j]==null || isTombstone(j)) {//if it is an empty index
						storage[j]=new TableEntry<>(key,val);
						items++;
						break;
					}
					if (storage[j].getKey().equals(key)) { //if they have the same key 
						storage[j]=new TableEntry<>(key,val);
						break;
						
					}
					else {
						j++;
						if (j==capacity) { //if it is at the end return to the start
							j=0;
						}
					}
				}
			}		
		}

		if ((double) items/capacity>=0.8) { //if the table is >=80% full, rehash  
			rehash(capacity*2);
		}

		return true; 
		
	}
	/**
	 *  get the value of the key.
	 * @param key to get its value
	 * @return the value of the key 
	 */
	public V get(K key) { 
		if (key==null) {
			return null;
		}
		for (int i=0;i<capacity;i++) {
			if (storage[i]==null || isTombstone(i)) { //if it is empty are is a tombstone
				continue;
			}
			if (storage[i].getKey().equals(key)) {
				return storage[i].getValue(); //if it is the key return its value
			}
		}

		return null; 

	}
	
	/**
	 * check if the location is a Tomb stone or not.
	 * @param loc to check for Tomb stone
	 * @return true if it is a Tomb stone, false if not
	 */
	public boolean isTombstone(int loc) {
		if (loc<0 || loc>=capacity) {
			return false;//if out of bounds 
		}
		if (storage[loc]==null) { 
			return false;
		}
		if (storage[loc].key==null && storage[loc].value!=null) {
			return true; //if the key is null and the value isnt it means it is a Tombstone
		}

		return false; 
	}

	/**
	 * change the Capacity of the hash table if needed.
	 * @param size , the new Capacity
	 * @return true if the hashtable is rehased, false if not 
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) { 
		TableEntry<K,V>[] temp=new TableEntry[size]; 
		if (items>size) { //if there is more items then capacity 
			return false;
		}
		capacity=size; //new Capacity
		for (int i=0;i<storage.length;i++) {
			if (storage[i]==null || isTombstone(i)) {
				continue;
			}

			int nindex=Math.abs(storage[i].getKey().hashCode()) % capacity;//get the new index for the keys
			if (temp[nindex]==null) { //if there is nothing create a new entry 
				temp[nindex]= storage[i];
				continue;
			}
			if (temp[nindex]!=null) { //if there is a value in the index 
				int j=nindex+1;//start right after the index 
				if (nindex==capacity-1) {//if it is the last index in the table
					j=0; //start from the index 0
				}
				while(j!=nindex) {
					if (temp[j]==null) {//if it is an empty index
						temp[j]=storage[i];
						break;
					}
					else {
						j++;
						if (j==capacity) //if it is at the end return to the start
							j=0;
					}
				}
			}

		}
		storage=temp;
		capacity=size; 
		return true; 
	}
	

	/**
	 * remove the Table entry and set it as a tomb stone.
	 * @param key of the Table entry to remove 
	 * @return the value of the removed Table entry
	 */
	public V remove(K key) {
		int index=Math.abs(key.hashCode()) % capacity;
		if (storage[index]==null) {
			return null;
		}
		V item=storage[index].getValue();
		storage[index]=new TableEntry<>(null,item); //make key null to know that it is a tombstone
		items--;

		return item;

	}

	//-------------------------------------------------------------
	// PROVIDED METHODS BELOW 
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	/**
	 * create a string with the values of each entry.
	 * @return string with the values of each entry
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null && !isTombstone(i)) {
				s.append(storage[i] + "\n");
			}
		}
		return s.toString().trim();
	}
	/**
	 * create a string with the values of each entry used to debug.
	 * @return string with the values of each entry
	 */
	public String toStringDebug() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			}
			else {
				s.append("[" + i + "]: tombstone\n");
			}
			
		}
		return s.toString().trim();
	}
	
	//-------------------------------------------------------------
	// END OF PROVIDED METHODS SECTION 
	//-------------------------------------------------------------

	//-------------------------------------------------------------
	// TESTING CODE   
	//-------------------------------------------------------------
	/**
	*This method is provided for debugging purposes.
	* @param args string to test the code
	*/
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		HashTable<String,Integer> ht1 = new HashTable<>(10);
		HashTable<Integer,Character> ht2 = new HashTable<>(5);
		
		//initialize
		if(ht1.getCapacity() == 10 && ht2.getCapacity() == 5 && ht1.size() == 0 && ht2.size() == 0) {
			System.out.println("Yay 1");
		}
		
		//put
		ht1.put("a",1); //"a".hashCode = 97
		ht1.put("b",1); //"b".hashCode = 98
		ht1.put("b",2); //update
		ht1.put("b",3);
		
		if(ht1.toString().equals("a:1\nb:3") 
			&& ht1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:3\n[9]: null")) {
			System.out.println("Yay 2");
		}
		
		if(!ht1.put(null,0) && ht1.getCapacity() == 10 && ht1.size() == 2 && ht1.get("a")==1 && ht1.get("b")==3) {
			System.out.println("Yay 3");
		}
		
		//put with collision
		ht2.put(12,'A');
		ht2.put(22,'B');
		ht2.put(37,'C');
		ht2.put(47,'D');

		if(ht2.getCapacity() == 10 && ht2.size() == 4 && ht2.get(1)==null 
			&& ht2.get(12)=='A' && ht2.get(22)=='B' && ht2.get(37)=='C'
			&& ht2.get(47)=='D') {
			System.out.println("Yay 4");
		}

		if(ht2.toString().equals("12:A\n22:B\n47:D\n37:C") && 
			ht2.toStringDebug().equals("[0]: null\n[1]: null\n[2]: 12:A\n[3]: 22:B\n[4]: null\n[5]: null\n[6]: null\n[7]: 47:D\n[8]: 37:C\n[9]: null")) {
			System.out.println("Yay 5");
		}
		
		//remove
		HashTable<String,Integer> ht3 = new HashTable<>(2);
		ht3.put("apple",1);  //hashCode: 93029210
		if(ht3.remove("apple") == 1 && ht3.remove("banana") == null && ht3.toString().equals("") 
			&& ht3.toStringDebug().equals("[0]: tombstone\n[1]: null")) {
			ht3.put("B",1);
			if(ht3.toString().equals("B:1") && ht3.toStringDebug().equals("[0]: B:1\n[1]: null")) {
				System.out.println("Yay 6");
			}
		}
		//rehash
		if(!ht2.rehash(2) && ht2.size() == 4 && ht2.getCapacity() == 10) {
			System.out.println("Yay 7");
		}
		
		if(ht2.rehash(7) && ht2.size() == 4 && ht2.getCapacity() == 7) {
			System.out.println("Yay 8");
		}
		//System.out.println(ht2);
		//System.out.println(ht2.toStringDebug());
		
		if(ht2.toString().equals("22:B\n37:C\n12:A\n47:D") 
			&& ht2.toStringDebug().equals("[0]: null\n[1]: 22:B\n[2]: 37:C\n[3]: null\n[4]: null\n[5]: 12:A\n[6]: 47:D")) {
			System.out.println("Yay 9");
		}
		
	}
	
}