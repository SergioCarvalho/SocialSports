package categorizacao;

import indexacao.HashEquipa;
import java.util.Vector;

public class VerificaHashEquipasAppM {

	public static void recolheDadosHashEquipa(Vector<String> vec_string) {
		// TODO Auto-generated method stub
		//hashequipa-lista-5id:5,hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol

		Vector<String> vec_hashequipas = new Vector<String>();
		Vector<String> vec_equipas = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();

		String hash_equipa_modalidade = null;
		Vector<String> equipa_modalidade = new Vector<String>();

		for(int i = 0; i!= vec_string.size(); i++){
			//			System.out.println("1º virgula: " + vec_string.elementAt(i).indexOf(','));
			//			System.out.println("1º virgula: " + vec_string.elementAt(i).lastIndexOf(','));
			hash_equipa_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());

			vec_hashequipas.add(hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(':')+1, 
					hash_equipa_modalidade.indexOf(',')));
			//			System.out.println("Hashtag Equipa: " + hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(':')+1, 
			//					hash_equipa_modalidade.indexOf(',')));

			//este vector vai conter apenas equipa e modalidade Ex: equipa:Benfica,modalidade:Futebol
			//e vai ser usado para retirar a equipa da string inicial
			equipa_modalidade.add(hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(',')+1, 
					hash_equipa_modalidade.length()));
		}

		for(int i = 0; i!= equipa_modalidade.size(); i++){
			vec_equipas.add(equipa_modalidade.elementAt(i).substring(equipa_modalidade.elementAt(i).indexOf(':')+1, 
					equipa_modalidade.elementAt(i).indexOf(',')));
			vec_modalidades.add(equipa_modalidade.elementAt(i).substring(equipa_modalidade.elementAt(i).lastIndexOf(':')+1,
					equipa_modalidade.elementAt(i).length()));
		}

		//gere as hashtags da tabela hashequipa
		gereHashEquipas(vec_hashequipas, vec_equipas, vec_modalidades);
	}

	//A gestão da tabela hashequipa não pressupõe remoções (visto existirem vários utilizadores)
	//Aqui vou apenas realizar inserções na bd, deixando a remoçao para a própria tabela de utilizadores associada às hashtags de equipas

	//método que compara as hashtags de equipas existentes na BD com as hashtags de equipas da AppMovel
	//se as hashtags não existirem na BD envia a hashtag, nome e modalidade da equipa para inserção
	private static void gereHashEquipas(Vector<String> vec_hashequipas, Vector<String> vec_equipas, Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		//vector com as hashtags de equipas existentes na BD
		Vector<String> hashequipas_bd = new Vector<String>();
		hashequipas_bd = HashEquipa.devolveHashEquipasBD();

		for(int i = 0; i!= vec_hashequipas.size(); i++){
			if(!hashequipas_bd.contains(vec_hashequipas.elementAt(i)))
				HashEquipa.insereHashEquipaBD(vec_hashequipas.elementAt(i), vec_equipas.elementAt(i), vec_modalidades.elementAt(i));
		}
	}

	public static void verificaDadosHashEquipa(String hash_equipa_padrao) {
		// TODO Auto-generated method stub
		//hashequipa-lista-5id:5,hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol

		String hash_equipa = null;
		String equipa = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol
		String hash_equipa_modalidade = hash_equipa_padrao.substring(hash_equipa_padrao.indexOf(',')+1,hash_equipa_padrao.length());

		//String que fica com a equipa e a modalidade Ex: equipa:Benfica,modalidade:Futebol
		String equipa_modalidade = hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(',')+1,hash_equipa_modalidade.length());

		hash_equipa = hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(':')+1, hash_equipa_modalidade.indexOf(','));
		equipa = equipa_modalidade.substring(equipa_modalidade.indexOf(':')+1, equipa_modalidade.indexOf(','));
		modalidade = equipa_modalidade.substring(equipa_modalidade.lastIndexOf(':')+1, equipa_modalidade.length());

		HashEquipa.actualizaHashequipa(hash_equipa, equipa, modalidade);
	}

	public static void verificaDadosUtilizadorHashEquipa(String hash_equipa_padrao) {
		// TODO Auto-generated method stub
		//hashequipa-lista-5id:5,hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol

		String hash_equipa = null;
		String equipa = null;
		String modalidade = null;

		//String que fica com os dados de interesse Ex: hashtag:#carregabenfica,equipa:Benfica,modalidade:Futebol
		String hash_equipa_modalidade = hash_equipa_padrao.substring(hash_equipa_padrao.indexOf(',')+1,hash_equipa_padrao.length());

		//String que fica com a equipa e a modalidade Ex: equipa:Benfica,modalidade:Futebol
		String equipa_modalidade = hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(',')+1,hash_equipa_modalidade.length());
		hash_equipa = hash_equipa_modalidade.substring(hash_equipa_modalidade.indexOf(':')+1, hash_equipa_modalidade.indexOf(','));
		equipa = equipa_modalidade.substring(equipa_modalidade.indexOf(':')+1, equipa_modalidade.indexOf(','));
		modalidade = equipa_modalidade.substring(equipa_modalidade.lastIndexOf(':')+1, equipa_modalidade.length());
		
		HashEquipa.actualizaUtilizadorHashEquipa(hash_equipa, equipa, modalidade);
	}

	public static void verificaIdsHashEquipa(Vector<String> vec_hashequipas) {
		// TODO Auto-generated method stub
		Vector<String> vec_idhashequipaappm = new Vector<String>();
		
		for(int i = 0; i != vec_hashequipas.size(); i++){
			String str_hashequipa = vec_hashequipas.elementAt(i).substring(vec_hashequipas.elementAt(i).indexOf(',')+1, 
					vec_hashequipas.elementAt(i).length());
			String hash_equipa = str_hashequipa.substring(str_hashequipa.indexOf(':')+1,str_hashequipa.indexOf(','));
			vec_idhashequipaappm.add(hash_equipa);
		}
		HashEquipa.eliminaDadosUtilizadorHashEquipa(vec_idhashequipaappm);
	}
}