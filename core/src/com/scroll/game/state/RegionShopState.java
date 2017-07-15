package com.scroll.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.Var;
import com.scroll.game.handler.Asset;
import com.scroll.game.ui.LocationButton;

public class RegionShopState extends State {

	public RegionShopState(GSM gsm) {
		super(gsm);
	}

	public State previousState;
	public TextureRegion sign;
	public BitmapFont font;
	public BitmapFont smFont;
	public LocationButton selectedButton;
	public int currentMoney;
	public boolean popup = false;
	public String popupText;
	public TextureRegion pixel;
	
	public RegionShopState(GSM gsm, State previousState, LocationButton selectedButton, int currentMoney) {
		super(gsm);
		this.previousState = previousState;
		this.selectedButton = selectedButton;
		this.currentMoney = currentMoney;
		
		smFont = Asset.instance().getFont("small_font");
		font = Asset.instance().getFont("med_font");
		sign = new TextureRegion(Asset.instance().getTexture("sign"));
		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));
	}
	
	@Override
	public void render(SpriteBatch sb) {
		previousState.render(sb);
		sb.begin();
		sb.draw(sign, previousState.cam.viewportWidth/2 - 45, previousState.cam.viewportHeight/2);
		sb.draw(new TextureRegion(Asset.instance().getTexture("regionbg_" + selectedButton.region.regionTag)), 375, 300);
		sb.draw(new TextureRegion(Asset.instance().getTexture("flag_" + selectedButton.region.regionTag)), 415, 715);
		font.draw(sb, selectedButton.locationName, 490, 750);
		smFont.draw(sb, "Campaign Time: 1/2 year", 490, 700);
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < selectedButton.region.affectedCrops.length; i++) {
			String name = selectedButton.region.affectedCrops[i].name();
			String textName = null;
			textName = name.substring(0,1) + name.substring(1).toLowerCase();
			builder.append(textName);
			if(i != selectedButton.region.affectedCrops.length - 1) builder.append(", ");
		}
		
		smFont.draw(sb, "Special Crops: " + builder.toString(), 490, 680);
		smFont.draw(sb, "Travel Cost: $" + selectedButton.region.travelCost, 490, 660);
		smFont.draw(sb, "Time Ratio: x" + selectedButton.region.timeRatio, 490, 640);
		smFont.draw(sb, "Press [SPACE] to buy", 490, 620);
		
		if(popup) {
			sb.setColor(Color.BLACK);
			sb.draw(pixel, Var.WIDTH / 2 + 175, Var.HEIGHT / 2 - 25 + 300, 400, 100);
			sb.setColor(Color.WHITE);
			sb.draw(pixel, Var.WIDTH / 2 + 175, Var.HEIGHT / 2 - 25 + 300, 400, 1);
			sb.draw(pixel, Var.WIDTH / 2 + 175, Var.HEIGHT / 2 - 25 + 300, 1, 100);
			sb.draw(pixel, Var.WIDTH / 2 + 175, Var.HEIGHT / 2 + 75 + 300, 400, 1);
			sb.draw(pixel, Var.WIDTH / 2 + 575, Var.HEIGHT / 2 - 25 + 300, 1, 100);
			smFont.draw(sb, popupText, 690, 580);
		}
		sb.end();
	}
	
	public void update(float dt) {
		System.out.println("money: " + currentMoney);
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && !popup) {
			Asset.instance().getSound("close").play();
			gsm.push(previousState);
		}
		
		if(popup) {
			if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				Asset.instance().getSound("close").play();
				popup = false;
			}
		}
		
		//note that esc will always give the same state back.
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(currentMoney - selectedButton.region.travelCost >= 0) {
				Asset.instance().getSound("purchase").play(0.5f);
				((RegionState)previousState).selectedRegion.isUnlocked = true;
				((RegionState)previousState).getLocations().remove(selectedButton);
				selectedButton.setUnlocked();
				((RegionState)previousState).getLocations().add(selectedButton);
				currentMoney -= selectedButton.region.travelCost;
				((RegionState)previousState).currentMoney = this.currentMoney;
				gsm.push(previousState);
				popup = true;
				popupText = "Bought region!";
			}
			else {
				popup = true;
				popupText = "Not enough money!";
			}
		}
		
	}
	
}
