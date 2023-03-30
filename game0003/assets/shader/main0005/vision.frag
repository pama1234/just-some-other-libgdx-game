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

uniform vec2 u_dist;

vec2 toCartesian(vec2 data) {
  float x = data.x * cos(data.y);
  float y = data.x * sin(data.y);
  return vec2(x, y) - u_dist;
}

vec2 toPolarCoord(vec2 data) {
  return vec2(length(data - u_dist), 
    atan(-(data.y - u_dist.y) / (data.x - u_dist.x)));
}

void main() {
  vec2 polarCoord = toCartesian(v_texCoords);
  vec4 color = texture2D(u_texture, polarCoord);
  gl_FragColor = v_color * color;
}