package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.Var;
import com.scroll.game.handler.Asset;

public class WarningState extends State {

	private State playState;
	private TextureRegion pixel;
	private BitmapFont font;
	
	protected WarningState(GSM gsm, State playState) {
		super(gsm);
		this.playState = playState;
		((PlayState)playState).music.pause();
		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));
		font = Asset.instance().getFont("small_font");
	}

	@Override
	public void render(SpriteBatch sb) {
		playState.render(sb);
		sb.begin();
		sb.setColor(Color.BLACK);
		sb.draw(pixel, Var.WIDTH / 2 - 200, Var.HEIGHT / 2 - 50, 400, 100);
		sb.setColor(Color.WHITE);
		sb.draw(pixel, Var.WIDTH / 2 - 200, Var.HEIGHT / 2 - 50, 400, 1);
		sb.draw(pixel, Var.WIDTH / 2 - 200, Var.HEIGHT / 2 - 50, 1, 100);
		sb.draw(pixel, Var.WIDTH / 2 - 200, Var.HEIGHT / 2 + 50, 400, 1);
		sb.draw(pixel, Var.WIDTH / 2 + 200, Var.HEIGHT / 2 - 50, 1, 100);
		font.draw(sb, "Are you sure you want to quit the campaign?", 225, 275);
		font.setColor(Color.GREEN);
		font.draw(sb, "SPACE to RETURN to campaign", 272, 250);
		font.setColor(Color.RED);
		font.draw(sb, "Press ESC to LEAVE campaign", 274, 235);
		font.setColor(Color.WHITE);
		sb.end();
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			gsm.push(new PlayState(gsm, (PlayState)playState));
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			((PlayState)playState).music.stop();
			gsm.push(new RegionState(gsm, ((PlayState)playState).getPlayer().getMoney(), ((PlayState)playState).getRegion()));
		}
	}

}
