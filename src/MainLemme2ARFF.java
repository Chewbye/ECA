import java.io.File;


public class MainLemme2ARFF {
	
	public static void main(String[] args){
//		String projectPath = "/auto_home/llafon/Cours/semestre8/ECD/ECD/corpus";
//		ARFF arff = new ARFF(projectPath, projectPath + "/result.arff");
		
		String projectPath = "/auto_home/adepret/Bureau/ProjetECA/ECA/lemmatisation";
		Lemme2ARFF l2a = new Lemme2ARFF(projectPath,projectPath+"/resultLemme.arff");
	}
}

