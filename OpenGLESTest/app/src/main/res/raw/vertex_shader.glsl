//vec4是一个包含4个浮点数（float，我们约定，在OpenGL中提到的浮点数都是指float类型）的向量，
//attribute表示变元,用来在Java程序和OpenGL间传递经常变化的数据，
//gl_Position 是OpenGL ES的内建变量，
//表示顶点坐标（xyzw，w是用来进行投影变换的归一化变量），
//我们会通过aPosition把要绘制的顶点坐标传递给gl_Position
attribute vec4 aposition;
void main(){
    gl_position = aposition;
}