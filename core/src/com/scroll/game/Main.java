package com.scroll.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scroll.game.handler.Asset;
import com.scroll.game.state.GSM;
import com.scroll.game.state.RegionState;

public class Main extends ApplicationAdapter {
	
	private GSM gsm;
	private SpriteBatch sb;
	private Asset asset;
	
	@Override
	public void create() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		asset = Asset.instance();
		
		//textures
		asset.loadTexture("tileset", "tileset.png");
		asset.loadTexture("player", "player.png");
		asset.loadTexture("truck", "truck.png");
		asset.loadTexture("pixel", "pixel.png");
		
		asset.loadTexture("borderImage", "borderImage.png");
		asset.loadTexture("plot_state.normal", "images/plot_normal.png");
		asset.loadTexture("plot_state.watered", "images/plot_watered.png");
		asset.loadTexture("plot_state.tilled", "images/plot_tilled.png");
		asset.loadTexture("map", "images/region/map.png");
		asset.loadTexture("location_tag", "images/region/location_tag.png");
		asset.loadTexture("lock", "images/region/lock.png");
		asset.loadTexture("cash", "images/cash.png");
		asset.loadTexture("sign", "images/sign.png");
		asset.loadTexture("map_frame", "images/region/map_frame.png");
		asset.loadTexture("seed_display_frame", "images/hud.png");
		
		asset.loadTexture("shop_button", "images/hud/shop_button.png");
		asset.loadTexture("settings_button", "images/hud/settings_button.png");
		asset.loadTexture("shop_button_clicked", "images/hud/shop_button_clicked.png");
		asset.loadTexture("settings_button_clicked", "images/hud/settings_button_clicked.png");	
		asset.loadTexture("help_button", "images/hud/help_button.png");
		asset.loadTexture("help_button_clicked", "images/hud/help_button_clicked.png");
		

		asset.loadTexture("help_guide_block_1", "images/hud/help_guide_block_1.png");
		asset.loadTexture("help_guide_block_2", "images/hud/help_guide_block_2.png");
		asset.loadTexture("help_guide_block_3", "images/hud/help_guide_block_3.png");

		asset.loadTexture("play_guide_block_1", "images/hud/play_guide_block_1.png");
		asset.loadTexture("play_guide_block_2", "images/hud/play_guide_block_2.png");
		asset.loadTexture("play_guide_block_3", "images/hud/play_guide_block_3.png");
		asset.loadTexture("play_guide_block_4", "images/hud/play_guide_block_4.png");
		asset.loadTexture("play_guide_block_5", "images/hud/play_guide_block_5.png");
		
		asset.loadTexture("quit_button", "images/hud/quit.png");
		asset.loadTexture("quit_button_pressed", "images/hud/quit_pressed.png");
		
		asset.loadTexture("flag_na", "images/region/flags/flag_na.png");
		asset.loadTexture("flag_nafr", "images/region/flags/flag_nafr.png");
		asset.loadTexture("flag_wafr", "images/region/flags/flag_wafr.png");
		asset.loadTexture("flag_eafr", "images/region/flags/flag_eafr.png");
		asset.loadTexture("flag_wasia", "images/region/flags/flag_wasia.png");
		asset.loadTexture("flag_seu", "images/region/flags/flag_seu.png");
		asset.loadTexture("flag_weu", "images/region/flags/flag_weu.png");
		asset.loadTexture("flag_ca", "images/region/flags/flag_ca.png");
		asset.loadTexture("flag_eeu", "images/region/flags/flag_eeu.png");
		asset.loadTexture("flag_casia", "images/region/flags/flag_casia.png");
		asset.loadTexture("flag_easia", "images/region/flags/flag_easia.png");
		asset.loadTexture("flag_seasia", "images/region/flags/flag_seasia.png");
		asset.loadTexture("flag_sa", "images/region/flags/flag_sa.png");
		asset.loadTexture("flag_safr", "images/region/flags/flag_safr.png");
		asset.loadTexture("flag_oce", "images/region/flags/flag_oce.png");
		asset.loadTexture("flag_cafr", "images/region/flags/flag_cafr.png");

		asset.loadTexture("regionbg_na", "images/region/regionbg/regionbg_na.png");
		asset.loadTexture("regionbg_nafr", "images/region/regionbg/regionbg_nafr.png");
		asset.loadTexture("regionbg_wafr", "images/region/regionbg/regionbg_wafr.png");
		asset.loadTexture("regionbg_eafr", "images/region/regionbg/regionbg_eafr.png");
		asset.loadTexture("regionbg_wasia", "images/region/regionbg/regionbg_wasia.png");
		asset.loadTexture("regionbg_seu", "images/region/regionbg/regionbg_seu.png");
		asset.loadTexture("regionbg_weu", "images/region/regionbg/regionbg_weu.png");
		asset.loadTexture("regionbg_ca", "images/region/regionbg/regionbg_ca.png");
		asset.loadTexture("regionbg_eeu", "images/region/regionbg/regionbg_eeu.png");
		asset.loadTexture("regionbg_casia", "images/region/regionbg/regionbg_casia.png");
		asset.loadTexture("regionbg_easia", "images/region/regionbg/regionbg_easia.png");
		asset.loadTexture("regionbg_seasia", "images/region/regionbg/regionbg_seasia.png");
		asset.loadTexture("regionbg_sa", "images/region/regionbg/regionbg_sa.png");
		asset.loadTexture("regionbg_safr", "images/region/regionbg/regionbg_safr.png");
		asset.loadTexture("regionbg_oce", "images/region/regionbg/regionbg_oce.png");
		asset.loadTexture("regionbg_cafr", "images/region/regionbg/regionbg_cafr.png");
		
