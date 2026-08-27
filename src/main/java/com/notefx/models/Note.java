package com.notefx.models;

import java.time.LocalDateTime;

public class Note {
	private Long id;
	private String titulo;
	private String contenido;
	private LocalDateTime fechaCreacion;
	private LocalDateTime ultimaModificacion;

	public Note() {

	}

	public Note(Long id, String titulo, String contenido, LocalDateTime fechaCreacion,
			LocalDateTime ultimaModificacion) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaCreacion = fechaCreacion;
		this.ultimaModificacion = ultimaModificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(LocalDateTime ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", fechaCreacion=" + fechaCreacion
				+ ", ultimaModificacion=" + ultimaModificacion + ", getId()=" + getId() + ", getTitulo()=" + getTitulo()
				+ ", getContenido()=" + getContenido() + ", getFechaCreacion()=" + getFechaCreacion()
				+ ", getUltimaModificacion()=" + getUltimaModificacion() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
