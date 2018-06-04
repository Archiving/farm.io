package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.scroll.game.handler.Asset;
import com.scroll.game.ui.GuideBlock;

public class RegionHelpState extends State {

	private GuideBlock[] guides;
	private State previousState;
	private int x, y;
	private BitmapFont largeFont;
	
	public RegionHelpState(GSM gsm, GuideBlock[] guides, State previousState, int x, int y) {
		super(gsm);
		this.guides = guides;
		this.previousState = previousState;
		this.x = x;
		this.y = y;
		largeFont = Asset.instance().getFont("large_font");
		
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setColor(Color.BLACK);
		renderer.begin(ShapeType.Filled);
		renderer.rect(x,y,1200,750);
		renderer.end();
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.line(x,y, x+1200,y);
		renderer.line(x,y,x,y+750);
		renderer.line(x,y + 750, x+1200, y+750);
		renderer.line(x+1200, y+750, x+1200, y);
		renderer.end();
		sb.begin();
		for(int i = 0; i < guides.length; i++) {
			guides[i].draw(sb);
		}

		largeFont.draw(sb, "Welcome to farm.io!", 250, 850);
		sb.end();
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) gsm.pop();
	}

}
