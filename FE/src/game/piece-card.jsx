import Config from "./config.json";

class PieceCard {

    constructor() {
        this.canvas = document.createElement('canvas');
        this.ctx = this.canvas.getContext('2d');
        this.canvas.width = Config.card.width;
        this.canvas.height = Config.card.height;
    }

    drawCanvas(actions, pieceImage, owner, isKing) {
        const actionsSize = Config.card.actionsSize;
        const actionsOffsetX = Config.card.actionsOffsetX;
        const actionsOffsetY = Config.card.actionsOffsetY;
        var ctx = this.ctx;

        if (actions !== undefined) {
            ctx.canvas.width = Config.card.width;
            ctx.canvas.height = Config.card.height;

            ctx.drawImage(pieceImage, 32, 10);

            // draw actions

            var actionLegend = [];

            for (var i = 0; i < actions.length; i++) {
                for (var j = 0; j < actions[0].length; j++) {
                    if (actions[i][j] !== "-") {
                        var offsetY;
                        if (owner === "P1") {
                            offsetY = actionsOffsetY + (actions[0].length - 1 - j) * actionsSize;
                        } else {
                            offsetY = actionsOffsetY + j * actionsSize
                        }
                        if (!actionLegend.includes(actions[i][j])) {
                            actionLegend.push(actions[i][j]);
                        }
                        ctx.fillStyle = this.mapActionToColor(actions[i][j]);
                        ctx.fillRect(actionsOffsetX + i * actionsSize, offsetY, actionsSize, actionsSize);
                    }
                }
            }


            ctx.lineWidth = 0.5;
            ctx.strokeStyle = "#AAAAAA";
            for (i = 0; i < actions.length + 1; i++) {
                //  ctx.drawline(actionsOffsetX + i * actionsSize, actionsOffsetY, actionsOffsetX + i * actionsSize, actionsOffsetX + actions.length + 1 * actionsSize);
                //console.log(i);

                ctx.beginPath();
                ctx.moveTo(actionsOffsetX + i * actionsSize, actionsOffsetY);
                ctx.lineTo(actionsOffsetX + i * actionsSize, actionsOffsetY + (actions.length) * actionsSize);
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(actionsOffsetX, actionsOffsetY + i * actionsSize);
                ctx.lineTo(actionsOffsetX + (actions.length) * actionsSize, actionsOffsetY + i * actionsSize);
                ctx.stroke();
            }

            if (isKing) {
                ctx.font = "20px Arial";
                ctx.fillStyle = "#111111";
                ctx.fillText("♔", this.canvas.width - 20, 37);
            }
        }
        // Legend
        for (i = 0; i < actionLegend.length; i++) {
            ctx.fillStyle = this.mapActionToColor(actionLegend[i]);
            //console.log(actionLegend[i]);
            ctx.fillRect(actionsOffsetX, actionsOffsetY + actions.length * actionsSize + (i + 1) * 2 * actionsSize, actionsSize, actionsSize);
            ctx.font = "10px Arial";
            ctx.fillText(this.mapActionToText(actionLegend[i]), actionsOffsetX + actionsSize * 2, actionsOffsetY + actions.length * actionsSize + (i + 1) * 2 * actionsSize + actionsSize);

        }

    }


    mapActionToColor(str) {
        if (str === "P") {
            return "#11AA11";
        } else if (str === "E") {
            return "#BBBB11";
        } else if (str === "F") {
            return "#1111BB";
        } else if (str === "M") {
            return "#11BBBB";
        } else if (str === "S") {
            return "#BB55BB";
        } else if (str === "R") {
            return "#BB1111";
        } else {
            return "#666666";
        }
    }

    mapActionToText(str) {
        if (str === "P") {
            return "piece location";
        } else if (str === "E") {
            return "attack only";
        } else if (str === "F") {
            return "move only";
        } else if (str === "M") {
            return "walk a way";
        } else if (str === "S") {
            return "swap positions";
        } else if (str === "R") {
            return "rush";
        } else {
            return "attack or just move";
        }
    }

    drawPieceCard(actions, pieceImage, owner, isKing) {
        this.drawCanvas(actions, pieceImage, owner, isKing);
        return (
            this.canvas
        );
    }

}
export default PieceCard;