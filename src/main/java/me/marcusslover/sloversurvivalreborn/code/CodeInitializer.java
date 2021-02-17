package me.marcusslover.sloversurvivalreborn.code;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import me.marcusslover.sloversurvivalreborn.code.event.Menu;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.PluginManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CodeInitializer implements IHandler<ICodeInitializer> {
    private static CodeInitializer instance;
    private final List<ICodeInitializer> codeInitializerList;

    public CodeInitializer() {
        instance = this;
        this.codeInitializerList = new ArrayList<>();
    }

    @Override
    public void add(ICodeInitializer object) {
        Validate.notNull(object);

        SloverSurvivalReborn instance = SloverSurvivalReborn.getInstance();
        Server server = instance.getServer();
        PluginManager pluginManager = server.getPluginManager();

        Class<? extends ICodeInitializer> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {

                if (annotation instanceof Init) {
                    boolean accessibilityChange = false;

                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                        accessibilityChange = true;
                    }
                    Class<?> type = field.getType();
                    if (type.isAssignableFrom(List.class)) {
                        try {
                            field.set(object, new ArrayList<>());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    if (accessibilityChange) {
                        field.setAccessible(false);
                    }
                }

                // Menu class
                if (annotation instanceof Menu) {
                    Menu menu = (Menu) annotation;
                    final String name = menu.name();

                    if (object instanceof IMenu) {
                        IMenu iMenu = (IMenu) object;
                        pluginManager.registerEvents(new Listener() {
                            @EventHandler
                            public void onClick(final InventoryClickEvent event) {
                                if (event.getView().getTitle().equalsIgnoreCase(name)) {
                                    HumanEntity whoClicked = event.getWhoClicked();
                                    iMenu.onClick((Player) whoClicked, event);
                                }
                            }
                        }, instance);
                    }
                }
            }
        }

        // Built-in event registerer
        if (object instanceof Listener) {
            Listener listener = (Listener) object;
            pluginManager.registerEvents(listener, instance);
        }

        try {
            object.initialize();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        codeInitializerList.add(object);
    }

    @Override
    public List<ICodeInitializer> getRegistered() {
        return codeInitializerList;
    }

    @SuppressWarnings("unchecked")
    public static <T> T find(Class<T> classType) {
        CodeInitializer instance = getInstance();
        List<ICodeInitializer> registered = instance.getRegistered();
        for (ICodeInitializer iCodeInitializer : registered) {
            Class<? extends ICodeInitializer> aClass = iCodeInitializer.getClass();

            if (aClass.equals(classType)) {
                return (T) iCodeInitializer;
            }
        }
        return null;
    }

    public static CodeInitializer getInstance() {
        return instance;
    }
}
