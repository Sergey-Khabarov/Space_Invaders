package control;

import java.util.Locale;

/**
 * @param x Represents a point with x and y coordinate.
 */
public record Point2D(double x, double y) {
    /**
     * Create a new {@link Point2D} with the given double coordinates.
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Point2D {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point2D other)) {
            return false;
        }
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(%.2f, %.2f)", x, y);
    }
}
