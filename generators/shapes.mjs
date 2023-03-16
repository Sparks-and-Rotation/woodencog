export const shapes = [];

export const registerShapes = (type, shape) => {
    shapes[type] = shape;
}

export const generateFile = (type, ...params) => {
    let recipe = shapes[type];
    for (let [key, value] of params.entries()) {
        recipe = recipe.replaceAll(`{{${key}}}`, value)
    }
    return JSON.parse(recipe);
}