package com.minecraftdimensions.bungeesuitechat.objects;


public class ServerData {
    static String serverName;
    static String shortName;
    static int localDistance;
    static boolean connectionMessages;
    static boolean usingFactionChannels;


    public ServerData( String name, String shortName, int localDistance, boolean connectionMessages ) {
        this.serverName = name;
        this.shortName = shortName;
        this.localDistance = localDistance;
        this.connectionMessages = connectionMessages;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getServerShortName() {
        return shortName;
    }

    public static boolean usingFactions() {
        return usingFactionChannels;
    }

    public static void useFactions() {
        usingFactionChannels = true;
    }

    public static int getLocalDistance() {
        return localDistance;
    }

    public static boolean usingConnectionMessages() {
        return connectionMessages;
    }
}
