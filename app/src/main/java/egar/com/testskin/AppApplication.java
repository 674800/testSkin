package egar.com.testskin;

import android.app.Application;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;

import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;

import theme.ExtraAttrRegister;
import theme.ThemeController;

import static android.content.ContentValues.TAG;

/**
 * Created by ybf on 2019/7/16.
 */
public class AppApplication extends Application implements ThemeController.ThemeChangeListener {
    private ThemeController mThemeController = null;
    @Override
    public void onCreate() {
        super.onCreate();
        /*SkinInflaterFactory.setFactory(LayoutInflater.from(this));  // for skin change
        SkinManager.get().init(this);*/
        initSkin();
    }


  public void initSkin() {
        Log.d(TAG, "initSkin()");
        ExtraAttrRegister.init();
        SkinInflaterFactory.setFactory(LayoutInflater.from(getApplicationContext()));  // for skin change
        SkinManager.get().init(getApplicationContext());
        mThemeController = ThemeController.getInstance(this, new Handler(), Settings.System.getUriFor("theme_setting"));
        mThemeController.registerThemeChangeListener(this);
        mThemeController.addSkinFileInfo(1+"", "ios", "skins/testskin2-debug.apk");
        mThemeController.checkAndUpdateTheme();
    }

    @Override
    public String getThemeValue() {
        return Settings.System.getInt(getApplicationContext().getContentResolver(), "theme_setting", 0)+"";
    }
}
