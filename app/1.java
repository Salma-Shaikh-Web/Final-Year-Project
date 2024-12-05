import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.hajricard.R;

public class EmergencyMonitoringService extends Service {

    private static final String TAG = "EmergencyService";
    public static final String EMERGENCY_CHANNEL_ID = "emergency_channel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); // Check every 5 seconds (adjust as needed)
                    if (isEmergencyDetected()) { // Replace with your emergency detection logic
                        sendEmergencyNotification();
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "Service interrupted", e);
                }
            }
        }).start();

        return START_STICKY; // Restart service if killed unexpectedly
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    EMERGENCY_CHANNEL_ID,
                    "Emergency Monitoring",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifications for emergencies detected by the app.");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private boolean isEmergencyDetected() {
        // Replace this with your actual logic to detect emergencies
        // This is just a simulation for now
        return Math.random() < 0.1; // Simulate a 10% chance of emergency every check
    }

    private void sendEmergencyNotification() {
        Intent intent = new Intent(this, EmergencyMonitoringService.class); // Replace with your emergency activity if needed
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, EMERGENCY_CHANNEL_ID)
                .setContentTitle("Emergency Detected!")
                .setContentText("Take action to respond to the emergency.")
                .setSmallIcon(R.drawable.ic_emergency) // Replace with your icon resource
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);}
}