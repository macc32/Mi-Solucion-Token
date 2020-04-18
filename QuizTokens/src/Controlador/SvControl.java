package Controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.DAOUsuario;
import VO.Usuario;

public class SvControl {

	private static final String key = "d79843f3186d1d0b5782cf8b01f8a63ae620341e46d4bb4a4158badee5d83645";

	DAOUsuario dao = new DAOUsuario();

	public JSONObject registro(JSONObject user) {
		Usuario temp = new Usuario();
		JSONObject resp = null;
		try {
			temp.setNickname(user.get("Nickname").toString());
			temp.setNombre(user.get("nombre").toString());
			String pass = user.get("Password").toString();
			pass = getSHA256(pass);
			temp.setContrase(pass);
			temp.setCargo(user.get("cargo").toString());
			if (dao.addUser(temp)==true) {
				String cadena = "{\"Status\":\"Hecho\"}";
				resp = new JSONObject(cadena);
				return resp;
			}
			String cadena = "{\"Status\":\"Hecho\"}";
			resp = new JSONObject(cadena);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;

	}

	public String Login(JSONObject user) {				
		String mytoken = "";
		try {
			String nik = user.get("Nickname").toString();
			String pass = user.get("Password").toString();
			pass = getSHA256(pass);			
			Usuario temp = dao.getUser(nik, pass);
			if (temp != null) {
				// se construye el header
				String h = "{\"Alg\":SHA256,\"type\":MWT}";
				JSONObject header = new JSONObject(h);				

				// se constuye el payload
				Date dt = new Date();
				int aux = (int) (dt.getTime() + 900000);
				String p = "{\"Nickname\":"+temp.getNickname()+",\"cargo\":"+temp.getCargo()+
						",\"nombre\":"+temp.getNombre()+",\"Expira\":"+aux+"}";
				JSONObject paylod = new JSONObject(p);				

				// se construye la firma
				String head = header.toString();
				String pay = paylod.toString();
				head = Base64.getEncoder().encodeToString(head.getBytes());
				pay = Base64.getEncoder().encodeToString(pay.getBytes());

				String cadena = head + pay + key;
				String firma = getSHA256(cadena);
				firma = Base64.getEncoder().encodeToString(firma.getBytes());
				// se arma el token
				mytoken = head + "." + pay + "." + firma;
			}else {
				mytoken = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mytoken;
	}
	
	public String saludar(String tok) {
		String[] token = tok.split("\\.");		
		if (comprobarToken(token)) {
			byte[] temp = Base64.getDecoder().decode(token[1]);
			String cadena = new String(temp); 
			try {
				JSONObject obj = new JSONObject(cadena);
				String nombre = obj.get("nombre").toString();
				return "Hola "+nombre+" :)";
			} catch (Exception e) {			
				e.printStackTrace();
			}
		}
		return "Error";
	}
	
	private boolean comprobarToken(String[] tok) {		
		byte[] temp = Base64.getDecoder().decode(tok[1]);
		String cadena = new String(temp); 
		try {
			Date dt = new Date();
			JSONObject obj = new JSONObject(cadena);
			int tin = (int) dt.getTime()*-1;
			int exp = Integer.parseInt(obj.get("Expira").toString())*-1;
			if (exp>tin) {				
				return false;
			}
			String aux = tok[0] + tok[1] + key;
			String firma = getSHA256(aux);
			firma = Base64.getEncoder().encodeToString(firma.getBytes());
			if(firma.equals(tok[2])) {				
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
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
