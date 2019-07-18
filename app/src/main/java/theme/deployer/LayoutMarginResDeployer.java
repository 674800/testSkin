package theme.deployer;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.wind.me.xskinloader.entity.SkinAttr;
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer;
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager;

import theme.ExtraAttrRegister;

/**
 * 新增marginLeft属性也支持换肤

 */
public class LayoutMarginResDeployer implements ISkinResDeployer {

    private static final String TAG = "LayoutMarginResDeployer";

    private Resources mDefaultResources = null;

    public LayoutMarginResDeployer(Context context) {
        mDefaultResources = context.getResources();
    }

    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager iSkinResourceManager) {
        Log.v(TAG, "deploy() skinAttr="+skinAttr.toString());
        if (ExtraAttrRegister.LAYOUT_MARGIN_LEFT.equals(skinAttr.attrName)) {
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.leftMargin = 0;
            try {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = (int) getDimen(skinAttr, iSkinResourceManager);
                view.setLayoutParams(layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ExtraAttrRegister.LAYOUT_MARGIN_RIGHT.equals(skinAttr.attrName)) {
            try {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.rightMargin = (int) getDimen(skinAttr, iSkinResourceManager);
                view.setLayoutParams(layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
