package net.rerenderreality.elitebyte.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import pw.prok.imagine.util.Array;

public class ExtendedBigBertha implements Runnable {
	
	Player player;
	RRRPMainClass plugin;
	List<String> botNames = Array.asList("Izar", "Izzy", "Chomsky", "my name is Hope.");
	String inputMsg, outputMsg;

	public ExtendedBigBertha(Player p, RRRPMainClass pl, String in) {
		player = p;
		plugin = pl;
		inputMsg = in;
	}

	String getStringDelimited(String source, String start, String end) {
		int startIdx = source.indexOf(start);
		int endIdx = source.indexOf(end, startIdx + start.length());
		if (startIdx < 0 || endIdx < 0) {
			return null;
		}
		return source.substring(startIdx + start.length(), endIdx);
	}

	public void run() {
		try {
			String botid = plugin.getConfig().getString("botid");// "b0dafd24ee35a477";
			if (botid == null) {
				botid = "b0dafd24ee35a477";
			}											
			String metadataKey = "Chatbot.custid";
			String urlStr = "http://www.pandorabots.com/pandora/talk-xml";
			String data = "botid=" + URLEncoder.encode(botid, "UTF-8")
					+ "&input=" + URLEncoder.encode(inputMsg, "UTF-8");

			if (player.hasMetadata(metadataKey)) {
				String custId = player.getMetadata("Chatbot.custid").get(0).asString();
				data += "&custid="
						+ URLEncoder.encode(custId, "UTF-8");
				plugin.getLogger().info("Using custid: " + custId);
			}

			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				plugin.getLogger().info(line);
				outputMsg = getStringDelimited(line, "<that>", "</that>");
				if (outputMsg == null) {
					break;
				}
				String custId = getStringDelimited(line, "custid=\"",
						"\"");
				player.setMetadata(metadataKey, new FixedMetadataValue(plugin, custId));
				String biggy = BigBertha.checkString(botNames, outputMsg);
				if (biggy != "") {
					//String msg = "";
					outputMsg = BigBertha.bb + " " + BigBertha.substituteName(outputMsg, BigBertha.checkString(botNames, outputMsg), false);
					Bukkit.broadcastMessage(outputMsg);
				} else Bukkit.broadcastMessage(BigBertha.bb + " " + outputMsg);
				break;
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			plugin.getLogger().info("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
    public String formatMessage(String msg) {
        return BigBertha.bb + " " + msg;
    }
}