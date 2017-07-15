package com.scroll.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.farm.Seed.Region;
import com.scroll.game.handler.Asset;

public class RegionHUD {

	public TextureRegion frame;
	public TextureRegion flag;
	public BitmapFont font;
	public Region selectedRegion;
	public StringBuilder builder;
	
	public RegionHUD() {
		frame = new TextureRegion(Asset.instance().getTexture("map_frame"));
		font = Asset.instance().getFont("small_font");
		builder = new StringBuilder();
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(frame, 30, 30);
		if(flag != null) {
			sb.draw(flag, 35, 36);
			font.draw(sb, "Click location tag to start.",  45+frame.getRegionWidth(), frame.getRegionHeight()-22);
		}
		if(selectedRegion != null) {
			builder.setLength(0);
			builder.append("Specialty Crops: ");
			for(int i = 0; i < selectedRegion.affectedCrops.length; i++) {
				String name = selectedRegion.affectedCrops[i].name();
				String textName = null;
				if(flag != null) {
					textName = name.substring(0,1) + name.substring(1).toLowerCase();
				}
				else { textName = "?"; }
				builder.append(textName);
				if(i != selectedRegion.affectedCrops.length - 1) builder.append(", ");
			}
			font.draw(sb, builder.toString(), 45+frame.getRegionWidth(), 24+frame.getRegionHeight());
			font.draw(sb, "Time Ratio for Crops: x" + selectedRegion.timeRatio, 45+frame.getRegionWidth(), frame.getRegionHeight());
			if(flag == null) {
				font.draw(sb, Float.toString(selectedRegion.travelCost), 30 + font.getSpaceWidth() * Float.toString(selectedRegion.travelCost).length()/2, 80);
				sb.draw(new TextureRegion(Asset.instance().getTexture("cash")), 35, 35);
				font.draw(sb, "Click location tag to buy.",  35+frame.getRegionWidth()+10, frame.getRegionHeight()-22);
			}
			
		}
	}
	
	public void update(float dt, Region selectedRegion) {
		this.selectedRegion = selectedRegion;
		if(selectedRegion != null) {
			//update so that the flag texture is always the selected region's flag
			if(selectedRegion.isUnlocked) flag = new TextureRegion(Asset.instance().getTexture("flag_" + selectedRegion.regionTag));
			else {
				flag = null;
			}
		}
	}
	
}
