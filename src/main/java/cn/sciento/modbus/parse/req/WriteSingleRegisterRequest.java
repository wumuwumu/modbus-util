package cn.sciento.modbus.parse.req;

import cn.sciento.modbus.parse.cm.FunctionCode;
import cn.sciento.modbus.parse.cm.MbapHeader;
import cn.sciento.modbus.parse.utils.ByteUtils;
import cn.sciento.modbus.parse.utils.ModbusUtils;

import java.nio.ByteBuffer;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：Modbus0x06H请求
 * </pre>
 */
public class WriteSingleRegisterRequest implements ModbusRequest {

	private final int deviceId;
	private final int address;
	private final int value;

	public WriteSingleRegisterRequest(int deviceId, int address, int value) {
		this.deviceId = deviceId;
		this.address = address;
		this.value = value;
	}

	@Override
	public int getDeviceId() {
		return deviceId;
	}

	public int getAddress() {
		return address;
	}

	public int getValue() {
		return value;
	}

	@Override
	public FunctionCode getFunctionCode() {
		return FunctionCode.WriteSingleRegister;
	}

	@Override
	public byte[] getRtuBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		writeDeviceIdAndPdu(buffer);
		ModbusUtils.writeCRC(buffer);
		return buffer.array();
	}

	@Override
	public byte[] getAsciiBytes() {
		ByteBuffer rtu = ByteBuffer.allocate(6);
		writeDeviceIdAndPdu(rtu);
		// length=1(start)+6*2+2(lrc)+2(end)
		ByteBuffer buffer = ByteBuffer.allocate(17);
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
		ByteBuffer buffer = ByteBuffer.allocate(5 + MbapHeader.LENGTH);
		header.setLength(5+3);
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
		// address
		buffer.put(ByteUtils.getByte2(address));
		buffer.put(ByteUtils.getByte1(address));
		// VALUE
		buffer.put(ByteUtils.getByte2(value));
		buffer.put(ByteUtils.getByte1(value));
	}

}
