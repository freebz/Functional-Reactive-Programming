// 리스트 2-2 레이블 예제: 텍스트 필드의 값을 보여주는 레이블

import javax.swing.*;
import java.awt.FlowLayout;
import swidgets.*;
import nz.sodium.*;

public class label {
    public static void main(String[] args) {
	JFrame frame = new JFrame("label");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new FlowLayout());
	STextField msg = new STExtField("Hello");
	SLabel lbl = new SLabel(msg.text);
	frame.add(msg);
	frame.add(lbl);
	frame.setSize(400, 160);
	frame.setVisible(true);
    }
}
