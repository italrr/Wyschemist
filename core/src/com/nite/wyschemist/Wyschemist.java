package com.nite.wyschemist;

import com.badlogic.gdx.ApplicationAdapter;

public class Wyschemist extends ApplicationAdapter {
    Game game = new Game();

	@Override
	public void create () {
        game.init();
    }

	@Override
	public void render () {
        game.update();
        game.draw();
	}
	
	@Override
	public void dispose () {
        game.end();
        game = null;
        Tools.print("Final dispose.");
	}
}
