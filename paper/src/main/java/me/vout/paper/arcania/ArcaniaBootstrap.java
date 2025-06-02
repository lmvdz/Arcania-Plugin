package me.vout.paper.arcania;

import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemType;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.tag.PreFlattenTagRegistrar;
import me.vout.paper.arcania.command.ArcaniaCommand;
import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import me.vout.paper.arcania.item.registry.RegisterArcaniaEnchant;
import me.vout.paper.arcania.item.registry.RegistryTags;
import me.vout.paper.arcania.manager.ArcaniaEnchantManager;
import me.vout.paper.arcania.manager.GuiManager;
import net.kyori.adventure.key.Key;

public class ArcaniaBootstrap implements PluginBootstrap {
    private static final GuiManager guiManager = new GuiManager();
    private static ArcaniaEnchantManager enchantManager;

    private final ArcaniaCommand arcaniaCommand = new ArcaniaCommand(guiManager);

    @Override
    public void bootstrap(BootstrapContext context) {

        LifecycleEventManager<BootstrapContext> lifecycle = context.getLifecycleManager();


        // Register command handler
        lifecycle.registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            // register arcania command
            commands.registrar().register("arcania", arcaniaCommand);
        });

        lifecycle.registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ITEM), event -> {
            // register all arcania item tags
            final PreFlattenTagRegistrar<ItemType> registrar = event.registrar();
            registrar.setTag(RegistryTags.TOOLS, RegistryTags.TOOLS_SET);
            registrar.setTag(RegistryTags.BOOKS, RegistryTags.BOOKS_SET);
            registrar.setTag(RegistryTags.PICKAXES_AND_BOOKS, RegistryTags.PICKAXES_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.AXES_AND_BOOKS, RegistryTags.AXES_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.SHOVELS_AND_BOOKS, RegistryTags.SHOVELS_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.HOES_AND_BOOKS, RegistryTags.HOES_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.SWORDS_AND_BOOKS, RegistryTags.SWORDS_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.TOOLS_AND_BOOKS, RegistryTags.TOOLS_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.SWORDS_AND_TOOLS, RegistryTags.SWORDS_AND_TOOLS_SET);
            registrar.setTag(RegistryTags.SWORDS_TOOLS_AND_BOOKS, RegistryTags.SWORDS_TOOLS_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.SWORDS_AND_RANGED, RegistryTags.SWORDS_AND_RANGED_SET);
            registrar.setTag(RegistryTags.SWORDS_RANGED_AND_BOOKS, RegistryTags.SWORDS_RANGED_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.RANGED, RegistryTags.RANGED_SET);
            registrar.setTag(RegistryTags.RANGED_AND_BOOKS, RegistryTags.RANGED_AND_BOOKS_SET);
            registrar.setTag(RegistryTags.PROSPERITY_TOOLS, RegistryTags.PROSPERITY_TOOLS_SET);
            registrar.setTag(RegistryTags.PROSPERITY_TOOLS_AND_BOOKS, RegistryTags.PROSPERITY_TOOLS_AND_BOOKS_SET);
        });
        

        // Register enchantment handler - this will be called at the right time by Paper
        lifecycle.registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
            enchantManager = new ArcaniaEnchantManager();

            // Register all arcania enchantments
            // ArcaniaEnchant implements Enchantment so we can just pass all the attributes directly to the builder using the RegisterArcaniaEncha
            for (ArcaniaEnchant enchant: enchantManager.getEnchants()) {
                RegisterArcaniaEnchant.register(event, enchant);
            }
        }));

        // add enchants to enchantment table tag
        lifecycle.registerEventHandler(LifecycleEvents.TAGS.postFlatten(RegistryKey.ENCHANTMENT).newHandler(event -> {
            // create a set of all arcania enchantments that are discoverable
            Set<TypedKey<Enchantment>> arcaniaEnchants = enchantManager.getEnchants().stream().filter(enchant -> enchant.isDiscoverable()).map(enchant -> EnchantmentKeys.create(Key.key(enchant.getKey().getNamespace(), enchant.getKey().getKey()))).collect(Collectors.toSet());
            
            context.getLogger().info("Adding " + arcaniaEnchants.size() + " arcania enchantments to enchantment table tag");
            // add all arcania enchantments to the enchantment table tag
            event.registrar().addToTag(EnchantmentTagKeys.IN_ENCHANTING_TABLE, arcaniaEnchants);
            // log all tags
            event.registrar().getAllTags().forEach((tagKey, tagValue) -> {
                context.getLogger().info("Tag: " + tagKey.key().toString() + " " + tagValue.stream().map(entry -> entry.key().asString()).collect(Collectors.joining(", ")));
            });
        }));

        // add enchants to enchantment table tag
        lifecycle.registerEventHandler(LifecycleEvents.TAGS.postFlatten(RegistryKey.ITEM).newHandler(event -> {
            // log all tags
            event.registrar().getAllTags().forEach((tagKey, tagValue) -> {
                context.getLogger().info("Tag: " + tagKey.key().asString() + " " + tagValue.stream().map(entry -> entry.key().asString()).collect(Collectors.joining(", ")));
            });
        }));
    }

    @Override
    public Arcania createPlugin(PluginProviderContext context) {
        return new Arcania();
    }

    public static ArcaniaEnchantManager getEnchantManager() {
        return enchantManager;
    }

    public static GuiManager getGuiManager() {
        return guiManager;
    }

}
