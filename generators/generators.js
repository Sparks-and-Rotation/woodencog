import {generateIngotsMelted} from "./generateIngotsMelted.js";
import {generateIngotMoldToIngot} from "./generateIngotMoldToIngot.js";
import {generateNuggetsMelted} from "./generateNuggetsMelted.js";
import {generateAnvilCrafts} from "./generateAnvilCrafts.js";
import {metals, nuggets} from "./data.mjs";
import {generateAlloying} from "./generateAlloying.js";
import {generateBarrelCrafts} from "./generateBarrelCrafts.js";
import {generateChiselCrafts} from "./generateChiselCrafts.js";
import {generateKnappingCrafts} from "./generateKnappingCrafts.js";

export const basePath = "/home/jeantet/Projets/woodencog";

export const tfcPaths = `${basePath}/generators/tfc_recipes`;
export const recipesPath = `${basePath}/src/main/resources/data/woodencog`;
export const mixing_path = `${recipesPath}/recipes/mixing`;
export const deploying_path = `${recipesPath}/recipes/deploying`;
export const cutting_path = `${recipesPath}/recipes/cutting`;

export const compacting_path = `${recipesPath}/recipes/compacting`;

metals.forEach(name => {
    generateIngotsMelted(name)
    generateIngotMoldToIngot(name)
});

nuggets.forEach(({name, result}) => {
    generateNuggetsMelted(name, result)
});

generateAnvilCrafts();
generateAlloying();
generateBarrelCrafts();
generateChiselCrafts();
generateKnappingCrafts();