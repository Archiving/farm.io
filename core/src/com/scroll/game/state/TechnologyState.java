package com.scroll.game.state;

import java.util.Arrays;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.handler.XmlDeserializer;
import com.scroll.game.tech.Tech;
import com.scroll.game.tech.Tech.TechType;

public class TechnologyState extends State{
	
	public State previousState;
	public Stack<Tech> techTree;
	public int x,y;
	public Tech selectedTech;
	public int row, col;
	public int maxCol;
	public int[] values;
	
	protected TechnologyState(GSM gsm, State previousState) {
		super(gsm);
		this.previousState = previousState;
		try {
			Tech[] techs = (Tech[])XmlDeserializer.deserialize("tech", Tech.class);
			for(int i = 0; i < techs.length; i++) {
				techTree.push(techs[i]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		row = selectedTech.getRow();
		col = selectedTech.getCol();
		
		for(int i = 0; i < techTree.size(); i++) {
			values[i] = techTree.get(i).getCol();
		}
		Arrays.sort(values);
		maxCol = values[values.length-1];
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
		
		sb.end();
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if(row > 0) {
				row--;
			}
			else {
				row = TechType.values().length;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)) {
			if(row < TechType.values().length) {
				row++;
			}
			else {
				row = 1;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if(col < maxCol) {
				col++;
			}
			else {
				col = 0;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if(col > 0) {
				col--;
			}
			else {
				col = maxCol;
			}
		}
	}
	
}
