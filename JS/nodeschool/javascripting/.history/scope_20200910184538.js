const a = 1; const b = 2; const c = 3;
(function firstFunction() {
    const b = 5; const c = 6;

    (function sencondFunction() {
        const b = 8;

        (function thirdFunction() {
            const a = 7; const c = 9;

            
        })
    })
})