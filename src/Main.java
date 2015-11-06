import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


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
				        for (int k=0; k<2; i++) {
				        	board[i][j] |= (1 << openings.get(k));
				        }
					}
				}
			}
		}
	}
}
