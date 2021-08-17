package com.ksproyectos.parking4all.DriverFacades.Q2;

import android.content.Context;

import com.cloudpos.DeviceException;
import com.cloudpos.POSTerminal;
import com.cloudpos.printer.Format;
import com.cloudpos.printer.PrinterDevice;

public class Q2DriverFacade {

    private PrinterDevice printerDevice;

    public Context context;

    private int lengthLine = 32;

    private Format format = new Format();

    public void setContext(Context context) {
        this.context = context;
    }

    public void printText(String text) throws DeviceException {
        printerDevice.printText(format, text);
        printerDevice.printText("\n");
    }

    public void printTextWithFormat(Format format, String text) throws DeviceException {
        printerDevice.printText(format, text);
    }

    public Format setTextFormat(int fuente, int negrilla, int subrayado) throws DeviceException {

        format.setParameter("align","left");                                                        // Lineado

        if (negrilla == 1) {
            format.setParameter("bold", "true");
            byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x21, 8};
            printerDevice.sendESCCommand(cmd);
        }else {
            format.setParameter("bold", "false");
            byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x21, 0};
            printerDevice.sendESCCommand(cmd);
        }

        switch (fuente) {
            case 0:                                                                                 // Normal
                format.setParameter("size","medium");
                lengthLine = 32;
                break;

            case 1:                                                                                 // Small
                format.setParameter("size","small");
                lengthLine = 42;
                break;

            case 2:                                                                                 // Big
                format.setParameter("size","large");
                lengthLine = 16;
                break;

            case 3:                                                                                 // Extra Big
                format.setParameter("size","extra-large");
                lengthLine = 10;
                break;

            default:
                format.setParameter("size","medium");
                lengthLine = 32;
                break;

        }

        if (subrayado == 1) {
            byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x21, (byte) 128};
            printerDevice.sendESCCommand(cmd);
        }else {
            byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x21, (byte) 0};
            printerDevice.sendESCCommand(cmd);
        }

        return format;

    }


    public void setLineSpace(int unidades) throws DeviceException {
        byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x33, (byte) unidades};

        printerDevice.sendESCCommand(cmd);
    }


    public Boolean openPrinter() throws DeviceException {

        Boolean isPrinterReady;

        isPrinterReady = isPrinterReady();

        if (!isPrinterReady){
            printerDevice.close();
        } else {
            printerDevice = (PrinterDevice) POSTerminal.getInstance(context).getDevice(
                    "cloudpos.device.printer");

            printerDevice.open();
        }

        return isPrinterReady;
    }

    public boolean closePrinter() throws DeviceException {
        printerDevice.close();
        return true;
    }


    public Boolean isPrinterReady() throws DeviceException {

        boolean result = false;                                                                                // Inicializa ret en 0

        printerDevice = (PrinterDevice) POSTerminal.getInstance(context).getDevice(
                "cloudpos.device.printer");

        try {
            printerDevice.open();
        }catch (DeviceException e){
            if(e.getCode() != -1){
                throw e;
            }
        }

        if (printerDevice.queryStatus() == printerDevice.STATUS_OUT_OF_PAPER) {
            result = false;
        }else {
            result = true;
        }

        printerDevice.close();

        return result;
    }


    public void printCenteredText(String text) throws DeviceException {
        int l, i;

        String espacios = "";

        i = 0;

        l = ((lengthLine - (text.getBytes().length)) / 2);

        while (i < l) {
            espacios = espacios + " ";
            i++;
        }

        printText(espacios + text);

        printText("\n");

    }

    public void printLineBreaks(int count) throws DeviceException {
        for (int i = 0; i < count; i++){
            printText("\n");
        }
    }

    public void feedInDots(int dotCount) throws DeviceException {
        byte[] cmd = new byte[]{(byte) 0x1B, (byte) 0x4A, (byte) dotCount};
        printerDevice.sendESCCommand(cmd);
    }




}
