package gio.sburbmod.chat;

import java.util.Map;

import gio.sburbmod.grist.EntityGristPickup;
import gio.sburbmod.playerdata.DataProvider;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandGrist extends SimpleCommand {

	public CommandGrist() {
		super();
		CMDNAME = "grist";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();

		if (world.isRemote) {
			System.out.println("Not processing on Client side");
		} else {

			// world.spawnEntity(new EntityGristPickup(world,
			// sender.getPosition().getX(),
			// sender.getPosition().getY() - 2,
			// sender.getPosition().getZ(),
			// 1,
			// 1));
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();

			if (args.length == 2) {
				player.getCapability(DataProvider.CAP, null).modGristValue(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
			
			Map<Integer, Integer> gristData = player.getCapability(DataProvider.CAP, null).getGristCollection();

			StringBuilder n = new StringBuilder();
			n.append(gristData.size() + " Known Items: ");
			for (Integer i : gristData.keySet()) {
				sender.sendMessage(new TextComponentTranslation("grist.name." + i).appendSibling(new TextComponentString(": " + gristData.get(i))));
			}
		}

	}

}
