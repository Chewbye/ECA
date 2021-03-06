import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONObject;

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
		    	System.out.println(response.getBody().getObject().getJSONObject("result").get("url"));
		    	String url = response.getBody().getObject().getJSONObject("result").get("url").toString();
		    	
		    	HttpResponse<JsonNode> responseReview = Unirest.get("https://metacritic-2.p.mashape.com/reviews?url=" + url)
		    			.header("X-Mashape-Key", "4FL7skJkjVmshpALgWZEdJBjhkcTp1MKW5Jjsn2kO1yB5oX3dl")
		    			.header("Accept", "application/json")
		    			.asJson();
		    	System.out.println(responseReview.getBody().getObject());
		    	for(int i=0; i<Integer.parseInt(responseReview.getBody().getObject().get("count").toString()); i++){
		    		int score = Integer.parseInt(((JSONObject)responseReview.getBody().getObject().getJSONArray("results").get(i)).get("score").toString());
		    		
		    		String classe = "negative";
		    		if(score>=40 && score<=60)
		    			classe = "mixed";
		    		else if(score>60 && score<=100)
		    			classe = "positive";
		    		
		    		String nom = response.getBody().getObject().getJSONObject("result").get("name").toString();
		    		String nomFichier = nom.replace(":", "_");
		    		nomFichier = nomFichier.replace(" ", "_");
		    		nomFichier = nomFichier.replace(".", "_");
		    		
		    		File file = new File("corpus/" + classe + "/" + nomFichier + i +".txt");
		    		System.out.println(file.getAbsolutePath());
		    		// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(((JSONObject)responseReview.getBody().getObject().getJSONArray("results").get(i)).get("excerpt").toString());
					bw.close();
		    		//System.out.println(((JSONObject)responseReview.getBody().getObject().getJSONArray("results").get(i)).get("excerpt"));
		    	}
		    }
		}
	}
	
	public static void main(String[] args){
		/*CorpusGenerator c = new CorpusGenerator("liste_films_12112015.txt");
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
		}*/
	}
}
