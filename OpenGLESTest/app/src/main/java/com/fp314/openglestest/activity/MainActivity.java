package com.fp314.openglestest.activity;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fp314.openglestest.R;
import com.fp314.openglestest.render.DrawTriangleRender;

public class MainActivity extends AppCompatActivity {

    //OpenGL ES的使用，一般包括如下几个步骤：
    //1. EGL Context初始化
    //2. OpenGL ES初始化
    //3. OpenGL ES设置选项与绘制
    //4. OpenGL ES资源释放（可选）
    //5. EGL资源释放
    //Android平台提供了一个GLSurfaceView，来帮助使用者完成第一步和第五步
    // 由于释放EGL资源时会自动释放之前申请的OpenGL ES资源，
    // 所以需要我们自己做的就只有2和3。
    private GLSurfaceView mglSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mglSurfaceView = (GLSurfaceView)findViewById(R.id.surface_view);

        //设置EGL上下文的客户端版本，因为我们使用的是OpenGL ES 2.0,所以设置为2
        mglSurfaceView.setEGLContextClientVersion(2);

        //一个是需要渲染（触控事件，渲染请求）才渲染，RENDERMODE_WHEN_DIRTY
        // 一个是不断渲染,RENDERMODE_CONTINUOUSLY
        mglSurfaceView.setRenderMode(mglSurfaceView.RENDERMODE_CONTINUOUSLY);
        mglSurfaceView.setRenderer(new DrawTriangleRender(this));
    }
}
