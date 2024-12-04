import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.auty.modules.applets.MusicApplet;
import com.auty.modules.responses.MusicResponse;
import com.auty.modules.workflows.MusicWorkflow;
import com.auty.modules.models.NotificationModel;
import com.auty.modules.models.UserModel;
import com.auty.modules.models.User;
import com.auty.modules.models.DatabaseInit;

import com.auty.MainActivity;

import org.junit.Before;
import org.junit.After;
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
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);
    
    // public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestsRule<>(MainActivity.class);
    
    private Context testContext;
    private MusicApplet testApplet;
    private MusicResponse testResponse;
    private MusicWorkflow musicWorkflow;
    private DatabaseInit dbInit;
    private Context context;
    private int user_id;
    private NotificationModel notificationModel;

    private int registerUser(UserModel userModel) {
        String username = "username";
        String password = "password";
        String repeatedPassword = "password";

        try {
            User registering_user = new User(username, password, repeatedPassword);
            userModel.register(registering_user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userModel.getUserID(username);
    }

   

    @Before
    public void setUp() {
        Intents.init();

        testContext = intentsTestRule.getActivity();

        dbInit = new DatabaseInit(context, ":memory:");
        notificationModel = new NotificationModel(dbInit);
        UserModel userModel = new UserModel(dbInit);
        user_id = registerUser(userModel);

        testApplet = new MusicApplet(testContext);
        testResponse = new MusicResponse(testContext, "musicResponse", user_id, notificationModel);
        musicWorkflow = new MusicWorkflow(testContext, testApplet, testResponse);
    }
    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void testBluetoothConnectionIntent_TriggersMusicAppRedirect() {

        Intent bluetoothIntent = new Intent(BluetoothDevice.ACTION_ACL_CONNECTED);


        musicWorkflow.handle(bluetoothIntent);


        intended(hasAction(BluetoothDevice.ACTION_ACL_CONNECTED));


        assertTrue(testResponse.responded); 
    }
}
