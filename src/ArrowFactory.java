import java.awt.*;
import java.awt.geom.AffineTransform;

class ArrowFactory {
    private static final int X_MULT = 10;
    private static final int Y_MULT = 3;

    static Shape createArrowShape(int fromX, int fromY, int toX, int toY) {
        Polygon arrowPolygon = new Polygon();
        arrowPolygon.addPoint(-6*X_MULT,  1*Y_MULT);
        arrowPolygon.addPoint( 3*X_MULT,  1*Y_MULT);
        arrowPolygon.addPoint( 3*X_MULT,  3*Y_MULT);
        arrowPolygon.addPoint( 6*X_MULT,  0*Y_MULT);
        arrowPolygon.addPoint( 3*X_MULT, -3*Y_MULT);
        arrowPolygon.addPoint( 3*X_MULT, -1*Y_MULT);
        arrowPolygon.addPoint(-6*X_MULT, -1*Y_MULT);


        int midX = (fromX + toX) / 2;
        int midY = (fromY + toY) / 2;
        double rotate = Math.atan2(toY - fromY, toX - fromX);
        int dx = fromX - toX;
        int dy = fromY - toY;
        double ptDistance = Math.sqrt(dx*dx + dy*dy);

        double scale = ptDistance / (12.0 * X_MULT); //12 is basic arrow length

        AffineTransform transform = new AffineTransform();
        transform.translate(midX, midY);
        transform.scale(scale, scale);
        transform.rotate(rotate);

        return transform.createTransformedShape(arrowPolygon);
    }
}