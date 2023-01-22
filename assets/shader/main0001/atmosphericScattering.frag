#ifdef GL_ES
#define LOWP lowp
precision mediump float;
precision mediump int;
#else
#define LOWP
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float iTime;
uniform vec2 iCam;
uniform vec3 iResolution;

#define PLANET_POS vec3(0.0) 
#define PLANET_RADIUS 6371e3 
#define ATMOS_RADIUS 6471e3 
#define RAY_BETA vec3(5.5e-6, 13.0e-6, 22.4e-6) 
#define MIE_BETA vec3(21e-6) 
#define AMBIENT_BETA vec3(0.0) 
#define ABSORPTION_BETA vec3(2.04e-5, 4.97e-5, 1.95e-6) 
#define G 0.7 
#define HEIGHT_RAY 8e3 
#define HEIGHT_MIE 1.2e3 
#define HEIGHT_ABSORPTION 30e3 
#define ABSORPTION_FALLOFF 4e3 

#ifdef HW_PERFORMANCE
#if HW_PERFORMANCE==0
#define PRIMARY_STEPS 12 
#define LIGHT_STEPS 4
# else
#define PRIMARY_STEPS 32 
#define LIGHT_STEPS 8 
#endif
# else
#define PRIMARY_STEPS 12 
#define LIGHT_STEPS 4 
#endif

#define CAMERA_MODE 0

vec3 calculate_scattering(
	vec3 start, 				
    vec3 dir, 					
    float max_dist, 			
    vec3 scene_color,			
    vec3 light_dir, 			
    vec3 light_intensity,		
    vec3 planet_position, 		
    float planet_radius, 		
    float atmo_radius, 			
    vec3 beta_ray, 				
    vec3 beta_mie, 				
    vec3 beta_absorption,   	
    vec3 beta_ambient,			
    float g, 					
    float height_ray, 			
    float height_mie, 			
    float height_absorption,	
    float absorption_falloff,	
    int steps_i, 				
    int steps_l 				
) {
    start -= planet_position;
    float a = dot(dir, dir);
    float b = 2.0 * dot(dir, start);
    float c = dot(start, start) - (atmo_radius * atmo_radius);
    float d = (b * b) - 4.0 * a * c;
    if (d < 0.0) return scene_color;
    vec2 ray_length = vec2(
        max((-b - sqrt(d)) / (2.0 * a), 0.0),
        min((-b + sqrt(d)) / (2.0 * a), max_dist)
    );
    if (ray_length.x > ray_length.y) return scene_color;
    bool allow_mie = max_dist > ray_length.y;
    ray_length.y = min(ray_length.y, max_dist);
    ray_length.x = max(ray_length.x, 0.0);
    float step_size_i = (ray_length.y - ray_length.x) / float(steps_i);
    float ray_pos_i = ray_length.x + step_size_i * 0.5;
    vec3 total_ray = vec3(0.0); 
    vec3 total_mie = vec3(0.0); 
    vec3 opt_i = vec3(0.0);
    vec2 scale_height = vec2(height_ray, height_mie);
    float mu = dot(dir, light_dir);
    float mumu = mu * mu;
    float gg = g * g;
    float phase_ray = 3.0 / (50.2654824574 ) * (1.0 + mumu);
    float phase_mie = allow_mie ? 3.0 / (25.1327412287 ) * ((1.0 - gg) * (mumu + 1.0)) / (pow(1.0 + gg - 2.0 * mu * g, 1.5) * (2.0 + gg)) : 0.0;
    for (int i = 0; i < steps_i; ++i) {
                vec3 pos_i = start + dir * ray_pos_i;
                float height_i = length(pos_i) - planet_radius;
                vec3 density = vec3(exp(-height_i / scale_height), 0.0);
                        float denom = (height_absorption - height_i) / absorption_falloff;
        density.z = (1.0 / (denom * denom + 1.0)) * density.x;
                    density *= step_size_i;
                opt_i += density;
                        a = dot(light_dir, light_dir);
        b = 2.0 * dot(light_dir, pos_i);
        c = dot(pos_i, pos_i) - (atmo_radius * atmo_radius);
        d = (b * b) - 4.0 * a * c;
                float step_size_l = (-b + sqrt(d)) / (2.0 * a * float(steps_l));
                float ray_pos_l = step_size_l * 0.5;
            vec3 opt_l = vec3(0.0);
                        for (int l = 0; l < steps_l; ++l) {
                    vec3 pos_l = pos_i + light_dir * ray_pos_l;
                    float height_l = length(pos_l) - planet_radius;
                                    vec3 density_l = vec3(exp(-height_l / scale_height), 0.0);
                            float denom = (height_absorption - height_l) / absorption_falloff;
            density_l.z = (1.0 / (denom * denom + 1.0)) * density_l.x;
                            density_l *= step_size_l;
                            opt_l += density_l;
                            ray_pos_l += step_size_l;
                }
                    vec3 attn = exp(-beta_ray * (opt_i.x + opt_l.x) - beta_mie * (opt_i.y + opt_l.y) - beta_absorption * (opt_i.z + opt_l.z));
            total_ray += density.x * attn;
        total_mie += density.y * attn;
            ray_pos_i += step_size_i;
    	
    }
    vec3 opacity = exp(-(beta_mie * opt_i.y + beta_ray * opt_i.x + beta_absorption * opt_i.z));
	
    return (
        	phase_ray * beta_ray * total_ray 
       		+ phase_mie * beta_mie * total_mie 
            + opt_i.x * beta_ambient 
    ) * light_intensity + scene_color * opacity; 
}

