package com.ugurhmz.common.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Roles")
public class Role {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=50, nullable = false, unique = true)
	private String name;
	
	@Column(length=200, nullable= false)
	private String description;
	
	

	
	
	
	// CONSTRUCTOR
	public Role() {
		
	}
	
	public Role(Integer id) {
		this.id = id;
	}
	
	public Role(String name) {
		
		this.name = name;
	}

	public Role(String name, String description) {
		
		this.name = name;
		this.description = description;
	}
	
	// GETTE & SETTER
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	// hashCode & equals & toString
	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return  name;
	}
	
	
}
