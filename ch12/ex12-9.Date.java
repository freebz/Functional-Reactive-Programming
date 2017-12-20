// 리스트 12-9 원격 서버 값을 위한 GUI 클라이언트

class Date {
    public Date(int year, int month, int day) {
	this.year = year; this.month = month; this.day = day; }
    public final int year, month, day;
    public final Date setYear(int year_) {
	                        return new Date(year_, month, day); }
    public final Date setMonth(int month_) {
	                        return new Date(year, month_, day); }
    public final Date setDay(int day_) {
	                        return new Date(year, month, day_); }
    public String toString() { return year+"."+month+"."+day; }
}
...
    BackEnd be = new BakEnd();
    Value<String> vName = be.allocate("name", "Joe Bloggs");
    value<Date> vBirthDate = be.allocate("birthDate",
        new Date(1980, 5, 1));

    Value<Integer> vYear = vBirthDate.lens(
	d -> d.year,
	(dt, y) -> dt.setYear(y)
    );
    Value<Integer> vMonth = vBirthDate.lens(
	d -> d.month,
	(dt, y) -> dt.setMonth(y)
    );
    Value<Integer> vDay = vBirthDate.lens(
	d -> d.day,
	(dt, y) -> dt.setDay(y)
    );
    Bijection<Integer,String> toString = new Bijection<>(
	    i -> Integer.toString(i),
	    s -> {
		try { return Integer.parseInt(s); }
		catch (NumberFormatException e) {
		    return 0;
		}
	    });
    Value<String> vYearStr = vYear.map(toString);
    Value<String> vMonthStr = vMonth.map(toString);
    Value<String> vDayStr = vDay.map(toString);
...
	        client.add(new JLabel("Name"), c);
		client.add(new VTextField(vName, 15), c);
		client.add(new JLabel("Birth date"), c);
		client.add(new VTextField(vYearStr, 4), c);
		client.add(new VTextField(vMonthStr, 2), c);
		client.add(new VTextField(vDayStr, 2), c);

		client.setSize(300,100);
		client.setVisible(true);
