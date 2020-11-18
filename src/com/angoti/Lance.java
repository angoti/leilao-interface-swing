package com.angoti;

import java.io.Serializable;

public class Lance implements Serializable {
	private String nome;
	private double valor;

	Lance(String n, double v) {
		nome = n;
		valor = v;
	}

	public String getNome() {
		return nome;
	}

	public double getValor() {
		return valor;
	}
	
}
