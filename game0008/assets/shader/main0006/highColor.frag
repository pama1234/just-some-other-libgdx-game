#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
// precision highp sampler2D;
#else
#define LOWP
#define HIGHP
#endif

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;
// uniform HIGHP sampler2D u_texture;

void main() {
  vec4 outColor=texture2D(u_texture, v_texCoords);
  gl_FragColor = v_color * outColor;
}