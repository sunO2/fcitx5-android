package org.fcitx.fcitx5.android.ui.main

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.fcitx.fcitx5.android.data.clipboard.ClipboardManager
import org.fcitx.fcitx5.android.databinding.ActivityClipboardEditBinding
import org.fcitx.fcitx5.android.utils.str
import splitties.systemservices.clipboardManager
import splitties.systemservices.inputMethodManager

class ClipboardEditActivity : Activity() {

    private val scope: CoroutineScope = MainScope()

    private lateinit var editText: EditText

    private var entryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.gravity = Gravity.TOP
        val binding = ActivityClipboardEditBinding.inflate(layoutInflater).apply {
            editText = clipboardEditText
            clipboardEditCancel.setOnClickListener { finish() }
            clipboardEditOk.setOnClickListener {
                val str = editText.str
                updateClipEntry(entryId, str)
                if (entryId == ClipboardManager.lastEntry?.id) copyText(str)
                finish()
            }
            clipboardEditCopy.setOnClickListener {
                val str = editText.str
                updateClipEntry(entryId, str)
                copyText(str)
                finish()
            }
        }
        setContentView(binding.root)
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        processIntent(intent)
    }

    private fun copyText(text: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text))
    }

    private fun updateClipEntry(entryId: Int, text: String) {
        if (entryId > 0) scope.launch {
            ClipboardManager.updateText(entryId, text)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        intent.extras?.getInt(ENTRY_ID)?.let { id ->
            scope.launch {
                ClipboardManager.get(id)?.let { entry ->
                    entryId = entry.id
                    editText.setText(entry.text)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val ENTRY_ID = "id"
    }
}
