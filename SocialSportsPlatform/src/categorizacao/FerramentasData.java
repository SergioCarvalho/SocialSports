package categorizacao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class FerramentasData {

	//	public static void main(String[] args){
	//		FerramentasData.devolveDataHojeString();
	//	}

	//método que devolve a data mais recente de um tweet sobre uma hashtag específica
	//para todas as mensagens que venham após essa data sejam descartadas (são duplicações)
	public static String devolveUltimaDataPorHashtag(String hashtag, String data_hoje) throws IOException, ParseException{
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
		//ver se o ficheiro deve ficar aqui
		File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
		Directory index = new SimpleFSDirectory(file);

		if(file.exists()){
			String[] array_strings; //array de strings que fica com as hashtags para o MultiFieldQueryParser
			String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

			List<String> lista_strings = new ArrayList<String>();
			lista_strings.add(hashtag);
			lista_strings.add(data_hoje);
			array_strings = lista_strings.toArray(new String[lista_strings.size()]);

			List<String> lista_campos = new ArrayList<String>();
			lista_campos.add("hashtag");
			lista_campos.add("data_ano_mes_dia");
			array_campos = lista_campos.toArray(new String[lista_campos.size()]);

			BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

			//esta variável vai ser igual ao número de tweets pesquisados por hashtag
			//secalhar mando vir através do método <- VER
			int hitsPerPage = 150;
			IndexSearcher searcher = new IndexSearcher(index,true);
			TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			if(hits.length > 0){
				int docId = hits[0].doc;
				Document d = searcher.doc(docId);
				String ultima_data = d.get("data_hora_minuto");
				int i = hits.length;
				//ciclo que percorre as últimas datas inseridas e devolve a mais recente
				while(i!=0){
					docId = hits[i-1].doc;
					d = searcher.doc(docId);
					//System.out.println("O que tem o d.get: " + d.get("data_horaminuto"));
					//System.out.println("O que tem a ultima data: " + ultima_data);
					if(Integer.parseInt(d.get("data_hora_minuto")) > Integer.parseInt(ultima_data))
						ultima_data = d.get("data_hora_minuto");	
					i--;
				}
				System.out.println("A ultima data é: " + ultima_data);
				searcher.close();
				return ultima_data;
			}
			else
				searcher.close();
		}
		return null;
	}

	//método que usa uma boolean query para juntar uma query simples com uma rangequery
	//bom para devolver os tweets associados a um hashtag da última semana por ex
	/*	private void devolveConteudosHashtagIntervalo() throws Exception {
		// TODO Auto-generated method stub
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
		//ver onde colocar este ficheiro
		File f = new File("C:\\Documents and Settings\\Sérgio\\Ambiente de trabalho\\LuceneIndex");
		Directory index = new SimpleFSDirectory(f);
		IndexSearcher searcher = new IndexSearcher(index);

		Query query1 = new QueryParser(Version.LUCENE_34, "hashtag", analyzer).parse("#Benfica");
		Query query2 = new TermRangeQuery("data_anomesdia", "20111120", "20111121", true, true);

		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, BooleanClause.Occur.MUST);
		booleanQuery.add(query2, BooleanClause.Occur.MUST);

		int hitsPerPage = 15;
		TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
		searcher.search(booleanQuery, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		System.out.println("Found " + hits.length + " hits.");
		for(int i=0;i<hits.length;++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("hashtag") + " " + d.get("corpo") + " " + d.get("data_anomesdia"));
		}

		searcher.close();

	}
	 */	

	public static String devolveDataHojeString(){
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//System.out.println("A data de hoje é: " + sdf.format(date));
		return sdf.format(date);
	}

	public static String devolveDataActualString(){
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		//System.out.println("A data de hoje é: " + sdf.format(date));
		return sdf.format(date);
	}
	
	public static String devolveDataTwitterString(Date data_twitter){
		String data = data_twitter.toString();

		String ano = data.substring(data.length()-4, data.length());
		String mes = convMes(data.substring(4,7));
		String dia = data.substring(8,10);
		String horas = data.substring(11, 13);
		String minutos = data.substring(14, 16);
//		String segundos = data.substring(17, 19);

		String final_date = ano+mes+dia+horas+minutos;//+segundos;
//		System.out.println("Formato final: " + final_date);
		return final_date;

	}

	private static String convMes(String mes_string) {
		// TODO Auto-generated method stub
		if(mes_string.equals("Jan")){
			return "01";
		}
		else if(mes_string.equals("Fev")){
			return "02";
		}
		else if(mes_string.equals("Mar")){
			return "03";
		}
		else if(mes_string.equals("Apr")){
			return "04";
		}
		else if(mes_string.equals("May")){
			return "05";
		}
		else if(mes_string.equals("Jun")){
			return "06";
		}
		else if(mes_string.equals("Jul")){
			return "07";
		}
		else if(mes_string.equals("Aug")){
			return "08";
		}
		else if(mes_string.equals("Sep")){
			return "09";
		}
		else if(mes_string.equals("Oct")){
			return "10";
		}
		else if(mes_string.equals("Nov")){
			return "11";
		}
		else{
			return "12";
		}
	}

	//devolve as mensagens relativas à hashtag e ao dia da mensagem que queremos ver se é duplicada 
	public static Vector<String> devolveMensagensHashData(String hashtag, String data_criacao) throws IOException, ParseException {
		// TODO Auto-generated method stub
		Vector<String> mensagens_indice = new Vector<String>();
		String data_amd = devolveDataAnoMesDia(data_criacao);

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
		//ver onde colocar este ficheiro
		File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
		Directory index = new SimpleFSDirectory(file);

		if(file.exists()){
			String[] array_strings; //array de strings que fica com a hashtag e a data para o MultiFieldQueryParser
			String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

			List<String> lista_strings = new ArrayList<String>();
			lista_strings.add(hashtag);
			lista_strings.add(data_amd);
			array_strings = lista_strings.toArray(new String[lista_strings.size()]);

			List<String> lista_campos = new ArrayList<String>();
			lista_campos.add("hashtag");
			lista_campos.add("data_ano_mes_dia");
			array_campos = lista_campos.toArray(new String[lista_campos.size()]);

			BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

			//esta variável representa o total de mensagens a guardar no vector para comparar com a mensagem
			//que estamos a ver se é duplicada, neste caso vamos guardar as últimas 200 mensagens
			int ocorrencias = 200;
			IndexSearcher searcher = new IndexSearcher(index,true);
			TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, ocorrencias, false, false, false, false);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			if(hits.length > 0){
				for(int i = 0; i!= hits.length; i++){
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					mensagens_indice.add(d.get("mensagem"));
				}
				searcher.close();
				return mensagens_indice;
			}
			else
				searcher.close();
		}
		return null;
	}

	//A data formatada a partir do Twitter fica ano+mes+dia+horas+minutos
	//Aqui vamos devolver apenas ano+mes+dia
	public static String devolveDataAnoMesDia(String data_criacao) {
		// TODO Auto-generated method stub
		String data_amd = data_criacao.substring(0, 8);
		return data_amd;
	}

	//A data formatada a partir do Twitter fica ano+mes+dia+horas+minutos
	//Aqui vamos devolver apenas hora+minuto
	public static String devolveDataHoraMinuto(String data_criacao) {
		// TODO Auto-generated method stub
		String data_hm = data_criacao.substring(8,12);
		return data_hm;
	}

	//A data formatada a partir do Twitter fica ano+mes+dia+horas+minutos
	//A data formatada para inserir no índice e mostrar na app móvel fica ex. 2011/12/04
	public static String devolveDataAnoMesDiaF(String data_criacao) {
		// TODO Auto-generated method stub
		String data_f_amd = data_criacao.substring(0, 4) + "/" + data_criacao.substring(4, 6)
		+ "/" + data_criacao.substring(6, 8);
		return data_f_amd;
	}

	//A data formatada a partir do Twitter fica ano+mes+dia+horas+minutos
	//A data formatada (hora:minuto) para inserir no índice e mostrar na app móvel fica ex. 19:01
	public static String devolveDataHoraMinutoF(String data_criacao) {
		// TODO Auto-generated method stub
		String data_f_hm = data_criacao.substring(8, 10) + ":" + data_criacao.substring(10, 12);
		return data_f_hm;
	}

	public static boolean pertenceDiaHoje(Date tweet_createdAt) {
		// TODO Auto-generated method stub
		String data_twitter = devolveDataTwitterString(tweet_createdAt);
		String data_final = devolveDataAnoMesDia(data_twitter);
		if(data_final.equals(devolveDataHojeString()))
			return true;
		return false;
	}
}
