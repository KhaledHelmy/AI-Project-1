package eg.edu.guc.ai;
import java.util.HashSet;
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
	public HashSet<Board> visitedStates = new HashSet<Board>();
	
	public SearchProblem(Board initialState, List<Operation> operations){
		this.initialState = initialState;
		this.operations = operations;
	}
	
	// Expand the given node using all the operations and return a list of all resulting children nodes.
	public abstract Queue<Node> expand(Node node);

	// Test whether a node is a goal node or not.
	public abstract boolean applyGoalTest(Node node);
}
