package com.app.controls;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class StylesControl {
	static int  x;
	static String []styles = {"/com/app/styles/ThemeOne.css","/com/app/styles/ThemeTwo.css"};
	
	public static void getIndex() {
		JSONParser p = new JSONParser();
		try {
			JSONObject obj = (JSONObject)p.parse(new FileReader("config.json"));
			 Long value= (Long) obj.get("indexStyle");
			 x=value.intValue();
			 System.out.println(x);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void saveIndex() {
		JSONObject obj=new JSONObject();
		obj.put("indexStyle", x);
		try(FileWriter r = new FileWriter("config.json",false)){
			r.write(obj.toString());
			r.flush();
			r.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String changeStyle() {
		x=x==styles.length-1?0:x+1;
		return styles[x];
	}
	public static String getStyle() {
		return styles[x];
	}
	
}
