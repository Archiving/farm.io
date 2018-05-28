package com.scroll.game.state;

import java.awt.Point;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.scroll.game.Var;
import com.scroll.game.farm.Patch;
import com.scroll.game.farm.Seed.Region;
import com.scroll.game.handler.Asset;
import com.scroll.game.handler.BoundCamera;
import com.scroll.game.state.entities.Player;
import com.scroll.game.state.entities.Truck;
import com.scroll.game.state.tile.MapObject.Direction;
import com.scroll.game.state.tile.TileMap;
import com.scroll.game.ui.GuideBlock;
import com.scroll.game.ui.UIButton;

public class PlayState extends State {

	private TileMap tm;
	private BoundCamera cam;
	private Player player;
	private Patch[][] farm;
	private float globalTime;
	private BitmapFont font;
	private StringBuilder builder;
	private Region region;
	private UIButton shopButton, settingsButton, helpButton;
	private Music music;
	public Truck truck;
	public int playTime;
	
	public PlayState(GSM gsm, Region selectedRegion, int playTime) {
		super(gsm);
		this.region = selectedRegion;
		this.playTime = playTime;
		music = Asset.instance().getMusic("music");
		music.setVolume(Var.MUSIC_VOL);
		music.setLooping(true);
		music.play();
		
		tm = new TileMap(16);
		Texture tiles = Asset.instance().getTexture("tileset");
		TextureRegion[][] tileset = TextureRegion.split(tiles, 16, 16);
		tm.loadTileset(tileset);		
		tm.loadMap("data/tilemap.tme");
		
		player = new Player(tm, selectedRegion);
		player.setPosition(100, 280);
		truck = new Truck(tm);
		
		cam = new BoundCamera();
		cam.setToOrtho(false, Var.WIDTH, Var.HEIGHT);
		cam.setBounds(0, 0, tm.getWidth(), tm.getHeight());
		cam.position.set(0,0,0);
		cam.position.set(player.getx(), player.gety(), 0);
		cam.update();
		
		farm = new Patch[10][20];
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col] = new Patch(tm, row + 4, col + 12);
			}
		}
		
		player.setFarm(farm);
		font = Asset.instance().getFont("small_font");
		builder = new StringBuilder();
		
		shopButton = new UIButton(13, Var.HEIGHT - 51, 24, 16, new TextureRegion(Asset.instance().getTexture("shop_button")), new TextureRegion(Asset.instance().getTexture("shop_button_clicked")));
		settingsButton = new UIButton(88, Var.HEIGHT - 26, 12, 12, new TextureRegion(Asset.instance().getTexture("settings_button")), new TextureRegion(Asset.instance().getTexture("settings_button_clicked")));
		helpButton = new UIButton(700, 10, 32, 32, new TextureRegion(Asset.instance().getTexture("help_button")), new TextureRegion(Asset.instance().getTexture("help_button_clicked")));
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		tm.render(sb, cam);
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col].render(sb);
			}
		}
		truck.render(sb);
		player.render(sb);
		
		sb.setProjectionMatrix(super.cam.combined);
		
		int seconds = (int) globalTime;
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		builder.setLength(0);
		builder.append(day).append((day == 1) ? "/10 day, " : "/10 days, ");
		if(hours < 10) {
			builder.append("0");
		}
		builder.append(hours).append(":");
		if(minute < 10) {
			builder.append("0");
		}
		builder.append(minute).append(":");
		if(second < 10) {
			builder.append("0");
		}
		builder.append(second);
		sb.draw(new TextureRegion(Asset.instance().getTexture("seed_display_frame")), 8, Var.HEIGHT - 56);
		if(player.getSelectedSeed() != null) {
			sb.draw(player.getSelectedSeed().seedItem, 12, Var.HEIGHT - 26);
			String seedName = player.getSelectedSeed().name().substring(0,1) + player.getSelectedSeed().name().toLowerCase().substring(1);
			font.draw(sb, seedName, 30, Var.HEIGHT - 16);
		}
		else { font.draw(sb, "No Seed", 18, Var.HEIGHT - 16); }
		font.draw(sb, "$"+Integer.toString(player.getMoney()), 40, Var.HEIGHT - 40);
		
		font.draw(sb, builder.toString(), 10, Var.HEIGHT - 64);
		font.draw(sb, "Seeds Left: " + player.getSeedInventory().size(), 10, 415);
		font.draw(sb, "Crops Stored: " + player.getNumCrops(), 10, 400);
		
		shopButton.draw(sb);
		settingsButton.draw(sb);
		helpButton.draw(sb);
		sb.end();
	}

	@Override
	public void update(float dt) {
		globalTime += 2880 * dt;
		int day = (int) TimeUnit.SECONDS.toDays((int)globalTime);
		if(day == playTime) {
			music.stop();
			font.setColor(Color.WHITE);
			gsm.push(new RegionState(gsm, player.getMoney(), region));
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			player.till();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			player.water();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			player.seed();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			player.harvest();
		}	
		if(Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
			player.pipe();
		}
		
		
		if(player.intersects(truck)) {
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				Asset.instance().getSound("open").play();
				gsm.push(new SeedShopState(gsm, this, player, region));
			}
			if(player.sell()) {
				Asset.instance().getSound("purchase").play(0.5f);
				truck.setShowing();
			}
		}
		
		player.setUp(Gdx.input.isKeyPressed(Keys.W));
		player.setDown(Gdx.input.isKeyPressed(Keys.S));
		player.setLeft(Gdx.input.isKeyPressed(Keys.A));
		player.setRight(Gdx.input.isKeyPressed(Keys.D));
		
		if(Gdx.input.isKeyJustPressed(Keys.W)) player.setDirection(Direction.UP);
		if(Gdx.input.isKeyJustPressed(Keys.S)) player.setDirection(Direction.DOWN);
		if(Gdx.input.isKeyJustPressed(Keys.A)) player.setDirection(Direction.LEFT);
		if(Gdx.input.isKeyJustPressed(Keys.D)) player.setDirection(Direction.RIGHT);
		player.update(dt);
		truck.update(dt);
		
		truck.setPosition(60, 280);
		
		cam.position.set(player.getx(), player.gety(), 0);
		cam.update();
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col].update(dt);
			}
		}
		
		Vector3 pos1 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		super.cam.unproject(pos1);
		if(settingsButton.getBounds().contains(new Point((int)pos1.x, (int)pos1.y))) {
			if(Gdx.input.isTouched()) {
				settingsButton.click();
			}
			else if(!Gdx.input.isTouched()) {
				settingsButton.clicked = false;
			}
		}
		
		if(helpButton.getBounds().contains(new Point((int)pos1.x, (int)pos1.y))) {
			if(Gdx.input.isTouched()) {
				helpButton.click();
				GuideBlock[] helpBlocks = new GuideBlock[]{
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_1")), "NUMPAD 1 to till, 2 to water, 3 to plant, and 4 to harvest.", 115, 330, 64, 64),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_2")), "Go to the truck to sell the plants you harvest.", 115, 230, 64, 64),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_3")), "Press SPACE while on the truck to open the shop, and buy more seeds!", 115, 130, 64, 64)
				};
				
				gsm.push(new PlayHelpState(gsm, helpBlocks, this, 50, 100));
				
			}
			else {
				helpButton.clicked = false;
			}
		}
		
		if(!helpButton.clicked) helpButton.image = new TextureRegion(Asset.instance().getTexture("help_button"));
		if(!shopButton.clicked) shopButton.image = new TextureRegion(Asset.instance().getTexture("shop_button"));
		if(!settingsButton.clicked) settingsButton.image = new TextureRegion(Asset.instance().getTexture("settings_button"));
		
	}
	
}
