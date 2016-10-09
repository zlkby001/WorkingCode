package ui;

public class MeasureProcess implements Runnable {
	
	LibMeasure libm= new LibMeasure();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			libm.TM_startListenMeasureValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

}
