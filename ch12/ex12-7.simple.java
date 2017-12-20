// 리스트 12-7 Value를 사용하는 간단한 예제

import nz.sodium.*;
import java.util.Optional;

public class simple {
    public static void main(String[] args) {
	BackEnd be = new BackEnd();
	Value<Integer> vAge = be.allocate("age", 0);
	StreamSink<Integer> sAge = new StreamSink<>();
	ValueOutput<Integer> out = vAge.construct(sAge);
	Cell<Optional<Integer>> age = out.value;
	Listener l = age.listen(oa -> {
	    System.out.println("age = "+(
	        os.isPresent() ? Integer.toString(oa.get())
		               : "<empty"));
	});
	try { Thread.sleep(1000); } catch (InterruptedException e) {}
	System.out.println("SEND 5");
	sAge.send(5);
	try { Thread.sleep(1000); } catch (InterruptedException e) {}
	l.unlisten();
    }
}

------ Output ------
simple:
    [java] age = <empty>
    [java] BackEnd: age -> 0
    [java] age = 0
    [java] SEND 5
    [java] BackEnd: age <- 5
    [java] BackEnd: age -> 5
    [java] age = 5
