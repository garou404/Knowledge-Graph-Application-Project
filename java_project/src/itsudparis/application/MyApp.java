package itsudparis.application;

import org.apache.log4j.BasicConfigurator;

import com.hp.hpl.jena.rdf.model.Model;
import java.util.Scanner;
import itsudparis.tools.JenaEngine;

public class MyApp {
	
	public static void main(String[] args) {
		//on indique le chemin vers jena-log4j.properties (pour supprimer les warnings)
		//String log4jConfPath = "/Users/amelbouzeghoub/Documents/Enseignement/ASR2020-2021/jena10/apache-jena-2.10.0/jena-log4j.properties";
		//PropertyConfigurator.configure(log4jConfPath);
		//fin de la config log4j
		String NS = "";
		// lire le model a partir d'une ontologie
		Model model = JenaEngine.readModel("data/ontologogie famille.rdf");
		if (model != null) {
		//lire le Namespace de l’ontologie
		NS = model.getNsPrefixURI("");
		
		// modifier le model
		// Ajouter une nouvelle femme dans le modele: Nora,50, estFilleDe Peter
		
		JenaEngine.createInstanceOfClass(model, NS, "Femme","Nora");
		JenaEngine.createInstanceOfClass(model, NS, "Personne","Nora");
		JenaEngine.updateValueOfDataTypeProperty(model, NS,"Nora", "age", 50);
		
		JenaEngine.updateValueOfObjectProperty(model, NS,"Nora", "estFilleDe", "Peter");
		//Ajouter un nouvel homme dans le modele: Rob, 51,seMarierAvec Nora
		JenaEngine.createInstanceOfClass(model, NS, "Homme","Rob");
		JenaEngine.updateValueOfDataTypeProperty(model, NS,"Rob", "age", 51);
		JenaEngine.updateValueOfDataTypeProperty(model, NS,"Rob", "nom", "Yeung");
//		JenaEngine.updateValueOfObjectProperty(model, NS,"Rob", "seMarierAvec", "Nora");
		//apply owl rules on the model
		Model owlInferencedModel =
		JenaEngine.readInferencedModelFromRuleFile(model, "data/owlrules.txt");
		// apply our rules on the owlInferencedModel
		Model inferedModel =
		JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel,"data/rules.txt");
		// query on the model after inference
		//System.out.println(JenaEngine.executeQueryFile(inferedModel,"/home/charles-m/Projects/eclipse-workplace/jena_lab2/src/data/query.txt"));
		Scanner new_scanner = new Scanner(System.in);
		
		String base = "PREFIX ns: <http://www.semanticweb.org/charles-m/ontologies/2025/0/untitled-ontology-5#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		while (true) {
			System.out.println("Type a name that is inside this list : ");
			System.out.println(JenaEngine.executeQuery(inferedModel,base+"SELECT ?name\n"
					+ "WHERE {\n"
					+ "	?pers ns:name ?name .\n"
					+ "}"));
			System.out.println("$: ");
			String name = new_scanner.nextLine();
			System.out.println(JenaEngine.executeQuery(inferedModel,base+"SELECT distinct ?name_parent ?sibling_name ?spouse_name ?spouse_age\n"
					+ "WHERE {\n"
					+ "?pers ns:name \"Paul\" .\n"
					+ "OPTIONAL {?pers ns:isChildOf ?parent } .\n"
					+ "OPTIONAL{ ?parent ns:isParentOf ?siblings } .\n"
					+ "OPTIONAL { ?parent ns:name ?name_parent } .\n"
					+ "OPTIONAL { ?siblings ns:name ?sibling_name } .\n"
					+ "OPTIONAL { ?pers ns:isMarriedWith ?spouse } .\n"
					+ "OPTIONAL { ?spouse ns:name ?spouse_name } .\n"
					+ "OPTIONAL { ?spouse ns:age ?spouse_age } .\n"
					+ "}"));
			System.out.println("you just typed : "+name);
			name = new_scanner.nextLine();
		}
		} else {
		System.out.println("Error when reading model from ontology");
		}
		
		 
	}
}
