package categorizacao;

import indexacao.Preferencias;

import java.util.Vector;

public class VerificaPreferenciasAppM {

	public static void recolheDadosPreferencias(String last_preferences) {
		// TODO Auto-generated method stub
//		preferences-lista-18id:18,tipoinformacao:info,lingua:portugues,fontesinformacao:socsportstwitter
		
		String tipo_informacao = null;
		String lingua = null;
		String fontes_informacao = null;

		String prefs = last_preferences.substring(last_preferences.indexOf(',')+1, last_preferences.length());
		String lingua_fontes = prefs.substring(prefs.indexOf(',')+1, prefs.length());
		
		tipo_informacao = prefs.substring(prefs.indexOf(':')+1, prefs.indexOf(','));
		lingua = lingua_fontes.substring(lingua_fontes.indexOf(':')+1, lingua_fontes.indexOf(','));
		fontes_informacao = lingua_fontes.substring(lingua_fontes.lastIndexOf(':')+1, lingua_fontes.length());

//		System.out.println("Tipo info: " + tipo_informacao);
//		System.out.println("Lingua: " + lingua);
//		System.out.println("Fontes: " + fontes_informacao);
		gerePreferenciasUtilizador(tipo_informacao, lingua, fontes_informacao);
	}

	private static void gerePreferenciasUtilizador(String tipo_informacao, String lingua, String fontes_informacao) {
		// TODO Auto-generated method stub
		Vector<String> vec_bdprefs = new Vector<String>();
		
		//devido à limitação do Sencha vamos utilizar sempre o utilizador com id_utilizador = 1
		int id_utilizador = 1;
		
		//vector que fica com os elementos existentes na bd necessarios para comparacao das preferencias
		vec_bdprefs = Preferencias.devolvePreferenciasBDUtilizador(id_utilizador);
		
//		System.out.println("BD tipo info: " + vec_bdprefs.elementAt(0));
//		System.out.println("BD lingua: " + vec_bdprefs.elementAt(1));
//		System.out.println("BD fonte info: " + vec_bdprefs.elementAt(2));
		
		//se tipoinfo, lingua ou fontes tiverem mudado faço a actualizacao dos dados na BD
		if(!tipo_informacao.equals(vec_bdprefs.elementAt(0)) || !lingua.equals(vec_bdprefs.elementAt(1))
				|| !fontes_informacao.equals(vec_bdprefs.elementAt(2)))
			Preferencias.ActualizaPreferenciasUtilizador(id_utilizador, tipo_informacao,lingua,fontes_informacao);
		else
			System.out.println("Nada a actualizar");
	}
	
}
