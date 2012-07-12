package agregacao;

import categorizacao.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgregaConteudosAppM {

	public static void agregaEquipas(File ficheiro_global) throws IOException{
		//verificar equipas por modalidade
		//equipas de futebol
		String equipas_fut = "equipa-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" +
		"[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Futebol";

		//equipas ténis
		String equipas_tenis = "equipa-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" + 
		"[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Ténis";

		agregaEquipasFut(ficheiro_global, equipas_fut);
		agregaEquipasTenis(ficheiro_global,equipas_tenis);

	}

	private static void agregaEquipasFut(File ficheiro, String equipas_fut) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro);
		char[] creader = new char[(int)ficheiro.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(equipas_fut);
		Matcher matcher = pattern.matcher(s_final);

		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de equipas
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		if(enc_padrao)
			VerificaEquipasAppM.recolheDadosEquipa(vec_string);
	}

	public static void agregaEquipasTenis(File ficheiro, String equipas_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro);
		char[] creader = new char[(int)ficheiro.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(equipas_tenis);
		Matcher matcher = pattern.matcher(s_final);

		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de equipas
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		if(enc_padrao)
			VerificaEquipasAppM.recolheDadosEquipa(vec_string);
	}


	public static void agregaJogadores(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex1 jogador-lista-2id:2,name:PabloAimar,modalidade:Futebol
		//Ex2 jogador-lista-1id:1,name:RogerFederer,modalidade:Ténis
		//verificar jogadores por modalidade
		//jogadores de futebol
		String jogadores_fut = "jogador-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" + 
		"[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Futebol";

		//jogadores de ténis
		String jogadores_tenis = "jogador-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" +
		"[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Ténis";

		agregaJogadoresFut(ficheiro_global, jogadores_fut);
		agregaJogadoresTenis(ficheiro_global, jogadores_tenis);
	}

	private static void agregaJogadoresTenis(File ficheiro_global,String jogadores_tenis) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(jogadores_tenis);
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

		if(enc_padrao)
			VerificaJogadoresAppM.recolheDadosJogador(vec_string);
	}

	private static void agregaJogadoresFut(File ficheiro_global,String jogadores_fut) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(jogadores_fut);
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

		if(enc_padrao)
			VerificaJogadoresAppM.recolheDadosJogador(vec_string);
	}

	public static void agregaEventos(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex1 evento-lista-2id:2,name:MundialClubes,modalidade:Futebol
		//Ex2 evento-lista-1id:1,name:AustralianOpen,modalidade:Ténis

		//verificar eventos por modalidade
		//eventos de futebol
		String eventos_fut = "evento-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Futebol";

		//eventos de ténis
		String eventos_tenis = "evento-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",name:" 
		+ "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*"
		+ ",modalidade:" + /*"[aA-zZ]**/ "Ténis";

		agregaEventosFut(ficheiro_global, eventos_fut);
		agregaEventosTenis(ficheiro_global, eventos_tenis);
	}

	private static void agregaEventosTenis(File ficheiro, String eventos_tenis) throws IOException{
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro);
		char[] creader = new char[(int)ficheiro.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(eventos_tenis);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de eventos
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		if(enc_padrao)
			VerificaEventosAppM.recolheDadosEvento(vec_string);
	}

	private static void agregaEventosFut(File ficheiro, String eventos_fut) throws IOException{
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro);
		char[] creader = new char[(int)ficheiro.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(eventos_fut);
		Matcher matcher = pattern.matcher(s_final);
		//Matcher matcher = pattern.matcher(dumb_str);
		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões de eventos
		Vector<String> vec_string = new Vector<String> ();

		while (matcher.find()) {
			//			System.out.println("I found the text: " + matcher.group());
			//			System.out.println("Inicial index: " + matcher.start());
			//			System.out.println("Inicial index: " + matcher.end());
			vec_string.add(matcher.group());
			enc_padrao = true;
		}

		if(enc_padrao)
			VerificaEventosAppM.recolheDadosEvento(vec_string);
	}

	public static void agregaPreferencias(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub
		//Ex 1 preferences-lista-11id:11,tipoinformacao:info,lingua:portugues,fontesinformacao:twitter
		//Ex 2 preferences-lista-11id:11,tipoinformacao:info,lingua:portugues,fontesinformacao:socialsports
		//Ex 3 preferences-lista-18id:18,tipoinformacao:info,lingua:portugues,fontesinformacao:socsportstwitter

		String fonte_informacao = devolveFonteInformacao(ficheiro_global);

		String preferencias = "preferences-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",tipoinformacao:" + "[aA-zZ]*"
		+ ",lingua:" + "[aA-zZ]*" + ",fontesinformacao:" + fonte_informacao;


		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern = Pattern.compile(preferencias);
		Matcher matcher = pattern.matcher(s_final);

		//String que vai ser passada à classe preferencias para verificar se os dados mudaram
		String last_preferences = null;

		if (matcher.find()) {
			last_preferences = matcher.group();
			//System.out.println("Ultimas preferencias sao: " + last_preferences);
			VerificaPreferenciasAppM.recolheDadosPreferencias(last_preferences);
		}
	}

	private static String devolveFonteInformacao(File ficheiro_global) throws IOException {
		// TODO Auto-generated method stub

		String str_twitter = "fontesinformacao:twitter";
		String str_socialsports = "fontesinformacao:socialsports";
		String str_twittersocialsports = "fontesinformacao:socsportstwitter";

		String s_final = null;
		FileReader fr = new FileReader(ficheiro_global);
		char[] creader = new char[(int)ficheiro_global.length()];
		fr.read(creader);
		s_final = new String(creader);

		Pattern pattern_twitter = Pattern.compile(str_twitter);
		Matcher matcher_twitter = pattern_twitter.matcher(s_final);
		Pattern pattern_socsports = Pattern.compile(str_socialsports);
		Matcher matcher_socsports = pattern_socsports.matcher(s_final);
		Pattern pattern_twittersocsports = Pattern.compile(str_twittersocialsports);
		Matcher matcher_twittersocsports = pattern_twittersocsports.matcher(s_final);

		if(matcher_twitter.find()){
			return "twitter";
		}
		else if(matcher_socsports.find()){
			return "socialsports";
		}
		else if(matcher_twittersocsports.find()){
			return "socsportstwitter";
		}
		else
			return "twitter";
	}
}
