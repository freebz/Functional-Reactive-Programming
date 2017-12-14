// 리스트 10-3 세 패러다임을 위한 공통 인터페이스

interface Paradigm {
    interface DocumentListener {
	void documentUpdated(Document doc);
    }
    interface Factory {
	Paradigm create(Document initDoc, DocumentListener dl);
    }
    void mouseEvent(MouseEvt me);
    void dispose();
}
