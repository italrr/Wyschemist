package com.nite.wyschemist.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nite.wyschemist.Wyschemist;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int)(720f*0.70f);
        config.height = (int)(1280f*0.70f);
        config.samples = 8;
		new LwjglApplication(new Wyschemist(), config);
	}
}
