// 리스트 3-2 입력 검증 예제

Transaction.runVoid(() -> {
    final int maxEmails = 4;

    JLabel[] labels = new JLabel[maxEmails+2];
    JComponent[] fields = new JComponent[maxEmails+2];
    Cell<String>[] valids = (Cell<String>[])Array.newInstance(
	Cell.class, maxEmails+2);
    int row = 0;

    labels[row] = new JLabel("Name");
    STextField name = new STextField("", 30);
    fields[row] = name;
    valids[row] = name.text.map(t ->
        t.trim().equals("") ? "<-- enter something" :
        t.trim().indexOf(' ') < 0 ? "<-- must contain space" :
				"");
    row++;

    labels[row] = new JLabel("No of email address");
    SSpinner number = new SSpiner(1);
    fields[row] = number;
    valids[row] = number.value.map(n ->
        n < 1 || n > maxEmails ? "<-- must be 1 to "+maxEmails
				   : "");
    row++;

    STextField[] emails = new STextField[maxEmails];
    for (int i = 0; i < maxEmails; i++, row++) {
	labels[row] = new JLabel("Email #"+(i+1));
	final int ii = i;
	Cell<Boolean> enabled = number.value.map(n -> ii < n);
	STextField email = new STextField("", 30, enabled);
	fields[row] = email;
	valids[row] = email.text.lift(number.value, (e, n) ->
                    ii >= n             ? "" :
		    e.trim().equals("") ? "<-- enter something" :
		    e.indexOf('@') < 0  ? "<-- must contain @" :
				          "");
    }

    Cell<Boolean> allValid = new Cell<Boolean>(true);
    for (int i = 0; i < row; i++) {
	view.add(labels[i], ...);
	view.add(fields[i], ...);
	SLabel validLabel = new SLabel(valids[i]);
	view.add(validLabel, ...);
	Cell<Boolean> thisValid = valids[i].map(t -> t.equals(""));
	allValid = allValid.lift(thisValid, (a, b) -> a && b);
    }
    SButton ok = new SButton("OK", allValid);
    view.add(ok, ...);
});
