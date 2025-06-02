package me.vout.paper.arcania.item.registry;

import java.util.Set;

import org.bukkit.inventory.ItemType;

import io.papermc.paper.registry.keys.ItemTypeKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;

public class RegistryTags {
        public static final TagKey<ItemType> TOOLS = ItemTypeTagKeys.create(Key.key("arcania:tools"));
        public static final TagKey<ItemType> BOOKS = ItemTypeTagKeys.create(Key.key("arcania:books"));
        public static final TagKey<ItemType> PICKAXES_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:pickaxes_and_books"));
        public static final TagKey<ItemType> AXES_AND_BOOKS = ItemTypeTagKeys.create(Key.key("arcania:axes_and_books"));
        public static final TagKey<ItemType> SHOVELS_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:shovels_and_books"));
        public static final TagKey<ItemType> HOES_AND_BOOKS = ItemTypeTagKeys.create(Key.key("arcania:hoes_and_books"));
        public static final TagKey<ItemType> SWORDS_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:swords_and_books"));
        public static final TagKey<ItemType> TOOLS_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:tools_and_books"));
        public static final TagKey<ItemType> SWORDS_AND_TOOLS = ItemTypeTagKeys
                        .create(Key.key("arcania:sword_and_tools"));
        public static final TagKey<ItemType> SWORDS_TOOLS_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:sword_tools_and_books"));
        public static final TagKey<ItemType> SWORDS_AND_RANGED = ItemTypeTagKeys
                        .create(Key.key("arcania:swords_and_ranged"));
        public static final TagKey<ItemType> SWORDS_RANGED_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:swords_ranged_and_books"));
        public static final TagKey<ItemType> RANGED = ItemTypeTagKeys.create(Key.key("arcania:ranged"));
        public static final TagKey<ItemType> RANGED_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:ranged_and_books"));
        public static final TagKey<ItemType> PROSPERITY_TOOLS = ItemTypeTagKeys
                        .create(Key.key("arcania:prosperity_tools"));
        public static final TagKey<ItemType> PROSPERITY_TOOLS_AND_BOOKS = ItemTypeTagKeys
                        .create(Key.key("arcania:prosperity_tools_and_books"));

        public static final Set<TagEntry<ItemType>> BOOKS_SET = Set.of(
                        TagEntry.valueEntry(ItemTypeKeys.BOOK),
                        TagEntry.valueEntry(ItemTypeKeys.ENCHANTED_BOOK));

        public static final Set<TagEntry<ItemType>> PICKAXES_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.PICKAXES),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> AXES_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.AXES),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> SHOVELS_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.SHOVELS),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> HOES_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.HOES),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> SWORDS_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.SWORDS),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> TOOLS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.PICKAXES),
                        TagEntry.tagEntry(ItemTypeTagKeys.AXES),
                        TagEntry.tagEntry(ItemTypeTagKeys.SHOVELS),
                        TagEntry.tagEntry(ItemTypeTagKeys.HOES));

        public static final Set<TagEntry<ItemType>> TOOLS_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(TOOLS),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> SWORDS_AND_TOOLS_SET = Set.of(
                        TagEntry.tagEntry(TOOLS),
                        TagEntry.tagEntry(ItemTypeTagKeys.SWORDS));

        public static final Set<TagEntry<ItemType>> SWORDS_TOOLS_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(SWORDS_AND_TOOLS),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> RANGED_SET = Set.of(
                        TagEntry.valueEntry(ItemTypeKeys.BOW),
                        TagEntry.valueEntry(ItemTypeKeys.CROSSBOW));

        public static final Set<TagEntry<ItemType>> RANGED_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(RANGED),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> SWORDS_AND_RANGED_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.SWORDS),
                        TagEntry.tagEntry(RANGED));

        public static final Set<TagEntry<ItemType>> SWORDS_RANGED_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(SWORDS_AND_RANGED),
                        TagEntry.tagEntry(BOOKS));

        public static final Set<TagEntry<ItemType>> PROSPERITY_TOOLS_SET = Set.of(
                        TagEntry.tagEntry(ItemTypeTagKeys.PICKAXES),
                        TagEntry.tagEntry(ItemTypeTagKeys.AXES),
                        TagEntry.tagEntry(ItemTypeTagKeys.SHOVELS),
                        TagEntry.valueEntry(ItemTypeKeys.SHEARS));

        public static final Set<TagEntry<ItemType>> PROSPERITY_TOOLS_AND_BOOKS_SET = Set.of(
                        TagEntry.tagEntry(PROSPERITY_TOOLS),
                        TagEntry.tagEntry(BOOKS));

}
