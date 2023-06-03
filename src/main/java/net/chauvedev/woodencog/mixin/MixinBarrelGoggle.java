package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.dries007.tfc.common.recipes.SealedBarrelRecipe;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = BarrelBlockEntity.class, remap = false)
public abstract class MixinBarrelGoggle implements IHaveGoggleInformation, ICalendarTickable {
    @Shadow() private @Nullable SealedBarrelRecipe recipe;

    @Shadow()
    public abstract long getSealedTick();
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        long sealed_at = getSealedTick();
        if(sealed_at > 0) {
            tooltip.add(componentSpacing.plainCopy().append(I18n.get("tfc.block_entity.barrel")));
            String date = ICalendar.getTimeAndDate(
                    Calendars.CLIENT.ticksToCalendarTicks(
                            getSealedTick()
                    ), Calendars.CLIENT.getCalendarDaysInMonth()
            ).getString();
            tooltip.add(componentSpacing.plainCopy().append(I18n.get("tfc.jade.sealed_date", date)).withStyle(ChatFormatting.GRAY));
            if(this.recipe != null) {
                tooltip.add(componentSpacing.plainCopy().append(I18n.get("tfc.jade.creating", recipe.getResultItem().getDisplayName().getString())).withStyle(ChatFormatting.GRAY));
                long left = (getSealedTick() + recipe.getDuration()) - Calendars.CLIENT.getTicks();
                tooltip.add(componentSpacing.plainCopy().append(I18n.get("tfc.jade.time_left", (int) Math.floor(left / 1200) +"m")).withStyle(ChatFormatting.GRAY));
            }
        }
        return true;
    }
}
