package main;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.level = 0;
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
        root.parent = null;
    }

    @SuppressWarnings("hiding")
	public class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
        int level;
        
        public Node(){
        	children = new ArrayList<Node<T>>();
        }
        
        public void addChild(T data){
        	Node<T> child = new Node<T>();
        	child.data = data;
        	child.parent = this;
        	child.level = this.level + 1;
        	System.out.println(level);
        	this.children.add(child);
        }
        
        public Node<T> dfSearch(T data){
        	Node<T> searchResult;
        	for(Node<T> n : this.children){
        		if(n.data == data){
        			return n;
        		}
        		else if(!n.children.isEmpty()){
        			searchResult = n.dfSearch(data);
        			if(searchResult != null){
        				return searchResult;
        			}
        		}
        	}
        	return null;
        }
        
        public Node<T> getChild(T data){
        	for(Node<T> n : this.children){
        		if(n.data == data){
        			return n;
        		}
        	}
        	
        	return null;
        }
        
        public T getData(){
        	try{
        		return this.data;
        	} catch (NullPointerException e){
        		return null;
        	}
        }
        
        public List<Node<T>> getPath(){
        	
        	List<Node<T>> path = new ArrayList<Node<T>>();
        	
        	Node<T> n = this;
        	
        	path.add(n);
        	
        	while(n.parent != null){
        		path.add(n.parent);
        		n = n.parent;
        	}
        	
			return path;
        	
        }
    }
    
    public Node<T> search(T data){
    	return root.dfSearch(data);
    }
}
