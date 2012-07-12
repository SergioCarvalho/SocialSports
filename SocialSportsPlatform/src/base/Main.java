package base;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.lucene.queryParser.ParseException;

import categorizacao.FerramentasData;

import agregacao.*;


	class AppThread extends Thread{

		public AppThread() throws Exception{
		start();
		Thread.currentThread();
		System.out.println("Vai fazer o sleep");
//		Thread.sleep(5000);
		Thread.sleep(300000); // 5 min de sleep
	}

	public void run(){
		//interacção Twitter
		try {
			System.out.println("Entrou no pesquisa equipas");
			PesquisaEquipas.iniciaPesquisaEquipas();
//			System.out.println("Entrou no pesquisa jogadores");
//			PesquisaJogadores.iniciaPesquisaJogadores();
			System.out.println("Entrou no pesquisa eventos");
			PesquisaEventos.iniciaPesquisaEventos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//interacção app móvel
		//		File ficheiro_global = GeraFichConteudosApp.devolveFicheiroGlobal();
		//		
		//		AgregaConteudosAppM.agregaEquipas(ficheiro_global);
		//		AgregaConteudosAppM.agregaJogadores(ficheiro_global);
		//		AgregaConteudosAppM.agregaEventos(ficheiro_global);
		//		AgregaConteudosAppM.agregaPreferencias(ficheiro_global);
		//		
		//		AgregaConteudosHashAppM.agregaHashEquipas(ficheiro_global);
		//		AgregaConteudosHashAppM.agregaHashJogadores(ficheiro_global);
		//		AgregaConteudosHashAppM.agregaHashEventos(ficheiro_global);

		//		File ficheiro_mensagens = GeraFichConteudosApp.devolveFicheiroMensagens();
		//		
		//		AgregaConteudosMensagensAppM.agregaMensagens(ficheiro_mensagens);

	}
}


	public class Main {

		public static void main(String[] args) throws Exception{
			Main main = new Main();
			main.runApp();
		}

		public void runApp() throws Exception {
			// TODO Auto-generated method stub
					int data_hora = 0;
					Date mindate = new Date();
					data_hora = mindate.getHours();

					System.out.println("Começou às " + FerramentasData.devolveDataActualString());
					
			//fazer iterações durante x tempo
					while(data_hora != 23){
						Thread runAppThread = new Thread(new AppThread());
						data_hora = devolveHoraData();
					}
			
			//interacção Twitter
			//		PesquisaEquipas.iniciaPesquisaEquipas();
			//		PesquisaJogadores.iniciaPesquisaJogadores();
			//		data_minuto = devolveMinutosData();
			//		System.out.println("Data minuto: " + data_minuto);
			//		PesquisaEventos.iniciaPesquisaEventos();
			//		}
					System.out.println("Terminou às " + FerramentasData.devolveDataActualString());
			//interacção app móvel
			//		File ficheiro_global = GeraFichConteudosApp.devolveFicheiroGlobal();
			//		
			//		AgregaConteudosAppM.agregaEquipas(ficheiro_global);
			//		AgregaConteudosAppM.agregaJogadores(ficheiro_global);
			//		AgregaConteudosAppM.agregaEventos(ficheiro_global);
			//		AgregaConteudosAppM.agregaPreferencias(ficheiro_global);
			//		
			//		AgregaConteudosHashAppM.agregaHashEquipas(ficheiro_global);
			//		AgregaConteudosHashAppM.agregaHashJogadores(ficheiro_global);
			//		AgregaConteudosHashAppM.agregaHashEventos(ficheiro_global);

			//		File ficheiro_mensagens = GeraFichConteudosApp.devolveFicheiroMensagens();
			//		
			//		AgregaConteudosMensagensAppM.agregaMensagens(ficheiro_mensagens);
			//		GereDadosMsgAppM.agregaDadosPreferencia();
			//		GereIndices.renovaIndices();

		}

		private int devolveHoraData() {
			// TODO Auto-generated method stub
			int hora_data = 0;
			Date data = new Date();
			hora_data = data.getHours();
			return hora_data;
			
		}

		public int devolveMinutosData(){
			int minuto_data = 0;
			Date data = new Date();
			minuto_data = data.getMinutes();
			return minuto_data;
		}
	}
