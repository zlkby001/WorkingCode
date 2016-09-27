package ui;

import org.dboperate.MeasurementLogUpdate;
import org.forms.Methods;

public class KnowledgeDownloaderProcess extends Thread {
	public void run()
	{
		MeasurementLogUpdate measurementlogUpdate  = new MeasurementLogUpdate(org.forms.MainForm.db);
		measurementlogUpdate.update();
		//Methods.Alert("下载完成！");
	}

}
