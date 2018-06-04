package com.scroll.game.state;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.Var;
import com.scroll.game.handler.Asset;
import com.scroll.game.handler.XmlDeserializer;
import com.scroll.game.state.entities.Player;
import com.scroll.game.tech.Root;
import com.scroll.game.tech.Tech;
import com.scroll.game.tech.Tech.TechType;

public class TechnologyState extends State {

	public State previousState;
	public Tech[][] techTree;
	public int x, y;
	public Tech selectedTech;
	public int row, col;
	public int maxCol;
	public BitmapFont medFont;
	public BitmapFont smallFont;
	public TextureRegion pixel;
	public String popupText;
	public boolean showingPopup;
	public TextureRegion lock;
	
	protected TechnologyState(GSM gsm, State previousState, int x, int y) {
		super(gsm);
		this.x = x;
		this.y = y;
		this.previousState = previousState;

		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));
		lock = new TextureRegion(Asset.instance().getTexture("lock"));
		
		techTree = new Tech[5][5];

		try {
			Root r = (Root) XmlDeserializer.deserialize("tech", Root.class);
			Tech[] techs = r.getTech();
			for (int i = 0; i < techs.length; i++) {

				techs[i].setRow(techs[i].getType().index - 1);
				techs[i].setCol(Integer.parseInt(techs[i].getImage().replaceAll("([^0-9])+", "")) - 1);
				
				
				techTree[techs[i].getRow()][techs[i].getCol()] = techs[i];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		selectedTech = techTree[0][0];
		
		row = selectedTech.getRow();
		col = selectedTech.getCol();

		maxCol = techTree[selectedTech.getCol()].length;

		smallFont = Asset.instance().getFont("small_font");
		medFont = Asset.instance().getFont("med_font");

	}

	@Override
	public void render(SpriteBatch sb) {
		
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(x, y, 1300, 800);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.line(x, y, x + 1300, y);
		renderer.line(x, y, x, y + 800);
		renderer.line(x + 1300, y, x + 1300, y + 800);
		renderer.line(x, y + 800, x + 1300, y + 800);
		renderer.end();

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		medFont.draw(sb, "Technology", 265, 415);
		for (int y = 0; y < techTree.length; y++) {
			for (int x = 0; x < techTree[y].length; x++) {
				if (techTree[y][x] != null) {
					TextureRegion image = new TextureRegion(Asset.instance().getTexture(techTree[y][x].getImage()), 16,
							16);

					int posX = this.x + 85 * x;
					int posY = 260 + this.y - 80 * y;
					if (!techTree[y][x].isUnlocked() && !techTree[y][x].isSelected() && !techTree[y][x].canResearch(techTree)) sb.setColor(Color.GRAY);
					sb.draw(image, posX, posY, 16, 16);
					sb.setColor(1, 1, 1, 1);
					if(!techTree[y][x].isUnlocked() && !techTree[y][x].canResearch(techTree)) sb.draw(lock, posX, posY);
					String name = techTree[y][x].getName();
					String cost = "Cost: $" + techTree[y][x].getCost();
		
					BigDecimal d = new BigDecimal(techTree[y][x].getTime()/24);
					String time = d.setScale(1, RoundingMode.DOWN) + " days";
					
					if(techTree[y][x].canResearch(techTree)) {
						smallFont.setColor(Color.GREEN);
					}
					smallFont.draw(sb, name, posX - (name.length() * smallFont.getSpaceWidth()) / 2, posY - 5);
					smallFont.setColor(Color.WHITE);
					smallFont.draw(sb, cost, posX - (cost.length() * smallFont.getSpaceWidth()) / 2, posY - 15);
					smallFont.draw(sb, time, posX - (time.length() * smallFont.getSpaceWidth()) / 2, posY - 25);
					
					if ((x + 1) < techTree[y].length) {
						int nextX = this.x + 85 * (x + 1);
						sb.draw(pixel, posX + 16, posY + 8, nextX - posX, 1);
					}
				}
			}
		}
		int posx = this.x + 85 * (col);
		int posy = 260 + this.y - 80 * (row);
		
		sb.setColor(Color.YELLOW);
		if(techTree[row][col].canResearch(techTree)) {
			sb.setColor(Color.GREEN);
		}
		sb.draw(pixel, posx, posy, 16, 1);
		sb.draw(pixel, posx + 16, posy, 1, 17);
		sb.draw(pixel, posx, posy + 16, 16, 1);
		sb.draw(pixel, posx, posy, 1, 16);
		sb.setColor(Color.WHITE);
		if (showingPopup) {
			sb.setColor(Color.BLACK);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 200, 50);
			sb.setColor(Color.WHITE);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 200, 1);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 1, 50);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 + 25, 200, 1);
			sb.draw(pixel, Var.WIDTH / 2 + 100, Var.HEIGHT / 2 - 25, 1, 50);
			smallFont.draw(sb, popupText, (Var.WIDTH - popupText.length() * smallFont.getSpaceWidth())/2 - 5, 254);
		}

		sb.end();
	}

	@Override
	public void update(float dt) {

		if (showingPopup) {
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				showingPopup = false;
				Asset.instance().getSound("close").play();
			}
		} else {

			if (techTree[row][col] == selectedTech)
				techTree[row][col].setSelected(true);

			if (Gdx.input.isKeyJustPressed(Keys.UP)) {
				Asset.instance().getSound("select").play(0.7f);
				if (row > 0) {
					techTree[row][col].setSelected(false);
					row--;
					techTree[row][col].setSelected(true);
				} else {
					techTree[row][col].setSelected(false);
					row = TechType.values().length - 1;
					techTree[row][col].setSelected(true);
				}
			}

			if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
				Asset.instance().getSound("select").play(0.7f);
				if (row < TechType.values().length - 1) {
					techTree[row][col].setSelected(false);
					row++;
					techTree[row][col].setSelected(true);
				} else {
					techTree[row][col].setSelected(false);
					row = 0;
					techTree[row][col].setSelected(true);
				}
			}

			if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
				Asset.instance().getSound("select").play(0.7f);
				if (col < maxCol - 1) {
					techTree[row][col].setSelected(false);
					col++;
					techTree[row][col].setSelected(true);
				} else {
					techTree[row][col].setSelected(false);
					col = 0;
					techTree[row][col].setSelected(true);
				}
			}

			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
				Asset.instance().getSound("select").play(0.7f);
				if (col > 0) {
					techTree[row][col].setSelected(false);
					col--;
					techTree[row][col].setSelected(true);
				} else {
					techTree[row][col].setSelected(false);
					col = maxCol - 1;
					techTree[row][col].setSelected(true);
				}
			}

			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				Player p = ((PlayState) previousState).getPlayer();
				
				boolean ok = p.buyTech(techTree[row][col]);
				boolean inProgress = techTree[row][col].isInProgress();
				boolean unlocked = techTree[row][col].isUnlocked();
				boolean canResearch = techTree[row][col].canResearch(techTree);
				
				if (ok) {
					// successful purchase
					System.out.println(p.getMoney());
					Var.PLAYER_MONEY = p.getMoney();
					Asset.instance().getSound("purchase").play(0.5f);
					techTree[row][col].setProgress(true);
					popupText = "Bought research!";
					showingPopup = true;
					((PlayState)previousState).setPlayer(p);
				} else {
					popupText = "Not enough money!";
					if(inProgress) popupText = "Already researching!";
					if(unlocked) popupText = "Already unlocked!";
					if(!canResearch) popupText = "Missing required tech!";
					showingPopup = true;
				}
			}

			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) && !showingPopup) {
				gsm.push(new PlayState(gsm, (PlayState) previousState, techTree));
			}
		}
	}

}
