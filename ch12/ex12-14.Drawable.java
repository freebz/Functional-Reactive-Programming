// 리스트 12-14 화면에 그려져야 하는 대상

package fridgets;

import java.awt.Graphics;

public class Drawable {
    public void draw(Graphics g) {}
    public final Drawable append(Drawable second) {
	Drawable first = this;
	return new Drawable() {
	    public void draw(Graphics g) {
		first.draw(g);second.draw(g);
	    }
	};
    }
}
