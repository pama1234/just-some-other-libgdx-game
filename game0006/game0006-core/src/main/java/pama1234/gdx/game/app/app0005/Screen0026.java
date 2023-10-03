package pama1234.gdx.game.app.app0005;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;

import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0026 extends ScreenCore2D{
  public static final PrintStream stderr=System.err,stdout=System.out;
  public static final PrintStream utilerr,utilout;
  public static StringBuffer outBuilder=new StringBuffer(),errBuilder=new StringBuffer();
  public static ArrayList<String> outText,errText;
  static {
    outText=new ArrayList<>();
    errText=new ArrayList<>();
    System.setOut(utilout=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        stderr.write(b);
        char a=(char)b;
        if(a=='\n') {
          outText.add(outBuilder.toString());
          outBuilder.setLength(0);
        }else errBuilder.append(a);
      }
    }));
    System.setErr(utilerr=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        stderr.write(b);
        char a=(char)b;
        if(a=='\n') {
          errText.add(errBuilder.toString());
          errBuilder.setLength(0);
        }else errBuilder.append(a);
      }
    }));
  }
  {
    isAndroid=true;
  }
  @Override
  public void setup() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      
      test_3();
    }
  }
  public void test_3() {
    // JavaParser has a minimal logging class that normally logs nothing.
    // Let's ask it to write to standard out:
    Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
    // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
    // In this case the root directory is found by taking the root from the current Maven module,
    // with src/main/resources appended.
    // System.out.println(Gdx.files.internal("java/test0002").exists());
    // System.out.println(FileHandle.class.getResource("/"+Gdx.files.internal("java/test0002").file().getPath().replace('\\','/')));
    // SourceRoot sourceRoot=new SourceRoot(Gdx.files.internal("java/test0002").file().toPath());
    SourceRoot sourceRoot;
    sourceRoot=new SourceRoot(FileUtil.assetToPath(Gdx.files.internal("java/test0002")));
    // Our sample is in the root of this directory, so no package name. 
    CompilationUnit cu=sourceRoot.parse("","Blabla.javad");
    Log.info("Positivizing!");
    cu.accept(new ModifierVisitor<Void>() {
      /**
       * For every if-statement, see if it has a comparison using "!=". Change it to "==" and switch
       * the "then" and "else" statements around.
       */
      @Override
      public Visitable visit(IfStmt n,Void arg) {
        // Figure out what to get and what to cast simply by looking at the AST in a debugger!
        n.getCondition().ifBinaryExpr(binaryExpr-> {
          if(binaryExpr.getOperator()==BinaryExpr.Operator.NOT_EQUALS&&n.getElseStmt().isPresent()) {
            /*
             * It's a good idea to clone nodes that you move around. JavaParser (or you) might get confused
             * about who their parent is!
             */
            Statement thenStmt=n.getThenStmt().clone();
            Statement elseStmt=n.getElseStmt().get().clone();
            n.setThenStmt(elseStmt);
            n.setElseStmt(thenStmt);
            binaryExpr.setOperator(BinaryExpr.Operator.EQUALS);
          }
        });
        return super.visit(n,arg);
      }
    },null);
    // This saves all the files we just read to an output directory.
    sourceRoot.saveAll(
      Gdx.files.local("data/cide").file().toPath()
        // appended with a path to "output"
        .resolve(Paths.get("output")));
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    text(outText.size()+" "+errText.size(),0,-40);
    for(int i=0;i<outText.size();i++) {
      text(Integer.toString(outText.get(i).length()),-60,i*20);
      text(outText.get(i),0,i*20);
    }
    for(int i=0;i<errText.size();i++) {
      text(Integer.toString(errText.get(i).length()),580,40+i*20);
      text(errText.get(i),640,40+i*20);
    }
  }
  @Override
  public void frameResized() {}
}
