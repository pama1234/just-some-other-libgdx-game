package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BackGroundCenter0001 extends EntityCenter<Screen0011,BackGround>{
	public World0001 pw;
	public BackGround0001 clouds0001;
	public BackGroundCloud clouds0002;
	public BackGroundCenter0001(Screen0011 p,World0001 pw) {
		super(p);
		this.pw=pw;
	}
	@Override
	public void display() {
		// p.imageBatch.begin();
		super.display();
		// p.imageBatch.end();
	}
}