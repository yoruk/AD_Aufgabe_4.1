package aufg_4_1_3;

public class BDBListImpl<E> implements BDBList<E> {
    public class Node {
		public E DATA = null;
		public int list_index = 0;
		public final int rValue;
		public final Node lNode;
		public final Node rNode;
		
		public Node(int rValue) {
			int next_rValue = 0;
			
			/* rValue fuer derzeitigen Node */
			this.rValue = rValue;
			
			/* fuer weitere berechnungen sollte 
			   rValue positiv sein */
			if(rValue < 0) {
				rValue *= -1;
			}
			
			/* weitere nodes erstellen wenn wir nicht 
			  schon auf der untersten ebene sind */
			if(rValue != 1) {
				next_rValue = (rValue / 2) + (rValue % 2);
				
				lNode = new Node(-next_rValue);
				rNode = new Node(next_rValue);
			} else {
				lNode = null;
				rNode = null;
			}
		}
    }
    
    private Node root = null;
    private int elements = 0;
    
    /* postion: anfang=true, ende=false */
    private boolean add(E e, boolean position) {
    	if(e == null) {
    		return false;
    	}
    	
    	Node tmp_tree = null;
    	Node tmp_node_from = null;
    	Node tmp_node_to = null;
    	
    	elements++;
    	
    	/* wenn elements == 1, dann existierte vorher kein baum,
    	   neuer baum muss erstellt werden */
    	if(elements == 1) {
    		/* ein element */
    		root = new Node(1);
    		
    		root.DATA = e;
    		root.list_index = elements;
    		
    		return true;
    		
    	/* ansonsten temporaeren baum erstellen */
    	} else {
    		if(elements == 2) {
    			tmp_tree = new Node(2);
    		} else {
    			tmp_tree = new Node((elements / 2) + 1);    			
    		}
    	}
    	
    	/* alten baum auslesen und inhalt in neuen baum schreiben */
    	for(int i=1; i<elements; i++) {
    		/* absender finden */
    		tmp_node_from = this.search(i, this.root);
    		
    		/* empfaenger finden */
    		if(position) {
    			/* einfuegen am anfang */
    			tmp_node_from.list_index++;
    			tmp_node_to = this.search(i+1, tmp_tree);
    		} else {
    			/* einfuegen am ende */
    			tmp_node_to = this.search(i, tmp_tree);
    		}

    		tmp_node_to.DATA = tmp_node_from.DATA;    			
    		tmp_node_to.list_index = tmp_node_from.list_index; 		
    	}
    	
    	/* neues element in neuen baum hinzufuegen */
    	if(position) {
    		/* einfuegen am anfang */
    		tmp_node_to = this.search(1, tmp_tree);
    		tmp_node_to.list_index = 1;    		
    	} else {
    		/* einfuegen am ende */
    		tmp_node_to = this.search(elements, tmp_tree);
    		tmp_node_to.list_index = elements;    		
    	}
    	tmp_node_to.DATA = e;
    	
    	root = tmp_tree;
    	
        return true;
    }
 
    public boolean addBeginning(E e) {
    	return this.add(e, true);
    }

    public boolean addEnd(E e) {
    	return this.add(e, false);
    }

    public E get(int index) {
    	Node tmp = this.search(index+1, this.root);
    	
    	if(tmp == null) {
    		return null;
    	} else {
    		return tmp.DATA;
    	}
    }
    
    private Node search(int index, Node tree) {
    	int calc_index = 0;
    	Node tmp_node = null;
    	
    	/* wird nach einem element gesucht,
    	   was nicht in der liste ist ? */
    	if(index > elements || index < 1 || root == null) {
    		return null;
    	} else {
    		
    		/* haben wir nur ein Element und suchen genau dieses */
    		if((tree.rValue == 1) && (index == 1)) {
    			return tree;
    		} else {
       			/* im baum suchen */
    			
    			/* startwerte setzen */
    			tmp_node = tree;
    			calc_index = tmp_node.rValue;
    				
    			while(calc_index != index) {	
    				/* ist der gesuchte index groesser oder kleiner als calc_index */
    				if((tmp_node.rValue != -1) && (tmp_node.rValue != 1)) {
    					if(index > calc_index) {
    						tmp_node = tmp_node.rNode;
    					} else {
    						tmp_node = tmp_node.lNode;
    					}
    											
    					calc_index += tmp_node.rValue;
    				} else {
    					break;
    				}
    			}

    			/* ist der derzeitige node, der den mann sucht? */
    			if(calc_index == index) {
    				return tmp_node;
    			}
    		}
    	}
    	
    	/* sollte nicht erreicht werden */
    	System.out.println("Fehler in search(int index, Node tree)!");
    	System.out.println("root-rvalue:" + tree.rValue + " index:" + index + " calc_index:" + calc_index);
    	return null;
    }
}