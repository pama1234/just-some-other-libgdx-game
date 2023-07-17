package pama1234.gdx.game.textmate.test;

import java.lang.reflect.Type;
import java.util.List;

import org.eclipse.tm4e.core.internal.matcher.Matcher;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pama1234.util.function.ExecuteFunction;

/**
 * VSCode TextMate matcher tests
 *
 * @see <a href=
 *      "https://github.com/Microsoft/vscode-textmate/blob/master/src/tests/matcher.test.ts">
 *      github.com/Microsoft/vscode-textmate/blob/master/src/tests/matcher.test.ts</a>
 */
public class MatcherTest{
  public static final class MatcherTestImpl implements ExecuteFunction{
    String expression;
    List<String> input;
    boolean result;
    @Override
    public void execute() {
      final var matcher=Matcher.createMatchers(expression);
      final boolean result=matcher.stream().anyMatch(m->m.matcher.matches(input));
      // assertEquals(result,this.result);
      System.out.println(result+" "+this.result);
    }
    // @Override
    // public String toString() {
    //   final var matcher=Matcher.createMatchers(expression);
    //   final boolean result=matcher.stream().anyMatch(m->m.matcher.matches(input));
    //   // assertEquals(result,this.result);
    //   return (result+" "+this.result);
    // }
    @Override
    public String toString() {
      final var matcher=Matcher.createMatchers(expression);
      final boolean result=matcher.stream().anyMatch(m->m.matcher.matches(input));
      // assertEquals(result,this.result);
      return "[expression=\""+expression+"\", input="+input+", result="+this.result+" "+result+"]";
    }
  }
  // @TestFactory
  // @DisplayName("Matcher tests")
  public static void matcherTests() throws Exception {
    final String jsonTests="""
      [
      	{ "expression": "foo", "input": ["foo"], "result": true },
      	{ "expression": "foo", "input": ["bar"], "result": false },
      	{ "expression": "- foo", "input": ["foo"], "result": false },
      	{ "expression": "- foo", "input": ["bar"], "result": true },
      	{ "expression": "- - foo", "input": ["bar"], "result": false },
      	{ "expression": "bar foo", "input": ["foo"], "result": false },
      	{ "expression": "bar foo", "input": ["bar"], "result": false },
      	{ "expression": "bar foo", "input": ["bar", "foo"], "result": true },
      	{ "expression": "bar - foo", "input": ["bar"], "result": true },
      	{ "expression": "bar - foo", "input": ["foo", "bar"], "result": false },
      	{ "expression": "bar - foo", "input": ["foo"], "result": false },
      	{ "expression": "bar, foo", "input": ["foo"], "result": true },
      	{ "expression": "bar, foo", "input": ["bar"], "result": true },
      	{ "expression": "bar, foo", "input": ["bar", "foo"], "result": true },
      	{ "expression": "bar, -foo", "input": ["bar", "foo"], "result": true },
      	{ "expression": "bar, -foo", "input": ["yo"], "result": true },
      	{ "expression": "bar, -foo", "input": ["foo"], "result": false },
      	{ "expression": "(foo)", "input": ["foo"], "result": true },
      	{ "expression": "(foo - bar)", "input": ["foo"], "result": true },
      	{ "expression": "(foo - bar)", "input": ["foo", "bar"], "result": false },
      	{ "expression": "foo bar - (yo man)", "input": ["foo", "bar"], "result": true },
      	{ "expression": "foo bar - (yo man)", "input": ["foo", "bar", "yo"], "result": true },
      	{ "expression": "foo bar - (yo man)", "input": ["foo", "bar", "yo", "man"], "result": false },
      	{ "expression": "foo bar - (yo | man)", "input": ["foo", "bar", "yo", "man"], "result": false },
      	{ "expression": "foo bar - (yo | man)", "input": ["foo", "bar", "yo"], "result": false }
      ]
      """;
    final Type listType=new TypeToken<List<MatcherTestImpl>>() {
    }.getType();
    final List<MatcherTestImpl> tests=new GsonBuilder().create().fromJson(jsonTests,listType);
    // final ArrayList<ExecuteFunction> dynamicTests=new ArrayList<ExecuteFunction>();
    for(int i=0;i<tests.size();i++) {
      // dynamicTests.add(()-> {
      System.out.println("Test #"+(i+1)+" "+tests.get(i).toString());
      // });
    }
    // return dynamicTests;
  }
}
