package cn.sciento.modbus.res;

import java.nio.ByteBuffer;

import jrain.modbus.cm.ExceptionCode;
import jrain.modbus.cm.FunctionCode;
import jrain.modbus.cm.MbapHeader;
import jrain.modbus.utils.ByteUtils;
import jrain.modbus.utils.ModbusUtils;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：异常响应
 * </pre>
 */
public class ExceptionResponse implements ModbusResponse {

	private final int deviceId;
	private final FunctionCode functionCode;
	private final ExceptionCode exceptionCode;

	public ExceptionResponse(int deviceId, FunctionCode functionCode, ExceptionCode exceptionCode) {
		this.deviceId = deviceId;
		this.functionCode = functionCode;
		this.exceptionCode = exceptionCode;
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	@Override
	public FunctionCode getFunctionCode() {
		return functionCode;
	}

	@Override
	public int getDeviceId() {
		return deviceId;
	}

	public byte[] getRtuBytes() {
		//
		ByteBuffer buffer = ByteBuffer.allocate(5);
		writeDeviceIdAndPdu(buffer);
		ModbusUtils.writeCRC(buffer);
		return buffer.array();
	}

	@Override
	public byte[] getAsciiBytes() {
		ByteBuffer rtu = ByteBuffer.allocate(3);
		writeDeviceIdAndPdu(rtu);
		// length=1(start)+3*2+2(lrc)+2(end)
		ByteBuffer buffer = ByteBuffer.allocate(11);
		// 开始
		ModbusUtils.writeAsciiStart(buffer);
		// 内容
		ModbusUtils.writeAsciiAndLrc(buffer, rtu.array());
		// 结束
		ModbusUtils.writeAsciiEnd(buffer);
		return buffer.array();
	}

	public byte[] getTcpBytes(MbapHeader header) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + MbapHeader.LENGTH);
		header.setLength(2+3);
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
		buffer.put(ByteUtils.getByte1(0x80 + getFunctionCode().getCode()));
		// errorCode
		buffer.put(ByteUtils.getByte1(getExceptionCode().getCode()));
	}

}