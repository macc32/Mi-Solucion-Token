package VO;

public class Usuario {
	
	private String nickname;
	private String nombre;
	private String contrase;
	private String cargo;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getContrase() {
		return contrase;
	}

	public void setContrase(String contrase) {
		this.contrase = contrase;
	}

	public Usuario(String nickname, String nombre, String contrase, String cargo) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.contrase = contrase;
		this.cargo = cargo;
	}

	public Usuario() {
		
	}
	
	
	
}
