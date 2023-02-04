package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BackGroundCenter0001 extends EntityCenter<Screen0011,BackGround0001>{
	// SpriteBatch batch;
	public BackGround0001 clouds;
	public BackGroundCenter0001(Screen0011 p) {
		super(p);
		// batch=new SpriteBatch();
	}
	@Override
	public void display() {
		// batch.setProjectionMatrix(p.cam.camera.combined);
		// batch.begin();
		p.imageBatch.begin();
		super.display();
		p.imageBatch.end();
		// batch.end();
	}
}