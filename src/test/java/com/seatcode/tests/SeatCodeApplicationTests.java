package com.seatcode.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.seatcode.application.SeatCodeApplication;
import com.seatcode.model.Robot;

@SpringBootTest
@SpringBootConfiguration
@ContextConfiguration
class SeatCodeApplicationTests {
	
	@Autowired
	public static Robot robot=new Robot();
	
	@Autowired
	public static SeatCodeApplication seatCodeApplication= new SeatCodeApplication(); 
	
	public String polyline="mpjyHx`i@VjAVKnAh@BHHX@LZR@Bj@Ml@WWc@]w@bAyAfBmCb@o@pLeQfCsDVa@@ODQR}AJ{A?{BGu AD_@FKb@MTUX]Le@^kBVcAVo@Ta@|EaFh@m@FWaA{DCo@q@mCm@cC{A_GWeA}@sGSeAcA_EOSMa @}A_GsAwFkAiEoAaFaBoEGo@]_AIWW{AQyAUyBQqAI_BFkEd@aHZcDlAyJLaBPqDDeD?mBEiA}@F]yKWqGSkI CmCIeZIuZi@_Sw@{WgAoXS{DOcAWq@KQGIFQDGn@Y`@MJEFIHyAVQVOJGHgFRJBBCCSKBcAKoACyA?m@^y VJmLJ{FGGWq@e@eBIe@Ei@?q@Bk@Hs@Le@Rk@gCuIkJcZsDwLd@g@Oe@o@mB{BgHQYq@qBQYOMSM GBUBGCYc@E_@H]DWJST?JFFHBDNBJ?LED?LBv@WfAc@@EDGNK|@e@hAa@`Bk@b@OEk@Go@IeACoA@ a@PyB`@yDDc@e@K{Bi@oA_@w@]m@_@]QkBoAwC{BmAeAo@s@uAoB_AaBmAwCa@mAo@iCgAwFg@iD q@}G[uEU_GBuP@cICmA?eI?qCB{FBkCI}BOyCMiAGcAC{AN{YFqD^}FR}CNu@JcAHu@b@_E`@}DVsB^mBTsAQ KkCmAg@[YQOIOvAi@[m@e@s@g@GKCKAEJIn@g@GYGIc@ScBoAf@{A`@uAlBfAG`@";
	public String robotName="robot";
	public Long setZero=0L;
	public double getExpetedDistanceBetweenPoints;
	public String expectedResult="0.01678121649803909";
	public String notExpectedResult="27.0";
	public Double expectedResults=0.01678121649803909;
	
	@Test
	void contextLoads() {
			
	}
	
	@Test
	public void testGetDistanceBeetwenPoints() {
		
		double testPoint1=51.50487;
		double testPoint2=-0.21571000;
		double testPoint3=51.50463000;
		double testPoint4=-0.21565000;
		String unit="M";
		
		Double seatGetDistance=seatCodeApplication.distance(testPoint1, testPoint2, testPoint3, testPoint4, unit);
		assertEquals(seatGetDistance,expectedResults);
	}
	
	
	@Test
	public void testRobotPolyLine() {
		String robotPolyline=robot.getPolyline();
		assertEquals(robotPolyline,polyline);
	}

	@Test
	public void testRobotPolyLineNotNull() {
		String robotPolyline=robot.getPolyline();
		assertNotNull(robotPolyline,"The robot Polyline must not be null");
	}
	
	@Test
	public void testRobotNameNotNull() {
	  String robotName=robot.getName();
	  assertNotNull(robotName,"The robot name must not be null");
	}
	
	@Test
	public void testRobotName() {
		String robotProbablyNamed=robot.getName();
		assertEquals(robotProbablyNamed,robotName);
	}
	
	@Test
	public void testSeatCodeSetZero() {
		Long seatSetZero=seatCodeApplication.setZero;
		assertEquals(seatSetZero,setZero);
	}
	
}
