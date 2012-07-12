package categorizacao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.queryParser.ParseException;
import com.wcohen.ss.Jaccard;
import com.wcohen.ss.api.StringWrapper;

import indexacao.*;

//verificaçao da mensagem passa por várias etapas
//1º verifica se a mensagem está em portugues ou ingles
//2º verifica se a mensagem é do tipo resposta, se sim é descartada
//3º verifica se a mensagem tem um nº mínimo de caracteres, caso contrário é descartada
//4º verifica se a mensagem é duplicada ou tem conteúdos impróprios
//5º se passar todas as anteriores verificações é analisado o tipo de mensagem que pode ser informação ou geral

public class MensagemEquipa {

	public static void recebeMensagem(String hashtag_equipa, int modalidade, String utilizador, String imagem, String mensagem, 
			String data_criacao, String lingua, String fonte, Vector<String> vec_mensagens) throws IOException, ParseException{
		if(!tipoResposta(mensagem) && minimoChars(hashtag_equipa, mensagem)){
			if(!mensagemDuplicada(hashtag_equipa,mensagem, data_criacao, vec_mensagens) && !conteudosImproprios(mensagem, lingua)){
				String tipo_mensagem = identificaTipoMensagem(mensagem, modalidade, lingua);
				GereIndiceEquipas.InsereMensagemEquipa(hashtag_equipa, utilizador, imagem, mensagem, data_criacao, lingua, tipo_mensagem, fonte);
			}
		}
	}
	
	public static void recebeMensagemAppM(String id, String hashtag_equipa, int modalidade, String utilizador, String imagem, String mensagem, 
			String data_criacao, String lingua, String fonte) throws IOException{
		String tipo_mensagem  = identificaTipoMensagem(mensagem, modalidade, lingua);
		GereIndiceEquipas.InsereMensagemEquipaAppM(id, hashtag_equipa, utilizador, imagem, mensagem, data_criacao, lingua, tipo_mensagem, fonte);

	}
	//se a mensagem contiver uma palavra chave associada à modalidade 
	//ou contiver uma expressão associada a informação (resultado ou minutos de jogo por ex) é considerada tipo informação
	//caso contrário é uma mensagem do tipo geral
	private static String identificaTipoMensagem(String mensagem,int modalidade, String lingua) throws FileNotFoundException {
		// TODO Auto-generated method stub
		boolean tipo_mensagem = false;

		if(contemExpressaoInfo(mensagem))
			tipo_mensagem = true;
		else{
			if(modalidade == 1 && lingua.equals("pt")){
				File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\keywords_fut_pt.txt");
				Scanner scanner = new Scanner(file);
				tipo_mensagem = verificaPalavraChave(mensagem, scanner);
			}

			else if (modalidade == 1 && lingua.equals("en")){
				File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\keywords_fut_en.txt");
				Scanner scanner = new Scanner(file);
				tipo_mensagem = verificaPalavraChave(mensagem, scanner);
			}
			else if (modalidade == 2 && lingua.equals("pt")){
				File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\keywords_tenis_pt.txt");
				Scanner scanner = new Scanner(file);
				tipo_mensagem = verificaPalavraChave(mensagem, scanner);
			}
			else if (modalidade == 2 && lingua.equals("en")){
				File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\keywords_tenis_en.txt");
				Scanner scanner = new Scanner(file);
				tipo_mensagem = verificaPalavraChave(mensagem, scanner);
			}
		}
		if(tipo_mensagem)
			return "info";
		return "geral";
	}

	//vao ser verificados dois tipos de expressões padrões
	//Padroes resultado e padroes de momentos de jogo
	private static boolean contemExpressaoInfo(String mensagem) {
		// TODO Auto-generated method stub
		//padrao resultado exs. 1-1, 1 vs 1, 1 x 1
		Pattern padrao_resultado = Pattern.compile("[0-9].*(-|x|X|/|vs|Vs|VS).*[0-9]");
		Matcher resultado = padrao_resultado.matcher(mensagem);
		boolean bool_resultado = resultado.find();
		
		//padrao minutos v1 exs. 5', 5 min, 5 minutos
		Pattern padrao_mindepois = Pattern.compile("[0-9].*('|min)");
		Matcher min_depois = padrao_mindepois.matcher(mensagem);
		boolean bool_mindepois = min_depois.find();
		
		//padrao minutos v2 exs. '5, min 5, minuto 5
		Pattern padrao_minantes = Pattern.compile("('|min).*[0-9]");
		Matcher min_antes = padrao_minantes.matcher(mensagem);
		boolean bool_minantes = min_antes.find();
		
		if(bool_resultado || bool_mindepois || bool_minantes)
			return true;
		return false;
	}

