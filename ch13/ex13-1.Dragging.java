// 리스트 13-1 전통적인 객체 지향 방식 드래그 앤 드롭 로직의 의사 자바 코드

class Dragging {
    Dragging(Element elt, Point origMousePOs) { ... }
    Element elt;
    Point origMousePOs;

    FloatingElement floatingElement(Point curMousePOs) {
	Vector moveBy = curMousePOs.subtract(origMousePOs);

	return new FloatingElement(elt.getPosition().add(moveBy), elt);
    }
}

class FloatingElement {
    FloatingElement(Point position, Element elt) { ... }
    Point position;
    Element elt;
}

class DragAndDrop implements MouseListener
{
    Document doc;
    Window window;
    Dragging dragging = null;

    DragAndDrop(Document doc, Window window) {
	...
	window.addMouseListener(this);
    }
    void mouseDown(Point mousePos) {
	Element elt = doc.lookupByPosition(mousePos);
	if (elt != null)
	    dragging = new Dragging(elt, mousePos);
    }
    void mouseMove(Point mousePos) {
    }
    void mouseUp(Point mousePos) {
	if (dragging != null) {
	    FloatingElement flt = dragging.floatingElement(mousePos);
	    doc.moveTo(flt.elt, flt.position);
	    dragging = null;
	    window.repaint();
	}
    }
}
