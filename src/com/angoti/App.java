package com.angoti;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class App implements ActionListener {

	private JFrame frame;
	private JTextField tfNome;
	private JTextField tfValor;

	private List<Leilao> listaDeLeiloes;
	private JList<String> jList;
	private DefaultListModel<String> defaultListModel;
	private JTextField tfLeilaoDoBem;
	private JTextField tfNomeDoArrematante;
	private JTextField tfValorDoLance;
	private JRadioButton rdbtnSim;
	private JRadioButton rdbtnNo;
	private JButton btnRegistrarLance;
	private Leilao leilaoEmCurso;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		listaDeLeiloes = new ArrayList<Leilao>();
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Leilão");
		frame.setBounds(100, 100, 540, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("", "[65px][6px][100px][150px,grow]", "[20px][20px][23px][][grow]"));

		JLabel lblNewLabel = new JLabel("Nome do bem");
		frame.getContentPane().add(lblNewLabel, "cell 0 0,alignx left,aligny center");

		tfNome = new JTextField();
		frame.getContentPane().add(tfNome, "cell 2 0 2 1,growx,aligny top");
		tfNome.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Valor inicial");
		frame.getContentPane().add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");

		tfValor = new JTextField();
		frame.getContentPane().add(tfValor, "cell 2 1 2 1,growx,aligny top");
		tfValor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bens cadastrados");
		frame.getContentPane().add(lblNewLabel_2, "cell 0 3 3 1,alignx center");

		JLabel lblLeiloDoBem = new JLabel("Leil\u00E3o do bem");
		frame.getContentPane().add(lblLeiloDoBem, "flowx,cell 3 3,alignx left");

		tfLeilaoDoBem = new JTextField();

		tfLeilaoDoBem.setEditable(false);
		tfLeilaoDoBem.setColumns(100);
		frame.getContentPane().add(tfLeilaoDoBem, "cell 3 3");

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 3 4,grow");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));

		JLabel lblNewLabel_3 = new JLabel("Nome do arrematante");
		panel.add(lblNewLabel_3, "cell 0 0,alignx trailing");

		tfNomeDoArrematante = new JTextField();
		panel.add(tfNomeDoArrematante, "cell 1 0,growx");
		tfNomeDoArrematante.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Valor do lance");
		panel.add(lblNewLabel_4, "cell 0 1");

		tfValorDoLance = new JTextField();
		panel.add(tfValorDoLance, "cell 1 1,growx");
		tfValorDoLance.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Aberto");
		panel.add(lblNewLabel_5, "cell 0 2");

		rdbtnSim = new JRadioButton("Sim");
		rdbtnSim.addActionListener(this);
		panel.add(rdbtnSim, "flowx,cell 1 2");

		rdbtnNo = new JRadioButton("N\u00E3o");
		rdbtnNo.addActionListener(this);
		panel.add(rdbtnNo, "cell 1 2");
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNo);
		group.add(rdbtnSim);
		btnRegistrarLance = new JButton("Registrar Lance");
		btnRegistrarLance.addActionListener(this);
		botaoCadastrar();
		listaDeBensCadastrados();
		panel.add(btnRegistrarLance, "cell 0 3 2 1,growx");
		
		btnNewButton_1 = new JButton("Salvar");
		btnNewButton_1.addActionListener(this);
		panel.add(btnNewButton_1, "cell 0 4,growx");
		
		btnNewButton_2 = new JButton("Carregar");
		btnNewButton_2.addActionListener(this);
		panel.add(btnNewButton_2, "cell 1 4,growx");
	}

	private void listaDeBensCadastrados() {
		defaultListModel = new DefaultListModel<String>();
		jList = new JList<String>(defaultListModel);
		jList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1) {
					int index = jList.locationToIndex(mouseEvent.getPoint());
					leilaoEmCurso = listaDeLeiloes.get(index);
					tfLeilaoDoBem.setText(leilaoEmCurso.getNome());
					if (leilaoEmCurso.isLeilaoAberto())
						rdbtnSim.setSelected(true);
					else {
						rdbtnNo.setSelected(true);
						desabilitaCamposLance();
					}

				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(jList);

		frame.getContentPane().add(scrollPane, "cell 0 4 3 1,grow");
	}

	private void botaoCadastrar() {
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(this);
		frame.getContentPane().add(btnNewButton, "cell 0 2 4 1,growx,aligny top");
	}

	private void desabilitaCamposLance() {
		tfNomeDoArrematante.setEnabled(false);
		tfValorDoLance.setEnabled(false);
		btnRegistrarLance.setEnabled(false);
	}

	private void habilitaCamposLance() {
		tfNomeDoArrematante.setEnabled(true);
		tfValorDoLance.setEnabled(true);
		btnRegistrarLance.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
		case "Sim":
			habilitaCamposLance();
			leilaoEmCurso.setLeilaoAberto(true);
			break;
		case "N�o":
			desabilitaCamposLance();
			leilaoEmCurso.setLeilaoAberto(false);
			leilaoEmCurso.finalizar(frame);
			break;
		case "Cadastrar":
			Leilao leilao = new Leilao(tfNome.getText(), Double.parseDouble(tfValor.getText()));
			listaDeLeiloes.add(leilao);
			defaultListModel.addElement(leilao.getNome());
			break;
		case "Registrar Lance":
			leilaoEmCurso.registrarLance(tfNomeDoArrematante.getText(), Double.parseDouble(tfValorDoLance.getText()));
			break;
		case "Salvar":
			salvar();
			break;
		case "Carregar":
			carregar();
			break;
		default:
			break;
		}
	}

	private void carregar()  {
		FileInputStream fis;
		try {
			fis = new FileInputStream("bd.leilao");
			ObjectInputStream ois = new ObjectInputStream(fis);
			listaDeLeiloes = (List<Leilao>) ois.readObject();
			ois.close();
			defaultListModel.clear();
			for (Leilao leilaoSalvo : listaDeLeiloes) {
				defaultListModel.addElement(leilaoSalvo.getNome());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void salvar() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("bd.leilao");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listaDeLeiloes);
			oos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
