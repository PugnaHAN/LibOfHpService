package com.hp.hpservicelib;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by zhangjuh on 2016/3/17.
 */
public class UsbDevicesMgr {
    private static final String TAG = UsbDevicesMgr.class.getSimpleName();

    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";

    private Context mContext;

    public UsbDevicesMgr(Context context){
        mContext = context;
    }

    public Printer getPrinter() throws NonSupportedPrinterFoundException{
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> devices = usbManager.getDeviceList();
        for(UsbDevice device : devices.values()){
            if(SupportedPrinterCollection.isSupported(device)){
                return Printer.getInstance(device);
            }
        }
        throw new NonSupportedPrinterFoundException("No supported printer was found!");
    }

    public void getUsbPermission(UsbDevice usbDevice){
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        if(!usbManager.hasPermission(usbDevice)){
            Intent intent = new Intent(ACTION_USB_PERMISSION);
            intent.putExtra(UsbManager.EXTRA_DEVICE, usbDevice);
            intent.putExtra(UsbManager.EXTRA_PERMISSION_GRANTED, true);
            mContext.sendBroadcast(intent);

            PendingIntent permissionIntent = PendingIntent.getBroadcast(
                    mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            mContext.registerReceiver(mUsbReceiver, filter);
            usbManager.requestPermission(usbDevice, permissionIntent);
        }
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    Printer printer = Printer.getInstance(device);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            printer.setIsGranted(true);
                            printer.setIsOnline(true);
                        }
                    }
                }
            }
        }
    };
}
