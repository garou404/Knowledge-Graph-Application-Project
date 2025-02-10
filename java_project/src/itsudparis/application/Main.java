/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package itsudparis.application;
import itsudparis.application.JenaSPARQLExecutor;
import java.io.File;
import java.util.Scanner;

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
		Scanner new_scanner = new Scanner(System.in);
		System.out.println("   _    ___   _      _____   __  __  ____      ______  ______ ");
		System.out.println("  / /  / __/ / \\    / ___/  / / / / / __/     / __  / / ____/ ");
		System.out.println(" / /_ / __/ / _ \\  / / ___ / /_/ / / __/     / /_/ / / ___/   ");
		System.out.println("/___//___/ /_/ \\_\\ \\__/_/ /_____/ /___/     /_____/ /_/       ");  

		System.out.println("   _    ___   _____   ____  __  __  _____   ______ ");
		System.out.println("  / /  / __/ / ___/  / __/ / / / / / __  | / ____/ ");
		System.out.println(" / /_ / __/ / / ___ / __/ /  \\/ / / /_/ / /___  /  ");
		System.out.println("/___//___/  \\__/_/ /___/ /_/\\ _/ /_____/ /_____/   ");
		System.out.println("\nWelcome to the League of Legends Interactive Encyclopedia!\n"
				+ "\n"
				+ "League of Legends (LoL) is a fast-paced, competitive Multiplayer Online Battle Arena \n(MOBA) game where two teams of five players face off on Summoner’s Rift. Each \nplayer controls a unique Champion, using strategy, teamwork, and skill to \ndestroy the enemy Nexus and claim victory.\n");
		while (true) {
			System.out.println("About which topic would you like to know?\n"
					+ "\n"
					+ "1 => Team – Learn about competitive teams, their rosters.\n"
					+ "2 => Player – Explore information on famous players, their teams, their champions.\n"
					+ "3 => Champion – Discover details about champions, their abilities, and lore.\n"
					+ "4 => Role – Understand different in-game roles like Top, Jungle, Mid, ADC, and Support.");
			
			System.out.println("\nType a number to begin your journey into the world of League of Legends!\n>");
			//System.out.println(executor.test());
			String topic_index = new_scanner.nextLine();
			switch (topic_index) {
			case "1": // TEAM - TEAM - TEAM - TEAM - TEAM - TEAM - TEAM - TEAM - TEAM
				System.out.println("Choose a question index among those listed below\n");
				System.out.println(
						"1 => List teams that exist\n" // V
						+ "2 => List different regions of teams\n" // V
						+ "3 => List the teams per region\n" // V
						+ "4 => List teams and their players\n" // V
						+ "5 => List the different nationalities in a team\n" // V
						+ "6 => List all players that play for a team\n" // V
						+ "7 => List every players that play for a team with year\n"); // V
				String team_question_index = new_scanner.nextLine();
				String team = "";
				String year = "";
				switch (team_question_index) {
				case "1":
	                System.out.println(executor.listTeams());
	                break;
	            case "2":
	                System.out.println(executor.listRegionsOfTeams());
	                break;
	            case "3":
	                System.out.println(executor.listTeamsPerRegion());
	                break;
	            case "4":
	                System.out.println(executor.listTeamsAndPlayers());
	                break;
	            case "5":
	            	System.out.println("type the name of a team: ");
	            	team = new_scanner.nextLine();
	                System.out.println(executor.getNationalitiesOfTeam(team));
	                break;
	            case "6":
	            	System.out.println("type the name of a team: ");
	            	team = new_scanner.nextLine();
	                System.out.println(executor.getPlayersOfTeamWithoutYear(team));
	                break;
	            case "7":
	            	System.out.println("type the name of a team: ");
	            	team = new_scanner.nextLine();
	            	System.out.println("\ntype a year (2024 or 2025)");
	            	year = new_scanner.nextLine();
	                System.out.println(executor.getPlayersOfTeamWithYear(team));
	                break;
	            default:
	                System.out.println("");
	        }
				break;
			case "2": // PLAYER - PLAYER - PLAYER - PLAYER - PLAYER - PLAYER - PLAYER - PLAYER
				System.out.println("Choose a question index among those listed below\n");
				System.out.println(
						"1 => List players and their team\n" // V
						+ "2 => List players of a region and their nationality\n" // V
						+ "3 => List champions mostly played by a player\n" // V
						+ "4 => List team of a player\n" // V
						+ "5 => List every team a player played for\n" // V
						+ "6 => List teammates of a player\n"
						+ "7 => List the nationality count of each player"); // V
				String player_question_index = new_scanner.nextLine();
				String player = "";
				switch (player_question_index) {
				case "1":
	                System.out.println(executor.listPlayersAndTeams());
	                break;
	            case "2":
	                System.out.println(executor.listPlayersByRegionAndNationality());
	                break;
	            case "3":
	            	System.out.println("type the name of a player: ");
	            	player = new_scanner.nextLine();
	                System.out.println(executor.getChampionsByPlayer(player));
	                break;
	            case "4":
	            	System.out.println("type the name of a player: ");
	            	player = new_scanner.nextLine();
	                System.out.println(executor.getTeammatesOfPlayer(player));
	                break;
	            case "5":
	            	System.out.println("type the name of a player: ");
	            	player = new_scanner.nextLine();
	                System.out.println(executor.listTeamsAPlayerPlayedFor(player));
	                break;
	            case "6":
	            	System.out.println("type the name of a player: ");
	            	player = new_scanner.nextLine();
	                System.out.println(executor.getTeammatesOfPlayer(player));
	                break;
	            case "7":
	            	System.out.println(executor.getNationalityCount());
	            	break;
	            default:
	                System.out.println("");
	        }
				break;
			case "3": // CHAMPION - CHAMPION - CHAMPION - CHAMPION - CHAMPION - CHAMPION
				System.out.println("Choose a question index among those listed below\n");
				System.out.println(
						"1 => List all champions and their stats\n" // V
						+ "2 => List all champions and their factions\n" // V
						+ "3 => List all champions and the players that play them\n");
				String champion_question_index = new_scanner.nextLine();
				switch (champion_question_index) {
				case "1":
	                System.out.println(executor.listChampionsAndStats());
	                break;
	            case "2":
	                System.out.println(executor.listChampionsAndFactions());
	                break;
	            case "3":
	                System.out.println(executor.listChampionsAndPlayers());
	                break;
	            default:
	                System.out.println("");
	        }
				break;
			case "4":// ROLE - ROLE - ROLE - ROLE - ROLE - ROLE - ROLE - ROLE
				System.out.println("Choose a question index among those listed below\n");
				System.out.println(
						"1 => List the five roles\n"
						+ "2 => List player that plays the role\n"
						+ "3 => List champions of a role\n");
				String role_question_index = new_scanner.nextLine();
				String role = "";
				switch (role_question_index) {
				case "1":
	                System.out.println(executor.listRoles());
	                break;
	            case "2":
	            	System.out.println("type a role: ");
	            	role = new_scanner.nextLine();
	                System.out.println(executor.listPlayersByRole(role));
	                break;
	            case "3":
	            	System.out.println("type a role: ");
	            	role = new_scanner.nextLine();
	                System.out.println(executor.listChampionsByRole(role));
	                break;
	            default:
	                System.out.println("");
	        }
				break;
			default:
				System.out.println("Sorry, wrong number\n");
			}
		}
	}
}


