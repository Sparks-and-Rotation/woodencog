import {metals, metal_temps} from "./data.mjs";
import fs from "fs";
import {advanced_filling_path} from "./generators.js";

export const generateFilling = () => {
    metals.forEach(metal => {
        const craft = {
            "type": "woodencog:filling",
                "ingredients": [
                {
                    "item": "tfc:ceramic/ingot_mold"
                },
                {
                    "fluid": `tfc:metal/${metal}`,
                    "nbt": {},
                    "amount": 100
                }
            ],
                "results": [
                {
                    "stack": {
                        "item": "tfc:ceramic/ingot_mold",
                        "nbt": {
                            "tank": {
                                "Amount": 100,
                                "FluidName": `tfc:metal/${metal}`
                            }
                        },
                        "count": 1
                    },
                    "modifiers": [
                        {
                            "type": "tfc:add_heat",
                            "temperature": metal_temps[metal]
                        }
                    ]
                }
            ]
        }


        fs.writeFileSync(`${advanced_filling_path}/${metal}_to_mold.json`, JSON.stringify(craft, null, 4), 'utf8')
    });
}