vec2 ray_sphere_intersect(
    vec3 start, 
    vec3 dir, 
    float radius 
) {
    float a = dot(dir, dir);
    float b = 2.0 * dot(dir, start);
    float c = dot(start, start) - (radius * radius);
    float d = (b*b) - 4.0*a*c;
    if (d < 0.0) return vec2(1e5,-1e5);
    return vec2(
        (-b - sqrt(d))/(2.0*a),
        (-b + sqrt(d))/(2.0*a)
    );
}

vec3 skylight(vec3 sample_pos, vec3 surface_normal, vec3 light_dir, vec3 background_col) {
    surface_normal = normalize(mix(surface_normal, light_dir, 0.6));
    return calculate_scattering(
    	sample_pos,						
        surface_normal, 				
        3.0 * ATMOS_RADIUS, 			
        background_col,					
        light_dir,						
        vec3(40.0),						
        PLANET_POS,						
        PLANET_RADIUS,                      ATMOS_RADIUS,                       RAY_BETA,						
        MIE_BETA,                           ABSORPTION_BETA,                    AMBIENT_BETA,					
        G,                          	
        HEIGHT_RAY,                         HEIGHT_MIE,                         HEIGHT_ABSORPTION,				
        ABSORPTION_FALLOFF,				
        LIGHT_STEPS, 					
        LIGHT_STEPS 					
    );
}
vec4 render_scene(vec3 pos, vec3 dir, vec3 light_dir) {
    vec4 color = vec4(0.0, 0.0, 0.0, 1e12);
    color.xyz = vec3(dot(dir, light_dir) > 0.9998 ? 3.0 : 0.0);
    vec2 planet_intersect = ray_sphere_intersect(pos - PLANET_POS, dir, PLANET_RADIUS); 
    if (0.0 < planet_intersect.y) {
    	color.w = max(planet_intersect.x, 0.0);
                vec3 sample_pos = pos + (dir * planet_intersect.x) - PLANET_POS;
                vec3 surface_normal = normalize(sample_pos);
                color.xyz = vec3(0.0, 0.25, 0.05); 
                vec3 N = surface_normal;
        vec3 V = -dir;
        vec3 L = light_dir;
        float dotNV = max(1e-6, dot(N, V));
        float dotNL = max(1e-6, dot(N, L));
        float shadow = dotNL / (dotNL + dotNV);
                color.xyz *= shadow;
                color.xyz += clamp(skylight(sample_pos, surface_normal, light_dir, vec3(0.0)) * vec3(0.0, 0.25, 0.05), 0.0, 1.0);
    }
	return color;
}
vec3 get_camera_vector() {
    return normalize(vec3(v_texCoords.x - 0.5, v_texCoords.y - 0.5, -1.0))+vec3(iCam, 0);
}

void main() {
    vec3 camera_vector = get_camera_vector();
#if CAMERA_MODE==0
    vec3 camera_position = vec3(0.0, PLANET_RADIUS + 100.0, 0.0);
#endif
#if CAMERA_MODE==1
    vec3 camera_position = vec3(0.0, ATMOS_RADIUS , ATMOS_RADIUS);
#endif
#if CAMERA_MODE==2
    vec3 camera_position = vec3(0.0, ATMOS_RADIUS + (-cos(iTime / 2.0) * (ATMOS_RADIUS - PLANET_RADIUS - 1.0)), 0.0);
#endif
#if CAMERA_MODE==3
    float offset = (1.0 - cos(iTime / 2.0)) * ATMOS_RADIUS;
    vec3 camera_position = vec3(0.0, PLANET_RADIUS + 1.0, offset);
#endif
    vec3 light_dir = normalize(vec3(0.0, cos(-iTime / 8.0), sin(-iTime / 8.0)));
    vec4 scene = render_scene(camera_position, camera_vector, light_dir);
    vec3 col = vec3(0.0);
    col += calculate_scattering(
    	camera_position,				
        camera_vector, 					
        scene.w, 						
        scene.xyz,						
        light_dir,						
        vec3(40.0),						
        PLANET_POS,						
        PLANET_RADIUS, ATMOS_RADIUS,    RAY_BETA,						
        MIE_BETA,      ABSORPTION_BETA, AMBIENT_BETA,					
        G,                          	
        HEIGHT_RAY,    HEIGHT_MIE,      HEIGHT_ABSORPTION,				
        ABSORPTION_FALLOFF,				
        PRIMARY_STEPS, 					
        LIGHT_STEPS 					
    );
        col = 1.0 - exp(-col);
    gl_FragColor = vec4(col, 1.0);
}