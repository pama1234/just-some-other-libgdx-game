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

uniform float pixels;
uniform float rotation;
uniform vec2 light_origin;
uniform float time_speed;
uniform float dither_size;
uniform bool should_dither;
uniform float light_border_1;
uniform float light_border_2;
uniform float river_cutoff;

uniform vec4 col1;
uniform vec4 col2;
uniform vec4 col3;
uniform vec4 col4;
uniform vec4 river_col;
uniform vec4 river_col_dark;

uniform float size;
uniform int OCTAVES;
uniform float seed1;

uniform float time;

uniform float seed;

float rand(vec2 coord) {
	// land has to be tiled (or the contintents on this planet have to be changing very fast)
	// tiling only works for integer values, thus the rounding
	// it would probably be better to only allow integer sizes
	// multiply by vec2(2,1) to simulate planet having another side
	coord = mod(coord, vec2(2.0,1.0)*floor(size+0.5));
	// coord = mod(coord, vec2(2.0,1.0)*round(size));
	return fract(sin(dot(coord.xy ,vec2(12.9898,78.233))) * 15.5453 * seed);
}

float noise(vec2 coord){
	vec2 i = floor(coord);
	vec2 f = fract(coord);
		
	float a = rand(i);
	float b = rand(i + vec2(1.0, 0.0));
	float c = rand(i + vec2(0.0, 1.0));
	float d = rand(i + vec2(1.0, 1.0));

	vec2 cubic = f * f * (3.0 - 2.0 * f);

	return mix(a, b, cubic.x) + (c - a) * cubic.y * (1.0 - cubic.x) + (d - b) * cubic.x * cubic.y;
}

float fbm(vec2 coord){
	float value = 0.0;
	float scale = 0.5;

	for(int i = 0; i < OCTAVES ; i++){
		value += noise(coord) * scale;
		coord *= 2.0;
		scale *= 0.5;
	}
	return value;
}

vec2 spherify(vec2 uv) {
	vec2 centered = uv *2.0-1.0;
	float z = sqrt(1.0 - dot(centered.xy, centered.xy));
//	float z = pow(1.0 - dot(centered.xy, centered.xy), 0.5);
	vec2 sphere = centered/(z + 1.0);
	
	return sphere * 0.5+0.5;
}

vec2 rotate(vec2 coord, float angle){
	coord -= 0.5;
	coord *= mat2(vec2(cos(angle),-sin(angle)),vec2(sin(angle),cos(angle)));
	return coord + 0.5;
}

bool dither(vec2 uv1, vec2 uv2) {
	return mod(uv1.x+uv2.y,2.0/pixels) <= 1.0 / pixels;
}

void main() {
	// pixelize uv
	vec2 uv = floor(v_texCoords*pixels)/pixels;
	
	bool dith = dither(uv, v_texCoords);
	// stepping over 0.5 instead of 0.49999 makes some pixels a little buggy
	float a = step(length(uv-vec2(0.5)), 0.49999);
	
	// map to sphere
	uv = spherify(uv);
	float d_light = distance(uv , light_origin);
	
	// give planet a tilt
	uv = rotate(uv, rotation);
	
	// some scrolling noise for landmasses
	vec2 base_fbm_uv = (uv)*size+vec2(time*time_speed,0.0);
	
	// use multiple fbm's at different places so we can determine what color land gets
	float fbm1 = fbm(base_fbm_uv);
	float fbm2 = fbm(base_fbm_uv - light_origin*fbm1);
	float fbm3 = fbm(base_fbm_uv - light_origin*1.5*fbm1);
	float fbm4 = fbm(base_fbm_uv - light_origin*2.0*fbm1);
	
	float river_fbm = fbm(base_fbm_uv+fbm1*6.0);
	river_fbm = step(river_cutoff, river_fbm);
	
	// size of edge in which colors should be dithered
	float dither_border = (1.0/pixels)*dither_size;
	// lots of magic numbers here
	// you can mess with them, it changes the color distribution
	if (d_light < light_border_1) {
		fbm4 *= 0.9;
	}
	if (d_light > light_border_1) {
		fbm2 *= 1.05;
		fbm3 *= 1.05;
		fbm4 *= 1.05;
	} 
	if (d_light > light_border_2) {
		fbm2 *= 1.3;
		fbm3 *= 1.4;
		fbm4 *= 1.8;
		
		if (d_light < light_border_2 + dither_border) {
			if (dith || !should_dither) {
				fbm4 *= 0.5;
			}
		}
		
	} 
	
	// increase contrast on d_light
	d_light = pow(d_light, 2.0)*0.4;
	vec4 col = col4;
	if (fbm4 + d_light < fbm1*1.5) {
		col = col3;
	}
	if (fbm3 + d_light < fbm1*1.0) {
		col = col2;
	}
	if (fbm2 + d_light < fbm1) {
		col = col1;
	}
	if (river_fbm < fbm1*0.5) {
		col = river_col_dark;
		if (fbm4 + d_light < fbm1*1.5) {
			col = river_col;
		}
	}

	gl_FragColor = vec4(col.rgb, a * col.a);
}
