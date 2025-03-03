package org.fcitx.fcitx5.android.ui.main.settings

import androidx.preference.PreferenceDataStore
import org.fcitx.fcitx5.android.core.RawConfig

class FcitxRawConfigStore(private var cfg: RawConfig, private val onStoreChange: () -> Unit) :
    PreferenceDataStore() {
    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        if (key == null) return defValue
        return cfg[key].value == "True"
    }

    override fun putBoolean(key: String?, value: Boolean) {
        if (key == null) return
        cfg[key].value = if (value) "True" else "False"
        onStoreChange()
    }

    override fun getInt(key: String?, defValue: Int): Int {
        if (key == null) return defValue
        return cfg[key].value.toInt()
    }

    override fun putInt(key: String?, value: Int) {
        if (key == null) return
        cfg[key].value = value.toString()
        onStoreChange()
    }

    override fun getString(key: String?, defValue: String?): String? {
        if (key == null) return defValue
        return cfg[key].value
    }

    override fun putString(key: String?, value: String?) {
        if (key == null) return
        cfg[key].value = value ?: ""
        onStoreChange()
    }

}
