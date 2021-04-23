package bogdandonduk.androidlibs.userinterfacethemesandroid

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.AppBarLayout

class UIThemesService(context: Context) {
    companion object {
        private const val LIBRARY_PREFIX = "prefs_bogdandonduk.androidlibs.userinterfacethemesandroid_"
        private const val IS_DARK_THEME_ENABLED = "isDarkThemeEnabled"

        private var instance: UIThemesService? = null

        fun getInstance(context: Context) : UIThemesService {
            if(instance == null) instance = UIThemesService(context)

            return instance!!
        }
    }

    private var darkTheme: Boolean? = null

    init {
        {
            darkTheme = isDarkThemeEnabled(context)
        }.run {
            invoke()

            context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)
                    .registerOnSharedPreferenceChangeListener { _: SharedPreferences, key: String ->
                        if (key == IS_DARK_THEME_ENABLED) invoke()
                        Log.d("TAG", ": Preferences Changed")

                    }
        }

        Log.d("TAG", ": UIThemesService Instantiated")

    }

    fun isDarkThemeEnabled(context: Context) : Boolean =
            context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE)
                .getBoolean(IS_DARK_THEME_ENABLED, context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)


    fun setDarkTheme(context: Context, enabled: Boolean, host: UIThemesHost?) {
        context.getSharedPreferences(LIBRARY_PREFIX + context.packageName, Context.MODE_PRIVATE).edit().putBoolean(IS_DARK_THEME_ENABLED, enabled).apply()

        host?.initializeTheme()
    }

    private fun getConfiguredResources(context: Context, darkTheme: Boolean) : Resources =
        context.createConfigurationContext(Configuration(context.resources.configuration).apply { uiMode = if(darkTheme) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO }).resources

    fun initializeTextColor(context: Context, vararg texts: TextView?, colorResId: Int) {
        texts.forEach {
            it?.setTextColor(ResourcesCompat.getColor(getConfiguredResources(context, darkTheme!!), colorResId, null))
        }
    }

    fun initializeTextColor(context: Context, vararg texts: TextView?, lightThemeColorResId: Int, darkThemeColorResId: Int) {
        texts.forEach {
            it?.setTextColor(ResourcesCompat.getColor(context.resources, if(darkTheme!!) darkThemeColorResId else lightThemeColorResId, null))
        }
    }

    fun initializeViewBackgroundColor(context: Context, vararg views: View?, colorResId: Int) {
        views.forEach {
            it?.setBackgroundColor(ResourcesCompat.getColor(getConfiguredResources(context, darkTheme!!), colorResId, null))
        }
    }

    fun initializeViewBackgroundColor(context: Context, vararg views: View?, lightThemeColorResId: Int, darkThemeColorResId: Int) {
        views.forEach {
            it?.setBackgroundColor(ResourcesCompat.getColor(context.resources, if(darkTheme!!) darkThemeColorResId else lightThemeColorResId, null))
        }
    }

    fun initializeDrawerToggleColor(context: Context, drawerToggle: ActionBarDrawerToggle?, colorResId: Int) {
        drawerToggle?.drawerArrowDrawable.color = ResourcesCompat.getColor(getConfiguredResources(context, darkTheme!!), colorResId, null)
    }

    fun initializeDrawerToggleColor(context: Context, drawerToggle: ActionBarDrawerToggle?, lightThemeColorResId: Int, darkThemeColorResId: Int) {
        drawerToggle?.drawerArrowDrawable.color = ResourcesCompat.getColor(context.resources, if(darkTheme!!) darkThemeColorResId else lightThemeColorResId, null)
    }

    fun initializeHomeAsUpIndicator(actionBar: ActionBar?, lightThemeIndicatorDrawableResId: Int, darkThemeIndicatorDrawableResId: Int) {
         actionBar.setHomeAsUpIndicator(if(darkTheme!!) darkThemeIndicatorDrawableResId else lightThemeIndicatorDrawableResId)
    }

    fun initializeAppBarLayoutStyle(appBarLayout: AppBarLayout?, lightThemeStyleId: Int = android.R.style.ThemeOverlay_Material_Dark, darkThemeStyleId: Int = android.R.style.ThemeOverlay_Material_Light) {
        appBarLayout?.context.setTheme(if(darkTheme!!) darkThemeStyleId else lightThemeStyleId)
        appBarLayout?.invalidate()
    }

    fun initializeImage(image: ImageView?, lightImageDrawableResId: Int, darkImageDrawableResId: Int) {
        image?.setImageResource(if(darkTheme!!) darkImageDrawableResId else lightImageDrawableResId)
    }

    fun initializeImage(image: ImageView?, lightImageDrawable: Drawable, darkImageDrawable: Drawable) {
        image?.setImageDrawable(if(darkTheme!!) darkImageDrawable else lightImageDrawable)
    }

    fun initializeMenuIcon(menuItem: MenuItem?, lightIconDrawableResId: Int, darkIconDrawableResId: Int) {
        menuItem?.setIcon(if(darkTheme!!) darkIconDrawableResId else lightIconDrawableResId)
    }

    fun initializeOverflowMenuIcon(context: Context, toolbar: Toolbar?, lightIconDrawableResId: Int, darkIconDrawableResId: Int) {
        toolbar?.overflowIcon = ResourcesCompat.getDrawable(context.resources, if(darkTheme!!) darkIconDrawableResId else lightIconDrawableResId, null)
    }
}