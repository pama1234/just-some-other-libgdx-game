#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
#else
#define LOWP
#define HIGHP
#endif

const float fl=1.0/256.0;
const float fadeStep=16.0/256.0;
const float fadeThreshold=128.0/256.0;

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  vec4 output=texture2D(u_texture, v_texCoords);
  // if(output.a>0) {
  if(output.a>fadeThreshold) output.a-=fl;
  else output.a-=fadeStep;

  if(output.a<=0.0) {
    output.r=output.g=output.b=0.0;
    output.a=0.0;
  }
  // }
  gl_FragColor = v_color * output;
}