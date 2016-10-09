package org.forms;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class UploadJobOperate {
	
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";  
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME"; 
    
    /** 
     * ���һ����ʱ����ʹ��Ĭ�ϵ����������������������� 
     * 
     * @param jobName ������ 
     * @param jobClass ���� 
     * @param time ʱ�����ã��ο�quartz˵���ĵ� 
     * @throws SchedulerException 
     * @throws ParseException 
     */  
    public static void addJob(String jobName, String jobClass, String time) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, Class.forName(jobClass));
            // ������  
            CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// ��������,��������  
            trigger.setCronExpression(time);// ������ʱ���趨  
            sched.scheduleJob(jobDetail, trigger);  
            // ����  
            if (!sched.isShutdown()){  
                sched.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    } 
       
    /** 
     * �޸�һ������Ĵ���ʱ��(ʹ��Ĭ�ϵ�����������������������) 
     * 
     * @param jobName 
     * @param time 
     */  
    public static void modifyJobTime(String jobName, String time) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);  
            if(trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);  
                Class objJobClass = jobDetail.getJobClass();  
                String jobClass = objJobClass.getName();  
                removeJob(jobName);    
                addJob(jobName, jobClass, time);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    } 
    
    /** 
     * �Ƴ�һ������(ʹ��Ĭ�ϵ�����������������������) 
     * 
     * @param jobName 
     */  
    public static void removeJob(String jobName) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// ֹͣ������  
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// �Ƴ���  
            sched.deleteJob(jobName, JOB_GROUP_NAME);// ɾ������  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }
    
    /** 
     * �ر����ж�ʱ���� 
     */  
    public static void shutdownJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            if(!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }
}