	private static boolean verificaPalavraChave(String mensagem, Scanner scanner) {
		// TODO Auto-generated method stub
		while(scanner.hasNextLine()){
			String palavra_chave = scanner.nextLine();
			if(mensagem.contains(palavra_chave) || mensagem.contains(palavra_chave.toUpperCase())
					|| mensagem.contains(devolveInicioMaiuscula(palavra_chave)))
				//				System.out.println("String a procurar: " + str_procurar);
				return true;
		}

		return false;
	}

	//verificar se a mensagem não contém asneiras (necessário a língua porque vamos ter mensagens em EN e PT)
	private static boolean conteudosImproprios(String mensagem, String lingua) throws FileNotFoundException {
		// TODO Auto-generated method stub
		boolean cont_imp;
		if(lingua.equals("pt"))
			cont_imp = procuraAsneiras(mensagem);
		else
			cont_imp = findSwearing(mensagem);

		return cont_imp;
	}

	/*ANTERIOR IMPLEMENTAÇÃO
	//a verificação da mensagem duplicada faz-se consoante a hashtag são vistas as últimas 500
	//mensagens que se encontram no índice relativas a essa hashtag e a esse dia
	 * ANTERIOR IMPLEMENTAÇÃO
	 */
	
	//São vistas todas as mensagens que vão sendo colocadas no vector de mensagens para essa hashtag
	@SuppressWarnings("null")
	private static boolean mensagemDuplicada(String hashtag, String mensagem, String data_criacao, Vector<String> vec_mensagens) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		/*ANTERIOR IMPLEMENTAÇÃO
		Vector<String> mensagens_indice = FerramentasData.devolveMensagensHashData(hashtag, data_criacao);
		
		//se o vector nada tiver significa que nao existem mensagens para essa hashtag naquela data
		//logo a mensagem nunca é duplicada
		if(mensagens_indice == null){
			return false;
		}
		ANTERIOR IMPLEMENTAÇÃO*/
		
		//se o vector de mensagens não tiver ainda mensagens a mensagem não pode ser duplicada
		//adicionamos ao vector
		if(vec_mensagens == null){
			vec_mensagens.add(mensagem);
			return false;
			
		}
		
		/*ANTERIOR IMPLEMENTACAO
		//caso contário compara as mensagens existentes no índice com a mensagem que queremos indexar
		//verificando o caso específico das mensagens reencaminhadas
		else{
			for(int i = 0; i!= mensagens_indice.size(); i++){
				if(comparaDuplicados(verificaPadraoRT(mensagem), verificaPadraoRT(mensagens_indice.elementAt(i))))
					return true;
			}
			return false;
		} ANTERIOR IMPLEMENTAÇÃO*/
		
