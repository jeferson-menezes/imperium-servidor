package com.zionflame.imperiumserver.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.zionflame.imperiumserver.model.enums.Renda;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TipoAtivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;

	@Enumerated(EnumType.STRING)
	private Renda renda;
	
	public TipoAtivo() {}
	
}
