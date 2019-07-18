package theme.deployer;

import android.view.View;

import com.wind.me.xskinloader.entity.SkinAttr;
import com.wind.me.xskinloader.skinInterface.ISkinResDeployer;
import com.wind.me.xskinloader.skinInterface.ISkinResourceManager;

/**
 * 新增progressDrawable属性也支持换肤

 */
public class ProgressDrawableResDeployer implements ISkinResDeployer {
    @Override
    public void deploy(View view, SkinAttr skinAttr, ISkinResourceManager iSkinResourceManager) {
//        if (!(view instanceof SeekBarImpl)) {
//            return;
//        }
//
////        LogUtils.v("ProgressDrawableResDeployer", "deploy() skinAttr="+skinAttr.toString());
//        SeekBarImpl seekBarImpl = (SeekBarImpl) view;
//        if (ExtraAttrRegister.PROGRESS_DRAWABLE.equals(skinAttr.attrName)) {
//            seekBarImpl.setProgressDrawableTiled(iSkinResourceManager.getDrawable(skinAttr.attrValueRefId));
//        }
    }
}
