import { Component } from 'react'
import RandomGenerator from '../random-generator';
import UtilFunctions from '../util-functions';



/**
 * Piece Generator for black and white design as silhouettes
 */
class ChessPieceGenerator extends Component {

    constructor(width, height, seedstr) {
        super();
        this.gen = new RandomGenerator(seedstr);
        this.width = width;
        this.height = height;
        this.canvas2 = document.createElement('canvas');
        this.c2 = this.canvas2.getContext('2d');
        this.canvas2.width = width;
        this.canvas2.height = height;
        this.leftBorder = this.width / 3;
        this.rightBorder = 2 * this.width / 3;
    }




    randomColor() {
        var r = Math.floor(this.gen.random() * 230).toString(16);
        var g = Math.floor(this.gen.random() * 230).toString(16);
        var b = Math.floor(this.gen.random() * 230).toString(16);
        return "#" + r + g + b;
    }

    randomPointsLeft(num) {
        var points = [];
        var ys = [];
        for (var i = 0; i < num; i++) {
            var y = this.gen.randNumOfRange(0, this.height);
            ys.push(y);
        }
        ys.sort((a, b) => a - b);
        for (var i = 0; i < num; i++) {
            var x = this.gen.randNumOfRange(0, this.leftBorder);
            points.push({ x: x, y: ys[i] });
        }
        console.log(points);
        return points;
    }




    drawSilhouette(points) {


        var pointNumber = this.gen.randNumOfRange(8, 13);
        var points = this.randomPointsLeft(pointNumber);
        var color = this.randomColor();
        this.c2.fillStyle = color;

        var shape = new Path2D();
        for (var i = 0; i < points.length - 1; i++) {
            if (i === 0) {
                shape.moveTo(points[i].x, points[i].y);
            } else if (i % 2 === 0) {
                shape.quadraticCurveTo(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y)
            }
        }
        shape.lineTo(points[points.length - 1].x, points[points.length - 1].y);
        for (var i = points.length - 1; i > 1; i--) {
            if (i === points.length - 1) {
                shape.lineTo(this.rightBorder - points[i].x, points[i].y);
            } else if (i % 2 === 0) {
                shape.quadraticCurveTo(this.rightBorder - points[i + 1].x, points[i + 1].y, this.rightBorder - points[i].x, points[i].y)
            }
        }
        shape.lineTo(this.rightBorder - points[1].x, points[1].y);
        shape.lineTo(points[0].x, points[0].y);
        this.c2.fill(shape);
    }



    drawSilhouettes(num) {
        return [...Array(num).keys()].map(() => this.drawSilhouette());
    }



    drawPieceCanvas(owner) {
        if (owner == "P1") {
            this.drawSilhouette();
            this.drawSilhouette();
        } else {
            this.drawSilhouette();
        }

        // this.drawMirroredPolygon();
        // this.drawMirroredPolygonColorDiff();
        // this.drawMirroredPolygon();
        // //this.drawMirroredPolygon();
        // //        this.drawMirroredPolygonColorDiff();
        // //this.drawMirroredPolygon();
        // this.drawMirroredPolygon();

        return (
            this.canvas2
        );
    }




}

export default ChessPieceGenerator