		//caso contário compara as mensagens existentes no vector de mensagens com a mensagem que queremos indexar
		//verificando o caso específico das mensagens reencaminhadas
		else{
			for(int i = 0; i!= vec_mensagens.size(); i++){
				if(comparaDuplicados(verificaPadraoRT(mensagem), verificaPadraoRT(vec_mensagens.elementAt(i))))
					return true;
			}
			
			//se nao for duplicada adiciona ao vector das mensagens
			vec_mensagens.add(mensagem);
			return false;
		}
	}
	
	//verifica se a mensagem é um retweet
	//Se for é necessário ver se tem texto antes do RT
	private static String verificaPadraoRT(String padrao) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("RT " + "[@]" + "[aA-zZ|0-9]");
		Matcher matcher = pattern.matcher(padrao);
		boolean bool = matcher.find();
		//se a mensagem for retweet 
		if(bool){
			//tem de verificar se tem texto antes do retweet (que pode interessar)
			//em caso de ter texto devolve toda a mensagem
			if(contemTextoAntesRT(padrao)){
				return padrao;
			}
			//se não tiver texto antes do RT devolve apenas a parte da mensagem que interessa comparar
			else{
				int curr_tot_index = padrao.length()-1;

				while(curr_tot_index != -1){
					if(padrao.charAt(curr_tot_index) == ':'){
						int curr_in_index = curr_tot_index -1;
						while(curr_in_index != -1){
							if(padrao.charAt(curr_in_index-2) == ' ' && padrao.charAt(curr_in_index-1) == '@'){
								return padrao.substring(curr_tot_index + 1, padrao.length());
							}
							else {
								curr_in_index--;
							}
						}
					}
					else{
						curr_tot_index--;
					}
				} 	
			}	
		}
		return padrao;
	}

	//método que verifica se mensagem com RT tem texto antes do RT
	private static boolean contemTextoAntesRT(String padrao) {
		// TODO Auto-generated method stub
		if(padrao.charAt(0) == 'R' && padrao.charAt(1) == 'T' && padrao.charAt(3) == '@'){
			return false;
		}
		return true;
	}

	//caso o índice de Jaccard seja superior a 0.8 as mensagens sao consideradas duplicadas
	private static boolean comparaDuplicados(String mensagem, String mensagem_vector) {
		// TODO Auto-generated method stub
		Jaccard j = new Jaccard();
		double valor_comparacao = 0;
		StringWrapper msg_comp = j.prepare(mensagem);
		StringWrapper vecmsg_comp = j.prepare(mensagem_vector);
		valor_comparacao = j.score(msg_comp, vecmsg_comp);
		if(valor_comparacao>0.8){
//			System.out.println("1ª msg: " + mensagem);
//			System.out.println("2ª msg: " + mensagem_vector);
//			System.out.println("Valor de jaccard: " + e);
			return true;
		}
		else
			return false;	

	}

	//método que retorna true se encontrar asneiras na mensagem (en)
	private static boolean findSwearing(String mensagem) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\swearwords_en.txt");
		Scanner scanner = new Scanner(file);

		while(scanner.hasNextLine()){
			String swear_string = scanner.nextLine();
			if(mensagem.contains(swear_string) || mensagem.contains(swear_string.toUpperCase())
					|| mensagem.contains(devolveInicioMaiuscula(swear_string)))
				//				System.out.println("String a procurar: " + str_procurar);
				return true;
		}
		return false;
	}

	//método que retorna true se encontrar asneiras na mensagem (pt)
	private static boolean procuraAsneiras(String mensagem) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\swearwords_pt.txt");
		Scanner scanner = new Scanner(file);

		while(scanner.hasNextLine()){
			String string_asneira = scanner.nextLine();
			if(mensagem.contains(string_asneira) || mensagem.contains(string_asneira.toUpperCase())
					|| mensagem.contains(devolveInicioMaiuscula(string_asneira)))
				//System.out.println("String a procurar: " + str_procurar);
				return true;
		}
		return false;
	}

	//devolve a palavra com a primeira letra em maiúscula: ex golo -> Golo
	private static String devolveInicioMaiuscula(String palavra) {
		// TODO Auto-generated method stub
		String inicial = palavra.substring(0, 1).toUpperCase();
		String abcd = palavra.substring(1, palavra.length());
		palavra = inicial + abcd;
		return palavra;
	}
	
	//tamanho mínimo da mensagem vai ser o tamanho da hashtag mais 4 caracteres
	//Ex: #Benfica 5' (os 3 caracteres sao " " "5" "'") é o mínimo para se identificar um tempo de jogo
	private static boolean minimoChars(String hashtag, String mensagem) {
		// TODO Auto-generated method stub
		if(mensagem.length() >= hashtag.length() + 3)
			return true;
		return false;
	}

	//decisão de descartar as mensagens do tipo reply que normalmente não irão conter conteúdo
	//do interesse do público em geral
	private static boolean tipoResposta(String mensagem) {
		// TODO Auto-generated method stub
		if(mensagem.charAt(0) == '@')
			return true;
		return false;
	}
}
