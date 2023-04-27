package me.balbucio.links;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

import me.balbucio.links.obj.Link;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
    private static Main instance;
    private File file = new File("plugins/balbLinks", "config.yml");
    public static Configuration config;
    public static HashMap<String, Link> links = new HashMap<>();

    public void onEnable() {
        setInstance(this);
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new LinkCommand());
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("[BalbucioLinks] §2Ativado com sucesso!"));
    }

    @Override
    public void onLoad(){
        loadFiles();
        loadUrls();
    }

    public static Main getInstance() {
        return instance;
    }

    private static void setInstance(final Main instance) {
        Main.instance = instance;
    }

    private void loadFiles(){
        try {
            if (!file.exists()) {
                File folder = file.getParentFile();
                if (!folder.exists()) {
                    folder.mkdir();
                }
                Files.copy(this.getResourceAsStream("config.yml"), file.toPath());
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadUrls(){
        for(String key : config.getSection("links").getKeys()){
            String name = config.getString("links."+key+".Name");
            String url = config.getString("links."+key+".Url");
            String perm = config.getString("links."+key+".Permission");
            links.put(name, new Link(name, url, perm));
        }
    }

    public static void openURL(CommandSender sender, String url){
        if(links.containsKey(url)){
            Link link = links.get(url);
            TextComponent component = new TextComponent(config.getString("messages.openurl").replace("&", "§").replace("{linkName}", link.getName()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link.getUrl()));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(config.getString("messages.hoveropen").replace("&", "§"))));
            sender.sendMessage(component);
        } else{
            sender.sendMessage(config.getString("messages.notfound"));
        }
    }

    public static void list(CommandSender sender){
        sender.sendMessage(config.getString("messages.list").replace("&", "§"));
        for(Link l : links.values()){
            sender.sendMessage(config.getString("messages.listItem").replace("&", "§").replace("{linkName}", l.getName()).replace("{url}", l.getUrl()));
        }
        sender.sendMessage(config.getString("messages.use").replace("&", "§"));
    }
}
