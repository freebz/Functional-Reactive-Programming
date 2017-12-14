// 리스트 10-1 폴리곤으로 이뤄진 Document의 외부 인터페이스

class Entry {
    public final String id;
    public final Element element;
}
class Document {
    public Optional<Entry> getByPoint(Point pt);
    public Document insert(String id, Element polygon);
    public void draw(Graphics g);
}
