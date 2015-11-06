package eg.edu.guc.ai;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// A class representing an abstract search problem
public abstract class SearchProblem {
	// operators 		√
	// initial state 	√
	// goal test 		√
	// path cost		?
	public List<Operation> operations;
	public Board initialState;
	
	public SearchProblem(Board initialState, List<Operation> operations){
		this.initialState = initialState;
		this.operations = operations;
	}
	
	// Expand the given node using all the operations and return a list of all resulting children nodes.
	public Queue<Node> expand(Node node){
		Queue<Node> children = new LinkedList<Node>();
		for(Operation operator : this.operations){
			Queue<Node> resultingNodes = operator.execute(node);
			if(resultingNodes != null){
				children.addAll(resultingNodes);
			}
		}
		return children;
	}
	
	// Test whether a node is a goal node or not.
	public abstract boolean applyGoalTest(Node node);
}
