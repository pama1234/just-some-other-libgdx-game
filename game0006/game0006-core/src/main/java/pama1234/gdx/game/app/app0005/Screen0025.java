package pama1234.gdx.game.app.app0005;

import java.io.File;
import java.nio.file.Path;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.OutputMode;
import com.badlogic.gdx.Gdx;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0025 extends ScreenCore2D{
  @Override
  public void setup() {
    try {
      D8Command command=D8Command.builder(new DiagnosticsHandler() {
      })
        .addClasspathFiles(new Path[0])
        .setMinApiLevel(26)
        .addLibraryFiles(new Path[0])
        .addProgramFiles(new Path[0])
        .addProgramFiles(Gdx.files.local("data/cache/dex/").file().toPath())
        .setOutput(
          new File(Gdx.files.local("data/cache/dex/build").path(),"bin").toPath(),OutputMode.DexIndexed)
        .build();
      D8.run(command);
    }catch(CompilationFailedException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
