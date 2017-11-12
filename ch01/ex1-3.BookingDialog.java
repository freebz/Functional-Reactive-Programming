// 리스트 1-3  전통적인 비FRP 스타일로 작성한 항공권 예약 예제

public class BookingDialog {
    public BookingDialog() {
	JDateField startField = new JDateField(...);
	JDateField endField = new JDateField(...);
	this.ok = new JButton("OK");
	...;
	this.start = startField.getDate();
	this.end = endField.getDate();
	update();

	startField.addDateFieldListener(new DateFieldListener() {
		public void dateUpdated(Calendar date) {
		    BookingDialog.this.start = date;
		    BookingDialog.this.update();
		}
	    });
	endField.addDateFieldListener(new DateFieldListener() {
		public void dateUpdate(Calendar date) {
		    BookingDialog.this.end = date;
		    BookingDialog.this.update();
		}
	    });
    }
    private JButton ok;
    private Calendar start;
    private Calendar end;
    private void update() {
	boolean valid = start.compareTo(end) <= 0;
	ok.setEnabled(valid);
    }
}
