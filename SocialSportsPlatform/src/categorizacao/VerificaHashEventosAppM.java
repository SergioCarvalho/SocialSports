package categorizacao;

import indexacao.HashEvento;
import java.util.Vector;

public class VerificaHashEventosAppM {

	public static void recolheDadosHashEvento(Vector<String> vec_string) {
		// TODO Auto-generated method stub
		//		hashevento-lista-3id:3,hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol

		Vector<String> vec_hasheventos = new Vector<String>();
		Vector<String> vec_eventos = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();

		String hash_evento_modalidade = null;
		Vector<String> evento_modalidade = new Vector<String>();

		for(int i = 0; i!= vec_string.size(); i++){
			hash_evento_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());
			vec_hasheventos.add(hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(':')+1, 
					hash_evento_modalidade.indexOf(',')));
			
			//este vector vai conter apenas jogador e modalidade Ex: equipa:Federer,modalidade:Ténis
			//e vai ser usado para retirar o jogador da string inicial
			evento_modalidade.add(hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(',')+1, 
					hash_evento_modalidade.length()));
		}

		for(int i = 0; i!= evento_modalidade.size(); i++){
			vec_eventos.add(evento_modalidade.elementAt(i).substring(evento_modalidade.elementAt(i).indexOf(':')+1, 
					evento_modalidade.elementAt(i).indexOf(',')));
			vec_modalidades.add(evento_modalidade.elementAt(i).substring(evento_modalidade.elementAt(i).lastIndexOf(':')+1,
					evento_modalidade.elementAt(i).length()));
		}
		//gere as hashtags da tabela hashevento
		gereHashEventos(vec_hasheventos, vec_eventos, vec_modalidades);
	}

	private static void gereHashEventos(Vector<String> vec_hasheventos,	Vector<String> vec_eventos, Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		//vector com as hashtags de eventos existentes na BD
		Vector<String> hasheventos_bd = new Vector<String>();
		hasheventos_bd = HashEvento.devolveHashEventosBD();

		for(int i = 0; i!= vec_hasheventos.size(); i++){
			if(!hasheventos_bd.contains(vec_hasheventos.elementAt(i)))
				HashEvento.insereHashEventoBD(vec_hasheventos.elementAt(i), vec_eventos.elementAt(i), vec_modalidades.elementAt(i));
		}	
	}
	
	public static void verificaDadosHashEvento(String hash_evento_padrao) {
		// TODO Auto-generated method stub
		//hashevento-lista-3id:3,hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol

		String hash_evento = null;
		String evento = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol
		String hash_evento_modalidade = hash_evento_padrao.substring(hash_evento_padrao.indexOf(',')+1,hash_evento_padrao.length());

		//String que fica com o evento e a modalidade Ex: evento:MundialClubes,modalidade:Futebol
		String equipa_modalidade = hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(',')+1,hash_evento_modalidade.length());

		hash_evento = hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(':')+1, hash_evento_modalidade.indexOf(','));
		evento = equipa_modalidade.substring(equipa_modalidade.indexOf(':')+1, equipa_modalidade.indexOf(','));
		modalidade = equipa_modalidade.substring(equipa_modalidade.lastIndexOf(':')+1, equipa_modalidade.length());

		HashEvento.actualizaHashevento(hash_evento, evento, modalidade);
	}

	public static void verificaDadosUtilizadorHashEvento(String hash_evento_padrao) {
		// TODO Auto-generated method stub
		//hashevento-lista-3id:3,hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol

		String hash_evento = null;
		String evento = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#MundialClubes,evento:MundialClubes,modalidade:Futebol
		String hash_evento_modalidade = hash_evento_padrao.substring(hash_evento_padrao.indexOf(',')+1,hash_evento_padrao.length());

		//String que fica com o evento e a modalidade Ex: evento:MundialClubes,modalidade:Futebol
		String evento_modalidade = hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(',')+1,hash_evento_modalidade.length());

		hash_evento = hash_evento_modalidade.substring(hash_evento_modalidade.indexOf(':')+1, hash_evento_modalidade.indexOf(','));
		evento = evento_modalidade.substring(evento_modalidade.indexOf(':')+1, evento_modalidade.indexOf(','));
		modalidade = evento_modalidade.substring(evento_modalidade.lastIndexOf(':')+1, evento_modalidade.length());

		HashEvento.actualizaUtilizadorHashEvento(hash_evento, evento, modalidade);
	}

	public static void verificaIdsHashEvento(Vector<String> vec_hasheventos) {
		// TODO Auto-generated method stub
		Vector<String> vec_idhasheventoappm = new Vector<String>();

		for(int i = 0; i != vec_hasheventos.size(); i++){
			String str_hashevento = vec_hasheventos.elementAt(i).substring(vec_hasheventos.elementAt(i).indexOf(',')+1, 
					vec_hasheventos.elementAt(i).length());
			String hash_evento = str_hashevento.substring(str_hashevento.indexOf(':')+1,str_hashevento.indexOf(','));
			vec_idhasheventoappm.add(hash_evento);
		}
		HashEvento.eliminaDadosUtilizadorHashEvento(vec_idhasheventoappm);
	}
}
