
class UtilFunctions {

    // check point is in polygon
    // generate point in polygon



    static polygonArea(points) {
        if (points === undefined) {
            return 0.0;
        }
        const num = points.length;
        if (num < 3) {
            return 0.0;
        }
        var area = 0.0;

        for (var i = 0; i < num; i++) {
            area += (points[i].y + points[(i + 1) % num].y) * (points[i].x - points[(i + 1) % num].x);
        }
        return Math.abs(area / 2.0);
    }



}

export default UtilFunctions