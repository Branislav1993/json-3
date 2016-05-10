package rs.fon.jgrass.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rs.fon.jgrass.domain.Member;

public class ParlamentApiCommunication {

	private static final String membersURL = "http://147.91.128.71:9090/parlament/api/members";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	public List<Member> getMembers() throws ParseException {
		try {
			String result = sendGet(membersURL);

			Gson gson = new GsonBuilder().create();
			JsonArray membersJson = gson.fromJson(result, JsonArray.class);

			LinkedList<Member> members = new LinkedList<Member>();

			for (int i = 0; i < membersJson.size(); i++) {
				JsonObject memberJson = (JsonObject) membersJson.get(i);

				Member m = new Member();
				m.setId(memberJson.get("id").getAsInt());
				m.setFirstName(memberJson.get("name").getAsString());
				m.setLastName(memberJson.get("lastName").getAsString());
				if (memberJson.get("birthDate") != null)
					m.setBirthDate(sdf.parse(memberJson.get("birthDate").getAsString()));

				members.add(m);
			}

			return members;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new LinkedList<Member>();
	}

	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		boolean endReading = false;
		String response = "";

		while (!endReading) {
			String s = in.readLine();

			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();

		return response.toString();
	}

}
