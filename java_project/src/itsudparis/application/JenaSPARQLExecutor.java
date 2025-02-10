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
    	NS = model.getNsPrefixURI("");
    	Model owlInferencedModel =
    			JenaEngine.readInferencedModelFromRuleFile(model, pathToData+owlRuleFile);    	
        // Apply inference rules
        this.inferedModel = 
        		JenaEngine.readInferencedModelFromRuleFile(owlInferencedModel, pathToData+ruleFile);
      
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
                "?team ns:teamName \"" + team + "\" . }"
                		+ "\nORDER BY (?roster_year)";
        return executeQuery(query);
    }

    public String getPlayersOfTeamWithoutYear(String team) {
        String query = "SELECT distinct ?player_name WHERE { " +
                "?player rdf:type ns:Player . " +
                "?player ns:summonerName ?player_name . " +
                "?player ns:playsInRoster ?roster . " +
                "?roster ns:rosterYear 2025 . " +
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
    
    public String listTeams() {
        String query = "SELECT ?team_name WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:teamName ?team_name . }";
        return executeQuery(query);
    }

    public String listRegionsOfTeams() {
        String query = "SELECT DISTINCT ?region WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:region ?region . }";
        return executeQuery(query);
    }

    public String listTeamsPerRegion() {
        String query = "SELECT ?team_name ?region WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:teamName ?team_name . " +
                "?team ns:region ?region . }";
        return executeQuery(query);
    }

    public String listTeamsAndPlayers() {
        String query = "SELECT ?team_name ?player_name WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:teamName ?team_name . " +
                "?team ns:hasRoster ?roster . " +
                "?roster ns:rosterYear 2025 . " +
                "?roster ns:hasPlayer ?player . " +
                "?player ns:summonerName ?player_name . }";
        return executeQuery(query);
    }

    public String listPlayersAndTeams() {
        String query = "SELECT ?player_name ?team_name\n"
        		+ "WHERE {\n"
        		+ "	?team rdf:type ns:Team .\n"
        		+ "	?team ns:teamName ?team_name .\n"
        		+ "	?team ns:hasRoster ?roster .\n"
        		+ "	?roster ns:rosterYear 2025 .\n"
        		+ "	?roster ns:hasPlayer ?player .\n"
        		+ "	?player ns:summonerName ?player_name .\n"
        		+ "}";
        return executeQuery(query);
    }

    public String listPlayersByRegionAndNationality() {
        String query = "SELECT ?player_name ?natio ?region ?team_name WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:teamName ?team_name . " +
                "?team ns:region ?region . " +
                "?team ns:hasRoster ?roster . " +
                "?roster ns:rosterYear 2025 . " +
                "?roster ns:hasPlayer ?player . " +
                "?player ns:summonerName ?player_name ; " +
                "ns:nationality ?natio . }";
        return executeQuery(query);
    }

    public String listTeamsAPlayerPlayedFor(String player) {
        String query = "SELECT ?player ?team_name ?year WHERE { " +
                "?team rdf:type ns:Team . " +
                "?team ns:teamName ?team_name . " +
                "?team ns:hasRoster ?roster . " +
                "?roster ns:hasPlayer ?player . " +
                "?roster ns:rosterYear ?year . " +
                "?player ns:summonerName \"" + player + "\" . }";
        return executeQuery(query);
    }

    public String listChampionsAndStats() {
        String query = "SELECT ?champion_name ?HP ?attack_damage ?attack_speed ?armor ?magic_resist ?movement_speed ?resource_type WHERE { " +
                "?champion rdf:type ns:Champion ; " +
                "ns:championName ?champion_name ; " +
                "ns:healthPoints ?HP ; " +
                "ns:attackDamage ?attack_damage ; " +
                "ns:attackSpeed ?attack_speed ; " +
                "ns:armor ?armor ; " +
                "ns:magicResist ?magic_resist ; " +
                "ns:movementSpeed ?movement_speed ; " +
                "ns:resourceType ?resource_type . }";
        return executeQuery(query);
    }

    public String listChampionsAndFactions() {
        String query = "SELECT ?champion_name ?faction_name WHERE { " +
                "?champion rdf:type ns:Champion ; " +
                "ns:championName ?champion_name ; " +
                "ns:hasFaction ?faction . "+ 
                "?faction ns:factionName ?faction_name . }";
        return executeQuery(query);
    }

    public String listChampionsAndPlayers() {
        String query = "SELECT ?champion_name ?player WHERE { " +
                "?champion rdf:type ns:Champion ; " +
                "ns:championName ?champion_name ; " +
                "ns:isPlayedBy ?player . }";
        return executeQuery(query);
    }
    
    public String listRoles() {
        String query = "SELECT ?role_name WHERE { " +
                "?champion rdf:type ns:Role ; " +
                "ns:roleName ?role_name . }";
        return executeQuery(query);
    }

    public String listPlayersByRole(String role) {
        String query = "SELECT ?player_name WHERE { " +
                "?player rdf:type ns:Player ; " +
                "ns:summonerName ?player_name ; " +
                "ns:playsRole ?role . " +
                "?role ns:roleName \"" + role + "\" . }";
        return executeQuery(query);
    }

    public String listChampionsByRole(String role) {
        String query = "SELECT ?champion_name WHERE { " +
                "?champion rdf:type ns:Champion ; " +
                "ns:championName ?champion_name ; " +
                "ns:hasPrimaryRole ?role . " +
                "?role ns:roleName \"" + role + "\" . }";
        return executeQuery(query);
    }
    
    public String getNationalityCount() {
        String query = "SELECT ?nationality (COUNT(?player) AS ?count)\n"
        		+ "WHERE {\n"
        		+ "	?player rdf:type ns:Player .\n"
        		+ "	?player ns:nationality ?nationality .\n"
        		+ "}\n"
        		+ "GROUP BY ?nationality\n"
        		+ "ORDER BY DESC(?count)";
        return executeQuery(query);
    }

}
