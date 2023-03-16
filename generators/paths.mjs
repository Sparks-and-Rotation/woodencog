export const paths = [];
let basePath = "";


export const setBasePath = (path) => {
    basePath = path;
}
export const addPath = (name, path, useBasePath = true) => {
    if(useBasePath) {
        paths[name] = `${basePath}/${path}/`;
    } else {
        paths[name] = `${path}/`;
    }
}