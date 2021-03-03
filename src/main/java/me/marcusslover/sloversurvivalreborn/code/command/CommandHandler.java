package me.marcusslover.sloversurvivalreborn.code.command;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.IHandler;
import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.code.PatchVersion;
import me.marcusslover.sloversurvivalreborn.command.BankCommand;
import me.marcusslover.sloversurvivalreborn.command.SpawnCommand;
import me.marcusslover.sloversurvivalreborn.command.WarpCommand;
import me.marcusslover.sloversurvivalreborn.utils.API;
import me.marcusslover.sloversurvivalreborn.utils.ChatUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.*;

@PatchVersion(version = "1.1.0")
public class CommandHandler implements ICodeInitializer, IHandler<ICommand> {
    @Init
    private List<ICommand> iCommandList;

    @Override
    public void initialize() {
        add(new SpawnCommand());
        add(new BankCommand());
        add(new WarpCommand());
    }

    @Override
    public void add(ICommand object) {
        Validate.notNull(object);

        Class<? extends ICommand> aClass = object.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        boolean isCommand = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Command) {
                Command command = (Command) annotation;
                String name = command.name();
                String description = command.description();
                List<String> aliases = new ArrayList<>();
                if (command.aliases().length >= 1) {
                    aliases.addAll(Arrays.asList(command.aliases()));
                }

                // Additional check
                for (ICommand iCommand : iCommandList) {
                    Class<? extends ICommand> aClass1 = iCommand.getClass();
                    Annotation[] annotations1 = aClass1.getAnnotations();

                    for (Annotation annotation1 : annotations1) {
                        if (annotation1 instanceof Command) {
                            Command command1 = (Command) annotation;
                            String name1 = command1.name();

                            if (name.equalsIgnoreCase(name1)) {
                                API.getLogger().warning("Could not register the command twice!");
                                return;
                            }
                        }
                    }
                }

                isCommand = true;
                SloverSurvivalReborn instance = SloverSurvivalReborn.getInstance();
                SimpleCommandMap commandMap = ((CraftServer) instance.getServer()).getCommandMap();
                long cooldown = command.cooldown();
                long finalTime = cooldown * 1000;

                // Spigot command
                commandMap.register(name, new org.bukkit.command.Command(name, description, "", aliases) {
                    private final Map<UUID, Long> cooldownMap = new HashMap<>();

                    @Override
                    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            if (object instanceof PlayerCommand) {
                                if (cooldown > 0) {
                                    long playerTime = cooldownMap.getOrDefault(player.getUniqueId(), -1L);
                                    long time = System.currentTimeMillis() - playerTime;
                                    if (time < finalTime) {
                                        long difference = (finalTime - time) / 1000;
                                        ChatUtil.error(player, String.format("Wait! You can't execute this command for another %s seconds!", difference));
                                        return true;
                                    }
                                    cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
                                }

                                PlayerCommand playerCommand = (PlayerCommand) object;
                                playerCommand.onCommand(player, args);
                            }
                        }
                        return true;
                    }
                });
            }
        }

        if (!isCommand) {
            API.getLogger().warning("Could not read the Command annotation!");
            return;
        }
        iCommandList.add(object);
        this.initLog(object.getClass().getName());
    }

    @Override
    public List<ICommand> getRegistered() {
        return iCommandList;
    }
}
