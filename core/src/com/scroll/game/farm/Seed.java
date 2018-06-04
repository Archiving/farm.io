package com.scroll.game.farm;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.scroll.game.Var;
import com.scroll.game.handler.Asset;

public class Seed {
	
	//time is in hours (35.0f = 35 hours)
	public enum Type {
		CARROT(new TextureRegion(Asset.instance().getTexture("seed_tile.carrot")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.carrot")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.carrot")),
				35.0f, 2, 1),
		WHEAT(new TextureRegion(Asset.instance().getTexture("seed_tile.wheat")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.wheat")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.wheat")),
				32.0f, 5, 2),
		POTATO(new TextureRegion(Asset.instance().getTexture("seed_tile.potato")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.potato")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.potato")),
				30.0f, 8, 5),
		CHILI(new TextureRegion(Asset.instance().getTexture("seed_tile.chili")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.chili")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.chili")),
				35.0f, 1000, 3),
		OLIVE(new TextureRegion(Asset.instance().getTexture("seed_tile.olive")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.olive")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.olive")), 
				30.0f, 20, 15),
		COCOA(new TextureRegion(Asset.instance().getTexture("seed_tile.chocolate")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.chocolate")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.chocolate")),
				45.0f, 20, 12),
		CINNAMON(new TextureRegion(Asset.instance().getTexture("seed_tile.cinnamon")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.cinnamon")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.cinnamon")),
				38.0f, 20, 14),
		CORN(new TextureRegion(Asset.instance().getTexture("seed_tile.corn")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.corn")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.corn")),
				37.5f, 4, 2),
		PEPPER(new TextureRegion(Asset.instance().getTexture("seed_tile.pepper")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.pepper")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.pepper")),
				35.0f, 12, 7),
		TEA(new TextureRegion(Asset.instance().getTexture("seed_tile.tea")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.tea")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.tea")),
				40.0f, 18, 10),
		RICE(new TextureRegion(Asset.instance().getTexture("seed_tile.rice")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.rice")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.rice")),
				40.0f, 14, 10), 
		COFFEE(new TextureRegion(Asset.instance().getTexture("seed_tile.coffee")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.coffee")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.coffee")),
				40.0f, 15, 10),
		SUGARCANE(new TextureRegion(Asset.instance().getTexture("seed_tile.sugarcane")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.sugarcane")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.sugarcane")),
				40.0f, 15, 10),
		TOMATO(new TextureRegion(Asset.instance().getTexture("seed_tile.tomato")),
				new TextureRegion(Asset.instance().getTexture("crop_tile.tomato")),
				new TextureRegion(Asset.instance().getTexture("seed.corn")),
				new TextureRegion(Asset.instance().getTexture("crop.tomato")),
				40.0f, 15, 10);
		
		
		public TextureRegion seedTile, cropTile;
		public TextureRegion seedItem, cropItem;
		public float requiredTime;
		public int value, cost;
		public ArrayList<Type> values;
		
		private Type(TextureRegion seedTile, TextureRegion cropTile, TextureRegion seedItem, TextureRegion cropItem, float requiredTime, int value, int cost) {
			values = new ArrayList<>();
			this.seedTile = seedTile;
			this.cropTile = cropTile;
			this.seedItem = seedItem;
			this.cropItem = cropItem;
			this.requiredTime = requiredTime;
			this.value = value;
			this.cost = cost;
			values.add(this);
		}
	}


	private TextureRegion pixel;
	private Type type;
	private Crop crop;
	private float x,y,w,h;
	
	private boolean watered;
	private boolean finished;
	private float time, requiredTime;
	
	private Region region;
	private TextureRegion image;
	
	public enum Region {
		NORTH_AMERICA("na", new Seed.Type[]{Seed.Type.CARROT, Seed.Type.WHEAT, Seed.Type.CORN}, 100.0f, 1.5f, 0, Var.HEIGHT + 218),
		SOUTH_AMERICA("sa", new Seed.Type[]{Seed.Type.SUGARCANE, Seed.Type.COFFEE}, 100.0f, 2.0f, 0, 240),
		CENTRAL_AMERICA("ca", new Seed.Type[]{Seed.Type.SUGARCANE, Seed.Type.COCOA, Seed.Type.CORN, Seed.Type.COFFEE}, 100.0f, 2.0f, 0, 646),
		WEST_EU("weu", new Seed.Type[]{Seed.Type.WHEAT, Seed.Type.SUGARCANE}, 200.0f, 2.0f, 589, 825),
		EAST_EU("eeu", new Seed.Type[]{Seed.Type.WHEAT, Seed.Type.POTATO}, 400.0f, 2.5f, 802, 814),
		SOUTH_EU("seu", new Seed.Type[]{Seed.Type.OLIVE, Seed.Type.WHEAT}, 200.0f, 3.0f, 589, 769),
		WEST_ASIA("wasia", new Seed.Type[]{Seed.Type.PEPPER, Seed.Type.WHEAT, Seed.Type.TEA, Seed.Type.RICE}, 500.0f, 2.5f, 835, 660),
		CENTRAL_ASIA("casia", new Seed.Type[]{Seed.Type.CHILI, Seed.Type.WHEAT, Seed.Type.TEA, Seed.Type.RICE}, 700.0f, 2.5f, 962, 605),
		EAST_ASIA("easia", new Seed.Type[]{Seed.Type.PEPPER, Seed.Type.CINNAMON, Seed.Type.POTATO, Seed.Type.TEA, Seed.Type.RICE}, 1000.0f, 3.0f, 1100, 680),
		SOUTH_EAST_ASIA("seasia", new Seed.Type[]{Seed.Type.SUGARCANE, Seed.Type.CINNAMON, Seed.Type.PEPPER, Seed.Type.TEA, Seed.Type.COFFEE}, 1250.0f, 3.0f, 1100, 518),
		OCEANIA("oce", new Seed.Type[]{Seed.Type.CHILI, Seed.Type.CINNAMON}, 1500.0f, 3.0f, 1100, 305),
		NORTH_AFRICA("nafr", new Seed.Type[]{Seed.Type.WHEAT, Seed.Type.OLIVE, Seed.Type.COFFEE}, 200.0f, 1.5f, 589, 651),
		WEST_AFRICA("wafr", new Seed.Type[]{Seed.Type.WHEAT, Seed.Type.COFFEE}, 200.0f, 1.5f, 589, 480),
		CENTRAL_AFRICA("cafr", new Seed.Type[]{Seed.Type.COFFEE, Seed.Type.SUGARCANE}, 400.0f, 2.0f, 792, 459),
		SOUTH_AFRICA("safr", new Seed.Type[]{Seed.Type.CHILI, Seed.Type.COFFEE, Seed.Type.SUGARCANE}, 600.0f, 2.5f, 731, 367),
		EAST_AFRICA("eafr", new Seed.Type[]{Seed.Type.POTATO, Seed.Type.RICE, Seed.Type.COFFEE}, 700.0f, 3.0f, 835, 482);

		public String regionTag;
		public TextureRegion regionImage;
		public Seed.Type[] affectedCrops;
		public float timeRatio;
		public float travelCost;
		public int x, y;
		public boolean isUnlocked;
		
		//affected crops get affected by timeRatio
		private Region(String regionTag, Seed.Type[] affectedCrops,  float travelCost, float timeRatio, int x, int y) {
			this.regionTag = regionTag;
			this.affectedCrops = affectedCrops;
			this.travelCost = travelCost;
			this.timeRatio = timeRatio;
			this.x = x;
			this.y = y;
			regionImage = new TextureRegion(Asset.instance().getTexture("region_" + regionTag));
		}
		
	}
	
	public Seed(Type type, float x, float y, float w, float h, Region region) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.region = region;
		
		image = type.seedTile;
		requiredTime = type.requiredTime;
		crop = new Crop(this);
		pixel = new TextureRegion(Asset.instance().getTexture("pixel"));
	}
	
	public float getx() { return x; }
	public float gety() { return y; }
	public float getw() { return w; }
	public float geth() { return h; }
	
	public Crop getCrop() { return crop; }
	public Type getType() { return type; }
	
	public boolean isFinished() { return finished; }
	public boolean isWatered() { return watered; }
	
	public void setWatered() { watered = true; }
	
	public void update(float dt) {
		if(watered) {
			time += dt;
			boolean specialCrop = Arrays.asList(region.affectedCrops).contains(type);
			float requiredTime = this.requiredTime/((specialCrop) ? region.timeRatio : 1);
			if(time >= requiredTime) {
				finished = true;
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		boolean specialCrop = Arrays.asList(region.affectedCrops).contains(type);
		sb.draw(image, x - (w / 2), y - (h / 2));
		if(watered) {
			float w = this.w/2;
			Color c = sb.getColor();
			sb.setColor(Color.GREEN);
			sb.draw(pixel, x - w, y, w * time / (requiredTime/((specialCrop) ? region.timeRatio : 1)), 3);
			sb.setColor(Color.BLACK);
			sb.draw(pixel, x - w, y, w, 1);
			sb.draw(pixel, x - w, y + 3, w, 1);
			sb.draw(pixel, x - w, y, 1, 4);
			sb.draw(pixel, x, y, 1, 4);
			sb.setColor(c);
		}
	}
	
}
