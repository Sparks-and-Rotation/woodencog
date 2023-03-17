import * as fs from 'fs';
import {nuggets, metals} from "./data.mjs";

const mixing_path = "/home/jeantet/Projets/woodencog/src/main/resources/data/woodencog/recipes/mixing";
const deploying_path = "/home/jeantet/Projets/woodencog/src/main/resources/data/woodencog/recipes/deploying";

const generateIngotsMelted = (name) => {
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
    fs.writeFile(`${mixing_path}/ingot_to_liquid_${name}.json`, JSON.stringify(data), 'utf8', (err) => { if(err) console.log(err)})
}

const generateNuggetsMelted = (name, result) => {
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
        fs.writeFile(`${mixing_path}/nugget_${type.type}_to_liquid_${name}.json`, JSON.stringify(data), 'utf8', (err) => { if(err) console.log(err)})
    });
}

const generateIngotMoldToIngot = (name) => {
    let data = {
        "type": "create:deploying",
        "ingredients": [
            {
                "type": "forge:nbt",
                "item": "tfc:ceramic/ingot_mold",
                "nbt": {
                    "tank":{
                        "Amount":100,
                        "FluidName":`tfc:metal/${name}`
                    }
                }
            },
            {
                "tag": "tfc:chisels"
            }
        ],
        "results": [
            {
                "item": `tfc:metal/ingot/${name}`
            },
            {
                "item": "tfc:ceramic/ingot_mold",
                "chance": 0.75
            }
        ]
    }
    fs.writeFile(`${deploying_path}/mold_to_ingot_${name}.json`, JSON.stringify(data), 'utf8', (err) => { if(err) console.log(err)})
}

metals.forEach(name => {
    generateIngotsMelted(name)
    generateIngotMoldToIngot(name)
})

nuggets.forEach(({name, result}) => {
    generateNuggetsMelted(name, result)
})
