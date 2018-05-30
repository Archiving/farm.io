package com.scroll.game.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.handler.Asset;
import com.scroll.game.handler.XmlDeserializer;
import com.scroll.game.tech.Root;
import com.scroll.game.tech.Tech;
import com.scroll.game.tech.Tech.TechType;

public class TechnologyState extends State{
	
	public State previousState;
	public Stack<Tech> techTree;
	public int x,y;
	public Tech selectedTech;
	public int row, col;
	public int maxCol;
	public BitmapFont medFont;
	public BitmapFont smallFont;
	
	protected TechnologyState(GSM gsm, State previousState, int x, int y) {
		super(gsm);
		this.x = x;
		this.y = y;
		this.previousState = previousState;
		
		techTree = new Stack<>();
		
		try {
			Root r = (Root)XmlDeserializer.deserialize("tech", Root.class);
			Tech[] techs = r.getTech();
			for(int i = 0; i < techs.length; i++) {
				
				techs[i].setRow(techs[i].getType().index);
				techs[i].setCol(Integer.parseInt(techs[i].getImage().replaceAll("([^0-9])+", "")));
				techTree.push(techs[i]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		selectedTech = techTree.get(0);
		
		row = selectedTech.getRow();
		col = selectedTech.getCol();
		
		ArrayList<Integer> values = new ArrayList<>();
		
		for(int i = 0; i < techTree.size(); i++) {
			values.add(i, techTree.get(i).getCol());
		}
		Collections.sort(values);
		maxCol = values.get(values.size()-1);
		
		smallFont = Asset.instance().getFont("small_font");
		medFont = Asset.instance().getFont("med_font");
		
	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(x,y,1300,800);
		renderer.end();
		
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.line(x,y,x+1300,y);
		renderer.line(x,y,x,y+800);
		renderer.line(x+1300,y,x+1300,y+800);
		renderer.line(x,y+800,x+1300,y+800);
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
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			gsm.push(new PlayState(gsm, (PlayState)previousState));
		}
	}
	
}
