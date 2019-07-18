package theme.deployer;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wind.me.xskinloader.entity.SkinAttr;
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer;
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager;

import theme.ExtraAttrRegister;

/**
 * 新增TextSize属性也支持换肤
 *
 */
public class TextSizeResDeployer implements ISkinResDeployer {

    private static final String TAG = "TextSizeResDeployer";

    private Resources mDefaultResources = null;

    public TextSizeResDeployer(Context context) {
        mDefaultResources = context.getResources();
    }

    /**
     * 默认主题
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: deploy() skinAttr=SkinAttr
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: [
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: attrName=textSize,
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: attrValueRefId=2131100007,
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: attrValueRefName=typec_dialog_charge_text_size,
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: attrValueTypeName=dimen
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: ]
     * 01-02 03:33:37.931 11531 11531 V SystemUI-TextSizeResDeployer: getDimen() pkgName=null, skinPluginResources=null, originDimen=35.0
     *
     * IOS主题
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: deploy() skinAttr=SkinAttr
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: [
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: attrName=textSize,
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: attrValueRefId=2131100007,
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: attrValueRefName=typec_dialog_charge_text_size,
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: attrValueTypeName=dimen
     * 01-02 03:32:35.318 11531 11531 V SystemUI-TextSizeResDeployer: ]
     * 01-02 03:32:35.319 11531 11531 V SystemUI-TextSizeResDeployer: getDimen() pkgName=com.android.systemui.skin_ios, skinPluginResources=android.content.res.Resources@46fbc5d, originDimen=35.0, trueDimen=40.0, trueResId=2130837504
     *
     */
    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager iSkinResourceManager) {
        if (!(view instanceof Button)) {    // 当前项目，只有Buttun的字体大小支持主题切换
            return;
        }

        Log.v(TAG, "deploy() skinAttr="+skinAttr.toString());
        Button button = (Button) view;
        if (ExtraAttrRegister.TEXT_SIZE.equals(skinAttr.attrName)) {
            button.setTextSize(getDimen(skinAttr, iSkinResourceManager));
        }
    }

    public float getDimen(SkinAttr skinAttr, ISkinResourceManager iSkinResourceManager) throws Resources.NotFoundException {
        int resId = skinAttr.attrValueRefId;
        float originDimen  = mDefaultResources.getDimension(resId);
        String pkgName = iSkinResourceManager.getPkgName();
        Resources skinPluginResources = iSkinResourceManager.getPluginResource();

        if (TextUtils.isEmpty(pkgName) || null==skinPluginResources) {
            Log.v(TAG, "getDimen() pkgName="+pkgName+", skinPluginResources="+skinPluginResources+", originDimen="+originDimen);
            return originDimen;
        }

        int trueResId = skinPluginResources.getIdentifier(skinAttr.attrValueRefName, skinAttr.attrValueTypeName, pkgName);

        float trueDimen;
        try {
            trueDimen  = skinPluginResources.getDimension(trueResId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            trueDimen = originDimen;
        }

        Log.v(TAG, "getDimen() pkgName="+pkgName+", skinPluginResources="+skinPluginResources+", originDimen="+originDimen+", trueDimen="+trueDimen+", trueResId="+trueResId);
        return trueDimen;
    }
}
