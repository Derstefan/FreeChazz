import Config from "./config.json";

class PieceCard {

    constructor() {
        this.canvas = document.createElement('canvas');
        this.ctx = this.canvas.getContext('2d');
        this.canvas.width = Config.card.width;
        this.canvas.height = Config.card.height;
    }

    drawCanvas(piece, pieceImage) {
        const actionsSize = Config.card.actionsSize;
        const actionsOffsetX = Config.card.actionsOffsetX;
        const actionsOffsetY = Config.card.actionsOffsetY;
        var ctx = this.ctx;

        if (piece.actions !== undefined) {
            ctx.canvas.width = Config.card.width;
            ctx.canvas.height = Config.card.height;

            ctx.drawImage(pieceImage, 32, 10);
            // draw actions
            console.log(Config.actionsSize);
            for (var i = 0; i < piece.actions.actions.length; i++) {
                for (var j = 0; j < piece.actions.actions[0].length; j++) {
                    if (piece.actions.actions[i][j] !== "-") {
                        if (piece.actions.actions[i][j] === "P") {
                            console.log("draw")
                            ctx.fillStyle = "#11AA11";
                            ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                        } else if (piece.actions.actions[i][j] === "E") {
                            ctx.fillStyle = "#BB1111";
                            ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                        } else if (piece.actions.actions[i][j] === "F") {
                            ctx.fillStyle = "#1111BB";
                            ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                        } else {
                            ctx.fillStyle = "#666666";
                            ctx.fillRect(actionsOffsetX + i * actionsSize, actionsOffsetY + j * actionsSize, actionsSize, actionsSize);
                        }
                    }
                }
            }

            ctx.lineWidth = 0.5;
            ctx.strokeStyle = "#AAAAAA";
            for (i = 0; i < piece.actions.actions.length + 1; i++) {
                //  ctx.drawline(actionsOffsetX + i * actionsSize, actionsOffsetY, actionsOffsetX + i * actionsSize, actionsOffsetX + piece.actions.actions.length + 1 * actionsSize);
                //console.log(i);

                ctx.beginPath();
                ctx.moveTo(actionsOffsetX + i * actionsSize, actionsOffsetY);
                ctx.lineTo(actionsOffsetX + i * actionsSize, actionsOffsetY + (piece.actions.actions.length) * actionsSize);
                ctx.stroke();

                ctx.beginPath();
                ctx.moveTo(actionsOffsetX, actionsOffsetY + i * actionsSize);
                ctx.lineTo(actionsOffsetX + (piece.actions.actions.length) * actionsSize, actionsOffsetY + i * actionsSize);
                ctx.stroke();
            }
        }
    }

    drawPieceCard(piece, pieceImage) {
        this.drawCanvas(piece, pieceImage);
        return (
            this.canvas
        );
    }
}

export default PieceCard