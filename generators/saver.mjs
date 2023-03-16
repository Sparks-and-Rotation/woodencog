import fs from "fs";

export const save = (path, name, value) => {
    fs.mkdir(path, { recursive: true }, (err) => {
        if (err) return;
    });
    fs.writeFile(
        `${path}/${name}.json`,
        JSON.stringify(value),
        'utf8',
        (err) => { if(err) console.log(err)}
    );
}