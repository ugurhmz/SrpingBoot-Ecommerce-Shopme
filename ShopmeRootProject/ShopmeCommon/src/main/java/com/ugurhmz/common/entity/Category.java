package com.ugurhmz.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=130, nullable=false, unique = true)
	private String name;
	
	@Column(length=60, nullable=false, unique = true)
	private String nickName;
	
	@Column(length=150, nullable=false)
	private String image;
	
	private boolean enabled;
	
	@OneToOne
	@JoinColumn(name="parent_id")			//	categories tablosunda ->  parent_id column olacak.
	private Category parent;
	
	@OneToMany(mappedBy="parent")
	private Set<Category> children = new HashSet<>();		//Set -> unique,  Set does not have index no, order mixed

	
	// DEF CONSTRUCTOR   -> For nested exception is org.hibernate.InstantiationException
	public Category(){

	}
	
	
	// ONLY ID CONSTRUCTOR
	public Category(Integer id) {
		this.id = id;
	}
	
	
	
	// nullable olanları yazdık testte sorun çıkartmasın . ROOT category için constructor
	public Category(String name) {
		this.name =name;
		this.nickName = name;
		this.image = "def.png";
	}
	
	
	
	// Sub category Constructor
	public Category(String name, Category parent) {
		this(name);
		this.parent = parent;	
	}
	
	
	
	
	// GETTER & SETTER
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	
}











