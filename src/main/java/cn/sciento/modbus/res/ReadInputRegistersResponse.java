package cn.sciento.modbus.res;

import cn.sciento.modbus.cm.FunctionCode;
import cn.sciento.modbus.cm.MbapHeader;
import cn.sciento.modbus.utils.ByteUtils;
import cn.sciento.modbus.utils.ModbusUtils;

import java.nio.ByteBuffer;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：Modbus0x04H响应
 * </pre>
 */
public class ReadInputRegistersResponse implements ModbusResponse {

	private final int deviceId;
	private final int length;
	private final byte[] values;

	public ReadInputRegistersResponse(int deviceId, int length, byte[] values) {
		this.deviceId = deviceId;
		this.length = length;
		this.values = values;
	}

	@Override
	public FunctionCode getFunctionCode() {
		return FunctionCode.ReadInputRegisters;
	}

	@Override
	public int getDeviceId() {
		return deviceId;
	}

	public int getLength() {
		return length;
	}

	public byte[] getValues() {
		return values;
	}

	@Override
	public byte[] getRtuBytes() {
		byte[] rsBytes = new byte[values.length + 5];
		// deviceId
		rsBytes[0] = ByteUtils.getByte1(deviceId);
		// funcCode
		rsBytes[1] = ByteUtils.getByte1(getFunctionCode().getCode());
		// length
		rsBytes[2] = ByteUtils.getByte1(length);
		// VALUE
		System.arraycopy(values, 0, rsBytes, 3, values.length);
		return rsBytes;
	}

	@Override
	public byte[] getAsciiBytes() {
		ByteBuffer rtu = ByteBuffer.allocate(values.length + 3);
		writeDeviceIdAndPdu(rtu);
		// length=1(start)+3*2+2(lrc)+2(end)
		ByteBuffer buffer = ByteBuffer.allocate(values.length * 2 + 11);
		// 开始
		ModbusUtils.writeAsciiStart(buffer);
		// 内容
		ModbusUtils.writeAsciiAndLrc(buffer, rtu.array());
		// 结束
		ModbusUtils.writeAsciiEnd(buffer);
		return buffer.array();
	}

	@Override
	public byte[] getTcpBytes(MbapHeader header) {
		ByteBuffer buffer = ByteBuffer.allocate(values.length + 2 + MbapHeader.LENGTH);
		header.setLength(values.length+2+3);
		MbapHeader.encode(header, buffer);
		writePdu(buffer);
		return buffer.array();
	}
	
	@Override
	public void writeDeviceIdAndPdu(ByteBuffer buffer) {
		// deviceId
		buffer.put(ByteUtils.getByte1(deviceId));
		writePdu(buffer);
	}

	@Override
	public void writePdu(ByteBuffer buffer) {
		// funcCode
		buffer.put(ByteUtils.getByte1(getFunctionCode().getCode()));
		// length
		buffer.put(ByteUtils.getByte1(length));
		// values
		buffer.put(values);
	}

}
