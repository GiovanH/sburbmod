package gio.sburbmod.alchtable;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * This Network Message is sent from the client to the server, to tell it to
 * spawn projectiles at a particular location. Typical usage: PREQUISITES: have
 * previously setup SimpleNetworkWrapper, registered the message class and the
 * handler
 *
 * 1) User creates an AirStrikeMessageToServer(projectile, targetCoordinates) 2)
 * simpleNetworkWrapper.sendToServer(airstrikeMessageToServer); 3) network code
 * calls airstrikeMessageToServer.toBytes() to copy the message member variables
 * to a ByteBuffer, ready for sending ... bytes are sent over the network and
 * arrive at the server.... 4) network code creates AirStrikeMessageToServer()
 * 5) network code calls airstrikeMessageToServer.fromBytes() to read from the
 * ByteBuffer into the member variables 6) the
 * handler.onMessage(airStrikeMessageToServer) is called to process the message
 *
 * User: The Grey Ghost Date: 15/01/2015
 */
public class PacketMessageToServer implements IMessage {

	private long poke;
	private short type;
	private boolean messageIsValid;

	public boolean isMessageValid() {
		return messageIsValid;
	}

	// for use by the message handler only.
	public PacketMessageToServer() {
		messageIsValid = false;
	}

	public PacketMessageToServer(long b, short i) {
		messageIsValid = true;
		this.poke = b;
		this.type = i;
	}

	/**
	 * Called by the network code once it has received the message bytes over the
	 * network. Used to read the ByteBuf contents into your member variables
	 * 
	 * @param buf
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		poke = buf.readLong();
		type = buf.readShort();
		messageIsValid = true;
	}

	/**
	 * Called by the network code. Used to write the contents of your message member
	 * variables into the ByteBuf, ready for transmission over the network.
	 * 
	 * @param buf
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(poke);
		buf.writeShort(type);
		if (!messageIsValid)
			return;
	}

	public long getPos() {
		return poke;
	}
	public short getType() {
		return type;
	}
}
