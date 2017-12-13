// 리스트 8-5 value가 예상대로 작동하지 않는 경우

import nz.sodium.*;
public class value1 {
    public static void main(String[] args) {
	CellSink<Integer> x = new CellSink<>(0);
	x.send(1);
	Listener l = Operational.value(x).listen(x_ -> {
	    System.out.println(x_);
	});
	x.send(2);
	x.send(3);
	l.unlisten();
    }
}
------ Output ------
value1:
    [java] 2
    [java] 3
