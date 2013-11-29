uniform mat4 u_MVPMatrix;
uniform vec4 u_Color;

attribute vec4 a_Position;

varying vec4 v_Color;

void main(){
	v_Color = a_Position;
	gl_Position = a_Position * u_MVPMatrix;
	
}