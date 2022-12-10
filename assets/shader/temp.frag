#ifdef GL_ES
#define LOWP lowp
precision mediump float;
precision mediump int;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
//---
uniform float iTime;
uniform vec4 iMouse;
uniform vec3 iResolution;

float distanceToSegment( vec2 a, vec2 b, vec2 p )
{
	vec2 pa = p - a, ba = b - a;
	float h = clamp( dot(pa,ba)/dot(ba,ba), 0.0, 1.0 );
	return length( pa - ba*h );
}

// void mainImage( out vec4 fragColor, in vec2 fragCoord )
void main()
{
	vec2 p = v_texCoords;
    vec2 cen = 0.5*iResolution.xy/iResolution.x;
    vec4 m = iMouse / iResolution.x;
	
	vec3 col = vec3(0.0);

	if( m.z>0.0 ) // button is down
	{
		float d = distanceToSegment( m.xy, abs(m.zw), p );
        col = mix( col, vec3(1.0,1.0,0.0), 1.0-smoothstep(.004,0.008, d) );
	}
	if( m.w>0.0 ) // button click
	{
        col = mix( col, vec3(1.0,1.0,1.0), 1.0-smoothstep(0.1,0.105, length(p-cen)) );
    }

	col = mix( col, vec3(1.0,0.0,0.0), 1.0-smoothstep(0.03,0.035, length(p-    m.xy )) );
    col = mix( col, vec3(0.0,0.0,1.0), 1.0-smoothstep(0.03,0.035, length(p-abs(m.zw))) );

	// fragColor = vec4( col, 1.0 );
  gl_FragColor = vec4(col, 1.0);
}