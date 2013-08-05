package com.minecraftdimensions.bungeesuitechat.objects;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;

public class ServerData {
	static String serverName;
	static String shortName;
	static String forcedChannel;
	static boolean forceChannel;
	static boolean usingFactionChannels;
	static int localDistance;
	static String adminColor;
	
	
	public ServerData(String name, String shortNam, boolean force, String channel, boolean facs, int localDis, String adminCol){
		serverName = name;
		shortName = shortNam;
		forceChannel = force;
		forcedChannel = channel;
		usingFactionChannels = facs;
		if(usingFactionChannels){
			BungeeSuiteChat.instance.setupFactions();
		}
		localDistance = localDis;
		adminColor = adminCol;
	}
	
	public static void deserialise(String deserialise){
		String data [] = deserialise.split("~");
		serverName = data[0];
		shortName = data[1];
		forceChannel = Boolean.parseBoolean(data[2]);
		forcedChannel = data[3];
		usingFactionChannels = Boolean.parseBoolean(data[4]);
		localDistance = Integer.parseInt(data[5]);
		adminColor = data[6];
	}
	
	public static String getServerName(){
		return serverName;
	}
	
	public static String getServerShortName(){
		return shortName;
	}
	
	public static String getForcedChannel(){
		return forcedChannel;
	}
	
	public static boolean forcingChannel(){
		return forceChannel;
	}
	
	public static boolean usingFactions(){
		return usingFactionChannels;
	}
	public static int getLocalDistance(){
		return localDistance;
	}
	
	public static String getAdminColor(){
		return adminColor;
	}

	public static String serialise() {
		return serverName+"~"+shortName+"~"+forceChannel+"~"+forcedChannel+"~"+usingFactionChannels+"~"+localDistance+"~"+adminColor;
	}
}
