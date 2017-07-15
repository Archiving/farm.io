package com.scroll.game.state;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GSM {

	public Stack<State> states;
	
	public GSM() {
		states = new Stack<State>();
	}
	
	public void set(State s) {
		states.pop();
		states.push(s);
	}
	
	public void push(State s) {
		states.push(s);
	}
	
	public void pop() {
		states.pop();
	}
	
	public void render(SpriteBatch sb) {
		states.peek().render(sb);
	}
	
	public void update(float dt) {
		states.peek().update(dt);
	}
}
