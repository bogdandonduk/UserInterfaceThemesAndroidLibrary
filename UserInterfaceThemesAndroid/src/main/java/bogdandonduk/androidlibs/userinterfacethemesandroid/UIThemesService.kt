package bogdandonduk.androidlibs.userinterfacethemesandroid

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.AppBarLayout

object UIThemesService {
    private const val delimiter = "_"

    private const val LIBRARY_PREFIX = "prefs" + delimiter + "bogdandonduk.androidlibs.userinterfacethemesandroid"
    private const val IS_DARK_THEME_ENABLED = "isDarkThemeEnabled$delimiter$LIBRARY_PREFIX"

    private fun getPreferences(context: Context) : SharedPreferences =
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)

    private fun isDarkThemeEnabled(context: Context) : Boolean =
        getPreferences(context)
            .getBoolean(IS_DARK_THEME_ENABLED, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)


    fun setDarkTheme(context: Context, enabled: Boolean, host: UIThemesHost?) {
        getPreferences(context).edit().putBoolean(IS_DARK_THEME_ENABLED, enabled).apply()

        host?.initializeTheme()
    }

    private fun getConfiguredResources(context: Context, darkTheme: Boolean) : Resources =
        context.createConfigurationContext(Configuration(context.resources.configuration).apply { uiMode = if(darkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO }).resources

    fun initializeTextColor(context: Context, vararg texts: TextView?, @ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        texts.forEach {
            it?.setTextColor(ResourcesCompat.getColor(getConfiguredResources(context, isDarkThemeEnabled(context)), colorResId, theme))
        }
    }

    fun initializeTextColor(context: Context, vararg texts: TextView?, @ColorRes lightThemeColorResId: Int, @ColorRes darkThemeColorResId: Int, theme: Resources.Theme? = null) {
        texts.forEach {
            it?.setTextColor(ResourcesCompat.getColor(context.resources, if(isDarkThemeEnabled(context)) darkThemeColorResId else lightThemeColorResId, theme))
        }
    }

    fun initializeViewBackgroundColor(context: Context, vararg views: View?, @ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        views.forEach {
            it?.setBackgroundColor(ResourcesCompat.getColor(getConfiguredResources(context, isDarkThemeEnabled(context)), colorResId, theme))
        }
    }

    fun initializeViewBackgroundColor(context: Context, vararg views: View?, @ColorRes lightThemeColorResId: Int, @ColorRes darkThemeColorResId: Int) {
        views.forEach {
            it?.setBackgroundColor(ResourcesCompat.getColor(context.resources, if(isDarkThemeEnabled(context)) darkThemeColorResId else lightThemeColorResId, null))
        }
    }

    fun initializeDrawerToggleColor(context: Context, drawerToggle: ActionBarDrawerToggle?, @ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        drawerToggle?.drawerArrowDrawable?.color = ResourcesCompat.getColor(getConfiguredResources(context, isDarkThemeEnabled(context)), colorResId, theme)
    }

    fun initializeDrawerToggleColor(context: Context, drawerToggle: ActionBarDrawerToggle?, @ColorRes lightThemeColorResId: Int, @ColorRes darkThemeColorResId: Int, theme: Resources.Theme? = null) {
        drawerToggle?.drawerArrowDrawable?.color = ResourcesCompat.getColor(context.resources, if(isDarkThemeEnabled(context)) darkThemeColorResId else lightThemeColorResId, theme)
    }

    fun initializeHomeAsUpIndicator(context: Context, actionBar: ActionBar?, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) {
        actionBar?.setHomeAsUpIndicator(ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme))
    }

    fun initializeHomeAsUpIndicator(context: Context, actionBar: ActionBar?, @DrawableRes lightThemeDrawableResId: Int, @DrawableRes darkThemeDrawableResId: Int) {
        actionBar?.setHomeAsUpIndicator(if(isDarkThemeEnabled(context)) darkThemeDrawableResId else lightThemeDrawableResId)
    }

    fun initializeAppBarLayoutStyle(context: Context, appBarLayout: AppBarLayout?, @StyleRes lightThemeStyleId: Int = android.R.style.ThemeOverlay_Material_Dark, @StyleRes darkThemeStyleId: Int = android.R.style.ThemeOverlay_Material_Light) {
        appBarLayout?.context?.setTheme(if(isDarkThemeEnabled(context)) darkThemeStyleId else lightThemeStyleId)
        appBarLayout?.invalidate()
    }

    fun initializeImage(context: Context, image: ImageView?, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) {
        image?.setImageDrawable(ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme))
    }

    fun initializeImage(context: Context, image: ImageView?, @DrawableRes lightImageDrawableResId: Int, @DrawableRes darkImageDrawableResId: Int) {
        image?.setImageResource(if(isDarkThemeEnabled(context)) darkImageDrawableResId else lightImageDrawableResId)
    }

    fun initializeImage(context: Context, image: ImageView?, lightImageDrawable: Drawable, darkImageDrawable: Drawable) {
        image?.setImageDrawable(if(isDarkThemeEnabled(context)) darkImageDrawable else lightImageDrawable)
    }

    fun initializeMenuIcon(context: Context, menuItem: MenuItem?, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) {
        menuItem?.icon = ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme)
    }

    fun initializeMenuIcon(context: Context, menuItem: MenuItem?, @DrawableRes lightIconDrawableResId: Int, @DrawableRes darkIconDrawableResId: Int) {
        menuItem?.setIcon(if(isDarkThemeEnabled(context)) darkIconDrawableResId else lightIconDrawableResId)
    }

    fun initializeOverflowMenuIcon(context: Context, toolbar: Toolbar?, @DrawableRes drawableResId: Int, theme: Resources.Theme? = null) {
        toolbar?.overflowIcon = ResourcesCompat.getDrawable(getConfiguredResources(context, isDarkThemeEnabled(context)), drawableResId, theme)
    }

    fun initializeOverflowMenuIcon(context: Context, toolbar: Toolbar?, @DrawableRes lightIconDrawableResId: Int, @DrawableRes darkIconDrawableResId: Int, theme: Resources.Theme? = null) {
        toolbar?.overflowIcon = ResourcesCompat.getDrawable(context.resources, if(isDarkThemeEnabled(context)) darkIconDrawableResId else lightIconDrawableResId, theme)
    }

    fun initializeActionBarTitleColor(context: Context, toolbar: Toolbar?, @ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        toolbar?.setTitleTextColor(ResourcesCompat.getColor(getConfiguredResources(context, isDarkThemeEnabled(context)), colorResId, theme))
    }

    fun initializeActionBarTitleColor(context: Context, toolbar: Toolbar?, @ColorRes lightThemeColorResId: Int, darkThemeColorResId: Int, theme: Resources.Theme? = null) {
        toolbar?.setTitleTextColor(ResourcesCompat.getColor(context.resources, if(isDarkThemeEnabled(context)) darkThemeColorResId else lightThemeColorResId, theme))
    }
}