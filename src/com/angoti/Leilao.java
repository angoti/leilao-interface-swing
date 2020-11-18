package com.angoti;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Leilao implements Serializable {
	private Lance lanceVencedor;
	private String nome; //nome do bem a ser leiloado
	private final double valorMinimo; //valor inicial do leilão
	private boolean leilaoAberto;
	private List<Lance> lancesRecebidos;


	public Leilao(String n, double v) {
		nome = n;
		valorMinimo = v;
		leilaoAberto = true;
		lanceVencedor = new Lance("inicial", valorMinimo);
		lancesRecebidos = new ArrayList<Lance>();
	}

	public void registrarLance(String nomeDaPessoa, double valor) {
		Lance lance = new Lance(nomeDaPessoa, valor);
		lancesRecebidos.add(lance);
		if (leilaoAberto) {
			if (valor > lanceVencedor.getValor()) {
				lanceVencedor = lance;
			}
		}
	}

	public void finalizar(JFrame frame) {
		leilaoAberto = false;
		StringBuffer listaDeLances = new StringBuffer();
		for (Lance lance : lancesRecebidos) {
			listaDeLances.append("\n______________\nNome do arrematante: " + lance.getNome()
					+ "\nValor de arremate: " + lance.getValor());
		}
		JTextArea textArea = new JTextArea("Bem leiloado: " + nome + listaDeLances
				+ "\n\n\nLance vencedor" + "\nNome do arrematante: " + lanceVencedor.getNome()
				+ "\nValor de arremate: " + lanceVencedor.getValor());
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		JOptionPane.showMessageDialog(frame, scrollPane);
	}

	public void setLeilaoAberto(boolean leilaoAberto) {
		this.leilaoAberto = leilaoAberto;
	}

	public Lance getLanceVencedor() {
		return lanceVencedor;
	}

	public void setLanceVencedor(Lance lanceVencedor) {
		this.lanceVencedor = lanceVencedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public boolean isLeilaoAberto() {
		return leilaoAberto;
	}

}
