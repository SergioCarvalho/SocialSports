package agregacao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.wcohen.ss.Jaccard;
import com.wcohen.ss.api.StringWrapper;

public class AgregaDadosVisualizacao {
	
	public static void agregaPorTipoLinguaFonte(String tipo_mensagem, String lingua, 
			String fonte_info, Vector<String> vec_hashtags) throws IOException, ParseException, InterruptedException{
		// TODO Auto-generated method stub
		String ind_lingua;
		String directoria;

		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices		
		if(lingua.equals("ingles"))
			ind_lingua = "en";
		else
			ind_lingua = "pt";
		
		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices
		if(fonte_info.equals("twitter"))
			directoria = "Indices_Lucene";
		else{
			directoria = "Indices_LuceneAppM";
			GereIndices.gereIndAppM();
		}	
		ArrayList<String> messages_list = new ArrayList<String>();

		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			File file = new File("C:\\Workspace\\SocialSports\\" + directoria + "\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(tipo_mensagem);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("tipo_mensagem");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				//TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
						//System.out.println((j + 1) + ". " + d.get("hashtag") + " " + d.get("utilizador") + " " + d.get("mensagem")
						//		+ " " + d.get("data_hm_formatada"));
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}
	
	public static void agregaPorLinguaFonte(String lingua, String fonte_info, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub
		String ind_lingua;
		String directoria;

		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices		
		if(lingua.equals("ingles"))
			ind_lingua = "en";
		else
			ind_lingua = "pt";
		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices
		if(fonte_info.equals("twitter"))
			directoria = "Indices_Lucene";
		else{
			directoria = "Indices_LuceneAppM";
			GereIndices.gereIndAppM();
		}
			
		ArrayList<String> messages_list = new ArrayList<String>();
		
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\" + directoria + "\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaPorTipoFonte(String tipo_mensagem, String fonte_info, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub
		String directoria;

		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices
		if(fonte_info.equals("twitter"))
			directoria = "Indices_Lucene";
		else{
			directoria = "Indices_LuceneAppM";
			GereIndices.gereIndAppM();
		}
		
		ArrayList<String> messages_list = new ArrayList<String>();
		
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\" + directoria + "\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(tipo_mensagem);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("tipo_mensagem");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaPorTipoLingua(String tipo_mensagem, String lingua, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub
		String ind_lingua;
			
		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices		
		if(lingua.equals("ingles"))
			ind_lingua = "en";
		else
			ind_lingua = "pt";

		ArrayList<String> messages_list = new ArrayList<String>();
		
		//agregacao das mensagens provenientes do Twitter
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(tipo_mensagem);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("tipo_mensagem");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		//antes de fazer a agregação das mensagens provenientes da app m, giro para não ter mensagens a mais
		GereIndices.gereIndAppM();
		//agregacao das mensagens provenientes da aplicação móvel
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(tipo_mensagem);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("tipo_mensagem");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaPorFonte(String fonte_info, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub
		String directoria;

		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices
		if(fonte_info.equals("twitter"))
			directoria = "Indices_Lucene";
		
		else{
			directoria = "IndicesLuceneAppM";
			GereIndices.gereIndAppM();
		}
		
		ArrayList<String> messages_list = new ArrayList<String>();
		
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\" + directoria + "\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaPorLingua(String lingua, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub
		String ind_lingua;

		//ligar a forma como os dados sao guardados na BD com a forma como estão guardados nos índices		
		if(lingua.equals("ingles"))
			ind_lingua = "en";
		else
			ind_lingua = "pt";

		ArrayList<String> messages_list = new ArrayList<String>();
		
		//agregacao das mensagens provenientes do Twitter
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		//antes de fazer a agregação das mensagens provenientes da app m, giro para não ter mensagens a mais
		GereIndices.gereIndAppM();
		//agregacao das mensagens provenientes da aplicação móvel
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(ind_lingua);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("lingua");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaPorTipo(String tipo_mensagem, Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<String> messages_list = new ArrayList<String>();
		
		//agregacao das mensagens provenientes do Twitter
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(tipo_mensagem);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("tipo_mensagem");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		//antes de fazer a agregação das mensagens provenientes da app m, giro para não ter mensagens a mais
		GereIndices.gereIndAppM();
		//agregacao das mensagens provenientes da aplicação móvel
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				lista_strings.add(tipo_mensagem);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				lista_campos.add("tipo_mensagem");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	public static void agregaTodos(Vector<String> vec_hashtags) throws Exception {
		// TODO Auto-generated method stub

		ArrayList<String> messages_list = new ArrayList<String>();
		
		//agregacao das mensagens provenientes do Twitter
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		//antes de fazer a agregação das mensagens provenientes da app m, giro para não ter mensagens a mais
		GereIndices.gereIndAppM();
		//agregacao das mensagens provenientes da aplicação móvel 
		for(int i = 0; i!= vec_hashtags.size(); i++){
			String hashtag = vec_hashtags.elementAt(i);
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
			//ver se o ficheiro deve ficar aqui
			File file = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag);
			Directory index = new SimpleFSDirectory(file);

			if(file.exists()){
				String[] array_strings; //array de strings que fica com as variaveis para o MultiFieldQueryParser
				String[] array_campos; //array de campos que fica com os fields para o MultiFieldQueryParser

				List<String> lista_strings = new ArrayList<String>();
				lista_strings.add(hashtag);
				array_strings = lista_strings.toArray(new String[lista_strings.size()]);

				List<String> lista_campos = new ArrayList<String>();
				lista_campos.add("hashtag");
				array_campos = lista_campos.toArray(new String[lista_campos.size()]);

				BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST};
				Query query = MultiFieldQueryParser.parse(Version.LUCENE_34, array_strings, array_campos, flags,analyzer);

				//nº de mensagens a visualizar por hashtag: ver as últimas 50 mensagens (PARA JA)
				int hitsPerPage = 50;
				IndexSearcher searcher = new IndexSearcher(index,true);
				TopFieldCollector collector = TopFieldCollector.create(Sort.INDEXORDER, hitsPerPage, false, false, false, false);
				searcher.search(query, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				if(hits.length > 0){
					System.out.println("Found " + hits.length + " hits.");
					for(int j=0;j<hits.length;j++) {
						int docId = hits[j].doc;
						Document d = searcher.doc(docId);
						//imagem, nome, mensagem, data, hora e fonte
						if(!d.get("mensagem").contains("\"") && !d.get("mensagem").contains("\n")){
							messages_list.add("{\"data\":\"" + d.get("data_amd_formatada") + "\",\"hora\":\"" + d.get("data_hm_formatada") + "\"," + 
									"\"image\":\"" + d.get("imagem") + "\",\"name\":\"" + d.get("utilizador") + "\",\"fonte\":\"" + d.get("fonte") +  
									"\",\"message\":\"" + d.get("mensagem") + "\"}");
						}
					}
					searcher.close();
				}
				else
					System.out.println("Nao ha mensagens");
				searcher.close();
			}
		}
		
		removeDupsCriaFich(messages_list);
	}

	//metodo que remove duplicados, organiza/ordena as mensagens e cria o ficheiro JSON
	private static void removeDupsCriaFich(ArrayList<String> messages_list) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		//remover duplicados antes da ordenacao e criacao do ficheiro
		ArrayList<String> messages_newlist = new ArrayList<String>();
		for(int i = 0; i != messages_list.size(); i++){
			int j = i+1;
			w_loop:
				while(j!=messages_list.size()){
					boolean duplicado = removeDuplicados(messages_list.get(i), messages_list.get(j));
					if(duplicado){
						break w_loop;
					}
					else if(j == messages_list.size()-1)
						messages_newlist.add(messages_list.get(i));
					j++;
				}
		}

		//inserir a ultima posicao
		int last_index = messages_list.size()-1;
		messages_newlist.add(messages_list.get(last_index));

		//ordenacao
		Object[] array_mensagens = new String[]{};
		array_mensagens = messages_newlist.toArray();
		Arrays.sort(array_mensagens);

		//JSON---------------------------------------
		//criacao do json
		
		//aqui vai ter de ler o ultimo ficheiro criado, criar o proximo e actualizar o id
		AgregaDadosVisualizacao agregadados = new AgregaDadosVisualizacao();
		
		//le o ultimo id json criado
		int id_json = agregadados.devolveAntigoJSON() + 1;
		
		//cria o proximo ficheiro json
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\Mensagens\\Util1Msg" + id_json + ".json");
		file.createNewFile();
		FileWriter fstream = new FileWriter(file,true);
		BufferedWriter fbw = new BufferedWriter(fstream);	
		for(int i = array_mensagens.length-1; i != 0; i--){
			fbw.write((String)array_mensagens[i]);
			fbw.newLine();
		}
		fbw.close();

		//actualiza o id do ultimo json criado
		agregadados.criaIdJSON(id_json);
	}

	//vai buscar o id do ultimo ficheiro json gerado para o utilizador
	private synchronized int devolveAntigoJSON() throws InterruptedException{
		// TODO Auto-generated method stub

		File ficheiro = new File("C:\\Workspace\\Ficheiros\\Mensagens\\JSONid.txt");
		int last_id = 1;
		if(ficheiro.exists()){
			FileReader fr;
			try {
				fr = new FileReader(ficheiro);
				LineNumberReader ln = new LineNumberReader(fr);
				last_id = Integer.parseInt(ln.readLine());
				return last_id;	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				wait(3000);
				devolveAntigoJSON();
			}
		}
		//se o ficheiro nao existir vai criar e colocar o id = 1 pq é o 1º JSON gerado
		else{
			criaIdJSON(last_id);
			//devolve 0 porque a criação do proximo json vai ser este + 1 e se o ficheiro ainda nao existe
			return 0;	
		}
		return last_id;
	}

	//ficheiro que guarda apenas o numero do ficheiro json com as ultimas infos de interesse
	private void criaIdJSON(int i) throws InterruptedException {
		// TODO Auto-generated method stub
		File ficheiro = new File("C:\\Workspace\\Ficheiros\\Mensagens\\JSONid.txt");
		String fich_id = Integer.toString(i);
		try{
			BufferedWriter bfwritter = new BufferedWriter(new FileWriter(ficheiro));
			bfwritter.write(fich_id);
			bfwritter.close();
		}
		catch (Exception e){
			wait(2000);
			criaIdJSON(i);
		}
	}
	
	// JSON --------------------------------------------------------------------------------

	private static boolean removeDuplicados(String ant_index, String sub_index) {
		// TODO Auto-generated method stub
		Jaccard j = new Jaccard();
		double e = 0;
		StringWrapper w = j.prepare(ant_index);
		StringWrapper v = j.prepare(sub_index);
		e = j.score(w, v);
		if(e>0.8)
			return true;
		else
			return false;
	}
}
