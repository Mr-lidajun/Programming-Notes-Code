const processArguments = process.argv;
let sum = 0
for (let i = 2; i < processArguments.length; i++) {
    sum += +processArguments[i];
    
}
console.log(sum)