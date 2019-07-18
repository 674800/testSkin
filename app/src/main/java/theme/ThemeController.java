package theme;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.wind.me.xskinloader.SkinManager;

import java.io.File;
import java.util.HashMap;

/**
 * Theme controller
 * <p>Used to listener </p>
 *
 */
public class ThemeController implements ThemeActions {
    //TAG
    private static final String TAG = "ThemeController";

    private static ThemeController mThemeController = null;

    /**
     * {@link Context}
     */
    private Application mContext = null;

    /**
     * {@link SkinFileInfo} set, used to save skin package information.
     */
    private HashMap<String, SkinFileInfo> mSkinFileInfoMap = new HashMap<>();   // <themeValue, SkinFileInfo>

    /**
     * Theme Uri
     */
    private Uri mThemeUri = null;
    // Default theme Uri path
    public static final String DEFAULT_THEME_URI = "content://carsettings/carinfo/theme_setting";
    /**
     * Inner class {@link ThemeChangeContentObserver} object.
     */
    private ContentObserver mThemeObserver = null;

    /**
     * 存储当前系统设置的主题数据库值
     */
    private String mCurrentThemeValue = "";

    private ThemeController(@NonNull Application context, @NonNull Handler handler, @Nullable Uri uri) {
        mContext = context;
        if (uri == null) {
            mThemeUri = Uri.parse(DEFAULT_THEME_URI);
        } else {
            mThemeUri = uri;
        }
        Log.d(TAG, "mThemeUri: [" + mThemeUri.getPath() + "]");

        registerThemeObserver(handler);
    }

    /**
     * Constructor
     *
     * @param context Must be Application
     * @param handler Must be handler from Application
     * @param uri     Theme flag is saved in ContentProvider, So uri is used to listener flag change state.
     *                This value could be null, default value is {@link #DEFAULT_THEME_URI}
     */
    public static ThemeController getInstance(@NonNull Application context, @NonNull Handler handler, @Nullable Uri uri) {
        if (null == mThemeController) {
            synchronized (ThemeController.class) {
                if (null == mThemeController) {
                    mThemeController = new ThemeController(context, handler, uri);
                }
            }
        }

        return mThemeController;
    }

    /**
     * Register {@link ContentObserver} to listener theme flag.
     */
    private void registerThemeObserver(Handler handler) {
        try {
            mThemeObserver = new ThemeChangeContentObserver(handler);
            mContext.getContentResolver().registerContentObserver(mThemeUri, true, mThemeObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * INNER class - Theme observer class.
     */
    private class ThemeChangeContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        ThemeChangeContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "onChange(" + selfChange + ")");
            checkAndUpdateTheme();
        }
    }
    public void checkAndUpdateTheme() {
        if (null != mThemeChangeListener) {
            String currentThemeValue = mThemeChangeListener.getThemeValue();
            Log.d(TAG, "checkAndUpdateTheme() >> {mCurrentThemeValue:" + mCurrentThemeValue + " , currentThemeValue:" + currentThemeValue + "}");
            String skinFilePath = getSkinFilePath(currentThemeValue);
            if (TextUtils.isEmpty(skinFilePath)) {
                SkinManager.get().restoreToDefaultSkin();
            } else {
                SkinManager.get().loadNewSkin(skinFilePath);
            }
        } else {
            Log.w(TAG, "checkAndUpdateTheme() mThemeChangeListener==null, so return!!!");
        }
    }

    /**
     * Inner interface {@link ThemeChangeListener} object
     */
    private ThemeChangeListener mThemeChangeListener = null;
    /**
     * Used to listener theme change state.
     */
    public interface ThemeChangeListener {
        /**
         * Get current theme value.
         * @return the value of theme flag.
         */
        String getThemeValue();
    }
    /**
     * Add callback listener
     *
     * @param l {@link ThemeChangeListener}
     */
    public void registerThemeChangeListener(ThemeChangeListener l) {
        mThemeChangeListener = l;
    }
    public void unregisterThemeChangeListener() {
        mThemeChangeListener = null;
    }

    /**
     * {@link Application#onTerminate()} action.
     */
    public void onTerminate() {
        Log.d(TAG, "onTerminate()");
        destroy();
    }
    private void destroy() {
        try {
            if (null != mThemeChangeListener) {
                unregisterThemeChangeListener();
                mThemeChangeListener = null;
            }
            mContext.getContentResolver().unregisterContentObserver(mThemeObserver);
            mThemeObserver = null;
            mContext = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSkinFileInfo(String themeValue, @Nullable String themeDesc, @NonNull String assetPath) {

        Log.i(TAG, "addSkinFileInfo("+themeValue+", "+themeDesc+", "+assetPath+")");
        if (TextUtils.isEmpty(themeValue) || TextUtils.isEmpty(assetPath) || TextUtils.isEmpty(assetPath.trim())) {
            return;
        }

        try {
            SkinFileInfo sfi = new SkinFileInfo();
            sfi.setThemeValue(themeValue);
            sfi.setSaveDir(mContext.getCacheDir().getAbsolutePath() + "/skins");
            sfi.setAssetPath(assetPath);
            sfi.setSaveFileName(themeValue + "_" + themeDesc + ".skin");
            mSkinFileInfoMap.put(themeValue, sfi);
            Log.e(TAG,"assetsFilePath="+sfi.getAssetPath() );
            Log.e(TAG,"targetPath="+sfi.getSaveDir()+File.separator+sfi.getSaveFileName() );
            CopyAssetsToDataDir.copyAssetData(mContext, sfi.getAssetPath(), sfi.getSaveDir()+File.separator+sfi.getSaveFileName());
        } catch (Exception e) {
            Log.e(TAG, "addSkinFileInfo() >> e: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getSkinFilePath(String themeFlag) {
        Log.d(TAG, "getSkinFilePath(" + themeFlag + ")");
        SkinFileInfo sfi = mSkinFileInfoMap.get(themeFlag);
        if (sfi != null) {
            File file = new File(sfi.getSaveDir() + File.separator + sfi.getSaveFileName());
            if (file.exists()) {
                return file.getPath();
            }
        }
        return null;
    }
}
