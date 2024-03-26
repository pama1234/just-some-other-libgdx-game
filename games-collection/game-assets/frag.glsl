#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif
#define near 1e-5
#define far 300.
uniform vec2 resolution;
uniform float time;

float hash12(vec2 p) {
    vec2 p2 = 55.1876653 * fract(p * 10.1321513);
    return fract((p2.x + p2.y) * p2.x * p2.y);
}

float rand(vec2 pos) {
    //return abs(fract(78523.215 * sin(dot(pos, vec2(25.32, 547.23)))));
    return hash12(pos);
}

vec2 fixuv(vec2 uv){
	return (2.*uv -resolution.xy)/min(resolution.x,resolution.y);
}
vec3 noise(vec2 p){
	vec2 i=floor(p);
	vec2 f=fract(p);
	vec2 u=f*f*(3.-2.*f);
	vec2 du=6.*u*(1.-u);
	float a=rand(i);
	float b=rand(i+vec2(1.0,0.));
	float c=rand(i+vec2(.0,1.));
	float d=rand(i+vec2(1.0,1.));

	return vec3(mix(a,b,u.x)+(c-a)*u.y*(1.-u.x)+(d-b)*u.x*u.y,
		du*(vec2(b-a,c-a)+(a-b-c+d)*u.yx));
}
mat2 rtr=mat2(.6,-.8,.8,.6);
float ground(vec2 x,int it){
	vec2 p=x*0.003;
	float a=0.;
	float b=1.;
	vec2 d=vec2(0);
	for(int i=0;i<it;i++){
		vec3 n=noise(p);
		d+=n.yz;
		a+=b*n.x/(1.+dot(d,d));
		p=rtr*p*2.;
		b*=.5;
	}
	return a*120.;
	//return sin(p.x)*sin(p.y)*5.*sin(time*4.+2.);
}

float ray(vec3 ro, vec3 rd, float tmin, float tmax) {
    float t = tmin;
    for(int i = 0; i < 100; i++) {
        vec3 p = ro + t * rd;
        float h = p.y - ground(p.xz,8);
        if(abs(h) < near * t || t > tmax)
            break;
        t += 0.4 * h;
    }
    return t;
}
mat3 getCam(vec3 ro,vec3 tar,float cr){
	vec3 z=normalize(tar-ro);
	vec3 up=normalize(vec3(sin(cr),cos(cr),0));
	vec3 x=cross(z,up);
	vec3 y=cross(x,z);
	return mat3(x,y,z);
}
vec3 caluNorm(vec3 p,float t){
	vec2 el=vec2(.001*t,0);
	int it=16;
	return normalize(vec3(
		ground(p.xz+el,it)-ground(p.xz-el,it),
		2.*el.x,
		ground(p.xz+el.yx,it)-ground(p.xz-el.yx,it)));
}
vec3 render(vec2 uv){
	vec3 col=vec3(0);
	float an = time * 0.05;
    float r = 500.;
    vec2 pos2d = vec2(r * sin(an), r * cos(an));
    float h = ground(pos2d,3) + 22.;
    vec3 ro = vec3(pos2d.x, h, pos2d.y);
    vec3 target = vec3(r * sin(an + 0.01), h, r * cos(an + 0.01));
    mat3 cam = getCam(ro, target, 0.);

	vec3 rd=normalize(cam*vec3(uv,1));
	float t=ray(ro,rd,near,far);
	vec3 p=ro+rd*t;
	if(t<far){
		vec3 n=caluNorm(p,t);
		vec3 amc=vec3(.67,.57,.44);
		col=amc*dot(n,vec3(0,1,0));
	}
	return col;
}
void main(void) {
	vec2 uv = fixuv(gl_FragCoord.xy);
	//uv+=sin(-time)*.2;
	vec3 col=render(uv);
	gl_FragColor = vec4(1.-exp(-col*2.),1.0);
}
