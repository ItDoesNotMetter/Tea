package com.fengjie.model.helper.printer;

public interface SerialPortDataReceived {
	public void onDataReceivedListener ( final byte[] buffer, final int size );

}
