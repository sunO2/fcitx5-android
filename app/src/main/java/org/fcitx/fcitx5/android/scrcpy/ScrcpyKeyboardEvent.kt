package org.fcitx.fcitx5.android.scrcpy

import android.view.inputmethod.EditorInfo
import org.fcitx.fcitx5.android.core.FcitxEvent
import org.fcitx.fcitx5.android.input.broadcast.InputBroadcastReceiver
import org.mechdancer.dependency.UniqueComponent
import timber.log.Timber

class ScrcpyKeyboardEvent : UniqueComponent<ScrcpyKeyboardEvent>(), InputBroadcastReceiver {
    /**
     * 更新输入
     */
    override fun onEditorInfoUpdate(info: EditorInfo?) {
        Timber.d("onFinishInputView: showInput=$info")
    }

    override fun onPreeditUpdate(data: FcitxEvent.PreeditEvent.Data) {
        super.onPreeditUpdate(data)
        Timber.d("onFinishInputView: finishingInput")
    }
}