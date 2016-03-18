package com.hp.testapplication;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hp.hpservicelib.PrinterProxy;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private UsbDevice mUsbDevice = null;

    private PrinterProxy mProxy;

    private Thread thread1;
    private Thread thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProxy = new PrinterProxy(getApplicationContext());
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String status = mProxy.getPrinterStatus();
                        Log.d(TAG, "Status is " + status);
                        Thread.sleep(500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String usage = mProxy.getPrintedUsage();
                        Log.d(TAG, "Usage is " + usage);
                        Thread.sleep(500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        thread1.interrupt();
        thread2.interrupt();
    }
}
