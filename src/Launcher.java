
public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CLI cli = new CLI();
		try{
			cli.task();
		}catch(Exception e){
			System.out.println(e);
		}

	}

}
