import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class CorpusGenerator {
	private String movieTitlesFile;
	public CorpusGenerator(String movieTitlesFile){
		this.movieTitlesFile = movieTitlesFile;
	}
	
	
	public void getCorpusFromSite() throws FileNotFoundException, IOException, UnirestException{
		try (BufferedReader br = new BufferedReader(new FileReader(movieTitlesFile))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	HttpResponse<JsonNode> response = Unirest.get("https://metacritic-2.p.mashape.com/find/movie?title=" + URLEncoder.encode(line, "UTF-8"))
		    			.header("X-Mashape-Key", "4FL7skJkjVmshpALgWZEdJBjhkcTp1MKW5Jjsn2kO1yB5oX3dl")
		    			.header("Accept", "application/json")
		    			.asJson();
		    	//System.out.println(response.getRawBody());
		    }
		}
	}
	
	public static void main(String[] args){
		CorpusGenerator c = new CorpusGenerator("liste_films_12112015.txt");
		try {
			c.getCorpusFromSite();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}