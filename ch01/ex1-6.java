// 리스트 1-6  추상적인 비즈니스 규칙 조작하기

private static boolean unlucky(Calendar dt) {
    int day = dt.get(Calendar.DAY_OF_MONTH);
    return day == 4 || day == 14 || day == 24;
}
...;
SDateField dep = new SDateField();
SDateField ret = new SDateField();
Rule r1 = new Rule((d, r) -> d.compareTo(r) <= 0);
Rule r2 = new Rule((d, r) -> !unlucky(d) && !unlucky(r));
Rule r = r1.and(r2);
Cell<Boolean> valid = r.reify(dep.date, ret.date);
SButton ok = new SButton("OK", valid);
