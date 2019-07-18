package theme;


import com.wind.me.xskinloader.SkinResDeployerFactory;

import theme.deployer.ProgressDrawableResDeployer;

/**
 * 扩展换肤属性和style中的换肤属性
 */
public class ExtraAttrRegister {

    public static final String PROGRESS_DRAWABLE = "progressDrawable";
    public static final String LAYOUT_MARGIN_LEFT = "layout_marginLeft";
    public static final String LAYOUT_MARGIN_RIGHT = "layout_marginRight";
    public static final String TEXT_SIZE = "textSize";

    public static final String RES_TYPE_NAME_DIMEN = "dimen";

    static {
        //增加自定义控件的自定义属性的换肤支持
        SkinResDeployerFactory.registerDeployer(PROGRESS_DRAWABLE, new ProgressDrawableResDeployer());
//        SkinResDeployerFactory.registerDeployer(LAYOUT_MARGIN_LEFT, new LayoutMarginResDeployer(SystemUIApplication.getContext()));
//        SkinResDeployerFactory.registerDeployer(LAYOUT_MARGIN_RIGHT, new LayoutMarginResDeployer(SystemUIApplication.getContext()));
//        SkinResDeployerFactory.registerDeployer(TEXT_SIZE, new TextSizeResDeployer(SystemUIApplication.getContext()));

        //增加xml里的style中指定的View background属性换肤
//        StyleParserFactory.addStyleParser(new ViewBackgroundStyleParser());
    }

    //仅仅为了使类的静态方法被加载而已
    public static void init() {}

}
