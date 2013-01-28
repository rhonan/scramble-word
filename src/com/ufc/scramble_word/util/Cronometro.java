package com.ufc.scramble_word.util;

import android.os.AsyncTask;
import android.widget.TextView;

	public class Cronometro extends AsyncTask<Void, Void, Void> {

		private long tempo;
		private long tempoInicio;
		private long tempoDecorrido;
		private boolean cronometrando;
		private TextView texto;

		public Cronometro(TextView texto) {
			tempo = 0;
			tempoInicio = System.currentTimeMillis();
			tempoDecorrido = 0;
			cronometrando = true;
			this.texto = texto;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... paramss) {
			try {
				while (!isCancelled()) {
					if (cronometrando) {
						tempo = System.currentTimeMillis() - tempoInicio
								+ tempoDecorrido;

						Thread.sleep(1);
						publishProgress();
					}
				}
			} catch (InterruptedException ex) {
				// ex.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			long time = tempo;
			long minuto = 0;
			long segundo = 0;
			long mile = 0;
			mile = time % 1000;
			time = time - mile;
			segundo = (time / 1000);
			minuto = (segundo - (segundo % 60)) / 60;
			segundo = segundo % 60;

			texto.setText(minuto + ":" + segundo + ":" + mile);
		}

		public void reset() {
			cronometrando = false;
			tempoInicio = 0;
			tempoDecorrido = 0;
		}

		public void pause() {
			cronometrando = false;
			tempoDecorrido = tempo;
		}

		public void start() {
			cronometrando = true;
			tempoInicio = System.currentTimeMillis();
		}
	}