		asset.loadTexture("till_pipe", "images/pipes/water_pipe.png");
		asset.loadTexture("water_pipe", "images/pipes/water_pipe.png");
		
		//tiles
		asset.loadCropTile("carrot");
		asset.loadSeedTile("carrot");
		asset.loadCropItem("carrot");
		asset.loadSeedItem("corn");

		asset.loadCropTile("potato");
		asset.loadSeedTile("potato");
		asset.loadCropItem("potato");
		
		asset.loadCropTile("wheat");
		asset.loadSeedTile("wheat");
		asset.loadCropItem("wheat");

		asset.loadCropTile("chili");
		asset.loadSeedTile("chili");
		asset.loadCropItem("chili");
		
		asset.loadCropTile("olive");
		asset.loadSeedTile("olive");
		asset.loadCropItem("olive");
		
		asset.loadCropTile("cinnamon");
		asset.loadSeedTile("cinnamon");
		asset.loadCropItem("cinnamon");
		
		asset.loadCropTile("pepper");
		asset.loadSeedTile("pepper");
		asset.loadCropItem("pepper");

		asset.loadCropTile("corn");
		asset.loadSeedTile("corn");
		asset.loadCropItem("corn");

		asset.loadCropTile("chocolate");
		asset.loadSeedTile("chocolate");
		asset.loadCropItem("chocolate");

		asset.loadCropTile("tea");
		asset.loadSeedTile("tea");
		asset.loadCropItem("tea");

		asset.loadCropTile("rice");
		asset.loadSeedTile("rice");
		asset.loadCropItem("rice");

		asset.loadCropTile("coffee");
		asset.loadSeedTile("coffee");
		asset.loadCropItem("coffee");

		asset.loadCropTile("sugarcane");
		asset.loadSeedTile("sugarcane");
		asset.loadCropItem("sugarcane");

		asset.loadCropTile("tomato");
		asset.loadSeedTile("tomato");
		asset.loadCropItem("tomato");
		
		asset.loadFont("small_font", 10);
		asset.loadFont("20_font", 20);
		asset.loadFont("large_font", 96);
		asset.loadFont("med_font", 40);
		
		asset.loadSound("select", "data/select.wav");
		asset.loadSound("purchase", "data/purchase.wav");
		asset.loadSound("click", "data/click.wav");
		asset.loadSound("till", "data/till.wav");
		asset.loadSound("water", "data/water.wav");
		asset.loadSound("select_fail", "data/select_fail.wav");
		asset.loadSound("open", "data/open.wav");
		asset.loadSound("close", "data/close.wav");
		
		//region pieces
		asset.loadRegion("na");
		asset.loadRegion("ca");
		asset.loadRegion("sa");
		asset.loadRegion("nafr");
		asset.loadRegion("safr");
		asset.loadRegion("eafr");
		asset.loadRegion("wafr");
		asset.loadRegion("cafr");
		asset.loadRegion("seu");
		asset.loadRegion("weu");
		asset.loadRegion("eeu");
		asset.loadRegion("wasia");
		asset.loadRegion("casia");
		asset.loadRegion("easia");
		asset.loadRegion("seasia");
		asset.loadRegion("oce");
		
		asset.loadTexture("watering_1", "images/technology/watering_1.png");
		asset.loadTexture("watering_2", "images/technology/watering_2.png");
		asset.loadTexture("watering_3", "images/technology/watering_3.png");
		asset.loadTexture("watering_4", "images/technology/watering_4.png");
		asset.loadTexture("watering_5", "images/technology/watering_5.png");
		
		asset.loadTexture("harvest_1", "images/technology/harvesting_1.png");
		asset.loadTexture("harvest_2", "images/technology/harvesting_2.png");
		asset.loadTexture("harvest_3", "images/technology/harvesting_3.png");
		asset.loadTexture("harvest_4", "images/technology/harvesting_4.png");
		asset.loadTexture("harvest_5", "images/technology/harvesting_5.png");
		
		asset.loadTexture("seeding_1", "images/technology/seeding_1.png");
		asset.loadTexture("seeding_2", "images/technology/seeding_2.png");
		asset.loadTexture("seeding_3", "images/technology/seeding_3.png");
		asset.loadTexture("seeding_4", "images/technology/seeding_4.png");
		asset.loadTexture("seeding_5", "images/technology/seeding_5.png");	
		
		asset.loadTexture("tilling_1", "images/technology/tilling_1.png");
		asset.loadTexture("tilling_2", "images/technology/tilling_2.png");
		asset.loadTexture("tilling_3", "images/technology/tilling_3.png");
		asset.loadTexture("tilling_4", "images/technology/tilling_4.png");
		asset.loadTexture("tilling_5", "images/technology/tilling_5.png");
		
		asset.loadMusic("music", "data/music.wav");
		asset.loadXML("tech", "data/techtree.xml");
		
		sb = new SpriteBatch();
		gsm = new GSM();
		gsm.push(new RegionState(gsm, 1000, null));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.render(sb);
		gsm.update(Gdx.graphics.getDeltaTime());
	}
	
}
