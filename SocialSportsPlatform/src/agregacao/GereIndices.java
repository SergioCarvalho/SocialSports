package agregacao;

import indexacao.HashEquipa;
import indexacao.HashEvento;
import indexacao.HashJogador;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class GereIndices {

	//os índices (Twitter) são mantidos apenas por iteração para gerar o json de interesse do utilizador
	//no final de cada iteracção apago os índices para os próximos terem apenas a info mais recente

	//este método apaga os índices (Twitter) criados para todas as hashtags (equipas, jogadores e eventos)
	public static void renovaIndices() {
		// TODO Auto-generated method stub
		Vector<String> vector_hashtags = devolveTodasHashtags();

		for(int i= 0; i!= vector_hashtags.size(); i++){
			File dir = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + vector_hashtags.get(i));
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (int j=0; j<children.length; j++) {
					deleteDir(new File(dir, children[j]));	
				}
			}
			// Depois de apagar toda a sub hierarquia posso apagar a directoria principal
			dir.delete();
		}	
	}

	//método que faz a remoção das directorias e subdirectorias
	private static void deleteDir(File file) {
		// TODO Auto-generated method stub
		file.delete();
	}

	//método que verifica o nº de documentos(msg) associadas a cada hashtag dos índices da app móvel
	//como pretendo devolver apenas as últimas x mensagens, vou apagar as mensagens em excesso
	public static void gereIndAppM() throws IOException, ParseException {
		// TODO Auto-generated method stub

		//total das mensagens provenientes da app móvel a retornar para gerar o ficheiro json
		int total_msg = 100;
		
		//vou buscar o conjunto de hashtags associadas a equipas, jogadores e eventos
		Vector<String> vector_hashtags = devolveTodasHashtags();
		for(String hashtag: vector_hashtags){
			File ficheiro = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag);
			//se o ficheiro para a hashtag existir verifico o nº de mensagens
			if(ficheiro.exists()){
				//vou buscar o nº de mensagens/documentos associados a essa hashtag
				int num_docs = devolveNumDocs(ficheiro);
				//se passar o limite de mensagens a retornar guardo o total de mensagens a apagar
				if(num_docs>total_msg){
					int tot_apagar = difMensagens(total_msg, num_docs);
					apagaMensagens(ficheiro, hashtag, tot_apagar);
				}
			}
		}
	}

	private static void apagaMensagens(File ficheiro, String hashtag, int tot_apagar) throws IOException, ParseException {
		// TODO Auto-generated method stub
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);
		Directory index = new SimpleFSDirectory(ficheiro);
		int hitsPerPage = tot_apagar;
		IndexSearcher searcher = new IndexSearcher(index, true);

		String querystr = hashtag.substring(hashtag.indexOf(1), hashtag.length());
		Query q = new QueryParser(Version.LUCENE_33, "hashtag", analyzer).parse(querystr);
		
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true); 
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
	
		//System.out.println("Found " + hits.length + " hits.");
		
		//vector que fica com os ids das mensagens a apagar
		Vector<String> vec_ids = new Vector<String>();
		for(int i=0;i!=hitsPerPage;++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			vec_ids.add(d.get("id"));
		}
		searcher.close();
		
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_33, analyzer);
		IndexWriter w = new IndexWriter(index, iwc);
		
		//apago, por id, o nº de mensagens de maneira a guardar apenas as necessárias
		for(int i = 0; i!= vec_ids.size(); i++){
			String queryid = vec_ids.get(i);
			Query query = new QueryParser(Version.LUCENE_33, "id", analyzer).parse(queryid);
			
			w.deleteDocuments(query);		

			w.commit();
			w.optimize();
		}
		w.close();
	}

	//método que devolve o num de mensagens necessárias apagar nos índices
	private static int difMensagens(int total_msg, int num_docs) {
		// TODO Auto-generated method stub
		int dif_msg;
		dif_msg = num_docs - total_msg;
		return dif_msg;
	}

	//método que devolve o nº de msg/docs associados a uma hashtag
	private static int devolveNumDocs(File ficheiro) throws IOException {
		// TODO Auto-generated method stub
		Directory index = new SimpleFSDirectory(ficheiro);
		IndexReader reader = IndexReader.open(index);

		int ndocs = reader.numDocs();

		reader.close();

		return ndocs;
	}

	//método que devolve todas as hashtags associadas a equipas, jogadores e eventos
	public static Vector<String> devolveTodasHashtags(){
		//vector que fica com o conjunto de hashtags
		Vector<String> vector_hashtags = new Vector<String>();

		//vector hashtags de equipas
		Vector<String> vec_hashequipas = HashEquipa.devolveHashEquipasBD();
		//vector hashtags de jogadores
		Vector<String> vec_hashjogadores = HashJogador.devolveHashJogadoresBD();
		//vector hashtags de eventos
		Vector<String> vec_hasheventos = HashEvento.devolveHashEventosBD();

		vector_hashtags.addAll(vec_hashequipas);
		vector_hashtags.addAll(vec_hashjogadores);
		vector_hashtags.addAll(vec_hasheventos);

		return vector_hashtags;
	}
}
