// 리스트 8-1 데이터를 스트림에 보내고 리슨하기

import nz.sodium.*;
public class stream {
    public static void main(String[] args) {
	StreamSink<Integer> sX = new StreamSink<>();
	Stream<Integer> sXPlus = sX.map(x -> x + 1);
	Listener l = sXPlus1.listen(x -> { System.out.println(x); });
	sX.send(1);
	sX.send(2);
	sX.send(3);
	l.unlisten();
    }
}
------ Output ------
stream:
    [java] 2
    [java] 3
    [java] 4
