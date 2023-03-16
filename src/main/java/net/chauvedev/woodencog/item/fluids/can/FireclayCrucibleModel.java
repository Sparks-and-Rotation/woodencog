package net.chauvedev.woodencog.item.fluids.can;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.CompositeModelState;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ItemMultiLayerBakedModel;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public final class FireclayCrucibleModel implements IModelGeometry<FireclayCrucibleModel> {
    public static final Loader LOADER = new Loader();
    private static final float NORTH_Z_COVER = 0.4685F;
    private static final float SOUTH_Z_COVER = 0.5315F;
    private static final float NORTH_Z_FLUID = 0.468625F;
    private static final float SOUTH_Z_FLUID = 0.531375F;
    @Nonnull
    private final FluidStack fluid;
    private final boolean coverIsMask;
    private final boolean applyFluidLuminosity;

    public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
        FluidAttributes attributes = this.fluid.getFluid().getAttributes();
        TextureAtlasSprite fluidSprite = !this.fluid.isEmpty() ? (TextureAtlasSprite)spriteGetter.apply(ForgeHooksClient.getBlockMaterial(attributes.getStillTexture(this.fluid))) : null;
        Material baseLocation = owner.isTexturePresent("base") ? owner.resolveTexture("base") : null;
        TextureAtlasSprite coverSprite = (!this.coverIsMask || baseLocation != null) && owner.isTexturePresent("cover") ? (TextureAtlasSprite)spriteGetter.apply(owner.resolveTexture("cover")) : null;
        TextureAtlasSprite particleSprite;
        if (owner.isTexturePresent("particle")) {
            particleSprite = spriteGetter.apply(owner.resolveTexture("particle"));
        } else if (fluidSprite != null) {
            particleSprite = fluidSprite;
        } else if (!this.coverIsMask && coverSprite != null) {
            particleSprite = coverSprite;
        } else {
            particleSprite = spriteGetter.apply(ModelLoaderRegistry.blockMaterial(MissingTextureAtlasSprite.getLocation()));
        }

        ModelState transformsFromModel = owner.getCombinedTransform();
        ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap = PerspectiveMapWrapper.getTransforms(new CompositeModelState(transformsFromModel, modelTransform));
        ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new ContainedFluidOverrideHandler(overrides, bakery, owner, this), transformMap);
        Transformation transform = modelTransform.getRotation();
        if (baseLocation != null) {
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprites(ImmutableList.of(baseLocation), transform, spriteGetter));
        }

        TextureAtlasSprite baseSprite;
        if (fluidSprite != null && owner.isTexturePresent("fluid")) {
            baseSprite = (TextureAtlasSprite)spriteGetter.apply(owner.resolveTexture("fluid"));
            if (baseSprite != null) {
                int luminosity = this.applyFluidLuminosity ? attributes.getLuminosity(this.fluid) : 0;
                int color = attributes.getColor(this.fluid);
                builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, baseSprite, fluidSprite, 0.468625F, Direction.NORTH, color, -1, luminosity));
                builder.addQuads(ItemLayerModel.getLayerRenderType(luminosity > 0), ItemTextureQuadConverter.convertTexture(transform, baseSprite, fluidSprite, 0.531375F, Direction.SOUTH, color, -1, luminosity));
            }
        }

        if (this.coverIsMask) {
            if (coverSprite != null) {
                baseSprite = (TextureAtlasSprite)spriteGetter.apply(baseLocation);
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, 0.4685F, Direction.NORTH, -1, 2));
                builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.convertTexture(transform, coverSprite, baseSprite, 0.5315F, Direction.SOUTH, -1, 2));
            }
        } else if (coverSprite != null) {
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), new BakedQuad[]{ItemTextureQuadConverter.genQuad(transform, 0.0F, 0.0F, 16.0F, 16.0F, 0.4685F, coverSprite, Direction.NORTH, -1, 2)});
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), new BakedQuad[]{ItemTextureQuadConverter.genQuad(transform, 0.0F, 0.0F, 16.0F, 16.0F, 0.5315F, coverSprite, Direction.SOUTH, -1, 2)});
        }

        builder.setParticle(particleSprite);
        return builder.build();
    }

    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<Material> texs = Sets.newHashSet();
        if (owner.isTexturePresent("particle")) {
            texs.add(owner.resolveTexture("particle"));
        }

        if (owner.isTexturePresent("base")) {
            texs.add(owner.resolveTexture("base"));
        }

        if (owner.isTexturePresent("fluid")) {
            texs.add(owner.resolveTexture("fluid"));
        }

        if (owner.isTexturePresent("cover")) {
            texs.add(owner.resolveTexture("cover"));
        }

        return texs;
    }

    public FireclayCrucibleModel(@Nonnull FluidStack fluid, boolean coverIsMask, boolean applyFluidLuminosity) {
        if (fluid == null) {
            throw new NullPointerException("fluid is marked non-null but is null");
        } else {
            this.fluid = fluid;
            this.coverIsMask = coverIsMask;
            this.applyFluidLuminosity = applyFluidLuminosity;
        }
    }

    public FireclayCrucibleModel withFluid(@Nonnull FluidStack fluid) {
        if (fluid == null) {
            throw new NullPointerException("fluid is marked non-null but is null");
        } else {
            return this.fluid == fluid ? this : new FireclayCrucibleModel(fluid, this.coverIsMask, this.applyFluidLuminosity);
        }
    }

    private static final class ContainedFluidOverrideHandler extends ItemOverrides {
        private static final ResourceLocation BAKE_LOCATION = new ResourceLocation("woodencog", "fireclay_crucible_dynamic");;
        private final Map<FluidStack, BakedModel> cache = Maps.newHashMap();
        private final ItemOverrides nested;
        private final ModelBakery bakery;
        private final IModelConfiguration owner;
        private final FireclayCrucibleModel parent;

        private ContainedFluidOverrideHandler(ItemOverrides nested, ModelBakery bakery, IModelConfiguration owner, FireclayCrucibleModel parent) {
            this.nested = nested;
            this.bakery = bakery;
            this.owner = owner;
            this.parent = parent;
        }

        private BakedModel getUncahcedModel(FluidStack fluid) {
            return this.parent.withFluid(fluid).bake(this.owner, this.bakery, ForgeModelBakery.defaultTextureGetter(), BlockModelRotation.X0_Y0, ItemOverrides.EMPTY, BAKE_LOCATION);
        }

        public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
            BakedModel overriden = this.nested.resolve(originalModel, stack, world, entity, seed);
            if (overriden != originalModel) {
                return overriden;
            } else {
                Fluid fluid = FireclayCrucibleItem.getFluid(stack);
                if (fluid != Fluids.EMPTY) {
                    FluidStack fluidStack = new FluidStack(fluid, FireclayCrucibleItem.CAPACITY, FireclayCrucibleItem.getFluidTag(stack));
                    return (BakedModel)this.cache.computeIfAbsent(fluidStack, this::getUncahcedModel);
                } else {
                    return originalModel;
                }
            }
        }
    }

    private static class Loader implements IModelLoader<FireclayCrucibleModel> {
        private Loader() {
        }

        public void onResourceManagerReload(ResourceManager resourceManager) {
        }

        public FireclayCrucibleModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            boolean coverIsMask = GsonHelper.getAsBoolean(modelContents, "coverIsMask", true);
            boolean applyFluidLuminosity = GsonHelper.getAsBoolean(modelContents, "applyFluidLuminosity", true);
            return new FireclayCrucibleModel(FluidStack.EMPTY, coverIsMask, applyFluidLuminosity);
        }
    }
}
