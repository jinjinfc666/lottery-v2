package com.jll.game.lotterytypeservice;

import javax.annotation.Resource;

import com.ehome.test.ServiceJunitBase;
import com.jll.game.LotteryTypeService;

public class CqsscServiceImplTest extends ServiceJunitBase{
		
	public CqsscServiceImplTest(String name) {
		super(name);
	}	
	
	@Resource
	LotteryTypeService lotteryTypeService;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		lotteryTypeService = new CqsscServiceImpl();
	}

	@Override
	protected void tearDown() throws Exception {
		//super.tearDown();
	}
	
	public void testQueryWinningNum(){
		String issueNum = "cqssc|190408-041";
		lotteryTypeService.queryWinningNum(issueNum);
		
	}
	
}