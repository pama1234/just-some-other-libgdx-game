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

uniform vec2 resolution;

uniform sampler2D tiles;
uniform sampler2D tilesData;

uniform vec2 tileSize;

vec2 flip(vec2 tIn) {
  return vec2(tIn.x,1.0-tIn.y);
}

void main() {
  // gl_FragColor=vec4(v_texCoords.x,v_texCoords.y,0.0,1.0);
  // gl_FragColor=v_color;
  // gl_FragColor=texture2D(tiles,v_texCoords);
  // gl_FragColor=texture2D(tiles,v_texCoords);
  // gl_FragColor=texture2D(tilesData,v_texCoords);
  vec4 tileDataCell=texture2D(tilesData,v_texCoords);
  // tileDataCell.xy*=resolution;
  // tileDataCell.xy*=tileSize;
  // vec4 tileDataCell=flip(texture2D(tilesData,v_texCoords));
  gl_FragColor=texture2D(tiles,mod(v_texCoords,tileDataCell.xy));
  // gl_FragColor=texture2D(tiles,flip(mod(v_texCoords,tileDataCell.xy)));
  // gl_FragColor=texture2D(tiles,v_texCoords)*texture2D(tilesData,v_texCoords);
}