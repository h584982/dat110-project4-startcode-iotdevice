package no.hvl.dat110.aciotdevice.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.Gson;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {
		
		
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();
		AccessMessage access_message = new AccessMessage(message);
		Gson gson = new Gson();
		String temp = gson.toJson(access_message);
		RequestBody body = RequestBody.create(JSON, temp);
		Request request = new Request.Builder().url("http://localhost:8080/accessdevice/log/").post(body).build();

		System.out.println(request.toString());

		try (Response response = client.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		

	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {
		AccessCode code = null;
		try {
		URL url = new URL("http://localhost:8080/accessdevice/code");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		Gson gson = new Gson();
		code = gson.fromJson(content.toString(), AccessCode.class);
		}catch (Exception exception){
			
		}
		// TODO: implement a HTTP GET on the service to get current access code

		return code;
	}
}
