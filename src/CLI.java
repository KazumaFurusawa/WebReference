import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import Web.Web;
import Web.EdgeList.Edge.Edge;
import Web.FileReadWrite.FileReadWrite;


public class CLI {
	Web web = new Web();

	public CLI(){
	}
	
	public void task() throws IOException{
        System.out.println("\n"+
        		"What's would you like to do? \n"+
        		" 1. Read web file or write web file \n" +
        		" 2. Update web file \n" +
        		" 3. Report of the web \n" +
        		" 4. Quit");
        BufferedReader input =
            new BufferedReader (new InputStreamReader (System.in));
		String str = input.readLine( );			
		int switcher = checkNumber(str);
		switch (switcher) {
			case 1:
				readWriteWeb();
				task();
			case 2:
				updateWeb();
				task();
			case 3:
				report();
				task();
			case 4:
				System.out.println("See you again");
				System.exit(0);
			default:
				System.out.println("It is illegal input, try again");
				task();
		}
	}
	
	public void readWriteWeb() throws IOException{
		FileReadWrite fileRW = web.toFileReadWrite();
        System.out.println("\n" +
        		"Please select one from the list below \n"+
        		" 1. Read a web from input.txt \n" +
        		" 2. Write a web to output.txt \n" +
        		" 3. Go back to last menu");
        BufferedReader input =
            new BufferedReader (new InputStreamReader (System.in));
		String str = input.readLine( );
		int switcher = checkNumber(str);
		switch (switcher) {
			case 1:
				System.out.println("Read a web from input.txt");
				web = fileRW.readWeb("input.txt");
				task();
			case 2:
				System.out.println("Write a web to output.txt");
				fileRW.writeWeb("output.txt");
				task();
			case 3:
				task();
			default:
				System.out.println("It is illegal input, try again");
				this.readWriteWeb();
		}
		
	}
	
	public void updateWeb() throws IOException{
		String personName= new String();
		String target = new String();
		int level =-1;
		Edge edge = new Edge(personName, level);
        System.out.println("\n" +
        		"Please select one from the list below \n"+
        		" 1. Add person \n" +
        		" 2. Add reference \n" +
        		" 3. Delete person \n" +
        		" 4. Delete Reference \n" +
        		" 5. Change Reference \n" +
        		" 6. Go back to last menu");
        BufferedReader input =
            new BufferedReader (new InputStreamReader (System.in));
		String str = input.readLine( );			
		int switcher = checkNumber(str);
		switch (switcher) {
			case 1:
				System.out.println("please input personName");
				str = input.readLine();

				if(web.addPerson(str)){
					System.out.println("Success to addPerson: "+str );
					updateWeb();
				}
				else{
					System.out.println("Error try again");
					updateWeb();
				}
			case 2:
				System.out.println("please input personName");
				personName = input.readLine();
				System.out.println("please input given personName");
				target = input.readLine();
				
				while(!(level ==1 || level ==-3 || level ==3)){
					System.out.println("please input referenc \n" +
							" 0-> Recommend 1 point\n"+
							" 1-> HighRecoomend 3 points \n"+
							" 2-> notRecommend -3 points \n");
					
					level = convertNum(checkNumber(input.readLine()));
				}
				edge = new Edge(target,level);
			
				if(web.addReference(personName, edge)){
					System.out.println("Success to addReference: "
							+personName + " " + edge);
				}
				else{
					System.out.println("Error try again");
				}
				updateWeb();
			case 3:
				System.out.println("please input personName");
				personName = input.readLine();
				if(web.deletePerson(personName)){
					System.out.println("Success to deletePerson: "+personName);
				}
				else{
					System.out.println("Couldn't fine the personf from the web");
				}
				updateWeb();
			case 4:
				System.out.println("please input personName");
				personName = input.readLine();
				System.out.println("please input given personName");
				target = input.readLine();
				if(web.deleteReference(personName, target)){
					System.out.println("Success to deleteReference: " + personName +" "
							+ target);
				}
				else{
					System.out.println("Error try again");
				}
				updateWeb();
			case 5:
				System.out.println("please input personName");
				personName = input.readLine();
				System.out.println("please input given personName");
				target = input.readLine();
				while(!(level ==1 || level ==-3 || level ==3)){
				System.out.println("please input referenc \n" +
						" 0-> Recommend 1 point\n"+
						" 1-> HighRecoomend 3 points \n"+
						" 2-> notRecommend -3 points \n");
				level = convertNum(checkNumber(input.readLine()));
				}
				edge = new Edge(target,level);
			
				if(web.changeReference(personName, edge)){
					System.out.println("Success to changeReference: "
							+personName + " " + edge);
				}
				else{
					System.out.println("Error try again");
				}
				updateWeb();
			case 6:
				task();
			default:
				System.out.println("It is illegal input, try again");
				updateWeb();
		}
	}
	
