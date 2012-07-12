package categorizacao;

import indexacao.Jogador;

import java.util.Vector;

public class VerificaJogadoresAppM {

	public static void recolheDadosJogador(Vector<String> vec_string) {
		// TODO Auto-generated method stub
		Vector<String> vec_jogadores = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();
		String jogador_modalidade = null;
		for(int i = 0; i!= vec_string.size(); i++){
			jogador_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());
			vec_jogadores.add(jogador_modalidade.substring(jogador_modalidade.indexOf(':')+1, jogador_modalidade.indexOf(',')));
			vec_modalidades.add(jogador_modalidade.substring(jogador_modalidade.lastIndexOf(':')+1, jogador_modalidade.length()));
		}
		gereJogadores(vec_jogadores, vec_modalidades);
	}

	private static void gereJogadores(Vector<String> vec_jogadores,Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		Vector<String> jogadores_bd = new Vector<String>();
		jogadores_bd = Jogador.devolveJogadoresBD();

		for(int i = 0; i!= vec_jogadores.size(); i++){
			if(!jogadores_bd.contains(vec_jogadores.elementAt(i)))
				Jogador.insereJogadorBD(vec_jogadores.elementAt(i), vec_modalidades.elementAt(i));
		}
	}

}
