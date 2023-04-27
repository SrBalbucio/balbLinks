package me.balbucio.links;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class LinkCommand extends Command{
	public LinkCommand() {
		super("link");
	}
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 0){
			Main.list(sender);
			return;
		}
		String arg = args[0];
		Main.openURL(sender, arg);
	}

}
