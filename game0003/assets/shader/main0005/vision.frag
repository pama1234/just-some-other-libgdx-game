#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform vec2 u_center;
varying vec2 v_texCoords;
vec2 polarToCartesian(vec2 polar) {
  float x = polar.x * cos(polar.y);
  float y = polar.x * sin(polar.y);
  return vec2(x, y);
}
vec2 cartesianToScreen(vec2 coord) {
  vec2 zeroToOne = coord / u_resolution;
  return zeroToOne * 2.0 - 1.0;
}
vec2 screenToCartesian(vec2 coord) {
  vec2 zeroToOne = (coord + 1.0) / 2.0;
    return zeroToOne * u_resolution;
}
void main() {
  vec2 polarCoord = vec2(length(v_texCoords - u_center), 
    atan((v_texCoords.y - u_center.y) / (v_texCoords.x - u_center.x)));
  vec2 cartesianCoord = polarToCartesian(polarCoord);
  vec2 screenCoord = cartesianToScreen(cartesianCoord);
  vec4 color = texture2D(u_texture, v_texCoords);
  gl_FragColor = color;
}