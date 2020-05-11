package com.seatcode.application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.seatcode.utils.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.internal.ratelimiter.PolylineEncoding;
import com.google.maps.model.EncodedPolyline;

import com.seatcode.model.Robot;

@SpringBootApplication
@EnableScheduling
public class SeatCodeApplication {
	
	static Properties appProps = new Properties();
	static InputStream inputStream;
	static org.slf4j.Logger logger = LoggerFactory.getLogger(SeatCodeApplication.class);
	public EncodedPolyline encodedPolyline;
	static ObjectMapper objectMapper = new ObjectMapper();
	static JSONObject jsonfile=new JSONObject();
	static JSONArray latLng = new JSONArray();
	public static PolylineEncoding ple=new PolylineEncoding();
	public static double disTotal=0;
	public static double disProva=0;
	
	@Autowired
	public static Robot robot=new Robot();
	
	public static long now;
	public static long robotLevel;
	public static List<LatLng> differentsRoutes;
	public static int checkPerPeriod=9; 
	public static int randomCheck=200; 
	public static Long setZero=0L;
	public static double finalDistance;
	public static double posIniToBuckingham; //Bonus 1
	public static double posIniToTemple; //Bonus 1
	public static double latitude=120.3;
	public static double longitude=-0.2;
	
	//CONSTANTS VALUES. ENUM
	final static int scheduledPM=100000; //ENUM. CONSTANT 
	final static int scheduledReport=900000; //ENUM. CONSTANT 
	
	//Construct
	public SeatCodeApplication() {
		latLng.clear();
	}
	
	//Main Method
	public static void main(String[] args) throws Exception {
		propertiesField();		 
		SpringApplication.run(SeatCodeApplication.class, args);
		runNecessaryMethods();
	}
	
	
	//Functions...
	public static void runNecessaryMethods() {
		logger.info(appProps.getProperty("textStart"));
		//Just start, we decode the polyLine and feed the robot.
		//System.out.println(distance(51.504870000000004,-0.2153300000000002,51.50475,-0.21571,appProps.getProperty("m")));
		//System.out.println(distance(51.50475,-0.21571,51.50463,-0.21565,appProps.getProperty("m")));
		//System.out.println(distance(51.50463,-0.21565,51.50423,-0.21586,appProps.getProperty("m")));
		//System.out.println(distance(51.50423,-0.21586,51.50421,-0.21591,appProps.getProperty("m")));
		
		//System.out.println(distance(51.504870000000004,-0.2153300000000002,51.50608,-0.21094,appProps.getProperty("m")));
		decodePolyline();
		getTotalDistance();//Get The distance between first and last points.
		getDistanceToBuckingham(); //Bonus 1
		getDistanceToTemple(); //Bonus 1
		checkDistanceBetweenPoints();
		checkCurrentPosition();
	}
	
