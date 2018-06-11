package com.scroll.game.state.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.Var;
import com.scroll.game.farm.Crop;
import com.scroll.game.farm.Patch;
import com.scroll.game.farm.Pipe;
import com.scroll.game.farm.Pipe.Form;
import com.scroll.game.farm.Seed;
import com.scroll.game.farm.Seed.Region;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.tile.MapObject;
import com.scroll.game.state.tile.TileMap;
import com.scroll.game.tech.Tech;

public class Player extends MapObject {

	private Patch[][] farm;
	private List<Crop> crops;
	private List<Seed.Type> seeds;
	private List<Pipe.Form> pipes;
	
	private Seed.Type nextSeedType;
	private Pipe.Form nextPipe;
	private Patch selectedPatch;
	private int row;
	private int col;
	
	private Action action;
	private int actionRow;
	private int actionCol;
	private float actionTime;
	private float actionTimeRequired;
	
	private TextureRegion[] leftWalkAnimation;
	private TextureRegion[] rightWalkAnimation;
	private TextureRegion[] upWalkAnimation;
	private TextureRegion[] downWalkAnimation;
	
	private TextureRegion[] leftIdleAnimation;
	private TextureRegion[] rightIdleAnimation;
	private TextureRegion[] upIdleAnimation;
	private TextureRegion[] downIdleAnimation;
	
	private Seed.Type selectedSeed;
	private int money;
	private Region region;
	
	private Tech currentResearch;
	
	public enum Action {
		TILLING(0.5f),
		WATERING(0.5f),
		SEEDING(0.5f),
		HARVESTING(0.2f),
		PIPING(0.2f);
		public float timeRequired;
		Action(float timeRequired) {
			this.timeRequired = timeRequired;
		}
	};
	
	public Player(TileMap tm, Region region) {
		super(tm);
		this.region = region;
		Texture sheet = Asset.instance().getTexture("player");
		TextureRegion[][] split = TextureRegion.split(sheet, 16, 16);
		downWalkAnimation = split[0];
		leftWalkAnimation = split[1];
		rightWalkAnimation = split[2];
		upWalkAnimation = split[3];
		
		downIdleAnimation = split[4];
		leftIdleAnimation = split[5];
		rightIdleAnimation = split[6];
		upIdleAnimation = split[7];
		
		lastDirection = Direction.DOWN;
		setAnimation(downIdleAnimation, 0, 1);
		cwidth = 12;
		cheight = 12;
		moveSpeed = 100;
		crops = new ArrayList<>();
		seeds = new ArrayList<>();
		pipes = new ArrayList<>();
		for(int i = 0; i < Var.NUM_SEEDS_START; i++) {
			Random number = new Random();
			seeds.add(region.affectedCrops[number.nextInt(region.affectedCrops.length)]);
		}
		
	}
	
	public void setFarm(Patch[][] farm) {
		this.farm = farm;
	}
	
	
	public void actionStarted(Action action, int actionRow, int actionCol) {
		this.action = action;
		this.actionRow = actionRow;
		this.actionCol = actionCol;
		actionTime = 0;
		actionTimeRequired = action.timeRequired;
	}
	
	public void actionFinished() {
		switch(action) {
		case TILLING:
			farm[actionRow][actionCol].till();
			break;
		case WATERING:
			farm[actionRow][actionCol].water();
			break;
		case SEEDING:
			farm[actionRow][actionCol].seed(new Seed(nextSeedType,
					tileSize * ((int) x / tileSize + 1.0f),
					tileSize * ((int) y / tileSize + 1.0f),
					32, 32, region));
			seeds.remove(0);
			break;
		case HARVESTING:
			Crop crop = farm[actionRow][actionCol].harvest();
			if(crop != null) {
				crops.add(crop);
			}
			break;
		case PIPING:
			Pipe pipe = new Pipe(nextPipe, this, actionRow, actionCol,
					tileSize * ((int)x / tileSize),
					tileSize * ((int)y / tileSize),
					32, 32);
			farm[actionRow][actionCol].pipe(pipe);
			pipes.remove(0);
			break;
		}
		action = null;
	}
	
	private void getCurrentTile() {
		row = (int) (tileMap.getNumRows() - (y / tileSize));
		col = (int) (x / tileSize);
		row -= 3;
		col -= 11;
	}
	
	public Patch[][] getFarm() { return farm; }
	
