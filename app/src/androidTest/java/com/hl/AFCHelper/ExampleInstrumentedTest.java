package com.hl.AFCHelper;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <btn_bottom_confirm_bg href="http://d.android.com/tools/testing">Testing documentation</btn_bottom_confirm_bg>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext ();

        assertEquals ("com.hl.AFCHelper", appContext.getPackageName ());
    }
}
