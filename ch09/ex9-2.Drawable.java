// 리스트 9-2 그릴 수 있는 장면을 표현하는 Drawable

import java.awt.Graphics;
public class Drawable {
    public void draw(Graphics g, Point orig, double scale) {}
    public final Drawable append(Drawable second) {
	Drawable first = this;
	return new Drawable() {
	    public void draw(Graphics g, Point orig, double scale) {
		first.draw(g, orig, scale);
		second.draw(g, orig, scale);
	    }
	};
    }
}
