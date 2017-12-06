// 리스트 5-2 선택한 원소의 수에 따라 마우스 커서 모양 바꾸기

public interface SelectionListener {
    void selected(Element e);
    void deselected(Element e);
}

public class CursorMonitor implements SelectionListener {
    private HashSet<Element> selectedElts = new HashSet();
    public void selected(Element e) {
	selectedElts.add(e);
	updateCursor();
    }
    public void deselected(Element e) {
	selectedElts.remove(e);
	updateCursor();
    }
    private void updateCurosr() {
	if (slectedElts.isEmpty()) crosshairCursor(); else
	                           arrowCursor();
    }
}
