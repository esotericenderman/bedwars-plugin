package dev.enderman.minecraft.plugins.games.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import dev.enderman.minecraft.plugins.games.bedwars.commands.BedwarsCommand;
import dev.enderman.minecraft.plugins.games.bedwars.event.listeners.ConnectionListener;
import dev.enderman.minecraft.plugins.games.bedwars.event.listeners.GameListener;
import dev.enderman.minecraft.plugins.games.bedwars.managers.ArenaManager;
import dev.enderman.minecraft.plugins.games.bedwars.types.Arena;
import dev.enderman.minecraft.plugins.games.bedwars.utility.ConfigurationUtility;

public final class BedwarsPlugin extends JavaPlugin {
	private ArenaManager arenaManager;

	@Override
	public void onEnable() {
		// Setting up the configuration must happen before setting up the arena manager,
		// as the arena manager relies heavily on the config.yml file.
		ConfigurationUtility.setUpConfig(this);

		arenaManager = new ArenaManager(this);

		final PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new ConnectionListener(this), this);
		pluginManager.registerEvents(new GameListener(this), this);

		new BedwarsCommand(this);
	}

	@Override
	public void onDisable() {
		if (arenaManager != null) {
			for (final Arena arena : arenaManager.getArenas()) {
				arena.getVillager().remove();
			}
		}
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}
}
