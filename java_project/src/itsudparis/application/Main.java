/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itsudparis.application;
import itsudparis.application.JenaSPARQLExecutor;
import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.hp.hpl.jena.rdf.model.Model;
import itsudparis.tools.JenaEngine;

/**
 *
 * @author DO.ITSUDPARIS
 */
public class Main {
   

	public static void main(String[] args) {
		JenaSPARQLExecutor executor = new JenaSPARQLExecutor("../graph/", "owlrules.txt", "rule.txt", "knowledge_graph_project.rdf");
		System.out.println(executor.getChampionsByPlayer("Vladi"));
	}
}

