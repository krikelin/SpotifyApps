package com.krikelin.celsius.aquasphere;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

public class AquaParser {
	public static String readString(String file) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		try{
			String s ="";
			while( ( s = br.readLine() ) != null){
				sb.append(s).append("\n");
			}
		}catch(Exception e){
			
		}
		br.close();
		return sb.toString();
	}
	public class Track{
		public String title;
		public String author;
		public String love;
	}
	public static void main(String[] args){
		try {
			AquaParser parser  = new AquaParser(readString(args[0]));
			// Sample objects
			Track c = parser.new Track();	
			c.title = "Sandstorm";
			c.author = "Darude";
			Track d = parser.new Track();	
			d.title = "Aquasphere";
			d.author = "Dr. Sounds";
			
		
	//		parser.process(args[0],c,d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private String template;
	public AquaParser(String string){
		setTemplate(string);
	}
	public String capitalize(String c){
		return c.substring(0,1).toUpperCase()+c.substring(1,c.length()-2);
	}
	/***
	 * 
	 * @param str
	 * @param obj
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Object[] parseObject(String str, Object obj) throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		if(!str.contains(".")){
			return new Object[]{obj.toString()};
		}
		String[] c = str.split("\\.");
		Object currentObj = obj;
		for(int i=0; i < c.length; i++){
			String member = c[i];
			try{
				Method meth = currentObj.getClass().getMethod("get"+capitalize(member));
				currentObj = meth.invoke(currentObj);
			}catch(NoSuchMethodException e){
				
			} catch (Exception e){
				Field f = currentObj.getClass().getDeclaredField(member);
				currentObj = f.get(currentObj);
			}
			
		}
		
		// Check if current object is an arraay
		if(currentObj.getClass().isArray()){
			Object[] objects = (Object[])Array.get(currentObj, 0);
			return objects;
		}
		return new Object[]{currentObj};
	}
	private String start_tag = "{{";
	private String end_tag = "}}";
	private String[] getSegments(String input){
		int curpos = 0;
		boolean inside=false;
		ArrayList<String> strings = new ArrayList<String>();
		String prevelant = input.substring(0,curpos);
		while(input.indexOf(start_tag,curpos) != -1){
			curpos = input.indexOf(start_tag, curpos)+2; // Position of next opening tag contents
			int endtag = input.indexOf(end_tag,curpos); // position of closing of the opening tag
			String  preprocessor = input.substring(curpos,endtag - curpos); // Opening tag contents
			
			// get position of next end tag or end of document
			curpos = input.indexOf(start_tag, endtag-curpos); 
			if(curpos == 0){
				curpos = input.length();
			}
			String markup = input.substring(endtag+end_tag.length(), curpos);
			
			strings.add(preprocessor.trim());
			strings.add(markup);
		/*	int startTag = input.indexOf(start_tag + start_tag.length(),curpos); // Distance from current to start tag
			int endTag = input.indexOf(end_tag + end_tag.length(),startTag ); // distance between start tag and end tag
			
			
			
			String  content = input.substring(curpos + end_tag.length(),endTag );
			String tag = input.substring(startTag+start_tag.length(),endTag);
			curpos = input.indexOf(endTag);
			
			strings.add(content);
			strings.add(tag);*/
						
		}
		return (String[])strings.toArray();
	}
	private int level = 0;
	private void processSegments(StringWriter localWriter, int i, Hashtable<String, Object> objects, String[] segments, int level) throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	
		
		
		if(i % 2 == 0){
			String[] tokens = segments[i].split(" ");
			
			// if token is length > then we're dealing with some control statements
			if(tokens[0].equals("end")){
				this.level-=1;
				return;
			}
			if(tokens.length > 0){
				if(tokens[0].equals("for")){
					if(tokens.length < 4 || tokens[2].equals("in")){
						throw new IllegalArgumentException();
					}
					// Parse for loop
					Object obj = objects.get(tokens[3]);
					// As we expect lists or array, we do following
					if(obj.getClass().isArray()){
						Object[] array = parseObject(tokens[3], obj);
						for(Object obje : array){
							String template = segments[i+1];
							Hashtable<String, Object> childObjects = new Hashtable<String, Object>();
							childObjects.put(tokens[1],obje);
							objects.put(tokens[1],obje);
							
							// find the endfor declaration
							while(true){
								
								
								 processSegments(localWriter,i,objects,segments,level+1);
								
								 if(level == this.level){
									break;
								 }
								 i++;
								
							}
							
							objects.remove(tokens[1]);
							this.level--;
							
							continue;
						}
					}
					// Now loop inside the object
					
					
				}
			}
		}else{
			localWriter.append(segments[i]);
		
		}
	}
	StringBuilder outputWriter = new StringBuilder();
	public String process(String inputStr,Hashtable<String, Object> objects) throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		String[] segments = getSegments(inputStr);
		outputWriter = new StringBuilder();
		// the string the segment will be substituted to
		int deep_level = 0;
		for(int i=0; i < segments.length; i++){

			
		}
		return outputWriter.toString();
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
