#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform vec2 u_center;
varying vec2 v_texCoord0;
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
    vec2 polarCoord = vec2(length(v_texCoord0 - u_center), atan((v_texCoord0.y - u_center.y) / (v_texCoord0.x - u_center.x)));
    vec2 cartesianCoord = polarToCartesian(polarCoord);
    vec2 screenCoord = cartesianToScreen(cartesianCoord);
    vec4 color = texture2D(u_texture, v_texCoord0);
    gl_FragColor = color;
    gl_FragCoord = vec4(screenCoord, 0.0, 1.0);
}