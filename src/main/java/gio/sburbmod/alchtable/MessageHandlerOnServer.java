package gio.sburbmod.alchtable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

/**
 * The MessageHandlerOnServer is used to process the network message once it has
 * arrived on the Server side. WARNING! In 1.8 onwards the MessageHandler now
 * runs in its own thread. This means that if your onMessage code calls any
 * vanilla objects, it may cause crashes or subtle problems that are hard to
 * reproduce. Your onMessage handler should create a task which is later
 * executed by the client or server thread as appropriate - see below. User: The
 * Grey Ghost Date: 15/01/2015
 */
public class MessageHandlerOnServer implements IMessageHandler<PacketMessageToServer, IMessage> {
	/**
	 * Called when a message is received of the appropriate type. CALLED BY THE
	 * NETWORK THREAD
	 * 
	 * @param message
	 *            The message
	 */
	public IMessage onMessage(final PacketMessageToServer message, MessageContext ctx) {
		if (ctx.side != Side.SERVER) {
			System.err.println("AirstrikeMessageToServer received on wrong side:" + ctx.side);
			return null;
		}
		if (!message.isMessageValid()) {
			System.err.println("AirstrikeMessageToServer was invalid" + message.toString());
			return null;
		}

		// we know for sure that this handler is only used on the server side, so it is
		// ok to assume
		// that the ctx handler is a serverhandler, and that WorldServer exists.
		// Packets received on the client side must be handled differently! See
		// MessageHandlerOnClient

		final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
		if (sendingPlayer == null) {
			System.err.println("EntityPlayerMP was null when AirstrikeMessageToServer was received");
			return null;
		}

		// This code creates a new task which will be executed by the server during the
		// next tick,
		// for example see MinecraftServer.updateTimeLightAndEntities(), just under
		// section
		// this.theProfiler.startSection("jobs");
		// In this case, the task is to call
		// messageHandlerOnServer.processMessage(message, sendingPlayer)
		final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
		playerWorldServer.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(message, sendingPlayer);
			}
		});

		return null;
	}

	// This message is called from the Server thread.
	// It spawns a random number of the given projectile at a position above the
	// target location
	void processMessage(PacketMessageToServer message, EntityPlayerMP sendingPlayer) {
		// first send a message to all clients to render a "target" effect on the ground
		// StartupCommon.simpleNetworkWrapper.sendToDimension(msg,
		// sendingPlayer.dimension); // DO NOT USE sendToDimension, it is buggy
		// as of build 1419 - see
		// https://github.com/MinecraftForge/MinecraftForge/issues/1908

		long l = message.getPos();
		BlockPos pos = BlockPos.fromLong(l);
		((TileTable)sendingPlayer.world.getTileEntity(pos)).doAlchemy(message.getType());

		return;
	}
}
