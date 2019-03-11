package cn.sciento.modbus.exception;

import jrain.modbus.res.ExceptionResponse;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：Modbus异常
 * </pre>
 */
public class ModbusResponseException extends Exception {
 
	private static final long serialVersionUID = 1L;
	
	private final ExceptionResponse response;

	public ModbusResponseException(ExceptionResponse response) {
		this.response = response;
	}

	public ExceptionResponse getResponse() {
		return response;
	}

	@Override
	public String getMessage() {
		String msg = "functionCode=%s, exceptionCode=%s";
		return String.format(msg, response.getFunctionCode(), response.getExceptionCode());
	}

}
