// 리스트 8-2 데이터를 셀로 보내고 리슨하기

import nz.sodium.*;
public class cell {
    public static void main(String[] args) {
	CellSink<Integer> x = new CellSink<>(0);
	Listener l = x.listen(x_ -> { System.out.println(x_); });
	x.send(10);
	x.send(20);
	x.send(30);
	l.unlisten();
    }
}
------ Output ------
cell:
    [java] 0
    [java] 10
    [java] 20
    [java] 30
