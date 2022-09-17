package com.tgirou.skilled.client.gui;

import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;


import java.util.List;
import java.util.ArrayList;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Menus {
    Menus() {}
    private static final List<MenuType<?>> REGISTRY = new ArrayList<>();
    public static final MenuType<ChoosePath> CHOOSE_MINER_PATH = register(
            ChoosePath::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(IContainerFactory<T> containerFactory) {
        MenuType<T> menuType = new MenuType<>(containerFactory);
        menuType.setRegistryName("choose_miner_path");
        REGISTRY.add(menuType);
        return menuType;
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().registerAll(REGISTRY.toArray(new MenuType[0]));
    }
}
