const fs = require('fs');
const mixing_path = "/home/jeantet/Projets/woodencog/src/main/resources/data/woodencog/recipes/mixing";
const deploying_path = "/home/jeantet/Projets/woodencog/src/main/resources/data/woodencog/recipes/deploying";

const metals = [
    "bismuth",
    "bismuth_bronze",
    "black_bronze",
    "bronze",
    "brass",
    "copper",
    "gold",
    "nickel",
    "rose_gold",
    "silver",
    "tin",
    "zinc",
    "sterling_silver",
    "wrought_iron",
    "cast_iron",
    "pig_iron",
    "steel",
    "black_steel",
    "blue_steel",
    "red_steel",
    "weak_steel",
    "weak_blue_steel",
    "weak_red_steel",
    "high_carbon_steel",
    "high_carbon_black_steel",
    "high_carbon_blue_steel",
    "high_carbon_red_steel",
    "unknown"
]
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
//{ForgeCaps: {Parent: {heat: 0.0f, ticks: 0L}}, id: "tfc:ceramic/ingot_mold", Count: 1b, tag: {tank: {FluidName: "tfc:metal/bismuth", Amount: 100}}}