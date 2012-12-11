package com.ufc.scramble_word.util;
public class Cronometro extends Thread {
	private long tempo;
	private long tempoInicio;
	private long tempoDecorrido;
	private boolean cronometrando;

	public Cronometro() {
		tempo = 0;
		tempoInicio = System.currentTimeMillis();
		tempoDecorrido = 0;
		cronometrando = true;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (cronometrando) {
					tempo = System.currentTimeMillis() - tempoInicio
							+ tempoDecorrido;
				}
				Thread.sleep(1);

			}
		} catch (InterruptedException ex) {
			// ex.printStackTrace();
		}
	}

	public void pausar() {
		cronometrando = false;
		tempoDecorrido = tempo;
	}

	public void iniciar() {
		cronometrando = true;
		tempoInicio = System.currentTimeMillis();
	}

	public void zerar() {
		tempoInicio = 0;
		tempoDecorrido = 0;
	}

	public String show() {
		long time = tempo;
		long minuto = 0;
		long segundo = 0;
		long mile = 0;
		mile = time % 1000;
		time = time - mile;
		segundo = (time / 1000);
		minuto = (segundo - (segundo % 60)) / 60;
		segundo = segundo % 60;

		return (minuto + ":" + segundo + ":" + mile);
	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	
	public static void main(String[] args) throws Throwable {
		Cronometro cro = new Cronometro();
		cro.start();
		sleep(3000);
		cro.pausar();
		System.out.println(cro.show());	
		sleep(5000);
		cro.iniciar();
		System.out.println(cro.show());
		cro.show();
		cro.stop();

	}

}
