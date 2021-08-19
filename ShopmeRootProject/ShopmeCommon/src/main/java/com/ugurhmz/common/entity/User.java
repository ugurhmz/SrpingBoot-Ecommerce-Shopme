package com.ugurhmz.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name", nullable=false, length=70)
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=100)
	private String lastName;
	
	@Column(nullable=false, unique=true, length=100)
	private String email;
	
	@Column(nullable= false, length=100)
	private String password;
	
	@Column(length=100)
	private String photos;
	
	
	private boolean enabled;

	
	@ManyToMany
	@JoinTable(
			name="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	
	//CONSTRUCTOR
	public User() {
		
	}
	
	

	public User(String firstName, String lastName, String email, String password, String photos, boolean enabled) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photos = photos;
		this.enabled = enabled;
	}
	
	


	// GETTER & SETTER 
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhotos() {
		return photos;
	}


	public void setPhotos(String photos) {
		this.photos = photos;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	// toString
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", photos=" + photos + ", enabled=" + enabled + "]";
	}

	
	
}
