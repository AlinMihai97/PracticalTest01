package ro.pub.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class PracticalTest01Service extends Service {

    ProcessingThread pt;

    public PracticalTest01Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int firstNumber = intent.getIntExtra("firstNumber", -1);
        pt = new ProcessingThread(this, firstNumber);
        pt.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
         pt.stopThread();
    }
}

class ProcessingThread extends Thread {

    private Context context = null;

    private Random random = new Random();

    int score;

    public ProcessingThread(Context context, int firstNumber) {
        this.context = context;
        this.score = firstNumber;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        sleep();
        sendMessage();

        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " Victory " + score);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
    }
}