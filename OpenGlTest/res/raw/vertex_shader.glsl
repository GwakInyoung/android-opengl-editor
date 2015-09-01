varying vec3 N;
varying vec3 v;
uniform mat4 gl_ModelViewMatrix;
uniform mat4 gl_ModelViewProjectionMatrix;
uniform mat4 gl_NormalMatrix;

attribute vec3 gl_Vertex;
attribute vec3 gl_Normal;


void main(void)  
{     
	v = vec3(gl_ModelViewMatrix * gl_Vertex);       
	N = normalize(gl_NormalMatrix * gl_Normal);
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;  
}

