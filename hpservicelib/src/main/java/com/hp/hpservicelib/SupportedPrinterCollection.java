package com.hp.hpservicelib;

import android.hardware.usb.UsbDevice;
import android.util.Log;

import java.util.Vector;

/**
 * Created by zhangjuh on 2016/3/18.
 */
public class SupportedPrinterCollection {
    private static final String TAG = SupportedPrinterCollection.class.getSimpleName();

    public static class PrinterSpec{
        private int mVenderId;
        private int mProductId;

        public PrinterSpec(int vid, int pid){
            mVenderId = vid;
            mProductId = pid;
        }

        public int getVenderId() {
            return mVenderId;
        }

        public void setVenderId(int venderId) {
            mVenderId = venderId;
        }

        public int getProductId() {
            return mProductId;
        }

        public void setProductId(int productId) {
            mProductId = productId;
        }

        @Override
        public String toString(){
            return "VenderId is " + mVenderId + "; ProductId is " + mProductId;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof PrinterSpec){
                PrinterSpec ps  = (PrinterSpec) o;
                return (ps.getVenderId() == mVenderId && ps.getProductId() == mProductId);
            }
            return false;
        }
    }

    private static Vector<PrinterSpec> sPrinters = new Vector<>();

    public static boolean isSupported(UsbDevice usbDevice){
        initPrinters();
        PrinterSpec printerSpec = new PrinterSpec(usbDevice.getVendorId(),
                usbDevice.getProductId());
        for(PrinterSpec ps : sPrinters){
            Log.d(TAG, ps.toString());
        }
        return sPrinters.contains(printerSpec);
    }

    private static void initPrinters(){
        PrinterSpec printerSpec = new PrinterSpec(1008, 50961);
        if(sPrinters.size() == 0) {
            sPrinters.add(printerSpec);
        }
        Log.d(TAG, "supported printer number is :" + sPrinters.size());
    }

    public void addSupportedPrinter(PrinterSpec ps){
        if(ps != null && !sPrinters.contains(ps)) {
            sPrinters.add(ps);
        }
    }
}
