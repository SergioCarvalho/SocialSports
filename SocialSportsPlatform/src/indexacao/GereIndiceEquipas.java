package indexacao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import categorizacao.*;

public class GereIndiceEquipas {

	public static void InsereMensagemEquipa(String hashtag, String utilizador, String imagem, String mensagem,
			String data_criacao, String lingua, String tipo_mensagem, String fonte) throws IOException{

		String data_ano_mes_dia = FerramentasData.devolveDataAnoMesDia(data_criacao);
		String data_hora_minuto = FerramentasData.devolveDataHoraMinuto(data_criacao);
		String data_amd_formatada = FerramentasData.devolveDataAnoMesDiaF(data_criacao);
		String data_hm_formatada = FerramentasData.devolveDataHoraMinutoF(data_criacao);

		adicionaMensagemEquipaIndice(hashtag, utilizador, imagem, mensagem, data_ano_mes_dia, data_hora_minuto,
				data_amd_formatada, data_hm_formatada, lingua, tipo_mensagem, fonte);

	}

	private static void adicionaMensagemEquipaIndice(String hashtag,
			String utilizador, String imagem, String mensagem,
			String data_ano_mes_dia, String data_hora_minuto,
			String data_amd_formatada, String data_hm_formatada, String lingua,
			String tipo_mensagem, String fonte) throws IOException {
		// TODO Auto-generated method stub

/*
		//PARA ESCREVER OS FICHEIROS DEPOIS TENHO DE TIRAR ISTO DAQUI!!!!!
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\Hashtags\\" + hashtag + ".txt");
		FileWriter fwriter;
		if(!file.exists())
			fwriter = new FileWriter(file, false);
		else
			fwriter = new FileWriter(file, true);
		
		BufferedWriter out = new BufferedWriter(fwriter);
		out.write("Mensagem: " + mensagem + "; Língua: " + lingua + "; Tipo Mensagem: " + tipo_mensagem);
		out.newLine();
		out.close();
*/
	
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
		File file = new File("C:\\Workspace\\SocialSports\\Indices_Lucene\\" + hashtag);
		Directory index = new SimpleFSDirectory(file);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_34, analyzer);
		if(!file.exists()){
			iwc.setOpenMode(OpenMode.CREATE);
		}
		else{
			iwc.setOpenMode(OpenMode.APPEND);
		}
		IndexWriter index_writer = new IndexWriter(index, iwc);

//		Um campo indexado "INDEX.ANALYZED" significa que é pesquisável ou 
//		que podem ser feitas pesquisas com base nesse campo.		

//		Um campo guardado "STORE.YES" (no índice) significa que o seu conteúdo pode ser visto 
//		como resultado de pesquisas

		Document doc = new Document();
		doc.add(new Field("hashtag", hashtag, Field.Store.YES, Index.ANALYZED));
		doc.add(new Field("utilizador", utilizador, Field.Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("imagem", imagem, Field.Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("mensagem", mensagem, Field.Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("data_ano_mes_dia", data_ano_mes_dia, Field.Store.YES, Index.ANALYZED));
		doc.add(new Field("data_hora_minuto", data_hora_minuto, Field.Store.YES, Index.ANALYZED));
		doc.add(new Field("data_amd_formatada", data_amd_formatada, Field.Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("data_hm_formatada", data_hm_formatada, Field.Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("lingua", lingua, Field.Store.YES, Index.ANALYZED));
		doc.add(new Field("tipo_mensagem", tipo_mensagem, Field.Store.YES, Index.ANALYZED));
		doc.add(new Field("fonte", fonte, Field.Store.YES, Index.ANALYZED));

		index_writer.addDocument(doc);
		index_writer.optimize();
		index_writer.close();

	 
}

public static void InsereMensagemEquipaAppM(String id, String hashtag_equipa,String utilizador, 
		String imagem, String mensagem,String data_criacao, String lingua, 
		String tipo_mensagem, String fonte) throws IOException {
	// TODO Auto-generated method stub
	String data_ano_mes_dia = FerramentasData.devolveDataAnoMesDia(data_criacao);
	String data_hora_minuto = FerramentasData.devolveDataHoraMinuto(data_criacao);
	String data_amd_formatada = FerramentasData.devolveDataAnoMesDiaF(data_criacao);
	String data_hm_formatada = FerramentasData.devolveDataHoraMinutoF(data_criacao);

	adicionaMensagemEquipaAppMIndice(id, hashtag_equipa, utilizador, imagem, mensagem, data_ano_mes_dia, data_hora_minuto,
			data_amd_formatada, data_hm_formatada, lingua, tipo_mensagem, fonte);

}

private static void adicionaMensagemEquipaAppMIndice(String id, String hashtag_equipa, String utilizador, 
		String imagem, String mensagem, String data_ano_mes_dia, String data_hora_minuto,
		String data_amd_formatada, String data_hm_formatada, String lingua,
		String tipo_mensagem, String fonte) throws IOException {
	// TODO Auto-generated method stub
	StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);
	File file = new File("C:\\Workspace\\SocialSports\\Indices_LuceneAppM\\" + hashtag_equipa);
	Directory index = new SimpleFSDirectory(file);
	IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_34, analyzer);
	if(!file.exists()){
		iwc.setOpenMode(OpenMode.CREATE);
	}
	else{
		iwc.setOpenMode(OpenMode.APPEND);
	}
	IndexWriter index_writer = new IndexWriter(index, iwc);

	//		Um campo indexado "INDEX.ANALYZED" significa que é pesquisável ou 
	//		que podem ser feitas pesquisas com base nesse campo.		

	//		Um campo guardado "STORE.YES" (no índice) significa que o seu conteúdo pode ser visto 
	//		como resultado de pesquisas

	Document doc = new Document();
	doc.add(new Field("id", id, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("hashtag", hashtag_equipa, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("utilizador", utilizador, Field.Store.YES, Index.NOT_ANALYZED));
	doc.add(new Field("imagem", imagem, Field.Store.YES, Index.NOT_ANALYZED));
	doc.add(new Field("mensagem", mensagem, Field.Store.YES, Index.NOT_ANALYZED));
	doc.add(new Field("data_ano_mes_dia", data_ano_mes_dia, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("data_hora_minuto", data_hora_minuto, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("data_amd_formatada", data_amd_formatada, Field.Store.YES, Index.NOT_ANALYZED));
	doc.add(new Field("data_hm_formatada", data_hm_formatada, Field.Store.YES, Index.NOT_ANALYZED));
	doc.add(new Field("lingua", lingua, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("tipo_mensagem", tipo_mensagem, Field.Store.YES, Index.ANALYZED));
	doc.add(new Field("fonte", fonte, Field.Store.YES, Index.ANALYZED));

	index_writer.addDocument(doc);
	index_writer.optimize();
	index_writer.close();
}
}
