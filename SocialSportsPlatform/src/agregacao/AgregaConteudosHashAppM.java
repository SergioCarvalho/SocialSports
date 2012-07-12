package agregacao;

import categorizacao.*;
import indexacao.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgregaConteudosHashAppM {

	public static void agregaHashEquipas(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex 1 hashequipa-lista-6id:6,hashtag:#Porto,equipa:Porto,modalidade:Futebol
		//Ex 2 hashequipa-lista-11id:11,hashtag:#EspanhaTenis,equipa:EspanhaTénis,modalidade:Ténis
		
		String hashequipas_fut = "hashequipa-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",equipa:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" 
		+ ",modalidade:"	+ "Futebol";
		
		String hashequipas_tenis = "hashequipa-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",equipa:" + "[aA-zZ|0-9]*" 
		+ ",modalidade:"	+ "Ténis";
		
		agregaHashEquipasFut(ficheiro_global, hashequipas_fut);
		agregaHashEquipasTenis(ficheiro_global, hashequipas_tenis);
		
		//para verificar actualizações feitas na app móvel não reflectidas na BD de hashtags de equipas
		agregaHashEquipasTotal(ficheiro_global, hashequipas_fut, hashequipas_tenis);
	}

	private static void agregaHashEquipasTotal(File ficheiro_global, String hashequipas_fut, String hashequipas_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern_fut = Pattern.compile(hashequipas_fut);
		Matcher matcher_fut = pattern_fut.matcher(s_final);

		Pattern pattern_tenis = Pattern.compile(hashequipas_tenis);
		Matcher matcher_tenis = pattern_tenis.matcher(s_final);
		
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das hashtags de todas equipas
		Vector<String> vec_hashequipas = new Vector<String> ();
		
		//adiciona os padroes de fut
		while(matcher_fut.find()){
			vec_hashequipas.add(matcher_fut.group());
			enc_padrao = true;
		}
		//adiciona os padroes de tenis
		while(matcher_tenis.find()){
			vec_hashequipas.add(matcher_tenis.group());
			enc_padrao = true;
		} 
	
		//se existir algum padrao de hashtags de equipas faz as verificações /actualizações se necessario
		if(enc_padrao){
			for(int i = 0; i!= vec_hashequipas.size(); i++){
				VerificaHashEquipasAppM.verificaDadosHashEquipa(vec_hashequipas.elementAt(i));
			}
			
			//depois de actualizar a tabela hashequipa verifica a tabela utilizadorhashequipa
			for(int i = 0; i!= vec_hashequipas.size(); i++){
				VerificaHashEquipasAppM.verificaDadosUtilizadorHashEquipa(vec_hashequipas.elementAt(i));
			}
			
			//Após inserir/actualizar a tabela utilizadorhashequipa verifica se não ficaram duplicados
			VerificaHashEquipasAppM.verificaIdsHashEquipa(vec_hashequipas);
		}
	}

	private static void agregaHashEquipasTenis(File ficheiro_global,String hashequipas_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hashequipas_tenis);
		Matcher matcher = pattern.matcher(s_final);

		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das hashtags de equipas de ténis
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a ténis ele gere (insere e remove) as hashtags associadas a essas equipas
		if(enc_padrao)
			VerificaHashEquipasAppM.recolheDadosHashEquipa(vec_string);
		
		//caso contrário confirma que não ficaram na bd hashtags de equipas que o utilizador já não quer ver 
		else
			HashEquipa.eliminaHashEquipaMod("Ténis");
	}

	private static void agregaHashEquipasFut(File ficheiro_global,String hashequipas_fut) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hashequipas_fut);
		Matcher matcher = pattern.matcher(s_final);

		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das hashtags de equipas de futebol
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a fut ele gere (insere e remove) as hashtags associadas a essas equipas
		if(enc_padrao)
			VerificaHashEquipasAppM.recolheDadosHashEquipa(vec_string);
		
		//caso contrário confirma que não ficaram na bd hashtags de equipas que o utilizador já não quer ver 
		else{
			System.out.println("Vai eliminar equipas");
			HashEquipa.eliminaHashEquipaMod("Futebol");
		}	
	}

	public static void agregaHashJogadores(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex1 hashjogador-lista-3id:3,hashtag:#PabloAimar,jogador:PabloAimar,modalidade:Futebol
		//Ex2 hashjogador-lista-4id:4,hashtag:#Federer,jogador:RogerFederer,modalidade:Ténis
		
		String hashjogadores_fut = "hashjogador-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" 
		+ ",jogador:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",modalidade:"	+ "Futebol";
		
		String hashjogadores_tenis = "hashjogador-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",jogador:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" 
		+ ",modalidade:"	+ "Ténis";
		 
		agregaHashJogadoresFut(ficheiro_global, hashjogadores_fut);
		agregaHashJogadoresTenis(ficheiro_global, hashjogadores_tenis);
		
		//para verificar actualizações feitas na app móvel não reflectidas na BD de hashtags de jogadores
		agregaHashJogadoresTotal(ficheiro_global, hashjogadores_fut, hashjogadores_tenis);
		
	}

	private static void agregaHashJogadoresTotal(File ficheiro_global, String hashjogadores_fut, String hashjogadores_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern_fut = Pattern.compile(hashjogadores_fut);
		Matcher matcher_fut = pattern_fut.matcher(s_final);

		Pattern pattern_tenis = Pattern.compile(hashjogadores_tenis);
		Matcher matcher_tenis = pattern_tenis.matcher(s_final);
		
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das hashtags de todos os jogadores
		Vector<String> vec_hashjogadores = new Vector<String> ();
		
		//adiciona os padroes de fut
		while(matcher_fut.find()){
			vec_hashjogadores.add(matcher_fut.group());
			enc_padrao = true;
		}
		//adiciona os padroes de tenis
		while(matcher_tenis.find()){
			vec_hashjogadores.add(matcher_tenis.group());
			enc_padrao = true;
		} 
	
		//se existir algum padrao de hashtags de jogadores faz as verificações /actualizações se necessario
		if(enc_padrao){
			for(int i = 0; i!= vec_hashjogadores.size(); i++){
				VerificaHashJogadoresAppM.verificaDadosHashJogador(vec_hashjogadores.elementAt(i));
			}
			
			//depois de actualizar a tabela hashjogador verifica a tabela utilizadorhashjogador
			for(int i = 0; i!= vec_hashjogadores.size(); i++){
				VerificaHashJogadoresAppM.verificaDadosUtilizadorHashJogador(vec_hashjogadores.elementAt(i));
			}
			
			//Após inserir/actualizar a tabela utilizadorhashequipa verifica se não ficaram duplicados
			VerificaHashJogadoresAppM.verificaIdsHashJogador(vec_hashjogadores);
		}
	}

	private static void agregaHashJogadoresTenis(File ficheiro_global,String hashjogadores_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hashjogadores_tenis);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de jogadores
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a ténis ele gere (insere e remove) as hashtags associadas a esses jogadores
		if(enc_padrao)
			VerificaHashJogadoresAppM.recolheDadosHashJogador(vec_string);
		
		//caso contrário confirma que não ficaram na bd hashtags de jogadores que o utilizador já não quer ver 
		else
			HashJogador.eliminaHashJogadorMod("Ténis");
	}

	private static void agregaHashJogadoresFut(File ficheiro_global,String hashjogador_fut) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hashjogador_fut);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de jogadores
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a fut ele gere (insere e remove) as hashtags associadas a esses jogadores
		if(enc_padrao)
			VerificaHashJogadoresAppM.recolheDadosHashJogador(vec_string);
		//caso contrário confirma que não ficaram na bd hashtags de jogadores que o utilizador já não quer ver 
		else
			HashJogador.eliminaHashJogadorMod("Futebol");
	}

	public static void agregaHashEventos(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex1 hashevento-lista-3id:3,hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol
		//Ex2 hashevento-lista-2id:2,hashtag:#AustralianOpen,evento:AustralianOpen,modalidade:Ténis
		
		String hasheventos_fut = "hashevento-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",evento:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" +
		",modalidade:"	+ "Futebol";
		
		String hasheventos_tenis = "hashevento-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" + 
		",evento:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" + 
		",modalidade:"	+ "Ténis";
		 
		agregaHashEventosFut(ficheiro_global, hasheventos_fut);
		agregaHashEventosTenis(ficheiro_global, hasheventos_tenis);
		
		//para verificar actualizações feitas na app móvel não reflectidas na BD de hashtags de eventos
		agregaHashEventosTotal(ficheiro_global, hasheventos_fut, hasheventos_tenis);
	}

	private static void agregaHashEventosTotal(File ficheiro_global, String hasheventos_fut, String hasheventos_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern_fut = Pattern.compile(hasheventos_fut);
		Matcher matcher_fut = pattern_fut.matcher(s_final);

		Pattern pattern_tenis = Pattern.compile(hasheventos_tenis);
		Matcher matcher_tenis = pattern_tenis.matcher(s_final);
		
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das hashtags de todos os eventos
		Vector<String> vec_hasheventos = new Vector<String> ();
		
		//adiciona os padroes de fut
		while(matcher_fut.find()){
			vec_hasheventos.add(matcher_fut.group());
			enc_padrao = true;
		}
		//adiciona os padroes de tenis
		while(matcher_tenis.find()){
			vec_hasheventos.add(matcher_tenis.group());
			enc_padrao = true;
		} 
	
		//se existir algum padrao de hashtags de eventos faz as verificações /actualizações se necessario na tabela hashevento
		if(enc_padrao){
			for(int i = 0; i!= vec_hasheventos.size(); i++){
				VerificaHashEventosAppM.verificaDadosHashEvento(vec_hasheventos.elementAt(i));
			}
			
			//depois de actualizar a tabela hashevento verifica a tabela utilizadorhashevento
			for(int i = 0; i!= vec_hasheventos.size(); i++){
				VerificaHashEventosAppM.verificaDadosUtilizadorHashEvento(vec_hasheventos.elementAt(i));
			}
			
			//Após inserir/actualizar a tabela utilizadorhashequipa verifica se não ficaram duplicados
			VerificaHashEventosAppM.verificaIdsHashEvento(vec_hasheventos);
		}
	}

	private static void agregaHashEventosTenis(File ficheiro_global, String hasheventos_tenis) throws IOException{
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hasheventos_tenis);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de hashtags de eventos de ténis
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a ténis ele gere (insere e remove) as hashtags associadas a esses eventos
		if(enc_padrao)
			VerificaHashEventosAppM.recolheDadosHashEvento(vec_string);
		
		//caso contrário confirma que não ficaram na bd hashtags de eventos que o utilizador já não quer ver 
		else
			HashEvento.eliminaHashEventoMod("Ténis");
	}

	private static void agregaHashEventosFut(File ficheiro_global, String hasheventos_fut) throws IOException{
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(hasheventos_fut);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de hashtags de eventos de ténis
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		//se existem padrões relativos a fut ele gere (insere e remove) as hashtags associadas a esses eventos
		if(enc_padrao)
			VerificaHashEventosAppM.recolheDadosHashEvento(vec_string);
		
		//caso contrário confirma que não ficaram na bd hashtags de eventos que o utilizador já não quer ver 
		else
			HashEvento.eliminaHashEventoMod("Futebol");
	}
}
