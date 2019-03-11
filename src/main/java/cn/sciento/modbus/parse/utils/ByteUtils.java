package cn.sciento.modbus.parse.utils;

/**
 * <pre>
 * 作者：3244924214@qq.com 
 * 描述：字节处理工具类
 * </pre>
 */
public class ByteUtils {

	private static final char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	// 整数字节-------------------------------------------------------------------------------------
	public static byte getByte1(int value) {
		return (byte) (0xff & value);
	}

	public static byte getByte2(int value) {
		return (byte) (0xff & value >> 8);
	}

	public static byte getByte3(int value) {
		return (byte) (0xff & value >> 16);
	}

	public static byte getByte4(int value) {
		return (byte) (0xff & value >> 24);
	}

	// 大端序列-------------------------------------------------------------------------------------
	public static byte[] getBytesBig12(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte2(value);
		bytes[1] = getByte1(value);
		return bytes;
	}

	public static byte[] getBytesBig13(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte3(value);
		bytes[1] = getByte2(value);
		bytes[2] = getByte1(value);
		return bytes;
	}

	public static byte[] getBytesBig14(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte4(value);
		bytes[1] = getByte3(value);
		bytes[2] = getByte2(value);
		bytes[3] = getByte1(value);
		return bytes;
	}

	public static byte[] getBytesBig23(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte3(value);
		bytes[1] = getByte2(value);
		return bytes;
	}

	public static byte[] getBytesBig24(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte4(value);
		bytes[1] = getByte3(value);
		bytes[2] = getByte2(value);
		return bytes;
	}

	public static byte[] getBytesBig34(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte4(value);
		bytes[1] = getByte3(value);
		return bytes;
	}

	// 小端序列-------------------------------------------------------------------------------------
	public static byte[] getBytesLow12(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte1(value);
		bytes[1] = getByte2(value);
		return bytes;
	}

	public static byte[] getBytesLow13(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte1(value);
		bytes[1] = getByte2(value);
		bytes[2] = getByte3(value);
		return bytes;
	}

	public static byte[] getBytesLow14(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte1(value);
		bytes[1] = getByte2(value);
		bytes[2] = getByte3(value);
		bytes[3] = getByte4(value);
		return bytes;
	}

	public static byte[] getBytesLow23(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte1(value);
		bytes[1] = getByte2(value);
		return bytes;
	}

	public static byte[] getBytesLow24(int value) {
		byte[] bytes = new byte[3];
		bytes[0] = getByte2(value);
		bytes[1] = getByte3(value);
		bytes[2] = getByte4(value);
		return bytes;
	}

	public static byte[] getBytesLow34(int value) {
		byte[] bytes = new byte[2];
		bytes[0] = getByte3(value);
		bytes[1] = getByte4(value);
		return bytes;
	}

	public static int getUnsignedByte(byte value) {
		return value & 0xFFFF;
	}

	public static int getUnsignedShort(short value) {
		return value & 0xFFFF;
	}

	// 进制转换-------------------------------------------------------------------------------------
	public static String byteToHex(byte b) {
		int n = b & 0xff;
		int h = n >> 4;
		int l = 0x0f & n;
		return "" + chs[h] + chs[l];
	}

	public static String byteToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2 + 1);
		if (bytes != null) {
			for (byte b : bytes) {
				sb.append(byteToHex(b));
			}
		}
		return sb.toString();
	}

	public static byte[] hexStrToBytes(String hex) {
		if (hex != null) {
			int hlength = hex.length();
			if (hlength % 2 != 0) {
				throw new RuntimeException("Invalid  hexadecimal character length:" + hlength);
			}
			byte[] bytes = new byte[hlength];
			for (int i = 0; i < hex.length(); i++) {
				int c = hex.charAt(i);
				if (c >= 48 && c <= 57) {
					c = (c - 48);
				} else if (c >= 65 && c <= 70) {
					c = (c - 55);
				} else if (c >= 97 && c <= 102) {
					c = (c - 87);
				} else {
					throw new RuntimeException("Invalid  hexadecimal character :" + c);
				}
				if (i % 2 == 0) {
					bytes[i] = ByteUtils.getByte1(c);
				} else {
					bytes[i] = ByteUtils.getByte1(c);
				}
			}

			byte[] rsBytes = new byte[hlength / 2];
			int k = 0;
			for (int i = 0; i < bytes.length; i = i + 2) {
				rsBytes[k++] = ByteUtils.getByte1((bytes[i] * 16 + bytes[i + 1]));
			}
			return rsBytes;
		}
		return new byte[0];
	}
	
	

}
