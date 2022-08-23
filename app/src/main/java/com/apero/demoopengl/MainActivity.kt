package com.apero.demoopengl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.Surface
import com.apero.demoopengl.decoder.AudioDecoder
import com.apero.demoopengl.decoder.VideoDecoder
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val path = Environment.getExternalStorageDirectory().absolutePath + "/mvtest.mp4"
    private val path2 = Environment.getExternalStorageDirectory().absolutePath + "/mvtest_2.mp4"
    private val render = SimpleRender()
    private val threadPool = Executors.newFixedThreadPool(10)

    lateinit var gl_surface: DefGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gl_surface = findViewById(R.id.gl_surface)
        initFirstVideo()
        initSecondVideo()
        initRender()
    }

    private fun initFirstVideo() {
        val drawer = VideoDrawer()
        drawer.setVideoSize(1920, 1080)
        drawer.getSurfaceTexture {
            initPlayer(path, Surface(it), true)
        }
        render.addDrawer(drawer)
    }

    private fun initSecondVideo() {
        val drawer = VideoDrawer()
        drawer.setVideoSize(1920, 1080)
        drawer.getSurfaceTexture {
            initPlayer(path2, Surface(it), false)
        }
        render.addDrawer(drawer)
        gl_surface.addDrawer(drawer)

//        Handler().postDelayed({
//            drawer.scale(0.5f, 0.5f)
//        }, 100)
    }

    private fun initPlayer(path: String, sf: Surface, withSound: Boolean) {
        val videoDecoder = VideoDecoder(path, null, sf)
        threadPool.execute(videoDecoder)
        videoDecoder.goOn()

        if (withSound) {
            val audioDecoder = AudioDecoder(path)
            threadPool.execute(audioDecoder)
            audioDecoder.goOn()
        }
    }

    private fun initRender() {
        gl_surface.setEGLContextClientVersion(2)
        gl_surface.setRenderer(render)
    }
}