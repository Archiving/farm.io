package com.scroll.game.state;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scroll.game.tech.Tech;

public class TechnologyState extends State{

	public State previousState;
	public Stack<Tech> techTree;
	
	protected TechnologyState(GSM gsm, State previousState) {
		super(gsm);
		this.previousState = previousState;
		
		
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		sb.begin();
		
		sb.end();
	}

	@Override
	public void update(float dt) {
		
	}
	
}