	public static void getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //long
        now=timestamp.getTime();
        robot.setNow(now);
    }

	public static void checkDistanceBetweenPoints() {
		double dt=0;
		double end=0;
		end=differentsRoutes.size();
		for(int i=0;i<=90;i++) {
			//System.out.println(distance((differentsRoutes.get(i).lat),(differentsRoutes.get(i).lng),(differentsRoutes.get(i+1).lat),(differentsRoutes.get(i+1).lng),"M"));
			//distance((differentsRoutes.get(i).lat),(differentsRoutes.get(i).lng),(differentsRoutes.get(i+1).lat),(differentsRoutes.get(i+1).lng),"M");
		//if()
			//System.out.println("Total Recorregut:"+dt+differentsRoutes.get(i).lat);
			dt+=distance((differentsRoutes.get(i).lat),(differentsRoutes.get(i).lng),(differentsRoutes.get(i+1).lat),(differentsRoutes.get(i+1).lng),"M");
			//if(dt<700) {
				//System.out.println("Total Recorregut:"+dt+" Posició Lat: "+differentsRoutes.get(i).lat);
				
				//robot.setLang(differentsRoutes.get(iTest).lat);
				//robot.setLng(differentsRoutes.get(iTest).lng);
			//}
		}
		//System.out.println("Total Recorregut:"+dt);
	}
	public static void decodePolyline() {
		//First: Get List of Locations Google for the specified polyline
		differentsRoutes=ple.decode(robot.getPolyline());
		
		//Feed all the routes to the Robot.
		robot.setDifferentsRoutes(differentsRoutes);
	}
	
	public void check100meters() {
		if(robot.getDistanceWalked()==100) {
			robot.setLang(51.50421);
			robot.setLng(-0.21591);
		}
			
	}
	
	public static void propertiesField() throws FileNotFoundException, IOException {
		//Load the properties field
				String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
				String appConfigPath = rootPath + "application.properties"; 
				appProps.load(new FileInputStream(appConfigPath));
				
	}
	
	public static void createJson() throws JSONException, Exception {
		//The first time we run the application, we create the object
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //long
	     now=timestamp.getTime();
	     robot.setNow(now);
	     
	     //Fill the Array to JSON File
	     latLng.add(appProps.getProperty("latJSON")+robot.getLang());
	     latLng.add(appProps.getProperty("lonJSON")+robot.getLng());
	    
	     jsonfile.put("timestamp", robot.now);
		 jsonfile.put("location", latLng);
				 
		 jsonfile.put("level", robot.level);
		 jsonfile.put("source", robot.name);
			
		 FileWriter file = new FileWriter(appProps.getProperty("fileOutput"));
		 file.write(jsonfile.toJSONString());
		 file.close();
	}
	
	
	@Scheduled(fixedRate = scheduledPM) // 1 Minute 40 seconds
	public void readLevel() {
		//READ LEVEL
		Random level=new Random();
		int actualLevel=level.nextInt(randomCheck);
			
		robot.accumulatedlevelReport+=actualLevel;
		robot.distanceWalked+=100;
		
		if(robot.distanceWalked>=101) {
			checkCurrentPosition();
		}
		check100meters();
		
		//Check Bonus1. The Robot is about to arrive to Buckingham Palace.
		if((posIniToBuckingham-robot.distanceWalked)<100)robot.setName("Buckingham Palace");
		else robot.setName("Robot");
		
		//Check Bonus1. The Robot has passed X meters from Buckingham.
		if(robot.distanceWalked>=(posIniToBuckingham+100))robot.setName("Robot");
		
		//Check Bonus1. The Robot is about to arrive to Temple Station.
		if((posIniToTemple-robot.distanceWalked)<100)robot.setName("Temple Station");
		else robot.setName("Robot");
				
		//Check Bonus1. The Robot has passed X meters from Temple Station.
		if(robot.distanceWalked>=(posIniToTemple+100))robot.setName("Robot");
				
		//distance
		logger.info(" "+appProps.getProperty("pm")+actualLevel+" "+appProps.getProperty("distance")+robot.distanceWalked+" "+appProps.getProperty("lat")+robot.lat+" "+appProps.getProperty("lon")+robot.lng);
	}
	

	@Scheduled(fixedRate = scheduledReport) // 15 segons
	public void createJsonAfterOne() throws IOException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //long
	    now=timestamp.getTime();
	    robot.setNow(now);
		
		robotLevel=(robot.accumulatedlevelReport)/checkPerPeriod;
		getAverageLevelString();
		
		//Fill the Array to JSON File
	     latLng.add(appProps.getProperty("latJSON")+robot.getLang());
	     latLng.add(appProps.getProperty("lonJSON")+robot.getLng());
	    		
		 jsonfile.put(appProps.getProperty("timestamp"), robot.now);
		 jsonfile.put(appProps.getProperty("location"), latLng);
		 jsonfile.put(appProps.getProperty("level"), robot.level);
		 jsonfile.put(appProps.getProperty("source"), robot.name);
					 
		 //We try to write the JSON File, else we catch the error.
		 try {
		 FileWriter file = new FileWriter(appProps.getProperty("fileOutput"));
		 file.write(jsonfile.toJSONString());
	
		 file.close();
	
		 robot.setAccumulatedlevelReport(setZero);// when we get the report, the period accumulated is 0
		 logger.info(appProps.getProperty("jsonCreated"));
		 
		 latLng.clear();
		 
		 }catch(IOException e) {
				 e.printStackTrace();
				 logger.error(appProps.getProperty("errorJsonCreated")+e);
		 }
	}
	
	public static void getTotalDistance() {
		finalDistance=distance(Double.parseDouble(appProps.getProperty("latIni")),Double.parseDouble(appProps.getProperty("lonIni")), Double.parseDouble(appProps.getProperty("finalLat")), Double.parseDouble(appProps.getProperty("finalLon")),appProps.getProperty("m"));
	}
		
	public static void getDistanceToBuckingham() {
		posIniToBuckingham=distance(Double.parseDouble(appProps.getProperty("latIni")),Double.parseDouble(appProps.getProperty("lonIni")), Double.parseDouble(appProps.getProperty("buckinghampalacelat")), Double.parseDouble(appProps.getProperty("buckinghampalacelon")),appProps.getProperty("m"));
	}
	
	public static void getDistanceToTemple() {
		posIniToTemple=distance(Double.parseDouble(appProps.getProperty("latIni")),Double.parseDouble(appProps.getProperty("lonIni")), Double.parseDouble(appProps.getProperty("templestationlat")), Double.parseDouble(appProps.getProperty("templestationlon")),appProps.getProperty("m"));
	}
	
	public void getDistanceMinus100() {
	  robot.setDistanceWalked(robot.distanceWalked-100);	
	}
	
	public void getAverageLevelString() {
     if(robotLevel>=0 && robotLevel<=50) {
    	 robot.setLevel(appProps.getProperty("good"));
     }else if(robotLevel>=51 && robotLevel<=100)
     {
    	 robot.setLevel(appProps.getProperty("moderate"));
     }else if(robotLevel>=101 && robotLevel<=150)
     {
    	 robot.setLevel(appProps.getProperty("usg"));
     }else if(robotLevel>=151)
     {
    	 robot.setLevel(appProps.getProperty("unhealthy"));
     }
   }
	
	//get Distance with 2 lang and 2 long
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals(appProps.getProperty("k"))) {
				dist = dist * 1.609344;
			} else if (unit.equals(appProps.getProperty("n"))) {
				dist = dist * 0.8684;
			}else if (unit.equals(appProps.getProperty("m"))) {
				dist = dist * 1.609344 * 1000 ;
			}
			return (dist);
		}
	}
	
	//Gets the Current position of the Robot
	public static void checkCurrentPosition() {

		  int i=0; // The i first ones
		  double p1=0;
		  double p2=0;
		  double p3=0;
		  double p4=0;
		  double accumulatedDistance=0;   
		  double differentDistance=0;   
		  double prova=0;
		 
		  if(robot.distanceWalked>finalDistance) {
			  logger.info(appProps.getProperty("textEnd"));
			  System.exit(0);
		  }
		  
		  if(robot.distanceWalked==100) {
			  robot.setLang(51.50421);
			  robot.setLng(-0.21591);
		  }
		  
		  double dt=0;
		  double end=0;
		  end=differentsRoutes.size();
			
		  for(int iTest=0;iTest<=90;iTest++) {
				//System.out.println("Total Recorregut:"+dt+differentsRoutes.get(iTest).lat);
				dt+=distance((differentsRoutes.get(iTest).lat),(differentsRoutes.get(iTest).lng),(differentsRoutes.get(iTest+1).lat),(differentsRoutes.get(iTest+1).lng),"M");
				
				if(dt<robot.distanceWalked) {
					//System.out.println("Total Recorregut:"+dt+" Posició Lat: "+differentsRoutes.get(iTest).lat);
					robot.setLang(differentsRoutes.get(iTest+1).lat);
					robot.setLng(differentsRoutes.get(iTest+1).lng);
				}
		  }
			//System.out.println("Total Recorregut:"+dt);
 	}
}
