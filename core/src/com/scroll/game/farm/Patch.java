package com.scroll.game.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.tile.TileMap;

public class Patch {

	private TextureRegion patchBorder;
	
	public enum State {
		NORMAL(new TextureRegion(Asset.instance().getTexture("plot_state.normal"))),
		TILLED(new TextureRegion(Asset.instance().getTexture("plot_state.tilled"))),
		WATERED(new TextureRegion(Asset.instance().getTexture("plot_state.watered")));
		
		public TextureRegion image;
		private State(TextureRegion image) {
			this.image = image;
		}
	}
	
	private State state;
	private float x,y,w,h;
	
	private Seed seed;
	private Crop crop;
	private Pipe pipe;
	
	private TextureRegion image;
	
	public Patch(TileMap tm, int row, int col) {
		state = State.NORMAL;
		int tileSize = tm.getTileSize();
		x = tileSize * (col + 0.5f);
		y = tileSize * (tm.getNumRows() - 1 - row + 0.5f);
		w = h = tileSize;
		image = state.image;

		patchBorder = new TextureRegion(Asset.instance().getTexture("borderImage"));
	}

	public boolean canSeed() { return state != State.NORMAL && seed == null && crop == null; }
	public boolean canTill() { return state == State.NORMAL && crop == null; }
	public boolean canWater() { return state == State.TILLED && crop == null; }
	public boolean canCrop() { return seed != null; }
	public boolean canHarvest() { return crop != null; }
	public boolean canPipe() { return state == State.NORMAL; }
	
	public void till() {
		if(canTill()) {
			state = State.TILLED;
			image = state.image;
		}
	}
	
	public void water() {
		if(canWater()) {
			state = State.WATERED;
			image = state.image;
			if(seed != null) {
				seed.setWatered();
			}
		}
	}
	
	public boolean seed(Seed seed) {
		if(canSeed()) {
			this.seed = seed;
			if(state == State.WATERED) seed.setWatered();
			return true;
		}
		return false;
	}
	
	public boolean pipe(Pipe pipe) {
		if(canPipe()) {
			this.pipe = pipe;
			return true;
		}
		return false;
	}
	
	private void crop() {
		if(canCrop()) {
			state = State.NORMAL;
			image = state.image;
			crop = seed.getCrop();
			seed = null;
		}
	}
	
	
	public Crop harvest() {
		Crop ret = null;
		if(canHarvest()) {
			image = state.image;
			ret = crop;
			crop = null;
		}
		return ret;
	}
	
	public boolean hasPipe() { return pipe != null; }
	
	public boolean hasSeed() { return seed != null; }
	public State getState() { return state; }
	
	public void update(float dt) {
		if(seed != null) {
			seed.update(dt);
			if(seed.isFinished()) {
				crop();
			}
		}
	}
	
	public void renderHighlight(SpriteBatch sb) {
		sb.draw(patchBorder, x - w / 2, y - h / 2);
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - w / 2, y - h / 2);
		if(seed != null) {
			seed.render(sb);
		}
		if(crop != null) {
			crop.render(sb);
		}
		if(pipe != null) {
			pipe.render(sb);
		}
	}
	
}
