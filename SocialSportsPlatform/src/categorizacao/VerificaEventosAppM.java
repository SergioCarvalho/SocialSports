package categorizacao;

import indexacao.Evento;

import java.util.Vector;

public class VerificaEventosAppM {
	public static void recolheDadosEvento(Vector<String> vec_string) {
		// TODO Auto-generated method stub
		Vector<String> vec_eventos = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();
		String evento_modalidade = null;
		for(int i = 0; i!= vec_string.size(); i++){
			evento_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());
			vec_eventos.add(evento_modalidade.substring(evento_modalidade.indexOf(':')+1, evento_modalidade.indexOf(',')));
			vec_modalidades.add(evento_modalidade.substring(evento_modalidade.lastIndexOf(':')+1, evento_modalidade.length()));
		}
		gereEventos(vec_eventos, vec_modalidades);
	}

	//método que compara os eventos existentes na BD com os eventos da AppMovel
	//se os eventos não existirem na BD envia o nome e modalidade do evento para inserção
	private static void gereEventos(Vector<String> vec_eventos,Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		//vector com as equipas existentes na BD
		Vector<String> eventos_bd = new Vector<String>();
		eventos_bd = Evento.devolveEventosBD();

		for(int i = 0; i!= vec_eventos.size(); i++){
			if(!eventos_bd.contains(vec_eventos.elementAt(i)))
				Evento.insereEventoBD(vec_eventos.elementAt(i), vec_modalidades.elementAt(i));
		}
	}
}
