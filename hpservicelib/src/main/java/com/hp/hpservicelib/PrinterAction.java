package com.hp.hpservicelib;

/**
 * Created by zhangjuh on 2016/3/18.
 */
public interface PrinterAction {
    String getPrinterStatus() throws UnConnectedException;
    String getPrintedUsage() throws UnConnectedException;
}
