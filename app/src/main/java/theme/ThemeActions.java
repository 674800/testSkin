package theme;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ThemeActions {
    /**
     * Add skin file information
     * addSkinFileInfo(1, "ios", "skins/ios_skin.apk");
     *
     * @param themeValue The theme value you set. The value must above 0.
     * @param themeDesc Theme description string ,such as "ios"
     * @param assetDir  Theme package path in assets.
     *                  Such as you put file to "/src/main/assets/skins/ios_skin.apk", parameter must be "skins/ios_skin.apk"
     */
    void addSkinFileInfo(String themeValue, @Nullable String themeDesc, @NonNull String assetDir);

    /**
     * get skin file path by themeFlag.
     *
     * @param themeValue Current theme flag value.
     * @return The skin file path.
     */
    String getSkinFilePath(String themeValue);
}
