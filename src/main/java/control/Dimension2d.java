package control;

import java.util.Locale;

public record Dimension2d(double width, double height) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dimension2d other)) {
            return false;
        }
        return height == other.height && width == other.width;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Dimension2d [width=%.2f, height=%.2f]", width, height);
    }
}
