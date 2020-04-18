package DAO;

import java.util.ArrayList;

import VO.Usuario;

public class DAOUsuario {
	ArrayList<Usuario> datos = new ArrayList<Usuario>();
	
	public boolean addUser(Usuario us) {
		if (us.getNickname() != null && us.getContrase()!=null) {
			datos.add(us);
			return true;
		}
		return false;
	}
	
	public Usuario getUser(String nk, String pass) {
		Usuario temp = null;		
		for (int i = 0; i < datos.size(); i++) {
			if (datos.get(i).getNickname().equals(nk) && datos.get(i).getContrase().equals(pass)) {
				temp = datos.get(i);
			}
		}
		return temp;
	}

	public DAOUsuario() {
		Usuario defa = new Usuario("Borta", "marco", "6baad6f126fa53233f5120dd32225d4a9eeaea26dce58789f0b3b6efcdb0dadb", "Adm");
		datos.add(defa);
	}
	
	
	
}
