// 리스트 1-4  더 자세한 항공권 예약 예제

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import swidgets.*;
import nz.sodium.*;

public class airline1 {
    public static void main(String[] args) {
	JFrame view = new JFrame("airline1");
	view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	SDateField dep = new SDateField();
	SDateField ret = new SDateField();
	Cell<Boolean> valid = dep.date.lift(ret.date,
					    (d, r) -> d.compareTo(r) <= 0);
	SButton ok = new SButton("OK", valid);

	GridBagLayout gridbag = new GridBagLayout();

	view.setLayout(gridbag);
	GridBagConstraints c = new GridBagConstraints();
	...;
	view.add(new JLabel("departure"), c);
	view.add(dep, c);
	view.add(new JLabel("return"), c);
	view.add(ok, c);
	view.setSite(380, 140);
	view.setVisible(true);
    }
}
