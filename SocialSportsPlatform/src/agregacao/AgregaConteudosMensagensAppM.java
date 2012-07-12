package agregacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import categorizacao.VerificaMensagensAppM;

public class AgregaConteudosMensagensAppM {

	public static void agregaMensagens(File ficheiro_mensagens) throws IOException {
		// TODO Auto-generated method stub
		//Ex. (pt) newmessages-lista-3id:3,hashtag:#Benfica,mensagem:Espera-se casa cheia no fim de semana contra o Gil Vicente,lingua:portugues
		//Ex. (en) newmessages-lista-4id:4,hashtag:#ausopen,mensagem:Great win by Frederico Gil over Granollers. He is now the first portuguese player to reach 3rd round in Australian Open,lingua:ingles
		String mensagens_pt = "newmessages-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|k|K|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" + 
		",mensagem:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f|' ']*" + 
		",lingua:" + "portugues";

		String mensagens_en = "newmessages-lista-" + "[0-9]*" + "id:" + "[0-9]*" + ",hashtag:" 
		+ "[aA-zZ|0-9|#|Ç|ç|k|K|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f]*" + 
		",mensagem:" + "[aA-zZ|0-9|#|Ç|ç|À|à|Á|á|Ã|ã|É|é|Ê|ê|Í|í|Ó|ó|Ú|ú|.|:|\\-|\'|\"|\\|!|?|(|)|=|º|\b|\f|' ']*" + 
		",lingua:" + "ingles";
		
		agregaMensagensPorLingua(ficheiro_mensagens, mensagens_pt, mensagens_en);
	}

	private static void agregaMensagensPorLingua(File ficheiro_mensagens, String mensagens_pt, String mensagens_en) throws IOException {
		// TODO Auto-generated method stub
		String s_final = null;
		FileReader fr = new FileReader(ficheiro_mensagens);
		char[] creader = new char[(int)ficheiro_mensagens.length()];
		fr.read(creader);
		s_final = new String(creader);
		Pattern pattern_pt = Pattern.compile(mensagens_pt);
		Matcher matcher_pt = pattern_pt.matcher(s_final);
		Pattern pattern_en = Pattern.compile(mensagens_en);
		Matcher matcher_en = pattern_en.matcher(s_final);

		boolean enc_padrao = false;

		//vector com as ocorrências dos padrões das mensagens
		Vector<String> vec_msg = new Vector<String> ();
		
		//adiciona os padroes em portugues
		while (matcher_pt.find()) {
			vec_msg.add(matcher_pt.group());
			enc_padrao = true;
		}

		//adiciona os padroes em ingles
		while (matcher_en.find()) {
			vec_msg.add(matcher_en.group());
			enc_padrao = true;
		}
		
		//se existem mensagens na app móvel
		if(enc_padrao){
			//crio um HashMap com todos os ids e mensagens provenientes da AppMóvel
			HashMap<Integer, String> hash_idmsg = new HashMap<Integer, String>();
			for(int i = 0; i != vec_msg.size(); i++){
				hash_idmsg.put(devolveIdMensagem(vec_msg.elementAt(i)), vec_msg.elementAt(i));
			}

			//crio um segundo hashmap com as mensagens ordenadas, para verificar quais ja foram colocadas nos índices
			//e quais ainda precisam de ser colocadas, e para garantir a inserção com a ordem certa
			HashMap<Object, String> hash_ordmap = new LinkedHashMap<Object, String>();
			List<Integer> hash_idmsgKeys = new ArrayList<Integer>(hash_idmsg.keySet());
			List<String> hash_idmsgValues = new ArrayList<String>(hash_idmsg.values());

			TreeSet<Integer> sortedSet = new TreeSet<Integer>(hash_idmsgKeys);
			Object[] sortedArray = sortedSet.toArray();
			int size = sortedArray.length;

			for(int i = 0; i!= size; i++){
				hash_ordmap.put(sortedArray[i], hash_idmsgValues.get(hash_idmsgKeys.indexOf(sortedArray[i])));
			}

			//vou buscar o último id das mensagens associadas a equipas
			int last_id_msg = devolveUltimoIdMsg();

			//construo um set que fica com todas as chaves existentes no hashmap
			Set<Object> set_hsmap = hash_ordmap.keySet();
			Iterator<Object> itr = set_hsmap.iterator();

			//verifico as chaves do HashMap em relação ao último id guardado para equipas
			//se a chave for inferior a mensagem é antiga e não é para colocar no índice
			while(itr.hasNext()){
				if(((Number)itr.next()).intValue()<=last_id_msg)
					itr.remove();
			}

			//construo um novo set que tem apenas as mensagens a inserir no índice
			Set<Object> set_keys = hash_ordmap.keySet();	
			Iterator<Object> set_iterator = set_keys.iterator();

			//para todas as mensagens cujo id é superior ao ultimo guardado, guardo num vector
			//para inserir no índice
			Vector<String> vec_mensagens = new Vector<String>();
			while(set_iterator.hasNext()){
				vec_mensagens.add(hash_ordmap.get(set_iterator.next()));
			}
			
			//para todas as mensagens guardadas no vector verifico os dados para inserir no índice
			VerificaMensagensAppM.verificaDadosMensagem(vec_mensagens);
			
			//no fim actualizo o último id guardado para as mensagens
			int ultimo_id = sortedSet.last();
			actualizaUltimaIdMensagem(ultimo_id);
		}
		else{
			System.out.println("Nao encontrou mensagens");
		}
	}

	private static void actualizaUltimaIdMensagem(int ultimo_id) {
		// TODO Auto-generated method stub
		File ficheiro = new File("C:\\Workspace\\SocialSports\\Ficheiros\\MsgID.txt");
		String str_ultimo_id = Integer.toString(ultimo_id);
		try{
			BufferedWriter bfwritter = new BufferedWriter(new FileWriter(ficheiro));
			bfwritter.write(str_ultimo_id);
			bfwritter.close();
		}
		catch (Exception e){
			System.err.println("Erro no actualizaIdLastMsg: " + e.getMessage());
		}
	}

	private static int devolveUltimoIdMsg() throws IOException {
		// TODO Auto-generated method stub

		File ficheiro = new File("C:\\Workspace\\SocialSports\\Ficheiros\\MsgID.txt");
		int last_id = 0;
		if(ficheiro.exists()){
			FileReader fr = new FileReader(ficheiro);
			LineNumberReader ln = new LineNumberReader(fr);
			last_id = Integer.parseInt(ln.readLine());
			return last_id;	
		}
		else
			return 0;
	}

	private static int devolveIdMensagem(String mensagem) {
		// TODO Auto-generated method stub
		//newmessages-lista-3id:3,hashtag:#Benfica,mensagem:Espera-se casa cheia no fim de semana contra o Gil Vicente,lingua:portugues
		int id = Integer.parseInt(mensagem.substring(mensagem.indexOf(':')+1,mensagem.indexOf(',')));
		return id;
	}
}