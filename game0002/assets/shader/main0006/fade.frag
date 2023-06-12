#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
// precision highp sampler2D;
#else
#define LOWP
#define HIGHP
#endif

uniform float fadeStepSlow;
uniform float fadeStep;
uniform float fadeThreshold;
uniform float voidThreshold;

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;
// uniform HIGHP sampler2D u_texture;

void main() {
  vec4 outColor=texture2D(u_texture, v_texCoords);
  // if(outColor.a>0) {
  if(outColor.a>fadeThreshold) outColor.a-=fadeStepSlow;
  else outColor.a-=fadeStep;

  if(outColor.a<=voidThreshold) {
    outColor.r=outColor.g=outColor.b=0.0;
    outColor.a=0.0;
  }
  // }
  gl_FragColor = v_color * outColor;
}