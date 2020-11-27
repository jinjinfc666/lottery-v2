package com.jll.game.lotterytypeservice;

import javax.annotation.Resource;

import com.ehome.test.ServiceJunitBase;
import com.jll.game.LotteryTypeService;

public class SportLottery3ServiceImplTest extends ServiceJunitBase{
		
	public SportLottery3ServiceImplTest(String name) {
		super(name);
	}	
	
	@Resource
	LotteryTypeService lotteryTypeService;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		lotteryTypeService = new SportLottery3ServiceImpl();
	}

	@Override
	protected void tearDown() throws Exception {
		//super.tearDown();
	}
	
	public void testQueryWinningNum(){
		String issueNum = "tc3|20272";
		lotteryTypeService.queryWinningNum(issueNum);
		try {
			Thread.sleep(600*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}