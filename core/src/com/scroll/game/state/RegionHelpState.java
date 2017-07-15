package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.handler.Asset;
import com.scroll.game.ui.GuideBlock;

public class RegionHelpState extends State {

	private GuideBlock[] guides;
	private State previousState;
	private TextureRegion frame;
	private int x, y;
	
	public RegionHelpState(GSM gsm, GuideBlock[] guides, State previousState, int x, int y) {
		super(gsm);
		this.guides = guides;
		this.previousState = previousState;
		this.x = x;
		this.y = y;
		
		frame = new TextureRegion(Asset.instance().getTexture("sign"));
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		sb.begin();
		sb.draw(frame, x, y);
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
