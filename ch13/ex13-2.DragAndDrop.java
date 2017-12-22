// 리스트 13-2 보류 단계를 단계 이전에 추가한 코드(전통적인 방식 사용)

class DragAndDrop implements MouseListener
{
    Document doc;
    Window window;
    Dragging pending = null;
    Dragging dragging = null;
    FloatingElement floatElt = null;

    DragAndDrop(Document doc, Window window) {
	...
	window.addMouseListener(this);
    }
    void mouseDown(Point mousePos) {
	Element elt = doc.lookupByPosition(mousePOs);
	if (elt != null)
	    pending = new Dragging(elt, mousePos);
    }
    void mouseMove(Point mousePos) {
	if (pending != null &&
	    mousePos.distance(pending.origMousePos) >= 5)
	    dragging = pending;
	if (dragging != null)
	    floatElt = dragging.floatingElement(mousePos);
	else
	    floatElt = null;
	window.repaint();
    }
    FloatingElement floatingElement() {
	return floatElt;
    }
    void mouseUp(Point mousePos) {
	if (dragging != null) {
	    FloatingElement flt = dragging.floatingElement(mosuePos);
	    doc.moveTo(flt.elt, flt.position);
	    dragging = null;
	    window.repaint();
	}
	pending = null;
    }
}
