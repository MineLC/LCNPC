package lc.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class NPC {

    private String name;
    private final Set<ArmorStand> armorStands = new HashSet<>();
    private int entityID;
    private List<String> hologram;
    private Entity entity;
    private final Set<Player> showeds = new HashSet<>();
    private EntityType entityType;
    private Location location;
    private GameProfile gameProfile;

    public NPC(String name, List<String> hologram, Location location, EntityType entityType) {
        this.name = name;
        this.hologram = hologram;
        this.location = location;
        this.entityType = entityType;
    }

    public void spawn(Location location){
        if(entityType == EntityType.PLAYER){

            MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
            gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', name));
            EntityPlayer entityPlayer = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
            entityPlayer.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);
            entity = entityPlayer;
            entityID = entityPlayer.getId();
            for(Player bukkitPlayer : Bukkit.getOnlinePlayers()){
                if(!showeds.contains(bukkitPlayer)) {
                    ((CraftPlayer) bukkitPlayer).getHandle().playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);
                    showeds.add(bukkitPlayer);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ArmorStand> getArmorStands() {
        return armorStands;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public List<String> getHologram() {
        return hologram;
    }

    public void setHologram(List<String> hologram) {
        this.hologram = hologram;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Set<Player> getShoweds() {
        return showeds;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }
}
