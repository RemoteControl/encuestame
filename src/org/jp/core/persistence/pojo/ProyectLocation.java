package org.jp.core.persistence.pojo;

// Generated 27-abr-2009 18:04:46 by Hibernate Tools 3.2.2.GA

/**
 * ProyectLocation generated by hbm2java
 */
public class ProyectLocation implements java.io.Serializable {

	private ProyectLocationId id;
	private Proyect proyect;
	private CatLocation catLocation;
	private int idEstado;

	public ProyectLocation() {
	}

	public ProyectLocation(ProyectLocationId id, Proyect proyect,
			CatLocation catLocation, int idEstado) {
		this.id = id;
		this.proyect = proyect;
		this.catLocation = catLocation;
		this.idEstado = idEstado;
	}

	public ProyectLocationId getId() {
		return this.id;
	}

	public void setId(ProyectLocationId id) {
		this.id = id;
	}

	public Proyect getProyect() {
		return this.proyect;
	}

	public void setProyect(Proyect proyect) {
		this.proyect = proyect;
	}

	public CatLocation getCatLocation() {
		return this.catLocation;
	}

	public void setCatLocation(CatLocation catLocation) {
		this.catLocation = catLocation;
	}

	public int getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

}
