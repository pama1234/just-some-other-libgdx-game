package pama1234.gdx.game.state.state0001.game.world;

import java.util.ArrayList;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BackgroundCenter extends EntityCenter<Screen0011,Background>{
	public World0001 pw;
	// public BackGround0001 clouds0001;
	public ArrayList<TextureBackground> cloudList;
	public BackgroundCloud clouds0002;
	public BackgroundCenter(Screen0011 p,World0001 pw) {
		super(p);
		this.pw=pw;
	}
	@Override
	public void display() {
		p.imageBatch.begin();
		super.display();
		p.imageBatch.end();
		p.noTint();
	}
}