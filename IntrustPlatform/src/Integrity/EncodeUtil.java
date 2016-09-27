package Integrity;

public class EncodeUtil 
{
	// TO HEX STRING
	public static String toHexString(byte[] data) {
		StringBuffer hexStr = new StringBuffer();
		StringBuffer sb = new StringBuffer(2);
		for (int i = 0; i < data.length; i++) {
			sb.replace(0, sb.length(), Integer.toHexString(data[i])
					.toUpperCase());
			// data[i] MSB is 1, covert into integer, it will expand to
			// 0xFFFFFFXX
			if (sb.length() > 2) // 0xFFFFFFC1 delete the prefix FFFFFF
				sb.delete(0, 6);
			if (sb.length() < 2)
				sb.insert(0, '0');
			hexStr.append(sb.toString());
			hexStr.append(" ");
		}
		return hexStr.toString();
	
	}
	
	public static String toHexFormatString(byte[] data) {
		StringBuffer hexStr = new StringBuffer();
		StringBuffer sb = new StringBuffer(2);
		int j = 0;
		for (int i = 0; i < data.length; i++) {
			sb.replace(0, sb.length(), Integer.toHexString(data[i])
					.toUpperCase());
			// data[i] MSB is 1, covert into integer, it will expand to
			// 0xFFFFFFXX
			if (sb.length() > 2) // 0xFFFFFFC1 delete the prefix FFFFFF
				sb.delete(0, 6);
			if (sb.length() < 2)
				sb.insert(0, '0');
			hexStr.append(sb.toString());
			j++;
			hexStr.append(" ");
			if(j == 0x10)
			{
				j = 0;
				hexStr.append("\n");
			}
		}
		return hexStr.toString();
	}
	public static String to32HexString(int x) {
		// Convert Format : 0x0000000A
		StringBuffer s = new StringBuffer(Integer.toHexString(x).toUpperCase());
		int len = s.length();
		if (len > 8)
			return null;
		for (int i = 0; i < 8 - len; i++)
			s.insert(0, '0');
		s.insert(0, "0x");
		return s.toString();
	}
	
	public static String Trim(String s)
	{
		char[] cos = s.toCharArray();
		//get the start index
		int i = 0;
		while(cos[i] == ' ' || cos[i] == '\t' || cos[i] == '\n')
		{
			i++;
		}
		// get the end index
		int j = s.length() - 1;
		while(cos[j] == ' ' || cos[j] == '\t' || cos[j] == '\n')
		{
			j--;
		}
		return s.substring(i, j+1);		
	}
	
	// Hex String Encode or Decode
    static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String hexEncode(byte[] bytes) {
		StringBuffer s = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			s.append(digits[(b & 0xf0) >> 4]);
			s.append(digits[b & 0x0f]);
		}
		return s.toString();
	}

	// hex String to byte[]
	public static byte[] hexDecode(String s) throws IllegalArgumentException {
		try {
			int len = s.length();
			s= s.toUpperCase();
			byte[] r = new byte[len / 2];
			for (int i = 0; i < r.length; i++) {
				int digit1 = s.charAt(i * 2), digit2 = s.charAt(i * 2 + 1);
				if ((digit1 >= '0') && (digit1 <= '9'))
					digit1 -= '0';
				else if ((digit1 >= 'A') && (digit1 <= 'F'))
					digit1 = digit1 - 'A' + 10;
				if ((digit2 >= '0') && (digit2 <= '9'))
					digit2 -= '0';
				else if ((digit2 >= 'A') && (digit2 <= 'F'))
					digit2 = digit2 - 'A' + 10;
				r[i] = (byte) ((digit1 << 4) + digit2);
			}
			return r;
		} catch (Exception e) {
			throw new IllegalArgumentException("hexDecode(): invalid input");
		}
	}
}

