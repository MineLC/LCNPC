package lc.npc;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class LCNPC extends JavaPlugin implements Listener {

    public LCNPC(){
        getServer().getPluginManager().registerEvents(this, this);
    }
    private static final LinkedHashMap<Integer, NPC> npcs = new LinkedHashMap<>();

    public static NPC createNPC(String name, Location location, List<String> hologram, EntityType e){
        NPC npc = new NPC(name, hologram, location, e);
        int lastKey = npcs.isEmpty() ? 0 : Collections.max(npcs.keySet());
        npcs.put(lastKey + 1, npc);
        return npc;
    }

    public static NPC getNPC(int id){
        return npcs.get(id);
    }

    public static LinkedHashMap<Integer, NPC> getNPCs() {
        return npcs;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        for (Map.Entry<Integer, NPC> entry : npcs.entrySet()) {
            Integer key = entry.getKey();
            NPC npc = entry.getValue();
            npc.getShoweds().remove(e.getPlayer());
        }
    }
}
