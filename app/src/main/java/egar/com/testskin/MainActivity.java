package egar.com.testskin;

import android.app.Activity;
import android.os.Bundle;

import com.wind.me.xskinloader.SkinInflaterFactory;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinInflaterFactory.setFactory(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
