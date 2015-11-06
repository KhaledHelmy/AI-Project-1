package eg.edu.guc.ai;

// A class representing a search tree node.
public class Node {
	public int pathCost;
	public Node parentNode;
	public int depth;
	public State state;
	public Operation operations;
	
	public Node(int cost, Node parentNode, State state, Operation operations){
		this.parentNode = parentNode;
		if(this.parentNode != null){
			this.pathCost = parentNode.pathCost + cost;
			this.depth = parentNode.depth + 1;
		}
		else{
			this.pathCost = cost;
			this.depth = 0;
		}
		this.state = state;
		this.operations = operations;
	}
	
	public static Node makeNode(State initialState){
		return new Node(0, null, initialState, null);
	}
}
