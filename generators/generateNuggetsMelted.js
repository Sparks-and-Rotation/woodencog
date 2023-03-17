import fs from "fs";
import {mixing_path} from "./generators.js";

export const generateNuggetsMelted = (name, result) => {
    [
        {type: 'small', quantity: 10},
        {type: 'poor', quantity: 15},
        {type: 'normal', quantity: 25},
        {type: 'rich', quantity: 35},
    ].forEach(type => {
        let data = {
            "type": "create:mixing",
            "ingredients": [
                {
                    "item": `tfc:ore/${type.type}_${name}`
                }
            ],
            "results": [
                {
                    "fluid": `tfc:metal/${result}`,
                    "nbt": {},
                    "amount": type.quantity
                }
            ],
            "heatRequirement": "heated"
        }
        fs.writeFileSync(`${mixing_path}/nugget_${type.type}_to_liquid_${name}.json`, JSON.stringify(data, null, 4))
    });
}