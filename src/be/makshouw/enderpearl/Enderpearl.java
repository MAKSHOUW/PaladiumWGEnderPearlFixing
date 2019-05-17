package be.makshouw.enderpearl;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import be.makshouw.enderpearl.listener.PlayerListener;

public class Enderpearl extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PluginManager pM = getServer().getPluginManager();
		pM.registerEvents(new PlayerListener(this), this);
	}
	
	/**
	 * sk89q demonstration api
	 */
	public WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

}
