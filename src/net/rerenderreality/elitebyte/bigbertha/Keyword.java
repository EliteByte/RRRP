package net.rerenderreality.elitebyte.bigbertha;

import java.util.List;

import pw.prok.imagine.util.Array;

public class Keyword {
	;
	List<String> bbAliases = Array.asList("bb ", "berty ", "biggy ", "bertha ",
			"biggie ", "bigbertha ", "big bertha ", "bb?", "berty?", "biggy?",
			"bertha?", "biggie?", "bigbertha?", "big bertha?");
	List<String> tps = Array.asList("tps", "ticks per second");
	List<String> feeling = Array.asList("feeling");
	List<String> cussWords = Array.asList("fuck", "fk", "ass", "arse", "dick",
			"dik", "asshole", "bastard", "bitch", "bollocks", "fuc", "fucker",
			"shit", "goddamn", "damn", "cunt", "kunt", "whore", "cock",
			"retard");
	List<String> liked = Array.asList("favorite", "fav", "loved", "like",
			"dearest");
	List<String> song = Array.asList("song", "music", "jam");
	List<String> loc = Array.asList("location", "dimension", "coords", "loc",
			"position", "world", "where is");
	List<String> kick = Array.asList("kick", "boot", "remove");
	List<String> mobs = Array.asList("mobs", "monsters", "entities",
			"fucken sacks of shit");
	List<String> butcher = Array.asList("butcher");
	List<String> kill = Array.asList("execute", "kill", "eliminate", "murder",
			"exterminate", "destroy", "obliterate", "void");
	List<String> ban = Array.asList("ban", "exile");
	List<String> pete = Array.asList("Peter");

	public boolean checkKeyWords(List<String> str1, List<String> str2,
			String msg) {
		String msgL = msg.toLowerCase();

		for (String s : str1) {
			if (msgL.contains(s.toLowerCase())) {
				if (str2 == null)
					return true;
				else
					for (String s2 : str2) {
						if (msgL.contains(s2.toLowerCase())) {
							return true;
						}
					}
			}
		}
		return false;
	}

	String beginsWith(List<String> bbAliases, String msgg) {
		for (String u : bbAliases) {
			if (msgg.startsWith(u)) {
				return u;
			}
		}

		return "";
	}

	String containsString(List<String> s, String msg) {
		for (String i : s) {
			if (msg.contains(i)) {
				return i;
			}
		}

		return "";
	}
}
