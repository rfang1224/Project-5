import java.util.*;

/**
 * An implementation of a binary search tree. 
 * The elements are ordered using their natural ordering.
 * 
 * @author Robert Fang
 */
public class BST<E extends Comparable<E>> implements Iterable<E>{

    //the root of the BST
    protected Node root;

    //the size of the BST
    protected int size;

    //the height of the BST
    protected int height;

    //whether element has been removed or not
    protected boolean removed;

    //whether element has been added or not
    protected boolean added;

    //Node class that represents each individual node of the BST
    protected class Node{

        //constructs a new node
        public Node(){
            left = null;
            right = null;
            data = null;
        }

        //node left of this node
        public Node left;

        //node right of this node
        public Node right;

        //data stored in node
        public E data;

        //height of the node
        public int height;
    }

    //class that provides 3 types of iterators to iterate through the BST
    private class BSTIterator implements Iterator<E>{
        //queue of values made after iteration
        private Queue<E> values = new ArrayDeque<E>();

        //creates a new iterator based on the input
        public BSTIterator(int c){
            if(c == 1){
                inorderIter(root);
            }
            else if(c == 2){
                preorderIter(root);
            }
            else{
                postorderIter(root);
            }
        }

        //iterator that iterates in order
        private void inorderIter (Node node) {
            if (node == null ) return;
            inorderIter(node.left);
            values.offer(node.data); 
            inorderIter(node.right); 
        }

        //iterator that iterates by processing current node
        private void preorderIter (Node node) {
            if (node == null ) return;
            values.offer(node.data);
            preorderIter(node.left);
            preorderIter(node.right); 
        }

        //iterator that iterates by processing left and right nodes
        private void postorderIter (Node node) {
            if (node == null ) return;
            postorderIter(node.left);
            postorderIter(node.right);
            values.offer(node.data); 
        }

        //checks if there is a value after the current one
        public boolean hasNext() { 
            return !(values.isEmpty());  
        }

        //returns the current value and moves onto the next
        public E next() {
            if (values.isEmpty()) {
                throw new NoSuchElementException("Nothing more"); 
            }
            return values.poll(); 
        }
    } 

    /**
     * Returns an iterator over the elements in this tree in ascending order.
     * 
     * @return an iterator over the elements in this set in ascending order
     */
    public Iterator<E> iterator(){
        return new BSTIterator(1);
    }

    /**
     * Returns an iterator over the elements in this tree in order of the preorder traversal.
     * 
     * @return an iterator over the elements in this tree in order of the preorder traversal
     */
    public Iterator<E> preorderIterator(){
        return new BSTIterator(2);
    }

    /**
     * Returns an iterator over the elements in this tree in order of the postorder traversal.
     * 
     * @return an iterator over the elements in this tree in order of the postorder traversal
     */
    public Iterator<E> postorderIterator(){
        return new BSTIterator(3);
    }

    /**
     * Constructs a new, empty tree, sorted according to the natural ordering of its elements. 
     * All elements inserted into the tree must implement the Comparable interface.
     */
    public BST(){
        size = 0;
        root = null;
        height = 0;
    }

    /**
     * Returns the height of this tree. The height of a leaf is 1. 
     * The height of the tree is the height of its root node
     * 
     * @return the height of this tree or zero if the tree is empty
     */
    public int height(){
        if(root == null){
            height = 0;
            return height;
        }
        height = root.height;
        return height;
    }

