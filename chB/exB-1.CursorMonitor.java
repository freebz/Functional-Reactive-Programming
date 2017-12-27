// 리스트 B.1 선택된 아이템 수에 따라 마우스 커서 설정하기

public interface SelectionListener {
    void selected(Item i);
    void deselected(Item i);
}

public class CursorMonitor implements SelectionListener {
    private HashSet<Item> selectedItems = new HashSet();
    public void selected(Item i) {
	selectedItems.add(i);
	updateCursor();
    }
    public void deselected(Item i) {
	selectedItems.remove(i);
	updateCursor();
    }
    private void updateCursor() {
	if (selectedElts.isEmpty()) crosshairCurosr();
	else arrowCursor();
    }
}
