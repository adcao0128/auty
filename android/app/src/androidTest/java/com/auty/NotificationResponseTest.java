// package com.example.myapplication;

// import android.app.NotificationManager;
// import android.content.Context;
// import android.service.notification.StatusBarNotification;
// import androidx.test.core.app.ActivityScenario;
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import com.example.myapplication.responses.NotificationResponse;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;

// import static org.junit.Assert.assertTrue;

// @RunWith(AndroidJUnit4.class)
// public class NotificationResponseTest {

//     private NotificationManager notificationManager;
//     private Context context;
//     private NotificationResponse notificationResponse;
//     private final int notificationId = 1001;

//     @Before
//     public void setUp() {

//         ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
//         scenario.onActivity(activity -> {
//             context = activity;
//             notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


//             notificationResponse = new NotificationResponse(context, "TestResponse");
//         });
//     }

//     @Test
//     public void testRespondTriggersNotification() {

//         notificationResponse.respond("This is a test notification");


//         boolean notificationFound = false;
//         for (StatusBarNotification sbn : notificationManager.getActiveNotifications()) {
//             if (sbn.getId() == notificationId) {
//                 notificationFound = true;
//                 break;
//             }
//         }


//         assertTrue("Notification was not triggered by respond()", notificationFound);
//     }
// }
