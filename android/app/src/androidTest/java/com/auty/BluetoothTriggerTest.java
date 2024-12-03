import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.auty.modules.applets.MusicApplet;
import com.auty.modules.responses.MusicResponse;
import com.auty.modules.workflows.MusicWorkflow;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.Intents.intended;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MusicWorkflowTest {

    @Rule
    public IntentsTestRule<TestActivity> intentsTestRule = new IntentsTestRule<>(TestActivity.class);

    private Context testContext;
    private MusicApplet testApplet;
    private MusicResponse testResponse;
    private MusicWorkflow musicWorkflow;

    @Before
    public void setUp() {
        Intents.init();
        testContext = intentsTestRule.getActivity();
        testApplet = new MusicApplet();
        testResponse = new MusicResponse(testContext);
        musicWorkflow = new MusicWorkflow(testContext, testApplet, testResponse);
    }

    @Test
    public void testBluetoothConnectionIntent_TriggersMusicAppRedirect() {

        Intent bluetoothIntent = new Intent(BluetoothDevice.ACTION_ACL_CONNECTED);


        musicWorkflow.handle(bluetoothIntent);


        intended(hasAction(BluetoothDevice.ACTION_ACL_CONNECTED));


        assertTrue(testResponse.wasResponseSent()); 
    }
}
