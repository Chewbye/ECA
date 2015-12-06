import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Construit la fréquence de tous les mots d'une taille (> à 2 caractères) et appartenant à une classe en particulier
 * @author axel
 *
 */
public class FrequenceMot {
	
	public FrequenceMot (String classe, String pathArff, String pathFileJson)
	{
		HashMap<String,Integer> frequencesMots = new HashMap<String,Integer>();
		
		try {
			String contentFile = readFile(pathArff, Charset.defaultCharset());
			String data = getDataContent(contentFile);
			
			String[] dataSplit = data.split("\n");
			
			for (String critique : dataSplit)
			{
				String[] critiqueSplit = critique.split("',");
				
				if (critiqueSplit[critiqueSplit.length-1].equals(classe))
				{
					critiqueSplit[critiqueSplit.length-2] = critiqueSplit[critiqueSplit.length-2].substring(1);
					critiqueSplit[critiqueSplit.length-2] = critiqueSplit[critiqueSplit.length-2].replace("n\\'t", "not");
					String[] mots = critiqueSplit[critiqueSplit.length-2].split(" ");
					
					for (String mot : mots)
					{
						if (mot.length()>2)
						{
							if (frequencesMots.containsKey(mot))
							{
								frequencesMots.put(mot, frequencesMots.get(mot) + 1);
							}
							else
							{
								frequencesMots.put(mot, 0);
							}
						}
					}
				}
			}
			
			System.out.println("** "+classe+" **");
			
			// remove 0 value
			frequencesMots.values().removeAll(Collections.singleton(0));
			// sort by DESC
			frequencesMots = (HashMap<String, Integer>) sortByComparator(frequencesMots);
			
			JSONArray mapToJson = getJsonObject(frequencesMots);
			System.out.println(mapToJson);
			createJsonFile(mapToJson.toString(),pathFileJson);
			
			//System.out.println(frequencesMots);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	private static String getDataContent(String content)
	{
		String[] data = content.split("@data");
		return data[data.length-1];
	}
	
	/** sort hashmap **/
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	private static JSONArray getJsonObject(Map<String, Integer> map) {
		
		JSONArray json_array = new JSONArray();
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			JSONObject json_obj = new JSONObject();
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
            	json_obj.put("name", key);
            	json_obj.put("value", value);
                json_array.put(json_obj);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }                           
        }
		
		return json_array;
	}
	
	private static void createJsonFile(String content, String pathDestination)
	{
		FileWriter fw;
		try {
			
			File file = new File(pathDestination);
			 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} 
			
			fw = new FileWriter(file.getAbsoluteFile());
			//BufferedWriter bw = new BufferedWriter(fw);
			
			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			fos.write(content.getBytes("UTF-8"));
			
			fos.close();
			
			//bw.write(arffContent);
			//bw.close();
			
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
