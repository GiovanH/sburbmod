package gio.sburbmod.chat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public abstract class SimpleCommand implements ICommand{
	private final List<String> aliases;
	protected String CMDNAME;

	public SimpleCommand() {
		aliases = new ArrayList<String>();
		aliases.add(CMDNAME);
	}

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return CMDNAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + CMDNAME;
	}

	@Override
	public List<String> getAliases() {
		return this.aliases;
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}
}
