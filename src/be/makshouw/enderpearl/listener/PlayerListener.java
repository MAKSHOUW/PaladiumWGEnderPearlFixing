package be.makshouw.enderpearl.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import be.makshouw.enderpearl.Enderpearl;

public class PlayerListener implements Listener {

	Enderpearl main;

	public PlayerListener(Enderpearl main) {
		this.main = main;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();

		if (e.getCause() != TeleportCause.ENDER_PEARL || p == null)
			return;

		Location loc = p.getLocation();
		World w = loc.getWorld();

		ApplicableRegionSet set = main.getWorldGuard().getRegionManager(w).getApplicableRegions(loc);

		State state = set.getFlag(DefaultFlag.ENDERPEARL);
		if(state == null) return;
		if(state == State.DENY) {
			p.sendMessage("§cVous ne pouvez pas utiliser d'Enderpearl ici !");
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onUsing(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action action = e.getAction();
		
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack i = e.getItem();
			if(i == null) return;
			Material m = i.getType();
			if(m == null) return;
			ItemMeta iM = i.getItemMeta();
			if(iM == null) return;
			
			if(i.getType() == Material.ENDER_PEARL) {
				Location loc = p.getLocation();
				World w = loc.getWorld();

				ApplicableRegionSet set = main.getWorldGuard().getRegionManager(w).getApplicableRegions(loc);

				State state = set.getFlag(DefaultFlag.ENDERPEARL);
				if(state == null) return;
				if(state == State.DENY) {
					p.sendMessage("§cVous ne pouvez pas utiliser d'Enderpearl ici !");
					e.setCancelled(true);
				}
			}
		}
	}
}
