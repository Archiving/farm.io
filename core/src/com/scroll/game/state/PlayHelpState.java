package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.handler.Asset;
import com.scroll.game.ui.GuideBlock;

public class PlayHelpState extends State {

	private GuideBlock[] guides;
	private State previousState;
	private int x, y;
	private BitmapFont font;
	
	protected PlayHelpState(GSM gsm, GuideBlock[] guides, State previousState, int x, int y) {
		super(gsm);
		this.guides = guides;
		this.previousState = previousState;
		((PlayState)previousState).music.pause();
		this.x = x;
		this.y = y;
		font = Asset.instance().getFont("med_font");
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(x,y,1400,750);
		renderer.end();
		
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.line(x,y,x+1400,y);
		renderer.line(x,y,x,y+750);
		renderer.line(x+1400,y,x+1400,y+750);
		renderer.line(x,y+750,x+1400,y+750);
		renderer.end();

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		for(int i = 0; i < guides.length; i++) {
			guides[i].draw(sb);
		}
		
		font.draw(sb, "Welcome to farm.io!", 170, 380);
		
		sb.end();
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) gsm.push(new PlayState(gsm, (PlayState)previousState, ((PlayState)previousState).getPlayer().getMoney()));
	}

}
