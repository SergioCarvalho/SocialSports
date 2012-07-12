import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class JSONPlayers
 */
public class JSONPlayers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSONPlayers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String callback = request.getParameter("callback");
		response.setContentType("text/javascript");
		//response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		/*
		JSONObject obj1 = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONObject obj3 = new JSONObject();
		JSONObject obj4 = new JSONObject();
		JSONObject obj5 = new JSONObject();
		JSONObject obj6 = new JSONObject();
		JSONObject obj7 = new JSONObject();
		JSONObject obj8 = new JSONObject();
		JSONObject obj9 = new JSONObject();
		JSONObject obj10 = new JSONObject();
		JSONObject obj11 = new JSONObject();
		
		JSONArray arr = new JSONArray();
		obj1.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1440079154/-5.jpg");
		obj1.put("name", "Aimar");
		obj1.put("message", "Aimar renova pelo grande #Benfica http://www.cmjornal.xl.pt/detalhe/noticias/ultima-hora/aimar-renova-por-mais-uma-epoca-com-o-benfica");
		obj1.put("data", "01/02/2012");
		obj1.put("hora", "19:15");
		obj1.put("fonte", "Twitter");
//		obj1.put("leaf", "true");

		obj2.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1505237653/bici_espn.jpg");
		obj2.put("name", "Witsel");
		obj2.put("message", "Witsel exibe classe no meio campo benfiquista #Benfica");
		obj2.put("data", "01/02/2012");
		obj2.put("hora", "18:18");
		obj2.put("fonte", "Social Sports");
//		obj2.put("leaf", "true");
		
		obj3.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/142515011/TwitPortugalIMG.jpg");
		obj3.put("name", "Nolito");
		obj3.put("message", "Nolito apesar de realizar grandes exibições parece não ter ainda conquista Jorge Jesus por completo #Benfica");
		obj3.put("data", "01/02/2012");
		obj3.put("hora", "17:15");
		obj3.put("fonte", "Twitter");
//		obj3.put("leaf", "true");

		obj4.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/80688327/benfica_twitter.jpg");
		obj4.put("name", "Garay");
		obj4.put("message", "Garay o central benfiquista iniciou a sua carreira de futebolista como avançado, passando pela posição de médio, sendo hoje em dia um central de nível mundial #Benfica");
		obj4.put("data", "31/01/2012");
		obj4.put("hora", "19:17");
		obj4.put("fonte", "Twitter");
//		obj4.put("leaf", "true");
		
		obj5.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1316027999/Logo_twiter.png");
		obj5.put("name", "Enzo");
		obj5.put("message", "Enzo deverá ter mais uma oportunidade no #Benfica");
		obj5.put("data", "31/01/2012");
		obj5.put("hora", "09:18");
		obj5.put("fonte", "Social Sports");
//		obj5.put("leaf", "true");
		
		obj7.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1440079154/-5.jpg");
		obj7.put("name", "Aimar");
		obj7.put("message", "Aimar rei das assistências #Benfica");
		obj7.put("data", "30/01/2012");
		obj7.put("hora", "16:34");
		obj7.put("fonte", "Twitter");
//		obj7.put("leaf", "true");

		obj8.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1505237653/bici_espn.jpg");
		obj8.put("name", "Witsel");
		obj8.put("message", "Witsel faz no mínimo três posições em campo, jogador muito versátil #Benfica");
		obj8.put("data", "30/01/2012");
		obj8.put("hora", "14:47");
		obj8.put("fonte", "Social Sports");
//		obj8.put("leaf", "true");
		
		obj9.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/142515011/TwitPortugalIMG.jpg");
		obj9.put("name", "Nolito");
		obj9.put("message", "Nolito nº9 do #Benfica");
		obj9.put("data", "30/01/2012");
		obj9.put("hora", "11:30");
		obj9.put("fonte", "Twitter");
//		obj9.put("leaf", "true");

		obj10.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/80688327/benfica_twitter.jpg");
		obj10.put("name", "Garay");
		obj10.put("message", "Garay fica com a camisola nº 24 no #Benfica");
		obj10.put("data", "30/01/2012");
		obj10.put("hora", "09:36");
		obj10.put("fonte", "Twitter");
//		obj10.put("leaf", "true");
		
		obj11.put("imagem", "https://twimg0-a.akamaihd.net/profile_images/1316027999/Logo_twiter.png");
		obj11.put("name", "Enzo");
		obj11.put("message", "Enzo dá dores de cabeça aos responsáveis benfiquistas #Benfica");
		obj11.put("data", "30/01/2012");
		obj11.put("hora", "09:18");
		obj11.put("fonte", "Social Sports");
//		obj11.put("leaf", "true");
		
		arr.add(obj1);
		arr.add(obj2);
		arr.add(obj3);
		arr.add(obj4);
		arr.add(obj5);
		arr.add(obj7);
		arr.add(obj8);
		arr.add(obj9);
		arr.add(obj10);
		arr.add(obj11);
		
		obj6.put("items", arr);
		*/
		
		RecolheDados recolhe_dados = new RecolheDados();
		JSONObject json_object = new JSONObject();
		JSONArray json_array = new JSONArray();

		json_array = recolhe_dados.devolveArrayJSON();

		json_object.put("items", json_array);
		out.print(callback + "(");
//		out.print(obj6);
		out.print(json_object);
		out.print(");");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

