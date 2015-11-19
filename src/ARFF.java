import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ARFF {

	public String  arffContent;
	
	public ARFF(String foldersPath, String targetPath){
		arffContent = "% Ceci represente la classification de critiques de films en fonction de leur jugement.";
		arffContent += "\n\n@relation critiques";
		arffContent += "\n@attribute contenu string";
		arffContent += "\n@attribute classe {positive, mixed, negative}";
		arffContent += "\n\n@data";
		
		File file = new File("");
		ArrayList<String> folderNames = new ArrayList<String>();
		folderNames.add("positive");
		folderNames.add("mixed");
		folderNames.add("negative");
		
		for(String folderName: folderNames){
			File[] files= getListOfFiles(foldersPath + "\\" + folderName);
			for(File f: files){
				try {
					//Path p = Paths.get(filePath);
					String fileName = f.getAbsolutePath();
					String fileContent = readFile(fileName, Charset.defaultCharset());
					//System.out.println(Charset.defaultCharset());
					arffContent += "\n\'" + fileContent.replace("\'", "\\'").replace("’", "\\'").replace("\n", "").replace("“", "").replace("”", "").replace("\"", "") 
								+ "\'," + folderName;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		FileWriter fw;
		try {
			
			file = new File(targetPath);
			 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} 
			
			fw = new FileWriter(file.getAbsoluteFile());
			//BufferedWriter bw = new BufferedWriter(fw);
			
			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			fos.write(arffContent.getBytes("UTF-8"));
			
			fos.close();
			
			//bw.write(arffContent);
			//bw.close();
			
			System.out.println("Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public static File[] getListOfFiles(String folderPath){
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		System.out.println(folderPath);
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }	
	    
	    return listOfFiles;
	}
}
