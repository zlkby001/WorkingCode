package ui;
import Integrity.IntegrityProducer;

public class IntegrityProducerGenerator {
	static private IntegrityProducer producer;
	
	static private void getNew(){
		producer = new IntegrityProducer();
	
	}
	
	static public IntegrityProducer getProducer(){
		if (producer == null){
			getNew();
		}
		
		return producer;
			
	}

}
