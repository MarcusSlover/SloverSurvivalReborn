package me.marcusslover.sloversurvivalreborn.code.command;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.IHandler;
import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.command.SpawnCommand;
import me.marcusslover.sloversurvivalreborn.utils.API;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public class CommandHandler implements ICodeInitializer, IHandler<SloverCommand> {
    @Init
    private List<SloverCommand> sloverCommandList;

    @Override
    public void initialize() {
        try {
            add(new SpawnCommand());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(SloverCommand object) {
        Validate.notNull(object);

        Class<? extends SloverCommand> aClass = object.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        boolean isCommand = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Command) {
                Command command = (Command) annotation;
                String name = command.name();

                // Additional check
                for (SloverCommand sloverCommand : sloverCommandList) {
                    Class<? extends SloverCommand> aClass1 = sloverCommand.getClass();
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
                commandMap.register(name, new org.bukkit.command.Command(name) {
                    @Override
                    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            if (object instanceof PlayerCommand) {
                                PlayerCommand playerCommand = (PlayerCommand) object;
                                playerCommand.onCommand(player, args);
                            }
                        }
                        return true;
                    }
                });

                this.initLog(object.getClass().getName());
                break;
            }
        }

        if (!isCommand) {
            API.getLogger().warning("Could not read the Command annotation!");
            return;
        }
        sloverCommandList.add(object);
    }

    @Override
    public List<SloverCommand> getRegistered() {
        return sloverCommandList;
    }
}
