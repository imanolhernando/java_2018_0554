package com.ipartek.formacion.modelo.pojos;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class Videos {

	// Atributos
	private long id;

	@NotEmpty
	@Size(min = 5, max = 150)
	private String nombre;

	@NotEmpty
	@Size(min = 11, max = 11)
	private String codigo;

	private Usuario usuario;

	// Constructores

	public Videos() {
			super();
			this.id = -1;
			this.nombre = "";
			this.codigo = "";
			this.usuario = new Usuario();
		}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Videos(String nombre) {
			this();
			this.nombre = nombre;
		}

	// Getters y Setters

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

	@Override
	public String toString() {
		return "Videos [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", usuario=" + usuario + "]";
	}

}