	public void till() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canTill()) {
			Asset.instance().getSound("till").play(0.5f);
			actionStarted(Action.TILLING, row, col);
		}
	}
	
	public void water() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canWater()) {
			Asset.instance().getSound("water").play(0.5f);
			actionStarted(Action.WATERING, row, col);
		}
	}
	
	public void seed() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		nextSeedType = (seeds.isEmpty() || farm[row][col].hasSeed() || farm[row][col].getState() == Patch.State.NORMAL) ? null : seeds.get(0);
		if(action == null && farm[row][col].canSeed() && nextSeedType != null) {
			Asset.instance().getSound("till").play(0.5f);
			actionStarted(Action.SEEDING, row, col);
		}
	}
	
	public void harvest() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canHarvest()) {
			Asset.instance().getSound("till").play(0.5f);
			actionStarted(Action.HARVESTING, row, col);
		}
	}
	
	public void pipe() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		
		System.out.println(farm[row][col].getState() == Patch.State.NORMAL);
		nextPipe = (pipes.isEmpty() || farm[row][col].hasPipe() || farm[row][col].getState() != Patch.State.NORMAL) ? null : pipes.get(0);
		if(action == null && farm[row][col].canPipe() && nextPipe != null) {
			actionStarted(Action.PIPING, row, col);
		}
		
	}
	
	public boolean sell() {
		if(!crops.isEmpty()) {	
			Asset.instance().getSound("purchase").play(0.5f);
			int result = 0;
			for(int i = 0; i < crops.size(); i++) {
				result += crops.get(i).getValue();
			}
			crops.clear();
			money += result;
			return true;
		}
		return false;	
	}
	
	public boolean buySeed(Seed.Type purchase) {
		if(this.money - purchase.cost < 0) return false;
		this.money -= purchase.cost;
		seeds.add(purchase);
		return true;
	}
	
	public int getNumCrops() {
		return crops.size();
	}
	
	public Action getAction() {
		return action;
	}
	
	private void highlightPatch() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 11;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			selectedPatch = null;
			return;
		}
		selectedPatch = farm[row][col];
	}
	
	@Override
	public void update(float dt) {
		//Selected seed is the first in the array.
		if(seeds.size() > 0) selectedSeed = seeds.get(0);
		else { selectedSeed = null; }
		if(action != null) {
			dx = 0;
			dy = 0;
			actionTime += dt;
			if(actionTime >= actionTimeRequired) {
				actionFinished();
			}
		}
		else {
			if(left) {
				dx = -moveSpeed * dt;
			}
			else if(right) {
				dx = moveSpeed * dt;
			}
			else {
				dx = 0;
			}
			if(down) {
				dy = -moveSpeed * dt;
			}
			else if(up) {
				dy = moveSpeed * dt;
			}
			else {
				dy = 0;
			}
		}
		checkTileMapCollision();
		x = xtemp;
		y = ytemp;
		highlightPatch();
		
		//animation check
		if(action == null) {
		if(left) {
			if(animation.getAnimation() != leftWalkAnimation) setAnimation(leftWalkAnimation, 0.1f, 3);
		}
		else if(right) {
			if(animation.getAnimation() != rightWalkAnimation) setAnimation(rightWalkAnimation, 0.1f, 3);
		}
		else if(up) {
			if(animation.getAnimation() != upWalkAnimation) setAnimation(upWalkAnimation, 0.1f, 3);
		}
		else if(down) {
			if(animation.getAnimation() != downWalkAnimation) setAnimation(downWalkAnimation, 0.1f, 3);
		}
		//if not left, right, up, or down, that means idle.
		else {
			//check idle direction
			switch(lastDirection) {
				case LEFT:
					if(animation.getAnimation() != leftIdleAnimation) setAnimation(leftIdleAnimation, 0, 1);
					break;
				case RIGHT:
					if(animation.getAnimation() != rightIdleAnimation) setAnimation(rightIdleAnimation, 0, 1);
					break;
				case UP:
					if(animation.getAnimation() != upIdleAnimation) setAnimation(upIdleAnimation, 0, 1);
					break;
				case DOWN:
					if(animation.getAnimation() != downIdleAnimation) setAnimation(downIdleAnimation, 0, 1);
					break;
			}
		}
		}
		animation.update(dt);
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		if(selectedPatch != null) {
			selectedPatch.renderHighlight(sb);
		}
	}
	
	public Seed.Type getSelectedSeed() { return selectedSeed; }
	
	public void increaseMoney(int incr) { money += incr; }
	public void decreaseMoney(int decr) { money -= decr; }
	public int getMoney() { return money; }	
	
	public List<Seed.Type> getSeedInventory() { return seeds; }

	public boolean buyPipe(Form purchase) {
		if(this.money - purchase.cost < 0) return false;
		this.money -= purchase.cost;
		pipes.add(purchase);
		return true;
	}
	
	public boolean buyTech(Tech purchase) {
		if(this.money - purchase.getCost() < 0) return false;
		this.money -= purchase.getCost();
		purchase.setProgress(true);
		return true;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
