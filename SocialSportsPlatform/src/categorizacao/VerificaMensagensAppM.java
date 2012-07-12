package categorizacao;

import indexacao.HashEquipa;
import indexacao.HashEvento;
import indexacao.HashJogador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class VerificaMensagensAppM {

	static Vector<String> vec_equipas = new Vector<String>();
	static Vector<String> vec_jogadores = new Vector<String>();
	static Vector<String> vec_eventos = new Vector<String>();


	public static void verificaDadosMensagem(Vector<String> vec_mensagens) throws IOException {
		// TODO Auto-generated method stub

		//preencho os vectores de equipas, jogadores e eventos porque quando for inserir a mensagem
		//no índice e verificar o tipo da mensagem preciso de saber a modalidade e favorito(equipa,
		//jogador ou evento) a que essa mensagem/hashtag pertence
		vec_equipas = HashEquipa.devolveHashEquipasBD();
		vec_jogadores = HashJogador.devolveHashJogadoresBD();
		vec_eventos = HashEvento.devolveHashEventosBD();

		//para todas as mensagens a inserir nos índices categoriza os seus dados, mensagem a mensagem
		for(String mensagem_total: vec_mensagens){
			String id = devolveIdMsgAppM(mensagem_total);
			String hashtag = devolveHashtagMsgAppM(mensagem_total);
			String mensagem = devolveMsgAppM(mensagem_total);
			String lingua = devolveLinguaMsgAppM(mensagem_total);
			categorizaDadosMensagemAppM(id, hashtag, mensagem, lingua);
			//			System.out.println("Hashtag: " + hashtag);
			//			System.out.println("Mensagem: " + mensagem);
			//			System.out.println("Lingua: " + lingua);
		}	
	}

	//pega no padrao total proveniente do modelo existe na app movel e devolve a língua
	private static String devolveLinguaMsgAppM(String mensagem_total) {
		// TODO Auto-generated method stub
		int lang = mensagem_total.length() - mensagem_total.lastIndexOf(':');
		if(lang == 10)
			return "pt";
		else
			return "en";
	}

	//pega no padrao total proveniente do modelo existe na app movel e retira-lhe a mensagem
	private static String devolveMsgAppM(String mensagem_total) {
		// TODO Auto-generated method stub
		//Ex. newmessages-lista-1id:1,hashtag:#ausopen,mensagem:Frederico Gil faz história ao conseguir o apuramento para a terceira ronda do Australian Open,lingua:portugues
		String msg_cortada = mensagem_total.substring(mensagem_total.indexOf(',')+1, mensagem_total.length());
		String msg_mcortada = msg_cortada.substring(msg_cortada.indexOf(',')+1, msg_cortada.length());
		String mensagem = msg_mcortada.substring(msg_mcortada.indexOf(':')+1, msg_mcortada.lastIndexOf(','));
		return mensagem;
	}

	//pega no padrao total proveniente do modelo existe na app movel e retira-lhe o id
	private static String devolveIdMsgAppM(String mensagem_total){

		//Ex. newmessages-lista-1id:1,hashtag:#ausopen,mensagem:Frederico Gil faz história ao conseguir o apuramento para a terceira ronda do Australian Open,lingua:portugues
		String id = mensagem_total.substring(mensagem_total.indexOf(':')+1,mensagem_total.indexOf(',') );
		return id;
	}

	//pega no padrao total proveniente do modelo existe na app movel e retira-lhe a hashtag
	private static String devolveHashtagMsgAppM(String mensagem_total) {
		// TODO Auto-generated method stub
		//Ex. newmessages-lista-1id:1,hashtag:#ausopen,mensagem:Frederico Gil faz história ao conseguir o apuramento para a terceira ronda do Australian Open,lingua:portugues
		String msg_cortada = mensagem_total.substring(mensagem_total.indexOf(',')+1, mensagem_total.length());
		String hashtag = msg_cortada.substring(msg_cortada.indexOf(':')+1, msg_cortada.indexOf(','));
		return hashtag;
	}

	//processo de categorização das mensagens provenientes da app movel contém 
	//1º atribuição do utilizador
	//2º atribuição da foto de utilizador
	//3º verificação se a hashtag faz parte da mensagem, caso contrário adicionar
	//4º atribuição de data à mensagem
	//5º atribuição da fonte à mensagem
	private static void categorizaDadosMensagemAppM(String id, String hashtag, String mensagem, String lingua) throws IOException {
		// TODO Auto-generated method stub
		String utilizador = devolveMensagemUtilizador();
		String imagem = devolveMensagemImagem();

		//verificação se a hashtag faz parte da mensagem, caso contrário adiciono-a no fim da msg
		if(!mensagem.contains(hashtag))
			mensagem += " " + hashtag;

		//4º atribuição de data à mensagem
		String data_msg = devolveMensagemData();
		//5º atribuição da fonte à mensagem	
		String fonte = devolveMensagemFonte();

		//defino o id_modalidade associado à hashtag da mensagem e passa para o recebeMensagemAppM
		// de equipas que identifica o tipo da mensagem 
		if(vec_equipas.contains(hashtag)){
			int modalidade = HashEquipa.devolveIdModalidePorDescHashEquipa(hashtag);
			MensagemEquipa.recebeMensagemAppM(id, hashtag, modalidade, utilizador, imagem, mensagem, data_msg, lingua, fonte);
		}
		//defino o id_modalidade associado à hashtag da mensagem e passa para o recebeMensagemAppM
		// de jogadores que identifica o tipo da mensagem
		else if(vec_jogadores.contains(hashtag)){
			int modalidade = HashJogador.devolveIdModalidePorDescHashJogador(hashtag);
			MensagemJogador.recebeMensagemAppM(id, hashtag, modalidade, utilizador, imagem, mensagem, data_msg, lingua, fonte);
		}
		//defino o id_modalidade associado à hashtag da mensagem e passa para o recebeMensagemAppM
		// de eventos que identifica o tipo da mensagem
		else if(vec_eventos.contains(hashtag)){
			int modalidade = HashEvento.devolveIdModalidePorDescHashEvento(hashtag);
			MensagemEvento.recebeMensagemAppM(id, hashtag, modalidade, utilizador, imagem, mensagem, data_msg, lingua, fonte);
		}
	}

	//estas mensagens são provenientes da app móvel
	private static String devolveMensagemFonte() {
		// TODO Auto-generated method stub
		return "Social Sports";
	}

	//atribui a data actual à mensagem Ex. 201201211721
	private static String devolveMensagemData() {
		// TODO Auto-generated method stub
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		return sdf.format(date);
	}

	//foto por defeito
	private static String devolveMensagemImagem() {
		// TODO Auto-generated method stub
		return "https://twimg0-a.akamaihd.net/profile_images/1223812314/sergiofoto.JPG";
	}

	//utilizador por defeito
	private static String devolveMensagemUtilizador() {
		// TODO Auto-generated method stub
		return "sencha_user";
	}
}
