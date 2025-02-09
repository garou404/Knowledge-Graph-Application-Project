package itsudparis.application;

import org.apache.log4j.BasicConfigurator;

import com.hp.hpl.jena.rdf.model.Model;
import java.util.Scanner;
import itsudparis.tools.JenaEngine;


public class JenaSPARQLExecutor {
	private Model inferedModel;
    private String NS;
    private String prefix;

    public JenaSPARQLExecutor(String pathToData, String owlRuleFile, String ruleFile, String rdfFile) {
        // Load RDF model
    	Model model = JenaEngine.readModel(pathToData+rdfFile);
    	Model owlInferencedModel =
    			JenaEngine.readInferencedModelFromRuleFile(model, pathToData+owlRuleFile);    	
        // Apply inference rules
        this.inferedModel = 
        		JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel, pathToData+ruleFile);
      //NS = baseModel.getNsPrefixURI("");
        this.prefix = "PREFIX ns: <http://www.semanticweb.org/charles-m/ontologies/2025/1/untitled-ontology-22#>\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
    }

    public String executeQuery(String queryString) {
        return JenaEngine.executeQuery(this.inferedModel, this.prefix+queryString);
    }

    // Query: Get players of a champion
    public String getPlayersOfChampion(String champion) {
        String query = "SELECT ?champion ?player WHERE { " +
                       "?champion rdf:type ns:Champion . " +
                       "?champion ns:championName \"" + champion + "\" . " +
                       "?champion ns:isPlayedBy ?player . }";
        return executeQuery(query);
    }

    // Query: Get champions mostly played by a player
    public String getChampionsByPlayer(String player) {
        String query = "SELECT ?player ?champion WHERE { " +
                       "?player rdf:type ns:Player . " +
                       "?player ns:summonerName \"" + player + "\" . " +
                       "?player ns:playsMostly ?champion . }";
        return executeQuery(query);
    }

    // Query: Get team of a player
    public String getTeamOfPlayer(String player) {
        String query = "SELECT ?player ?team_name WHERE { " +
                       "?team rdf:type ns:Team . " +
                       "?team ns:hasRoster ?roster . " +
                       "?team ns:teamName ?team_name . " +
                       "?roster ns:rosterYear 2025 . " +
                       "?roster ns:hasPlayer ?player . " +
                       "?player ns:summonerName \"" + player + "\" . }";
        return executeQuery(query);
    }
    
    public String getTeammatesOfPlayer(String player) {
        String query = "SELECT ?player ?teammate_name WHERE { " +
                "?player rdf:type ns:Player . " +
                "?player ns:summonerName \"" + player + "\" . " +
                "?player ns:isTeammateOf ?teammate . " +
                "?teammate rdf:type ns:Player . " +
                "?teammate ns:summonerName ?teammate_name . }";
        return executeQuery(query);
    }

    public String getPlayersOfTeamWithYear(String team) {
        String query = "SELECT distinct ?player_name ?roster_year WHERE { " +
                "?player rdf:type ns:Player . " +
                "?player ns:summonerName ?player_name . " +
                "?player ns:playsInRoster ?roster . " +
                "?roster ns:rosterYear ?roster_year . " +
                "?roster ns:belongsToTeam ?team . " +
                "?team ns:teamName \"" + team + "\" . }";
        return executeQuery(query);
    }

    public String getPlayersOfTeamWithoutYear(String team) {
        String query = "SELECT distinct ?player_name WHERE { " +
                "?player rdf:type ns:Player . " +
                "?player ns:summonerName ?player_name . " +
                "?player ns:playsInRoster ?roster . " +
                "?roster ns:belongsToTeam ?team . " +
                "?team ns:teamName \"" + team + "\" . }";
        return executeQuery(query);
    }

    public String getChampionsOfFaction(String faction) {
        String query = "SELECT ?champion_name WHERE { " +
                "?champion rdf:type ns:Champion . " +
                "?champion ns:championName ?champion_name . " +
                "?champion ns:hasFaction ?faction . " +
                "?faction ns:factionName \"" + faction + "\" . }";
        return executeQuery(query);
    }

    public String getPlayersByRole(String role) {
        String query = "SELECT ?player_name WHERE { " +
                "?role rdf:type ns:Role . " +
                "?role ns:roleName \"" + role + "\" . " +
                "?role ns:isPrimaryRoleFor ?player . " +
                "?player ns:summonerName ?player_name . }";
        return executeQuery(query);
    }

    public String getNationalityCountByRegion() {
        String query = "SELECT ?nationa (COUNT(?player) AS ?count) WHERE { " +
                "?player rdf:type ns:Player . " +
                "?player ns:nationality ?nationa . } " +
                "GROUP BY ?nationa";
        return executeQuery(query);
    }

    public String getNationalitiesOfTeam(String team) {
        String query = "SELECT distinct ?natio WHERE { " +
                "?team a ns:Team ; " +
                "ns:hasRoster ?roster ; " +
                "ns:teamName \"" + team + "\" . " +
                "?roster ns:rosterYear 2025 ; " +
                "ns:hasPlayer ?player . " +
                "?player ns:nationality ?natio }";
        return executeQuery(query);
    }
}
