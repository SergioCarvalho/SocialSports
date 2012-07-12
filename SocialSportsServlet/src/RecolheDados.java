import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class RecolheDados {

	public JSONArray devolveArrayJSON() throws IOException {
		// TODO Auto-generated method stub
		JSONArray json_array = new JSONArray();

		int i = devolveIdMsg();
		File file = new File("C:\\Workspace\\SocialSports\\Ficheiros\\Mensagens\\Util1Msg" + i + ".json");
		try {
			InputStream in = new FileInputStream(file);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = bfr.readLine()) != null){
				String jsontext = line;
				JSONObject json = (JSONObject) JSONSerializer.toJSON(jsontext);
				JSONObject json_obj = new JSONObject();
				json_obj.put("imagem", json.getString("image"));
				json_obj.put("name", json.getString("name"));
				json_obj.put("message", json.getString("message"));
				json_obj.put("data", json.getString("data"));
				json_obj.put("hora", json.getString("hora"));
				json_obj.put("fonte", json.getString("fonte"));

				json_array.add(json_obj);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json_array;
	}

	private int devolveIdMsg() {
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
				return last_id;
			}
		}	
		else	
			return last_id;
	}
}
