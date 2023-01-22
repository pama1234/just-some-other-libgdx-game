#ifdef GL_ES
#define LOWP lowp
#define HIGHP highp
precision highp float;
#else
#define LOWP
#define HIGHP
#endif

varying LOWP vec4 v_color;
varying HIGHP vec2 v_texCoords;
uniform sampler2D u_texture;

uniform sampler2D tiles;
uniform sampler2D tilesData;

uniform vec2 resolution;

void main() {
  // gl_FragColor=vec4(
  //   v_texCoords.x*resolution.x/255.0,
  //   v_texCoords.y*resolution.y/255.0,
  //   0.0,1.0);
  gl_FragColor=vec4(v_texCoords.x,v_texCoords.y,0.0,1.0);
  // gl_FragColor=v_color*texture2D(tiles,v_texCoords);
  // gl_FragColor=v_color*texture2D(tilesData,v_texCoords);
  // gl_FragColor=v_color*texture2D(tiles,v_texCoords)*texture2D(tilesData,v_texCoords);
}