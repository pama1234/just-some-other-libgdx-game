#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
#else
#define LOWP
#define HIGHP
#endif

uniform float fadeStepSlow;
uniform float fadeStepFast;
uniform float fadeThreshold;
uniform float voidThresholdF;

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
  vec4 outColor=texture2D(u_texture, v_texCoords);

  if(outColor.a>fadeThreshold) {outColor.a-=fadeStepSlow;}
  else {outColor.a-=fadeStepFast;}

  if(outColor.a<voidThresholdF) {
    // outColor.r=outColor.g=outColor.b=0.0;
    // outColor.a=0.0;
    // gl_FragColor = vec4(1.0,0.0,0.0,1.0);
    gl_FragColor = vec4(0.0,0.0,0.0,0.0);
    return;
  }else

  gl_FragColor = v_color * outColor;
}