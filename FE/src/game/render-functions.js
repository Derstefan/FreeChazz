
class RenderFunctions {
    static renderMatterAnimation(ctx, matterBodies, frameCount) {


        //TODO: compute forces? i think not
        // opacity down
        // delete when opacity is 0 or below

        for (var i = 0; i < matterBodies.length; i += 1) {
            matterBodies[i].alpha -= frameCount * 0.0001;
            if (matterBodies[i].alpha <= 0) matterBodies[i].alpha = 0;
            ctx.globalAlpha = matterBodies[i].alpha;

            if (matterBodies[i].visible !== false) {
                var vertices = matterBodies[i].body.vertices;

                ctx.beginPath();
                ctx.moveTo(vertices[0].x, vertices[0].y);

                for (var j = 1; j < vertices.length; j += 1) {
                    ctx.lineTo(vertices[j].x, vertices[j].y);
                }

                ctx.lineTo(vertices[0].x, vertices[0].y);
                //matterBodies[i].render.opacity -= frameCount * 0.0001;//render.opacity is one....!!!

                ctx.fillStyle = matterBodies[i].color;

                ctx.fill();
            }
        }



    }
}
export default RenderFunctions
