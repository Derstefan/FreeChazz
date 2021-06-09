import { Component } from 'react'
import RandomGenerator from '../generator/random-generator';

class PieceGeneratorComponent extends Component {

    static gen;
    static width = 600;
    static height = 600;







    // static nextRand() {
    //     var hi = this.seed / (2147483647 / 48271);
    //     var lo = this.seed % (2147483647 / 48271);
    //     var test = 48271 * lo - (2147483647 % 48271) * hi;
    //     if (test > 0) {
    //         this.seed = test;
    //     } else {
    //         this.seed = test + 2147483647;
    //     }
    //     return this.seed * (1.0 / 2147483647);
    // }



    // static drawPolygon() {

    //     var pointNumber = this.randNumOfRange(3, 5);
    //     console.log("pointNumber=" + pointNumber)
    //     //console.log(Math.floor(this.nextRand() * 16777215).toString(16));
    //     var color = "#" + Math.floor(this.nextRand() * 16777215).toString(16);

    //     var pts = "";
    //     var ptsMirror = "";
    //     for (var i = 0; i < pointNumber; i++) {
    //         var x = this.randNumOfRange(0, this.width);
    //         var y = this.randNumOfRange(0, this.height);
    //         pts += x + "," + y + " ";
    //         var x2 = this.width - x;
    //         ptsMirror += x2 + "," + y + " ";
    //         //  console.log(i);
    //     }

    //     return <><polygon points={pts} fill={color} />
    //         <polygon points={ptsMirror} fill={color} /></>;
    // }


    // static drawPiece(width, height, seed, owner) {
    //     this.seed = seed;
    //     this.width = width;
    //     this.height = height;

    //     return (
    //         <>
    //             <svg height={this.height} width={this.width}>
    //                 {this.drawPolygon()}
    //                 {this.drawPolygon()}
    //                 {this.drawPolygon()}
    //                 {this.drawPolygon()}
    //                 {this.drawPolygon()}
    //             </svg>
    //         </>
    //     )
    // }



    static drawPolygonCanvas(c2) {
        // TODO:     var shape1 = new Path2D();
        // circle.arc(100, 35, 25, 0, 2 * Math.PI);

        // ctx.stroke(rectangle);
        // ctx.fill(circle);


        var pointNumber = this.randNumOfRange(3, 10);
        //console.log("pointNumber=" + pointNumber)
        var r = Math.floor(this.random() * 255).toString(16);
        var g = Math.floor(this.random() * 255).toString(16);
        var b = Math.floor(this.random() * 255).toString(16);
        var color = "#" + r + g + b;


        // var color = "#" + Math.floor(this.random() * 255).toString(2);
        // 16 777 216
        // console.log(color);

        var points = [];
        for (var i = 0; i < pointNumber; i++) {
            var x = this.randNumOfRange(0, this.width);
            var y = this.randNumOfRange(0, this.height);
            points.push({ x: x, y: y });
            // console.log("peint added: (", x, ",", y, ")");
        }


        c2.fillStyle = color;
        c2.beginPath();
        for (var i = 0; i < points.length; i++) {
            if (i === 0) {
                c2.moveTo(points[i].x, points[i].y);
            } else {
                c2.lineTo(points[i].x, points[i].y);
            }
        }
        c2.closePath();
        c2.fill();

        c2.fillStyle = color;
        c2.beginPath();

        for (var j = 0; j < points.length; j++) {
            var x2 = this.width - points[j].x;
            if (j === 0) {
                c2.moveTo(x2, points[j].y);
            } else {
                c2.lineTo(x2, points[j].y);
            }
        }
        c2.closePath();
        c2.fill();
    }


    static random() {
        return this.gen.random();
    }

    static randNumOfRange(a, b) {
        return this.gen.randNumOfRange(a, b);
    }




    static drawPieceCanvas(width, height, seedstr, owner) {
        this.gen = new RandomGenerator(seedstr);

        this.width = width;
        this.height = height;

        var canvas2 = document.createElement('canvas');
        canvas2.width = width;
        canvas2.height = height;
        var context2 = canvas2.getContext('2d');
        // console.log("Piece: ", seedstr);

        this.drawPolygonCanvas(context2);
        this.drawPolygonCanvas(context2);
        this.drawPolygonCanvas(context2);
        this.drawPolygonCanvas(context2);

        return (
            canvas2
        );
    }




}

export default PieceGeneratorComponent