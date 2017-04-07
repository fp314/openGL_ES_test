package com.fp314.openglestest.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * Created by 314-FP on 2017/4/7.
 */

public class ShaderUtil {

    /**
     * 从raw读取Shader文件，GLSL脚本（GLSL，OpenGL Shader Language，即着色器语言）
     * Vertex shader（定点着色器，负责定点位置与坐标变换，即决定显示哪个部分，以何种位置/姿态显示），
     * Fragment shader（片元着色器，负责纹理的填充与转换，即决定显示成什么样子）
     * @param context
     * @param resId
     * @return
     */
    private static final String TAG = "ShaderUtil";
    public static String readRawTextFile(Context context, int resId){

        InputStream inputStream = context.getResources().openRawResource(resId);
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 加载编译生成响应的shader
     * @param shaderType
     * @param source
     * @return
     */
    public static int loadShader(int shaderType, String source){
        //创建shader
        int shader = GLES20.glCreateShader(shaderType);
        if(shader != 0){
            //加载shader脚本
            GLES20.glShaderSource(shader, source);
            //编译shader脚本
            GLES20.glCompileShader(shader);

            //获取编译状态，如果出错，删除该shader,返回0
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if(compiled[0] == 0){
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public static int createProgram(String vertexSource,String fragmentSource){

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
        if(vertexShader == 0) return 0;
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        if(fragmentShader == 0) return 0;

        int program = GLES20.glCreateProgram();
        if(program != 0 ){
            //向工程增加shader
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, fragmentShader);
            checkGlError("glAttachShader");
            //连接编译工程
            GLES20.glLinkProgram(program);
            //检查工程的编译状态
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_COMPILE_STATUS,linkStatus,0);
            if(linkStatus[0] != GLES20.GL_TRUE){
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    public static void checkGlError(String label){
        int error;
        while((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR){
            Log.e(TAG, label + ": glError " + error);
            throw new RuntimeException(label + ": glError " + error);
        }
    }
}
