import fs from "fs";
import {mixing_path} from "./generators.js";

export const generateIngotsMelted = (name) => {
    let data = {
        "type": "create:mixing",
        "ingredients": [
            {
                "item": `tfc:metal/ingot/${name}`
            }
        ],
        "results": [
            {
                "fluid": `tfc:metal/${name}`,
                "nbt": {},
                "amount": 100
            }
        ],
        "heatRequirement": "heated"
    }
    fs.writeFileSync(`${mixing_path}/ingot_to_liquid_${name}.json`, JSON.stringify(data, null, 4), 'utf8')
}