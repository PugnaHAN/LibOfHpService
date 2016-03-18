package com.hp.hpservicelib;

import android.hardware.usb.UsbDevice;

import java.util.Random;

/**
 * Created by zhangjuh on 2016/3/18.
 */
public class Printer implements PrinterAction{

    private UsbDevice mUsbDevice;

    private static Printer sInstance = null;

    private boolean mIsGranted = false;
    private boolean mIsOnline = false;

    String status = "200";
    String usage = "100";

    private Printer(UsbDevice usbDevice){
        mUsbDevice = usbDevice;
    }

    public static Printer getInstance(UsbDevice usbDevice){
        if(sInstance == null){
            sInstance = new Printer(usbDevice);
        }
        return sInstance;
    }

    public boolean isGranted() {
        return mIsGranted;
    }

    public void setIsGranted(boolean isGranted) {
        mIsGranted = isGranted;
    }

    public UsbDevice getUsbDevice() {
        return mUsbDevice;
    }

    public void setUsbDevice(UsbDevice usbDevice) {
        mUsbDevice = usbDevice;
    }

    public boolean isOnline() {
        return mIsOnline;
    }

    public void setIsOnline(boolean isOnline) {
        mIsOnline = isOnline;
    }

    @Override
    public String getPrinterStatus(){
        synchronized (this) {
            Random random = new Random();
            if(random.nextBoolean()) {
                usage = (Integer.parseInt(usage) + 1) + "";
            }
            return status;
        }
    }

    @Override
    public String getPrintedUsage(){
        synchronized (this) {
            Random random = new Random();
            if(random.nextBoolean()) {
                status = (Integer.parseInt(status) + 1) + "";
            }
            return usage;
        }
    }
}
