package com.scroll.game.state;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.scroll.game.Var;
import com.scroll.game.farm.Seed;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.entities.Player;

public class SeedShopState extends State {

	private Player player;
	private TextureRegion pixel;
	private BitmapFont smallFont;
	private BitmapFont medFont;
	private Seed.Type[][] crops;

	private boolean showingPopup;
	private String popupText;

	private int currentRow;
	private int currentCol;

	private State previousState;
	private Seed.Region selectedRegion;

	protected SeedShopState(GSM gsm, State previousState, Player player, Seed.Region selectedRegion) {
		super(gsm);
		this.player = player;
		this.previousState = previousState;
		this.selectedRegion = selectedRegion;
		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));

		crops = new Seed.Type[2][7];

		crops[0][0] = Seed.Type.WHEAT;
		crops[0][1] = Seed.Type.CARROT;
		crops[0][2] = Seed.Type.CHILI;
		crops[0][3] = Seed.Type.POTATO;
		crops[0][4] = Seed.Type.OLIVE;
		crops[0][5] = Seed.Type.CORN;
		crops[0][6] = Seed.Type.COCOA;

		crops[1][0] = Seed.Type.TEA;
		crops[1][1] = Seed.Type.CINNAMON;
		crops[1][2] = Seed.Type.RICE;
		crops[1][3] = Seed.Type.PEPPER;
		crops[1][4] = Seed.Type.COFFEE;
		crops[1][5] = Seed.Type.SUGARCANE;
		crops[1][6] = Seed.Type.TOMATO;

		smallFont = Asset.instance().getFont("small_font");
		medFont = Asset.instance().getFont("med_font");
	}

	@Override
	public void update(float dt) {
		if (showingPopup) {
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				showingPopup = false;
				Asset.instance().getSound("close").play();
			}
		} 
		else {
			if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
				if (currentCol < 6) {
					Asset.instance().getSound("select").play(0.7f);
					currentCol++;
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {

				if (currentCol > 0) {
					Asset.instance().getSound("select").play(0.7f);
					currentCol--;
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.UP)) {
				if (currentRow > 0) {
					Asset.instance().getSound("select").play(0.7f);
					currentRow--;
				}
			}
			if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
				if (currentRow < 1) {
					Asset.instance().getSound("select").play(0.7f);
					currentRow++;
				}
				
			}

			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				Asset.instance().getSound("close").play();
				gsm.pop();
			}

			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {

				boolean ok = false;
				Seed.Type purchase = crops[currentRow][currentCol];
				ok = player.buySeed(purchase);
				if (ok) {
					Asset.instance().getSound("purchase").play(0.5f);
					popupText = "Bought item!";
					showingPopup = true;
				} else {
					Asset.instance().getSound("select_fail").play(0.8f);
					popupText = "Not enough money!";
					showingPopup = true;
				}

			}
		}

	}

	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(50, 100, 1400, 750);
		renderer.end();

		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.setColor(Color.WHITE);
		medFont.draw(sb, "Farmer's Seed Delivery", 100, 400);
		for (int row = 0; row < crops.length; row++) {
			for (int col = 0; col < crops[0].length; col++) {
				sb.draw(crops[row][col].cropItem, 180 + 67 * col, 265 - 67 * row);
				smallFont.draw(sb, "$" + crops[row][col].cost, 180 + 67 * col - ((crops[row][col].cost >= 10) ? 5 : 0)
						- ((crops[row][col].cost >= 100) ? 5 : 0), 265 - 65 * row);
				smallFont.draw(sb,
						crops[currentRow][currentCol].toString().substring(0, 1)
								+ crops[currentRow][currentCol].toString().substring(1).toLowerCase() + " Seed",
						335, 360);
				if (Arrays.asList(selectedRegion.affectedCrops).contains(crops[currentRow][currentCol])) {
					smallFont.draw(sb, "Growth Time: ~"
							+ (int) (crops[currentRow][currentCol].requiredTime / selectedRegion.timeRatio) + " hours",
							290, 345);
					smallFont.draw(sb, "Special Crop!", 325, 330);
					medFont.setColor(Color.GREEN);
				} else {
					smallFont.draw(sb, "Growth Time: ~" + (int) (crops[currentRow][currentCol].requiredTime) + " hours",
							290, 345);
					medFont.setColor(Color.WHITE);
				}
			}
		}

		sb.draw(pixel, 178 + currentCol * 67 - 8, 300 - currentRow * 67 - 16, 32, 1);
		sb.draw(pixel, 178 + currentCol * 67 - 8, 300 - currentRow * 67 - 16 - 32, 32, 1);
		sb.draw(pixel, 178 + currentCol * 67 - 8, 300 - currentRow * 67 - 16 - 32, 1, 32);
		sb.draw(pixel, 178 + currentCol * 67 - 8 + 32, 300 - currentRow * 67 - 16 - 32, 1, 33);

		if (showingPopup) {
			sb.setColor(Color.BLACK);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 200, 50);
			sb.setColor(Color.WHITE);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 200, 1);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 - 25, 1, 50);
			sb.draw(pixel, Var.WIDTH / 2 - 100, Var.HEIGHT / 2 + 25, 200, 1);
			sb.draw(pixel, Var.WIDTH / 2 + 100, Var.HEIGHT / 2 - 25, 1, 50);
			smallFont.draw(sb, popupText, 312, 254);
		}
		sb.end();
	}

}
