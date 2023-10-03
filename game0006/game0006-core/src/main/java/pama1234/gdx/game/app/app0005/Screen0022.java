package pama1234.gdx.game.app.app0005;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.SimpleCompiler;

import com.badlogic.gdx.Gdx;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0022 extends ScreenCore2D{
  private static final String FILE_PATH="java/test0001/ReversePolishNotation.javad";
  @Override
  public void setup() {
    // test_1();
    test_2();
  }
  public void test_1() {
    ExpressionEvaluator ee=new ExpressionEvaluator();
    try {
      ee.cook("3 + 4");
      System.out.println(ee.evaluate()); // Prints "7".
    }catch(CompileException|InvocationTargetException e) {
      e.printStackTrace();
    }
  }
  public void test_2() {
    String javaSrcString=Gdx.files.internal(FILE_PATH).readString();
    test_3(javaSrcString);
    CompilationUnit cu=StaticJavaParser.parse(javaSrcString);
    List<CommentReportEntry> comments=cu.getAllContainedComments()
      .stream()
      .map(p->new CommentReportEntry(p.getClass().getSimpleName(),
        p.getContent(),
        p.getRange().map(r->r.begin.line).orElse(-1),
        !p.getCommentedNode().isPresent()))
      .collect(Collectors.toList());
    comments.forEach(System.out::println);
  }
  public void test_3(String in) {
    SimpleCompiler compiler=new SimpleCompiler();
    compiler.setSourceVersion(16);
    compiler.setTargetVersion(16);
    try {
      compiler.cook(in);
      System.out.println(compiler.getBytecodes());
    }catch(CompileException e) {
      e.printStackTrace();
    }
  }
  public static class CommentReportEntry{
    private String type;
    private String text;
    private int lineNumber;
    private boolean isOrphan;
    CommentReportEntry(String type,String text,int lineNumber,boolean isOrphan) {
      this.type=type;
      this.text=text;
      this.lineNumber=lineNumber;
      this.isOrphan=isOrphan;
    }
    @Override
    public String toString() {
      return lineNumber+"|"+type+"|"+isOrphan+"|"+text.replaceAll("\\n","").trim();
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
