import fs from "fs";
import path from "path";
import {cutting_path, sequenced_assembly_path, tfcPaths} from "./generators.js";

const anvils_translation = {
    // hit two time
    'punch': (transitional) => {
        return [
            {
                "type": "create:pressing",
                "ingredients": [
                    {
                        "item": transitional
                    }
                ],
                "results": [
                    {
                        "item": transitional
                    }
                ]
            },
            {
                "type": "create:pressing",
                "ingredients": [
                    {
                        "item": transitional
                    }
                ],
                "results": [
                    {
                        "item": transitional
                    }
                ]
            }
        ]
    },
    // hit one time
    'hit': (transitional) => {
        return [
            {
                "type": "create:pressing",
                "ingredients": [
                    {
                        "item": transitional
                    }
                ],
                "results": [
                    {
                        "item": transitional
                    }
                ]
            }
        ]
    },
    'upset': (transitional) => {
        return {
            "type": "create:deploying",
            "ingredients": [
                {
                    "item": transitional
                },
                {
                    "tag": "forge:rods"
                }
            ],
            "results": [
                {
                    "item": transitional
                }
            ]
        }
    },
    'draw': (transitional) => {
        return {
            "type": "create:deploying",
            "ingredients": [
                {
                    "item": transitional
                },
                {
                    "tag": "tfc:hammers"
                }
            ],
            "results": [
                {
                    "item": transitional
                }
            ]
        }
    },
    'bend': (transitional) => {
        return {
            "type": "create:deploying",
            "ingredients": [
                {
                    "item": transitional
                },
                {
                    "tag": "tfc:chisels"
                }
            ],
            "results": [
                {
                    "item": transitional
                }
            ]
        }
    },
    'shrink': (transitional) => {
        return {
            "type": "create:deploying",
            "ingredients": [
                {
                    "item": transitional
                },
                {
                    "tag": "tfc:maces"
                }
            ],
            "results": [
                {
                    "item": transitional
                }
            ]
        }
    }
}
export const generateAnvilCrafts = () => {
    const anvil_crafts = fs.readdirSync(`${tfcPaths}/anvil`).filter(file => path.extname(file) === ".json");

    anvil_crafts.forEach(file => {
        const fileData = fs.readFileSync(path.join(`${tfcPaths}/anvil`, file));
        const json = JSON.parse(fileData.toString());
        let types = [];
        types.push(...json.rules.map(name => name.split('_')[0]));
        types = types.filter((value, index) => types.indexOf(value) === index)
        const craft = {
            "type": "create:sequenced_assembly",
            "ingredient": json.input,
            "loops": 1,
            "results": [json.result],
            "sequence": [
                {
                    "type": "create:deploying",
                    "ingredients": [
                        {
                            "item": json.result.item
                        },
                        {
                            "item": json.result.item
                        }
                    ],
                    "results": [
                        {
                            "item": json.result.item
                        }
                    ],
                    "keepHeldItem": true
                },
                types.map(type => anvils_translation[type](json.result.item)).flat()
            ].flat(),
            "transitionalItem": {
                "item": json.result.item
            }
        }

        fs.writeFileSync(`${sequenced_assembly_path}/${file}`, JSON.stringify(craft, null, 4), 'utf8')
    });
}

