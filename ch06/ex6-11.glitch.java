// 리스트 6-11 소듐은 도시 이벤트를 어떻게 처리하는가?

import nz.sodium.*;

public class glitch {
    public static void main(String[] args) {
	CellSink<Integer> ones = new CellSink<>(1);
	Cell<Integer> hundreds = ones.map(o -> o * 100);
	Cell<Integer> sum = ones.lift(hundreds, (o, h) -> o + h);
	Listener l = sum.listen(s -> System.out.println(s));
	ones.send(2);
	l.unlisten();
    }
}
