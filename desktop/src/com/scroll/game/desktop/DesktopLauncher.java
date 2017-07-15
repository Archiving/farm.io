package com.scroll.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.scroll.game.Main;
import com.scroll.game.Var;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = Var.WIDTH * Var.SCALE;
		config.height = Var.HEIGHT * Var.SCALE;
		config.title = Var.TITLE;
		new LwjglApplication(new Main(), config);
	}
}
