package categorizacao;

import indexacao.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificaEquipasAppM {

	public static void recolheDadosEquipa(Vector<String> vec_string) {
		// TODO Auto-generated method stub
		Vector<String> vec_equipas = new Vector<String>();
		Vector<String> vec_modalidades = new Vector<String>();
		String equipa_modalidade = null;
		for(int i = 0; i!= vec_string.size(); i++){
			equipa_modalidade = vec_string.elementAt(i).substring((vec_string.elementAt(i).indexOf(',') + 1
			), vec_string.elementAt(i).length());
			vec_equipas.add(equipa_modalidade.substring(equipa_modalidade.indexOf(':')+1, equipa_modalidade.indexOf(',')));
			vec_modalidades.add(equipa_modalidade.substring(equipa_modalidade.lastIndexOf(':')+1, equipa_modalidade.length()));
		}
		
		gereEquipas(vec_equipas, vec_modalidades);
	}

	//método que compara as equipas existentes na BD com as equipas da AppMovel
	//se as equipas não existirem na BD envia o nome e modalidade da equipa para inserção
	private static void gereEquipas(Vector<String> vec_equipas,Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		//vector com as equipas existentes na BD
		Vector<String> equipas_bd = new Vector<String>();
		equipas_bd = Equipa.devolveEquipasBD();

		for(int i = 0; i!= vec_equipas.size(); i++){
			if(!equipas_bd.contains(vec_equipas.elementAt(i)))
				Equipa.insereEquipaBD(vec_equipas.elementAt(i), vec_modalidades.elementAt(i));
		}
	}
}