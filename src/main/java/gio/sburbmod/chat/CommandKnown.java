package gio.sburbmod.chat;

import java.util.Set;

import gio.sburbmod.playerdata.DataProvider;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandKnown extends SimpleCommand {
	
	public CommandKnown() {
		super();
		CMDNAME = "known";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();

		if (world.isRemote) {
			System.out.println("Not processing on Client side");
		} else {
			System.out.println("Processing on Server side");
			sender.sendMessage(new TextComponentTranslation("command.sayhello"));
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			Set<String> knownItems = player.getCapability(DataProvider.CAP, null).getKnownItems();

			StringBuilder n = new StringBuilder();
			n.append(knownItems.size() + " Known Items: ");
			for (String i : knownItems) {
				n.append(", " + i);
			}
			sender.sendMessage(new TextComponentString(n.toString()));
		}

	}
	

}
