package net.chauvedev.woodencog.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import net.dries007.tfc.common.capabilities.heat.Heat;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BasinCategory.class})
public abstract class MixinBasinCategory {
    public MixinBasinCategory() {
    }

    @Inject(
            method = {"setRecipe(Lmezz/jei/api/gui/builder/IRecipeLayoutBuilder;Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/recipe/IFocusGroup;)V"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/processing/basin/BasinRecipe;getRequiredHeat()Lcom/simibubi/create/content/processing/recipe/HeatCondition;"
            )},
            cancellable = true,
            remap = false
    )
    public void addSlotBlazeBurner(IRecipeLayoutBuilder builder, BasinRecipe recipe, IFocusGroup focuses, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = {"draw(Lcom/simibubi/create/content/processing/basin/BasinRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lcom/mojang/blaze3d/vertex/PoseStack;DD)V"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I"
            )},
            cancellable = true
    )
    private void draw(BasinRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY, CallbackInfo ci) {
        HeatCondition requiredHeat = recipe.getRequiredHeat();
        if (requiredHeat != HeatCondition.NONE) {
            int heat = requiredHeat == HeatCondition.HEATED ? 7 : 10;
            MutableComponent color = TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(Heat.values()[heat].getMin());
            if (color != null) {
                Minecraft mc = Minecraft.getInstance();
                Font font = mc.font;
                font.draw(matrixStack, color, 9.0F, 86.0F, 16777215);
            }

            ci.cancel();
        }
    }
}
