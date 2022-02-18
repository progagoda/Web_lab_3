package utils;

public class HitChecker {

    public boolean isHit(double x, double y, double r) {
        return isHitCircle(x, y, r) || isHitSquare(x, y, r) || isHitTriangle(x, y, r);
    }

    public boolean isHitCircle(double x, double y, double r) {
        return ((x * x + y * y <= r * r) && (x >= 0 && y <= 0));
    }

    public boolean isHitSquare(double x, double y, double r) {
        return (y <= r) && (x >= -r) && (x <= 0) && (y >= 0);
    }

    public boolean isHitTriangle(double x, double y, double r) {
        return ((y >= ((-2.0) * x - (r))) && (x <= 0) && (y <= 0));
    }
}

