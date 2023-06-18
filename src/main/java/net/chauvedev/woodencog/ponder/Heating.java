package net.chauvedev.woodencog.ponder;

import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class Heating {
    public static void heating(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("heat", "Heating items");

        ItemStack steak = Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("tfc:food/venison")
        )).getDefaultInstance();
        ItemStack cooked_steak = Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("tfc:food/cooked_venison")
        )).getDefaultInstance();
        ItemStack unfired_pot = TFCItems.UNFIRED_VESSEL.get().getDefaultInstance();
        ItemStack pot = TFCItems.VESSEL.get().getDefaultInstance();

        scene.configureBasePlate(0, 0, 5);


        scene.world.showSection(util.select.fromTo(0, 0, 0, 4, 0, 4), Direction.UP);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(2, 1, 0, 4, 1, 4), Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 1, 1, 4), Direction.DOWN);
        scene.idle(10);

        BlockPos lava_stand = util.grid.at(1, 1, 1);
        Selection lava_stand_selection = util.select.position(lava_stand);
        scene.world.createItemOnBeltLike(lava_stand, Direction.UP, unfired_pot);
        scene.overlay.showSelectionWithText(lava_stand_selection, 70)
                .attachKeyFrame()
                .colored(PonderPalette.INPUT)
                .text("An item capable of receiving heat will be heated by lava, the maximum temperature is 1700°.");
        scene.idle(35);
        scene.world.removeItemsFromBelt(lava_stand);
        scene.effects.indicateSuccess(lava_stand);
        scene.world.createItemOnBeltLike(lava_stand, Direction.UP, pot);
        scene.idle(35);
        BlockPos fire_stand = util.grid.at(1, 1, 3);
        Selection fire_stand_selection = util.select.position(fire_stand);
        scene.world.createItemOnBeltLike(fire_stand, Direction.UP, steak);
        scene.overlay.showSelectionWithText(fire_stand_selection, 70)
                .attachKeyFrame()
                .colored(PonderPalette.INPUT)
                .text("It's the same with fire, but the temperature limit is 200°. Perfect for cooking !");
        scene.idle(35);
        scene.world.removeItemsFromBelt(fire_stand);
        scene.effects.indicateSuccess(fire_stand);
        scene.world.createItemOnBeltLike(fire_stand, Direction.UP, cooked_steak);
        scene.idle(35);
    }

    public static void cooling(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("cool", "Cooling items");

        ItemStack ingot = Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("tfc:metal/ingot/copper")
        )).getDefaultInstance();

        scene.configureBasePlate(0, 0, 5);

        scene.world.showSection(util.select.fromTo(0, 0, 0, 4, 0, 4), Direction.UP);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(2, 1, 0, 4, 1, 4), Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 1, 1, 4), Direction.DOWN);
        scene.idle(10);
        BlockPos item_stand = util.grid.at(1, 1, 2);
        Selection item_stand_selection = util.select.position(item_stand);
        Vec3 itemVec = util.vector.blockSurface(item_stand, Direction.UP);
        scene.world.createItemOnBeltLike(item_stand, Direction.UP, ingot);
        scene.effects.emitParticles(
                itemVec,
                Emitter.simple(ParticleTypes.FLAME, new Vec3(0, 0.01, 0)),
                1,
                30
        );
        scene.idle(30);
        scene.overlay.showSelectionWithText(item_stand_selection, 70)
                .attachKeyFrame()
                .colored(PonderPalette.BLUE)
                .text("Heated item can be cooled down with water.");
        scene.effects.emitParticles(
                itemVec,
                Emitter.simple(ParticleTypes.SMOKE, new Vec3(0, 0.01, 0)),
                1,
                25
        );
        scene.idle(25);
    }
}
