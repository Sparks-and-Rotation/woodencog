import {registerShapes, generateFile} from "./shapes.mjs";
import {addPath, paths, setBasePath} from "./paths.mjs";
import {save} from "./saver.mjs";
import {alloys, metals} from "./data.mjs";

setBasePath('/home/jeantet/Projets/woodencog/src/main/resources/data/woodencog/recipes');
addPath('mixing', 'mixing');
addPath('deployer', 'deploying');

registerShapes('heated_mixing', JSON.stringify({
    "type": "create:mixing",
    "ingredients": [
        {
            "item": 'tfc:metal/ingot/{{0}}'
        }
    ],
    "results": [
        {
            "fluid": 'tfc:metal/{{0}}',
            "nbt": {},
            "amount": 100
        }
    ],
    "heatRequirement": "heated"
}));

registerShapes('base_deploying', JSON.stringify({
    "type": "create:deploying",
    "ingredients": [
        {
            "type": "forge:nbt",
            "item": "tfc:ceramic/ingot_mold",
            "nbt": {
                "tank":{
                    "Amount":100,
                    "FluidName":`tfc:metal/{{0}}`
                }
            }
        },
        {
            "tag": "tfc:chisels"
        }
    ],
    "results": [
        {
            "item": `tfc:metal/ingot/{{0}}`
        },
        {
            "item": "tfc:ceramic/ingot_mold",
            "chance": 0.75
        }
    ]
}));

metals.forEach(metal => {
    save(
        paths['mixing'],
        `ingot_to_liquid_${metal}`,
        generateFile('heated_mixing', metal)
    );
    save(
        paths['deployer'],
        `mold_to_ingot_${metal}`,
        generateFile('base_deploying', metal)
    )
});

alloys.forEach((alloy) => {
    save(
        paths['mixing'],
        `create_mixing_alloying_${alloy.name}`,
        {
            "type": "create:mixing",
            "ingredients": alloy.input,
            "results": [
                {
                    "fluid": alloy.result,
                    "nbt": {},
                    "amount": 100
                }
            ],
            "heatRequirement": alloy.type ?? "heated"
        }
    );
});
