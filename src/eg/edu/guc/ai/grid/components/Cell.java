package eg.edu.guc.ai.grid.components;

import eg.edu.guc.ai.grid.components.tiles.Tile;

public class Cell {
	private Tile tile;
	private int x, y;

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
