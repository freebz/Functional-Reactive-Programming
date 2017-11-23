// 리스트 2-3 map을 사용해 텍스트 뒤집기

import javax.swing.*;
import java.awt.FlowLayout;
import swidgets.*;
import nz.sodium.*;

public class reverse {
    public static void main(String[] args) {
	JFrame frame = new JFrame("reverse");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new FlowLayout());
	STextField = new STextField("Hello");
	Cell<String> reversed = msg.text.map(t ->
	    new StringBuilder(t).reverse().toString());
	SLabel lbl = new SLabel(reversed);
	frame.add(msg);
	frame.add(lbl);
	frame.setSize(400, 160);
	frame.setVisible(true);
    }
}
