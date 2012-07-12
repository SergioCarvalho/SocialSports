package categorizacao;

import indexacao.HashJogador;
import java.util.Vector;

public class VerificaHashJogadoresAppM {

	public static void recolheDadosHashJogador(Vector<String> vec_string) {
		// TODO Auto-generated method stub

		//hashjogador-lista-4id:4,hashtag:#Federer,jogador:RogerFederer,modalidade:Ténis

		Vector<String> vec_hashjogadores = new Vector<String>();
		Vector<String> vec_jogadores = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();

		String hash_jogador_modalidade = null;
		Vector<String> jogador_modalidade = new Vector<String>();

		for(int i = 0; i!= vec_string.size(); i++){
			hash_jogador_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());

			vec_hashjogadores.add(hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(':')+1, 
					hash_jogador_modalidade.indexOf(',')));

			//este vector vai conter apenas jogador e modalidade Ex: equipa:Federer,modalidade:Ténis
			//e vai ser usado para retirar o jogador da string inicial
			jogador_modalidade.add(hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(',')+1, 
					hash_jogador_modalidade.length()));
		}

		for(int i = 0; i!= jogador_modalidade.size(); i++){
			vec_jogadores.add(jogador_modalidade.elementAt(i).substring(jogador_modalidade.elementAt(i).indexOf(':')+1, 
					jogador_modalidade.elementAt(i).indexOf(',')));
			vec_modalidades.add(jogador_modalidade.elementAt(i).substring(jogador_modalidade.elementAt(i).lastIndexOf(':')+1,
					jogador_modalidade.elementAt(i).length()));
		}
		gereHashJogadores(vec_hashjogadores, vec_jogadores, vec_modalidades);
	}

	//A gestão da tabela hashjogador não pressupõe remoções (visto existirem vários utilizadores)
	//Aqui vou apenas realizar inserções na bd, deixando a remoçao para a própria tabela de utilizadores associada às hashtags de jogadores
	private static void gereHashJogadores(Vector<String> vec_hashjogadores,	Vector<String> vec_jogadores, Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		//vector com as hashtags de jogadores existentes na BD
		Vector<String> hashjogadores_bd = new Vector<String>();
		hashjogadores_bd = HashJogador.devolveHashJogadoresBD();

		for(int i = 0; i!= vec_hashjogadores.size(); i++){
			if(!hashjogadores_bd.contains(vec_hashjogadores.elementAt(i)))
				HashJogador.insereHashJogadorBD(vec_hashjogadores.elementAt(i), vec_jogadores.elementAt(i), vec_modalidades.elementAt(i));
		}
	}

	public static void verificaDadosHashJogador(String hash_jogador_padrao) {
		// TODO Auto-generated method stub
		//hashjogador-lista-3id:3,hashtag:#PabloAimar,jogador:PabloAimar,modalidade:Futebol

		String hash_jogador = null;
		String jogador = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#PabloAimar,jogador:PabloAimar,modalidade:Futebol
		String hash_jogador_modalidade = hash_jogador_padrao.substring(hash_jogador_padrao.indexOf(',')+1,hash_jogador_padrao.length());

		//String que fica com o jogador e a modalidade Ex: jogador:PabloAimar,modalidade:Futebol
		String jogador_modalidade = hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(',')+1,hash_jogador_modalidade.length());

		hash_jogador = hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(':')+1, hash_jogador_modalidade.indexOf(','));
		jogador = jogador_modalidade.substring(jogador_modalidade.indexOf(':')+1, jogador_modalidade.indexOf(','));
		modalidade = jogador_modalidade.substring(jogador_modalidade.lastIndexOf(':')+1, jogador_modalidade.length());

		HashJogador.actualizaHashjogador(hash_jogador, jogador, modalidade);
	}

	public static void verificaDadosUtilizadorHashJogador(String hash_jogador_padrao) {
		// TODO Auto-generated method stub
		//hashjogador-lista-3id:3,hashtag:#PabloAimar,jogador:PabloAimar,modalidade:Futebol

		String hash_jogador = null;
		String jogador = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol
		String hash_jogador_modalidade = hash_jogador_padrao.substring(hash_jogador_padrao.indexOf(',')+1,hash_jogador_padrao.length());

		//String que fica com a equipa e a modalidade Ex: equipa:Benfica,modalidade:Futebol
		String jogador_modalidade = hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(',')+1,hash_jogador_modalidade.length());

		hash_jogador = hash_jogador_modalidade.substring(hash_jogador_modalidade.indexOf(':')+1, hash_jogador_modalidade.indexOf(','));
		jogador = jogador_modalidade.substring(jogador_modalidade.indexOf(':')+1, jogador_modalidade.indexOf(','));
		modalidade = jogador_modalidade.substring(jogador_modalidade.lastIndexOf(':')+1, jogador_modalidade.length());

		HashJogador.actualizaUtilizadorHashJogador(hash_jogador, jogador, modalidade);
	}

	public static void verificaIdsHashJogador(Vector<String> vec_hashjogadores) {
		// TODO Auto-generated method stub
		Vector<String> vec_idhashjogadorappm = new Vector<String>();

		for(int i = 0; i != vec_hashjogadores.size(); i++){
			String str_hashjogador = vec_hashjogadores.elementAt(i).substring(vec_hashjogadores.elementAt(i).indexOf(',')+1, 
					vec_hashjogadores.elementAt(i).length());
			String hash_jogador = str_hashjogador.substring(str_hashjogador.indexOf(':')+1,str_hashjogador.indexOf(','));
			vec_idhashjogadorappm.add(hash_jogador);
		}
		HashJogador.eliminaDadosUtilizadorHashJogador(vec_idhashjogadorappm);
	}
}