import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import eg.edu.guc.ai.Board;
import eg.edu.guc.ai.RollTheBall;
import eg.edu.guc.ai.SearchAlgorithm;
import eg.edu.guc.ai.SearchAlgorithm.BFS;
import eg.edu.guc.ai.SearchAlgorithm.SearchOutput;


public class Main {

	public static void main(String[] args) {
		Random heightGenerator = new Random();
		Random widthGenerator = new Random();
		Random tileTypeGenerator = new Random();
		Random specialTileGeneratorX = new Random();
		Random specialTileGeneratorY = new Random();
		int height = heightGenerator.nextInt(9) + 2;
		int width = widthGenerator.nextInt(9) + 2;
		int startTileX = specialTileGeneratorX.nextInt(height);
		int startTileY = specialTileGeneratorY.nextInt(width);
		int endTileX = specialTileGeneratorX.nextInt(height);
		int endTileY = specialTileGeneratorY.nextInt(width);
		while (endTileX == startTileX && endTileY == startTileY) {
			endTileX = specialTileGeneratorX.nextInt(height);
			endTileY = specialTileGeneratorY.nextInt(width);
		}
		int[][] board = new int[height][width];
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				board[i][j] = 0;
				if (i == startTileX && j == startTileY) {
					Random initialOpeningGenerator = new Random();
					int opening = initialOpeningGenerator.nextInt(4) + 1;
					board[i][j] |= (1 << 5);
					board[i][j] |= (1 << opening);
				} else if (i == endTileX && j == endTileY) {
					Random goalOpeningGenerator = new Random();
					int opening = goalOpeningGenerator.nextInt(4) + 1;
					board[i][j] |= (1 << 6);
					board[i][j] |= (1 << opening);
				} else {
					int tileType = tileTypeGenerator.nextInt(3);
					//0 --> BlankTile, 1 --> BlockTile, 2 --> PathTile
					if (tileType == 1) {
						board[i][j] |= (1 << 0);
					} else {
						Random movableGenerator = new Random();
						int movable = movableGenerator.nextInt(2);
						board[i][j] |= (movable << 0);
						ArrayList<Integer> openings = new ArrayList<Integer>();
				        for (int k=1; k<=4; k++) {
				            openings.add(k);
				        }
				        Collections.shuffle(openings);
				        for (int k=0; k<2; k++) {
				        	board[i][j] |= (1 << openings.get(k));
				        }
					}
				}
			}
		}
		/*int[][] board = new int[3][3];
		board[0][0] = (1 << 4) | (1 << 5);
		board[1][1] = (1 << 0) | (1 << 2) | (1 << 4);
		board[2][0] = (1 << 2) | (1 << 3);
		board[2][1] = (1 << 1) | (1 << 6);*/
		Board stateInstance = new Board(board);
		RollTheBall problemInstance = new RollTheBall(stateInstance);
		SearchAlgorithm searchInstance = new SearchAlgorithm();
		BFS bfsInstance = new BFS();
		SearchOutput output = searchInstance.search(problemInstance, bfsInstance, true);
	}
}
