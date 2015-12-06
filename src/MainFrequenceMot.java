
public class MainFrequenceMot {

	public static void main(String[] args) {
		
		FrequenceMot fm = new FrequenceMot("negative", "/home/axel/Bureau/ProjetExtractionConn/ECA/lemmatisation/resultLemmeAdjectifsAdverbesVerbes.arff","/home/axel/Bureau/ProjetExtractionConn/ECA/Visualisation/FrequenceMot/negative.json");
		FrequenceMot fm1 = new FrequenceMot("positive", "/home/axel/Bureau/ProjetExtractionConn/ECA/lemmatisation/resultLemmeAdjectifsAdverbesVerbes.arff","/home/axel/Bureau/ProjetExtractionConn/ECA/Visualisation/FrequenceMot/positive.json");
		FrequenceMot fm2 = new FrequenceMot("mixed", "/home/axel/Bureau/ProjetExtractionConn/ECA/lemmatisation/resultLemmeAdjectifsAdverbesVerbes.arff","/home/axel/Bureau/ProjetExtractionConn/ECA/Visualisation/FrequenceMot/mixed.json");

	}

}
