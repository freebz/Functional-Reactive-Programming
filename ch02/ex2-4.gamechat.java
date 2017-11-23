// 리스트 2-4 두 상용구 메시지 소스를 하나로 합치기

import javax.swing.*;
import java.awt.FlowLayout;
import swidget.*;
import nz.sodium.*;

public class gamechat {
    public static void main(String[] args) {
	JFrame frame = new JFrame("gamechat");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new FlowLayout());
	SButton onegai = new SButton("Onegai shimasu");
	SButton thanks = new SButton("Thank you");
	Stream<String> sOnegai = onegai.sClicked.map(u ->
	    "Onegai shimasu");
	Stream<String> sThanks = thanks.sClicked.map(u -> "Thank you");
	Stream<String> sCanned = sOnegai.orElse(sThanks);
	STextField text = new STextField(sCanned, "");
	frame.add(text);
	frame.add(onegai);
	frame.add(thanks);
	frame.setSize(400, 160);
	frame.setVisible(true);
    }
}
