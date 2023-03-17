import fs from "fs";
import path from "path";
import {compacting_path, tfcPaths} from "./generators.js";

export const generateKnappingCrafts = () => {
    ["clay_knapping", "fire_clay_knapping", "leather_knapping", "rock_knapping"].forEach(type => {
        const knapping_crafts = fs.readdirSync(`${tfcPaths}/${type}`).filter(file => path.extname(file) === ".json");
        knapping_crafts.forEach(file => {
            const fileData = fs.readFileSync(path.join(`${tfcPaths}/${type}`, file));
            const json = JSON.parse(fileData.toString());
            const craft = {
                "type": "create:compacting",
                "ingredients": [
                    {
                        "tag": `tfc:${type}`
                    }
                ],
                "results": [
                    json.result
                ]
            }

            fs.writeFileSync(`${compacting_path}/${file}`, JSON.stringify(craft, null, 4), 'utf8');
        });
    });
}