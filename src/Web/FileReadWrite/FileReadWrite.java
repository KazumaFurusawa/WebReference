package Web.FileReadWrite;
import Web.Web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map.Entry;
import Web.EdgeList.EdgeList;
import Web.EdgeList.Edge.Edge;


public class FileReadWrite {
	Web web = new Web();
	String logFile = ".\\logFile.txt";
	//
	public  FileReadWrite(Web web){
		this.web = web;
	}
	

	
	public Web readWeb(String readFileName){
		Web webOld = web;
		if(readFile(readFileName) == true){
			System.out.println("Success to read");
			return web;
		}
		else{
			System.out.println("UnSuccess to read");
			return webOld;
		}
	}
	
	public boolean writeWeb(String writeFileName){
		try {
			if(writeFile(writeFileName) == true){
				System.out.println("Success to write");
				return true;
			}
			else{
				System.out.println("UnSuccess to write");
				return false;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error to write the file");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean readFile(String filePath){
		try {
			File input = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(input));
			String line = "";
			while((line = br.readLine())!= null){
				this.select(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return false;
		} catch (IOException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	private boolean select(String inputLine) throws FileNotFoundException{
		File input = new File(logFile);
		FileOutputStream fos = new FileOutputStream(input,true);
	    OutputStreamWriter osw = new OutputStreamWriter(fos);
		PrintWriter pw = new PrintWriter(osw);
		try{
			//read input file.
			String[] str = inputLine.split(" ");
			String operand = str[0];
			String person = new String();
			String targetPerson = new String();
			int recommend = 0;
			int level = 0;
			Edge edge = new Edge(targetPerson,level);
			
			if(operand.equals("addPerson") || operand.equals("deletePerson")){
				person = str[1];
			}
			else if(operand.equals("deleteReference")){
				person = str[1];
				targetPerson = str[2];
			}
			else if(operand.equals("addReference") || operand.equals("changeReference")){
				person = str[1];
				targetPerson = str[2];
				recommend = new Integer(str[3]);
				switch(recommend){
					case 0: level = 1; break;
					case 1: level = 3; break;
					case 2: level = -3; break;
					default: 
						pw.println(Calendar.getInstance().getTime());
						pw.println("ReadError: " + operand +" " +  person + " " + new Edge(targetPerson,recommend));
						pw.println("this recommendation is unknown");
						pw.close();
						return false;
				}
				edge = new Edge(targetPerson,level);
			}
			else{
				pw.println(Calendar.getInstance().getTime());
				pw.println("ReadError: " + inputLine);
				pw.close();
				return false;
			}
			
			if(operand.equals("addPerson")){
				web.addPerson(person);
				pw.println(Calendar.getInstance().getTime());
				pw.println("Read: addPerson " + person);
				pw.close();
				return true;
			}
			else if(operand.equals("addReference")){
				web.addReference(person, edge);
				pw.println(Calendar.getInstance().getTime());
				pw.println("Read: addReference " + person +" "+ edge);
				pw.close();
				return true;
			}
			else if(operand.equals("deletePerson")){
				web.deletePerson(person);
				pw.println(Calendar.getInstance().getTime());
				pw.println("Read: deletePerson " + person);
				pw.close();
				return true;	
			}
			else if(operand.equals("deleteReference")){
				web.deleteReference(person,targetPerson);
				pw.println(Calendar.getInstance().getTime());
				pw.println("Read: deleteReference " + person +" "+ edge);
				pw.close();
				return true;
				
			}
			else if(operand.equals("changeReference")){
				web.changeReference(person, edge);
				pw.println(Calendar.getInstance().getTime());
				pw.println("Read: changeReference " + person +" "+ edge);
				pw.close();
				return true;
			}
			else{
				pw.println(Calendar.getInstance().getTime());
				pw.println("ReadError: unknown operand");
				pw.close();
				return false;
			}
		}
		catch(Exception e){
			pw.println(Calendar.getInstance().getTime());
			pw.println("ReadError: "+e);
			pw.close();
			return false;
		}
	}
	
	
	
	private boolean writeFile(String filePath) throws FileNotFoundException{
		File input = new File(logFile);
		FileOutputStream fos = new FileOutputStream(input,true);
	    OutputStreamWriter osw = new OutputStreamWriter(fos);
		PrintWriter pw2 = new PrintWriter(osw);
		try{
			File file = new File(filePath);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			//write addPerson to the file.
			Iterator<String> personIterator = web.getAdjHash().keySet().iterator();
			while(personIterator.hasNext()){
				String person = personIterator.next();
				pw.println("addPerson "+ person);
				pw2.println(Calendar.getInstance().getTime());
				pw2.println("Output: addPerson " + person);
			}
			
			//write addReference to the file
			Iterator<Entry<String, EdgeList>> reftIerator = web.getAdjHash().entrySet().iterator();
			while(reftIerator.hasNext()){
				Entry<String,EdgeList>  entry = reftIerator.next();
				String person = entry.getKey();
				EdgeList list = entry.getValue();
				Iterator<Edge> iterator = list.iterator();
				
				while(iterator.hasNext()){
					Edge edge = iterator.next();
					String targetPerson = edge.getPersonName();
					int level = edge.getLevel();
					int recommend = 0;
					switch(level){
						case 1: recommend = 0; break;
						case 3: recommend = 1; break;
						case -3: recommend = 2; break;
						default: return false;
					}	
					pw.println("addReference "+ person +" " + targetPerson + " " + recommend);
					pw2.println(Calendar.getInstance().getTime());
					pw2.println("Output: addReference "+ person +" " + targetPerson + " " + recommend);
				}
			}
			
			//pw.write(adjHash.toString());
			pw.close();
			pw2.close();
		}catch(Exception e){
			pw2.println(Calendar.getInstance().getTime());
			pw2.println(e);
			pw2.close();
			return false;
		}
		return true;
	}
	
}
