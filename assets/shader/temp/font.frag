#ifdef GL_ES
#define LOWP lowp
precision mediump float;
precision mediump int;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

//uniform int u_smoothing;
//uniform int u_range;

void main() {
//	vec2 unit = textureSize(u_texture, 0);
//	vec4 mult = texture2D(u_texture, v_texCoords);
//	vec2 offset = vec2(0, 0);
//	for (int i = -u_range; i <= u_range; i++) {
//		for (int j = -u_range; j <= u_range; j++) {
//			offset.x = i;
//			offset.y = j;
//			offset /= unit;
//			mult += texture2D(u_texture, v_texCoords + offset)
//					/ (1 + length(offset));
//		}
//	}
//	mult /= u_smoothing;
//	mult = step(0.5, mult);
//	gl_FragColor = v_color * mult;

	vec4 mult = texture2D(u_texture, v_texCoords);
	gl_FragColor = v_color * mult;

//	float smoothing = 0.25 / u_smoothing;
//	float distance = texture2D(u_texture, v_texCoords).a;
//	float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
//	gl_FragColor = vec4(v_color.rgb, alpha * v_color.a);
}
