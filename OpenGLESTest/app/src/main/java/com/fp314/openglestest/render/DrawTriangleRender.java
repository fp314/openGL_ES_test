package com.fp314.openglestest.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.fp314.openglestest.R;
import com.fp314.openglestest.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 314-FP on 2017/4/7.
 */

public class DrawTriangleRender implements GLSurfaceView.Renderer{

    private Context mcontext;
    private int programId;
    private int aPositionHandle;

    public DrawTriangleRender(Context mcontext){
        this.mcontext = mcontext;
    }
    //数据，定点片元
    private final float[] vertexData = {
            0f,0f,0f,
            1f,-1f,0f,
            1f,1f,0f
    };
    //主要数据
    private FloatBuffer vertexBuffer;
    /**
     * 这个函数在Surface被创建的时候调用，
     * 每次我们将应用切换到其他地方，
     * 再切换回来的时候都有可能被调用，在这个函数中，
     * 我们需要完成一些OpenGL ES相关变量的初始化
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        String vertexShader = ShaderUtil.readRawTextFile(mcontext, R.raw.vertex_shader);
        String fragmentShader = ShaderUtil.readRawTextFile(mcontext, R.raw.fragment_shader);
        programId = ShaderUtil.createProgram(vertexShader, fragmentShader);
        aPositionHandle = GLES20.glGetAttribLocation(programId, "aposition");

        //一个float是4个字节，ByteBuffer用来在本地内存分配足够的大小，
        // 并设置存储顺序为nativeOrder，最后把vertexData放进去，
        // 当然，不要忘了设定索引位置vertexBuffer.position(0);
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length*4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);
    }

    /**
     * 每当屏幕尺寸发生变化时，
     * 这个函数会被调用（包括刚打开时以及横屏、竖屏切换），
     * width和height就是绘制区域的宽和高（上图黑色区域）
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    /**
     * 这个是主要的函数，我们的绘制部分就在这里，
     * 每一次绘制时这个函数都会被调用，
     * 之前设置了GLSurfaceView.RENDERMODE_CONTINUOUSLY，
     * 也就是说按照正常的速度，每秒这个函数会被调用60次，
     * 虽然我们还什么都没做
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {

        //清空颜色缓冲区和深度缓冲区
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(programId);
        //启用顶点数组，aPositionHandle就是我们传送数据的目标位置
        GLES20.glEnableVertexAttribArray(aPositionHandle);
        //stride表示步长，因为一个顶点三个坐标，一个坐标是float（4字节），
        // 所以步长是12字节(当然，这个只在一个数组中同时包含多个属性时才有作用，例如同时包含纹理坐标和顶点坐标，在只有一种属性时（例如现在），和传递0是相同效果)
        //glVertexAttribPointer(
        //int indx,
        //int size,  数据个数
        //int type,  数据类型
        //boolean normalized,
        //int stride,  步长，一个数据长度
        //java.nio.Buffer ptr  数据
        GLES20.glVertexAttribPointer(aPositionHandle,
                3, GLES20.GL_FLOAT, false,
                12, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
