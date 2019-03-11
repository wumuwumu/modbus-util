package cn.sciento.modbus.parse.exception;

import java.time.Duration;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：Modbus超时
 * </pre>
 */
public class ModbusTimeoutException extends Exception {

	private static final long serialVersionUID = 1L;
	private final long durationMillis;

	public ModbusTimeoutException(Duration duration) {
		this(duration.toMillis());
	}

	public ModbusTimeoutException(long durationMillis) {
		this.durationMillis = durationMillis;
	}

	@Override
	public String getMessage() {
		String msg = "request timed out after %s ms milliseconds.";
		return String.format(msg, durationMillis);
	}

}
