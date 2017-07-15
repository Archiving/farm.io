package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.ui.GuideBlock;

public class PlayHelpState extends State {

	private GuideBlock[] guides;
	private State previousState;
	private int x, y;
	
	protected PlayHelpState(GSM gsm, GuideBlock[] guides, State previousState, int x, int y) {
		super(gsm);
		this.guides = guides;
		this.previousState = previousState;
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(x,y,1400,750);
		renderer.end();

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		for(int i = 0; i < guides.length; i++) {
			guides[i].draw(sb);
		}
		sb.end();
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) gsm.pop();
	}

}
