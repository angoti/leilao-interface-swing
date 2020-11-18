package com.angoti;

class Teste {
	public static void main(String[] args) {
		Leilao l1 = new Leilao("Bicicleta", 10);
		l1.registrarLance("Arematante", 20);
		l1.registrarLance("Arematante", 30);
		l1.registrarLance("Arematante", 12);
		l1.registrarLance("Arematante", 22);
		l1.finalizar(null);
	}

}
