package agregacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GeraFichConteudosApp {

	private static String ficheiro_origem = "C:\\Documents and Settings\\Sérgio\\Definições locais\\Application Data\\Google\\Chrome\\User Data\\Default\\Local Storage\\__0.localstorage";
	private static String ficheiro_destino = "C:\\Workspace\\SocialSports\\Ficheiros\\FavoritosPreferencias\\LocalStorageSS.txt";
	private static String ficheiro_mensagens = "C:\\Workspace\\SocialSports\\Ficheiros\\FavoritosPreferencias\\LocalStorageMSG.txt";

	public static File devolveFicheiroGlobal() throws IOException{
		
		CriaFicheiroLocal(ficheiro_origem, ficheiro_destino);

		System.out.println("Criou o ficheiro a analisar sem problemas");

		File ficheiro = new File(ficheiro_destino);

		File ficheiro_global = CriaFicheiroGlobal(ficheiro);
		
		return ficheiro_global;
	}


	private static File CriaFicheiroGlobal(File ficheiro) throws IOException {
		// TODO Auto-generated method stub
		
		//Não posso passar directamente para um ficheiro porque preciso de tratar os dados
		//Antes de os ter no ficheiro, por isso passo 1º para uma string e dps converto para ficheiro
		String file_to_string = devolveStrFicheiro(ficheiro);
		File string_to_file = devolveFicheiroStr(file_to_string);

		return string_to_file;
	}


	private static File devolveFicheiroStr(String s_file) {
		// TODO Auto-generated method stub
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\FavoritosPreferencias\\LocalStorageSS_final.txt");

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(int i = 0;i != s_file.length(); i++){
				if(Character.isDigit(s_file.charAt(i)) || Character.isLetter(s_file.charAt(i)) || isSymbol(s_file.charAt(i))){
					out.write(s_file.charAt(i));
				}
			}
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Erro a criar o ficheiro a partir da string");
		}

		return file;
	}

	//vou construir dois isSymbol porque para ler as mensagens provenientes da APP Móvel
	//tenho de preocupar-me com outras coisas/símbolos
	
	//este isSymbol serve para preferencias e favoritos
	private static boolean isSymbol(char c) {
		// TODO Auto-generated method stub
		if(c == '-' || c == ':'|| c == ','|| c == '@' || c == '.' || c =='#' ||
				c == '(' || c == ')')
			return true;
		return false;
	}

	private static String devolveStrFicheiro(File ficheiro) throws IOException {
		// TODO Auto-generated method stub
		StringBuffer stringDados = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(ficheiro));
		char[] chartos = new char[1024];
		int numRead=0;
		while((numRead=reader.read(chartos)) != -1){
			String readDados = String.valueOf(chartos,0,numRead);
			stringDados.append(readDados);
			chartos = new char[1024];
		}
		reader.close();

		return stringDados.toString();
	}


	private static void CriaFicheiroLocal(String src, String dest) throws IOException {
		// TODO Auto-generated method stub
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Não conseguiu encontrar o ficheiro");
		}
		try {
			out = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Não conseguiu criar o ficheiro de destino");
		}

		//transferência dos dados
		//int tamanho = 2048;
		byte[] buffer = new byte[(int) src.length()];

		int len;

		while((len = in.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
	}

	public static File devolveFicheiroMensagens() throws IOException {
		// TODO Auto-generated method stub
		CriaFicheiroLocalMensagens(ficheiro_origem, ficheiro_mensagens);

		System.out.println("Criou o ficheiro a analisar sem problemas");

		File ficheiro = new File(ficheiro_mensagens);

		File ficheiro_mensagens = CriaFicheiroMensagens(ficheiro);
		
		return ficheiro_mensagens;
	}


	private static File CriaFicheiroMensagens(File ficheiro) throws IOException {
		// TODO Auto-generated method stub
		
		//Não posso passar directamente para um ficheiro porque preciso de tratar os dados
		//Antes de os ter no ficheiro, por isso passo 1º para uma string e dps converto para ficheiro
		String file_to_string = devolveStrFicheiro(ficheiro);
		File string_to_file = devolveFicheiroStrMsg(file_to_string);

		return string_to_file;
	}


	private static File devolveFicheiroStrMsg(String s_file) {
		// TODO Auto-generated method stub
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\FavoritosPreferencias\\LocalStorageMSG_final.txt");

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(int i = 0;i != s_file.length(); i++){
				if(Character.isDigit(s_file.charAt(i)) || Character.isLetter(s_file.charAt(i)) || isSymbolMsg(s_file.charAt(i))){
					out.write(s_file.charAt(i));
				}
			}
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Erro a criar o ficheiro de mensagens a partir da string");
		}

		return file;
	}

	//este isSymbol serve em exclusivo para as mensagens e contém os espaços
	private static boolean isSymbolMsg(char c) {
		// TODO Auto-generated method stub
		if(c == '-' || c == ':'|| c == ','|| c == '@' || c == '.' || c =='#' ||	c == '(' 
			|| c == ')' || c == '!' || c == '?'|| c == ' ')
			return true;
		return false;
	}


	private static void CriaFicheiroLocalMensagens(String ficheiro_origem, String ficheiro_destino) throws IOException {
		// TODO Auto-generated method stub
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(ficheiro_origem);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Não conseguiu encontrar o ficheiro");
		}
		try {
			out = new FileOutputStream(ficheiro_destino);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Não conseguiu criar o ficheiro de destino de mensagens");
		}

		//transferência dos dados
		//int tamanho = 2048;
		byte[] buffer = new byte[(int) ficheiro_origem.length()];

		int len;

		while((len = in.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
		
	}
}
