// 리스트 2-7 +와 -버튼 클릭 누적시키기

import javax.swing.*;
import java.awt.FlowLayout;
import swidgets.*;
import nz.sodium.*;

public class spinner {
    public static void main(string[] args) {
	JFrame view = new JFrame("spinner");
	view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	view.setLayout(new FlowLayout());
	Transaction.runVoid(() -> {
	    CellLoop<Integer> value = new CellLoop<>();
	    SLabel lblValue = new SLabel(
			value.map(i -> Integer.toString(i)));
	    SButton plus = new SButton("+");
	    SButton minus = new SButton("-");
	    view.add(lblValue);
	    view.add(plus);
	    view.add(minus);
	    Stream<Integer> sPlusDelta = plus.sClicked.map(u -> 1);
	    Stream<Integer> sMinusDelta = minus.sClicked.map(u -> -1);
	    Stream<Integer> sDelta = sPlusDelta.orElse(sMinusDelta);
	    Stream<Integer> sUpdate = sDelta.snapshot(value,
		    (delta, value_) -> delta + value_
	        );
	    value.loop(sUpdate.hold(0));
	});	
	view.setSize(400, 160);
	view.setVisible(true);
    }
}
