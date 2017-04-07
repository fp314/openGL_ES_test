//precision mediump float用来指定运算的精度以提高效率（因为运算量还是蛮大的），
//gl_FragColor 是一个内建的变量，表示颜色，以rgba的方式排布，范围是[0,1]的浮点数
precision mediump float;
void main(){
    gl_FragColor = vec4(0, 0.5, 0.5, 1);
}