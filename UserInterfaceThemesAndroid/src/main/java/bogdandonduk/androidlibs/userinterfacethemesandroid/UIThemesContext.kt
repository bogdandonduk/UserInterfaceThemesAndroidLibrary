package bogdandonduk.androidlibs.userinterfacethemesandroid

import android.content.Context
import android.content.res.Configuration

object UIThemesContext {
    private const val LIBRARY_PREFIX = "prefs_bogdandonduk.androidlibs.userinterfacethemesandroid_"
    private const val IS_DARK_THEME_ENABLED = "isDarkThemeEnabled"

    fun isDarkThemeEnabled(context: Context) =
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)
            .getBoolean(IS_DARK_THEME_ENABLED, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
}