    /**
     * Returns the element at the specified position in this tree. 
     * The order of the indexed elements is the same as provided by this tree's iterator. 
     * The indexing is zero based (i.e., the smallest element in this tree is at index 0)
     * the largest one is at index size()-1.
     * 
     * @param index - index of the element to return
     * 
     * @return the element at the specified position in this tree
     * 
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
     */
    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Invalid Index");
        }
        //create a new inorder iterator
        Iterator<E> itr = iterator();
        int i = 0;
        E val = null;
        //iterate through the ordered list of values from the iterator
        //retrieve and return the one at the specified index
        while (itr.hasNext()) {
            val = itr.next();
            if (i == index) {
                break;
            }
            i++;
        }
        return val;
    }

    /**
     * Constructs a new tree containing the elements in the specified collection 
     * sorted according to the natural ordering of its elements. 
     * All elements inserted into the tree must implement the Comparable interface.
     * 
     * @param collection - collection whose elements will comprise the new tree
     * 
     * @throws NullPointerException - if the specified collection is null
     */
    public BST(E[] collection){
        if(collection == null){
            throw new NullPointerException("collection is null");
        }
        size = 0;
        height = 0;
        root = null;
        //add each element in the collection to the tree
        for (E e : collection) {
            add(e);
        }
    }

    /**
     * Returns the first (lowest) element currently in this tree.
     * 
     * @return the first (lowest) element currently in this tree
     * 
     * @throws NoSuchElementException - if this set is empty
     */
    public E first(){
        if(root == null){
            throw new NoSuchElementException("set is empty");
        }
        //recursively move down the left node until you reach the first element
        return first(root);
    }

    /**
     * goes through the BST in order to return the smallest element
     * 
     * @param node current node
     * 
     * @return the smallest value of the BST
     */
    private E first(Node node){
        if(node.left == null){
            return node.data;
        }
        return first(node.left);
    }

    /**
     * Returns the last (highest) element currently in this tree.
     * 
     * @return the last (highest) element currently in this tree
     * 
     * @throws NoSuchElementException - if this set is empty
     */
    public E last(){
        if(root == null){
            throw new NoSuchElementException("set is empty");
        }
        //recursively move down the right node until you reach the last element
        return last(root);
    }

    /**
     * goes through the BST in order to return the biggest element
     * 
     * @param node current node
     * 
     * @return the biggest value of the BST
     */
    private E last(Node node){
        if(node.right == null){
            return node.data;
        }
        return last(node.right);
    }

    /**
     * Removes all of the elements from this set. 
     * The set will be empty after this call returns.
     */
    public void clear(){
        height = 0;
        root = null;      
        size = 0;         
    }

    /**
     * Removes the specified element from this tree if it is present. 
     * More formally, removes an element e such that Objects.equals(o, e)
     * if this tree contains such an element. 
     * Returns true if this tree contained the element 
     * (or equivalently, if this tree changed as a result of the call). 
     * (This tree will not contain the element once the call returns.)
     * 
     * @param o - object to be removed from this set, if present
     * 
     * @return true if this set contained the specified element
     * 
     * @throws ClassCastException - 
     * if the specified object cannot be compared with the elements currently in this tree
     * 
     * @throws NullPointerException - if the specified element is null
     */
    public boolean remove(Object o){
        if(o == null){
            throw new NullPointerException("element cannot be null");
        }

        E value;

        //convert o into type e
        try{
            value = (E) o;
        }
        catch(ClassCastException e){
            throw new ClassCastException("not comparable");
        }

        removed = false;
        //use recursion to find and remove value
        root = remove(value, root);
        //if successfully remove, decrement the size
        if(removed){
            size--;
        }
        return removed;
    }

    /**
     * traverses the BST in order to find the node with the target value to remove
     * This code came from the ed workspaces
     * 
     * @param e the target value
     * 
     * @param node the current node of the traversal
     * 
     * @return the node that was removed or null
     */
    private Node remove(E value, Node node){
        if(node == null){
            removed = false;
            return node;
        }

        int comp = value.compareTo(node.data);

        //if value is smaller than the data in the current node, perform removal on the left subtree
        if(comp < 0){
            node.left = remove(value, node.left);
        }
        //if value is greater than the data in the current node, perform removal on the right subtree
        else if(comp > 0){
            node.right = remove(value, node.right);
        }
        //remove the current node and replace it with another one or null
        else{
            removed = true;
            node = removeNode(node);
        }

        //calculate the height of the current node
        if(node != null){
            int leftVal = 0;
            int rightVal = 0;
            //height of the current node is 1 more than the max height between the left and right nodes
            if(node.left != null){
                leftVal = node.left.height;
            }
            if(node.right != null){
                rightVal = node.right.height;
            }
            node.height = 1 + Math.max(leftVal, rightVal);
        }

        return node;
    }

    /**
     * returns the replacement node after removal
     * This code came from the ed workspaces
     * 
     * @param node the current node
     * 
     * @return the replacement node after removal
     */
    private Node removeNode(Node node){
        //replace current node with the one right to it if there is no left node
        if(node.left == null){
            return node.right;
        }

        //replace current node with the one left to it if there is no right node
        else if(node.right == null){
            return node.left;
        }

        //if there is a left and right node, replace current with predecessor
        else{
            //find predecessor in left subtree
            E data = predecessor(node.left);
            node.data = data;

            //perform removal on left subtree to remove predecessor
            node.left = remove(data, node.left);

            return node;
        }
    }

    /**
     * returns the data of the predecessor node
     * 
     * @param node the current node
     * 
     * @return the predecessor node value
     */
    private E predecessor(Node node){
        //continuously goes down right in the left subtree to get predecessor
        if(node.right == null){
            return node.data;
        }
        return predecessor(node.right);
    }

    /**
     * Adds the specified element to this set if it is not already present. 
     * More formally, adds the specified element e to this tree 
     * if the set contains no element e2 such that Objects.equals(e, e2). 
     * If this set already contains the element, the call leaves the set unchanged and returns false
     * 
     * @param e - element to be added to this set
     * 
     * @return true if this set did not already contain the specified element
     * 
     * @throws NullPointerException - if the specified element is null 
     * and this set uses natural ordering 
     * or its comparator does not permit null elements
     */
    public boolean add(E e){
        if(e == null){
            throw new NullPointerException("element cannot be null");
        }

        added = false;
        
        //if root is null, root is a new node with the parameter as data
        if(root == null){
            Node newNode = new Node();
            newNode.height = 1;
            newNode.data = e;
            root = newNode;
            added = true;
        }

        //add new node recursively if root is not null
        else{
            add(e, root);
        }

        //if sucessfully added, increase size by 1
        if(added == true){
            size++;
        }

        return added;
    }

    /**
     * traverse through the BST and add the new value at the correct position
     * calculates the height of the tree
     * 
     * @param e the value to be added to the BST
     * 
     * @param node the current node during traversal
     */
    private void add(E e, Node node){
        int comp = e.compareTo(node.data);         

        //if data is already in the BST, the stop
        if(comp == 0){
            return;
        }

        //if the current data value is less than e
        //if the right subtree does not exist, add the new node to the right of current
        //otherwise perform add on the right subtree
        else if(comp > 0){
            if(node.right == null){
                Node newNode = new Node();
                newNode.height = 1;
                newNode.data = e;
                node.right = newNode;
                added = true;
            }
            else{
                add(e, node.right);
            }
        }

        //if the current data value is greater than e
        //if the left subtree does not exist, add the new node to the left of current
        //otherwise perform add on the left subtree
        else{
            if(node.left == null){
                Node newNode = new Node();
                newNode.height = 1;
                newNode.data = e;
                node.left = newNode;
                added = true;
            }
            else{
                add(e, node.left);
            }
        }

        //calculate height of the current node
        if(node != null){
            int leftVal = 0;
            int rightVal = 0;
            //height of the current node is 1 more than the max height between 
            //the left and right nodes
            if(node.left != null){
                leftVal = node.left.height;
            }
            if(node.right != null){
                rightVal = node.right.height;
            }
            node.height = 1 + Math.max(leftVal, rightVal);
        }
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element 
     * or null if there is no such element
     * 
     * @param e the value to match
     * 
     * @return the greatest element less than or equal to e
     * or null if there is no such element
     * 
     * @throws ClassCastException - if the specified element cannot be compared with 
     * the elements currently in the set
     * 
     * @throws NullPointerException - if the specified element is null
     */
    public E floor(E e){
        if(e == null){
            throw new NullPointerException("element cannot be null");
        }

        //check if the specified element and BST elements are comparable
        if (root != null) {
            try{
                root.data.compareTo(e);
            }   
            catch (ClassCastException ex) {
                throw new ClassCastException("not comparable");
            }
        }

        E result = findFloor(e, root, null);

        if(result == null || result.compareTo(e) > 0){
            return null;
        }

        return result;
    }

    /**
     * returns the greatest element less than or equal to the target
     * 
     * @param e the target element
     * 
     * @param current the current node during traversal
     * 
     * @param floor the current value of floor to be returned
     * 
     * @return the greatest element less than or equal to the target
     */
    private E findFloor(E e, Node current, E floor){

        E newFloor = floor;

        //if the current node is null, return the floor value
        if(current == null){
            return newFloor;
        }

        //if the current node data is equal to the target, return the current node data
        if(current.data.compareTo(e) == 0){
            return current.data;
        }

        //if the current node data is less than the target
        //first check if the current node data is greater than the current floor
        //if so, floor is set to the current node data
        //perform the search on the right subtree
        if(current.data.compareTo(e) < 0){
            if(newFloor == null || current.data.compareTo(newFloor) > 0){
                newFloor = current.data;
            }
            return findFloor(e, current.right, newFloor);
        }

        //if the current node data is greater than the target
        //perform search on left subtree
        else{
            return findFloor(e, current.left, newFloor);
        }
    }

    /**
     * Returns the least element in this tree greater than or equal to the given element 
     * or null if there is no such element.
     * 
     * @param e the value to match
     * 
     * @return the least element greater than or equal to e, or null if there is no such element
     * 
     * @throws ClassCastException - if the specified element cannot be compared with 
     * the elements currently in the set
     * 
     * @throws NullPointerException - if the specified element is null
     */
    public E ceiling(E e){
        if(e == null){
            throw new NullPointerException("element cannot be null");
        }

        //check if the specified element and BST elements are comparable
        if (root != null) {
            try {
                root.data.compareTo(e);
            } 
            catch (ClassCastException ex) {
                throw new ClassCastException("not comparable");
            }
        }

        E result = findCeiling(e, root, null);

        if(result == null || result.compareTo(e) < 0){
            return null;
        }

        return result;
    }

    /**
     * returns the least element greater than or equal to the target
     * 
     * @param e the target element
     * 
     * @param current the current node during traversal
     * 
     * @param ceiling the current value of ceiling to be returned
     * 
     * @return the least element greater than or equal to the target
     */
    private E findCeiling(E e, Node current, E ceiling){
        E newCeiling = ceiling;

        //if the current node is null, return ceiling
        if (current == null) {
            return newCeiling;
        }

        //if the current node data is equal to the target, return the current node data
        if (current.data.compareTo(e) == 0) {
            return current.data;
        }

        //if the current node data is greater than the target
        //first check if the current node data is less than the current ceiling
        //if so, ceiling is set to the current node data
        //perform the search on the left subtree
        if (current.data.compareTo(e) > 0) {
            if (newCeiling == null || current.data.compareTo(newCeiling) < 0) {
                newCeiling = current.data;
            }
            return findCeiling(e, current.left, newCeiling);
        } 

        //if the current node data is less than the target
        //perform search on right subtree
        else {
            return findCeiling(e, current.right, newCeiling);
        }
    }

    /**
     * Returns the greatest element in this set strictly less than the given element
     * or null if there is no such element.
     * 
     * @param e the value to match
     * 
     * @return the greatest element less than e, or null if there is no such element
     * 
     * @throws ClassCastException - if the specified element cannot be compared with the elements 
     * currently in the set
     * 
     * @throws NullPointerException - if the specified element is null
     */
    public E lower(E e){
        if(e == null){
            throw new NullPointerException("element cannot be null");
        }

        //check if the specified element and BST elements are comparable
        if (root != null) {
            try {
                root.data.compareTo(e);
            } 
            catch (ClassCastException ex) {
                throw new ClassCastException("not comparable");
            }
        }
            
        E result = findLower(e, root, null);

        if(result == null || result.compareTo(e) > 0){
            return null;
        }

        return result;
    }

    /**
     * returns the greatest element less than the target
     * 
     * @param e the target element
     * 
     * @param current the current node during traversal
     * 
     * @param lower the current value of lower to be returned
     * 
     * @return the greatest element less than the target
     */
    private E findLower(E e, Node current, E lower){
        E newLower = lower;

        //if current node is null, return lower
        if (current == null) {
            return newLower;
        }

        //if the current node data is less than the target
        //first check if the current node data is greater than the current lower
        //if so, lower is set to the current node data
        //perform the search on the right subtree
        if (current.data.compareTo(e) < 0) {
            if (newLower == null || current.data.compareTo(newLower) > 0) {
                newLower = current.data;
            }
            return findLower(e, current.right, newLower);
        }

        //if the current node data is greater than the target
        //perform search on left subtree
        else {
            return findLower(e, current.left, newLower);
        }
    }

    /**
     * Returns the least element in this tree strictly greater than the given element
     * or null if there is no such element.
     * 
     * @param e the value to match
     * 
     * @return the least element greater than e, or null if there is no such element
     * 
     * @throws ClassCastException - if the specified element cannot be compared with the elements 
     * currently in the set
     * 
     * @throws NullPointerException - if the specified element is null
     */
    public E higher(E e){
        if(e == null){
            throw new NullPointerException("element cannot be null");
        }

        //check if the specified element and BST elements are comparable
        if (root != null) {
            try {
                root.data.compareTo(e);
            } 
            catch (ClassCastException ex) {
                throw new ClassCastException("not comparable");
            }
        }
            
        E result = findHigher(e, root, null);
        
        if(result == null || result.compareTo(e) < 0){
            return null;
        }
            
        return result;
    }

    /**
     * returns the least element greater than the target
     * 
     * @param e the target element
     * 
     * @param current the current node during traversal
     * 
     * @param higher the current value of higher to be returned
     * 
     * @return the least element greater than the target
     */
    private E findHigher(E e, Node current, E higher){
        E newHigher = higher;

        //if current node is null, return higher
        if (current == null) {
            return newHigher;
        }

        //if the current node data is greater than the target
        //first check if the current node data is less than the current higher
        //if so, higher is set to the current node data
        //perform the search on the left subtree
        if (current.data.compareTo(e) > 0) {
            if (newHigher == null || current.data.compareTo(newHigher) < 0) {
                newHigher = current.data;
            }
            return findHigher(e, current.left, newHigher);
        } 

        //if the current node data is less than the target
        //perform search on right subtree
        else {
            return findHigher(e, current.right, newHigher);
        }
    }

    /**
     * Returns the number of elements in this tree.
     * 
     * @return the number of elements in this tree
     */
    public int size(){
        return size;
    }

    /**
     * Returns true if this set contains the specified element. 
     * More formally, returns true if and only if this set contains an 
     * element e such that Objects.equals(o, e)
     * 
     * @param o object to be checked for containment in this set
     * 
     * @return true if this set contains the specified element
     * 
     * @throws ClassCastException - if the specified object cannot be compared with 
     * the elements currently in the set
     * 
     * @throws NullPointerException - if the specified element is null 
     * and this set uses natural ordering
     * or its comparator does not permit null elements
     */
    public boolean contains(Object o){
        if(o == null){
            throw new NullPointerException("element cannot be null");
        }
        E value;
        try{
            value = (E) o;
        }
        catch(ClassCastException e){
            throw new ClassCastException("not comparable");
        }
        return recSearch(value, root);
    }

    /**
     * Returns true if the target is in the BST, false otherwise
     * 
     * @param value the target value
     * 
     * @param current the current node during traversal
     * 
     * @return true if the target is in the BST, false otherwise 
     */
    private boolean recSearch(E value, Node current){
        //if the current node is null, return false
        if(current == null){
            return false;
        }
        //if the current node data is greater than the target
        //perform the search on the left subtree
        if(current.data.compareTo(value) > 0){
            return recSearch(value, current.left);
        }
        //if the current node data is less than the target
        //perform the search on the right subtree
        else if(current.data.compareTo(value) < 0){
            return recSearch(value, current.right);
        }
        //return true if otherwise
        return true;
    }

    /**
     * Returns true if this set contains no elements.
     * 
     * @return true if this set contains no elements
     */
    public boolean isEmpty(){
         if(root == null){
            return true;
         }
         return false;
    }

    /**
     * Compares the specified object with this tree for equality. 
     * Returns true if the given object is also a tree
     * the two trees have the same size
     * and every member of the given tree is contained in this tree.
     * This code came from the ed workspaces
     * 
     * @param obj - object to be compared for equality with this tree
     * 
     * @return true if the specified object is equal to this tree
     */
    public boolean equals (Object obj){
        //if this equals object, return true
        if (this == obj ) return true; 

        //if the object is null, return false
        if ( obj == null ) return false; 


        //if the object is not a BST instance, return false
        if (!(obj instanceof BST )) return false;

        BST<?> other = (BST<?>) obj; 
        Iterator<E> itr1 = this.iterator(); 
        Iterator<?> itr2 = other.iterator(); 

        //while both trees have more values, traverse through them and check if they are equal
        while (itr1.hasNext() && itr2.hasNext() ) {
            E e1 = itr1.next();
            Object e2 = itr2.next(); 
            //if a pair of values are not equal, return false
            if (!e1.equals(e2)){ 
                return false; 
            }
        }

        //if one tree still has more nodes, return false
        if (itr1.hasNext() || itr2.hasNext()) return false;

        //return true if otherwise
        return true;
    }

    /**
     * Returns a string representation of this tree. 
     * The string representation consists of a list of the tree's elements 
     * in the order they are returned 
     * by its iterator (inorder traversal) enclosed in square brackets ("[]"). 
     * Adjacent elements are separated by the characters ", " (comma and space). 
     * Elements are converted to strings as by String.valueOf(Object).
     * 
     * @return a string representation of this collection
     */
    public String toString(){
        Iterator<E> itr = this.iterator(); 
        String stringRep = "[";
        //add values in the BST to a list with inorder traversal
        while(itr.hasNext()){
            stringRep += itr.next() + ", ";
        }
        //remove comma at the end
        if(stringRep.charAt(stringRep.length()-1) == ','){
            stringRep = stringRep.substring(0, stringRep.length()-1);
        }
        stringRep += "]";
        //return string representation
        return stringRep;
    }

    /**
     * Produces tree like string representation of this tree. 
     * Returns a string representation of this tree in a tree-like format. 
     * The string representation consists of a tree-like representation of this tree. 
     * Each node is shown in its own line with the indentation  
     * The root is printed on the first line, followed by its left and right subtrees
     * This code came from the ed workspaces
     * 
     * @return string containing tree-like representation of this tree.
     */
    public String toStringTreeFormat( ) {
        StringBuffer sb = new StringBuffer();
        toStringTree(sb, root, 0);
        return sb.toString();
    }

    /**
     * returns a string representation of the tree in a tree format
     * This code came from the ed workspaces
     * 
     * @param sb StringBuilder to build the final string representation
     * 
     * @param node current node during traversal
     * 
     * @param level level that the current node is on
     */
    private void toStringTree( StringBuffer sb, Node node, int level ) {
        if (level > 0 ) {
            //add spacing for each level of ancestors
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            //add branches
            sb.append("|--");
        }
        //if current node is null, add an arrow to the stringbuilder and stop
        if (node == null) {
            sb.append( "->\n");
            return;
        }
        //otherwise, stop
        else {
            sb.append(node.data + "\n");
        }
        //performs String tree formatting on left and right subtrees
        toStringTree(sb, node.left, level+1);
        toStringTree(sb, node.right, level+1);
    }
}
