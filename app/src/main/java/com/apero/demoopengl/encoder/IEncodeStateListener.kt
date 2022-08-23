package com.cxp.learningvideo.media.encoder

import com.apero.demoopengl.encoder.BaseEncoder


/**
 * 编码状态回调接口
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 *
 */
interface IEncodeStateListener {
    fun encodeStart(encoder: BaseEncoder)
    fun encodeProgress(encoder: BaseEncoder)
    fun encoderFinish(encoder: BaseEncoder)
}