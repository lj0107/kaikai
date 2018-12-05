package com.qtong.afinance.module.unitTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qtong.afinance.module.quartz.BillQuartz;
import com.qtong.afinance.module.quartz.StatsQuartz;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml","file:WebContent/WEB-INF/applicationContext-redis.xml"})
public class TestQuartz {

	@Autowired
	private StatsQuartz statsQuartz;
	@Autowired
	private BillQuartz billQuartz;
	
	@Test
	public void testExecute(){
		
		statsQuartz.execute();
	}
	@Test
	public void testBill(){
		
		billQuartz.execute();
	}
}
