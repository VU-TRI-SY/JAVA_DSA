import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

class Node <E> {
	E data;
	Node<E> next;
	Node<E> jump;
	Node<E> prev;
	public Node(E data){
		this.data = data;
		next = null;
		jump = null;
		prev = null;
	}
}

public class ExpressLinkedList <E> {
	private final E DEFAULT_VALUE; //default value for all nodes on the list
	private int size; //number of elements or number of nodes on the list
	
	private Node<E> head, tail;
	ExpressLinkedList(){ //create empty list
		this.DEFAULT_VALUE = null;
		this.size = 0;
		this.head = this.tail = null;
	}

	ExpressLinkedList(E defaultValue){ //constructor to create the empty list with given default value for all nodes
		this.DEFAULT_VALUE = defaultValue;
		this.size = 0;
		this.head = this.tail = null;
	}

	// @Override
	public boolean add(E e) { //append an element 'e' into the list
		Node<E> newNode = new Node<>(e); //create single node with given data 'e'
		if(head == null){ //empty list
			head = tail = newNode;
			this.size++;
		}else{
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
			this.size++;
			if(this.size >= 11){
				//the newNode will be refered by a previous node
				//the newNode has index size-1
				//we must find the node that has index (size-1)-10 and refer that node to the newNode
				int idx = this.size - 1; //index of newNode
				Node<E> cur = newNode; //reference to refer the target node
				int index_of_new_node = this.size-1;
				//we must move the idx to the node that has index = idx-10
				while(idx > index_of_new_node-10){
					idx--;
					cur = cur.prev; //move backward
				}
				//after while, idx = index_of_new_node-10 and cur refers to the target node
				cur.jump = newNode;
			}
		}
		
		return true;
	}

	public void display(){
		if(head == null) return;
		// Node<E> cur = tail;
		Node<E> cur = head;
		while(true){
			System.out.println("Traverse the node: " + cur.data);
			if(cur.jump != null)
				System.out.println("The refered node: " + cur.jump.data);
			else{
				System.out.println("Next refers to null");
			}

			System.out.println("---------------------");

			if(cur == tail) return;
			// cur = cur.prev;
			cur = cur.next;
		}
	}

	// @Override
	public void add(int index, E element) { //add element at specific index
		if(index > this.size) throw new IndexOutOfBoundsException(); //throw exception for invalid index
		else if(index == this.size) this.add(element);
		else if(index == 0) { //add to the head (modify head)
			Node<E> newNode = new Node<>(element);
			this.size++; //current size after adding
			if(head == null){
				head = tail = newNode;
			}else{
				head.prev = newNode;
				newNode.next = head;
				Node<E> temp = head.jump;
				head = newNode;
				if(temp != null){
					newNode.jump = temp.prev;
				}else if (this.size > 10){
					newNode.jump = tail;
				}
			}
		}else{ //list is not empty, insert to the middle
			Node<E> newNode = new Node<>(element);
			this.size++; //current size after adding
			Node<E> cur = tail;
			int idx = this.size - 1; //index of tail
			while(idx > index + 1){
				cur = cur.prev;
				idx--;
			}

			//after while, idx = index+1 and cur refers to target node (node that we must insert newnode before it)
			cur.prev.next = newNode;
			newNode.prev = cur.prev;
			newNode.next = cur;
			cur.prev = newNode;
			if(cur.jump != null){//having some nodes from cur that has next Node
				Node<E> after = cur.jump.prev;
				Node<E> before = newNode;

				while(true){
					before.jump = after;
					if(before == head) return; //there is no node to re-connect
					//move pair before and after references to the next pair to re-connect (move to the left hand side)
					before = before.prev;
					after = after.prev;
				}
			}else{
				Node<E> after = null; //dont know the next of the before
				Node<E> before = newNode;
				int cur_index = index; //index of newNode or index of the starting node to move

				while(true){
					if(after != null){
						//just link before to the after
					}else if(cur_index + 10 == this.size - 1){ //current node shoudl has next reference refers to tail
						after = tail;
					}

					//link before to after ~ reconnect
					before.jump = after;
					if(after != null) {
						after = after.prev;
					}

					if(before == head) return; //there is no node to move backward
					before = before.prev;
					cur_index--;
				}
			}
		}
	}

	
	// @Override
	public E get(int index) {
		if (index < 0 || index >= this.size())
			throw new IndexOutOfBoundsException();
		if (head == null) return null;


		int temp_index = index;
		int idx = 0;
		while(temp_index >= 10) temp_index -= 10;

		Node<E> cur = head;
		while(idx < temp_index) {
			idx++;
			cur = cur.next;
		}

		while(idx != index){
			cur = cur.jump;
			idx += 10;
		}
		return cur.data;
	}

	
	// @Override
	public int size() {
		return this.size;
	}

