package Integrity;

public class test {

		public static void main(String args[]) {
		try {
			System.out.println("success");

			Runtime.getRuntime().exec("sh ps.sh");
			Runtime.getRuntime().exec("gedit ps.txt");
System.out.println("success");
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		int ret=0;
//		String fn = "pfile";
//		
//		IntegrityProducer ip = new IntegrityProducer();
//		
//		ret = ip.generateFile(fn,"ALL");
//		
//		System.out.println(ret);
//	}

//}
