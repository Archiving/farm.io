package com.scroll.game.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scroll.game.Var;

public abstract class State {

	protected GSM gsm;
	protected OrthographicCamera cam;
	
	protected State(GSM gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Var.WIDTH, Var.HEIGHT);
	}
	
	public abstract void render(SpriteBatch sb);
	public abstract void update(float dt);
}
