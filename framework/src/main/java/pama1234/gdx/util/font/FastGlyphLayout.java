package pama1234.gdx.util.font;

// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Colors;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
// import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

// import com.badlogic.gdx.utils.Align;
// import com.badlogic.gdx.utils.Array;
// import com.badlogic.gdx.utils.FloatArray;
// import com.badlogic.gdx.utils.IntArray;
// import com.badlogic.gdx.utils.Pool;
// import com.badlogic.gdx.utils.Pools;
// import pama1234.gdx.util.font.FontUtil.UniFontDependent;
public class FastGlyphLayout extends GlyphLayout{
  // public static final Pool<FastGlyphRun> glyphRunPool=Pools.get(FastGlyphRun.class);
  // public static final IntArray colorStack=new IntArray(4);
  // public static final float epsilon=0.0001f;
  // @UniFontDependent
  // @Override
  // public void setText(BitmapFont font,CharSequence str,int start,int end,Color color,float
  // targetWidth,int halign,boolean wrap,String truncate) {
  // // super.setText(font,str,start,end,color,targetWidth,halign,wrap,truncate);
  // reset();
  // BitmapFontData fontData=font.getData();
  // if(start==end) { // Empty string.
  // height=fontData.capHeight;
  // return;
  // }
  // // Avoid wrapping one line per character, which is very inefficient.
  // if(wrap) targetWidth=Math.max(targetWidth,fontData.spaceXadvance*3);
  // boolean wrapOrTruncate=wrap||truncate!=null;
  // int currentColor=color.toIntBits(),nextColor=currentColor;
  // colors.add(0,currentColor);
  // boolean markupEnabled=fontData.markupEnabled;
  // if(markupEnabled) colorStack.add(currentColor);
  // boolean isLastRun=false;
  // float y=0,down=fontData.down;
  // FastGlyphRun lineRun=null; // Collects glyphs for the current line.
  // Glyph lastGlyph=null; // Last glyph of the previous run on the same line, used for kerning
  // between runs.
  // int runStart=start;
  // outer:
  // while(true) {
  // int runEnd;
  // boolean newline=false;
  // if(start==end) { // End of text.
  // if(runStart==end) break; // No run to process, we're done.
  // runEnd=end; // Process the final run.
  // isLastRun=true;
  // }else {
  // // Each run is delimited by newline or left square bracket.
  // switch(str.charAt(start++)) {
  // case '\n': // End of line.
  // runEnd=start-1;
  // newline=true;
  // break;
  // case '[': // Possible color tag.
  // if(markupEnabled) {
  // int length=parseColorMarkup(str,start,end);
  // if(length>=0) {
  // runEnd=start-1;
  // start+=length+1;
  // if(start==end) isLastRun=true; // Color tag is the last element in the string.
  // else nextColor=colorStack.peek();
  // break;
  // }
  // if(length==-2) start++; // Skip first of "[[" escape sequence.
  // }
  // // Fall through.
  // default:
  // continue outer;
  // }
  // }
  // runEnded:
  // {
  // // Store the run that has ended.
  // FastGlyphRun run=glyphRunPool.obtain();
  // run.x=0;
  // run.y=y;
  // fontData.getGlyphs(run,str,runStart,runEnd,lastGlyph);
  // glyphCount+=run.glyphs.size;
  // if(nextColor!=currentColor) { // Can only be different if markupEnabled.
  // if(colors.get(colors.size-2)==glyphCount) {
  // // Consecutive color changes, or after an empty run, or at the beginning of the string.
  // colors.set(colors.size-1,nextColor);
  // }else {
  // colors.add(glyphCount);
  // colors.add(nextColor);
  // }
  // currentColor=nextColor;
  // }
  // if(run.glyphs.size==0) {
  // glyphRunPool.free(run);
  // if(lineRun==null) break runEnded; // Otherwise wrap and truncate must still be processed for
  // lineRun.
  // }else if(lineRun==null) {
  // lineRun=run;
  // runs.add(lineRun);
  // }else {
  // lineRun.appendRun(run);
  // glyphRunPool.free(run);
  // }
  // if(newline||isLastRun) {
  // setLastGlyphXAdvance(fontData,lineRun);
  // lastGlyph=null;
  // }else lastGlyph=lineRun.glyphs.peek();
  // if(!wrapOrTruncate||lineRun.glyphs.size==0) break runEnded; // No wrap or truncate, or no
  // glyphs.
  // if(newline||isLastRun) {
  // // Wrap or truncate. First xadvance is the first glyph's X offset relative to the drawing
  // position.
  // // float runWidth=lineRun.xAdvances.first()+lineRun.xAdvances.get(1); // At least the first
  // glyph will fit.
  // float runWidth=lineRun.xAdvances.first()+lineRun.xAdvances.get(1); // At least the first
  // glyph will fit.
  // // System.out.println(lineRun.xAdvances.first());
  // // float runWidth=0;
  // // fontBatch.draw(texture,
  // // v.x+glyph.xoffset*scale,
  // // v.y+glyph.yoffset*scale,
  // // glyph.width*scale,
  // // glyph.height*scale,
  // // glyph.u,glyph.v,
  // // glyph.u2,glyph.v2);
  // // posI.x+=glyph.xadvance/charWidth;
  // // posI.z+=1;
  // // v.x+=glyph.xadvance*scale;
  // for(int i=2;i<lineRun.xAdvances.size;i++) {
  // Glyph glyph=lineRun.glyphs.get(i-1);
  // float glyphWidth=getGlyphWidth(glyph,fontData);
  // if(runWidth+glyphWidth-epsilon<=targetWidth) {
  // // Glyph fits.
  // runWidth+=lineRun.xAdvances.items[i];
  // continue;
  // }
  // if(truncate!=null) {
  // // Truncate.
  // truncate(fontData,lineRun,targetWidth,truncate);
  // break outer;
  // }
  // // Wrap.
  // int wrapIndex=fontData.getWrapIndex(lineRun.glyphs,i);
  // if((wrapIndex==0&&lineRun.x==0) // Require at least one glyph per line.
  // ||wrapIndex>=lineRun.glyphs.size) { // Wrap at least the glyph that didn't fit.
  // wrapIndex=i-1;
  // }
  // lineRun=wrap(fontData,lineRun,wrapIndex);
  // if(lineRun==null) break runEnded; // All wrapped glyphs were whitespace.
  // runs.add(lineRun);
  // y+=down;
  // lineRun.x=0;
  // lineRun.y=y;
  // // Start the wrap loop again, another wrap might be necessary.
  // runWidth=lineRun.xAdvances.first()+lineRun.xAdvances.get(1); // At least the first glyph will
  // fit.
  // i=1;
  // }
  // }
  // }
  // if(newline) {
  // lineRun=null;
  // lastGlyph=null;
  // // Next run will be on the next line.
  // if(runEnd==runStart) // Blank line.
  // y+=down*fontData.blankLineScale;
  // else y+=down;
  // }
  // runStart=start;
  // }
  // height=fontData.capHeight+Math.abs(y);
  // calculateWidths(fontData);
  // alignRuns(targetWidth,halign);
  // // Clear the color stack.
  // if(markupEnabled) colorStack.clear();
  // // if(str.charAt(0)=='[') runs.get(0).x+=2;//TODO ugly
  // // System.out.println("FastGlyphLayout.setText()");
  // }
  // /**
  // * Breaks a run into two runs at the specified wrapIndex.
  // *
  // * @return May be null if second run is all whitespace.
  // */
  // public FastGlyphRun wrap(BitmapFontData fontData,FastGlyphRun first,int wrapIndex) {
  // Array<Glyph> glyphs2=first.glyphs; // Starts with all the glyphs.
  // int glyphCount=first.glyphs.size;
  // FloatArray xAdvances2=first.xAdvances; // Starts with all the xadvances.
  // // Skip whitespace before the wrap index.
  // int firstEnd=wrapIndex;
  // for(;firstEnd>0;firstEnd--) if(!fontData.isWhitespace((char)glyphs2.get(firstEnd-1).id))
  // break;
  // // Skip whitespace after the wrap index.
  // int secondStart=wrapIndex;
  // for(;secondStart<glyphCount;secondStart++)
  // if(!fontData.isWhitespace((char)glyphs2.get(secondStart).id)) break;
  // // Copy wrapped glyphs and xadvances to second run.
  // // The second run will contain the remaining glyph data, so swap instances rather than
  // copying.
  // FastGlyphRun second=null;
  // if(secondStart<glyphCount) {
  // second=glyphRunPool.obtain();
  // Array<Glyph> glyphs1=second.glyphs; // Starts empty.
  // glyphs1.addAll(glyphs2,0,firstEnd);
  // glyphs2.removeRange(0,secondStart-1);
  // first.glyphs=glyphs1;
  // second.glyphs=glyphs2;
  // FloatArray xAdvances1=second.xAdvances; // Starts empty.
  // xAdvances1.addAll(xAdvances2,0,firstEnd+1);
  // xAdvances2.removeRange(1,secondStart); // Leave first entry to be overwritten by next line.
  // xAdvances2.items[0]=getLineOffset(glyphs2,fontData);
  // first.xAdvances=xAdvances1;
  // second.xAdvances=xAdvances2;
  // int firstGlyphCount=first.glyphs.size; // After wrapping it.
  // int secondGlyphCount=second.glyphs.size;
  // int droppedGlyphCount=glyphCount-firstGlyphCount-secondGlyphCount;
  // this.glyphCount-=droppedGlyphCount;
  // if(fontData.markupEnabled&&droppedGlyphCount>0) {
  // int reductionThreshold=this.glyphCount-secondGlyphCount;
  // for(int i=colors.size-2;i>=2;i-=2) { // i >= 1 because first 2 values always determine the
  // base color.
  // int colorChangeIndex=colors.get(i);
  // if(colorChangeIndex<=reductionThreshold) break;
  // colors.set(i,colorChangeIndex-droppedGlyphCount);
  // }
  // }
  // }else {
  // // Second run is empty, just trim whitespace glyphs from end of first run.
  // glyphs2.truncate(firstEnd);
  // xAdvances2.truncate(firstEnd+1);
  // int droppedGlyphCount=secondStart-firstEnd;
  // if(droppedGlyphCount>0) {
  // this.glyphCount-=droppedGlyphCount;
  // if(fontData.markupEnabled&&colors.get(colors.size-2)>this.glyphCount) {
  // // Many color changes can be hidden in the dropped whitespace, so keep only the very last
  // color entry.
  // int lastColor=colors.peek();
  // while(colors.get(colors.size-2)>this.glyphCount) colors.size-=2;
  // colors.set(colors.size-2,this.glyphCount); // Update color change index.
  // colors.set(colors.size-1,lastColor); // Update color entry.
  // }
  // }
  // }
  // if(firstEnd==0) {
  // // If the first run is now empty, remove it.
  // glyphRunPool.free(first);
  // runs.pop();
  // }else setLastGlyphXAdvance(fontData,first);
  // return second;
  // }
  // /**
  // * Returns an X offset for the first glyph so when drawn, none of it is left of the line's
  // * drawing position.
  // */
  // private float getLineOffset(Array<Glyph> glyphs,BitmapFontData fontData) {
  // return -glyphs.first().xoffset*fontData.scaleX-fontData.padLeft;
  // // return 0;
  // }
  // /**
  // * Align runs to center or right of targetWidth. Requires run.width of runs to be already set
  // */
  // public void alignRuns(float targetWidth,int halign) {
  // if((halign&Align.left)==0) { // Not left aligned, so must be center or right aligned.
  // boolean center=(halign&Align.center)!=0;
  // Object[] runsItems=runs.items;
  // for(int i=0,n=runs.size;i<n;i++) {
  // FastGlyphRun run=(FastGlyphRun)runsItems[i];
  // run.x+=center?0.5f*(targetWidth-run.width):targetWidth-run.width;
  // }
  // }
  // }
  // public int parseColorMarkup(CharSequence str,int start,int end) {
  // if(start==end) return -1; // String ended with "[".
  // switch(str.charAt(start)) {
  // case '#':
  // // Parse hex color RRGGBBAA to an ABGR int, where AA is optional and defaults to FF if
  // omitted.
  // int color=0;
  // for(int i=start+1;i<end;i++) {
  // char ch=str.charAt(i);
  // if(ch==']') {
  // if(i<start+2||i>start+9) break; // Illegal number of hex digits.
  // if(i-start<8) color=color<<(9-(i-start)<<2)|0xff; // RRGGBB or fewer chars.
  // colorStack.add(Integer.reverseBytes(color));
  // return i-start;
  // }
  // color=(color<<4)+ch;
  // if(ch>='0'&&ch<='9') color-='0';
  // else if(ch>='A'&&ch<='F') color-='A'-10;
  // else if(ch>='a'&&ch<='f') color-='a'-10;
  // else break; // Unexpected character in hex color.
  // }
  // return -1;
  // case '[': // "[[" is an escaped left square bracket.
  // return -2;
  // case ']': // "[]" is a "pop" color tag.
  // if(colorStack.size>1) colorStack.pop();
  // return 0;
  // }
  // // Parse named color.
  // for(int i=start+1;i<end;i++) {
  // char ch=str.charAt(i);
  // if(ch!=']') continue;
  // Color color=Colors.get(str.subSequence(start,i).toString());
  // if(color==null) return -1; // Unknown color name.
  // colorStack.add(color.toIntBits());
  // return i-start;
  // }
  // return -1; // Unclosed color tag.
  // }
  // /** Calculate run widths and the entire layout width. */
  // public void calculateWidths(BitmapFontData fontData) {
  // float width=0;
  // Object[] runsItems=runs.items;
  // for(int i=0,n=runs.size;i<n;i++) {
  // FastGlyphRun run=(FastGlyphRun)runsItems[i];
  // float[] xAdvances=run.xAdvances.items;
  // float runWidth=run.x+xAdvances[0],max=0; // run.x is needed to ensure floats are rounded same
  // as above.
  // Object[] glyphs=run.glyphs.items;
  // for(int ii=0,nn=run.glyphs.size;ii<nn;) {
  // Glyph glyph=(Glyph)glyphs[ii];
  // float glyphWidth=getGlyphWidth(glyph,fontData);
  // max=Math.max(max,runWidth+glyphWidth); // A glyph can extend past the right edge of
  // subsequent glyphs.
  // ii++;
  // runWidth+=xAdvances[ii];
  // }
  // run.width=Math.max(runWidth,max)-run.x;
  // width=Math.max(width,run.x+run.width);
  // }
  // this.width=width;
  // }
  // /** Sets the xadvance of the last glyph to use its width instead of xadvance. */
  // public void setLastGlyphXAdvance(BitmapFontData fontData,FastGlyphRun run) {
  // Glyph last=run.glyphs.peek();
  // if(!last.fixedWidth) run.xAdvances.items[run.xAdvances.size-1]=getGlyphWidth(last,fontData);
  // }
  // /** Returns the distance from the glyph's drawing position to the right edge of the glyph. */
  // public float getGlyphWidth(Glyph glyph,BitmapFontData fontData) {
  // return (glyph.width+glyph.xoffset)*fontData.scaleX-fontData.padRight;
  // }
  // /** @param truncate May be empty string. */
  // public void truncate(BitmapFontData fontData,FastGlyphRun run,float targetWidth,String
  // truncate) {
  // int glyphCount=run.glyphs.size;
  // // Determine truncate string size.
  // FastGlyphRun truncateRun=glyphRunPool.obtain();
  // fontData.getGlyphs(truncateRun,truncate,0,truncate.length(),null);
  // float truncateWidth=0;
  // if(truncateRun.xAdvances.size>0) {
  // setLastGlyphXAdvance(fontData,truncateRun);
  // float[] xAdvances=truncateRun.xAdvances.items;
  // for(int i=1,n=truncateRun.xAdvances.size;i<n;i++) // Skip first for tight bounds.
  // truncateWidth+=xAdvances[i];
  // }
  // targetWidth-=truncateWidth;
  // // Determine visible glyphs.
  // int count=0;
  // float width=run.x;
  // float[] xAdvances=run.xAdvances.items;
  // while(count<run.xAdvances.size) {
  // float xAdvance=xAdvances[count];
  // width+=xAdvance;
  // if(width>targetWidth) break;
  // count++;
  // }
  // if(count>1) {
  // // Some run glyphs fit, append truncate glyphs.
  // run.glyphs.truncate(count-1);
  // run.xAdvances.truncate(count);
  // setLastGlyphXAdvance(fontData,run);
  // if(truncateRun.xAdvances.size>0)
  // run.xAdvances.addAll(truncateRun.xAdvances,1,truncateRun.xAdvances.size-1);
  // }else {
  // // No run glyphs fit, use only truncate glyphs.
  // run.glyphs.clear();
  // run.xAdvances.clear();
  // run.xAdvances.addAll(truncateRun.xAdvances);
  // }
  // int droppedGlyphCount=glyphCount-run.glyphs.size;
  // if(droppedGlyphCount>0) {
  // this.glyphCount-=droppedGlyphCount;
  // if(fontData.markupEnabled) {
  // while(colors.size>2&&colors.get(colors.size-2)>=this.glyphCount) colors.size-=2;
  // }
  // }
  // run.glyphs.addAll(truncateRun.glyphs);
  // this.glyphCount+=truncate.length();
  // glyphRunPool.free(truncateRun);
  // }
  // /**
  // * Stores glyphs and positions for a line of text.
  // *
  // * @author Nathan Sweet
  // */
  // public static class FastGlyphRun extends GlyphRun{
  // public void appendRun(FastGlyphRun run) {
  // glyphs.addAll(run.glyphs);
  // // Remove the width of the last glyph. The first xadvance of the appended run has kerning for
  // the last glyph of this run.
  // if(xAdvances.notEmpty()) xAdvances.size--;
  // xAdvances.addAll(run.xAdvances);
  // }
  // }
}
