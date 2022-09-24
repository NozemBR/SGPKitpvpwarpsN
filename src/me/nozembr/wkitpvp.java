//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.nozembr;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wkitpvp extends JavaPlugin implements Listener {
    public static FileConfiguration cf;

    public void onLoad() {
        cf = getConfig();
        cf.options().copyDefaults(true);
        saveConfig();
    }

    public Server server = Bukkit.getServer();

    private static wkitpvp instance;
    public FileConfiguration msgConfig;
    public static ArrayList<Player> Naknock;

    public static ArrayList<Player> Nafps;

    public static ArrayList<Player> Nalava;
    public static ArrayList<Player> Na1v1;

    private Map<Player, BukkitTask> tasks = new HashMap<>();

    private Map<Player, BukkitTask> tasks1 = new HashMap<>();

    private Map<Player, BukkitTask> tasks2 = new HashMap<>();
    public BukkitScheduler scheduler = Bukkit.getScheduler();

    private Location lava;
    private Location fps;
    private Location knock;

    private Location s1v1;

    private Location lobby;


    public void onEnable() {
        wkitpvp.Naknock = new ArrayList<Player>();
        wkitpvp.Nafps = new ArrayList<Player>();
        wkitpvp.Nalava = new ArrayList<Player>();
        wkitpvp.Na1v1 = new ArrayList<Player>();
        instance = this;
        saveDefaultConfig();
        msgConfig = get("messages.yml", new File(getDataFolder(), "messages.yml"));
        PluginDescriptionFile pdfFile = this.getDescription();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[KitPvPwarpsN]" + ChatColor.GREEN + " Ativado," + ChatColor.GREEN + " Versao: " + pdfFile.getVersion());
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[KitPvPwarpsN]" + ChatColor.RED + " Desativado");
    }

    private static File file;

    private static FileConfiguration config;

    public FileConfiguration get(String resourceName, File out) {
        try {
            InputStream in = getResource(resourceName);
            InputStreamReader inReader = new InputStreamReader(in);

            if (!out.exists()) {

                getDataFolder().mkdir();
                out.createNewFile();
            }
            FileConfiguration file = YamlConfiguration.loadConfiguration(out);

            if (in != null) {
                file.setDefaults(YamlConfiguration.loadConfiguration(inReader));
                file.options().copyDefaults(true);
                file.save(out);
            }
            return file;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        super.reloadConfig();
    }


    public static wkitpvp getPlugin() {
        return (wkitpvp) getPlugin((Class) wkitpvp.class);
    }

    public static ArrayList<String> teleportkitpvp = new ArrayList();

    public wkitpvp() {
    }

    private ItemStack getItem(ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore) {
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void cliqueinventario(InventoryClickEvent e) {

        if (!e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("menu-name")))) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if (slot == 0) {
            player.performCommand("fps");
        }
        if (slot == 1) {
            player.performCommand("lava");
        }
        if (slot == 2) {
            player.performCommand("knockback");
        }
        if (slot == 8) {
            player.performCommand("sair");
        }


        e.setCancelled(true);
    }


    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        //MENU
        if (command.getName().equalsIgnoreCase("wkmenu")) {

            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    Player p = (Player) sender;

                    Inventory inv = Bukkit.createInventory(p, 9 * 1, ChatColor.translateAlternateColorCodes('&', msgConfig.getString("menu-name")));

                    inv.setItem(0, getItem(new ItemStack(Material.DIAMOND_SWORD), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("fps-menu")), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("fps-lore"))));
                    inv.setItem(1, getItem(new ItemStack(Material.LAVA_BUCKET), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-menu")), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-lore"))));
                    inv.setItem(2, getItem(new ItemStack(Material.STICK), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("knock-menu")), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("knock-lore"))));
                    inv.setItem(8, getItem(new ItemStack(Material.REDSTONE_BLOCK), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-menu")), ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-lore"))));

                    p.openInventory(inv);
                }
            });
        }
        if (sender instanceof Player && !(sender instanceof ConsoleCommandSender)) {
            final Player p = (Player) sender;
            msgConfig = get("messages.yml", new File(getDataFolder(), "messages.yml"));
            if (command.getName().equalsIgnoreCase("wk")) {
                if (args.length == 0) {
                    sender.sendMessage("§b------- KitpvpWarpsN Plugin ---------");
                    sender.sendMessage(" ");
                    sender.sendMessage("§b- /sair: §aSaida das warps.");
                    sender.sendMessage(" ");
                    sender.sendMessage("- §b/fps: §aIr para warp FPS.");
                    sender.sendMessage(" ");
                    sender.sendMessage("- §b/lava: §aIr para warp lavachallenge.");
                    sender.sendMessage(" ");
                    sender.sendMessage("- §b/knockback: §aIr warp knockback.");
                    sender.sendMessage(" ");
                    sender.sendMessage("§b- /wkadmin: §cVer comandos de admin.");
                    sender.sendMessage(" ");
                    sender.sendMessage("§b------------------------------");
                } else {
                    BukkitScheduler scheduler;
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (p.hasPermission("KitPvPwarpsN.reload")) {
                            reload();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("reloaded")));
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("Permission")));
                        }
                    }
                }
            }
            if (command.getName().equalsIgnoreCase("fps")) {
                if (this.getConfig().contains("fps")) {
                    teleportkitpvp.add(p.getName());

                    scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            msgConfig = get("messages.yml", new File(getDataFolder(), "messages.yml"));
                            Player p1 = (Player) sender;
                            World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("fps.world"));
                            double x = wkitpvp.this.getConfig().getDouble("fps.x");
                            double y = wkitpvp.this.getConfig().getDouble("fps.y");
                            double z = wkitpvp.this.getConfig().getDouble("fps.z");
                            fps = new Location(w, x, y, z);
                            fps.setPitch((float) wkitpvp.this.getConfig().getDouble("fps.pitch"));
                            fps.setYaw((float) wkitpvp.this.getConfig().getDouble("fps.yaw"));
                            p1.teleport(fps);
                            teleportkitpvp.remove(p.getName());
                            p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("fps-teleport")));
                            p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                            ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
                            ItemStack espada = new ItemStack(Material.matchMaterial(getConfig().getString("fps-sword").toUpperCase()));
                            ItemMeta sopameta = sopa.getItemMeta();
                            sopameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("soup-inventory")));
                            sopa.setItemMeta(sopameta);
                            ItemStack helmet = new ItemStack(Material.matchMaterial(getConfig().getString("fps-helmet").toUpperCase()));
                            ItemStack armor = new ItemStack(Material.matchMaterial(getConfig().getString("fps-chestplate").toUpperCase()));
                            ItemStack legs = new ItemStack(Material.matchMaterial(getConfig().getString("fps-leggings").toUpperCase()));
                            ItemStack boots = new ItemStack(Material.matchMaterial(getConfig().getString("fps-boots").toUpperCase()));
                            new ItemStack(Material.AIR);
                            p.getEquipment().setHelmet(helmet);
                            p.getEquipment().setChestplate(armor);
                            p.getEquipment().setLeggings(legs);
                            p.getEquipment().setBoots(boots);
                            p.getInventory().clear();

                            for (PotionEffect e : p.getActivePotionEffects()) {
                                p.removePotionEffect(e.getType());
                            }

                            for (int i = 1; i < 2; ++i) {
                                p.getInventory().addItem(new ItemStack[]{espada});
                            }
                            for (int i = 2; i < 37; ++i) {
                                p.getInventory().addItem(new ItemStack[]{sopa});
                            }
                            wkitpvp.Nalava.remove(p);
                            wkitpvp.Naknock.remove(p);
                            wkitpvp.Na1v1.remove(p1);
                            wkitpvp.Nafps.add(p);
                        }
                    });
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("fps-not-set")));
                }
            } else if (command.getName().equalsIgnoreCase("lava")) {
                if (this.getConfig().contains("lava")) {
                    teleportkitpvp.add(p.getName());

                    scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            Player p1 = (Player) sender;
                            World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("lava.world"));
                            double x = wkitpvp.this.getConfig().getDouble("lava.x");
                            double y = wkitpvp.this.getConfig().getDouble("lava.y");
                            double z = wkitpvp.this.getConfig().getDouble("lava.z");
                            lava = new Location(w, x, y, z);
                            lava.setPitch((float) wkitpvp.this.getConfig().getDouble("lava.pitch"));
                            lava.setYaw((float) wkitpvp.this.getConfig().getDouble("lava.yaw"));
                            p1.teleport(lava);
                            teleportkitpvp.remove(p.getName());
                            p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-teleport")));
                            p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                            ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
                            ItemMeta sopameta = sopa.getItemMeta();
                            sopameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("soup-inventory")));
                            sopa.setItemMeta(sopameta);
                            ItemStack helmet = new ItemStack(Material.AIR);
                            ItemStack armor = new ItemStack(Material.AIR);
                            ItemStack legs = new ItemStack(Material.AIR);
                            ItemStack boots = new ItemStack(Material.AIR);
                            new ItemStack(Material.AIR);
                            p.getEquipment().setHelmet(helmet);
                            p.getEquipment().setChestplate(armor);
                            p.getEquipment().setLeggings(legs);
                            p.getEquipment().setBoots(boots);
                            p.getInventory().clear();

                            for (PotionEffect e : p.getActivePotionEffects()) {
                                p.removePotionEffect(e.getType());
                            }

                            for (int i = 1; i < 37; ++i) {
                                p.getInventory().addItem(new ItemStack[]{sopa});
                            }
                            wkitpvp.Na1v1.remove(p);
                            wkitpvp.Naknock.remove(p);
                            wkitpvp.Nafps.remove(p1);
                            wkitpvp.Nalava.add(p);
                        }
                    });
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-not-set")));
                }

            } else if (command.getName().equalsIgnoreCase("knockback")) {
                if (this.getConfig().contains("knockback")) {
                    teleportkitpvp.add(p.getName());

                    Player p1 = (Player) sender;
                    World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("knockback.world"));
                    double x = wkitpvp.this.getConfig().getDouble("knockback.x");
                    double y = wkitpvp.this.getConfig().getDouble("knockback.y");
                    double z = wkitpvp.this.getConfig().getDouble("knockback.z");
                    knock = new Location(w, x, y, z);
                    knock.setPitch((float) wkitpvp.this.getConfig().getDouble("knockback.pitch"));
                    knock.setYaw((float) wkitpvp.this.getConfig().getDouble("knockback.yaw"));
                    p1.teleport(knock);
                    wkitpvp.teleportkitpvp.remove(p.getName());
                    p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("knockback-teleport")));
                    p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                    ItemStack vareta = new ItemStack(Material.STICK, 1);
                    ItemMeta varetaMeta = vareta.getItemMeta();
                    varetaMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
                    vareta.setItemMeta(varetaMeta);
                    ItemStack helmet = new ItemStack(Material.AIR);
                    ItemStack armor = new ItemStack(Material.AIR);
                    ItemStack legs = new ItemStack(Material.AIR);
                    ItemStack boots = new ItemStack(Material.AIR);
                    new ItemStack(Material.AIR);
                    p.getEquipment().setHelmet(helmet);
                    p.getEquipment().setChestplate(armor);
                    p.getEquipment().setLeggings(legs);
                    p.getEquipment().setBoots(boots);
                    p.getInventory().clear();

                    for (final PotionEffect effect : p.getActivePotionEffects()) {
                        if(p.getActivePotionEffects() != null) {
                            p.removePotionEffect(effect.getType());
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 100));
                        }
                        if(p.getActivePotionEffects() == null) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 100));
                        }
                    }

                    for (int i = 1; i < 2; ++i) {
                        p.getInventory().addItem(vareta);
                    }

                    wkitpvp.Nalava.remove(p);
                    wkitpvp.Na1v1.remove(p);
                    wkitpvp.Nafps.remove(p1);
                    wkitpvp.Naknock.add(p);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("knockback-not-set")));
                }
            }else if (command.getName().equalsIgnoreCase("1v1")) {
                if (this.getConfig().contains("1v1")) {
                    teleportkitpvp.add(p.getName());

                    Player p1 = (Player) sender;
                    World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("1v1.world"));
                    double x = wkitpvp.this.getConfig().getDouble("1v1.x");
                    double y = wkitpvp.this.getConfig().getDouble("1v1.y");
                    double z = wkitpvp.this.getConfig().getDouble("1v1.z");
                    s1v1 = new Location(w, x, y, z);
                    s1v1.setPitch((float) wkitpvp.this.getConfig().getDouble("1v1.pitch"));
                    s1v1.setYaw((float) wkitpvp.this.getConfig().getDouble("1v1.yaw"));
                    p1.teleport(s1v1);
                    wkitpvp.teleportkitpvp.remove(p.getName());
                    p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("1v1-teleport")));
                    p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                    ItemStack vareta1v1 = new ItemStack(Material.BLAZE_ROD, 1);
                    ItemMeta varetaMeta1v1 = vareta1v1.getItemMeta();
                    varetaMeta1v1.setDisplayName("§61V1");
                    vareta1v1.setItemMeta(varetaMeta1v1);
                    ItemStack helmet = new ItemStack(Material.AIR);
                    ItemStack armor = new ItemStack(Material.AIR);
                    ItemStack legs = new ItemStack(Material.AIR);
                    ItemStack boots = new ItemStack(Material.AIR);
                    new ItemStack(Material.AIR);
                    p.getEquipment().setHelmet(helmet);
                    p.getEquipment().setChestplate(armor);
                    p.getEquipment().setLeggings(legs);
                    p.getEquipment().setBoots(boots);
                    p.getInventory().clear();
                    p.getInventory().setItem(4,vareta1v1);

                    for (final PotionEffect effect : p.getActivePotionEffects()) {
                        if (p.getActivePotionEffects() != null) {
                            p.removePotionEffect(effect.getType());
                        }
                    }

                    wkitpvp.Nalava.remove(p);
                    wkitpvp.Naknock.remove(p);
                    wkitpvp.Nafps.remove(p1);
                    wkitpvp.Na1v1.add(p);
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("1v1-not-set")));
                }
                }else if (command.getName().equalsIgnoreCase("t1v1")) {
                    if (this.getConfig().contains("1v1")) {
                        teleportkitpvp.add(p.getName());

                        Player p1 = (Player) sender;
                        World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("1v1.world"));
                        double x = wkitpvp.this.getConfig().getDouble("1v1.x");
                        double y = wkitpvp.this.getConfig().getDouble("1v1.y");
                        double z = wkitpvp.this.getConfig().getDouble("1v1.z");
                        s1v1 = new Location(w, x, y, z);
                        s1v1.setPitch((float) wkitpvp.this.getConfig().getDouble("1v1.pitch"));
                        s1v1.setYaw((float) wkitpvp.this.getConfig().getDouble("1v1.yaw"));
                        p1.teleport(s1v1);
                        wkitpvp.teleportkitpvp.remove(p.getName());
                        p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("1v1-teleport")));
                        p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                        ItemStack vareta1v1 = new ItemStack(Material.BLAZE_ROD, 1);
                        ItemMeta varetaMeta1v1 = vareta1v1.getItemMeta();
                        varetaMeta1v1.setDisplayName("§61V1");
                        vareta1v1.setItemMeta(varetaMeta1v1);
                        ItemStack helmet = new ItemStack(Material.AIR);
                        ItemStack armor = new ItemStack(Material.AIR);
                        ItemStack legs = new ItemStack(Material.AIR);
                        ItemStack boots = new ItemStack(Material.AIR);
                        new ItemStack(Material.AIR);
                        p.getEquipment().setHelmet(helmet);
                        p.getEquipment().setChestplate(armor);
                        p.getEquipment().setLeggings(legs);
                        p.getEquipment().setBoots(boots);
                        p.getInventory().clear();
                        p.getInventory().setItem(4,vareta1v1);

                        for (final PotionEffect effect : p.getActivePotionEffects()) {
                            if (p.getActivePotionEffects() != null) {
                                p.removePotionEffect(effect.getType());
                            }
                        }

                        wkitpvp.Nalava.remove(p);
                        wkitpvp.Naknock.remove(p);
                        wkitpvp.Nafps.remove(p1);
                        wkitpvp.Na1v1.add(p);
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("1v1-not-set")));
                    }
                }
            if (command.getName().equalsIgnoreCase("sair")) {
                if (this.getConfig().contains("sair")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 1000));
                    if (tasks.containsKey(p)) {
                        p.sendMessage(("§4Comando já utilizado"));
                        return true;
                    }
                    p.sendMessage("§eVocê será teleportado em §b3 segundos.");
                    tasks1.put(p, new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§eVocê será teleportado em §b1 segundo.");
                            tasks1.remove(p);
                        }
                    }.runTaskLater(this, 20L * 2));
                    tasks2.put(p, new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§eVocê será teleportado em §b2 segundos.");
                            tasks2.remove(p);
                        }
                    }.runTaskLater(this, 20L));
                    teleportkitpvp.add(p.getName());
                                tasks.put(p, new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        Player p1 = (Player) sender;
                                        World w = Bukkit.getServer().getWorld(wkitpvp.this.getConfig().getString("sair.world"));
                                        double x = wkitpvp.this.getConfig().getDouble("sair.x");
                                        double y = wkitpvp.this.getConfig().getDouble("sair.y");
                                        double z = wkitpvp.this.getConfig().getDouble("sair.z");
                                        lobby = new Location(w, x, y, z);
                                        lobby.setPitch((float) wkitpvp.this.getConfig().getDouble("sair.pitch"));
                                        lobby.setYaw((float) wkitpvp.this.getConfig().getDouble("sair.yaw"));
                                        p1.teleport(lobby);
                                        teleportkitpvp.remove(p1.getName());
                                        p1.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-teleport")));
                                        ItemStack ar = new ItemStack(Material.AIR);
                                        p1.getEquipment().setHelmet(ar);
                                        p1.getEquipment().setChestplate(ar);
                                        p1.getEquipment().setLeggings(ar);
                                        p1.getEquipment().setBoots(ar);
                                        p1.getInventory().clear();

                                        for (PotionEffect e : p1.getActivePotionEffects()) {
                                            p1.removePotionEffect(e.getType());
                                        }
                                        wkitpvp.Nalava.remove(p1);
                                        wkitpvp.Naknock.remove(p1);
                                        wkitpvp.Nafps.remove(p1);
                                        wkitpvp.Na1v1.remove(p1);
                                        tasks.remove(p);
                                    }
                                }.runTaskLater(this, 3 * 20L));
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-not-set")));
                }
            } else if (command.getName().equalsIgnoreCase("wkadmin")) {
                if (p.hasPermission("KitPvPwarpsN.set")) {
                    if (args.length == 0) {
                        p.getPlayer().sendMessage("§c------- KitpvpWarpsN Admin -----------");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§b- /wkset sair: §cSetar saida das warps.");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§b- /wkset fps: §cSetar warp fps.");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§b- /wkset lavachallenge: §cSetar warp lavachallenge.");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§b- /wkset knockback: §cSetar warp knockback.");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§b- /wk reload: §cRecarregar a config.");
                        p.getPlayer().sendMessage(" ");
                        p.getPlayer().sendMessage("§c------------------------------");
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("Permission")));
                }


                //WK SET
            } else if (command.getName().equalsIgnoreCase("wkset")) {
                if (p.hasPermission("KitPvPwarpsN.set")) {
                    if (args.length == 0) {
                        p.getPlayer().sendMessage(ChatColor.RED + "Use : fps, lavachallenge e sair");
                    } else if (args[0].equalsIgnoreCase("fps")) {
                        this.getConfig().set("fps.x", p.getLocation().getX());
                        this.getConfig().set("fps.y", p.getLocation().getY());
                        this.getConfig().set("fps.z", p.getLocation().getZ());
                        this.getConfig().set("fps.pitch", p.getLocation().getPitch());
                        this.getConfig().set("fps.yaw", p.getLocation().getYaw());
                        this.getConfig().set("fps.world", p.getLocation().getWorld().getName());
                        this.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("fps-set")));
                    } else if (args[0].equalsIgnoreCase("lavachallenge")) {
                        this.getConfig().set("lava.x", p.getLocation().getX());
                        this.getConfig().set("lava.y", p.getLocation().getY());
                        this.getConfig().set("lava.z", p.getLocation().getZ());
                        this.getConfig().set("lava.pitch", p.getLocation().getPitch());
                        this.getConfig().set("lava.yaw", p.getLocation().getYaw());
                        this.getConfig().set("lava.world", p.getLocation().getWorld().getName());
                        this.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("lava-set")));
                    } else if (args[0].equalsIgnoreCase("knockback")) {
                        this.getConfig().set("knockback.x", p.getLocation().getX());
                        this.getConfig().set("knockback.y", p.getLocation().getY());
                        this.getConfig().set("knockback.z", p.getLocation().getZ());
                        this.getConfig().set("knockback.pitch", p.getLocation().getPitch());
                        this.getConfig().set("knockback.yaw", p.getLocation().getYaw());
                        this.getConfig().set("knockback.world", p.getLocation().getWorld().getName());
                        this.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("knockback-set")));
                    } else if (args[0].equalsIgnoreCase("sair")) {
                        this.getConfig().set("sair.x", p.getLocation().getX());
                        this.getConfig().set("sair.y", p.getLocation().getY());
                        this.getConfig().set("sair.z", p.getLocation().getZ());
                        this.getConfig().set("sair.pitch", p.getLocation().getPitch());
                        this.getConfig().set("sair.yaw", p.getLocation().getYaw());
                        this.getConfig().set("sair.world", p.getLocation().getWorld().getName());
                        this.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-set")));
                    } else if (args[0].equalsIgnoreCase("1v1")) {
                        this.getConfig().set("1v1.x", p.getLocation().getX());
                        this.getConfig().set("1v1.y", p.getLocation().getY());
                        this.getConfig().set("1v1.z", p.getLocation().getZ());
                        this.getConfig().set("1v1.pitch", p.getLocation().getPitch());
                        this.getConfig().set("1v1.yaw", p.getLocation().getYaw());
                        this.getConfig().set("1v1.world", p.getLocation().getWorld().getName());
                        this.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("1v1-set")));
                    }

                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("Permission")));
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("console-cmd")));
        }
        return true;
    }

    @EventHandler
    public void Resnasce(final PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (lava != null) {
            if (wkitpvp.Nalava.contains(e.getPlayer())) {
                e.setRespawnLocation(lava);
                ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
                ItemMeta sopameta = sopa.getItemMeta();
                sopameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("soup-inventory")));
                sopa.setItemMeta(sopameta);
                ItemStack helmet = new ItemStack(Material.AIR);
                ItemStack armor = new ItemStack(Material.AIR);
                ItemStack legs = new ItemStack(Material.AIR);
                ItemStack boots = new ItemStack(Material.AIR);
                new ItemStack(Material.AIR);
                p.getEquipment().setHelmet(helmet);
                p.getEquipment().setChestplate(armor);
                p.getEquipment().setLeggings(legs);
                p.getEquipment().setBoots(boots);
                p.getInventory().clear();

                for (PotionEffect effect : p.getActivePotionEffects()) {
                    p.removePotionEffect(effect.getType());
                }

                for (int i = 1; i < 37; ++i) {
                    p.getInventory().addItem(new ItemStack[]{sopa});
                }
            }
        }
            if (fps != null) {
                if (wkitpvp.Nafps.contains(e.getPlayer())) {
                    e.setRespawnLocation(fps);
                    ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
                    ItemStack espada = new ItemStack(Material.matchMaterial(getConfig().getString("fps-sword").toUpperCase()));
                    ItemMeta sopameta = sopa.getItemMeta();
                    sopameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("soup-inventory")));
                    sopa.setItemMeta(sopameta);
                    ItemStack helmet = new ItemStack(Material.matchMaterial(getConfig().getString("fps-helmet").toUpperCase()));
                    ItemStack armor = new ItemStack(Material.matchMaterial(getConfig().getString("fps-chestplate").toUpperCase()));
                    ItemStack legs = new ItemStack(Material.matchMaterial(getConfig().getString("fps-leggings").toUpperCase()));
                    ItemStack boots = new ItemStack(Material.matchMaterial(getConfig().getString("fps-boots").toUpperCase()));
                    new ItemStack(Material.AIR);
                    p.getEquipment().setHelmet(helmet);
                    p.getEquipment().setChestplate(armor);
                    p.getEquipment().setLeggings(legs);
                    p.getEquipment().setBoots(boots);
                    p.getInventory().clear();

                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        p.removePotionEffect(effect.getType());
                    }

                    for (int i = 1; i < 2; ++i) {
                        p.getInventory().addItem(new ItemStack[]{espada});
                    }
                    for (int i = 2; i < 37; ++i) {
                        p.getInventory().addItem(new ItemStack[]{sopa});
                    }
                }
            }
                if (knock != null) {
                    if (wkitpvp.Naknock.contains(e.getPlayer())) {
                        e.setRespawnLocation(knock);
                        ItemStack vareta = new ItemStack(Material.STICK, 1);
                        ItemMeta varetaMeta = vareta.getItemMeta();
                        varetaMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
                        vareta.setItemMeta(varetaMeta);
                        ItemStack helmet = new ItemStack(Material.AIR);
                        ItemStack armor = new ItemStack(Material.AIR);
                        ItemStack legs = new ItemStack(Material.AIR);
                        ItemStack boots = new ItemStack(Material.AIR);
                        new ItemStack(Material.AIR);
                        p.getEquipment().setHelmet(helmet);
                        p.getEquipment().setChestplate(armor);
                        p.getEquipment().setLeggings(legs);
                        p.getEquipment().setBoots(boots);
                        p.getInventory().clear();

                        for (int i = 1; i < 2; ++i) {
                            p.getInventory().addItem(vareta);
                        }
                    }
                }
        if (s1v1 != null) {
            if (wkitpvp.Na1v1.contains(e.getPlayer())) {
                e.setRespawnLocation(s1v1);
                ItemStack vareta1v1 = new ItemStack(Material.BLAZE_ROD, 1);
                ItemMeta varetaMeta1v1 = vareta1v1.getItemMeta();
                varetaMeta1v1.setDisplayName("§61V1");
                vareta1v1.setItemMeta(varetaMeta1v1);
                ItemStack helmet = new ItemStack(Material.AIR);
                ItemStack armor = new ItemStack(Material.AIR);
                ItemStack legs = new ItemStack(Material.AIR);
                ItemStack boots = new ItemStack(Material.AIR);
                new ItemStack(Material.AIR);
                p.getEquipment().setHelmet(helmet);
                p.getEquipment().setChestplate(armor);
                p.getEquipment().setLeggings(legs);
                p.getEquipment().setBoots(boots);
                p.getInventory().clear();
                p.getInventory().setItem(4,vareta1v1);

                for (final PotionEffect effect : p.getActivePotionEffects()) {
                    if (p.getActivePotionEffects() != null) {
                        p.removePotionEffect(effect.getType());
                    }
                }
            }
        }
    }

    @EventHandler
    public void ComandoNaswarps(final PlayerCommandPreprocessEvent e) {
        if (wkitpvp.Nafps.contains(e.getPlayer()) || wkitpvp.Nalava.contains(e.getPlayer()) || wkitpvp.Naknock.contains(e.getPlayer()) || wkitpvp.Na1v1.contains(e.getPlayer())) {
            if (!e.getPlayer().hasPermission("kitpvpwarpsn.comandos")) {
                if (!e.getMessage().startsWith("/sair") || !e.getMessage().startsWith("/s")) {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("Command-blocked")));
                    e.setCancelled(true);
                }
            }
            if (e.getMessage().startsWith("/spawn")) {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("leave-msg")));
                e.setCancelled(true);
            }
        } else {
        }
    }



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() != e.getTo().getBlockX()
                || e.getFrom().getBlockY() != e.getTo().getBlockY()
                || e.getFrom().getBlockZ() != e.getTo().getBlockZ() || e.getFrom().getY() + 0.119 < e.getTo().getY()) {
            BukkitTask task = tasks.get(e.getPlayer());
            BukkitTask task1 = tasks1.get(e.getPlayer());
            BukkitTask task2 = tasks2.get(e.getPlayer());
            if (task != null) {
                task.cancel();
                tasks.remove(e.getPlayer());
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msgConfig.getString("teleport-cancel")));
                tasks1.remove(e.getPlayer());
                tasks2.remove(e.getPlayer());
                task1.cancel();
                task2.cancel();
            }
        }
    }
}

