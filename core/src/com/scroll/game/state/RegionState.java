package com.scroll.game.state;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.scroll.game.Var;
import com.scroll.game.farm.Seed.Region;
import com.scroll.game.handler.Asset;
import com.scroll.game.ui.GuideBlock;
import com.scroll.game.ui.LocationButton;
import com.scroll.game.ui.RegionHUD;
import com.scroll.game.ui.UIButton;

public class RegionState extends State {
	
	public BitmapFont largeFont;
	public TextureRegion worldMap;
	public TextureRegion cropImages;
	public List<LocationButton> locations;
	public Region selectedRegion;
	public OrthographicCamera cam;
	public RegionHUD hud;
	public int currentMoney;
	public Region currentLocation;
	public UIButton helpButton;
	public GlyphLayout layout = new GlyphLayout();
	
	public RegionState(GSM gsm, int currentMoney) {
		super(gsm);
		this.currentMoney = currentMoney;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Var.WIDTH * Var.SCALE, Var.HEIGHT * Var.SCALE);
		worldMap = new TextureRegion(Asset.instance().getTexture("map"));
		locations = new ArrayList<>();
		hud = new RegionHUD();
		
		largeFont = Asset.instance().getFont("large_font");
		helpButton = new UIButton(975, 50, 32, 32, new TextureRegion(Asset.instance().getTexture("help_button")), new TextureRegion(Asset.instance().getTexture("help_button_clicked")));
		
		LocationButton america = new LocationButton(175, 795, 16, 16, Region.NORTH_AMERICA, "Moab, Utah", false);
		LocationButton central_america = new LocationButton(250, 650, 16, 16, Region.CENTRAL_AMERICA, "Mexico City, Mexico", false);
		LocationButton north_africa = new LocationButton(650, 750, 16, 16, Region.NORTH_AFRICA, "Fes, Morroco", false);
		LocationButton west_africa = new LocationButton(650, 600, 16, 16, Region.WEST_AFRICA, "Porto-Novo, Benin", false);
		LocationButton east_africa = new LocationButton(855, 630, 16, 16, Region.EAST_AFRICA, "Dire Dawa, Ethiopia", false);
		LocationButton west_asia = new LocationButton(875, 700, 16, 16, Region.WEST_ASIA, "Medina, Saudi Arabia", false);
		LocationButton south_eu = new LocationButton(740, 800, 16, 16, Region.SOUTH_EU, "Naples, Italy", false);
		LocationButton west_eu = new LocationButton(670, 860, 16, 16, Region.WEST_EU, "Cambridge, England", false);
		LocationButton east_eu = new LocationButton(840, 860, 16, 16, Region.EAST_EU, "Moscow, Russia", false);
		LocationButton central_asia = new LocationButton(970, 760, 16, 16, Region.CENTRAL_ASIA, "Kabul, Afghanistan", false);
		LocationButton east_asia = new LocationButton(1190, 740, 16, 16, Region.EAST_ASIA, "Hunan, China", false);
		LocationButton se_asia = new LocationButton(1180, 550, 16, 16, Region.SOUTH_EAST_ASIA, "Kuala Lumpur, Malaysia", false);
		LocationButton south_america = new LocationButton(475, 500, 16, 16, Region.SOUTH_AMERICA, "Cruz das Almas, Brazil", false);
		LocationButton south_africa = new LocationButton(780, 425, 16, 16, Region.SOUTH_AFRICA, "Johannesburg, South Africa", false);
		LocationButton oceania = new LocationButton(1350, 375, 16, 16, Region.OCEANIA, "Melbourne, Australia", false);
		LocationButton central_africa = new LocationButton(810, 545, 16, 16, Region.CENTRAL_AFRICA, "Kampala, Uganda", false);
		
		locations.add(america);		
		locations.add(central_america);
		locations.add(north_africa);
		locations.add(west_africa);
		locations.add(east_africa);
		locations.add(west_asia);
		locations.add(south_eu);
		locations.add(west_eu);
		locations.add(east_eu);
		locations.add(central_asia);
		locations.add(east_asia);
		locations.add(se_asia);	
		locations.add(south_america);
		locations.add(south_africa);
		locations.add(oceania);
		locations.add(central_africa);
		
		layout.setText(largeFont, Integer.toString(currentMoney));
	}
	
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.setColor(Color.GRAY);
		sb.draw(worldMap, 0, 0);
		sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if(selectedRegion != null) sb.draw(selectedRegion.regionImage, selectedRegion.x, selectedRegion.y);
		
		for(int i = 0; i < locations.size(); i++) {
			locations.get(i).draw(sb);
		}
		
		sb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		largeFont.draw(sb, "farm.io", cam.viewportWidth / 2 -  7 * largeFont.getSpaceWidth() / 2, 100);
		largeFont.draw(sb, "$" + currentMoney, 1400 - layout.width, 950);
		hud.draw(sb);
		helpButton.draw(sb);
		sb.end();
	}
	
	@Override
	public void update(float dt) {
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(pos);
			for(LocationButton button : locations) {
				if(button.getBounds().contains(new Point((int)pos.x, (int)pos.y)) && button.isUnlocked()) {
					if(!button.hasHovered()) {
						Asset.instance().getSound("select").play(0.5f);
						button.setHovered();
					}
					selectedRegion = button.region;
					button.setSelected(true);
					if(Gdx.input.isTouched()) {
						Asset.instance().getSound("click").play(0.5f);
						gsm.push(new PlayState(gsm, selectedRegion));
					}
				}
				else if(button.getBounds().contains(new Point((int)pos.x, (int)pos.y)) && !button.isUnlocked()) {
					if(!button.hasHovered()) {
						Asset.instance().getSound("select").play(0.5f);
						button.setHovered();
					}
					selectedRegion = button.region;
					if(Gdx.input.isTouched()) {
						Asset.instance().getSound("open").play(0.5f);
						gsm.push(new RegionShopState(gsm, this, button, currentMoney));
					}
				}
			}
			
			for(LocationButton button : locations) {
				if(button.region == selectedRegion) continue;
				button.hovered = false;
				button.setSelected(false);
			}
			
			Vector3 pos1 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(pos1);
			if(helpButton.getBounds().contains(new Point((int)pos1.x, (int)pos1.y))) {
				if(Gdx.input.isTouched()) {
					helpButton.click();
					GuideBlock[] helpBlocks = new GuideBlock[]{
							new GuideBlock(new TextureRegion(Asset.instance().getTexture("help_guide_block_1")), "Click the location tag to open a shop dialog.", 415, 630, 128, 128),
							new GuideBlock(new TextureRegion(Asset.instance().getTexture("help_guide_block_2")), "The amount of money in the top left comes from previous farming campaigns.", 415, 480, 128, 128),
							new GuideBlock(new TextureRegion(Asset.instance().getTexture("help_guide_block_3")), "Use money to travel to new places and get higher quality crops!", 415, 330, 128, 128)
					};
					
					gsm.push(new RegionHelpState(gsm, helpBlocks, this, Var.WIDTH/2-50, Var.HEIGHT/2));
					
				}
				else {
					helpButton.clicked = false;
				}
			}
			
			if(!helpButton.clicked) helpButton.image = new TextureRegion(Asset.instance().getTexture("help_button"));
			hud.update(dt, selectedRegion);
	}
	
	public List<LocationButton> getLocations() { return locations; }
}
	

