package cn.sciento.modbus.cm;

import java.nio.ByteBuffer;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：ModbusAdu
 * </pre>
 */
public interface ModbusAdu {

	public int getDeviceId();

	FunctionCode getFunctionCode();

	public byte[] getRtuBytes();

	public byte[] getAsciiBytes();

	public byte[] getTcpBytes(MbapHeader header);

	public void writeDeviceIdAndPdu(ByteBuffer buffer);

	public void writePdu(ByteBuffer buffer);

}
