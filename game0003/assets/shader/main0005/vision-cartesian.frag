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
// uniform float debug_1;
// uniform float debug_2;

const float QUARTER_PI      =  0.785398163397;
const float    HALF_PI      =  1.570796326794;
const float         PI      =  3.141592653589;
const float         PI_2    =  6.283185307179;
const float         PI_4    = 12.566370614358;

float atan2(in float y, in float x) {
  return x > 0.0 ? atan(x,y) :HALF_PI - atan(y,x);
}

vec2 toCartesian(vec2 data) {
  return vec2(
    // (atan( -(data.y - u_dist.y) / (data.x - u_dist.x) ) + (QUARTER_PI * debug_2) ) / (QUARTER_PI * debug_1), 
    (atan2( -(data.y - u_dist.y) , (data.x - u_dist.x) ) + HALF_PI) / PI, 
    length(data - u_dist));
}

bool inRange(float a, float b, float c) {
  return a>b&&a<c;
}

void main() {
  vec2 outputCoord = toCartesian(v_texCoords);
  if(
      !inRange(outputCoord.x, 0.0, 1.0)||
      !inRange(outputCoord.y, 0.0, 1.0)) {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
    return;
  }
  vec4 color = texture2D(u_texture, outputCoord);
  gl_FragColor = v_color * color;
}