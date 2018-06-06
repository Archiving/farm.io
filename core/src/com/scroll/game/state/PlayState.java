package com.scroll.game.state;

import java.awt.Point;
import java.util.ArrayList;
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
import com.scroll.game.tech.Tech;
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
	private UIButton shopButton, settingsButton, helpButton, quitButton;
	public Music music;
	public Truck truck;
	public int playTime;
	//current tech being researched
	public ArrayList<Tech> progressTech;
	public ArrayList<Tech> finishedTech;
	
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
		
		//[y][x]
		farm = new Patch[12][22];
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col] = new Patch(tm, row + 3, col + 11);
			}
		}
		
		player.setFarm(farm);
		font = Asset.instance().getFont("small_font");
		builder = new StringBuilder();
		
		progressTech = new ArrayList<>();
		finishedTech = new ArrayList<>();
		
		shopButton = new UIButton(13, Var.HEIGHT - 51, 24, 16, new TextureRegion(Asset.instance().getTexture("shop_button")), new TextureRegion(Asset.instance().getTexture("shop_button_clicked")));
		settingsButton = new UIButton(88, Var.HEIGHT - 26, 12, 12, new TextureRegion(Asset.instance().getTexture("settings_button")), new TextureRegion(Asset.instance().getTexture("settings_button_clicked")));
		helpButton = new UIButton(700, 10, 32, 32, new TextureRegion(Asset.instance().getTexture("help_button")), new TextureRegion(Asset.instance().getTexture("help_button_clicked")));
		quitButton = new UIButton(670, 460, 32, 32, new TextureRegion(Asset.instance().getTexture("quit_button")), new TextureRegion(Asset.instance().getTexture("quit_button_pressed")));
	}
	
	public PlayState(GSM gsm, PlayState previousState) {
		this(gsm, previousState.region, previousState.playTime);
		this.player = previousState.player;
		this.farm = previousState.farm;
		this.globalTime = previousState.globalTime;
		this.truck = previousState.truck;
		this.cam = previousState.cam;
	}
	
	public PlayState(GSM gsm, PlayState previousState, Tech[][] currentTech) {
		this(gsm, previousState);
		for(int y = 0; y < currentTech.length; y++) {
			for(int x = 0; x < currentTech[y].length; x++) {
				if(currentTech[y][x] != null) {
					if(currentTech[y][x].isInProgress()) this.progressTech.add(currentTech[y][x]);
					if(currentTech[y][x].isUnlocked()) this.finishedTech.add(currentTech[y][x]);
				}
			}
		}
		
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
		builder.append(day).append((day == 1) ? "/" +  playTime + " day, " : "/" + playTime + " days, ");
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
		font.draw(sb, "Quit", 673, 458);
		
		shopButton.draw(sb);
		settingsButton.draw(sb);
		helpButton.draw(sb);
		quitButton.draw(sb);
		
		sb.end();
	}

	@Override
	public void update(float dt) {
		System.out.println(progressTech.isEmpty() ? "1" : "0");
		globalTime += 2880 * dt;
		int day = (int) TimeUnit.SECONDS.toDays((int)globalTime);
		if(day == playTime) {
			music.stop();
			font.setColor(Color.WHITE);
			gsm.push(new RegionState(gsm, player.getMoney(), region));
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Q)) {
			player.till();
		}
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			player.water();
		}
		if(Gdx.input.isKeyJustPressed(Keys.E)) {
			player.seed();
		}
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			player.harvest();
		}	
		if(Gdx.input.isKeyJustPressed(Keys.R)) {
			player.pipe();
		}	
		
		if(Gdx.input.isKeyJustPressed(Keys.T)) {
			gsm.push(new TechnologyState(gsm, this, 100, 75));
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
		
		player.setUp(Gdx.input.isKeyPressed(Keys.UP));
		player.setDown(Gdx.input.isKeyPressed(Keys.DOWN));
		player.setLeft(Gdx.input.isKeyPressed(Keys.LEFT));
		player.setRight(Gdx.input.isKeyPressed(Keys.RIGHT));
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)) player.setDirection(Direction.UP);
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) player.setDirection(Direction.DOWN);
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) player.setDirection(Direction.LEFT);
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) player.setDirection(Direction.RIGHT);
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
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_1")), "Press Q to till, W to water, E to seed and SPACE to harvest, and arrow keys to move.", 90, 300, 32, 32),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_2")), "Go to the truck to sell the crops that you harvest for money.", 90, 250, 32, 32),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_3")), "Press SPACE while on the truck to use that money to buy more seeds.", 90, 200, 32, 32),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_4")), "Press T to research new technologies with the money you get from selling crops!", 90, 150, 32, 32),
						new GuideBlock(new TextureRegion(Asset.instance().getTexture("play_guide_block_5")), "Save money near the end of the campaign to travel to new places for better crops.", 90, 100, 32, 32)
				};
				gsm.push(new PlayHelpState(gsm, helpBlocks, this, 50, 100));
			}
			else {
				helpButton.clicked = false;
			}
		}
		
		if(quitButton.getBounds().contains(new Point((int)pos1.x, (int)pos1.y))) {
			if(Gdx.input.isTouched()) {
				quitButton.click();
				gsm.push(new WarningState(gsm, this));
			}
			else {
				quitButton.clicked = false;
			}
		}
		
		if(!helpButton.clicked) helpButton.image = new TextureRegion(Asset.instance().getTexture("help_button"));
		if(!shopButton.clicked) shopButton.image = new TextureRegion(Asset.instance().getTexture("shop_button"));
		if(!settingsButton.clicked) settingsButton.image = new TextureRegion(Asset.instance().getTexture("settings_button"));
		if(!quitButton.clicked) quitButton.image = new TextureRegion(Asset.instance().getTexture("quit_button"));
	}
	
	public Player getPlayer() { return player; }
	public Region getRegion() { return region; }

	public void setPlayer(Player p) {
		this.player = p;
	}
	
}
