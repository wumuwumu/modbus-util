package cn.sciento.modbus.req;

import java.nio.ByteBuffer;

import jrain.modbus.cm.FunctionCode;
import jrain.modbus.cm.MbapHeader;
import jrain.modbus.utils.ByteUtils;
import jrain.modbus.utils.ModbusUtils;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：Modbus0x0FH请求
 * </pre>
 */
public class WriteMultipleCoilsRequest implements ModbusRequest {

	private final int deviceId;
	private final int address;
	private final int num;
	private final int length;
	private final byte[] values;

	public WriteMultipleCoilsRequest(int deviceId, int address, int num, int length, byte[] values) {
		this.deviceId = deviceId;
		this.address = address;
		this.num = num;
		this.length = length;
		this.values = values;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public int getAddress() {
		return address;
	}

	public int getNum() {
		return num;
	}

	public int getLength() {
		return length;
	}

	public byte[] getValues() {
		return values;
	}

	@Override
	public FunctionCode getFunctionCode() {
		return FunctionCode.WriteMultipleCoils;
	}

	public byte[] getRtuBytes() {
		// deviceId(1)+funcCode(1)+address(2)+NUM(2)+length(1)+CRC(2)=9
		ByteBuffer buffer = ByteBuffer.allocate(values.length + 9);
		writeDeviceIdAndPdu(buffer);
		ModbusUtils.writeCRC(buffer);
		return buffer.array();
	}

	@Override
	public byte[] getAsciiBytes() {
		ByteBuffer rtu = ByteBuffer.allocate(values.length + 7);
		writeDeviceIdAndPdu(rtu);
		// length=1(start)+(values.length+7)*2+2(lrc)+2(end)
		ByteBuffer buffer = ByteBuffer.allocate(values.length * 2 + 19);
		// 开始
		ModbusUtils.writeAsciiStart(buffer);
		// 内容
		ModbusUtils.writeAsciiAndLrc(buffer, rtu.array());
		// 结束
		ModbusUtils.writeAsciiEnd(buffer);
		return buffer.array();
	}

	public byte[] getTcpBytes(MbapHeader header) {
		ByteBuffer buffer = ByteBuffer.allocate(values.length + 6 + MbapHeader.LENGTH);
		header.setLength(values.length+6+3);
		MbapHeader.encode(header, buffer);
		writePdu(buffer);
		return buffer.array();
	}

	public void writeDeviceIdAndPdu(ByteBuffer buffer) {
		// deviceId
		buffer.put(ByteUtils.getByte1(deviceId));
		writePdu(buffer);
	}

	public void writePdu(ByteBuffer buffer) {
		// funcCode
		buffer.put(ByteUtils.getByte1(getFunctionCode().getCode()));
		// address
		buffer.put(ByteUtils.getByte2(address));
		buffer.put(ByteUtils.getByte1(address));
		// NUM
		buffer.put(ByteUtils.getByte2(num));
		buffer.put(ByteUtils.getByte1(num));
		// length
		buffer.put(ByteUtils.getByte1(length));
		// VALUE
		buffer.put(values);
	}
}
