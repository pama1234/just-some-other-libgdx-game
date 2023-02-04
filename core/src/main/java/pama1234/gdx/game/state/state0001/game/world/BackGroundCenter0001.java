package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.app.UtilScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackGroundCenter0001<T extends UtilScreen> extends EntityCenter<T,BackGround0001>{
	SpriteBatch batch;
	public BackGroundCenter0001(T s){
		super(s);
		batch = new SpriteBatch();
	}

	@Override
	public void display() {
		batch.setProjectionMatrix(p.cam.camera.combined);
		batch.begin();
		super.display();
		batch.end();
	}
}
