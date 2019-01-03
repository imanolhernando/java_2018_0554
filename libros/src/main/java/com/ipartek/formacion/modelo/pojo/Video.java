package com.ipartek.formacion.modelo.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Video {

	// Atributos
	
	private long id;
	
	@NotEmpty
	@Size(min=5, max=100)
	private String nombre;
	
	@NotEmpty
	@Size(min=11, max=11)
	private String codigo;

	// Constructores

	public Video() {
		super();
		this.id = -1;
		this.nombre = "";
		this.codigo = "";
	}
	
	public Video(String nombre) {
		this();
		this.nombre = nombre;
	}
	

	// Getters y Setters

	public Video(Long id, String nombre, String codigo) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	// Otros metodos => toString

	@Override
	public String toString() {
		return "Video [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + "]";
	}

}