	public void report() throws IOException{
		String str = new String();
		String personName= new String();
		int display = -1;		
		BufferedReader input =
	            new BufferedReader (new InputStreamReader (System.in));
		while(!(str.equals("1") || str.equals("2"))){
			System.out.println("\n Please select one from the list below \n" +
					" 1. Output to display \n" +
					" 2. Write to a file");
			str = input.readLine( );
			System.out.println(str);
		}
		display = checkNumber(str);
        System.out.println("\n" +
        		"Please select one from the list below \n"+
        		" 1. Output complete report \n"+
        		" 2. Output person's report \n"+
        		" 3. Output all highly recommend persons \n" +
        		" 4. Output all reference of a person \n"+
        		" 5. Output all reference by a person \n" +
        		" 6. Ouput topX \n" +
        		" 7. Output Stat \n" +
        		" 8  Go back to last menu");
        str = input.readLine();
		int switcher = checkNumber(str);
		switch (switcher) {
			case 1:
				if(web.getAdjHash().isEmpty()){
					System.out.println("Current web is empty");
				}
				else{
					reportDW(display,web.toString());
					System.out.println();
				}
				report();
			case 2:
				System.out.println("Please input person's name");
				personName = input.readLine();
				if(web.getAdjHash().containsKey(personName)){
					reportDW(display, web.toGetReport().getPersonReportOf(personName));
				}
				else{
					System.out.println("Could n't fine the person in the web");
				}
				report();
			case 3:
				if(web.getAdjHash().isEmpty()){
					System.out.println("Current Web is empty");
				}
				else{
					reportDW(display,web.toGetReport().getAllHighlyRecommend());
				}
				report();
			case 4:
				System.out.println("Please input perons's name");
				personName = input.readLine();
				if(web.getAdjHash().containsKey(personName)){
					reportDW(display, web.toGetReport().getAllReferenceOf(personName));
				}
				else{
					System.out.println("Couldn't fine the person in the web");
				}
				report();
			case 5:
				System.out.println("Please input persons' name");
				personName = input.readLine();
				if(web.getAdjHash().containsKey(personName)){
					reportDW(display, web.toGetReport().getAllReferenceBy(personName));
				}
				else{
					System.out.println("Couldn't fine the person in the web");
				}
					report();
			case 6:
				System.out.println("Please input top X");
				int X = checkNumber(input.readLine());
				if(!(web.getAdjHash().isEmpty())){
					if(X>0){
						reportDW(display, web.toGetReport().getTopX(X));
					}
					else{
						System.out.println("X must be larger than 0");
					}
				}
				else{
					System.out.println("Current web is empty");
				}
				report();
			case 7:
				if(web.getAdjHash().isEmpty()){
					System.out.println("Current web is empty");
				}
				else{
					reportDW(display, web.toGetReport().getStat());
				}
				report();
			case 8:
				task();
			default:
				System.out.println("It is illegal input, try again");
				report();
		}
	
	}
	
	private boolean reportDW(int display, String str){
		try{
			if(display ==1){
				System.out.println(str);
				return true;
			}
			else{
				System.out.println("Pleaes input the file name");
				BufferedReader input =
			            new BufferedReader (new InputStreamReader (System.in));
				String fileName = input.readLine();
				FileOutputStream fos = new FileOutputStream(fileName,true);
			    OutputStreamWriter osw = new OutputStreamWriter(fos);
				PrintWriter pw = new PrintWriter(osw);
				pw.println(str);
				System.out.println("Success to output data to the file");
				pw.close();
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("Error");
			System.out.println(e );
			return false;
		}
	}
	private int checkNumber(String val) {
		try{
	        return Integer.parseInt(val);
	    }catch (NumberFormatException nfex) {
	        System.out.println("It isn't number");
	    	return -1;
	    }
	}
	
	private int convertNum(int val){
		switch(val){
		case 0:
			return 1;
		case 1:
			return 3;
		case 2:
			return -3;
		default:
			return -1;
		}
	}


	
}
