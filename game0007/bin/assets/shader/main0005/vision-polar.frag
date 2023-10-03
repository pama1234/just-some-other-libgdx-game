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

const float HALF_PI      = 1.570796326794;
const float      PI      = 3.141592653589;
const float      PI_2    = 6.283185307179;

vec2 toPolar(vec2 data) {
  float tempx = data.x * PI_2 + HALF_PI;
  float x = data.y * sin(tempx);
  float y = data.y * cos(tempx + PI);
  return vec2(x, y) + u_dist;
}

bool inRange(float a, float b, float c) {
  return a>b&&a<c;
}

void main() {
  vec2 outputCoord = toPolar(v_texCoords);
  if(
      !inRange(outputCoord.x, 0.0, 1.0)||
      !inRange(outputCoord.y, 0.0, 1.0)) {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
    return;
  }
  vec4 color = texture2D(u_texture, outputCoord);
  gl_FragColor = v_color * color;
}