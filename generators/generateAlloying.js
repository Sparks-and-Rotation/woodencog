import {alloys} from "./data.mjs";
import fs from "fs";
import {mixing_path} from "./generators.js";

export const generateAlloying = () => {
    alloys.forEach(alloy => {
        const craft = {
            "type": "create:mixing",
            "ingredients": alloy.input,
            "results": [
                {
                    "fluid": alloy.result,
                    "nbt": {},
                    "amount": 100
                }
            ],
            "heatRequirement": alloy.type,
            "processingTime": 400
        }

        fs.writeFileSync(`${mixing_path}/create_mixing_alloying_${alloy.name}.json`, JSON.stringify(craft, null, 4), 'utf8')
    });
}