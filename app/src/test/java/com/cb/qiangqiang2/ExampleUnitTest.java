package com.cb.qiangqiang2;

import android.widget.EditText;

import com.cb.qiangqiang2.test.activity.MainTestActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        MainTestActivity mainTestActivity = Robolectric.setupActivity(MainTestActivity.class);
        EditText editText = new EditText(RuntimeEnvironment.application);
        editText.setText("haha");
        Assert.assertEquals("haha", editText.getText().toString());
    }
}