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

vec2 toPolar(vec2 data) {
  float tempx = data.x * PI_2;
  float x = data.y * sin(tempx);
  float y = data.y * cos(tempx + PI);
  return vec2(x, y) + u_dist;
}

vec2 toCartesian(vec2 data) {
  return vec2(length(data - u_dist), 
    atan(-(data.y - u_dist.y) / (data.x - u_dist.x)));
}

void main() {
  vec2 outputCoord = toPolar(v_texCoords);
  vec4 color = texture2D(u_texture, outputCoord);
  gl_FragColor = v_color * color;
}