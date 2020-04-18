package Vista;
import java.util.Scanner;;
public class Vista {
	
	int op = 0;
	Scanner sc = new Scanner(System.in);
	
	public int menu() {		
		System.out.println("1. LogIn \n 2. Registro \n 3. Salir");
		op = sc.nextInt();
		return op;	
	}
	
	public int menuInter() {		
		System.out.println("1. Saludar \n 2. Salir");
		op = sc.nextInt();
		return op;	
	}
	
	public String getString(String ms) {
		System.out.println(ms);
		String cade = sc.next();
		return cade;
	}
	
	public int getInt(String ms) {
		System.out.println(ms);
		int nu = sc.nextInt();
		return nu;
	}
	
}
