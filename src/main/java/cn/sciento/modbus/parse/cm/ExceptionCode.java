package cn.sciento.modbus.parse.cm;

import java.util.Optional;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：异常代码
 * </pre>
 */
public enum ExceptionCode {

	// 非法函数
	IllegalFunction(0x01),
	// 非法数据地址
	IllegalDataAddress(0x02),
	// 非法数值
	IllegalDataValue(0x03),
	// 从设备故障
	SlaveDeviceFailure(0x04),
	// Acknowledge
	Acknowledge(0x05),
	// 从设备忙
	SlaveDeviceBusy(0x06),
	// 内存奇偶校验错误
	MemoryParityError(0x08),
	// 网关路径不可用
	GatewayPathUnavailable(0x0A),
	// 网关目标设备未能响应
	GatewayTargetDeviceFailedToResponse(0x0B);

	private final int code;

	ExceptionCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Optional<ExceptionCode> fromCode(int code) {
		switch (code) {
		case 0x01:
			return Optional.of(IllegalFunction);
		case 0x02:
			return Optional.of(IllegalDataAddress);
		case 0x03:
			return Optional.of(IllegalDataValue);
		case 0x04:
			return Optional.of(SlaveDeviceFailure);
		case 0x05:
			return Optional.of(Acknowledge);
		case 0x06:
			return Optional.of(SlaveDeviceBusy);
		case 0x08:
			return Optional.of(MemoryParityError);
		case 0x0A:
			return Optional.of(GatewayPathUnavailable);
		case 0x0B:
			return Optional.of(GatewayTargetDeviceFailedToResponse);
		}

		return Optional.empty();
	}

}
