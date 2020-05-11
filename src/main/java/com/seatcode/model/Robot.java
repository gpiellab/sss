package com.seatcode.model;
import java.util.HashMap;
import java.util.List;

import com.seatcode.utils.*;

import java.io.*; 
import java.lang.*; 

public class Robot {
	
	public String polyline;
	
	//The values of the JSON_FILE
	public static long now;
	public static long velocity;
	public String level="USR";
	public Long accumulatedlevelReport=(long) 0;
	
	//Current lat and 
	public double lat;
	public double lng;
	
	public String source;
	public String name="robot";
	public List<LatLng> differentsRoutes; //All the routes
	public List<LatLng> currentRoute; //The single LatLng	
	public long distanceWalked;
	
	public Robot() {
		distanceWalked=-100;
		lat=51.504870000000004;
		lng=-0.21533000000000002;
		velocity=100;
		polyline="mpjyHx`i@VjAVKnAh@BHHX@LZR@Bj@Ml@WWc@]w@bAyAfBmCb@o@pLeQfCsDVa@@ODQR}AJ{A?{BGu AD_@FKb@MTUX]Le@^kBVcAVo@Ta@|EaFh@m@FWaA{DCo@q@mCm@cC{A_GWeA}@sGSeAcA_EOSMa @}A_GsAwFkAiEoAaFaBoEGo@]_AIWW{AQyAUyBQqAI_BFkEd@aHZcDlAyJLaBPqDDeD?mBEiA}@F]yKWqGSkI CmCIeZIuZi@_Sw@{WgAoXS{DOcAWq@KQGIFQDGn@Y`@MJEFIHyAVQVOJGHgFRJBBCCSKBcAKoACyA?m@^y VJmLJ{FGGWq@e@eBIe@Ei@?q@Bk@Hs@Le@Rk@gCuIkJcZsDwLd@g@Oe@o@mB{BgHQYq@qBQYOMSM GBUBGCYc@E_@H]DWJST?JFFHBDNBJ?LED?LBv@WfAc@@EDGNK|@e@hAa@`Bk@b@OEk@Go@IeACoA@ a@PyB`@yDDc@e@K{Bi@oA_@w@]m@_@]QkBoAwC{BmAeAo@s@uAoB_AaBmAwCa@mAo@iCgAwFg@iD q@}G[uEU_GBuP@cICmA?eI?qCB{FBkCI}BOyCMiAGcAC{AN{YFqD^}FR}CNu@JcAHu@b@_E`@}DVsB^mBTsAQ KkCmAg@[YQOIOvAi@[m@e@s@g@GKCKAEJIn@g@GYGIc@ScBoAf@{A`@uAlBfAG`@";
	}
	
	public String getPolyline() {
		return polyline;
	}
	
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	
	public static long getNow() {
		return now;
	}
	
	public void setNow(long now) {
		Robot.now = now;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LatLng> getDifferentsRoutes() {
		return differentsRoutes;
	}

	public void setDifferentsRoutes(List<LatLng> differentsRoutes) {
		this.differentsRoutes = differentsRoutes;
	}

	public List<LatLng> getCurrentRoute() {
		return currentRoute;
	}

	public void setCurrentRoute(List<LatLng> currentRoute) {
		this.currentRoute = currentRoute;
	}

	public long getDistanceWalked() {
		return distanceWalked;
	}

	public void setDistanceWalked(long distanceWalked) {
		this.distanceWalked = distanceWalked;
	}

	public Long getAccumulatedlevelReport() {
		return accumulatedlevelReport;
	}

	public void setAccumulatedlevelReport(Long accumulatedlevelReport) {
		this.accumulatedlevelReport = accumulatedlevelReport;
	}

	public double getLang() {
		return lat;
	}

	public void setLang(double p1) {
		this.lat = p1;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double p2) {
		this.lng = p2;
	}
	
	public static long getVelocity() {
		return velocity;
	}

	public static void setVelocity(long velocity) {
		Robot.velocity = velocity;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
