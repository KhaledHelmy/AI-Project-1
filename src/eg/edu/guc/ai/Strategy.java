package eg.edu.guc.ai;
import java.util.Queue;

// A class representing an interface for a search strategy
public interface Strategy {
	public Queue<Node> execute(Queue<Node> nodes, Queue<Node> newNodes);
}
