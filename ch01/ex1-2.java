// 리스트 1-2  FRP를 사용한 항공권 예약 예제

SDateField dep = new SDateField();
SDateField ret = new SDateField();
Cell<Boolean> valid = dep.date.lift(ret.date,
				    (d, r) -> d.compareTo(r) <= 0);
SButton ok = new SButton("OK", valid);
