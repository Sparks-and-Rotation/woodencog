import fs from "fs";
import path from "path";
import {cutting_path, tfcPaths} from "./generators.js";

export const generateChiselCrafts = () => {
    ["slab", "smooth", "stair"].forEach(type => {
        const chisel_crafts = fs.readdirSync(`${tfcPaths}/chisel/${type}`).filter(file => path.extname(file) === ".json");
        chisel_crafts.forEach(file => {
            const fileData = fs.readFileSync(path.join(`${tfcPaths}/chisel/${type}`, file));
            const json = JSON.parse(fileData.toString());
            const craft = {
                "type": "create:cutting",
                "ingredients": [
                    {
                        "item": json.ingredient
                    }
                ],
                "results": [
                    {
                        "item": json.result
                    },
                    (() => {
                        if(json?.extra_drop?.item !== undefined){
                            return {
                                "item": json?.extra_drop?.item
                            }
                        }
                    })()
                ].filter(result => result !== undefined)
            }

            fs.writeFileSync(`${cutting_path}/${type}/${file}`, JSON.stringify(craft, null, 4), 'utf8')
        });
    });
}