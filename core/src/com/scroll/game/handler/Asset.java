package com.scroll.game.handler;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Asset {

	private static Asset asset = new Asset();
	
	//HashMap<K,V>
	public HashMap<String, Texture> textures;
	public HashMap<String, Sound> sounds;
	public HashMap<String, BitmapFont> fonts;
	public HashMap<String, Music> music;
	
	public Asset() {
		textures = new HashMap<>();
		sounds = new HashMap<>();
		fonts = new HashMap<>();
		music = new HashMap<>();
	}
	
	public void loadTexture(String key, String path) {
		textures.put(key, new Texture(Gdx.files.internal(path)));
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public void loadCropItem(String cropName) {
		loadTexture("crop." + cropName, "images/crops/crop_" + cropName + ".png");
	}
	
	public void loadCropTile(String cropName) {
		loadTexture("crop_tile." + cropName, "images/crop_tiles/crop_tile_" + cropName + ".png");
	}
	
	public void loadSeedItem(String cropName) {
		loadTexture("seed." + cropName, "images/seeds/seed_" + cropName + ".png");
	}
	
	public void loadSeedTile(String cropName) {
		loadTexture("seed_tile." + cropName, "images/seed_tiles/seed_tile_" + cropName + ".png");
	}
	
	public void loadRegion(String regionTag) {
		String key = "region_" + regionTag;
		loadTexture(key, "images/region/regions/" + key + ".png");
		
	}
	
	public void loadSound(String key, String path) {
		sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
	}
	
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	
	public void loadFont(String key, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("data/prstart.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.borderColor = new Color(0.2f, 0.2f, 0.2f, 1);
		param.borderStraight = true;
		param.borderWidth = 1;
		fonts.put(key, gen.generateFont(param));
		gen.dispose();
	}
	
	public BitmapFont getFont(String key) {
		return fonts.get(key);
	}
	
	public void loadMusic(String key, String path) {
		music.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
	}
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public static Asset instance() {
		return asset;
	}
}
