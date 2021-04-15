package bogdandonduk.androidlibs.userinterfacethemesandroid

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

object UIThemesContext {
    private const val LIBRARY_PREFIX = "prefs_bogdandonduk.androidlibs.userinterfacethemesandroid_"
    private const val IS_DARK_THEME_ENABLED = "isDarkThemeEnabled"

    fun isDarkThemeEnabled(context: Context) =
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)
            .getBoolean(IS_DARK_THEME_ENABLED, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)

    fun setDarkTheme(context: Context, enabled: Boolean) {
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE).edit().putBoolean(IS_DARK_THEME_ENABLED, enabled).apply()
    }

    fun initializeTextTheme(context: Context, vararg texts: TextView, colorResId: Int) {
        val darkTheme = isDarkThemeEnabled(context)

        texts.forEach {
            it.setTextColor(ResourcesCompat.getColor(getConfiguredResources(context, darkTheme), colorResId, null))
        }
    }

    fun initializeTextTheme(context: Context, vararg texts: TextView, lightThemeColorResId: Int, darkThemeColorResId: Int) {
        val darkTheme = isDarkThemeEnabled(context)

        texts.forEach {
            it.setTextColor(ResourcesCompat.getColor(context.resources, if(darkTheme) darkThemeColorResId else lightThemeColorResId, null))
        }
    }

    private fun getConfiguredResources(context: Context, darkTheme: Boolean) : Resources =
            context.createConfigurationContext(Configuration(context.resources.configuration).apply { uiMode = if(darkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO }).resources

}