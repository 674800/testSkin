package theme;

/**
 * Skin file information.
 *

 */
public class SkinFileInfo extends Object {
    private String mThemeValue;     // 本主题在系统主题数据库对应的值。例如：1
    private String mAssetPath;       // 主题包源文件assert目录下的相对路径。例如："skins/ios_skin.apk"
    private String mSaveDir;        // 主题包拷贝到“/data/data/包名”目录下的相对路径（目录）。例如：mContext.getCacheDir().getAbsolutePath() + "/skins"
    private String mSaveFileName;   // 主题包拷贝后的文件名称。例如："1_ios.skin"

    public SkinFileInfo() {
    }

    public String getThemeValue() {
        return mThemeValue;
    }
    public void setThemeValue(String themeFlag) {
        this.mThemeValue = themeFlag;
    }

    public String getAssetPath() {
        return mAssetPath;
    }
    public void setAssetPath(String assetPath) {
        this.mAssetPath = assetPath;
    }

    public String getSaveDir() {
        return mSaveDir;
    }
    public void setSaveDir(String saveDir) {
        this.mSaveDir = saveDir;
    }

    public String getSaveFileName() {
        return mSaveFileName;
    }
    public void setSaveFileName(String saveFileName) {
        this.mSaveFileName = saveFileName;
    }
}
