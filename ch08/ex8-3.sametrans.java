// 리스트 8-3 같은 트랜잭션 안에서 send()와 listen()을 함께 사용하기

import nz.sodium.*;
public class sametrans {
    public static void main(String[] args) {
	StreamSink<Integer> sX = new StreamSink<>();
	Stream<Integer> sXPlus1 = sX.map(x -> x + 1);
	Listener l = Transaction.run(() -> {
	    xS.send(1);
	    Listener l_ = sXPlus1.listen(x -> { System.out.println(x); });
	    return l_;
	});
	xS.send(2);
	sX.send(3);
	l.unlisten();
    }
}
------ Output ------
sametrans:
    [java] 2
    [java] 3
    [java] 4
