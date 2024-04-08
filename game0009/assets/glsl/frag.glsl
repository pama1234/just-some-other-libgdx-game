varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float time;

const float PI = 3.1415926;

void main (void) {
    float duration = .1;
    float time =time;
    vec4 mask = texture2D(u_texture, v_texCoords);
    if(time>duration) gl_FragColor =mask;
    else{
    vec4 whiteMask = vec4(1.0, 1.0, 1.0, 1.0);
    float amplitude = abs(sin(time * (PI / duration)));
    gl_FragColor = mask * (1.0 - amplitude) + whiteMask * amplitude;
    }
}