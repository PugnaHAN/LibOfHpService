package com.hp.hpservicelib;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.util.Log;

/**
 * Created by zhangjuh on 2016/3/18.
 */
public class PrinterProxy implements PrinterAction{
    private static final String TAG = PrinterProxy.class.getSimpleName();

    private Printer mPrinter;
    private Context mContext;

    public PrinterProxy(Context context){
        mContext = context;
        UsbDevicesMgr usbDevicesMgr = new UsbDevicesMgr(mContext);
        try {
            mPrinter = usbDevicesMgr.getPrinter();
        } catch (Exception e){
            Log.e(TAG, "Exception happend");
            e.printStackTrace();
        }
        Log.d(TAG, "printer is " + mPrinter.getUsbDevice());
    }

    @Override
    public String getPrinterStatus() throws UnConnectedException{
        if(checkConnection(mPrinter.getUsbDevice())){
            return mPrinter.getPrinterStatus();
        }
        throw new UnConnectedException("Printer is offline!");
    }

    @Override
    public String getPrintedUsage() throws UnConnectedException{
        if(checkConnection(mPrinter.getUsbDevice())){
            return mPrinter.getPrintedUsage();
        }
        throw new UnConnectedException("Printer is offline!");
    }

    private boolean checkConnection(UsbDevice usbDevice) throws UnConnectedException{
        try{
            Thread.sleep(100);
        } catch(Exception e){
            e.printStackTrace();
        }
        UsbDevicesMgr usbDevicesMgr = new UsbDevicesMgr(mContext);
        try{
            return usbDevicesMgr.getPrinter() != null;
        } catch (Exception e){
            throw new UnConnectedException("Printer is offline!");
        }
    }
}
