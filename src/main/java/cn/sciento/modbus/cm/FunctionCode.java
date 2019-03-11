
package cn.sciento.modbus.cm;

import java.util.Optional;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：功能代码
 * </pre>
 */
public enum FunctionCode {

	// Read Coils
	ReadCoils(0x01),
	// Read Discrete Inputs
	ReadDiscreteInputs(0x02),
	// Read Holding Registers
	ReadHoldingRegisters(0x03),
	// Read Input Registers
	ReadInputRegisters(0x04),
	// Write Single Coil
	WriteSingleCoil(0x05),
	// Write Single Register
	WriteSingleRegister(0x06),
	// 0x07
	ReadExceptionStatus(0x07),
	// 0x08
	Diagnostics(0x08),
	// 0x0B
	GetCommEventCounter(0x0B),
	// 0x0C
	GetCommEventLog(0x0C),
	// Write Multiple Coils
	WriteMultipleCoils(0x0F),
	// Write Multiple Registers
	WriteMultipleRegisters(0x10),
	// 0x11
	ReportSlaveId(0x11),
	// 0x14
	ReadFileRecord(0x14),
	// 0x15
	WriteFileRecord(0x15),
	// 0x16
	MaskWriteRegister(0x16),
	// 0x17
	ReadWriteMultipleRegisters(0x17),
	// 0x18
	ReadFifoQueue(0x18),
	// 0x2B
	EncapsulatedInterfaceTransport(0x2B);

	private final int code;

	FunctionCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Optional<FunctionCode> fromCode(int code) {
		switch (code) {
		case 0x01:
			return Optional.of(ReadCoils);
		case 0x02:
			return Optional.of(ReadDiscreteInputs);
		case 0x03:
			return Optional.of(ReadHoldingRegisters);
		case 0x04:
			return Optional.of(ReadInputRegisters);
		case 0x05:
			return Optional.of(WriteSingleCoil);
		case 0x06:
			return Optional.of(WriteSingleRegister);
		case 0x07:
			return Optional.of(ReadExceptionStatus);
		case 0x08:
			return Optional.of(Diagnostics);
		case 0x0B:
			return Optional.of(GetCommEventCounter);
		case 0x0C:
			return Optional.of(GetCommEventLog);
		case 0x0F:
			return Optional.of(WriteMultipleCoils);
		case 0x10:
			return Optional.of(WriteMultipleRegisters);
		case 0x11:
			return Optional.of(ReportSlaveId);
		case 0x14:
			return Optional.of(ReadFileRecord);
		case 0x15:
			return Optional.of(WriteFileRecord);
		case 0x16:
			return Optional.of(MaskWriteRegister);
		case 0x17:
			return Optional.of(ReadWriteMultipleRegisters);
		case 0x18:
			return Optional.of(ReadFifoQueue);
		case 0x2B:
			return Optional.of(EncapsulatedInterfaceTransport);
		}
		return Optional.empty();
	}

	public static boolean isExceptionCode(int code) {
		return fromCode(code - 0x80).isPresent();
	}

}
