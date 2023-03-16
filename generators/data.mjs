export const metals = [
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
];

export const alloys = [
    {
        name: "bismuth_bronze",
        input: [
            {
                fluid: "tfc:metal/bismuth",
                nbt: {},
                amount: 20
            },
            {
                fluid: "tfc:metal/zinc",
                nbt: {},
                amount: 30
            },
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 50,
            }
        ],
        result: "tfc:metal/bismuth_bronze",
        type: "heated"
    },
    {
        name: "black_bronze",
        input: [
            {
                fluid: "tfc:metal/silver",
                nbt: {},
                amount: 25
            },
            {
                fluid: "tfc:metal/gold",
                nbt: {},
                amount: 25
            },
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 50
            }
        ],
        result: "tfc:metal/black_bronze",
        type: "heated"
    },
    {
        name: "brass",
        input: [
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 90,
            },
            {
                fluid: "tfc:metal/zinc",
                nbt: {},
                amount: 10,
            }
        ],
        result: "tfc:metal/brass",
        type: "heated"
    },
    {
        name: "bronze",
        input: [
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 90,
            },
            {
                fluid: "tfc:metal/tin",
                nbt: {},
                amount: 10,
            }
        ],
        result: "tfc:metal/bronze",
        type: "heated"
    },
    {
        name: "rose_gold",
        input: [
            {
                fluid: "tfc:metal/gold",
                nbt: {},
                amount: 70,
            },
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 30,
            }
        ],
        result: "tfc:metal/rose_gold",
        type: "heated"
    },
    {
        name: "sterling_silver",
        input: [
            {
                fluid: "tfc:metal/silver",
                nbt: {},
                amount: 60,
            },
            {
                fluid: "tfc:metal/copper",
                nbt: {},
                amount: 40,
            }
        ],
        result: "tfc:metal/sterling_silver",
        type: "heated"
    },
    {
        name: "weak_blue_steel",
        input: [
            {
                fluid: "tfc:metal/black_steel",
                nbt: {},
                amount: 50,
            },
            {
                fluid: "tfc:metal/steel",
                nbt: {},
                amount: 20,
            },
            {
                fluid: "tfc:metal/bismuth_bronze",
                nbt: {},
                amount: 15,
            },
            {
                fluid: "tfc:metal/sterling_silver",
                nbt: {},
                amount: 15,
            }
        ],
        result: "tfc:metal/weak_blue_steel",
        type: "heated"
    },
    {
        name: "weak_red_steel",
        input: [
            {
                fluid: "tfc:metal/black_steel",
                nbt: {},
                amount: 50,
            },
            {
                fluid: "tfc:metal/steel",
                nbt: {},
                amount: 20,
            },
            {
                fluid: "tfc:metal/brass",
                nbt: {},
                amount: 15,
            },
            {
                fluid: "tfc:metal/rose_gold",
                nbt: {},
                amount: 15,
            }
        ],
        result: "tfc:metal/weak_red_steel",
        type: "heated"
    },
    {
        name: "weak_steel",
        input: [
            {
                fluid: "tfc:metal/steel",
                nbt: {},
                amount: 50,
            },
            {
                fluid: "tfc:metal/nickel",
                nbt: {},
                amount: 25,
            },
            {
                fluid: "tfc:metal/black_bronze",
                nbt: {},
                amount: 25,
            },
        ],
        result: "tfc:metal/weak_steel",
        type: "heated"
    }
]