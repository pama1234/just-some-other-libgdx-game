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

const float PI   = 3.141592653589;
const float PI_2 = 6.283185307179;

vec2 toCartesian(vec2 data) {
  float x = data.x * sin(data.y * PI_2);
  float y = data.x * cos(data.y * PI_2);
  return vec2(x, y) + u_dist;
}

vec2 toPolarCoord(vec2 data) {
  return vec2(length(data - u_dist), 
    atan(-(data.y - u_dist.y) / (data.x - u_dist.x)));
}

void main() {
  vec2 outputCoord = toCartesian(v_texCoords);
  vec4 color = texture2D(u_texture, outputCoord);
  gl_FragColor = v_color * color;
}