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
    }

    public class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
        int level;
        
        public void addChild(T data){
        	Node<T> child = new Node<T>();
        	child.data = data;
        	child.parent = this;
        	child.level = this.level++;
        	children.add(child);
        }
        
        public Node<T> dfSearch(T data){
        	Node<T> searchResult;
        	for(Node<T> n : children){
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
    }
    
    public Node<T> search(T data){
    	return root.dfSearch(data);
    }
}
