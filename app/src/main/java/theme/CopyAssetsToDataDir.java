package theme;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 拷贝assets目录下的文件到/data/data/包名/目录下。
 *
 */
public class CopyAssetsToDataDir {

    private static final String TAG = "CopyAssetsToDataDir";

    /**
     * 获取单个文件的MD5值
     *
     * @param path the path
     * @return file md 5
     */
    public static String getFileMD5(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获得assets目录下assetsFileName文件的MD5值
     *
     * @param context        the context
     * @param assetsFileName the assets file mName
     * @return the asset file md 5
     */
    public static String getAssetFileMD5(Context context, String assetsFileName) {
        MessageDigest digest = null;
        InputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = context.getAssets().open(assetsFileName);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 判断assetsFileName文件和targetPath文件的MD5值是否相同
     *
     * @param context        the context
     * @param assetsFilePath the assets file path
     * @param targetPath     the target path
     * @return true MD5值相同
     */
    public static boolean isFileSame(Context context, String assetsFilePath, String targetPath) {
        boolean flag = false;

        String assetFileMD5 = "";
        String targetFileMD5 = "";
        do {
            assetFileMD5 = getAssetFileMD5(context, assetsFilePath);
            targetFileMD5 = getFileMD5(targetPath);

            if (!TextUtils.isEmpty(assetFileMD5) && !TextUtils.isEmpty(targetFileMD5) && assetFileMD5.equals(targetFileMD5)) {
                flag = true;
                break;
            }
        } while (false);

        Log.d(TAG, "isFileSame() assetsFileName="+assetsFilePath+", targetPath="+targetPath+", assetFileMD5="+assetFileMD5+", targetFileMD5="+targetFileMD5+", flag="+flag);

        return flag;
    }

    /**
     * 拷贝assets目录下的assetsFileName文件到targetPath目录下
     *
     * @param context        the context
     * @param assetsFilePath the assets file path
     * @param targetPath     the target path
     * @return the boolean
     */
    public static boolean copyAssetData(Context context, String assetsFilePath, String targetPath) {
        try {
            if (isFileSame(context, assetsFilePath, targetPath)) {
                return true;
            }
            InputStream inputStream = context.getAssets().open(assetsFilePath);
            FileOutputStream output = new FileOutputStream(targetPath);
            byte[] buf = new byte[10240];
            int count = 0;
            while ((count = inputStream.read(buf)) > 0) {
                output.write(buf, 0, count);
            }
            output.close();
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
