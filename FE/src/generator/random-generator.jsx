import React, { Component } from 'react'

class RandomGenerator extends Component {


    constructor(str) {
        super();
        this.tempSeed = this.hash(str);

        //        console.log("str=", str, "seed=", this.tempSeed);
    }

    random() {
        var x = Math.sin(this.tempSeed) * 10000;
        this.tempSeed = Math.sin(this.tempSeed);
        return x - Math.floor(x);
    }

    randNumOfRange(a, b) {

        return Math.round(this.random() * (b - a) + a);
    }


    hash(str) {
        var hash = 0, i, chr;
        if (str.length === 0) return hash;
        for (i = 0; i < str.length; i++) {
            chr = str.charCodeAt(i);
            hash = ((hash << 5) - hash) + chr;
            hash |= 0; // Convert to 32bit integer
        }
        return hash;
    }
}



export default RandomGenerator