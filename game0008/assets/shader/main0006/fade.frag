#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
#else
#define LOWP
#define HIGHP
#endif

uniform HIGHP float fadeStepSlow;
uniform HIGHP float fadeStepFast;
uniform HIGHP float fadeThreshold;
uniform HIGHP float voidThresholdF;
uniform vec4 voidColor;

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  vec4 outColor=texture2D(u_texture, v_texCoords);

  if(outColor.a>fadeThreshold) outColor.a-=fadeStepSlow;
  else outColor.a-=fadeStepFast;

  if(outColor.a<voidThresholdF) {
    gl_FragColor = voidColor;
    return;
  }else gl_FragColor = v_color * outColor;
}