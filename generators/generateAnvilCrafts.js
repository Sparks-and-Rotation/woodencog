import fs from "fs";
import path from "path";
import {cutting_path, tfcPaths} from "./generators.js";

export const generateAnvilCrafts = () => {
    const anvil_crafts = fs.readdirSync(`${tfcPaths}/anvil`).filter(file => path.extname(file) === ".json");
    anvil_crafts.forEach(file => {
        const fileData = fs.readFileSync(path.join(`${tfcPaths}/anvil`, file));
        const json = JSON.parse(fileData.toString());
        const craft = {
            "type": "create:cutting",
            "ingredients": [
                json.input
            ],
            "results": [
                json.result
            ],
            "processingTime": 400
        }

        fs.writeFileSync(`${cutting_path}/${file}`, JSON.stringify(craft, null, 4), 'utf8')
    });
}