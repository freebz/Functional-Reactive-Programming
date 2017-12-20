// 리스트 12-23 textfield 예제 메인 프로그램

import fridgets.*;
import javax.swing.*;
import java.util.ArrayList;
import nz.sodium.*;

public class textfield {
    public static void main(String[] args) {
	JFrame frame = new JFrame("button");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setContentPane(Transaction.run(() -> {
	    FrTextField firstName = new FrTextField("Joe");
	    FrTextField lastName = new FrTextField("Bloggs");
	    FrButton ok = new FrButton(new Cell<>("OK"));
	    ArrayList<Fridget> fridgets = new ArrayList<>();
	    fridgets.add(ok);
	    fridgets.add(cancel);
	    Fridget buttons = new FrFlow(FrFlow.Direction.HORIZONTAL, fridgets);
	    fridgets = new ArrayList<>();
	    fridgets.add(firstName);
	    fridgets.add(lastName);
	    fridgets.add(buttons);
	    Fridget dailog =
		new FrFlow(FrFlow.Direction.VERTICAL, fridgets);
	    Listener l =
		ok.sClicked
		      .map(u -> firstName.text.sample()+" "+
			        lastName.text.sample())
		  .append(
		      cancel.sClicked.listen(
			  u -> System.out.println("Cancel")
		      )
		  );
	    return new FrView(frame, dialog) {
		public void removeNotify() {
		    super.removeNotify();
		    l.unlisten();
		}
	    };
	}));
	frame.setSize(360,120);
	frame.setVisible(true);
    }
}
