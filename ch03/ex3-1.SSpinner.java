// 리스트 3-1 SSpinner: 여러분 자신의 스피너 위젯

import javax.swing.*;
import java.awt.*;
import swidgets.*;
import nz.sodium.*;

public class SSpinner extends JPanel {
    SSpinner(int initialValue) {
	StreamLoop<Integer> sSetValue = new StreamLoop<>();
	STextField textField = new STextField(
	    sSetValue.map(v -> Integer.toString(v)),
	    Integer.toString(initialValue),
	    5
	);
	this.value = textField.text.map(txt -> {
	    try {
		return Integer.parseInt(txt);
	    }
	    catch (NumberFormatException e) {
		return 0;
	    }
	});
	SButton plus = new SButton("+");
	SButton minus = new SButton("-");

	setLayout(new GridBagLayout());
	add(textField, ...);
	add(plus, ...);
	add(minus, ...);

	Stream<Integer> sPlusDelta = plus.sClicked.map(u -> 1);
	Stream<Integer> sMinusDelta = minus.sClicked.map(u -> -1);
	Stream<Integer> sDelta = sPlusDelta.orElse(sMinusDelta);
	sSetValue.loop(
	    sDelta.snapshot(
	        this.value,
		(delta, value) -> delta + value
	));
    }
    public final Cell<Integer> value;
}
