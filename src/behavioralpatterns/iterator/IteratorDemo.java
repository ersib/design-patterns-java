package behavioralpatterns.iterator;

import java.util.Iterator;

// ITERATOR - AN OBJECT THAT FACILITATES THE TRAVERSAL OF A DATA STRUCTURE

class Node<T> {
	public T value;
	public Node<T> left, right, parent;
	
	public Node(T value) {
		this.value = value;
	}

	public Node(T value, Node<T> left, Node<T> right) {
		this.value = value;
		this.left = left;
		this.right = right;
		left.parent = right.parent = this;
	}	

}

class InOrderIterator<T> implements Iterator<T> {

	private Node<T> current;
	private Node<T> root;
	private boolean yieldedStart;
	
	public InOrderIterator(Node<T> root) {
		this.root = current = root;
		while (current.left != null)
			current = current.left;
	}
	
	private boolean hasRightMostParent(Node<T> node) {
		if (node.parent == null)
			return false;
		else {
			return node == node.parent.left || hasRightMostParent(node.parent);
		}
	}
	
	@Override
	public boolean hasNext() {
		return current.left != null 
				|| current.right != null
				|| hasRightMostParent(current);
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

public class IteratorDemo {

}
