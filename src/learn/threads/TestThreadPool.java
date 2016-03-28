package learn.threads;

import org.junit.Assert;
import org.junit.Test;

public class TestThreadPool {
	public class JobTypeOne implements IJob {
		private int processTime;
		private long pst;
		private long endTime;
		public JobTypeOne(int processTime, long processStartTime) {
			this.processTime = processTime;
			this.pst = processStartTime;
		}
		@Override
		public void process() {
			try {
				//do some heavy job
				Thread.sleep(processTime);
				this.endTime = System.currentTimeMillis() - pst;
				System.out.println("Endtime: " + endTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public long getEndTime() {
			return this.endTime;
		}
	}
	
	private static final int EPS_TIME = 50;

	@Test
	public void testTypeOne2Thread() {
		long startTime = System.currentTimeMillis();
		JobTypeOne[]  list2TP = {
				new JobTypeOne(3000,startTime), //3000 , 3000	 
				new JobTypeOne(1000,startTime), // 1000, 1000
				new JobTypeOne(1500,startTime), // 2500, 1500
				new JobTypeOne(600,startTime), // 3100, 1600
				new JobTypeOne(2000,startTime), //5000, 3500
				new JobTypeOne(3000,startTime), //6100, 4600
				new JobTypeOne(1500,startTime), //6500, 4500
				new JobTypeOne(1500,startTime), //7600, 5000
		};

		long[] EXPECTED_END_TIME_TP2 = {3000,1000,2500,3100,5000,6100,6500,7600};

		MyThreadPool tp2Thread = new MyThreadPool(2);
		for(IJob task : list2TP) {
			tp2Thread.add(task);
		}

		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < list2TP.length ; i++) {
			JobTypeOne job = list2TP[i];
			Assert.assertTrue(Math.abs(job.getEndTime()-EXPECTED_END_TIME_TP2[i]) < EPS_TIME);
		}
		
//		long[] EXPECTED_END_TIME_TP3 = {3000,1000,1500,1600,3500,4600,4500,5000};
//		MyThreadPool tp3Thread = new MyThreadPool(3);
//		startTime = System.currentTimeMillis();

	}
	@Test
	public void testTypeOne3Thread() {
		long startTime = System.currentTimeMillis();
		JobTypeOne[]  list = {
				new JobTypeOne(3000,startTime), //3000 , 3000	 
				new JobTypeOne(1000,startTime), // 1000, 1000
				new JobTypeOne(1500,startTime), // 2500, 1500
				new JobTypeOne(600,startTime), // 3100, 1600
				new JobTypeOne(2000,startTime), //5000, 3500
				new JobTypeOne(3000,startTime), //6100, 4600
				new JobTypeOne(1500,startTime), //7500, 4500
				new JobTypeOne(1500,startTime), //8600, 5000
		};

		long[] EXPECTED_END_TIME = {3000,1000,1500,1600,3500,4600,4500,5000};

		MyThreadPool tp2Thread = new MyThreadPool(3);
		for(IJob task : list) {
			tp2Thread.add(task);
		}

		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < list.length ; i++) {
			JobTypeOne job = list[i];
			Assert.assertTrue(Math.abs(job.getEndTime()-EXPECTED_END_TIME[i]) < EPS_TIME);
		}
		
	}

}
