package com.zionflame.imperiumserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 50)
	private String mimeType;

	@Column(nullable = false, length = 255)
	private String fileName;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String path;

	@Column(nullable = false)
	private Long size;

	private Boolean deleted = Boolean.FALSE;
	
	@ManyToOne
	private Usuario usuario;
	
	public Imagem() {
		
	}

	public Imagem(String name, String mimeType, String fileName, String path, Long size, Usuario usuario) {
		this.name = name;
		this.mimeType = mimeType;
		this.fileName = fileName;
		this.path = path;
		this.size = size;
		this.usuario = usuario;
	}
	
}
