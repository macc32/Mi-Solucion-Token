package Controlador;

import Vista.Vista;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.json.JSONObject;

public class Controlador {

	public static void main(String[] args) {

		Vista vs = new Vista();
		SvControl sv = new SvControl();
		int op = 0;

		while (op != 3) {
			op = vs.menu();
			switch (op) {
				case 1:
					try {
						String nick = vs.getString("Ingrese nickname");
						String pass = vs.getString("Ingrese password");
						pass = getSHA256(pass);
						String cadena = "{\"Nickname\":" + nick + ",\"Password\":" + pass + "}";
						JSONObject obj = new JSONObject(cadena);
						String token = sv.Login(obj);
						if (token != null) {
							System.out.println(token);
							int op1 = 0;
							while(op1!=2) {
								op = vs.menuInter();
								if (op==1) {
									System.out.println(sv.saludar(token));
								}
							}
						}
						System.out.println("Error");
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					boolean stat = false;
					while (stat != true) {
						String nickname = vs.getString("Ingrese nickname");
						String nombre = vs.getString("Ingrese nombre");
						String pass = vs.getString("Ingrese Contraseña");
						pass = getSHA256(pass);
						String cargo = vs.getString("Ingrese cargo: Adm, Cli");
						try {
							String cadena = "{\"Nickname\":" + nickname + ",\"nombre\":" + nombre + ",\"Password\":" + pass
									+ ",\"cargo\":" + cargo + "}";
							JSONObject job = new JSONObject(cadena);
							JSONObject rep = sv.registro(job);
							if (rep.get("Status").toString().equals("Hecho")) {
								stat = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;

			}
		}
	}

	public static String getSHA256(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}

}