	// @Override
	// public boolean addAll(Collection<? extends E> c) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public boolean addAll(int index, Collection<? extends E> c) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public void clear() {
	// 	map.clear();
	// 	this.size = 0;
	// }

	// @Override
	// public boolean contains(Object o) {
	// 	return map.containsValue(o);
	// }

	// @Override
	// public boolean containsAll(Collection<?> c) {
	// 	throw new UnsupportedOperationException();
	// }


	// @Override
	// public int indexOf(Object o) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public boolean isEmpty() {
	// 	return this.size == 0;
	// }

	// @Override
	// public Iterator<E> iterator() {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public int lastIndexOf(Object o) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public ListIterator<E> listIterator() {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public ListIterator<E> listIterator(int index) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public boolean remove(Object o) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public E remove(int index) {
	// 	if (index < 0 || index >= this.size())
	// 		throw new IndexOutOfBoundsException();
		
	// 	E removed = map.remove(index);
	// 	for (int i = index; i < this.size-1; i++){
	// 		if (map.containsKey(i + 1))
	// 			map.put(i, map.get(i + 1));
	// 		else
	// 			map.remove(i);
	// 	}
	// 	map.remove(this.size - 1);
	// 	this.size--;
	// 	return removed;
	// }

	// @Override
	// public boolean removeAll(Collection<?> c) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public boolean retainAll(Collection<?> c) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public E set(int index, E element) {
	// 	if (index < 0 || index >= this.size())
	// 		throw new IndexOutOfBoundsException();
	// 	E oldValue = map.get(index);
	// 	map.put(index, element);
	// 	return oldValue;
	// }


	// @Override
	// public List<E> subList(int fromIndex, int toIndex) {
	// 	throw new UnsupportedOperationException();
	// }

	// @Override
	// public Object[] toArray() {
	// 	Object [] array = new Object[this.size];
	// 	for (int i = 0; i < this.size; i++){
	// 		if (map.containsKey(i))
	// 			array[i] = map.get(i);
	// 		else
	// 			array[i] = this.DEFAULT_VALUE;
	// 	}
	// 	return array;
	// }

	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> T[] toArray(T[] a) {
	// 	if (!a.getClass().getComponentType().isAssignableFrom(this.DEFAULT_VALUE.getClass()))
	// 		throw new ArrayStoreException();
		
	// 	if (a.length < this.size) a = Arrays.copyOf(a, this.size);
	// 	for (int i = 0; i < this.size; i++){
	// 		if (map.containsKey(i)) 
	// 			a[i] = (T) map.get(i);
	// 		else 
	// 			a[i] = (T) this.DEFAULT_VALUE;
	// 	}
	// 	return a;
	// }

	
	// @Override
	// public String toString(){
	// 	if (this.size == 0) return "[]"; 
	// 	StringBuilder sb = new StringBuilder();
	// 	sb.append('[');
	// 	for (int i = 0; i < this.size - 1; i++){
	// 		E value = (E) this.map.get(i);
	// 		if (value != null) 
	// 			sb.append(value);
	// 		else 
	// 			sb.append(this.DEFAULT_VALUE);
	// 		sb.append(", ");
	// 	}

	// 	E value = (E) this.map.get(this.size - 1);
	// 	if (value != null) 
	// 		sb.append(value);
	// 	else 
	// 		sb.append(this.DEFAULT_VALUE);
	// 	//sb.append(map.get(this.size - 1));
	// 	sb.append(']');
	// 	return sb.toString(); //+ " " + map.toString();
	// }

	public static void main(String[] args){
		ExpressLinkedList<Integer> ll = new ExpressLinkedList<>(-1);

		for(int i = 150; i >=0 ; i--) {
			if( i==4) continue;
			ll.add(0, i);
		}

		ll.add(4, 4);

		System.out.println(ll.get(100));
	}
}
