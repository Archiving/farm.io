package com.scroll.game.state.tile;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Animation;

public abstract class MapObject {
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	
	// position and vector
	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected float xdest;
	protected float ydest;
	protected float xtemp;
	protected float ytemp;
	protected int leftTile;
	protected int rightTile;
	protected int topTile;
	protected int bottomTile;
	protected boolean topCollision;
	protected boolean leftCollision;
	protected boolean rightCollision;
	protected boolean bottomCollision;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	
	public enum Direction { LEFT, RIGHT, UP, DOWN; };
	protected Direction lastDirection;
	
	// movement attributes
	protected float moveSpeed;
	protected float maxSpeed;
	protected float stopSpeed;
	
	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		animation = new Animation();
	}
	
	protected void setAnimation(TextureRegion[] images, float interval, int numFrames) {
		animation.setImages(images);
		animation.setInterval(interval);
		animation.setNumFrames(numFrames);
		width = images[0].getRegionWidth();
		height = images[0].getRegionHeight();
	}
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}
	
	public boolean contains(int x, int y) {
		return getRectangle().contains(x, y);
	}
	
	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}
	
	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)(x - cwidth / 2),
				(int)(y - cheight / 2),
				cwidth,
				cheight
		);
	}
	
	private void calculateCollision(double x, double y) {
		topCollision = leftCollision = rightCollision = bottomCollision = false;
		int xl = (int) (x - cwidth / 2);
		int xr = (int) (x + cwidth / 2 - 1);
		int yt = (int) (y + cheight / 2 - 1);
		int yb = (int) (y - cheight / 2);
		leftTile = xl / tileSize;
		rightTile = xr / tileSize;
		topTile = yt / tileSize;
		bottomTile = yb / tileSize;
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
			leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			return;
		}
		for(int i = 0; i < rightTile - leftTile + 1; i++) {
			topCollision |= tileMap.getType(topTile, leftTile + i) == TileMap.BLOCKED;
			bottomCollision |= tileMap.getType(bottomTile, leftTile + i) == TileMap.BLOCKED;
		}
		for(int i = 0; i < topTile - bottomTile + 1; i++) {
			leftCollision |= tileMap.getType(bottomTile + i, leftTile) == TileMap.BLOCKED;
			rightCollision |= tileMap.getType(bottomTile + i, rightTile) == TileMap.BLOCKED;
		}
	}
	
	protected boolean checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		boolean collision = false;
		
		calculateCollision(x, ydest);
		if(dy > 0) {
			if(topCollision) {
				dy = 0;
				ytemp = (topTile) * tileSize - cheight / 2;
				collision = true;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy < 0) {
			if(bottomCollision) {
				dy = 0;
				ytemp = (bottomTile + 1) * tileSize + cheight / 2;
				collision = true;
			}
			else {
				ytemp += dy;
			}
		}
		
		calculateCollision(xdest, y);
		if(dx < 0) {
			if(leftCollision) {
				dx = 0;
				xtemp = (leftTile + 1) * tileSize + cwidth / 2;
				collision = true;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(rightCollision) {
				dx = 0;
				xtemp = rightTile * tileSize - cwidth / 2;
				collision = true;
			}
			else {
				xtemp += dx;
			}
		}
		
		return collision;
		
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public double getdx() { return dx; }
	public double getdy() { return dy; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	public int getRow() { return (int) (y / tileMap.getTileSize()); }
	public int getCol() { return (int) (x / tileMap.getTileSize()); }
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public void setCollision(int w, int h) {
		cwidth = w;
		cheight = h;
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	public void setDirection(Direction d) { lastDirection = d; }
	
	public abstract void update(float dt);
	
	public void render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(animation.getImage(), (int) (x - width / 2), (int) (y - height / 2), width, height);
	}
	
}
