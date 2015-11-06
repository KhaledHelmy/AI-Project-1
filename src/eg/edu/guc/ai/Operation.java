package eg.edu.guc.ai;
import java.util.Queue;

// An interface representing an operation in a search problem
public interface Operation {
	
	// Execute the operation on the search tree node.
	// It returns the resulting search tree nodes from the operation.
	// If the operation is not valid, it returns null.
	public Queue<Node> execute(Node node, int... args);
}
