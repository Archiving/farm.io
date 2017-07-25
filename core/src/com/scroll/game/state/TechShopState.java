package com.scroll.game.state;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.scroll.game.ui.UIButton;

public class TechShopState extends State {

	private List<UIButton> buttons;
	private State previousState;
	
	public TechShopState(GSM gsm, State previousState) {
		super(gsm);
		this.previousState = previousState;
		buttons = new ArrayList<>();
	
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeType.Filled);
		renderer.rect(50, 100, 1400, 750);
		renderer.end();
		
		sb.begin();
		for(UIButton button : buttons) {
			button.draw(sb);
		}
		sb.end();
	}

	@Override
	public void update(float dt) {
		Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(pos);
		for(UIButton button : buttons) {
			TextureRegion tmp = button.image;
			if(button.getBounds().contains(new Point((int)pos.x, (int)pos.y))) {
				if(Gdx.input.isTouched()) {
					button.click();
				}
				else if(!Gdx.input.isTouched()) {
					button.clicked = false;
				}
			}
			if(!button.clicked) button.image = tmp;
			
		}
		
	}

	
	
}
