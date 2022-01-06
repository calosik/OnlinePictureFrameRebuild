package com.cat.pictures.common.packet;

import java.util.ArrayList;

import com.cat.pictures.OPFrame;
import com.cat.pictures.common.packet.CreativeCorePacket;
import com.cat.pictures.common.packet.CreativeMessageHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketHandler {

	public static void sendPacketToAllPlayers(CreativeCorePacket packet)
	{
		OPFrame.network.sendToAll(new CreativeMessageHandler(packet));
	}
	
	public static void sendPacketToServer(CreativeCorePacket packet)
	{
		OPFrame.network.sendToServer(new CreativeMessageHandler(packet));
	}
	
	public static void sendPacketsToAllPlayers(ArrayList<CreativeCorePacket> packets)
	{
		for (int i = 0; i < packets.size(); i++) {
			sendPacketToAllPlayers(packets.get(i));
		}
	}
	
	public static void sendPacketToPlayer(CreativeCorePacket packet, EntityPlayerMP player)
	{
		OPFrame.network.sendTo(new CreativeMessageHandler(packet), player);
	}
	
}