package bogdandonduk.androidlibs.userinterfacethemesandroid

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ColorScheme(
        val viewBackgroundScheme: Pair<Int, MutableList<View>>,
        val textScheme: Pair<Int, MutableList<TextView>>,
        val additionalAction: ((viewBackgroundColorResId: Int, textColorResId: Int) -> Unit)? = null
) {
    fun apply() {
        CoroutineScope(Main.immediate).launch {
            val colorResId = viewBackgroundScheme.first

            viewBackgroundScheme.second.forEach {
                it.setBackgroundColor(colorResId)
            }
        }

        CoroutineScope(Main.immediate).launch {
            val colorResId = textScheme.first

            textScheme.second.forEach {
                it.setTextColor(colorResId)
            }
        }

        additionalAction?.invoke(viewBackgroundScheme.first, textScheme.first)
    }
}