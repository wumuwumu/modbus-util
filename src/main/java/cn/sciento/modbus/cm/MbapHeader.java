package cn.sciento.modbus.cm;

import java.nio.ByteBuffer;

import jrain.modbus.utils.ByteUtils;


/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：MbapHeader
 * </pre>
 */
public class MbapHeader {

	public static final int PROTOCOL_ID = 0;
	public static final int LENGTH = 7;

	private final int transactionId;
	private final int protocolId;
	private int length;
	private final int unitId;

	public MbapHeader(int transactionId, int unitId) {
		this(transactionId, PROTOCOL_ID, 0, unitId);
	}

	public MbapHeader(int transactionId, int length, int unitId) {
		this(transactionId, PROTOCOL_ID, length, unitId);
	}

	public MbapHeader(int transactionId, int protocolId, int length, int unitId) {
		this.transactionId = transactionId;
		this.protocolId = protocolId;
		this.length = length;
		this.unitId = unitId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public int getProtocolId() {
		return protocolId;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public int getUnitId() {
		return unitId;
	}

	public static MbapHeader decode(ByteBuffer buffer) {
		int tid = ByteUtils.getUnsignedShort(buffer.getShort());
		int pid = ByteUtils.getUnsignedShort(buffer.getShort());
		int len = ByteUtils.getUnsignedShort(buffer.getShort());
		int uid = ByteUtils.getUnsignedShort(buffer.get());
		return new MbapHeader(tid, pid, len, uid);
	}

	public static void encode(MbapHeader header, ByteBuffer buffer) {
		buffer.put(ByteUtils.getBytesBig12(header.transactionId));
		buffer.put(ByteUtils.getBytesBig12(header.protocolId));
		buffer.put(ByteUtils.getBytesBig12(header.length));
		buffer.put(ByteUtils.getByte1(header.unitId));
	}

}
